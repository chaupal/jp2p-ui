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
package org.chaupal.jp2p.ui.template.fragment;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.jp2p.container.utils.Utils;
import net.jp2p.container.utils.io.IOUtils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.internal.core.ibundle.IBundle;
import org.eclipse.pde.internal.core.ibundle.IBundlePluginModelBase;
import org.eclipse.pde.internal.ui.wizards.plugin.PluginFieldData;
import org.eclipse.pde.ui.IFieldData;
import org.eclipse.pde.ui.IPluginContentWizard;
import org.eclipse.pde.ui.templates.AbstractNewPluginTemplateWizard;
import org.eclipse.pde.ui.templates.ITemplateSection;

/**
 * @author Marine
 *
 */
@SuppressWarnings("restriction")
public class JxseFragmentWizard extends AbstractNewPluginTemplateWizard  implements IPluginContentWizard{

	public static final String S_MSG_SETUP_FRAGMENT = "Set up JXSE Fragment Project";
	public static final String S_MSG_BUNDLE_FRAGMENT_PAGE = "Jxse Fragment";

	public static final String S_JXSE_INF = "JXSE-INF/";
	public static final String S_TOKENS_FILE = "tokens.txt";

	public static final String S_OSGI_INF = "OSGI-INF/";
	public static final String S_ATTENDESS_XML = "attendees.xml";

	public static final String S_META_INF = "META-INF/";
	public static final String S_MANIFEST_MF = "MANIFEST.MF";
	private static final String S_FRAGMENT_HOST = "Fragment-Host";
	private static final String S_FRAGMENT_VALUE = "org.eclipselabs.jxse.ui;bundle-version=\"1.0.0\"";

	private FragmentWizardPage fragmentPage;

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
		fragmentPage = (FragmentWizardPage) page;
		return super.getNextPage(page);
	}
	
	@Override
	public boolean performFinish( final IProject project, IPluginModelBase model, final IProgressMonitor monitor) {
		if( fragmentPage == null )
			return false;
		String[] tokens = fragmentPage.complete();
		if(( tokens == null ) || ( Utils.isNull( tokens[0] )) || ( Utils.isNull( tokens[1])))
				return false;
		InputStream source = null;
		try{
			source = new ByteArrayInputStream(( tokens[0] + "\n" + tokens[1]).getBytes() );
			this.createFile(project, S_JXSE_INF + "/", S_TOKENS_FILE, source, monitor);
		}
		finally{
			IOUtils.closeInputStream(source);
		}
		IBundlePluginModelBase mb = (IBundlePluginModelBase) model;
        IBundle bundle = mb.getBundleModel().getBundle();
        bundle.setHeader( S_FRAGMENT_HOST, S_FRAGMENT_VALUE );
		boolean retval = super.performFinish(project, model, monitor);

		return retval;
	}

	/**
	 * Create the given file from the inputstream
	 * @param project
	 * @param directory
	 * @param name
	 * @param source
	 * @param monitor
	 */
	protected void createFile( IProject project, String directory, String name, InputStream source, IProgressMonitor monitor ){
		IFolder folder = project.getFolder( directory );
		if( !folder.exists() ){
			try {
				folder.create(true, true, monitor);
				IFile file = project.getFile(directory + name );
				file.create(source, true, monitor);
			} catch (CoreException e) {
				e.printStackTrace();
			}finally{
				IOUtils.closeInputStream( source);
			}
		}
	}

	@Override
	protected void addAdditionalPages() {
		fragmentPage = new FragmentWizardPage( S_MSG_BUNDLE_FRAGMENT_PAGE);
		addPage(fragmentPage);
	}

	@Override
	public ITemplateSection[] getTemplateSections() {
		return new ITemplateSection[0];
	}
}
