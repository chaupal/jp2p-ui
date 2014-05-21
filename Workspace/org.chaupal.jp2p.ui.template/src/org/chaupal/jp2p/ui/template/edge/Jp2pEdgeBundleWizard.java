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
package org.chaupal.jp2p.ui.template.edge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.chaupal.jp2p.ui.template.AbstractJp2pBundleWizard;
import org.eclipse.pde.ui.templates.ITemplateSection;

/**
 * @author Marine
 *
 */
public class Jp2pEdgeBundleWizard extends AbstractJp2pBundleWizard{

	@Override
	public String[] getImportPackages() {
		List<String> results = new ArrayList<String>();
		if( super.getImportPackages() != null )
			results = new ArrayList<String>( Arrays.asList( super.getImportPackages()));
        results.add( JP2P_NET_JP2P_CHAUPAL_ACTIVATOR );
        results.add( JP2P_NET_JP2P_CHAUPAL_JXTA_ACTIVATOR );
        results.add( JP2P_NET_JP2P_CHAUPAL_SERVICE );
		results.add( JP2P_NET_JP2P_CONTAINER );
		results.add( JP2P_NET_JP2P_CONTAINER_ACTIVATOR );
		results.add( JP2P_NET_JP2P_CONTAINER_COMPONENT );

        results.add( ORG_ECLIPSELABS_OSGI_BROKER_SERVICE);
        results.add( ORG_OSGI_FRAMEWORK);
		return results.toArray( new String[ results.size()]);
	}	

	@Override
	public ITemplateSection[] createTemplateSections() {
		ITemplateSection[] sections = new ITemplateSection[1];
		EdgeTemplateSection acs1 = new EdgeTemplateSection();
		sections[0] = acs1;
		return sections;
	}
}
