/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.configurator;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.chaupal.jp2p.ui.jxta.network.seeds.RdvUIPropertySource;
import org.chaupal.jp2p.ui.jxta.network.seeds.RelayUIPropertySource;
import org.chaupal.jp2p.ui.jxta.network.transport.Http2UIPropertySource;
import org.chaupal.jp2p.ui.jxta.network.transport.HttpUIPropertySource;
import org.chaupal.jp2p.ui.jxta.network.transport.TcpUIPropertySource;
import org.chaupal.jp2p.ui.property.AbstractUIPropertySource;
import org.chaupal.jp2p.ui.property.IJp2pUIPropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import net.jp2p.chaupal.jxta.platform.configurator.NetworkConfigurationPropertySource.NetworkConfiguratorProperties;
import net.jp2p.container.properties.IJp2pProperties;
import net.jxta.peer.PeerID;
import net.jxta.platform.NetworkConfigurator;

public class NetworkConfiguratorPropertySource extends AbstractUIPropertySource<NetworkConfigurator> {

	private Collection<AbstractUIPropertySource<?>> sources;
	
	public NetworkConfiguratorPropertySource(NetworkConfigurator configurator) {
		super( configurator );
		sources = new ArrayList<AbstractUIPropertySource<?>>();
		sources.add( new HttpUIPropertySource( configurator ));
		sources.add( new Http2UIPropertySource( configurator ));
		sources.add( new TcpUIPropertySource( configurator ));
		sources.add( new SecurityUIPropertySource( configurator ));
		sources.add( new InfrastructureUIPropertySource( configurator ));
		sources.add( new RdvUIPropertySource( configurator ));
		sources.add( new RelayUIPropertySource( configurator ));
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		for( IPropertySource source: this.sources ){
			Collections.addAll( descriptors, source.getPropertyDescriptors());
		}
		PropertyDescriptor descriptor = null;
		for( NetworkConfiguratorProperties property: NetworkConfiguratorProperties.values() ){
			String[] parsed = super.parseProperty( S_JP2P_PROPERTY_TEXT, property);
			switch( property ){
			default:
				descriptor = new TextPropertyDescriptor( property, parsed[1]);
				break;
			}	
			descriptor.setCategory(parsed[2]);
			descriptors.add(descriptor);
		}
		if( super.getPropertyDescriptors() != null )
			descriptors.addAll( Arrays.asList( super.getPropertyDescriptors()));
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size()]);
	}

	@Override
	public Object onGetPropertyValue( IJp2pProperties id) {
		Object retval = null;
		for( AbstractUIPropertySource<?> source: this.sources ){
			retval = source.onGetPropertyValue(id);
			if( retval != null )
				return retval;
		}
		if( !NetworkConfiguratorProperties.isValidProperty( (IJp2pProperties) id ))
			return null;
		NetworkConfiguratorProperties property = ( NetworkConfiguratorProperties )id;
		NetworkConfigurator configurator = super.getModule();
		switch( property ){
		case DESCRIPTION:
			return S_NO_READ_VALUE;
		case HOME:
			return configurator.getHome();
		case CONFIG_MODE:
			return configurator.getMode();
		case NAME:
			return configurator.getName();
		case PEER_ID:
			return configurator.getPeerID();
		case STORE_HOME:
			return configurator.getStoreHome();
		default:
			break;
		}
		return null;
	}

	/**
	 * Returns true if the given property can be modified
	 * @param id
	 * @return
	 */
	@Override
	public boolean isEditable( Object id ){
		for( IJp2pUIPropertySource<?> source: this.sources ){
			source.isEditable(id);
		}
		if(!( id instanceof NetworkConfiguratorProperties ))
			return false;
		return this.isEditable( ( NetworkConfiguratorProperties )id);
	}

	/**
	 * Returns true if the given property can be modified
	 * @param id
	 * @return
	 */
	public boolean isEditable( NetworkConfiguratorProperties property ){
		switch( property ){
		//case HTTP_8PUBLIC_ADDRESS_EXCLUSIVE:
		//case HTTP_8TO_PUBLIC_ADDRESS_EXCLUSIVE:
		//case TCP_8PUBLIC_ADDRESS_EXCLUSIVE:
		//	return false;
		default:
			return true;
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		for( IJp2pUIPropertySource<?> source: this.sources ){
			source.setPropertyValue(id, value);
		}
		if(!( this.isEditable(id)))
			return;
		NetworkConfigurator configurator = super.getModule();
		NetworkConfiguratorProperties property = ( NetworkConfiguratorProperties )id;
		switch( property ){
		case DESCRIPTION:
			configurator.setDescription((String) value);
			return;
		case HOME:
			configurator.setHome( (File) value);
			return;
		case CONFIG_MODE:
			configurator.setMode( (int) value);
			return;
		case NAME:
			configurator.setName( (String) value);
			return;
		case PEER_ID:
			configurator.setPeerID( (PeerID) value);
			return;
		case STORE_HOME:
			configurator.setStoreHome( (URI) value);
			return;
		default:
			break;
		}
		super.setPropertyValue(id, value);
	}
}