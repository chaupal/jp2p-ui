/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.property;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import net.jp2p.container.utils.EnumUtils;
import net.jp2p.jxta.network.NetworkManagerPropertySource.NetworkManagerProperties;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.platform.NetworkManager;
import net.jxta.platform.NetworkManager.ConfigMode;

import org.chaupal.jp2p.ui.jxta.property.descriptors.CheckBoxPropertyDescriptor;
import org.chaupal.jp2p.ui.jxta.property.descriptors.TextBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class NetworkManagerPropertySource extends AbstractJp2pUIPropertySource<NetworkManager> {

	public NetworkManagerPropertySource( NetworkManager component ) {
		super( component );
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		for( NetworkManagerProperties property: NetworkManagerProperties.values() ){
			String[] parsed = super.parseProperty(property);
			PropertyDescriptor descriptor;
			switch( property ){
			case CONFIG_PERSISTENT:
				descriptor = new CheckBoxPropertyDescriptor( property, parsed[1] );
				break;
			case CONFIG_MODE:
				descriptor = new ComboBoxPropertyDescriptor( property, parsed[1], EnumUtils.toString( ConfigMode.values() ));
				break;
			case INSTANCE_NAME:
				descriptor = new TextBoxPropertyDescriptor( property, parsed[1] );
				TextBoxPropertyDescriptor tpd = ( TextBoxPropertyDescriptor )descriptor;
				tpd.setEnabled(false );
				break;
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
	public Object getPropertyValue(Object id) {
		NetworkManager manager = super.getModule();
		if(!( id instanceof NetworkManagerProperties ))
			return super.getPropertyValue(id);
		NetworkManagerProperties property  = ( NetworkManagerProperties )id;
		switch( property ){
		case CONFIG_PERSISTENT:
			return manager.isConfigPersistent();
		case INFRASTRUCTURE_ID:
			return manager.getInfrastructureID();
		case INSTANCE_HOME:
			return manager.getInstanceHome();
		case INSTANCE_NAME:
			return manager.getInstanceName();
		case CONFIG_MODE:
			return manager.getMode().ordinal();
		case PEER_ID:
			return manager.getPeerID();
		}
		return super.getPropertyValue(id);
	}

	/**
	 * Returns true if the given property can be modified
	 * @param id
	 * @return
	 */
	@Override
	public boolean isEditable( Object id ){
		return ( id instanceof NetworkManagerProperties );
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		NetworkManager manager = super.getModule();
		NetworkManagerProperties property  = ( NetworkManagerProperties )id;
		switch( property ){
		case CONFIG_PERSISTENT:
			if( manager.isConfigPersistent() != (boolean)value )
			  manager.setConfigPersistent((boolean) value );
			return;
		case INFRASTRUCTURE_ID:
			if(!( manager.getInfrastructureID().equals( value )))
			  manager.setInfrastructureID( (PeerGroupID) value );
			return;
		case INSTANCE_HOME:
			if(!( manager.getInstanceHome().equals( value )))
			  manager.setInstanceHome( (URI) value );
			return;
		case INSTANCE_NAME:
			if(!( value instanceof String ))
				return;
			if(!( manager.getInstanceName().equals( value )))
			  manager.setInstanceName( (String) value );
			return;
		case CONFIG_MODE:
			if(!( manager.getMode().equals( value )))
				try {
					manager.setMode( ConfigMode.values()[(int)value ]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			return;
		case PEER_ID:
			if(!( manager.getPeerID().equals( value )))
			  manager.setPeerID( (PeerID) value );
			return;
		}
		super.setPropertyValue(id, value);
	}
}