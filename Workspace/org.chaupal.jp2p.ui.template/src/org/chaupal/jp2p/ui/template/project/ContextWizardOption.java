/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.template.project;

import net.jp2p.container.Jp2pContainerPropertySource;
import net.jp2p.container.utils.StringStyler;

import org.eclipse.pde.ui.templates.TemplateOption;
import org.eclipse.swt.widgets.Composite;

public class ContextWizardOption extends TemplateOption{
	
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
	private ContextView view;

	public ContextWizardOption( AbstractJxseBundleSection section, String name, String label) {
		super( section, name, label);
	}

	/**
	 * Get the template that is requested
	 * @return
	 */
	public TemplateOptions getTemplate(){
		return this.view.getTemplate();
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void createControl(Composite parent, int span ) {
		view = new ContextView( parent, span );
		//AbstractJxseBundleSection section = (AbstractJxseBundleSection) super.getSection();
		//view.addTemplateOptionListener( );
	}

	/**
	 * Initialise the view with the property source
	 * @param properties
	 */
	public void init( Jp2pContainerPropertySource properties ){
		view.init(properties);
	}

	/**
	 * Determines the flow of the wizard
	 * @return
	 */
	public TemplateOptions getTemplateOption(){
		return view.getSelectedOption();
	}

	/**
	 * Set the template option
	 * @param option
	 */
	public void setTemplateOption( TemplateOptions option ){
		this.view.setTemplateOption( option );
	}

	public boolean complete() throws Exception {
		return view.complete();
	}
}