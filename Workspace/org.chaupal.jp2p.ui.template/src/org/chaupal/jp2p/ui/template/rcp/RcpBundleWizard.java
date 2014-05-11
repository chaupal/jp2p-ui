/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.template.rcp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.chaupal.jp2p.ui.template.config.JxseBundleWizard;
import org.eclipse.pde.ui.templates.ITemplateSection;

/**
 * @author Marine
 *
 */
public class RcpBundleWizard extends JxseBundleWizard{

	private static final String S_IMPORT_ORG_CHAUPAL_JP2P_UI_P2 = "org.chaupal.jp2p.ui.p2";
	private static final String S_IMPORT_ORG_CHAUPAL_JP2P_UI_TRAY = "org.chaupal.jp2p.ui.tray";
	private static final String S_IMPORT_ORG_ECLIPSE_CORE_RESOURCES = "org.eclipse.core.resources";
	private static final String S_IMPORT_ORG_ECLIPSE_EQUINOX_APP = "org.eclipse.equinox.app;version=\"1.1.0\"";
	private static final String S_IMPORT_ORG_ECLIPSE_EQUINOX_P2_CORE = "org.eclipse.equinox.p2.core;version=\"2.0.0\"";
	private static final String S_IMPORT_ORG_ECLIPSE_EQUINOX_P2_UI = "org.eclipse.equinox.p2.ui;version=\"2.0.0\"";
	private static final String S_IMPORT_ORG_ECLIPSE_JFACE_ACTION = "org.eclipse.jface.action";
	private static final String S_IMPORT_ORG_ECLIPSE_JFACE_RESOURCE = "org.eclipse.jface.resource";
	private static final String S_IMPORT_ORG_ECLIPSE_SWT = "org.eclipse.swt";
	private static final String S_IMPORT_ORG_ECLIPSE_SWT_GRAPHICS = "org.eclipse.swt.graphics";
	private static final String S_IMPORT_ORG_ECLIPSE_SWT_WIDGETS = "org.eclipse.swt.widgets";
	private static final String S_IMPORT_ORG_ECLIPSE_UI = "org.eclipse.ui";
	private static final String S_IMPORT_ORG_ECLIPSE_UI_APPLICATION = "org.eclipse.ui.application";
	
	@Override
	public String[] getImportPackages() {
		List<String> results = new ArrayList<String>();
		if( super.getImportPackages() != null )
			results = new ArrayList<String>( Arrays.asList( super.getImportPackages()));
        results.add( S_IMPORT_ORG_CHAUPAL_JP2P_UI_P2);
		results.add( S_IMPORT_ORG_CHAUPAL_JP2P_UI_TRAY);
		results.add( S_IMPORT_ORG_ECLIPSE_CORE_RESOURCES);
		results.add( S_IMPORT_ORG_ECLIPSE_EQUINOX_APP);
        results.add( S_IMPORT_ORG_ECLIPSE_EQUINOX_P2_CORE);
        results.add( S_IMPORT_ORG_ECLIPSE_EQUINOX_P2_UI);
        results.add( S_IMPORT_ORG_ECLIPSE_JFACE_ACTION);
        results.add( S_IMPORT_ORG_ECLIPSE_JFACE_RESOURCE);
        results.add( S_IMPORT_ORG_ECLIPSE_SWT );
        results.add( S_IMPORT_ORG_ECLIPSE_SWT_WIDGETS );
        results.add( S_IMPORT_ORG_ECLIPSE_SWT_GRAPHICS );
        results.add( S_IMPORT_ORG_ECLIPSE_UI );
        results.add( S_IMPORT_ORG_ECLIPSE_UI_APPLICATION );
		return results.toArray( new String[ results.size()]);
	}	

	@Override
	public ITemplateSection[] createTemplateSections() {
		ITemplateSection[] sections = new ITemplateSection[1];
		RcpTemplateSection acs1 = new RcpTemplateSection();
		sections[0] = acs1;
		return sections;
	}
}
