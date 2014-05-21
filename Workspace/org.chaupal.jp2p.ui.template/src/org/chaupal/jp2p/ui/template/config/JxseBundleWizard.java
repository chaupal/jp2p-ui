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
package org.chaupal.jp2p.ui.template.config;

import org.chaupal.jp2p.ui.template.project.AbstractJp2pTemplateSection;
import org.chaupal.jp2p.ui.template.project.ContextWizardOption;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.internal.ui.wizards.plugin.PluginFieldData;
import org.eclipse.pde.ui.IFieldData;
import org.eclipse.pde.ui.IPluginContentWizard;
import org.eclipse.pde.ui.templates.ITemplateSection;
import org.eclipse.pde.ui.templates.NewPluginTemplateWizard;
import org.eclipse.pde.ui.templates.TemplateOption;

/**
 * @author Marine
 *
 */
@SuppressWarnings("restriction")
public class JxseBundleWizard extends NewPluginTemplateWizard  implements IPluginContentWizard{

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
	public IWizardPage getNextPage(IWizardPage page) {
		ITemplateSection[] sections = super.getTemplateSections();
		AbstractJp2pTemplateSection acs1 = ( AbstractJp2pTemplateSection )sections[0];
		if( page instanceof NetworkConfiguratorWizardPage ){
			try {
				acs1.update();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			//NetworkConfiguratorWizardPage ncwp = ( NetworkConfiguratorWizardPage )page;
			//ncwp.init(acs1.getPropertySource());
		}
		return super.getNextPage(page);
	}

	@Override
	public ITemplateSection[] createTemplateSections() {
		ITemplateSection[] sections = new ITemplateSection[1];
		sections[0] = new JxseConfigurationTemplateSection();
		return sections;
	}
	
	@Override
	public boolean performFinish(final IProject project, IPluginModelBase model, IProgressMonitor monitor) {
		if( !super.performFinish(project, model, monitor))
			return false;
		try {
			ITemplateSection[] sections = super.getTemplateSections();
			monitor.beginTask("perform finish", sections.length);

			AbstractJp2pTemplateSection contextSection = null;

			TemplateOption[] allOptions = null;
			for (int i = 0; i < sections.length; i++) {
				if (sections[i] instanceof AbstractJp2pTemplateSection ) {
					contextSection = (AbstractJp2pTemplateSection) sections[i];
					allOptions = contextSection.getOptions(0);
					break;
				}
			}
			if (allOptions == null)
				return true;
			
			for (int i = 0; i < allOptions.length; i++) {
				TemplateOption option = allOptions[i];
				if (option != null && option instanceof ContextWizardOption) {
					contextSection.execute(project, model, new SubProgressMonitor(
							monitor, 1));
				}	
			}
		} catch (CoreException e) {
			e.printStackTrace();
			return false;
		} finally {
			monitor.done();
		}
		return true;
	}
}
