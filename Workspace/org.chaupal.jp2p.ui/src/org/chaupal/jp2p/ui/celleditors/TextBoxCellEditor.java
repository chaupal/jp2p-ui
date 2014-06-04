/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.celleditors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class TextBoxCellEditor extends AbstractControlCellEditor {

	/**
	 * The values.
	 */
	private Text textBox;
	
	/**
	 * Default CheckboxCellEditor style
	 */
	private static final int defaultStyle = SWT.NONE;

	/**
	 * Creates a new checkbox cell editor with no control
	 */
	public TextBoxCellEditor()
	{
		setStyle(defaultStyle);
	}

	/**
	 * Creates a new checkbox cell editor parented under the given control. The cell editor value is a boolean value, which is initially <code>false</code>.
	 * Initially, the cell editor has no cell validator.
	 * 
	 * @param parent the parent control
	 */
	public TextBoxCellEditor(Composite parent)
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
	public TextBoxCellEditor(Composite parent, int style)
	{
		super(parent, style);
	}


	/**
	 * Creates a new checkbox cell editor with no control
	 */
	public TextBoxCellEditor( Composite parent, String value, int style )
	{
		super( parent, style);
		if(( this.textBox == null ) || ( !super.isEnabled() ))
			return;
		this.textBox.setText( value );
	}

	@Override
	public Control createControl(final Composite parent)
	{
		this.textBox = new Text( parent, SWT.NONE );
		this.textBox.setEnabled( super.isEnabled());
		this.textBox.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e) {
				if( !isEnabled() )
					return;
				Text sp = ( Text )e.getSource();
				doSetValue( sp.getSelection() );
				super.widgetSelected(e);
			}
		});
		return this.textBox;
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
	public Object doGetValue()
	{
		if( this.textBox == null )
			return "";
		return this.textBox.getSelection();
	}

	/*
	 * (non-Javadoc) Method declared on CellEditor.
	 */
	@Override
	public void doSetFocus()
	{
		this.textBox.setFocus();
	}

	/**
	 * The <code>CheckboxCellEditor</code> implementation of this <code>CellEditor</code> framework method accepts a value wrapped as a
	 * <code>Boolean</code> .
	 * 
	 * @param val a Boolean value
	 */
	@Override
	public void doSetValue(Object val)
	{
		//Assert.isTrue(( val != null ) && ( val instanceof Integer ));
		if(( this.textBox == null ) || ( val == null ))
			return;
		this.textBox.setText( val.toString() );
	}
}
