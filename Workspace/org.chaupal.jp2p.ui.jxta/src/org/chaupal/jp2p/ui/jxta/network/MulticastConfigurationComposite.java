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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class MulticastConfigurationComposite extends Composite {
	
	private NetworkConfigurationPropertySource source;
	
	protected Button btnEnabled;
	protected Text text;
	protected Spinner spinner;
	protected Button btnPublicAddress;
	protected Button btnExclusive;
	private Spinner poolSizeSpinner;
	private Spinner sizeSpinner;
	private Label lblPoolSize;
	private Label lblSize;
	private Label lblAddress;
	private Text addressText;
	private Button btnActivated;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public MulticastConfigurationComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		btnEnabled = new Button(this, SWT.CHECK);
		btnEnabled.setText("Enable:");
		
		btnActivated = new Button(this, SWT.RADIO);
		btnActivated.setText("Activated");
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setBounds(0, 0, 55, 15);
		lblNewLabel.setText("Port:");
		
		spinner = new Spinner(this, SWT.BORDER);
		spinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		lblPoolSize = new Label(this, SWT.NONE);
		lblPoolSize.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPoolSize.setText("Pool Size:");
		
		poolSizeSpinner = new Spinner(this, SWT.BORDER);
		poolSizeSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		lblSize = new Label(this, SWT.NONE);
		lblSize.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSize.setText("Size:");
		
		sizeSpinner = new Spinner(this, SWT.BORDER);
		sizeSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		btnPublicAddress = new Button(this, SWT.CHECK);
		btnPublicAddress.setText("Public Address:");
		
		btnExclusive = new Button(this, SWT.CHECK);
		btnExclusive.setText("Exclusive");
		
		lblAddress = new Label(this, SWT.NONE);
		lblAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAddress.setText("Address:");
		
		addressText = new Text(this, SWT.BORDER);
		addressText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblInterfaceAddress = new Label(this, SWT.NONE);
		lblInterfaceAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInterfaceAddress.setText("Interface Address:");
		
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	protected NetworkConfigurationPropertySource getSource() {
		return source;
	}


	public void init( NetworkConfigurationPropertySource source ){
		this.source = source;
		this.btnEnabled.setSelection((boolean) source.getDefault( NetworkConfiguratorProperties.MULTICAST_8ENABLED ));
		this.btnActivated.setSelection((boolean) source.getDefault( NetworkConfiguratorProperties.MULTICAST_8STATUS));
		this.addressText.setText((String) source.getDefault(NetworkConfiguratorProperties.MULTICAST_8ADDRESS));
		this.spinner.setSelection((int) source.getDefault(NetworkConfiguratorProperties.MULTICAST_8PORT));
		this.poolSizeSpinner.setSelection((int) source.getDefault(NetworkConfiguratorProperties.MULTICAST_8POOL_SIZE));
		this.sizeSpinner.setSelection((int) source.getDefault(NetworkConfiguratorProperties.MULTICAST_8SIZE));
		this.text.setText((String) source.getDefault( NetworkConfiguratorProperties.MULTICAST_8INTERFACE));
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
