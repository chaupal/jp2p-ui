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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.chaupal.jp2p.ui.template.Activator;
import org.chaupal.jp2p.ui.template.TemplateUtil;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.pde.core.plugin.IPluginReference;
import org.eclipse.pde.internal.core.ibundle.IBundle;
import org.eclipse.pde.internal.core.ibundle.IBundlePluginModelBase;
import org.eclipse.pde.ui.IFieldData;
import org.eclipse.pde.ui.templates.OptionTemplateSection;
import org.eclipse.pde.ui.templates.PluginReference;

/**
 * @author Marine
 *
 */
@SuppressWarnings("restriction")//Needed to use the IBundle interface
public class PJ2ExamplesTemplateSection extends OptionTemplateSection {

	public static final String TEMPLATE_ROOT = "practical-jxta";

	// key for hidden field
	public static final String KEY_DOLLAR_MARK            = "dollarMark";
	public static final String KEY_PACKAGE_PATH           = "packagePath";
	public static final String KEY_SOURCE_PATH            = "sourcePath";
	
	public static final String KEY_APPLICATION_DOMAIN = "applicationDomain";
	public static final String KEY_WEBSITE = "website";
	public static final String KEY_DOMAIN = "domain";
	
	public static final String FILE_OSGI_XML = "OSGI-INF/attendees.xml";
	public static final String FOLDER_OSGI = "OSGI-INF/";

	
	static final String JP2P_NET_JP2P_CONTAINER = "net.jp2p.container";
	static final String JP2P_NET_OSGI_JP2P_SERVICE = JP2P_NET_JP2P_CONTAINER + ".service";
	static final String JP2P_NET_OSGI_JP2P_COMPONENT = JP2P_NET_JP2P_CONTAINER +".component";
	static final String JP2P_NET_OSGI_JP2P_CONTEXT = JP2P_NET_JP2P_CONTAINER + ".context";
	static final String JP2P_NET_OSGI_JP2P_PROPERTIES = JP2P_NET_JP2P_CONTAINER + ".properties";
	static final String JP2P_NET_OSGI_JP2P_UTILS = JP2P_NET_JP2P_CONTAINER + ".utils";

	static final String JP2P_NET_JP2P_COMPATIBILITY = "net.jp2p.jxse.compatibility";
	static final String JP2P_NET_JP2P_COMPATIBILITY_ACTIVATOR = JP2P_NET_JP2P_COMPATIBILITY + ".activator";
	static final String JP2P_NET_JP2P_COMPATIBILITY_CONTAINER = JP2P_NET_JP2P_COMPATIBILITY + ".container";
	static final String JP2P_NET_JP2P_COMPATIBILITY_SERVICE = JP2P_NET_JP2P_COMPATIBILITY + ".service";
	static final String JP2P_NET_JP2P_COMPATIBILITY_UTILS = JP2P_NET_JP2P_COMPATIBILITY + ".utils";

	static final String NET_JXSE = "net.jxse";
	static final String NET_JXSE_CONFIGURATION = NET_JXSE + ".configuration";
	
	static final String NET_JXTA = "net.jxta";
	static final String NET_JXTA_DOCUMENT = NET_JXTA + ".document";
	static final String NET_JXTA_EXCEPTION = NET_JXTA + ".exception";
	static final String NET_JXTA_PSE = NET_JXTA + ".impl.membership.pse";
	static final String NET_JXTA_IMPL_PEERGROUP = NET_JXTA + ".impl.peergroup";
	static final String NET_JXTA_PEERGROUP = NET_JXTA + ".peergroup";
	static final String NET_JXTA_PLATFORM = NET_JXTA + ".platform";
	static final String NET_JXTA_PROTOCOL = NET_JXTA + ".protocol";
	static final String NET_JXTA_REDEZVOUS = NET_JXTA + ".rendezvous";
	static final String NET_JXTA_SERVICE = NET_JXTA + ".service";
	static final String ORG_ECLIPSELABS_OSGI_BROKER = "org.eclipselabs.osgi.ds.broker";
	static final String ORG_ECLIPSELABS_OSGI_BROKER_SERVICE = ORG_ECLIPSELABS_OSGI_BROKER + ".service";
	
	static final String ORG_OSGI_FRAMEWORK = "org.osgi.framework;version=\"1.3.0\"";

	public static final String S_META_INF = "META-INF/";
	public static final String S_MANIFEST_MF = "MANIFEST.MF";

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
	
	private Logger logger = Logger.getLogger( PJ2ExamplesTemplateSection.class.getName() );

	public PJ2ExamplesTemplateSection() {
		this.setPageCount(0);
	}

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
	}

	public String getPluginName() {
		return pluginName;
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

	/**
	 * @param sourcePath the sourcePath to set
	 */
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	@Override
	public IPluginReference[] getDependencies(String schemaVersion) {
		ArrayList<PluginReference> result = new ArrayList<PluginReference>();
		return result.toArray(
				new IPluginReference[result.size()]);
	}
	
	@Override
	protected void updateModel(IProgressMonitor monitor) throws CoreException {
		logger.info("Updating model");
		IBundlePluginModelBase mb = (IBundlePluginModelBase) model;
		IBundle bundle = mb.getBundleModel().getBundle();
		bundle.setHeader( DS_MANIFEST_KEY, FILE_OSGI_XML );
		//AbstractJp2pTemplateSection.createOSGIInf( this.packageName, this.project, 0, monitor);
	}
}
