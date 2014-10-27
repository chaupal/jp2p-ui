/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.property;

import org.eclipse.ui.views.properties.IPropertySource;

public interface IJp2pPropertySourceProvider<T extends Object> {

	/**
	 * Get the bundle id for the component
	 * @return
	 */
	public String getBundleId();

	/**
	 * Get the component name
	 * @return
	 */
	public String getComponentName();
	
	/**
	 * Get the descriptor
	 * @return
	 */
	public IPropertySource getPropertySource();
}
