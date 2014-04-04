/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.property.descriptors;

import org.chaupal.jp2p.ui.celleditors.CheckBoxCellEditor;
import org.chaupal.jp2p.ui.jxta.provider.CheckBoxLabelProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class CheckBoxPropertyDescriptor extends PropertyDescriptor {

	static final CheckBoxLabelProvider LABEL_PROVIDER = new CheckBoxLabelProvider();

	public CheckBoxPropertyDescriptor(Object id, String displayName)
	{
		super(id, displayName);
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent)
	{
		return new CheckBoxCellEditor(parent);
	}

	@Override
	public ILabelProvider getLabelProvider()
	{
		return LABEL_PROVIDER;
	}
}
