/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/

package org.chaupal.jp2p.ui.jxta.osgi.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import net.jp2p.container.Jp2pContainer;
import net.jp2p.container.IJp2pDSComponent;
import net.jp2p.container.Jp2pContainer.ServiceChange;
import net.jp2p.container.component.ComponentChangedEvent;
import net.jp2p.container.component.IComponentChangedListener;
import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.dispatcher.ServiceChangedEvent;
import net.jp2p.container.dispatcher.ServiceEventDispatcher;
import net.jp2p.container.factory.ChaupalContainer;
import net.jp2p.container.utils.ILeaf;
import net.jp2p.container.utils.Utils;
import net.jp2p.jxta.peergroup.PeerGroupFactory;
import net.jp2p.jxta.peergroup.PeerGroupNode;

import org.chaupal.jp2p.ui.log.Jp2pLog;
import org.chaupal.jp2p.ui.osgi.RootPropertySource;
import org.chaupal.jp2p.ui.osgi.RootPropertySource.PetitionerProperties;
import org.eclipselabs.osgi.ds.broker.service.AbstractPalaver;
import org.eclipselabs.osgi.ds.broker.service.AbstractPetitioner;
import org.eclipselabs.osgi.ds.broker.service.ParlezEvent;

public class PeerGroupPetitioner extends AbstractPetitioner<String, String, ChaupalContainer>
{
	public static final String S_WRN_THREAD_INTERRUPTED = "The thread is interrupted. Probably stopping service";
	public static final String S_PEERGROUP_PETITIONER = "PeerGroupPetitioner";
	
	private static PeerGroupPetitioner attendee = new PeerGroupPetitioner();
	
	private List<IJp2pComponent<?>> children;

	private ServiceEventDispatcher dispatcher = ServiceEventDispatcher.getInstance();	
	private IComponentChangedListener<IJp2pComponent<?>> listener;
	private RefreshRunnable refresher;
	private RootPropertySource source;
	
	private Logger logger = Logger.getLogger(PeerGroupPetitioner.class.getName());
	
	private PeerGroupPetitioner() {
		super( new ResourcePalaver());
		children = new ArrayList<IJp2pComponent<?>>();
		source = new RootPropertySource();
		refresher = new RefreshRunnable( source );
		this.listener = new IComponentChangedListener<IJp2pComponent<?>>() {
			
			@Override
			public void notifyServiceChanged(ComponentChangedEvent<IJp2pComponent<?>> event) {
				dispatcher.serviceChanged( new ServiceChangedEvent( this, ServiceChange.COMPONENT_EVENT ));
				refresher.start();
			}
		};
	}
	
	public static PeerGroupPetitioner getInstance(){
		return attendee;
	}

	@Override
	protected void onDataReceived( ParlezEvent<ChaupalContainer> event ) {
		if(!( event.getData() instanceof ChaupalContainer ))
			return;
		super.onDataReceived( event );
		logger.info("Container added: " + event.getData().getIdentifier( ));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PeerGroupNode createPeerGroupTree(){
		PeerGroupNode root = new PeerGroupNode( null ); 
		for( ChaupalContainer container: super.getCollection() ){
			root.addChild(( ILeaf) PeerGroupFactory.createPeerGroupTree( container ));
		}
		return root;
	}
	
	public void finalise(){
		for( IJp2pComponent<?> container: this.children ){
			((Jp2pContainer<?>) container).getDispatcher().removeServiceChangeListener( listener );
		}
		this.refresher.stop();
	}

	/**
	 * The palaver contains the conditions for attendees to create an assembly. In this case, the attendees must
	 * pass a string identifier (the package id) and provide a token that is equal
	 * @author Kees
	 *
	 */
	private static class ResourcePalaver extends AbstractPalaver<String>{

		private static final String S_JP2P_INF = "/JP2P-INF/token.txt";
		
		private String providedToken;

		protected ResourcePalaver() {
			super( getProvidedInfo()[0]);
			this.providedToken = getProvidedInfo()[1];
		}

		private static final String[] getProvidedInfo(){
			Class<?> clss = ResourcePalaver.class;
			String[] info = { S_PEERGROUP_PETITIONER, IJp2pDSComponent.S_IP2P_TOKEN} ;
			URL url = clss.getResource(S_JP2P_INF );
			if( url == null )
				return info;
			Scanner scanner = null;
			try{
				scanner = new Scanner( clss.getResourceAsStream( S_JP2P_INF ));
				String str = scanner.nextLine();
				if( !Utils.isNull(str))
					info[0] = str;
				str = scanner.nextLine();
				if( !Utils.isNull(str))
					info[1] = str;
				return info;
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if( scanner != null )
					scanner.close();
			}
			return null;
			
		}
		
		@Override
		public String giveToken() {
			if( this.providedToken == null )
				return  IJp2pDSComponent.S_IP2P_TOKEN;
			return this.providedToken;	
		}

		@Override
		public boolean confirm(Object token) {
			if( token == null )
				return false;
			boolean retval = token.equals( IJp2pDSComponent.S_IP2P_TOKEN ); 
			if( retval )
				return ( retval );
			return token.equals(this.providedToken );
		}	
	}

	private class RefreshRunnable implements Runnable{

		private ExecutorService service;
		private RootPropertySource source;
		
		public RefreshRunnable( RootPropertySource source) {
			super();
			this.source = source;
		}

		/**
		 * Start the runnable thread
		 */
		synchronized void start(){
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
			ServiceEventDispatcher dispatcher = ServiceEventDispatcher.getInstance();
			dispatcher.serviceChanged( new ServiceChangedEvent(this, ServiceChange.REFRESH ));
			try{
				Thread.sleep((long) this.source.getProperty( PetitionerProperties.REFRESH_TIME ));
			}
			catch( InterruptedException ex ){
				Jp2pLog.logWarning( PeerGroupPetitioner.S_WRN_THREAD_INTERRUPTED );
			}
			service = null;
		}
	}
}