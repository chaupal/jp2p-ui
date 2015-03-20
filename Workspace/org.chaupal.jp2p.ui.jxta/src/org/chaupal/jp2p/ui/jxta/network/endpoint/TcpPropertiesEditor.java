/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.endpoint;

import net.jp2p.chaupal.jxta.platform.configurator.NetworkConfigurationPropertySource;

import org.chaupal.jp2p.ui.jxta.network.configurator.AbstractEditor;
import org.chaupal.jp2p.ui.jxta.network.configurator.NetworkConfiguratorEditorInput;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;


public class TcpPropertiesEditor extends AbstractEditor {
	
	public static final String ID = "nl.cultuurinzicht.eetmee.rcp.editors.NAWEditor";
	public static final String TITLE = "Persoonsgegevens";
	
	private NetworkConfigurationPropertySource source;

	public TcpPropertiesEditor() {
		super();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		source = ((NetworkConfiguratorEditorInput) input).getSource();
		setPartName(createPartname());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		
		boolean isValid = true;
//		try {
//			BoekingValidator.validate(boeking);
//		} catch (ValidationException e) {
//			isValid = MessageDialog.openConfirm(getSite().getShell(), "Bevestiging", e.getKey().getKey() + ", wilt u deze boeking toch opslaan?");
//		}
		if (isValid) {
			//super.doSave(monitor);
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite tcpComposite = new TcpConfigurationComposite( parent, SWT.NONE );
	}

	@Override
	protected String createPartname() {
		// TODO Auto-generated method stub
		return null;
	}
}