/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.osgi;

import org.eclipselabs.osgi.ds.broker.service.AbstractAttendeeProviderComponent;

public class OsgiComponent extends AbstractAttendeeProviderComponent{

	MessageBoxProvider mp = MessageBoxProvider.getInstance();
	
	@Override
	protected void initialise() {
		super.addAttendee( Jp2pContainerPetitioner.getInstance() );
		super.addAttendee( PropertySourcePetitioner.getInstance() );
		super.addAttendee( mp );
	}

	@Override
	protected void finalise() {
		Jp2pContainerPetitioner.getInstance().finalise();
		mp.finalise();	
		super.finalise();
	}
}
