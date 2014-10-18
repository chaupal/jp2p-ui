/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.property.descriptors;

import java.lang.reflect.Type;

import org.chaupal.jp2p.ui.celleditors.AbstractControlCellEditor;
import org.chaupal.jp2p.ui.celleditors.CheckBoxCellEditor;
import org.chaupal.jp2p.ui.celleditors.EnumSpinnerCellEditor;
import org.chaupal.jp2p.ui.celleditors.SpinnerCellEditor;
import org.chaupal.jp2p.ui.celleditors.TextBoxCellEditor;
import org.chaupal.jp2p.ui.property.ObjectProperty;
import org.chaupal.jp2p.ui.property.ObjectProperty.SupportedTypes;
import org.chaupal.jp2p.ui.provider.CheckBoxLabelProvider;
import org.chaupal.jp2p.ui.provider.ControlLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class CompositePropertyDescriptor extends AbstractControlPropertyDescriptor<Object> {

	private SupportedTypes type;
	
	public CompositePropertyDescriptor(Object id, String displayName, Type type )
	{
		super(id, displayName );
		this.type = ObjectProperty.getSupportedTypes( type );
	}

	
	@Override
	protected AbstractControlCellEditor onCreatePropertyEditor(Composite parent) {
		AbstractControlCellEditor editor = getCellEditor( this.type, parent, SWT.NONE );
		editor.setEnabled( super.isEnabled());
		return editor;
	}

	@Override
	public ILabelProvider getLabelProvider()
	{
		return getLabelProvider(this, type);
	}

	/**
	 * Convert the reflection type to a supported type
	 * @param type
	 * @return
	 */
	static final ILabelProvider getLabelProvider( IPropertyDescriptor descriptor, SupportedTypes type ){
		ILabelProvider provider;
		switch( type ){
		case BOOLEAN:
			provider = new CheckBoxLabelProvider();
			break;
		default:
			provider = new ControlLabelProvider( (IControlPropertyDescriptor<?>) descriptor );
			break;
		}
		return provider;
	}

	/**
	 * Convert the reflection type to a supported type
	 * @param type
	 * @return
	 */
	static final AbstractControlCellEditor getCellEditor( SupportedTypes type, Composite parent, int style ){
		AbstractControlCellEditor editor;
		switch( type ){
		case BOOLEAN:
			editor = new CheckBoxCellEditor( parent, style);
			break;
		case INTEGER:
			editor = new SpinnerCellEditor( parent, style );
			break;
		case ENUM:
			editor = new EnumSpinnerCellEditor( parent, style );
			break;
		default:
			editor = new TextBoxCellEditor( parent, style );
			break;
		}
		return editor;
	}

}
