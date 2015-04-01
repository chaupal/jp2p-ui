/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.endpoint;

import net.jp2p.chaupal.jxta.platform.tcp.TcpPropertySource;
import net.jp2p.jxta.transport.TransportPropertySource.TransportProperties;

import org.chaupal.jp2p.ui.jxta.network.configurator.AbstractProtocolConfigurationComposite;
import org.eclipse.swt.widgets.Composite;

public class TcpConfigurationComposite extends AbstractProtocolConfigurationComposite<TcpPropertySource> {
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TcpConfigurationComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void init( TcpPropertySource source ){
		super.init( source );
		Object value = source.isEnabled();
		//if( value != null)
		//	new BooleanDataBinding( TransportProperties.ENABLED, source, this.btnEnabled);
		value = source.getDefault( TransportProperties.INCOMING_STATUS  );
		if( value != null)
			this.btnIncomingStatus.setSelection((boolean) value);
		value = source.getDefault( TransportProperties.OUTGOING_STATUS  );
		if( value != null)
			this.btnOutgoingStatus.setSelection((boolean)value );
		value = source.getDefault( TransportProperties.PUBLIC_ADDRESS  );
		if( value != null)
			this.btnPublicAddress.setSelection((boolean) value );
		value = source.getDefault( TransportProperties.PUBLIC_ADDRESS_EXCLUSIVE  );
		if( value != null)
			this.btnExclusive.setSelection((boolean) value );
		value = source.getDefault( TransportProperties.INTERFACE_ADDRESS  );
		if( value != null)
			this.interfaceAddressText.setText((String)value );		
		//new SpinnerDataBinding<NetworkConfiguratorProperties>( TransportProperties.PORT ,source, 
		//		this.portSpinner, TransportProperties.START_PORT, TransportProperties.END_PORT );
	}
}
