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

import net.jp2p.chaupal.jxta.platform.activator.Jp2pCompatBundleActivator;
import net.jp2p.container.builder.IJp2pContainerBuilder;
import net.jp2p.container.properties.IJp2pDirectives.DeveloperModes;
import net.jxse.practical.jxta.PJ2Examples;
import net.jxse.practical.jxta.PJ2Examples.Examples;

import org.osgi.framework.BundleContext;

public class Activator extends AbstractJp2pBundleActivator<IJp2pContainer> {

	public static final String S_PLUGIN_ID = "$packageName$";
	
	public Activator() {
		super( S_BUNDLE_ID, PJ2Examples.getJxseCompatible( Examples.A_100_Starting_And_Stopping_JXTA ));
		activator = this;
	}
	
	@SuppressWarnings("unchecked")
	public static IJp2pContainerBuilder<Object> getDefault(){
		return (IJp2pContainerBuilder<Object>) activator;
	}
}
