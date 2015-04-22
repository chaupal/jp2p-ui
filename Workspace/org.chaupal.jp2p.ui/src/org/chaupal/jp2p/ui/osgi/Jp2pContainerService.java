/*******************************************************************************
 * Copyright (c) 2013 Condast and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Kees Pieters - initial API and implementation
 *******************************************************************************/
package org.chaupal.jp2p.ui.osgi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import net.jp2p.chaupal.container.AbstractContainerRefresh;
import net.jp2p.container.IJp2pContainer;
import net.jp2p.container.Jp2pContainer;
import net.jp2p.container.component.ComponentChangedEvent;
import net.jp2p.container.component.IComponentChangedListener;
import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.log.LoggerFactory;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class Jp2pContainerService<T extends Object> extends AbstractContainerNode<T>{

	private static final String S_ERR_NO_CONTAINERS_FOUND = " No Containers are found. Please verify that the JP2P bundles are activated correctly, and are in debug mode. ";	

	private String filter = "(objectclass=" + IJp2pContainer.class.getName() + ")";
		
	private BundleContext context;
	
	private ServiceTracker<?,?> containerTracker;
	
	private Collection<IComponentChangedListener<IJp2pComponent<T>>> listeners;
	
	private AbstractContainerRefresh<T> refresh = new AbstractContainerRefresh<T>( AbstractContainerRefresh.DEFAULT_TIME ){

		@Override
		protected void onRefresh(
				ComponentChangedEvent<IJp2pComponent<T>> event) {
			for( IComponentChangedListener<IJp2pComponent<T>> listener:  listeners ){
				listener.notifyServiceChanged(event);
			}
			
		}		
	};
	
	//This listeners registers itself with all the containers
	private IComponentChangedListener<IJp2pComponent<T>> listener = new IComponentChangedListener<IJp2pComponent<T>>(){

		@Override
		public void notifyServiceChanged(
				ComponentChangedEvent<IJp2pComponent<T>> event) {
			refresh.refresh(event);
		}	
	};

	private Logger logger = Logger.getLogger( this.getClass().getName() );

	private ServiceListener sl = new ServiceListener() {

		@SuppressWarnings("unchecked")
		public void serviceChanged(ServiceEvent ev) {
			ServiceReference<?> sr = ev.getServiceReference();
			Object obj = context.getService(sr);
			if( obj == null )
				return;
			System.out.println( obj.getClass().getName());
					
			IJp2pContainer<T> container = (IJp2pContainer<T>)obj;
			switch(ev.getType()) {
			case ServiceEvent.REGISTERED:
				LoggerFactory.getJp2pLogMessage( container.getPropertySource(), Jp2pContainer.printContainerStructure(container));
				addChild( container );
				container.getDispatcher().addServiceChangeListener(listener);
				break;
			case ServiceEvent.UNREGISTERING:
				removeChild( container );
				container.getDispatcher().removeServiceChangeListener(listener);
				break;
			default:
				break;
			}
		}
	};

	//private 
	public Jp2pContainerService() {
		super();
		listeners = new ArrayList<IComponentChangedListener<IJp2pComponent<T>>>();
	}

	public void addServiceChangeListener( IComponentChangedListener<IJp2pComponent<T>> listener ){
		listeners.add( listener );
	}

	public void removeServiceChangeListener( IComponentChangedListener<IJp2pComponent<T>> listener ){
		listeners.remove( listener );
	}

	public void start(final BundleContext bundleContext) throws Exception {
		context = bundleContext;
		containerTracker = new ServiceTracker<Object, Object>( bundleContext, IJp2pContainer.class.getName(), null );
		containerTracker.open();
		bundleContext.addServiceListener(sl, filter);
		ServiceReference<?>[] srl = bundleContext.getServiceReferences( IJp2pContainer.class.getName(), filter);
		if( srl == null ){
			logger.severe( S_ERR_NO_CONTAINERS_FOUND );
			return;	
		}
		for( ServiceReference<?> sr:  srl ) {
			sl.serviceChanged(new ServiceEvent(ServiceEvent.REGISTERED, sr));
		}
	}
}