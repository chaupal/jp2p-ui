/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.property.databinding;

import net.jp2p.container.properties.IJp2pPropertySource;
import net.jp2p.container.properties.ManagedProperty;
import net.jp2p.container.properties.ManagedPropertyEvent;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

public class BooleanDataBinding<T extends Object> extends AbstractManagedPropertySourceDatabinding<T, Boolean>{

	private Button button;
	
	public BooleanDataBinding(ManagedProperty<T, Boolean> source, Button button) {
		super(source);
		button.addSelectionListener(this);
		button.setSelection(source.getValue());
		button.setData(this);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BooleanDataBinding( T id, IJp2pPropertySource<T> source, Button button) {
		this((ManagedProperty)source.getManagedProperty(id), button);
	}

	@Override
	public void notifyValueChanged(ManagedPropertyEvent<T, Boolean> event) {
		this.button.setSelection( event.getValue());
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		Button button = ( Button )e.widget;
		super.getSource().setValue( button.getSelection() );
	}

	@Override
	public void dispose() {
		this.button.removeSelectionListener(this);
	}

}
