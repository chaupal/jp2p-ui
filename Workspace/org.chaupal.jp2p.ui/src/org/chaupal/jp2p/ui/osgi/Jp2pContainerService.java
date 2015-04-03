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
import java.util.logging.Logger;

import net.jp2p.container.IJp2pContainer;
import net.jp2p.container.builder.ContainerBuilderEvent;
import net.jp2p.container.builder.IContainerBuilderListener;

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
	private Collection<IContainerBuilderListener<T>> containerListeners;
	
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
				addChild( container );
				notifyContainerChanged(container);
				break;
			case ServiceEvent.UNREGISTERING:
				removeChild( container );
				notifyContainerChanged(container);
				break;
			default:
				break;
			}
		}
	};
			
	//private 
	public Jp2pContainerService() {
		super();
		containerListeners = new ArrayList<IContainerBuilderListener<T>>();
	}

	public void addContainerBuilderListener(
			IContainerBuilderListener<T> listener) {
		this.containerListeners.add( listener );
	}

	public void removeContainerBuilderListener(
			IContainerBuilderListener<T> listener) {
		this.containerListeners.remove( listener );
	}
	
	protected void notifyContainerChanged( IJp2pContainer<T> container ){
		for( IContainerBuilderListener<T> listener: containerListeners )
			listener.notifyContainerBuilt( new ContainerBuilderEvent<T>( this, container ));

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