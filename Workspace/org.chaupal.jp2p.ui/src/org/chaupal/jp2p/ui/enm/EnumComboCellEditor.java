/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.enm;

import org.chaupal.jp2p.ui.celleditors.IControlCellEditor;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.widgets.Composite;

public class EnumComboCellEditor extends ComboBoxCellEditor implements IControlCellEditor{

	private Enum<?>[] values;

	/**
	 * Creates a new check box cell editor with no control
	 */
	public EnumComboCellEditor( Enum<?>[] values)
	{
		super();
		this.values = values;
	}

	/**
	 * Creates a new checkbox cell editor with no control
	 */
	public EnumComboCellEditor( Composite parent, int style )
	{
		super( parent, null, style );
	}

	/**
	 * Creates a new checkbox cell editor with no control
	 */
	public EnumComboCellEditor( Composite parent, Enum<?>[] values, int style )
	{
		super( parent, setItems( values ), style );
		this.values = values;
	}
	
	/**
	 * The <code>CheckboxCellEditor</code> implementation of this <code>CellEditor</code> framework method returns the check box setting wrapped as a
	 * <code>Boolean</code>.
	 * 
	 * @return the Boolean checkbox value
	 */
	@Override
	protected Enum<?> doGetValue()
	{
		return values[ (int) super.doGetValue()];
		//String value = (String) super.doGetValue();
		//for( Enum<?> enm: values  ){
		//	if( enm.name().equals( value ))
		//	return enm;
		//}
		//return null;
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
		Enum<?> enm = (Enum<?>) val;
		super.doSetValue( enm.ordinal() );
	}

	/**
	 * Set the items for the given values
	 * @param values
	 */
	public static String[] setItems( Object[] values ){
		int i=0;
		String[] items = new String[values.length ];
		for( Object value: values )
			items[i++] = value.toString();		
		return items;
	}

	@Override
	public boolean isEnabled() {
		return super.isActivated();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.getControl().setEnabled(enabled);
	}

}
