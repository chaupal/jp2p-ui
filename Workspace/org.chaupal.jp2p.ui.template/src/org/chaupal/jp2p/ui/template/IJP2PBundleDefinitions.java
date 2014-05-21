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
 * Contributors:
 *     Kees Pieters - initial API and implementation
 *******************************************************************************/
package org.chaupal.jp2p.ui.template;

public interface IJP2PBundleDefinitions {

	static final String S_PROVIDER = "Project Chaupal (http://www.chaupal.org)";
	
	static final String JP2P_NET_JP2P_CONTAINER = "net.jp2p.container";
	static final String JP2P_NET_JP2P_CONTAINER_ACTIVATOR = JP2P_NET_JP2P_CONTAINER + ".activator";
	static final String JP2P_NET_JP2P_CONTAINER_SERVICE = JP2P_NET_JP2P_CONTAINER + ".service";
	static final String JP2P_NET_JP2P_CONTAINER_COMPONENT = JP2P_NET_JP2P_CONTAINER +".component";
	static final String JP2P_NET_JP2P_CONTAINER_CONTEXT = JP2P_NET_JP2P_CONTAINER + ".context";
	static final String JP2P_NET_JP2P_CONTAINER_PROPERTIES = JP2P_NET_JP2P_CONTAINER + ".properties";
	static final String JP2P_NET_JP2P_CONTAINER_UTILS = JP2P_NET_JP2P_CONTAINER + ".utils";

	static final String JP2P_NET_JP2P_COMPATIBILITY = "net.jp2p.jxse.compatibility";
	static final String JP2P_NET_JP2P_COMPATIBILITY_ACTIVATOR = JP2P_NET_JP2P_COMPATIBILITY + ".activator";
	static final String JP2P_NET_JP2P_COMPATIBILITY_CONTAINER = JP2P_NET_JP2P_COMPATIBILITY + ".container";
	static final String JP2P_NET_JP2P_COMPATIBILITY_SERVICE = JP2P_NET_JP2P_COMPATIBILITY + ".service";
	static final String JP2P_NET_JP2P_COMPATIBILITY_UTILS = JP2P_NET_JP2P_COMPATIBILITY + ".utils";

	static final String JP2P_NET_JP2P_CHAUPAL = "net.jp2p.chaupal";
	static final String JP2P_NET_JP2P_CHAUPAL_ACTIVATOR = JP2P_NET_JP2P_CHAUPAL + ".activator";
	static final String JP2P_NET_JP2P_CHAUPAL_CONTAINER = JP2P_NET_JP2P_CHAUPAL + ".container";
	static final String JP2P_NET_JP2P_CHAUPAL_SERVICE = JP2P_NET_JP2P_CHAUPAL + ".service";
	static final String JP2P_NET_JP2P_CHAUPAL_UTILS = JP2P_NET_JP2P_CHAUPAL + ".utils";

	static final String JP2P_NET_JP2P_CHAUPAL_JXTA = JP2P_NET_JP2P_CHAUPAL + ".jxta";
	static final String JP2P_NET_JP2P_CHAUPAL_JXTA_ACTIVATOR = JP2P_NET_JP2P_CHAUPAL_JXTA + ".activator";

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
	
	static final String ORG_CHAUPAL_JP2P_UI_P2 = "org.chaupal.jp2p.ui.p2";
	static final String ORG_CHAUPAL_JP2P_UI_TRAY = "org.chaupal.jp2p.ui.tray";

	static final String ORG_CHAUPAL_JP2P_UI_JXTA = "org.chaupal.jp2p.ui.jxta";
	static final String ORG_CHAUPAL_JP2P_UI_JXTA_PROPERTY = ORG_CHAUPAL_JP2P_UI_JXTA + ".property";
	
	static final String ORG_ECLIPSE_CORE_RESOURCES = "org.eclipse.core.resources";
	static final String ORG_ECLIPSE_EQUINOX_APP = "org.eclipse.equinox.app;version=\"1.1.0\"";
	static final String ORG_ECLIPSE_EQUINOX_P2_CORE = "org.eclipse.equinox.p2.core;version=\"2.0.0\"";
	static final String ORG_ECLIPSE_EQUINOX_P2_UI = "org.eclipse.equinox.p2.ui;version=\"2.0.0\"";
	static final String ORG_ECLIPSE_JFACE_ACTION = "org.eclipse.jface.action";
	static final String ORG_ECLIPSE_JFACE_RESOURCE = "org.eclipse.jface.resource";
	static final String ORG_ECLIPSE_SWT = "org.eclipse.swt";
	static final String ORG_ECLIPSE_SWT_GRAPHICS = "org.eclipse.swt.graphics";
	static final String ORG_ECLIPSE_SWT_WIDGETS = "org.eclipse.swt.widgets";
	static final String ORG_ECLIPSE_UI = "org.eclipse.ui";
	static final String ORG_ECLIPSE_UI_APPLICATION = "org.eclipse.ui.application";

	static final String ORG_OSGI_FRAMEWORK = "org.osgi.framework;version=\"1.3.0\"";

	public static final String S_BUNDLE_SYMBOLIC_NAME = "Bundle-SymbolicName";
	public static final String S_BUNDLE_SINGLETON = ";singleton:=true";

	public static final String KEY_JP2P_CONTEXT = "Jp2pContext";
	public static final String FILE_JP2P_XML = "JP2P-INF/jp2p-1.0.0.xml";
	public static final String S_JP2P_INF = "JP2P-INF/";
	public static final String S_JP2P_FILE = "jp2p-1.0.0.xml";
	
	public static final String S_META_INF = "META-INF/";
	public static final String S_MANIFEST_MF = "MANIFEST.MF";

	
}
