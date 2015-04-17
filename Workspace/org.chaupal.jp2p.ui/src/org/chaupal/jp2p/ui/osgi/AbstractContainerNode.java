/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/

package org.chaupal.jp2p.ui.osgi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.jp2p.container.Jp2pContainer;
import net.jp2p.container.Jp2pContainer.ServiceChange;
import net.jp2p.container.IJp2pContainer;
import net.jp2p.container.component.ComponentChangedEvent;
import net.jp2p.container.component.ComponentEventDispatcher;
import net.jp2p.container.component.IComponentChangedListener;
import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.component.Jp2pComponentNode;
import net.jp2p.container.properties.AbstractJp2pWritePropertySource;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.utils.StringStyler;

import org.chaupal.jp2p.ui.Activator;
import org.chaupal.jp2p.ui.comparator.Jp2pServiceComparator;
import org.chaupal.jp2p.ui.log.Jp2pLog;
import org.chaupal.jp2p.ui.osgi.RootPropertySource.PetitionerProperties;

public class AbstractContainerNode<T extends Object> extends Jp2pComponentNode<T>
{
	public static final String S_WRN_THREAD_INTERRUPTED = "The thread is interrupted. Probably stopping service";
	
	private List<IJp2pContainer<T>> containers;

	private IComponentChangedListener<IJp2pComponent<?>> listener;
	
	private RefreshRunnable refresher;
	
	protected AbstractContainerNode() {
		super( new RootPropertySource(), null );
		super.setRoot(true);
		containers = new ArrayList<IJp2pContainer<T>>();
		refresher = new RefreshRunnable( (RootPropertySource) super.getPropertySource() );
		this.listener = new IComponentChangedListener<IJp2pComponent<?>>() {
			
			@Override
			public void notifyServiceChanged(ComponentChangedEvent<IJp2pComponent<?>> event) {
				refresher.start();
			}
		};
	}

	public IJp2pContainer<?> getJp2pContainer( String identifier ) {
		for( IJp2pContainer<?> container: this.containers )
			if( container.getIdentifier().equals( identifier ))
				return container;
		return null;
	}

		@Override
	public IJp2pComponent<?>[] getChildren() {
		return containers.toArray( new IJp2pComponent<?>[ this.containers.size()]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addChild(IJp2pComponent<?> child) {
		if( containers.contains( child ))
			return false;
		containers.add( (IJp2pContainer<T>) child );
		IJp2pContainer<?> container = (IJp2pContainer<?>) child;
		container.getDispatcher().addServiceChangeListener( listener );
		Collections.sort( containers, new Jp2pServiceComparator<Object>());
		return true;
	}

	@Override
	public void removeChild(IJp2pComponent<?> child) {
		containers.remove( child );
		IJp2pContainer<?> container = (IJp2pContainer<?>) child;
		container.getDispatcher().removeServiceChangeListener( listener );
	}

	@Override
	public boolean hasChildren() {
		return !this.containers.isEmpty();
	}

	public void finalise(){
		for( IJp2pComponent<?> container: this.containers ){
			((Jp2pContainer<?>) container).getDispatcher().removeServiceChangeListener( listener );
		}
		this.refresher.stop();
	}

	private class RefreshRunnable implements Runnable{

		private ExecutorService service;
		private RootPropertySource source;
		private int counter;
		
		public RefreshRunnable( RootPropertySource source) {
			super();
			this.source = source;
			this.counter = 0;
		}

		/**
		 * Start the runnable thread
		 */
		synchronized void start(){
			counter++;
			if( service != null )
				return;
			service = Executors.newCachedThreadPool();
			service.execute(this);	
		}
		
		/**
		 * Stop the service
		 */
		public void stop(){
			Thread.currentThread().interrupt();
		}
		
		@Override
		public void run() {
			ComponentChangedEvent<IJp2pComponent<T>> event = new ComponentChangedEvent<IJp2pComponent<T>>( this, ServiceChange.STATUS_CHANGE );
			ComponentEventDispatcher dispatcher = getDispatcher();
			while( counter > 0 ){
				counter = 0;
				dispatcher.serviceChanged(event);
				try{
					Thread.sleep((long) this.source.getProperty( PetitionerProperties.REFRESH_TIME ));
				}
				catch( InterruptedException ex ){
					Jp2pLog.logWarning( AbstractContainerNode.S_WRN_THREAD_INTERRUPTED );
				}
			}
			service = null;
		}
	}

	private static class RootPropertySource extends AbstractJp2pWritePropertySource
	{
		private static final String S_ROOT_TEXT = "JP2P Root Container";
		private static final long DEFAULT_TIME_OUT = 500; //msec

		public enum PetitionerProperties implements IJp2pProperties{
			REFRESH_TIME;

			@Override
			public String toString() {
				return StringStyler.prettyString( super.toString() );
			}
		}

		public RootPropertySource() {
			super( Activator.BUNDLE_ID, S_ROOT_TEXT );
			super.setProperty( PetitionerProperties.REFRESH_TIME, DEFAULT_TIME_OUT);
		}
	}
}