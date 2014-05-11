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
package org.chaupal.jp2p.ui.template.rdv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.chaupal.jp2p.ui.template.config.JxseBundleWizard;
import org.eclipse.pde.ui.templates.ITemplateSection;

/**
 * @author Marine
 *
 */
public class Jp2pRdvBundleWizard extends JxseBundleWizard{

	private static final String S_IMPORT_NET_JXTA_PLATFORM = "net.jxta.platform";
	private static final String S_IMPORT_NET_OSGI_JXSE_BUILDER ="net.osgi.jxse.builder";
	private static final String S_IMPORT_NET_OSGI_JXSE_CONTEXT ="net.osgi.jxse.context";
	private static final String S_IMPORT_NET_OSGI_JXSE_FACTORY ="net.osgi.jxse.factory";
	private static final String S_IMPORT_NET_OSGI_SERVICE_ACTIVATOR ="net.osgi.jxse.service.activator";
	private static final String S_IMPORT_NET_OSGI_SERVICE_CORE ="net.osgi.jxse.service.core";
	private static final String S_IMPORT_ORG_ECLIPSELABS_BROKER = "org.eclipselabs.osgi.ds.broker.service";
	private static final String S_IMPORT_ORG_OSGI_FRAMEWORK = "org.osgi.framework;version=\"1.3.0\"";
	
	@Override
	public String[] getImportPackages() {
		List<String> results = new ArrayList<String>();
		if( super.getImportPackages() != null )
			results = new ArrayList<String>( Arrays.asList( super.getImportPackages()));
        results.add( S_IMPORT_NET_JXTA_PLATFORM);
		results.add( S_IMPORT_NET_OSGI_JXSE_BUILDER);
		results.add( S_IMPORT_NET_OSGI_JXSE_CONTEXT);
		results.add( S_IMPORT_NET_OSGI_JXSE_FACTORY);
        results.add( S_IMPORT_NET_OSGI_SERVICE_ACTIVATOR);
        results.add( S_IMPORT_NET_OSGI_SERVICE_CORE);
        results.add( S_IMPORT_ORG_ECLIPSELABS_BROKER);
        results.add( S_IMPORT_ORG_OSGI_FRAMEWORK);
		return results.toArray( new String[ results.size()]);
	}	

	@Override
	public ITemplateSection[] createTemplateSections() {
		ITemplateSection[] sections = new ITemplateSection[1];
		RdvTemplateSection acs1 = new RdvTemplateSection();
		sections[0] = acs1;
		return sections;
	}
}
