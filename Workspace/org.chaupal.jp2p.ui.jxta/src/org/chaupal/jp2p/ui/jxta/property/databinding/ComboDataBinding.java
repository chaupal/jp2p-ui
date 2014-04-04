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
import net.jp2p.container.utils.Utils;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;

public class ComboDataBinding<T extends Object, U extends Enum<U>> extends AbstractManagedPropertySourceDatabinding<T, U>{

	private Combo combo;
	
	@SuppressWarnings("unchecked")
	public ComboDataBinding(ManagedProperty<T, Enum<U>> managedProperty, Combo combo) {
		super((ManagedProperty<T, U>) managedProperty);
		combo.addSelectionListener(this);
		combo.setData(this);
		combo.select( managedProperty.getValue().ordinal());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ComboDataBinding( T id, IJp2pPropertySource<T> source, Combo combo) {
		this(( ManagedProperty ) source.getManagedProperty(id), combo);
	}

	@Override
	public void notifyValueChanged(ManagedPropertyEvent<T, U> event) {
		this.combo.select( event.getValue().ordinal() );
	}

	@SuppressWarnings("unchecked")
	@Override
	public void widgetSelected(SelectionEvent e) {
		Combo combo = ( Combo )e.widget;
		String str = combo.getText();
		if( Utils.isNull(str)){
			super.getSource().setValue(null);
		}else{
			U value= (U) Enum.valueOf( super.getSource().getValue().getClass(), str.toUpperCase() );
			super.getSource().setValue( value );
		}
	}

	@Override
	public void dispose() {
		this.combo.removeSelectionListener(this);
	}
}
