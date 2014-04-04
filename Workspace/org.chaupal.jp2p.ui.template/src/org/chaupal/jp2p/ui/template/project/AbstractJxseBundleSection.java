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
package org.chaupal.jp2p.ui.template.project;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import net.jp2p.container.Jp2pContainerPropertySource;
import net.jp2p.container.utils.io.IOUtils;
import net.jp2p.chaupal.xml.XMLContainerBuilder;

import org.chaupal.jp2p.ui.template.Activator;
import org.chaupal.jp2p.ui.template.TemplateUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.pde.core.plugin.IPluginReference;
import org.eclipse.pde.internal.core.ibundle.IBundle;
import org.eclipse.pde.internal.core.ibundle.IBundlePluginModelBase;
import org.eclipse.pde.ui.IFieldData;
import org.eclipse.pde.ui.templates.OptionTemplateSection;
import org.eclipse.pde.ui.templates.PluginReference;
import org.eclipse.swt.widgets.Button;

/**
 * @author Marine
 *
 */
@SuppressWarnings("restriction")//Needed to use the IBundle interface
public abstract class AbstractJxseBundleSection extends OptionTemplateSection {

	public static final String TEMPLATE_ROOT = "jxse";

	// key for input field
	public static final String KEY_JXSE_CLASS_NAME = "jxseClassName";
	// key for hidden field
	public static final String KEY_DOLLAR_MARK            = "dollarMark";
	public static final String KEY_PACKAGE_PATH           = "packagePath";
	public static final String KEY_SOURCE_PATH            = "sourcePath";
	
	public static final String KEY_APPLICATION_DOMAIN = "applicationDomain";
	public static final String KEY_WEBSITE = "website";
	public static final String KEY_DOMAIN = "domain";
	
	public static final String KEY_JXSE_CONTEXT = "JxseContext";
	public static final String FILE_JXSE_XML = "JXSE-INF/jxse-1.0.0.xml";
	public static final String FILE_OSGI_XML = "OSGI-INF/attendees.xml";
	public static final String FOLDER_OSGI = "OSGI-INF/";

	public static final String JXSE_NET_OSGI_JXSE = "net.osgi.jxse";
	public static final String JXSE_NET_OSGI_JXSE_SERVICE = JXSE_NET_OSGI_JXSE + ".service";
	public static final String ORG_ECLIPSELABS_OSGI_BROKER = "org.eclipselabs.osgi.ds.broker";

	public static final String S_META_INF = "META-INF/";
	public static final String S_MANIFEST_MF = "MANIFEST.MF";
	public static final String S_JXSE_INF = "JXSE-INF/";
	public static final String S_JXSE_FILE = "jxse-1.0.0.xml";

	public static final String S_OSGI_INF = "OSGI-INF/";
	public static final String S_ATTENDESS_XML = "attendees.xml";

	private final String DS_MANIFEST_KEY = "Service-Component"; //$NON-NLS-1$

	private String packageName             = null;
	private String packagePath             = null;
	private String dollarMark              = null;
	private String sourcePath              = null;
	
	private String domain                  = null;
	private String website                 = null;
	private String applicationDomain       = null;
	
	private String pluginName;
	
	private Button nextButton;
	
	private Jp2pContainerPropertySource properties;
	
	private Logger logger = Logger.getLogger( AbstractJxseBundleSection.class.getName() );

	public AbstractJxseBundleSection() {
		super();
	}

	
	public Jp2pContainerPropertySource getPropertySource() {
		return this.properties;
	}
	
	/**
	 * allow subclasses to fill the properties after initialisation
	 * @param properties
	 */
	protected abstract void onFillProperties( Jp2pContainerPropertySource properties );
	
	@Override
	protected void initializeFields(IFieldData data) {
		String id = data.getId();
		this.packageName = TemplateUtil.getFormattedPackageName(id);
		this.packagePath = packageName.replace('.', '\\');
		this.dollarMark = "$";
		this.sourcePath = "";
		this.pluginName = data.getId();
		
		initializeOption(KEY_PACKAGE_PATH, packagePath);
		initializeOption(KEY_DOLLAR_MARK, this.dollarMark);
		initializeOption(KEY_SOURCE_PATH, this.sourcePath);
		this.properties = new Jp2pContainerPropertySource( data.getId());
		this.onFillProperties(properties);
	}

