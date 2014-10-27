/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.advertisement.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import net.jxta.document.Advertisement;
import net.jxta.protocol.ModuleImplAdvertisement;
import net.jxta.protocol.ModuleSpecAdvertisement;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.jxta.advertisement.AdvertisementPropertySource.AdvertisementProperties;

import org.chaupal.jp2p.ui.jxta.advertisement.property.ModuleImplAdvPropertySource.ModuleImplAdvProperties;
import org.chaupal.jp2p.ui.jxta.advertisement.property.ModuleSpecAdvPropertySource.ModuleSpecAdvProperties;
import org.chaupal.jp2p.ui.property.AbstractUIPropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class AdvertisementPropertySource extends AbstractUIPropertySource<Advertisement> {

	public static final String S_DEFAULT_NAME = "Default JP2P Advertisement";

	public AdvertisementPropertySource( Advertisement source ) {
		super( source );
	}

	/**
	 * Provides an abstract description of the object, used for displays
	 */
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Advertisement advertisement = super.getModule();
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		if( advertisement instanceof ModuleSpecAdvertisement ){
			ModuleSpecAdvPropertySource msaps = new ModuleSpecAdvPropertySource((ModuleSpecAdvertisement) advertisement );
			descriptors.addAll( Arrays.asList( msaps.getPropertyDescriptors()));
		}
		if( advertisement instanceof ModuleImplAdvertisement ){
			ModuleImplAdvPropertySource miaps = new ModuleImplAdvPropertySource((ModuleImplAdvertisement) advertisement );
			descriptors.addAll( Arrays.asList( miaps.getPropertyDescriptors()));
		}
		descriptors.addAll( super.getPropertyDescriptors( AdvertisementProperties.values()));
		descriptors.addAll( Arrays.asList(super.getPropertyDescriptors()));
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size() ]);
	}

	@Override
	public Object onGetPropertyValue( IJp2pProperties id) {
		Advertisement advertisement = super.getModule();
		if( id instanceof ModuleSpecAdvProperties ){
			ModuleSpecAdvPropertySource msaps = new ModuleSpecAdvPropertySource((ModuleSpecAdvertisement) advertisement );
			return msaps.getPropertyValue(id);
		}
		if( id instanceof ModuleImplAdvProperties ){
			ModuleImplAdvPropertySource miaps = new ModuleImplAdvPropertySource((ModuleImplAdvertisement) advertisement );
			return miaps.getPropertyValue(id);
		}
		if(!( id instanceof AdvertisementProperties ))
			return super.getPropertyValue(id);
		
		AdvertisementProperties property = ( AdvertisementProperties )id;
		/**
		case ID:
			return advertisement.getID();
		case NAME:
			return S_DEFAULT_NAME;
		case ADVERTISEMENT_TYPE:
			return advertisement.getAdvType();
		case ADV_TYPE:
			return advertisement.getAdvType();
		}
	*/
		return null;
	}

	/**
	 * Returns true if the given property can be modified
	 * @param id
	 * @return
	 */
	@Override
	public boolean isEditable( Object id ){
		Advertisement advertisement = super.getModule();
		if( id instanceof ModuleSpecAdvProperties ){
			ModuleSpecAdvPropertySource msaps = new ModuleSpecAdvPropertySource((ModuleSpecAdvertisement) advertisement );
			return msaps.isEditable(id);
		}
		if( id instanceof ModuleImplAdvProperties ){
			ModuleImplAdvPropertySource miaps = new ModuleImplAdvPropertySource((ModuleImplAdvertisement) advertisement );
			return miaps.isEditable(id);
		}
		return false;
	}

	/**
	 * Currently not needed, there is no editing of properties
	 * @param id
	 * @param value
	 */
	@Override
	public void setPropertyValue(Object id, Object value){
		Advertisement advertisement = super.getModule();
		if( id instanceof ModuleSpecAdvProperties ){
			ModuleSpecAdvPropertySource msaps = new ModuleSpecAdvPropertySource((ModuleSpecAdvertisement) advertisement );
			msaps.setPropertyValue(id, value );
			return;
		}
		if( id instanceof ModuleImplAdvProperties ){
			ModuleImplAdvPropertySource miaps = new ModuleImplAdvPropertySource((ModuleImplAdvertisement) advertisement );
			miaps.setPropertyValue(id, value);
			return;
		}
		super.setPropertyValue(id, value);	
	}
}