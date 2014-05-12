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
package org.chaupal.jp2p.ui.template.rcp;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import net.jp2p.container.Jp2pContainerPropertySource;

import org.chaupal.jp2p.ui.template.Activator;
import org.chaupal.jp2p.ui.template.project.AbstractJp2pTemplateSection;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.pde.core.plugin.IPluginReference;
import org.eclipse.pde.ui.templates.PluginReference;

/**
 * @author Marine
 *
 */
public class RcpTemplateSection extends AbstractJp2pTemplateSection {

	public static final String TEMPLATE_ROOT = "rcp";
	
	public RcpTemplateSection() {
		super( TEMPLATE_ROOT );
		this.setPageCount(0);
	}

	@Override
	protected void onFillProperties(Jp2pContainerPropertySource properties) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.pde.ui.templates.OptionTemplateSection#getInstallURL()
	 */
	@Override
	protected URL getInstallURL() {
		return Activator.getDefault().getBundle().getEntry("/");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.pde.ui.templates.OptionTemplateSection#getSectionId()
	 */
	@Override
	public String getSectionId() {
		return TEMPLATE_ROOT;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.pde.ui.templates.AbstractTemplateSection#getPluginResourceBundle()
	 */
	@Override
	protected ResourceBundle getPluginResourceBundle() {
		return Platform.getResourceBundle(Activator.getDefault().getBundle());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.pde.ui.templates.ITemplateSection#getNewFiles()
	 */
	@Override
	public String[] getNewFiles() {
		return new String[]{FOLDER_OSGI, FILE_OSGI_XML};
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.pde.ui.templates.ITemplateSection#getUsedExtensionPoint()
	 */
	@Override
	public String getUsedExtensionPoint() {
		return null;
	}

	@Override
	public boolean isDependentOnParentWizard() {
		return true;
	}

	@Override
	public IPluginReference[] getDependencies(String schemaVersion) {
		ArrayList<PluginReference> result = new ArrayList<PluginReference>();
		return result.toArray(
				new IPluginReference[result.size()]);
	}
	
	@Override
	protected void updateModel(IProgressMonitor monitor) throws CoreException {
		Logger logger = Logger.getLogger( RcpTemplateSection.class.getName() );
		logger.info("Updating model");
	}
}
