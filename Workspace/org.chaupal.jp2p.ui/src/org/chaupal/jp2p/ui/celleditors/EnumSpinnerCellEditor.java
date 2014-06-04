/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.celleditors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.Composite;

public class EnumSpinnerCellEditor extends SpinnerCellEditor {

	private Enum<?>[] values;

	/**
	 * Creates a new checkbox cell editor with no control
	 */
	public EnumSpinnerCellEditor()
	{
		super();
	}

	/**
	 * Creates a new checkbox cell editor with no control
	 */
	public EnumSpinnerCellEditor( Composite parent, int style )
	{
		super( parent, style );
	}

	/**
	 * Creates a new checkbox cell editor with no control
	 */
	public EnumSpinnerCellEditor( Composite parent, Enum<?>[] values, int style )
	{
		super( parent, 0, values.length, style );
		this.values = values;
	}

	public void setValues( Enum<?>[] values){
		this.values = values;
		super.setMinValue(0);
		super.setMaxValue( values.length);
	}
	
	/**
	 * The <code>CheckboxCellEditor</code> implementation of this <code>CellEditor</code> framework method returns the checkbox setting wrapped as a
	 * <code>Boolean</code>.
	 * 
	 * @return the Boolean checkbox value
	 */
	@Override
	protected Enum<?> doGetValue()
	{
		if( super.getSpinner() == null )
			return values[0];
		return values[ super.getSpinner().getSelection()];
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
		Assert.isTrue(( val != null ) && ( val instanceof Enum<?> ));
		super.doSetValue(((Enum<?>)val).ordinal() );
	}
}
