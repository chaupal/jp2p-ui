/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package $packageName$;

import net.jp2p.chaupal.jxta.activator.Jp2pJxseBundleActivator;

import org.osgi.framework.*;
import $packageName$.service.ContainerObserver;

public class Activator extends Jp2pJxseBundleActivator {

	public static final String S_PLUGIN_ID = "$packageName$";
	
	private static Jp2pJxseBundleActivator activator;

	public Activator() {
		super( S_PLUGIN_ID);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.setObserver( new ContainerObserver() );
		super.start(bundleContext);
		activator = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
		activator = null;
	}
	
	public static Jp2pJxseBundleActivator getDefault(){
		return activator;
	}
}