	public String getPluginName() {
		return pluginName;
	}

	@Override
	public void addPages(Wizard wizard) {
		this.markPagesAdded();
	}

	
	@Override
	public String getStringOption(String name) {
		if (name.equals(KEY_PACKAGE_NAME)) {
			return packageName;
		} else if (name.equals(KEY_PACKAGE_PATH)) {
			return packagePath;
		} else if (name.equals(KEY_DOLLAR_MARK)) {
			return dollarMark;
		} else if (name.equals(KEY_SOURCE_PATH)) {
			return sourcePath;
		} else if (name.equals(KEY_APPLICATION_DOMAIN)) {
			return this.applicationDomain;
		} else if (name.equals(KEY_DOMAIN)) {
			return this.domain;
		} else if (name.equals(KEY_WEBSITE)) {
			return this.website;
		}
		return super.getStringOption(name);
	}

	@Override
	public int getNumberOfWorkUnits() {
		return super.getNumberOfWorkUnits() + 1;
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
		return new String[]{FOLDER_OSGI, FILE_OSGI_XML, FILE_JXSE_XML};
	}

	protected String getAttenddeesXML(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buffer.append("\t<scr:component xmlns:scr=\"http://www.osgi.org/xmlns/scr/v1.1.0\" name=\"" + this.packageName + ".service\">\n");
		buffer.append("\t<implementation class=\"" + this.packageName + ".service.OsgiComponent\"/>\n");
		buffer.append("\t<reference bind=\"setAttendeeService\" cardinality=\"1..1\" interface=\"org.eclipselabs.osgi.ds.broker.IAttendeeService\"" +
		" name=\"IAttendeeService\" policy=\"static\" unbind=\"unsetAttendeeService\"/>\n");
		buffer.append("</scr:component>");
		return buffer.toString();
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

	/**
	 * @param sourcePath the sourcePath to set
	 */
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	@Override
	public IPluginReference[] getDependencies(String schemaVersion) {
		ArrayList<PluginReference> result = new ArrayList<PluginReference>();

		result.add(new PluginReference( JXSE_NET_OSGI_JXSE, null, 0)); 
		result.add(new PluginReference( JXSE_NET_OSGI_JXSE_SERVICE, null, 0)); 
		result.add(new PluginReference( ORG_ECLIPSELABS_OSGI_BROKER, null, 0)); 
		return result.toArray(
				new IPluginReference[result.size()]);
	}

	
	public void update() throws Exception{}
	
	@Override
	protected void updateModel(IProgressMonitor monitor) throws CoreException {
		logger.info("Updating model");
		IBundlePluginModelBase mb = (IBundlePluginModelBase) model;
		IBundle bundle = mb.getBundleModel().getBundle();
		bundle.setHeader( DS_MANIFEST_KEY, FILE_OSGI_XML );
		createComponent(monitor);
	}
	
	
	private void createComponent( IProgressMonitor monitor ){
		XMLContainerBuilder builder = 	new XMLContainerBuilder(DS_MANIFEST_KEY, null, null);
		InputStream source = null;
		try{
			source = null;//new ByteArrayInputStream( builder.build( ).getBytes()); 
			this.createFile(project, S_JXSE_INF + "/", S_JXSE_FILE, source, monitor);
			IOUtils.closeInputStream(source);
			monitor.worked(3);
			source = new ByteArrayInputStream( this.getAttenddeesXML().getBytes()); 
			this.createFile(project, S_OSGI_INF + "/", S_ATTENDESS_XML, source, monitor);
			monitor.worked(4);
		}
		finally{
			IOUtils.closeInputStream(source);
		}
		
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE,  monitor );
		} catch (CoreException e) {
			logger.severe( e.getMessage() );
			e.printStackTrace();
		}

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

}
