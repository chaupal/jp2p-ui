/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.adapter;

import net.jp2p.container.component.IJp2pComponent;

import org.chaupal.jp2p.ui.osgi.PropertySourcePetitioner;
import org.chaupal.jp2p.ui.property.IJp2pPropertySourceProvider;
import org.chaupal.jp2p.ui.property.Jp2pComponentUIPropertySource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;

public class Jp2pAdapterFactory implements IAdapterFactory {

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		  if(adapterType != IPropertySource.class )
			  return null;
		  if( adaptableObject instanceof IJp2pComponent )
			  return this.getPropertySource(((IJp2pComponent) adaptableObject) );
		  return null;
	}

	/**
	 * Get the correct property source
	 * @param adaptableObject
	 * @return
	 */
	protected IPropertySource getPropertySource( IJp2pComponent<?> adaptableObject ){
		PropertySourcePetitioner petitioner = PropertySourcePetitioner.getInstance();
		petitioner.petition(adaptableObject );
		IJp2pPropertySourceProvider<?> psp = petitioner.getPropertyDescriptorProvider(adaptableObject.getPropertySource().getComponentName());
		if( psp != null ){
			IPropertySource ps = psp.getPropertySource();
			if( ps != null )
				return ps;
		}
		return new Jp2pComponentUIPropertySource( adaptableObject, Jp2pComponentUIPropertySource.S_PROPERTY_JP2P_TEXT );			
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class[] getAdapterList() {
		return new Class[]{IPropertySource.class };
	}

}
