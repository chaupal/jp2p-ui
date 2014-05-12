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
import java.util.ResourceBundle;
import java.util.logging.Logger;

import net.jp2p.container.Jp2pContainerPropertySource;
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
public abstract class AbstractJp2pTemplateSection extends OptionTemplateSection implements IJP2PBundleDefinitions{

	// key for input field
	public static final String KEY_JP2P_CLASS_NAME = "jp2pClassName";
	// key for hidden field
	public static final String KEY_DOLLAR_MARK            = "dollarMark";
	public static final String KEY_PACKAGE_PATH           = "packagePath";
	public static final String KEY_SOURCE_PATH            = "sourcePath";
	
	public static final String KEY_APPLICATION_DOMAIN = "applicationDomain";
	public static final String KEY_WEBSITE = "website";
	public static final String KEY_DOMAIN = "domain";
	
	public static final String FILE_OSGI_XML = "OSGI-INF/attendees.xml";
	public static final String FOLDER_OSGI = "OSGI-INF/";

	public static final String JP2P_NET_OSGI_JP2P = "net.osgi.jxse";
	public static final String JP2P_NET_OSGI_JP2P_SERVICE = JP2P_NET_OSGI_JP2P + ".service";
	public static final String ORG_ECLIPSELABS_OSGI_BROKER = "org.eclipselabs.osgi.ds.broker";

	public static final String S_META_INF = "META-INF/";
	public static final String S_MANIFEST_MF = "MANIFEST.MF";

	public static final String S_OSGI_INF = "OSGI-INF/";
	public static final String S_ATTENDEES_XML = "attendees.xml";

	private final static String DS_MANIFEST_KEY = "Service-Component"; //$NON-NLS-1$

	private String packageName             = null;
	private String packagePath             = null;
	private String dollarMark              = null;
	private String sourcePath              = null;
	
	private String domain                  = null;
	private String website                 = null;
	private String applicationDomain       = null;
	
	private String pluginName;
	
	//private Button nextButton;
	
	private Jp2pContainerPropertySource properties;
	private String templateRoot;
	
	private Logger logger = Logger.getLogger( AbstractJp2pTemplateSection.class.getName() );

	protected AbstractJp2pTemplateSection( String templateRoot) {
		this.templateRoot = templateRoot;
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
		return new String[]{FOLDER_OSGI, FILE_OSGI_XML, FILE_JP2P_XML};
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
		createJP2PFolder( this.packageName, this.project, 0, monitor);
		createOSGIInf( this.packageName, this.project, 0, monitor);
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

	public static String getJP2PXML( String packageName, String name ){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		buffer.append("  <jp2p-container id=\""+ packageName + "\" name=\""+ name + "\" auto-start=\"true\">\n");
		buffer.append("    <properties>\n");
		buffer.append("      <bundle-id>org.condast.rdv</bundle-id>\n");
		buffer.append("      <home-folder>file:/C:/Users/HP/.jxse/"+ packageName + "</home-folder>\n");
		buffer.append("    </properties>\n");
		buffer.append("    <persistence-service context=\"chaupal\"/>\n");
		buffer.append("    <network-manager id=\""+ packageName + "\".context\" name=\"Rdv\" clear-config=\"true\">\n");
		buffer.append("      <properties>\n");
		buffer.append("        <config-mode>RENDEZVOUS</config-mode>\n");
		buffer.append("          <peer-id create=\"true\" persist=\"true\"/>\n");
		buffer.append("        </properties>\n");
		buffer.append("      <network-configurator id=\""+ packageName + ".networkmanager\" name=\"Rdv Peer\">\n");
		buffer.append("        <properties>\n");
		buffer.append("          <tcp>\n");
		buffer.append("            <enabled>true</enabled>\n");
		buffer.append("            <port>9715</port>\n");
		buffer.append("            <incoming-status>true</incoming-status>\n");
		buffer.append("            <outgoing-status>true</outgoing-status>\n");
		buffer.append("          </tcp>\n");
		buffer.append("          <http>\n");
		buffer.append("            <enabled>true</enabled>\n");
		buffer.append("            <port>8081</port>\n");
		buffer.append("            <incoming-status>true</incoming-status>\n");
		buffer.append("            <outgoing-status>true</outgoing-status>\n");
		buffer.append("          </http>\n");
		buffer.append("          <multicast>\n");
		buffer.append("            <enabled>false</enabled>\n");
		buffer.append("          </multicast>\n");
		buffer.append("        </properties>\n");
		buffer.append("      </network-configurator>\n");
		buffer.append("    </network-manager>\n");
		buffer.append("</jp2p-container>\n");
		return buffer.toString();
	}

	public static int createJP2PFolder( String packageName, IProject project, int worked, IProgressMonitor monitor ){
		Logger logger = Logger.getLogger( AbstractJp2pTemplateSection.class.getName() );
		//XMLContainerBuilder builder = 	new XMLContainerBuilder(DS_MANIFEST_KEY, null, null);
		InputStream source = null;
		try{
			source = new ByteArrayInputStream( getJP2PXML( packageName, "RDV").getBytes()); 
			createFile(project, S_JP2P_INF + "/", S_JP2P_FILE, source, monitor);
			IOUtils.closeInputStream(source);
			monitor.worked( worked++);
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
	
	public static int createOSGIInf( String packageName, IProject project, int worked, IProgressMonitor monitor ){
		Logger logger = Logger.getLogger( AbstractJp2pTemplateSection.class.getName() );
		InputStream source = null;
		try{
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
