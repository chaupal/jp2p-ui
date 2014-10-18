/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.provider;

import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.properties.AbstractJp2pPropertySource;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.utils.StringStyler;
import net.jp2p.jxta.factory.IJxtaComponents.JxtaNetworkComponents;
import net.jp2p.jxta.factory.IJxtaComponents.JxtaComponents;
import net.jp2p.jxta.socket.SocketPropertySource.SocketTypes;
import net.jxta.peergroup.PeerGroup;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import net.jxta.socket.JxtaServerSocket;
import net.jxta.socket.JxtaSocket;

import org.chaupal.jp2p.ui.property.IJp2pPropertySourceProvider;
import org.chaupal.jp2p.ui.jxta.network.NetworkManagerPropertySource;
import org.chaupal.jp2p.ui.jxta.network.configurator.NetworkConfiguratorPropertySource;
import org.chaupal.jp2p.ui.jxta.peergroup.PeerGroupPropertySource;
import org.chaupal.jp2p.ui.jxta.socket.JxtaSocketPropertySource;
import org.chaupal.jp2p.ui.jxta.socket.JxtaSocketServerPropertySource;
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

	@Override
	public IPropertySource getPropertySource() {
		if( component.getModule() == null )
			return null;
		if(JxtaComponents.isComponent( this.getComponentName())){
			JxtaComponents jxtacomps = JxtaComponents.valueOf( StringStyler.styleToEnum( this.getComponentName() ));
			if( jxtacomps != null ){
				switch( jxtacomps ){
				case ADVERTISEMENT:
					break;
				case PEERGROUP_SERVICE:
					return new PeerGroupPropertySource( (PeerGroup) component.getModule() );
				case NET_PEERGROUP_SERVICE:
					return new PeerGroupPropertySource( (PeerGroup) component.getModule() );
				case JXSE_SOCKET_SERVICE:
					String typeStr = AbstractJp2pPropertySource.getType( component.getPropertySource());
					SocketTypes type = SocketTypes.getType(typeStr);
					switch( type ){
					case SERVER:
						return new JxtaSocketServerPropertySource((JxtaServerSocket) component.getModule());
					default:
						return new JxtaSocketPropertySource( (JxtaSocket) component.getModule() );
					}
				default:
					break;
				}
				return null;
			}
		}

		if(JxtaNetworkComponents.isComponent( this.getComponentName())){
			JxtaNetworkComponents jxtaccomps = JxtaNetworkComponents.valueOf( StringStyler.styleToEnum( this.getComponentName() ));
			if( jxtaccomps != null ){
				switch( jxtaccomps ){
				case NETWORK_MANAGER:
					return new NetworkManagerPropertySource( (NetworkManager) component.getModule() );
				case NETWORK_CONFIGURATOR:
					return new NetworkConfiguratorPropertySource( (NetworkConfigurator) component.getModule() );
				default:
					break;
				}
			}
		}
		return null;
	}

}
