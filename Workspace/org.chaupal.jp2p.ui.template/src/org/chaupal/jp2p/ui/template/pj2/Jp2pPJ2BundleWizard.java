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
package org.chaupal.jp2p.ui.template.pj2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.chaupal.jp2p.ui.template.IJP2PBundleDefinitions;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.internal.ui.wizards.plugin.PluginFieldData;
import org.eclipse.pde.ui.IFieldData;
import org.eclipse.pde.ui.templates.ITemplateSection;
import org.eclipse.pde.ui.templates.NewPluginTemplateWizard;

/**
 * @author Marine
 *
 */
@SuppressWarnings("restriction")
public class Jp2pPJ2BundleWizard extends NewPluginTemplateWizard implements IJP2PBundleDefinitions{
		
	@Override
	public void init(IFieldData data) {
		super.init(data);
		//Override creation of an activator by making the plugin simple
		if( data instanceof PluginFieldData ){
			PluginFieldData fData = (PluginFieldData) data;
			fData.setSimple(true);
		}
	}

	@Override
	public ITemplateSection[] createTemplateSections() {
		ITemplateSection[] sections = new ITemplateSection[1];
		PJ2ExamplesTemplateSection acs1 = new PJ2ExamplesTemplateSection();
		sections[0] = acs1;
		return sections;
	}

	@Override
	public String[] getImportPackages() {
		List<String> results = new ArrayList<String>();
		if( super.getImportPackages() != null )
			results = new ArrayList<String>( Arrays.asList( super.getImportPackages()));
        results.add( JP2P_NET_JP2P_CONTAINER );
		results.add( JP2P_NET_JP2P_COMPATIBILITY_ACTIVATOR );
		results.add( JP2P_NET_JP2P_COMPATIBILITY_CONTAINER );
		results.add( JP2P_NET_JP2P_COMPATIBILITY_SERVICE );
		results.add( JP2P_NET_JP2P_COMPATIBILITY_UTILS );

		results.add( JP2P_NET_JP2P_CONTAINER_SERVICE );
		results.add( JP2P_NET_JP2P_CONTAINER_COMPONENT );
		results.add( JP2P_NET_JP2P_CONTAINER_CONTEXT );
		results.add( JP2P_NET_JP2P_CONTAINER_PROPERTIES );
		results.add( JP2P_NET_JP2P_CONTAINER_UTILS );

		results.add( NET_JXSE_CONFIGURATION );
		
		results.add( NET_JXTA_DOCUMENT );
		results.add( NET_JXTA_EXCEPTION );
		results.add( NET_JXTA_PSE );
		results.add( NET_JXTA_IMPL_PEERGROUP );
		results.add( NET_JXTA_PEERGROUP );
		results.add( NET_JXTA_PLATFORM );
		results.add( NET_JXTA_PROTOCOL );
		results.add( NET_JXTA_REDEZVOUS );
		results.add( NET_JXTA_SERVICE );
		results.add( ORG_ECLIPSELABS_OSGI_BROKER_SERVICE );

        results.add( ORG_ECLIPSELABS_OSGI_BROKER_SERVICE);
        results.add( ORG_OSGI_FRAMEWORK);
		return results.toArray( new String[ results.size()]);
	}	
	
	@Override
	public boolean performFinish(final IProject project, IPluginModelBase model, IProgressMonitor monitor) {
		if( !super.performFinish(project, model, monitor))
			return false;
		return true;
	}
}
