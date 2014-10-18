/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.property.descriptors;

import org.chaupal.jp2p.ui.celleditors.AbstractControlCellEditor;
import org.chaupal.jp2p.ui.celleditors.TextBoxCellEditor;
import org.chaupal.jp2p.ui.provider.ControlLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class TextBoxPropertyDescriptor extends AbstractControlPropertyDescriptor<String> {

	private TextBoxCellEditor editor;
	
	public TextBoxPropertyDescriptor(Object id, String displayName )
	{
		super(id, displayName );
	}

	
	@Override
	protected AbstractControlCellEditor onCreatePropertyEditor(Composite parent) {
		this.editor = new TextBoxCellEditor(parent, super.getValue(), SWT.NONE );
		editor.setEnabled( super.isEnabled());
		return editor;
	}

	@Override
	public ILabelProvider getLabelProvider()
	{
		return new ControlLabelProvider( this );
	}
}
