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

import net.jp2p.chaupal.utils.Utils;
import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.properties.IJp2pProperties;

import org.chaupal.jp2p.ui.property.descriptors.TextBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class Jp2pComponentUIPropertySource<T extends Object> implements IJp2pUIPropertySource<T> {

	public static final String S_PROPERTY_JXTA_COMPONENT_ID = "org.condast.jxta.service.component";
	public static final String S_PROPERTY_JXTA_COMPONENT_TEXT_ID = "org.condast.jxta.service.component.text";

	public static final String S_PROPERTY_JP2P_TEXT = "JP2P";
	public static final String S_PROPERTY_JTTA_SERVICE_COMPONENT_TEXT = "ServiceComponent";
	public static final String S_PROPERTY_TEXT = "Properties";

	public static final String S_MODULE_CATEGORY = "Module";

	private String defaultText;
	private IJp2pComponent<T> component;

	public Jp2pComponentUIPropertySource( IJp2pComponent<T> component, String defaultText ) {
		super();
		this.defaultText = defaultText;
		this.component = component;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		PropertyDescriptor descriptor = new Jp2pComponentPropertyDescriptor( S_PROPERTY_JXTA_COMPONENT_ID, S_PROPERTY_JTTA_SERVICE_COMPONENT_TEXT );
		descriptor.setCategory(S_PROPERTY_JP2P_TEXT);
		Iterator<IJp2pProperties> iterator = this.component.getPropertySource().propertyIterator();
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		descriptors.add( descriptor);
		IJp2pProperties key;
		String attribute;
		TextBoxPropertyDescriptor textDescriptor; 
		while( iterator.hasNext() ){
			key = iterator.next();
			String category = component.getPropertySource().getCategory( key );
			attribute = key.toString();
			String[] split = attribute.split("[.]");
			if( split.length > 1 ){
				attribute = split[split.length - 1];
				if( net.jp2p.container.utils.Utils.isNull( category) )
					category = key.toString().replace( "." + attribute, "");
			}
			textDescriptor = new TextBoxPropertyDescriptor( key, attribute);
			textDescriptor.setEnabled(false);
			if( net.jp2p.container.utils.Utils.isNull( category ))
				category = S_PROPERTY_JP2P_TEXT;
			textDescriptor.setCategory( category);
			descriptors.add( textDescriptor);
		}
		SimpleUIPropertySource.addPropertyDescriptorsForModule( this, descriptors);
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size()]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals( S_PROPERTY_JXTA_COMPONENT_ID))
			return Utils.getLabel( this.component );
		Object value = this.component.getPropertySource().getProperty( (IJp2pProperties) id );
		if(!( id instanceof ObjectProperty ))
			return value;
		ObjectProperty prop = (ObjectProperty)id;
		return prop.getValue();
	}

	@Override
	public boolean isPropertySet(Object id) {
		if (id.equals( S_PROPERTY_JXTA_COMPONENT_TEXT_ID )) {
			String curName = (String)getPropertyValue(id);
			return !curName.equals( this.defaultText );
		}
		return false;	
	}

	/**
	 * Currently not needed, there is no editing of properties, so a reset is not needed
	 * @param id
	 * @param value
	 */
	@Override
	public void resetPropertyValue(Object id) {
	}

	/**
	 * Currently not needed, there is no editing of properties
	 * @param id
	 * @param value
	 */
	@Override
	public void setPropertyValue(Object id, Object value) {
	}

	@Override
	public boolean isEditable(Object id) {
		return false;
	}

	@Override
	public T getModule() {
		return component.getModule();
	}
}
