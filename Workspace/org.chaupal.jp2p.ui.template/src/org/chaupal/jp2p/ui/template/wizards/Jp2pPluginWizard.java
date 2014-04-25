/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
/**
 * 
 */
package org.chaupal.jp2p.ui.template.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 * @author Marine
 *
 */
public class Jp2pPluginWizard extends Wizard implements IWorkbenchWizard{

	public static final String S_MSG_SETUP_CONTEXT = "Set up JP2P Bundle Project";
	public static final String S_MSG_BUNDLE_CONTEXT_PAGE = "JP2P Bundle context";
	
	//private SelectStringsWizardPage selectStringsPage;

	@Override
	public void addPages() {
		setWindowTitle( S_MSG_SETUP_CONTEXT);
		//contextPage = new ContextWizardView( S_MSG_BUNDLE_CONTEXT_PAGE);
		//addPage(contextPage);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}	

	
	@Override
	public boolean performFinish() {
		return false;
	}
}
