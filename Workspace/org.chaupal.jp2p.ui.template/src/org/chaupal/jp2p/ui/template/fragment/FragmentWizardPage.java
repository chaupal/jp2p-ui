/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.template.fragment;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class FragmentWizardPage extends WizardPage {
	
	private static final String S_MSG_JXSE_FRAGMENT_PROPS = "JXSE Fragment Properties";
	private static final String S_MSG_SET_FRAGMENT_CONTEXT_PROPS = "Set the identifier and token for secure declarative srvices";
	
	private Text text_pass_1;
	private Text text_pass_2;
	
	/**
	 * @wbp.parser.constructor
	 */
	public FragmentWizardPage(String pageName) {
		super(pageName);
		setTitle( S_MSG_JXSE_FRAGMENT_PROPS);
		setDescription(S_MSG_SET_FRAGMENT_CONTEXT_PROPS);
	}

	public FragmentWizardPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(2, false));

		Label lblName = new Label(container, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Identifier:");
		
		text_pass_1 = new Text(container, SWT.BORDER);
		text_pass_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		Label lblHomeFolder = new Label(container, SWT.NONE);
		lblHomeFolder.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblHomeFolder.setText("Token:");
		
		text_pass_2 = new Text(container, SWT.BORDER);
		text_pass_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	}

	/**
	 * Complete the view by filling in the properties and directives
	 */
	public String[] complete(){
		String[] retval = new String[2];
		retval[0] = this.text_pass_1.getText();
		retval[1] = this.text_pass_2.getText();
		return retval;
	}
}