/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.property;

import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.properties.IJp2pProperties;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class Jp2pComponentUIPropertySource<T extends Object> extends AbstractUIJp2pPropertySource<T> {

	public static final String S_PROPERTY_JXTA_COMPONENT_ID = "org.condast.jxta.service.component";
	public static final String S_PROPERTY_JXTA_COMPONENT_TEXT_ID = "org.condast.jxta.service.component.text";

	public static final String S_PROPERTY_JP2P_TEXT = "JP2P";
	public static final String S_PROPERTY_JTTA_SERVICE_COMPONENT_TEXT = "ServiceComponent";
	public static final String S_PROPERTY_TEXT = "Properties";

	public static final String S_MODULE_CATEGORY = "Module";

	public Jp2pComponentUIPropertySource( IJp2pComponent<T> component, String defaultText ) {
		super( component.getPropertySource());
	}

	@Override
	public Object getEditableValue() {
		return this;
	}


	@Override
	public IPropertyDescriptor onGetPropertyDescriptor(IJp2pProperties id) {
		return null;
	}
}
