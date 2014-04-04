/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.template.project;

import net.jp2p.container.Jp2pContainerPropertySource;

public interface IPropertySourceView {

	/**
//	 * Get the property source for this view
	 * @return
	 */
	public abstract Jp2pContainerPropertySource getPropertySource();

	/**
	 * Complete the view by filling in the properties and directives
	 */
	public boolean complete() throws Exception;
}