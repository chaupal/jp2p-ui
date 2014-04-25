/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package $packageName$.service;

import net.jp2p.chaupal.service.Jp2pDSComponent;

import $packageName$.Activator;

/**
 * Makes the service container accessible for the IDE
 * @author keesp
 *
 */
public class OsgiComponent extends Jp2pDSComponent {

	public OsgiComponent() {
		super( Activator.getDefault() );
	}
}