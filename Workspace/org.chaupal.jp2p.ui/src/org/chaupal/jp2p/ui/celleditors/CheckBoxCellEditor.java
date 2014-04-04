/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.celleditors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class CheckBoxCellEditor extends AbstractControlCellEditor {

	/**
	 * The checkbox value.
	 */
	/* package */
	boolean value = false;

	/**
	 * Default CheckboxCellEditor style
	 */
	private static final int defaultStyle = SWT.NONE;

	/**
	 * Creates a new checkbox cell editor with no control
	 */
	public CheckBoxCellEditor()
	{
		setStyle(defaultStyle);
	}

	/**
	 * Creates a new checkbox cell editor parented under the given control. The cell editor value is a boolean value, which is initially <code>false</code>.
	 * Initially, the cell editor has no cell validator.
	 * 
	 * @param parent the parent control
	 */
	public CheckBoxCellEditor(Composite parent)
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
	public CheckBoxCellEditor(Composite parent, int style)
	{
		super(parent, style);
	}

	@Override
	protected Control createControl(final Composite parent)
	{
		Canvas canvas = new Canvas(parent, SWT.NO_BACKGROUND); // transparent
		canvas.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseUp(MouseEvent e)
			{
				toggle();
			}
		});
		return canvas;
	}

	/**
	 * The object is being passed in, return the index to be used in the editor.
	 * 
	 * It should return sNoSelection if the value can't be converted to a index. The errormsg will have already been set in this case.
	 */

	@Override
	public void activate()
	{
		toggle();
	}

	protected void toggle()
	{
		value = !value;
		markDirty();
		fireApplyEditorValue();
	}


	/**
	 * The <code>CheckboxCellEditor</code> implementation of this <code>CellEditor</code> framework method returns the checkbox setting wrapped as a
	 * <code>Boolean</code>.
	 * 
	 * @return the Boolean checkbox value
	 */
	@Override
	protected Object doGetValue()
	{
		return value ? Boolean.TRUE : Boolean.FALSE;
	}

	/*
	 * (non-Javadoc) Method declared on CellEditor.
	 */
	@Override
	protected void doSetFocus()
	{
		// Ignore
	}

	/**
	 * The <code>CheckboxCellEditor</code> implementation of this <code>CellEditor</code> framework method accepts a value wrapped as a
	 * <code>Boolean</code> .
	 * 
	 * @param val a Boolean value
	 */
	@Override
	protected void doSetValue(Object val)
	{
		this.value = Boolean.TRUE.equals(val);
	}
}
