/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.configurator;

import net.jp2p.jxta.network.configurator.NetworkConfigurationPropertySource;
import net.jp2p.jxta.network.configurator.NetworkConfigurationPropertySource.NetworkConfiguratorProperties;

import org.chaupal.jp2p.ui.property.databinding.BooleanDataBinding;
import org.chaupal.jp2p.ui.property.databinding.SpinnerDataBinding;
import org.eclipse.swt.widgets.Composite;

public class TcpConfigurationComposite extends AbstractProtocolConfigurationComposite {
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TcpConfigurationComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init( NetworkConfigurationPropertySource source ){
		super.init( source );
		Object value = source.getDefault( NetworkConfiguratorProperties.TCP_8ENABLED  );
		if( value != null)
			new BooleanDataBinding( NetworkConfiguratorProperties.TCP_8ENABLED, source, this.btnEnabled);
		value = source.getDefault( NetworkConfiguratorProperties.TCP_8INCOMING_STATUS  );
		if( value != null)
			this.btnIncomingStatus.setSelection((boolean) value);
		value = source.getDefault( NetworkConfiguratorProperties.TCP_8OUTGOING_STATUS  );
		if( value != null)
			this.btnOutgoingStatus.setSelection((boolean)value );
		value = source.getDefault( NetworkConfiguratorProperties.TCP_8PUBLIC_ADDRESS  );
		if( value != null)
			this.btnPublicAddress.setSelection((boolean) value );
		value = source.getDefault( NetworkConfiguratorProperties.TCP_8PUBLIC_ADDRESS_EXCLUSIVE  );
		if( value != null)
			this.btnExclusive.setSelection((boolean) value );
		value = source.getDefault( NetworkConfiguratorProperties.TCP_8INTERFACE_ADDRESS  );
		if( value != null)
			this.interfaceAddressText.setText((String)value );		
		new SpinnerDataBinding<NetworkConfiguratorProperties>( NetworkConfiguratorProperties.TCP_8PORT ,source, 
				this.portSpinner, NetworkConfiguratorProperties.TCP_8START_PORT, NetworkConfiguratorProperties.TCP_8END_PORT );
	}
}
