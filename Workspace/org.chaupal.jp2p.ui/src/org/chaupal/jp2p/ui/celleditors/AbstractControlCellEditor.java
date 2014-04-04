/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.celleditors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractControlCellEditor extends CellEditor {

	private boolean enabled;

	/**
	 * Default CheckboxCellEditor style
	 */
	private static final int defaultStyle = SWT.NONE;

	/**
	 * Creates a new checkbox cell editor with no control
	 */
	public AbstractControlCellEditor()
	{
		setStyle(defaultStyle);
		this.enabled = true;
	}

	/**
	 * Creates a new checkbox cell editor parented under the given control. The cell editor value is a boolean value, which is initially <code>false</code>.
	 * Initially, the cell editor has no cell validator.
	 * 
	 * @param parent the parent control
	 */
	public AbstractControlCellEditor(Composite parent)
	{
		this(parent, defaultStyle);
	}

	/**
	 * Creates a new checkbox cell editor parented under the given control. The cell editor value is a boolean value, which is initially <code>false</code>.
	 * Initially, the cell editor has no cell validator.
	 * 
	 * @param parent the parent control
	 * @param style the style bits
	 */
	public AbstractControlCellEditor(Composite parent, int style)
	{
		super(parent, style);
		this.enabled = true;
	}

	public boolean isEnabled() {
		if( super.getControl() != null )
			return super.getControl().isEnabled();
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		if( super.getControl() != null )
			super.getControl().setEnabled(enabled);
		this.enabled = enabled;
	}
}
