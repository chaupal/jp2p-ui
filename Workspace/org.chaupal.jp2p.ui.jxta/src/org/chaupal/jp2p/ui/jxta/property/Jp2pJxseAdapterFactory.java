/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.property;

import net.jp2p.container.IJp2pContainer;
import net.jxta.document.Advertisement;
import net.jxta.peergroup.PeerGroup;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import net.jxta.rendezvous.RendezVousService;

import org.chaupal.jp2p.ui.adapter.Jp2pAdapterFactory;
import org.chaupal.jp2p.ui.jxta.advertisement.property.AdvertisementPropertySource;
import org.chaupal.jp2p.ui.jxta.network.NetworkManagerPropertySource;
import org.chaupal.jp2p.ui.jxta.network.configurator.NetworkConfiguratorPropertySource;
import org.chaupal.jp2p.ui.jxta.peergroup.PeerGroupUIPropertySource;
import org.chaupal.jp2p.ui.jxta.rendezvous.RendezVousUIPropertySource;
import org.chaupal.jp2p.ui.property.SimpleUIPropertySource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;

public class Jp2pJxseAdapterFactory extends Jp2pAdapterFactory implements IAdapterFactory {

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		  if(adapterType != IPropertySource.class )
			  return null;
		  if( adaptableObject instanceof IJp2pContainer )
			  return this.getContainerPropertySource(( IJp2pContainer )adaptableObject );
		  IPropertySource source = this.getPropertySource(adaptableObject);
		  if( source != null )
			  return source;
		  return super.getAdapter(adaptableObject, adapterType);
	}

	/**
	 * Get the correct property source
	 * @param adaptableObject
	 * @return
	 */
	protected IPropertySource getPropertySource( Object adaptableObject ){
		  if( adaptableObject instanceof NetworkManager )
			  return new NetworkManagerPropertySource(( NetworkManager )adaptableObject );
		  if( adaptableObject instanceof NetworkConfigurator )
			  return new NetworkConfiguratorPropertySource(( NetworkConfigurator )adaptableObject);
		  if( adaptableObject instanceof Advertisement )
			  return new AdvertisementPropertySource(( Advertisement )adaptableObject);
		  if( adaptableObject instanceof PeerGroup )
			  return new PeerGroupUIPropertySource(( PeerGroup )adaptableObject);
		  if( adaptableObject instanceof RendezVousService )
			  return new RendezVousUIPropertySource( (RendezVousService) adaptableObject);
		  if( adaptableObject instanceof RendezVousService )
			  return new RendezVousUIPropertySource( (RendezVousService) adaptableObject);
		  return new SimpleUIPropertySource( adaptableObject );			
	}

	/**
	 * Get the correct property source
	 * @param adaptableObject
	 * @return
	 */
	protected IPropertySource getContainerPropertySource( IJp2pContainer<?> component ){
		Object module = component.getModule();  
		return this.getPropertySource( module );			
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class[] getAdapterList() {
		return new Class[]{IPropertySource.class };
	}

}
