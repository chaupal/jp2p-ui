/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.property;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class Jp2pComponentPropertyDescriptor extends PropertyDescriptor {

	public Jp2pComponentPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
		super.setLabelProvider( new LabelProvider());
	}	
}