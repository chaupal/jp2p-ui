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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.chaupal.jp2p.ui.template.project.AbstractJxseBundleSection;
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

	private static final String S_IMPORT_NET_JXTA_PLATFORM = "net.jxta.platform";
	private static final String S_IMPORT_NET_OSGI_JXSE_BUILDER ="net.osgi.jxse.builder";
	private static final String S_IMPORT_NET_OSGI_JXSE_CONTEXT ="net.osgi.jxse.context";
	private static final String S_IMPORT_NET_OSGI_JXSE_FACTORY ="net.osgi.jxse.factory";
	private static final String S_IMPORT_NET_OSGI_SERVICE_ACTIVATOR ="net.osgi.jxse.service.activator";
	private static final String S_IMPORT_NET_OSGI_SERVICE_CORE ="net.osgi.jxse.service.core";
	private static final String S_IMPORT_ORG_ECLIPSELABS_BROKER = "org.eclipselabs.osgi.ds.broker.service";
	private static final String S_IMPORT_ORG_OSGI_FRAMEWORK = "org.osgi.framework;version=\"1.3.0\"";
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
		AbstractJxseBundleSection acs1 = ( AbstractJxseBundleSection )sections[0];
		if( page instanceof NetworkConfiguratorWizardPage ){
			try {
				acs1.update();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			NetworkConfiguratorWizardPage ncwp = ( NetworkConfiguratorWizardPage )page;
			ncwp.init(acs1.getPropertySource());
		}
		return super.getNextPage(page);
	}

	@Override
	public ITemplateSection[] createTemplateSections() {
		ITemplateSection[] sections = new ITemplateSection[1];
		sections[0] = new JxseConfigurationBundleSection();
		return sections;
	}

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
	public boolean performFinish(final IProject project, IPluginModelBase model, IProgressMonitor monitor) {
		if( !super.performFinish(project, model, monitor))
			return false;
		try {
			ITemplateSection[] sections = super.getTemplateSections();
			monitor.beginTask("perform finish", sections.length);

			AbstractJxseBundleSection contextSection = null;

			for (int i = 0; i < sections.length; i++) {
				if (sections[i] instanceof AbstractJxseBundleSection ) {
					contextSection = (AbstractJxseBundleSection) sections[i];
				}
			}

			TemplateOption[] allOptions = contextSection.getOptions(0);
			if (allOptions != null) {
				for (int i = 0; i < allOptions.length; i++) {
					TemplateOption option = allOptions[i];
					if (option != null && option instanceof ContextWizardOption) {
						contextSection.execute(project, model, new SubProgressMonitor(
								monitor, 1));
					}
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
