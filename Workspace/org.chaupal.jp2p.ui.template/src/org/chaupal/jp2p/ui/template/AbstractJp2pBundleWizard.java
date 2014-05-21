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
package org.chaupal.jp2p.ui.template;

import org.chaupal.jp2p.ui.template.IJP2PBundleDefinitions;
import org.chaupal.jp2p.ui.template.project.AbstractBundleTemplateSection;
import org.eclipse.pde.internal.ui.wizards.plugin.PluginFieldData;
import org.eclipse.pde.ui.IFieldData;
import org.eclipse.pde.ui.templates.ITemplateSection;
import org.eclipse.pde.ui.templates.NewPluginTemplateWizard;

/**
 * @author Marine
 *
 */
@SuppressWarnings("restriction")
public abstract class AbstractJp2pBundleWizard extends NewPluginTemplateWizard implements IJP2PBundleDefinitions{
		
	@Override
	public void init(IFieldData data) {
		super.init(data);
		if( data instanceof PluginFieldData ){
			PluginFieldData fData = (PluginFieldData) data;
			fData.setSimple(true);
			fData.setProvider( S_PROVIDER );
		}
		for( ITemplateSection section: super.getTemplateSections() ){
			if( section instanceof AbstractBundleTemplateSection ){
				AbstractBundleTemplateSection os = (AbstractBundleTemplateSection) section;
				os.initializeFields(data);
			}
		}
	}
}