/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.socket;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import net.jxta.document.Advertisement;
import net.jxta.protocol.JxtaSocket;
import net.jxta.protocol.ModuleSpecAdvertisement;
import net.jp2p.jxta.advertisement.AdvertisementPropertySource.AdvertisementProperties;

import org.chaupal.jp2p.ui.jxta.advertisement.property.ModuleImplAdvPropertySource.ModuleImplAdvProperties;
import org.chaupal.jp2p.ui.jxta.advertisement.property.ModuleSpecAdvPropertySource.ModuleSpecAdvProperties;
import org.chaupal.jp2p.ui.jxta.property.AbstractJp2pUIPropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class SocketPropertySource extends AbstractJp2pUIPropertySource<Advertisement> {

	public static final String S_DEFAULT_NAME = "Default JP2P Advertisement";

	public SocketPropertySource( Advertisement source ) {
		super( source );
	}

	/**
	 * Provides an abstract description of the object, used for displays
	 */
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Object module = super.getModule();
		Field[] fields = module.getClass().getFields();
		Type type = fields[0].getGenericType();
		type.
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		if( module instanceof JxtaSocket ){
			JxtaSocket socket = new ModuleSpecAdvPropertySource((ModuleSpecAdvertisement) module );
			descriptors.addAll( Arrays.asList( msaps.getPropertyDescriptors()));
		}
		if( module instanceof JxtaSocket ){
			JxtaSocketPropertySource miaps = new JxtaSocketPropertySource((JxtaSocket) module );
			descriptors.addAll( Arrays.asList( miaps.getPropertyDescriptors()));
		}
		descriptors.addAll( Arrays.asList(super.getPropertyDescriptors( AdvertisementProperties.values())));
		descriptors.addAll( Arrays.asList(super.getPropertyDescriptors()));
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size() ]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		Advertisement advertisement = super.getModule();
		if( id instanceof ModuleSpecAdvProperties ){
			ModuleSpecAdvPropertySource msaps = new ModuleSpecAdvPropertySource((ModuleSpecAdvertisement) advertisement );
			return msaps.getPropertyValue(id);
		}
		if( id instanceof ModuleImplAdvProperties ){
			JxtaSocketPropertySource miaps = new JxtaSocketPropertySource((JxtaSocket) advertisement );
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
		return super.getPropertyValue(id);
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
			JxtaSocketPropertySource miaps = new JxtaSocketPropertySource((JxtaSocket) advertisement );
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
			JxtaSocketPropertySource miaps = new JxtaSocketPropertySource((JxtaSocket) advertisement );
			miaps.setPropertyValue(id, value);
			return;
		}
		super.setPropertyValue(id, value);	
	}
}