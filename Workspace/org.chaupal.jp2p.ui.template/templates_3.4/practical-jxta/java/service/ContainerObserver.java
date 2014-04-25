/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package $packageName$.service;

import net.jp2p.container.component.ComponentChangedEvent;
import net.jp2p.container.component.IComponentChangedListener;

public class ContainerObserver implements IComponentChangedListener {

	public ContainerObserver() {
		System.out.println( this.getClass().getName() + ": " + "Starting to Observe.");
	}

	@Override
	public void notifyServiceChanged(ComponentChangedEvent event) {
		System.out.println( "Observing: " + this.getClass().getName() + ": " + event.toString());
	}
}