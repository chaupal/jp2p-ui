/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.provider;

import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.utils.StringStyler;
import net.jp2p.jxta.factory.IJxtaComponents.JxtaNetworkComponents;
import net.jp2p.jxta.factory.IJxtaComponents.JxtaComponents;
import net.jxta.peergroup.PeerGroup;
import net.jxta.refplatform.platform.NetworkConfigurator;
import net.jxta.refplatform.platform.NetworkManager;

import org.chaupal.jp2p.ui.property.IJp2pPropertySourceProvider;
import org.chaupal.jp2p.ui.jxta.property.NetworkConfiguratorPropertySource;
import org.chaupal.jp2p.ui.jxta.property.NetworkManagerPropertySource;
import org.chaupal.jp2p.ui.jxta.property.PeerGroupPropertySource;
import org.eclipse.ui.views.properties.IPropertySource;

public class JxtaPropertySourceProvider implements
		IJp2pPropertySourceProvider<IJp2pProperties> {

	private  IJp2pComponent<?> component;
	
	public JxtaPropertySourceProvider(  IJp2pComponent<?> component ){
		this.component = component;
	}

	@Override
	public String getComponentName() {
		return component.getPropertySource().getComponentName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public IPropertySource getPropertySource() {
		if(!JxtaComponents.isComponent( this.getComponentName()))
			return null;
		JxtaComponents jxtacomps = JxtaComponents.valueOf( StringStyler.styleToEnum( this.getComponentName() ));
		if( jxtacomps != null ){
			switch( jxtacomps ){
			case ADVERTISEMENT:
				break;
			case PEERGROUP_SERVICE:
				return new PeerGroupPropertySource( (IJp2pComponent<PeerGroup> )component );
			case NET_PEERGROUP_SERVICE:
				return new PeerGroupPropertySource( (IJp2pComponent<PeerGroup>) component );
			default:
				break;
			}
			return null;
		}
		JxtaNetworkComponents jxtaccomps = JxtaNetworkComponents.valueOf( StringStyler.styleToEnum( this.getComponentName() ));
		if( jxtaccomps != null ){
			switch( jxtaccomps ){
			case NETWORK_MANAGER:
				return new NetworkManagerPropertySource( (IJp2pComponent<NetworkManager>) component );
			case NETWORK_CONFIGURATOR:
				return new NetworkConfiguratorPropertySource((IJp2pComponent<NetworkConfigurator>) component );
			default:
				break;
			}
		}
		return null;
	}

}
