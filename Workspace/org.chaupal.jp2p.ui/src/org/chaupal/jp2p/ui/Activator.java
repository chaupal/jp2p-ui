/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui;

import org.chaupal.jp2p.ui.osgi.Jp2pContainerService;
import org.chaupal.jp2p.ui.refresh.RefreshService;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;


public class Activator implements BundleActivator {

	public static final String BUNDLE_ID = "org.chaupal.jp2p.ui";
	
	private static Activator plugin;
	private ServiceTracker<BundleContext,LogService> logServiceTracker;
	private static LogService logService;
	private static Jp2pContainerService<Object> containerService;
	
	private static RefreshService refreshService;
	
	
	@Override
	public void start(BundleContext context) throws Exception {
		plugin = this;
		// create a tracker and track the log service
		logServiceTracker = 
			new ServiceTracker<BundleContext,LogService>(context, LogService.class.getName(), null);
		logServiceTracker.open();
		
		// grab the service
		logService = logServiceTracker.getService();

		if(logService != null)
			logService.log(LogService.LOG_INFO, "Logging service started");
		
		//Collects the containers that are displayed in the UI
		containerService = new Jp2pContainerService<Object>();
		containerService.start( context);
		
		//Provides a refresh every second
		refreshService = new RefreshService( RefreshService.TIME_OUT );
		refreshService.start(context);
		context.registerService( CommandProvider.class, refreshService, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if( refreshService != null ){
			refreshService.stop(context);
		}
		if( containerService != null ){
			containerService.finalise();
		}
		if(logService != null)
			logService.log(LogService.LOG_INFO, "Logging service Stopped");
		
		// close the service tracker
		logServiceTracker.close();
		logServiceTracker = null;
		plugin = null;
	}	
	
	public static LogService getLog(){
		return logService;
	}
	
	public static Activator getDefault(){
		return plugin;
	}
	
	/**
	 * Get the container service
	 * @return
	 */
	public static Jp2pContainerService<Object> getJp2pContainerService(){
		return containerService;
	}
}
