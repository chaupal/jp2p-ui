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
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

import net.jp2p.container.utils.Utils;
import net.jp2p.container.utils.io.IOUtils;

import org.chaupal.jp2p.ui.template.IJP2PBundleDefinitions;
import org.chaupal.jp2p.ui.template.TemplateUtil;
import org.chaupal.jp2p.ui.util.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.pde.ui.IFieldData;

/**
 * @author Marine
 *
 */
public abstract class AbstractJp2pTemplateSection extends AbstractBundleTemplateSection implements IJP2PBundleDefinitions{
	
	private Logger logger = Logger.getLogger( AbstractJp2pTemplateSection.class.getName() );

	protected AbstractJp2pTemplateSection( String templateRoot ) {
		super( templateRoot );
		this.onFillProperties( super.getAttributes());
	}
	
	/**
	 * allow subclasses to fill the properties after initialisation
	 * @param properties
	 */
	protected abstract void onFillProperties( Map<String, String> attributes );
	
	@Override
	public void initializeFields(IFieldData data) {
		super.initializeFields(data);
		String id = data.getId();
		String packageName = TemplateUtil.getFormattedPackageName(id);
		Map<String, String> attributes = super.getAttributes();
		attributes.put( KEY_PACKAGE, packageName );
		attributes.put( KEY_BUNDLE_ID, packageName );
	}

	/* (non-Javadoc)
	 * @see org.eclipse.pde.ui.templates.ITemplateSection#getNewFiles()
	 */
	@Override
	public String[] getNewFiles() {
		return new String[]{FOLDER_OSGI, FILE_OSGI_XML, FILE_JP2P_XML};
	}

	
	@Override
	protected void updateModel(IProgressMonitor monitor) throws CoreException {
		logger.info("Updating model");
		super.updateModel(monitor);
		createJP2PFolder( this.project, 0, monitor);
	}
	
	protected abstract String getResourceLocation( );
	
	/**
	 * Create the configuration file for the JP2P-INF folder 
	 * @param packageName
	 * @param name
	 * @return
	 */
	/**
	 * Get the XML info from the given location
	 */
	protected String getJP2PXML(){
		InputStream in = null;
		String location = this.getResourceLocation();
		if( Utils.isNull( location ))
			return null;
		try{
			in = this.getClass().getResourceAsStream( location );
			return parse( in );
		}
		catch( Exception ex ){
			ex.printStackTrace();
		}
		finally{
			IOUtils.closeInputStream( in );
		}
		return null;
	}

	protected int createJP2PFolder( IProject project, int worked, IProgressMonitor monitor ){
		Logger logger = Logger.getLogger( AbstractJp2pTemplateSection.class.getName() );
		//XMLContainerBuilder builder = 	new XMLContainerBuilder(DS_MANIFEST_KEY, null, null);
		InputStream source = null;
		try{
			String str =  getJP2PXML();
			if( Utils.isNull(str))
				return worked;
			source = new ByteArrayInputStream( str.getBytes()); 
			createFile(project, S_JP2P_INF + "/", S_JP2P_FILE, source, monitor);
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
	
	/**
	 * Parse the given stream, and replace package names and bundle ids with the correct types
	 * @param urlStream
	 * @return
	 */
	public String parse( InputStream urlStream ){
		Scanner scanner = new Scanner( urlStream );
		StringBuffer buffer = new StringBuffer();
		try{
			String line = null;
			while( scanner.hasNextLine() ){
				line = scanner.nextLine();
				if( Utils.isNull( line ))
					continue;

				//First check for comments
				boolean comment = StringUtils.isComment( StringUtils.S_HASH, line ) | 
						StringUtils.isComment( StringUtils.S_DOUBLE_SLASH, line );
				if (comment )
					continue;
				try {
					Set<Map.Entry<String, String>> set = super.getAttributes().entrySet();
					Iterator<Map.Entry<String, String>> iterator = set.iterator();
					while( iterator.hasNext() ){
						Map.Entry<String, String> entry = iterator.next();
						if( line.contains( entry.getKey() ))
						line = line.replace( entry.getKey(), entry.getValue());
					}
					buffer.append( line + "\n");
				} catch (Exception allElse) {
					logger.severe( "Failed to register:" + line );
				}
			}
		}
		catch( Exception ex ){
			ex.printStackTrace();
		}
		finally{
			scanner.close();
		}
		String str = buffer.toString();
		if( Utils.isNull(str))
			return null;
		return str.substring(0, str.length() - 1 );
	}

	/**
	 * Create the OSGI-INF directory
	 * @param project
	 * @param worked
	 * @param monitor
	 * @return
	 */
	protected int createOSGIInf( IProject project, int worked, IProgressMonitor monitor ){
		Logger logger = Logger.getLogger( AbstractJp2pTemplateSection.class.getName() );
		InputStream source = null;
		try{
			String packageName = super.getAttributes().get( KEY_PACKAGE );
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
