/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/

package org.chaupal.jp2p.ui.jxta.osgi.service;

import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.properties.IJp2pProperties;

import org.chaupal.jp2p.ui.property.IJp2pPropertySourceProvider;
import org.chaupal.jp2p.ui.jxta.provider.JxtaPropertySourceProvider;
import org.eclipselabs.osgi.ds.broker.service.AbstractPalaver;
import org.eclipselabs.osgi.ds.broker.service.AbstractProvider;

public class PropertySourceProvider extends AbstractProvider<String, IJp2pComponent<?>, IJp2pPropertySourceProvider<IJp2pProperties>>
{
	private static PropertySourceProvider attendee = new PropertySourceProvider();
	
	private PropertySourceProvider() {
		super( new PropertySourcePalaver());
	}
	
	public static PropertySourceProvider getInstance(){
		return attendee;
	}

	
	@Override
	protected void onDataReceived( IJp2pComponent<?> component ) {
		super.provide( new JxtaPropertySourceProvider( component ));
	}
	
	private static class PropertySourcePalaver extends AbstractPalaver<String>{

		protected PropertySourcePalaver() {
			super("PropertySourceMessage");
		}

		@Override
		public String giveToken() {
			return "PropertySourceToken";
		}

		@Override
		public boolean confirm(Object token) {
			return ( token instanceof String );
		}	
	}
}