/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.configurator;

import net.jp2p.container.Jp2pContainerPropertySource;
import net.jp2p.jxta.network.NetworkManagerPropertySource;
import net.jp2p.jxta.network.configurator.NetworkConfigurationPropertySource;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.layout.FillLayout;

public class Jp2pNetworkConfiguratorComposite extends Composite {

	private OverviewConfigurationComposite overviewComposite;
	private TcpConfigurationComposite tcpComposite;
	private HttpConfigurationComposite httpComposite;
	private Http2ConfigurationComposite http2Composite;
	private MulticastConfigurationComposite multicastComposite;
	private RdvRelayConfigurationComposite rdvRelayComposite;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Jp2pNetworkConfiguratorComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		
		overviewComposite = new OverviewConfigurationComposite(sashForm, SWT.NONE);
		
		TabFolder tabFolder = new TabFolder(sashForm, SWT.NONE);
		
		TabItem tbtmTcpItem = new TabItem(tabFolder, SWT.NONE);
		tbtmTcpItem.setText("Tcp");
		tcpComposite = new TcpConfigurationComposite( tabFolder, SWT.NONE);
		tbtmTcpItem.setControl(tcpComposite);
		sashForm.setWeights(new int[] {1, 1});
		
		TabItem tbtmHttpItem = new TabItem(tabFolder, SWT.NONE);
		tbtmHttpItem.setText("Http");
		httpComposite = new HttpConfigurationComposite( tabFolder, SWT.NONE);
		tbtmHttpItem.setControl(httpComposite);
		sashForm.setWeights(new int[] {1, 1});

		TabItem tbtmHttp2Item = new TabItem(tabFolder, SWT.NONE);
		tbtmHttp2Item.setText("Http2");
		http2Composite = new Http2ConfigurationComposite( tabFolder, SWT.NONE);
		tbtmHttp2Item.setControl(httpComposite);
		sashForm.setWeights(new int[] {1, 1});

		TabItem tbtmMulticastItem = new TabItem(tabFolder, SWT.NONE);
		tbtmMulticastItem.setText("Multicast");
		multicastComposite = new MulticastConfigurationComposite( tabFolder, SWT.NONE);
		tbtmMulticastItem.setControl(multicastComposite);
		sashForm.setWeights(new int[] {1, 1});

		TabItem tbtmRdvRelayItem = new TabItem(tabFolder, SWT.NONE);
		tbtmRdvRelayItem.setText("Relay/Rendezvous");
		rdvRelayComposite = new RdvRelayConfigurationComposite( tabFolder, SWT.NONE);
		tbtmRdvRelayItem.setControl(rdvRelayComposite);
		sashForm.setWeights(new int[] {1, 1});
	}

	public void init( Jp2pContainerPropertySource source ){
		NetworkManagerPropertySource nmps = new NetworkManagerPropertySource( source );
		NetworkConfigurationPropertySource ncps = new NetworkConfigurationPropertySource( nmps );
		this.overviewComposite.init(ncps);
		this.tcpComposite.init(ncps);
		this.httpComposite.init(ncps);
		this.http2Composite.init(ncps);
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
