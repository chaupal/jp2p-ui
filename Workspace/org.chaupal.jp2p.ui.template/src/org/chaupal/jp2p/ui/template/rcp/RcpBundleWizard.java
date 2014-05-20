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

import org.chaupal.jp2p.ui.template.IJP2PBundleDefinitions;
import org.eclipse.pde.ui.templates.ITemplateSection;
import org.eclipse.pde.ui.templates.NewPluginTemplateWizard;

/**
 * @author Marine
 *
 */
public class RcpBundleWizard extends NewPluginTemplateWizard implements IJP2PBundleDefinitions{

	
	@Override
	public String[] getImportPackages() {
		List<String> results = new ArrayList<String>();
		if( super.getImportPackages() != null )
			results = new ArrayList<String>( Arrays.asList( super.getImportPackages()));
        results.add( ORG_CHAUPAL_JP2P_UI_P2);
		results.add( ORG_CHAUPAL_JP2P_UI_TRAY);
		results.add( ORG_CHAUPAL_JP2P_UI_JXTA_PROPERTY );
		results.add( ORG_ECLIPSE_CORE_RESOURCES);
		results.add( ORG_ECLIPSE_EQUINOX_APP);
        results.add( ORG_ECLIPSE_EQUINOX_P2_CORE);
        results.add( ORG_ECLIPSE_EQUINOX_P2_UI);
        results.add( ORG_ECLIPSE_JFACE_ACTION);
        results.add( ORG_ECLIPSE_JFACE_RESOURCE);
        results.add( ORG_ECLIPSE_SWT );
        results.add( ORG_ECLIPSE_SWT_WIDGETS );
        results.add( ORG_ECLIPSE_SWT_GRAPHICS );
        results.add( ORG_ECLIPSE_UI );
        results.add( ORG_ECLIPSE_UI_APPLICATION );
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
