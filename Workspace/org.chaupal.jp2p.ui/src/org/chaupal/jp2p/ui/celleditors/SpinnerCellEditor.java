/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.celleditors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SpinnerCellEditor extends AbstractControlCellEditor {

	/**
	 * The values.
	 */
	private Spinner spinner;
	
	/**
	 * Default CheckboxCellEditor style
	 */
	protected static final int defaultStyle = SWT.NONE;

	/**
	 * Creates a new checkbox cell editor with no control
	 */
	public SpinnerCellEditor()
	{
		setStyle(defaultStyle);
	}

	/**
	 * Creates a new checkbox cell editor parented under the given control. The cell editor value is a boolean value, which is initially <code>false</code>.
	 * Initially, the cell editor has no cell validator.
	 * 
	 * @param parent the parent control
	 */
	public SpinnerCellEditor(Composite parent)
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
	public SpinnerCellEditor(Composite parent, int style)
	{
		super(parent, style);
	}


	/**
	 * Creates a new checkbox cell editor with no control
	 */
	public SpinnerCellEditor( Composite parent, int minValue, int maxValue, int style )
	{
		super( parent, style);
		if( this.spinner == null )
			return;
		this.spinner.setMinimum( minValue );
		this.spinner.setMaximum( maxValue );
	}

	
	protected Spinner getSpinner() {
		return spinner;
	}

	/**
	 * Creates a new checkbox cell editor with no control
	 */
	public SpinnerCellEditor( Composite parent, int maxValue, int style )
	{
		this( parent, 0, maxValue, style );
	}

	@Override
	public Control createControl(final Composite parent)
	{
		this.spinner = new Spinner( parent, SWT.NONE );
		this.spinner.setEnabled( super.isEnabled());
		spinner.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e) {
				Spinner sp = ( Spinner )e.getSource();
				doSetValue( sp.getSelection() );
				super.widgetSelected(e);
			}
		});
		return spinner;
	}

	protected void setMinValue( int value ){
		this.spinner.setMinimum(value);
	}

	protected void setMaxValue( int value ){
		this.spinner.setMaximum(value);
	}

	/**
	 * The object is being passed in, return the index to be used in the editor.
	 * 
	 * It should return sNoSelection if the value can't be converted to a index. The errormsg will have already been set in this case.
	 */

	@Override
	public void activate()
	{
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
		if( this.spinner == null )
			return -1;
		return this.spinner.getSelection();
	}

	/*
	 * (non-Javadoc) Method declared on CellEditor.
	 */
	@Override
	protected void doSetFocus()
	{
		this.spinner.setFocus();
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
		Assert.isTrue(( val != null ) && ( val instanceof Integer ));
		if( this.spinner == null )
			return;
		this.spinner.setSelection((int)val );
	}
}
