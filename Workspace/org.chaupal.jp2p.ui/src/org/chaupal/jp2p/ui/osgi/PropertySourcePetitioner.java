/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/

package org.chaupal.jp2p.ui.osgi;

import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.utils.Utils;

import org.chaupal.jp2p.ui.property.IJp2pPropertySourceProvider;
import org.eclipselabs.osgi.ds.broker.service.AbstractPalaver;
import org.eclipselabs.osgi.ds.broker.service.AbstractPetitioner;
import org.eclipselabs.osgi.ds.broker.service.ParlezEvent;

public class PropertySourcePetitioner extends AbstractPetitioner<String, IJp2pComponent<?>, IJp2pPropertySourceProvider<IJp2pProperties>>
{
	private static PropertySourcePetitioner attendee = new PropertySourcePetitioner();
	
	private PropertySourcePetitioner() {
		super( new PropertySourcePalaver());
	}
	
	public static PropertySourcePetitioner getInstance(){
		return attendee;
	}
	
	/**
	 * Get the property descriptor provider, or null if none was found
	 * @param componentName
	 * @return
	 */
	public IJp2pPropertySourceProvider<IJp2pProperties> getPropertyDescriptorProvider( String componentName ) {
		if( Utils.isNull( componentName ))
			return null;
		for( IJp2pPropertySourceProvider<IJp2pProperties> provider: super.getCollection() ){
			if( componentName.equals( provider.getComponentName()))
					return provider;
		}
		return null;
	}
	
	@Override
	protected void onDataReceived( ParlezEvent<IJp2pPropertySourceProvider<IJp2pProperties>> event ) {
		super.onDataReceived( event );
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