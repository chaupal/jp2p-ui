/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import net.jp2p.container.properties.AbstractJp2pPropertySource;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.properties.IJp2pPropertySource;
import net.jp2p.container.properties.ManagedProperty;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class Jp2pUIPropertySource implements IPropertySource {

	public static final String S_PROPERTY_JXTA_COMPONENT_ID = "org.condast.jxta.service.component";
	public static final String S_PROPERTY_JXTA_COMPONENT_TEXT_ID = "org.condast.jxta.service.component.text";

	public static final String S_PROPERTY_JP2P_TEXT = "JP2P";
	public static final String S_PROPERTY_JTTA_SERVICE_COMPONENT_TEXT = "ServiceComponent";
	public static final String S_PROPERTY_TEXT = "Properties";

	private String defaultText;
	private IJp2pPropertySource<IJp2pProperties> source;

	public Jp2pUIPropertySource( IJp2pPropertySource<IJp2pProperties> source, String defaultText ) {
		super();
		this.defaultText = defaultText;
		this.source = source;
	}

	
	public String getDefaultText() {
		return defaultText;
	}


	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		PropertyDescriptor descriptor = new Jp2pComponentPropertyDescriptor( S_PROPERTY_JXTA_COMPONENT_ID, S_PROPERTY_JTTA_SERVICE_COMPONENT_TEXT );
		descriptor.setCategory(S_PROPERTY_JP2P_TEXT);
		Iterator<?> iterator = this.source.propertyIterator();
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		descriptors.add( descriptor);
		Object key;
		String category = S_PROPERTY_JP2P_TEXT;
		String attribute;
		TextPropertyDescriptor textDescriptor; 
		while( iterator.hasNext() ){
			key = iterator.next();
			attribute = key.toString();
			String[] split = attribute.split("[.]");
			if( split.length > 1 ){
				category = split[0];
				attribute = attribute.replace(category + ".", "");
			}
			textDescriptor = new TextPropertyDescriptor( key, attribute);
			textDescriptor.setCategory( category);
			descriptors.add( textDescriptor);
		}
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size()]);
	}

	@Override
	public Object getPropertyValue(Object key) {
		IJp2pProperties id = AbstractJp2pPropertySource.convert( key );
		return this.source.getProperty( id );
	}

	@Override
	public boolean isPropertySet(Object key) {
		IJp2pProperties id = AbstractJp2pPropertySource.convert( key );
		ManagedProperty<IJp2pProperties, Object> prop = source.getManagedProperty(id);
		if( prop == null )
			return false;
		return prop.isDirty();	
	}

	/**
	 * Currently not needed, there is no editing of properties, so a reset is not needed
	 * @param id
	 * @param value
	 */
	@Override
	public void resetPropertyValue(Object key) {
		IJp2pProperties id = AbstractJp2pPropertySource.convert( key );
		ManagedProperty<IJp2pProperties, Object> prop = source.getManagedProperty(id);
		if( prop == null )
			return;
		prop.setValue( prop.getDefaultValue());
	}

	/**
	 * Currently not needed, there is no editing of properties
	 * @param id
	 * @param value
	 */
	@Override
	public void setPropertyValue(Object key, Object value) {
		IJp2pProperties id = AbstractJp2pPropertySource.convert( key );
		ManagedProperty<IJp2pProperties, Object> prop = source.getManagedProperty(id);
		if( prop == null )
			return;
		prop.setValue(value);
	}
}
