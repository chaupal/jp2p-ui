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
import java.util.Collections;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class CollectionPropertySource<T extends Object> implements IPropertySource {

	public static final String S_PROPERTY_JP2P_COMPONENT_TEXT_ID = "org.chaupal.jp2p.component.text";

	private String defaultText;
	private List<T> source;
	private String category;

	public CollectionPropertySource( String category, Collection<T> source, String defaultText ) {
		super();
		this.defaultText = defaultText;
		this.source = new ArrayList<T>( source );
		this.category = category;
	}

	public CollectionPropertySource( String category, T[] items, String defaultText ) {
		super();
		this.defaultText = defaultText;
		this.source = new ArrayList<T>();
		this.category = category;
		Collections.addAll( this.source,  items ); 
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor textDescriptor; 
		int i=0;
		for( T value: source ){
			textDescriptor = new TextPropertyDescriptor( i, value.toString() );
			textDescriptor.setCategory( category);
			descriptors.add( textDescriptor);
			i++;
		}
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size()]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		return this.source.get(( int )id );
	}

	@Override
	public boolean isPropertySet(Object id) {
		if (id.equals( S_PROPERTY_JP2P_COMPONENT_TEXT_ID )) {
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

}
