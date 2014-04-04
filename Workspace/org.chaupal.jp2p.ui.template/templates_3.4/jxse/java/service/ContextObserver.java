/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package $packageName$.service;

import net.osgi.jxse.builder.ICompositeBuilderListener;
import net.osgi.jxse.factory.ComponentFactoryEvent;

public class ContextObserver implements ICompositeBuilderListener {

	public ContextObserver() {
		System.out.println( this.getClass().getName() + ": " + "Starting to Observe.");
	}

	@Override
	public void notifyCreated(ComponentFactoryEvent event) {
		System.out.println( this.getClass().getName() + ": " + event.getFactoryEvent().toString() + "=>" + event.getFactory().getComponentName() );
	}
}
