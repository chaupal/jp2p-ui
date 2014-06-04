/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.template.config;

import net.jp2p.container.Jp2pContainerPropertySource;

import org.chaupal.jp2p.ui.jxta.network.configurator.Jp2pNetworkConfiguratorComposite;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class NetworkConfiguratorWizardPage extends WizardPage {

	private Jp2pNetworkConfiguratorComposite composite;
	
	protected NetworkConfiguratorWizardPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		composite = new Jp2pNetworkConfiguratorComposite( parent, SWT.NONE );
		super.setControl(composite);	
	}
	public void init( Jp2pContainerPropertySource source ){
		composite.init(source);
	}
}