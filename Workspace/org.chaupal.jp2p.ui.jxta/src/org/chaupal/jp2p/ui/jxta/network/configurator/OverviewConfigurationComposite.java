/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.configurator;

import java.net.URI;

import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.validator.StringValidator;
import net.jp2p.jxta.network.configurator.NetworkConfigurationPropertySource;
import net.jp2p.jxta.network.configurator.NetworkConfigurationPropertySource.NetworkConfiguratorProperties;
import net.jxta.peer.PeerID;
import net.jxta.platform.NetworkManager.ConfigMode;

import org.chaupal.jp2p.ui.property.databinding.ComboDataBinding;
import org.chaupal.jp2p.ui.property.databinding.IJp2pDatabinding;
import org.chaupal.jp2p.ui.property.databinding.StringDataBinding;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class OverviewConfigurationComposite extends Composite {
	
	private NetworkConfigurationPropertySource source;
	protected Text text;
	private Label lblPoolSize;
	private Label lblAddress;
	private Text peerIdText;
	private Text nameText;
	private Text storeHomeText;
	private Text storeText;
	private Label lblDescription;
	private Text descriptionText;
	private Combo combo;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public OverviewConfigurationComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setBounds(0, 0, 55, 15);
		lblNewLabel.setText("Name:");
		
		nameText = new Text(this, SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblDescription = new Label(this, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDescription.setText("Description:");
		
		descriptionText = new Text(this, SWT.BORDER);
		descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblPoolSize = new Label(this, SWT.NONE);
		lblPoolSize.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPoolSize.setText("Store Home:");
		
		storeHomeText = new Text(this, SWT.BORDER);
		storeHomeText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Text text = ( Text )e.widget;
				source.setProperty( NetworkConfiguratorProperties.STORE_HOME, URI.create( text.getText() ));
			}
		});
		storeHomeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		storeText = new Text(this, SWT.BORDER);
		storeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblInterfaceAddress = new Label(this, SWT.NONE);
		lblInterfaceAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInterfaceAddress.setText("Mode:");
		
		combo = new Combo(this, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblAddress = new Label(this, SWT.NONE);
		lblAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAddress.setText("Peer ID:");
		
		peerIdText = new Text(this, SWT.BORDER);
		peerIdText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Text text = ( Text )e.widget;
				source.setProperty( NetworkConfiguratorProperties.PEER_ID, text.getText() );
			}
		});
		peerIdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	protected NetworkConfigurationPropertySource getSource() {
		return source;
	}

	/**
	 * Initialise the view
	 * @param source
	 */
	public void init( NetworkConfigurationPropertySource source ){
		this.source = source;
		Object value = source.getProperty( NetworkConfiguratorProperties.HOME );
		if( value != null)
			this.storeHomeText.setText((( URI) value ).getPath() );
		value = source.getDefault( NetworkConfiguratorProperties.STORE_HOME );
		if( value != null)
			this.storeText.setText((( URI )value ).getPath() );
		
		IJp2pDatabinding<NetworkConfiguratorProperties, String> db = new StringDataBinding<NetworkConfiguratorProperties>( NetworkConfiguratorProperties.NAME, source, nameText);
		db.setValidator( new StringValidator<NetworkConfiguratorProperties>( NetworkConfiguratorProperties.NAME, StringValidator.S_NAME_REGEX ));

		value = source.getDefault( NetworkConfiguratorProperties.DESCRIPTION );
		if( value != null)
			this.descriptionText.setText(( String )value );

		this.combo.setItems( NetworkConfigurationPropertySource.getConfigModes());
		value = source.getDefault( NetworkConfiguratorProperties.CONFIG_MODE );
		ConfigMode mode = ( value == null )? ConfigMode.EDGE: (ConfigMode ) value;
		this.combo.select( mode.ordinal() );
		new ComboDataBinding<IJp2pProperties, ConfigMode>( NetworkConfiguratorProperties.CONFIG_MODE, source, combo );		

		value = source.getDefault( NetworkConfiguratorProperties.PEER_ID );
		if( value != null)
			this.peerIdText.setText(((PeerID )value).toString());
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
