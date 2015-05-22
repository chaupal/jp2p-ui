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

import net.jp2p.chaupal.activator.Jp2pBundleActivator;

import org.osgi.framework.*;
import $packageName$.service.ContainerObserver;

public class Activator extends Jp2pBundleActivator {

	public static final String S_PLUGIN_ID = "$packageName$";
	
	private static Jp2pBundleActivator activator;

	public Activator() {
		super( S_PLUGIN_ID);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.addObserver( new ContainerObserver() );
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
	
	public static Jp2pBundleActivator getDefault(){
		return activator;
	}
}