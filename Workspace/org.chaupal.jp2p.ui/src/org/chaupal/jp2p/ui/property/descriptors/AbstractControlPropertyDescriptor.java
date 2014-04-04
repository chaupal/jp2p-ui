/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.property.descriptors;

import org.chaupal.jp2p.ui.celleditors.AbstractControlCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public abstract class AbstractControlPropertyDescriptor<T extends Object> extends PropertyDescriptor {

	private boolean enabled;
	private AbstractControlCellEditor editor;
	
	private T value;

	public AbstractControlPropertyDescriptor( Object id, String displayName, boolean enabled)
	{
		super( id, displayName );
		this.enabled = enabled;
	}

	public AbstractControlPropertyDescriptor( Object id, String displayName )
	{
		this( id, displayName, true );
	}

	/**
	 * should be used to create the cell editor instead of createPropertyEditor
	 * @param parent
	 * @return
	 */
	protected abstract AbstractControlCellEditor onCreatePropertyEditor( Composite parent );
	
	@Override
	public CellEditor createPropertyEditor(Composite parent)
	{
		this.editor = this.onCreatePropertyEditor(parent);
		editor.setEnabled(enabled);
		return editor;
	}

	
	protected T getValue() {
		return value;
	}

	protected void setValue( T value) {
		this.value = value;
	}

	public boolean isEnabled() {
		if( this.editor != null )
			return this.editor.isEnabled();
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		if( this.editor != null )
			this.editor.setEnabled(enabled);
		this.enabled = enabled;
	}
}
