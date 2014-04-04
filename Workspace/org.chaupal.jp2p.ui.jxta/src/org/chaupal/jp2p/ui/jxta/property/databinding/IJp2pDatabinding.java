/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.property.databinding;

import net.jp2p.container.properties.IJp2pValidator;

public interface IJp2pDatabinding<T, U> {

	public abstract void setValidator(IJp2pValidator<T, U> validator);

	/**
	 * Dispose the data binding
	 */
	public abstract void dispose();

}