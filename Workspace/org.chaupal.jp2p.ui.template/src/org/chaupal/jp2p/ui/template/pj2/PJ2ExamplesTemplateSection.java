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
package org.chaupal.jp2p.ui.template.pj2;

import net.jp2p.container.Jp2pContainerPropertySource;

import org.chaupal.jp2p.ui.template.project.AbstractJp2pTemplateSection;

/**
 * @author Marine
 *
 */
public class PJ2ExamplesTemplateSection extends AbstractJp2pTemplateSection {

	public static final String TEMPLATE_ROOT = "practical-jxta";
	
	public PJ2ExamplesTemplateSection() {
		super( TEMPLATE_ROOT );
		this.setPageCount(0);
	}

	@Override
	protected void onFillProperties(Jp2pContainerPropertySource properties) {
		// TODO Auto-generated method stub
		
	}
}
