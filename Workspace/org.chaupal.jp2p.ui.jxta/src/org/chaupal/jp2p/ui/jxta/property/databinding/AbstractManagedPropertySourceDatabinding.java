/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.property.databinding;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import net.jp2p.container.properties.IJp2pValidator;
import net.jp2p.container.properties.IManagedPropertyListener;
import net.jp2p.container.properties.ManagedProperty;

public abstract class AbstractManagedPropertySourceDatabinding<T,U> implements
		IManagedPropertyListener<T, U>, SelectionListener, IJp2pDatabinding<T, U> {

	private ManagedProperty<T,U> source;
	
	public AbstractManagedPropertySourceDatabinding( ManagedProperty<T,U> source) {
		this.source = source;
	}

	/* (non-Javadoc)
	 * @see org.eclipselabs.jxse.ui.property.databinding.IJseDatabinding#setValidator(net.osgi.jxse.properties.IJxseValidator)
	 */
	@Override
	public void setValidator( IJp2pValidator<T,U> validator ){
		this.source.setValidator(validator);
	}
	
	protected ManagedProperty<T, U> getSource() {
		return source;
	}

	/* (non-Javadoc)
	 * @see org.eclipselabs.jxse.ui.property.databinding.IJseDatabinding#dispose()
	 */
	@Override
	public abstract void dispose();

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
