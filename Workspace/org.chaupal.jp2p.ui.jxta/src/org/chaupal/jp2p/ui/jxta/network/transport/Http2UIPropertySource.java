/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.transport;

import net.jp2p.jxta.transport.TransportPropertySource.TransportProperties;
import net.jxta.platform.NetworkConfigurator;

public class Http2UIPropertySource extends AbstractTransportUIPropertySource {

	public static final String S_CATEGORY = "Http2";
	
	public Http2UIPropertySource(NetworkConfigurator configurator) {
		super( configurator, S_CATEGORY );
	}

	@Override
	public Object onGetPropertyValue( TransportProperties property) {
		NetworkConfigurator configurator = super.getModule();
		switch( property ){
		case PUBLIC_ADDRESS:
			return configurator.getHttp2PublicAddress();
		case PUBLIC_ADDRESS_EXCLUSIVE:
			return configurator.isHttp2PublicAddressExclusive();
		case INCOMING_STATUS:
			return configurator.getHttp2IncomingStatus();
		case INTERFACE_ADDRESS:
			return configurator.getHttp2InterfaceAddress();
		case OUTGOING_STATUS:
			return configurator.getHttp2OutgoingStatus();
		case PORT:
			return configurator.getHttp2Port();
		case START_PORT:
			return configurator.getHttp2StartPort();
		case END_PORT:
			return configurator.getHttp2EndPort();
		default:
			break;
		}
		return null;
	}

	@Override
	public boolean onSetPropertyValue( TransportProperties property, Object value) {
		NetworkConfigurator configurator = super.getModule();
		Object[] combined;
		switch( property ){
		case PUBLIC_ADDRESS:
			combined = ( Object[] )value;
			configurator.setHttp2PublicAddress(( String )combined[0], ( boolean)combined[1] );
			return true;
		case PUBLIC_ADDRESS_EXCLUSIVE:
			//configurator.setHttp2PublicAddressExclusive(( String )combined[0], ( boolean )combined[1]);
			return true;
		case INCOMING_STATUS:
			configurator.setHttp2Incoming( (boolean) value);
			return true;
		case INTERFACE_ADDRESS:
			configurator.setHttp2InterfaceAddress( (String) value);
			return true;
		case OUTGOING_STATUS:
			configurator.setHttp2Outgoing( (boolean) value);
			return true;
		case PORT:
			configurator.setHttp2Port((int) value);
			return true;
		case START_PORT:
			configurator.setHttp2StartPort( (int) value);
			return true;
		case END_PORT:
			configurator.setHttp2EndPort( (int) value);
			return true;
		default:
			break;
		}
		return false;
	}
}