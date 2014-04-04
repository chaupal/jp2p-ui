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
package org.chaupal.jp2p.ui.template.rdv;

import org.chaupal.jp2p.ui.template.config.JxseBundleWizard;
import org.eclipse.pde.ui.templates.ITemplateSection;

/**
 * @author Marine
 *
 */
public class JxseRdvBundleWizard extends JxseBundleWizard{

	@Override
	public ITemplateSection[] createTemplateSections() {
		ITemplateSection[] sections = new ITemplateSection[1];
		RdvTemplateSection acs1 = new RdvTemplateSection();
		sections[0] = acs1;
		return sections;
	}
}
