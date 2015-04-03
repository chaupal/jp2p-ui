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

public class HttpUIPropertySource extends AbstractTransportUIPropertySource{

	public static final String S_CATEGORY = "Http";
	public static final String S_NO_READ_VALUE = "<Not a readable property>";
	
	public HttpUIPropertySource(NetworkConfigurator configurator) {
		super( configurator, S_CATEGORY );
	}

	
	@Override
	protected boolean isValidForTransportType(TransportProperties property) {
		switch( property ){
		case START_PORT:
		case END_PORT:
			return false;
		default:
			break;
		}
		return super.isValidForTransportType(property);
	}


	@Override
	public Object onGetPropertyValue( TransportProperties property) {
		NetworkConfigurator configurator = super.getModule();
		switch( property ){
		case PUBLIC_ADDRESS:
			return configurator.getHttpPublicAddress();
		case PUBLIC_ADDRESS_EXCLUSIVE:
			return configurator.isHttpPublicAddressExclusive();
		case INCOMING_STATUS:
			return configurator.getHttpIncomingStatus();
		case INTERFACE_ADDRESS:
			return configurator.getHttpInterfaceAddress();
		case OUTGOING_STATUS:
			return configurator.getHttpOutgoingStatus();
		case PORT:
			return configurator.getHttpPort();
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
			configurator.setHttpPublicAddress(( String )combined[0], ( boolean)combined[1] );
			return true;
		case PUBLIC_ADDRESS_EXCLUSIVE:
			//configurator.setHttpPublicAddressExclusive(( String )combined[0], ( boolean )combined[1]);
			return true;
		case INCOMING_STATUS:
			configurator.setHttpIncoming( (boolean) value);
			return true;
		case INTERFACE_ADDRESS:
			configurator.setHttpInterfaceAddress( (String) value);
			return true;
		case OUTGOING_STATUS:
			configurator.setHttpOutgoing( (boolean) value);
			return true;
		case PORT:
			configurator.setHttpPort( (int) value);
			return true;
		default:
			break;
		}
		return false;
	}
}