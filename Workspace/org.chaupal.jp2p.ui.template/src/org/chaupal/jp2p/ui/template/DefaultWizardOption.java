/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.template;

import net.jp2p.container.utils.StringStyler;

import org.chaupal.jp2p.ui.template.project.AbstractJp2pTemplateSection;
import org.eclipse.pde.ui.templates.TemplateOption;
import org.eclipse.swt.widgets.Composite;

public class DefaultWizardOption extends TemplateOption{
	
	public enum TemplateOptions{
		CUSTOM,
		SIMPLE_NETWORK_MANAGER,
		SIMPLE_RDV;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString());
		}
		
		public static String[] getValues(){
			String[] retval = new String[ values().length ];
			for( int i=0; i< values().length; i++ )
				retval[i] = values()[i].toString();
			return retval;
		}
	}

	public DefaultWizardOption( AbstractJp2pTemplateSection section, String name, String label) {
		super( section, name, label);
	}

	@Override
	public void createControl(Composite parent, int span) {
		// TODO Auto-generated method stub
		
	}
}