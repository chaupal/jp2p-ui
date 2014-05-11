/*******************************************************************************
 * Copyright 2014 Chaupal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *******************************************************************************/
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
