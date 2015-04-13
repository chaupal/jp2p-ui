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

import net.jp2p.container.AbstractJp2pContainer.ServiceChange;
import net.jp2p.container.IJp2pContainer;
import net.jp2p.container.component.ComponentChangedEvent;
import net.jp2p.container.component.IComponentChangedListener;
import net.jp2p.container.component.IJp2pComponent;

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
	
	//Listeners can listen to adding or removing containers
	private Collection<IComponentChangedListener<IJp2pComponent<?>>> containerListeners;
	
	
	//But the system itself also tracks this
	private IComponentChangedListener<IJp2pComponent<?>> listener = new IComponentChangedListener<IJp2pComponent<?>>(){

		@Override
		public void notifyServiceChanged(
				ComponentChangedEvent<IJp2pComponent<?>> event) {
			notifyContainerChanged(event);
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
			ComponentChangedEvent<IJp2pComponent<?>> event = null;
			switch(ev.getType()) {
			case ServiceEvent.REGISTERED:
				addChild( container );
				event = new ComponentChangedEvent<IJp2pComponent<?>>( container, ServiceChange.CHILD_ADDED );
				notifyContainerChanged( event );
				container.getDispatcher().addServiceChangeListener(listener);
				break;
			case ServiceEvent.UNREGISTERING:
				removeChild( container );
				container.getDispatcher().removeServiceChangeListener(listener);
				event = new ComponentChangedEvent<IJp2pComponent<?>>( container, ServiceChange.CHILD_REMOVED );
				notifyContainerChanged( event );
				break;
			default:
				break;
			}
		}
	};
			
	//private 
	public Jp2pContainerService() {
		super();
		containerListeners = new ArrayList<IComponentChangedListener<IJp2pComponent<?>> >();
	}

	public void addContainerBuilderListener(
			IComponentChangedListener<IJp2pComponent<?>>  listener) {
		this.containerListeners.add( listener );
	}

	public void removeContainerBuilderListener(
			IComponentChangedListener<IJp2pComponent<?>>  listener) {
		this.containerListeners.remove( listener );
	}
	
	/**
	 * Adding or removing a container is translated to a service change event 
	 * @param component
	 */
	protected void notifyContainerChanged( ComponentChangedEvent<IJp2pComponent<?>> event ){
		for( IComponentChangedListener<IJp2pComponent<?>>  listener: containerListeners )
			listener.notifyServiceChanged( event );

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