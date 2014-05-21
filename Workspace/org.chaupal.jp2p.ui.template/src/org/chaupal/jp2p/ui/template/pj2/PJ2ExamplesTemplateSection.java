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

import org.chaupal.jp2p.ui.template.project.AbstractBundleTemplateSection;

/**
 * @author Marine
 *
 */
public class PJ2ExamplesTemplateSection extends AbstractBundleTemplateSection {

	public static final String TEMPLATE_ROOT = "practical-jxta";
	public static final String S_PJ2 = "Practical JXTA";
	
	public PJ2ExamplesTemplateSection() {
		super( TEMPLATE_ROOT );
		this.setPageCount(0);
	}

	protected String getJP2PXML(){
		//StringBuffer buffer = new StringBuffer();
		//buffer.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		//buffer.append("<jp2p-container id=\""+ packageName + "\" name=\""+ name + "\" auto-start=\"true\">\n");
		//buffer.append("  <properties>\n");
		//buffer.append("    <bundle-id>org.condast.rdv</bundle-id>\n");
		//buffer.append("    <home-folder>file:/C:/Users/HP/.jxse/"+ packageName + "</home-folder>\n");
		//buffer.append("  </properties>\n");
		//buffer.append("  <persistence-service context=\"chaupal\"/>\n");
		//buffer.append("</jp2p-container>\n");
		return null;//buffer.toString();
	}

}
