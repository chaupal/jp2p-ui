/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network;

import net.jp2p.chaupal.jxta.platform.NetworkManagerPropertySource.NetworkManagerProperties;
import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.utils.EnumUtils;
import net.jxta.platform.NetworkManager;
import net.jxta.platform.NetworkManager.ConfigMode;

import org.chaupal.jp2p.ui.property.AbstractUIJp2pPropertySource;
import org.chaupal.jp2p.ui.property.descriptors.CheckBoxPropertyDescriptor;
import org.chaupal.jp2p.ui.property.descriptors.TextBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class NetworkManagerComponentUIPropertySource extends AbstractUIJp2pPropertySource<NetworkManager> {

	public NetworkManagerComponentUIPropertySource( IJp2pComponent<NetworkManager> component ) {
		super( component );
	}

	@Override
	public IPropertyDescriptor onGetPropertyDescriptor( IJp2pProperties property ) {
		return getPropertyDescriptor( property );
	}

	/**
	 * List the property descriptors which are uniquw for the network manager
	 * @return
	 */
	public static IPropertyDescriptor getPropertyDescriptor( IJp2pProperties prop ) {
		if( !( prop instanceof NetworkManagerProperties ))
			return null;
		NetworkManagerProperties property = (NetworkManagerProperties) prop;
		String[] parsed = parseProperty( S_JP2P_PROPERTY_TEXT, property);
		PropertyDescriptor descriptor;
		TextBoxPropertyDescriptor tpd = null;
		switch( property ){
		case CONFIG_PERSISTENT:
			descriptor = new CheckBoxPropertyDescriptor( property, parsed[1] );
			break;
		case CONFIG_MODE:
			descriptor = new ComboBoxPropertyDescriptor( property, parsed[1], EnumUtils.toString( ConfigMode.values() ));
			break;
		case INSTANCE_NAME:
			descriptor = new TextBoxPropertyDescriptor( property, parsed[1] );
			tpd = ( TextBoxPropertyDescriptor )descriptor;
			tpd.setEnabled(false );
			break;
		default:
			descriptor = new TextBoxPropertyDescriptor( property, parsed[1]);
			tpd = ( TextBoxPropertyDescriptor )descriptor;
			tpd.setEnabled(false );
			break;
		}	
		descriptor.setCategory(parsed[2]);
		return descriptor;
	}	

}