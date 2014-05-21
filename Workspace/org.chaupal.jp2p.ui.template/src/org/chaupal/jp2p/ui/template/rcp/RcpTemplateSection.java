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
package org.chaupal.jp2p.ui.template.rcp;

import org.chaupal.jp2p.ui.template.project.AbstractBundleTemplateSection;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.pde.internal.core.ibundle.IBundle;
import org.eclipse.pde.internal.core.ibundle.IBundlePluginModelBase;

/**
 * @author Marine
 *
 */
@SuppressWarnings("restriction")
public class RcpTemplateSection extends AbstractBundleTemplateSection {

	public static final String TEMPLATE_ROOT = "rcp";
	
	public RcpTemplateSection() {
		super( TEMPLATE_ROOT );
		this.setPageCount(0);
	}
	
	@Override
	protected void updateModel(IProgressMonitor monitor) throws CoreException {
		IBundlePluginModelBase mb = (IBundlePluginModelBase) model;
		IBundle bundle = mb.getBundleModel().getBundle();
		String value = bundle.getHeader(S_BUNDLE_SYMBOLIC_NAME ) + S_BUNDLE_SINGLETON;
		bundle.setHeader( S_BUNDLE_SYMBOLIC_NAME, value );
		super.updateModel(monitor);
	}

}