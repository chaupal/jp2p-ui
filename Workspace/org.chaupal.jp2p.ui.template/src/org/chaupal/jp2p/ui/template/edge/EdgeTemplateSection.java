/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.template.edge;

import java.util.Map;

import org.chaupal.jp2p.ui.template.project.AbstractJp2pTemplateSection;
import org.eclipse.pde.ui.templates.ITemplateSection;

public class EdgeTemplateSection extends AbstractJp2pTemplateSection implements
		ITemplateSection {

	public static final String TEMPLATE_ROOT = "jxse";
	public static final String S_EDGE = "Anna the Edge Peer";
	public static final String S_RESOURCE_LOCATION = "/resources/edge.xml";
	
	public EdgeTemplateSection() {
		super( TEMPLATE_ROOT );
		this.setPageCount(0);
	}

	@Override
	protected void onFillProperties(Map<String, String> attributes) {
		attributes.put( KEY_NAME, S_EDGE );
	}

	@Override
	protected String getResourceLocation() {
		return S_RESOURCE_LOCATION;
	}
}