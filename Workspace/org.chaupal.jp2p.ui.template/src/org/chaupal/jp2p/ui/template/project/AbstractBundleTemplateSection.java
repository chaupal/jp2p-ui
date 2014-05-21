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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import net.jp2p.container.utils.io.IOUtils;

import org.chaupal.jp2p.ui.template.Activator;
import org.chaupal.jp2p.ui.template.IJP2PBundleDefinitions;
import org.chaupal.jp2p.ui.template.TemplateUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.pde.internal.core.ibundle.IBundle;
import org.eclipse.pde.internal.core.ibundle.IBundlePluginModelBase;
import org.eclipse.pde.ui.IFieldData;
import org.eclipse.pde.ui.templates.OptionTemplateSection;

/**
 * @author Marine
 *
 */
@SuppressWarnings("restriction")//Needed to use the IBundle interface
public abstract class AbstractBundleTemplateSection extends OptionTemplateSection implements IJP2PBundleDefinitions{

	// key for hidden field
	public static final String KEY_DOLLAR_MARK            = "dollarMark";
	public static final String KEY_PACKAGE_PATH           = "packagePath";
	public static final String KEY_SOURCE_PATH            = "sourcePath";

	public static final String KEY_PACKAGE           = "$packageName$";
	public static final String KEY_BUNDLE_ID         = "$bundle-id$";
	public static final String KEY_NAME              = "$name$";

	public static final String KEY_APPLICATION_DOMAIN = "applicationDomain";
	public static final String KEY_WEBSITE = "website";
	public static final String KEY_DOMAIN = "domain";
	
	public static final String FILE_OSGI_XML = "OSGI-INF/attendees.xml";
	public static final String FOLDER_OSGI = "OSGI-INF/";

	public static final String S_OSGI_INF = "OSGI-INF/";
	public static final String S_ATTENDEES_XML = "attendees.xml";

	private final static String DS_MANIFEST_KEY = "Service-Component"; //$NON-NLS-1$

	private String packagePath             = null;
	private String dollarMark              = null;
	private String sourcePath              = null;
	
	private String domain                  = null;
	private String website                 = null;
	private String applicationDomain       = null;
	
	private Map<String, String> attributes;
	
	//private Button nextButton;
	
	private String templateRoot;
	
	private Logger logger = Logger.getLogger( AbstractBundleTemplateSection.class.getName() );

	protected AbstractBundleTemplateSection( String templateRoot ) {
		this.templateRoot = templateRoot;
		this.attributes = new HashMap<String, String>();
	}
	
	protected final Map<String, String> getAttributes() {
		return attributes;
	}

	@Override
	public void initializeFields(IFieldData data) {
		super.initializeFields(data);
		String id = data.getId();
		String packageName = TemplateUtil.getFormattedPackageName(id);
		this.attributes.put( KEY_PACKAGE, packageName );
		this.attributes.put( KEY_BUNDLE_ID, packageName );
		this.packagePath = packageName.replace('.', '\\');
		this.dollarMark = "$";
		this.sourcePath = "";
		
		initializeOption(KEY_PACKAGE_PATH, packagePath);
		initializeOption(KEY_DOLLAR_MARK, this.dollarMark);
		initializeOption(KEY_SOURCE_PATH, this.sourcePath);
	}

	public String getPluginName() {
		return this.attributes.get( KEY_PACKAGE );
	}

	@Override
	public void addPages(Wizard wizard) {
		this.markPagesAdded();
	}

	
	@Override
	public String getStringOption(String name) {
		if (name.equals(KEY_PACKAGE_NAME)) {
			return this.attributes.get( KEY_PACKAGE );
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
		return this.templateRoot;
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

	/**
	 * @param sourcePath the sourcePath to set
	 */
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public void update() throws Exception{}
	
	@Override
	protected void updateModel(IProgressMonitor monitor) throws CoreException {
		logger.info("Updating model");
		IBundlePluginModelBase mb = (IBundlePluginModelBase) model;
		IBundle bundle = mb.getBundleModel().getBundle();
		bundle.setHeader( DS_MANIFEST_KEY, FILE_OSGI_XML );
		createOSGIInf( this.project, 0, monitor);
	}
	
	public static String getAttenddeesXML( String packageName ){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buffer.append("\t<scr:component xmlns:scr=\"http://www.osgi.org/xmlns/scr/v1.1.0\" name=\"" + packageName + ".service\">\n");
		buffer.append("\t<implementation class=\"" + packageName + ".service.OsgiComponent\"/>\n");
		buffer.append("\t<reference bind=\"setAttendeeService\" cardinality=\"1..1\" interface=\"org.eclipselabs.osgi.ds.broker.IAttendeeService\"" +
		" name=\"IAttendeeService\" policy=\"static\" unbind=\"unsetAttendeeService\"/>\n");
		buffer.append("</scr:component>");
		return buffer.toString();
	}

	/**
	 * Create the OSGI-INF directory
	 * @param project
	 * @param worked
	 * @param monitor
	 * @return
	 */
	protected int createOSGIInf( IProject project, int worked, IProgressMonitor monitor ){
		Logger logger = Logger.getLogger( AbstractBundleTemplateSection.class.getName() );
		InputStream source = null;
		try{
			String packageName = this.attributes.get( KEY_PACKAGE );
			source = new ByteArrayInputStream( getAttenddeesXML( packageName).getBytes()); 
			createFile(project, S_OSGI_INF + "/", S_ATTENDEES_XML, source, monitor);
			monitor.worked(worked++);
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
		return worked;
	}

	/**
	 * Create the given file from the inputstream
	 * @param project
	 * @param directory
	 * @param name
	 * @param source
	 * @param monitor
	 */
	public static void createFile( IProject project, String directory, String name, InputStream source, IProgressMonitor monitor ){
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
