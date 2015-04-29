/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.property.descriptors;

import org.chaupal.jp2p.ui.celleditors.CheckBoxCellEditor;
import org.chaupal.jp2p.ui.celleditors.IControlCellEditor;
import org.chaupal.jp2p.ui.provider.CheckBoxLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

public class CheckBoxPropertyDescriptor extends AbstractControlPropertyDescriptor<Boolean> {

	private static final CheckBoxLabelProvider labelProvider = new CheckBoxLabelProvider();
	
	private CheckBoxCellEditor editor;

	public CheckBoxPropertyDescriptor(Object id, String displayName)
	{
		super(id, displayName);
	}

	@Override
	public ILabelProvider getLabelProvider()
	{
		return labelProvider;
	}

	@Override
	protected IControlCellEditor onCreatePropertyEditor(Composite parent) {
		this.editor = new CheckBoxCellEditor(parent );
		editor.setEnabled( super.isEnabled());
		return editor;
	}
}
