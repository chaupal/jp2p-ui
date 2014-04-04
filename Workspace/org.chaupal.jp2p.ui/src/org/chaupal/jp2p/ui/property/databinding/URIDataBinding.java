/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.property.databinding;

import java.net.URI;

import net.jp2p.container.properties.IJp2pWritePropertySource;
import net.jp2p.container.properties.ManagedProperty;
import net.jp2p.container.properties.ManagedPropertyEvent;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;

public class URIDataBinding<T extends Enum<T>> extends AbstractManagedPropertySourceDatabinding<T, URI>{

	private Text text;
	
	public URIDataBinding(ManagedProperty<T, URI> managedProperty, Text text) {
		super(managedProperty);
		text.addSelectionListener(this);
		text.setData(this);
		if(( managedProperty.getValue() != null ) && ( managedProperty.getValue().getPath() != null ))
			text.setText( managedProperty.getValue().getPath());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public URIDataBinding( T id, IJp2pWritePropertySource source, Text text) {
		this( source.getOrCreateManagedProperty(id, null, false), text);
		text.setData(this);
	}

	@Override
	public void notifyValueChanged(ManagedPropertyEvent<T, URI> event) {
		this.text.setText( event.getValue().getPath());
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		Text text = ( Text )e.widget;
		super.getSource().setValue( URI.create( text.getText() ));
	}

	@Override
	public void dispose() {
		this.text.removeSelectionListener(this);
	}
}
