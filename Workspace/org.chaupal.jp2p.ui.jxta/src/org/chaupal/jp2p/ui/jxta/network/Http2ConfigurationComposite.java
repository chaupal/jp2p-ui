/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network;

import net.jp2p.jxta.root.network.configurator.NetworkConfigurationPropertySource;
import net.jp2p.jxta.root.network.configurator.NetworkConfigurationPropertySource.NetworkConfiguratorProperties;

import org.eclipse.swt.widgets.Composite;

public class Http2ConfigurationComposite extends AbstractProtocolConfigurationComposite {
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Http2ConfigurationComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void init( NetworkConfigurationPropertySource source ){
		super.init( source );
		Object value = source.getDefault( NetworkConfiguratorProperties.HTTP2_8ENABLED  );
		if( value != null)
			this.btnEnabled.setSelection((boolean)value );
		value = source.getDefault( NetworkConfiguratorProperties.HTTP2_8INCOMING_STATUS  );
		if( value != null)
			this.btnIncomingStatus.setSelection((boolean) value);
		value = source.getDefault( NetworkConfiguratorProperties.HTTP2_8OUTGOING_STATUS  );
		if( value != null)
			this.btnOutgoingStatus.setSelection((boolean)value );
		value = source.getDefault( NetworkConfiguratorProperties.HTTP2_8PUBLIC_ADDRESS  );
		if( value != null)
			this.btnPublicAddress.setSelection((boolean) value );
		value = source.getDefault( NetworkConfiguratorProperties.HTTP2_8PUBLIC_ADDRESS_EXCLUSIVE  );
		if( value != null)
			this.btnExclusive.setSelection((boolean) value );
		value = source.getDefault( NetworkConfiguratorProperties.HTTP2_8INTERFACE_ADDRESS  );
		if( value != null)
			this.interfaceAddressText.setText((String)value );
		value = source.getDefault( NetworkConfiguratorProperties.HTTP2_8PORT  );
		if( value != null)
			this.portSpinner.setSelection(( int )value );
		value = source.getDefault( NetworkConfiguratorProperties.HTTP2_8START_PORT  );
		if( value != null)
			this.portSpinner.setMinimum(( int )value );
		value = source.getDefault( NetworkConfiguratorProperties.HTTP2_8END_PORT  );
		if( value != null)
			this.portSpinner.setMaximum(( int )value );
	}
}
