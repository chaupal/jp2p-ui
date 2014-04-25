/*******************************************************************************
 * Copyright (c) 2013 Condast.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Condast - initial API and implementation
 ******************************************************************************/
package $packageName$;

import net.jp2p.container.IJp2pContainer;
import net.jp2p.jxse.compatibility.activator.AbstractJp2pBundleActivator;

import $packageName$.examples.ServiceContextProvider;
import $packageName$.examples.ServiceContextProvider.Examples;

import org.osgi.framework.BundleContext;

public class Activator extends AbstractJp2pBundleActivator<IJp2pContainer> {

	public static final String S_PLUGIN_ID = "$packageName$";
	
	public Activator() {
		super( S_PLUGIN_ID);
	}

	private static Activator activator;

	public static Activator getDefault() {
		return activator;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		activator = this;
		super.start(bundleContext);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		activator = null;
		super.stop(bundleContext);
	}
	
	@Override
	protected IJp2pContainer onCreateContainer() {
		 IJp2pContainer container = ServiceContextProvider.getExample( Examples.EXAMPLE_100 );
		 return container;
	}
}
