/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.endpoint;

import net.jp2p.chaupal.jxta.platform.configurator.NetworkConfigurationPropertySource;
import net.jp2p.chaupal.jxta.platform.configurator.NetworkConfigurationPropertySource.NetworkConfiguratorProperties;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Spinner;

public class RdvRelayConfigurationComposite extends Composite {
	
	private Button btnRelayOnly;
	private Button btnRdvOnly;
	private Table table;
	private Table table_1;
	private Spinner relayMaxAmountSpinner;
	private Spinner rdvMaxAmountSpinner;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public RdvRelayConfigurationComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group grpRelay_1 = new Group(this, SWT.NONE);
		grpRelay_1.setText("Relay");
		grpRelay_1.setLayout(new GridLayout(2, false));
		
		btnRelayOnly = new Button(grpRelay_1, SWT.CHECK);
		btnRelayOnly.setText("Relay Only");
		new Label(grpRelay_1, SWT.NONE);
		
		Label lblMaxAmount = new Label(grpRelay_1, SWT.NONE);
		lblMaxAmount.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMaxAmount.setText("Max amount:");
		
		relayMaxAmountSpinner = new Spinner(grpRelay_1, SWT.BORDER);
		relayMaxAmountSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		table = new Table(grpRelay_1, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		Group grpRdv = new Group(this, SWT.NONE);
		grpRdv.setText("Rendezvous");
		grpRdv.setLayout(new GridLayout(2, false));
		
		btnRdvOnly = new Button(grpRdv, SWT.CHECK);
		btnRdvOnly.setText("Rdv Only");
		new Label(grpRdv, SWT.NONE);
		
		Label lblMaxAmount_1 = new Label(grpRdv, SWT.NONE);
		lblMaxAmount_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMaxAmount_1.setText("Max amount:");
		
		rdvMaxAmountSpinner = new Spinner(grpRdv, SWT.BORDER);
		rdvMaxAmountSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		table_1 = new Table(grpRdv, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
	}

	public void init( NetworkConfigurationPropertySource source ){
		this.btnRelayOnly.setSelection((boolean) source.getDefault( NetworkConfiguratorProperties.USE_ONLY_RELAY_SEEDS ));
		this.btnRdvOnly.setSelection((boolean) source.getDefault( NetworkConfiguratorProperties.USE_ONLY_RENDEZVOUS_SEEDS ));
		this.rdvMaxAmountSpinner.setSelection((int) source.getDefault( NetworkConfiguratorProperties.RENDEZVOUS_8MAX_CLIENTS ));
		this.relayMaxAmountSpinner.setSelection(( int) source.getDefault( NetworkConfiguratorProperties.RELAY_8MAX_CLIENTS ));
	}
}
