/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.enm;

import org.chaupal.jp2p.ui.celleditors.IControlCellEditor;
import org.chaupal.jp2p.ui.property.descriptors.AbstractControlPropertyDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class EnumPropertyDescriptor extends AbstractControlPropertyDescriptor<Enum<?>> {

	private Enum<?>[] base;
	
	private EnumComboCellEditor editor;
	
	public EnumPropertyDescriptor(Object id, String displayName, Enum<?>[] base )
	{
		super(id, displayName );
		this.base = base;
	}

	@Override
	protected IControlCellEditor onCreatePropertyEditor(Composite parent) {
		this.editor = new EnumComboCellEditor(parent, base, SWT.NONE );
		editor.setEnabled( super.isEnabled());
		return editor;
	}

	@Override
	public ILabelProvider getLabelProvider()
	{
		return new EnumLabelProvider<Enum<?>>( base);
	}
}
