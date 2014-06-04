/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.view.advertisements;

import net.jxta.document.Advertisement;

import org.chaupal.jp2p.ui.jxta.advertisement.property.AdvertisementPropertySource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;

public class JxseAdvertisementAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		  if (adapterType== IPropertySource.class && adaptableObject instanceof Advertisement){
		      return new AdvertisementPropertySource((Advertisement) adaptableObject);
		    }
		    return null;	
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[]{IPropertySource.class };
	}
}
