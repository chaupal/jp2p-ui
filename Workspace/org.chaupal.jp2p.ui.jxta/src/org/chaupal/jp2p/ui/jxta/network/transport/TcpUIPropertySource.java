/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.transport;

import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.jxta.transport.TransportPropertySource.TransportProperties;
import net.jxta.platform.NetworkConfigurator;

public class TcpUIPropertySource extends AbstractTransportUIPropertySource {

	public static final String S_CATEGORY = "Tcp";
	public TcpUIPropertySource(NetworkConfigurator configurator) {
		super( configurator, S_CATEGORY );
	}

	@Override
	protected boolean onIsEnabledValue(IJp2pProperties id) {
		NetworkConfigurator configurator = super.getModule();
		return configurator.isTcpEnabled();
	}

	@Override
	protected boolean onSetEnabledValue(Object value) {
		NetworkConfigurator configurator = super.getModule();
		configurator.setTcpEnabled((boolean) value);
		return ( boolean ) value;
	}

	@Override
	public Object onGetPropertyValue( TransportProperties property) {
		NetworkConfigurator configurator = super.getModule();
		switch( property ){
		case PUBLIC_ADDRESS:
			return configurator.getTcpPublicAddress();
		case PUBLIC_ADDRESS_EXCLUSIVE:
			return configurator.isTcpPublicAddressExclusive();
		case INCOMING_STATUS:
			return configurator.getTcpIncomingStatus();
		case INTERFACE_ADDRESS:
			return configurator.getTcpInterfaceAddress();
		case OUTGOING_STATUS:
			return configurator.getTcpOutgoingStatus();
		case PORT:
			return configurator.getTcpPort();
		case START_PORT:
			return configurator.getTcpStartPort();
		case END_PORT:
			return configurator.getTcpEndport();
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
			configurator.setTcpPublicAddress(( String )combined[0], ( boolean)combined[1] );
			return true;
		case PUBLIC_ADDRESS_EXCLUSIVE:
			//configurator.setHttpPublicAddressExclusive(( String )combined[0], ( boolean )combined[1]);
			return true;
		case INCOMING_STATUS:
			configurator.setTcpIncoming( (boolean) value);
			return true;
		case INTERFACE_ADDRESS:
			configurator.setTcpInterfaceAddress( (String) value);
			return true;
		case OUTGOING_STATUS:
			configurator.setTcpOutgoing( (boolean) value);
			return true;
		case PORT:
			configurator.setTcpPort( (int) value);
			return true;
		case START_PORT:
			configurator.setTcpStartPort( (int) value);
			return true;
		case END_PORT:
			configurator.setTcpEndPort( (int) value);
			return true;
		default:
			break;
		}
		return false;
	}
}