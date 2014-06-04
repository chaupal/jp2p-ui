/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.configurator;

import net.jp2p.jxta.network.configurator.NetworkConfigurationPropertySource;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class AbstractProtocolConfigurationComposite extends Composite {
	
	private NetworkConfigurationPropertySource source;
	
	protected Button btnEnabled;
	protected Text interfaceAddressText;
	protected Spinner portSpinner;
	protected Button btnIncomingStatus;
	protected Button btnOutgoingStatus;
	protected Button btnPublicAddress;
	protected Button btnExclusive;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AbstractProtocolConfigurationComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));
		
		btnEnabled = new Button(this, SWT.CHECK);
		btnEnabled.setText("Enabled:");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setBounds(0, 0, 55, 15);
		lblNewLabel.setText("Port:");
		
		portSpinner = new Spinner(this, SWT.BORDER);
		portSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		new Label(this, SWT.NONE);
		
		btnIncomingStatus = new Button(this, SWT.CHECK);
		btnIncomingStatus.setText("Incoming Status");
		
		btnOutgoingStatus = new Button(this, SWT.CHECK);
		btnOutgoingStatus.setText("ougoing status");
		new Label(this, SWT.NONE);
		
		btnPublicAddress = new Button(this, SWT.CHECK);
		btnPublicAddress.setText("Public Address");
		
		btnExclusive = new Button(this, SWT.CHECK);
		btnExclusive.setText("Exclusive");
		
		Label lblInterfaceAddress = new Label(this, SWT.NONE);
		lblInterfaceAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInterfaceAddress.setText("Interface Address:");
		
		interfaceAddressText = new Text(this, SWT.BORDER);
		interfaceAddressText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
	}

	protected NetworkConfigurationPropertySource getSource() {
		return source;
	}


	public void init( NetworkConfigurationPropertySource source ){
		this.source = source;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
