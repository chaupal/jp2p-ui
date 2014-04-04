/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.property.databinding;

import net.jp2p.container.properties.IJp2pPropertySource;
import net.jp2p.container.properties.IJp2pWritePropertySource;
import net.jp2p.container.properties.ManagedProperty;
import net.jp2p.container.properties.ManagedPropertyEvent;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Spinner;

public class SpinnerDataBinding<T extends Enum<T>> extends AbstractManagedPropertySourceDatabinding<T, Integer>{

	private Spinner spinner;
	private ManagedProperty<T, Integer> minProp, maxProp;
	
	public SpinnerDataBinding(ManagedProperty<T, Integer> managedProperty, Spinner spinner, int min, int max ) {
		super(managedProperty);
		spinner.addSelectionListener(this);
		spinner.setData(this);
		spinner.setMinimum( min );
		spinner.setMaximum(max);
		spinner.setSelection(managedProperty.getValue());
	}

	public SpinnerDataBinding(ManagedProperty<T, Integer> managedProperty, Spinner spinner, int max ) {
		this( managedProperty, spinner, 0, max );
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SpinnerDataBinding( T id, IJp2pPropertySource source, Spinner spinner, int min, int max) {
		this(source.getManagedProperty(id), spinner, min, max);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SpinnerDataBinding( T id, IJp2pWritePropertySource source, Spinner spinner, T id_min, T id_max ) {
		this(source.getManagedProperty(id), spinner, 
				( int )source.getOrCreateManagedProperty(id_min, 0, false).getValue(), 
				( int )source.getOrCreateManagedProperty(id_max, 65535, false).getValue());
		this.minProp = source.getManagedProperty(id_min);
		//TODO CP: Rethink
		//if( this.minProp != null )
		//	this.minProp.addPropertyListener(this);
		//this.maxProp = source.getManagedProperty(id_max);
		//if( this.maxProp != null )
		//	this.maxProp.addPropertyListener(this);
	}

	@Override
	public void notifyValueChanged(ManagedPropertyEvent<T, Integer> event) {
		if( event.getId().equals( super.getSource().getKey() ))
			this.spinner.setSelection( event.getValue() );
		if(( this.minProp != null ) && ( event.getId().equals( this.minProp.getKey() )))
			this.spinner.setMinimum( event.getValue() );
		if(( this.maxProp != null ) && ( event.getId().equals( this.maxProp.getKey() )))
			this.spinner.setMaximum( event.getValue() );
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		Spinner spinner = ( Spinner )e.widget;
		super.getSource().setValue( spinner.getSelection() );
	}

	@Override
	public void dispose() {
		this.spinner.removeSelectionListener(this);
	}
}
