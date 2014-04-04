/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.property.descriptors;

import org.chaupal.jp2p.ui.celleditors.AbstractControlCellEditor;
import org.chaupal.jp2p.ui.celleditors.SpinnerCellEditor;
import org.chaupal.jp2p.ui.jxta.provider.ControlLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class SpinnerPropertyDescriptor extends AbstractControlPropertyDescriptor<Integer> {

	private int minValue, maxValue;
	
	private SpinnerCellEditor editor;
	
	public SpinnerPropertyDescriptor(Object id, String displayName, int minValue, int maxValue)
	{
		super(id, displayName );
		this.maxValue = minValue;
		this.maxValue = maxValue;
	}

	public SpinnerPropertyDescriptor(Object id, String displayName, int maxValue)
	{
		this( id, displayName, 0, maxValue );
	}

	public SpinnerPropertyDescriptor(Object id, String displayName )
	{
		this( id, displayName, 0, 9999 );
	}
	
	@Override
	protected AbstractControlCellEditor onCreatePropertyEditor(Composite parent) {
		this.editor = new SpinnerCellEditor(parent, minValue, maxValue, SWT.NONE );
		editor.setEnabled( super.isEnabled());
		return editor;
	}

	@Override
	public ILabelProvider getLabelProvider()
	{
		return new ControlLabelProvider( this );
	}
}
