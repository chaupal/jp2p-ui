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

import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.component.IJp2pComponent.ModuleProperties;
import net.jp2p.container.properties.DefaultPropertySource;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.properties.IJp2pPropertySource;
import net.jp2p.container.properties.IJp2pWritePropertySource;

import org.chaupal.jp2p.ui.property.descriptors.AbstractControlPropertyDescriptor;
import org.chaupal.jp2p.ui.property.descriptors.TextBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public abstract class AbstractJp2pUIPropertySource<T extends Object> implements IJp2pUIPropertySource {

	public static final String S_PROPERTY_JP2P_TEXT = "JP2P";

	private T module;
	private IJp2pPropertySource<IJp2pProperties> defaults ;

	protected AbstractJp2pUIPropertySource( T module ) {
		this( module, new DefaultPropertySource( S_PROPERTY_JP2P_TEXT, module.getClass().getName() ));
	}

	protected AbstractJp2pUIPropertySource( IJp2pComponent<T> component ) {
		this( component.getModule(), component.getPropertySource() );
	}

	protected AbstractJp2pUIPropertySource( T module, IJp2pPropertySource<IJp2pProperties> defaults ) {
		this.module = module;
		this.defaults = defaults;
	}

	protected T getModule() {
		return module;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	/**
	 * Returns true if the given property can be modified
	 * @param id
	 * @return
	 */
	public abstract boolean isEditable( Object id );
	
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<ModuleProperties> properties = new ArrayList<ModuleProperties>();
		for( ModuleProperties property: ModuleProperties.values()){
			if( defaults.getProperty( property ) != null )
				properties.add(property);
		}
		return getPropertyDescriptors( properties.toArray( new ModuleProperties[ properties.size()]));
	}

	@Override
	public Object getPropertyValue(Object id) {
		if(!( id instanceof IJp2pProperties ))
			return null;
		return defaults.getProperty(( IJp2pProperties)id );
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(!( id instanceof ModuleProperties ))
			return;
		ModuleProperties property = ( ModuleProperties )id;
		if(!( defaults instanceof IJp2pWritePropertySource ))
			return;
		IJp2pWritePropertySource<IJp2pProperties> wps = (IJp2pWritePropertySource<IJp2pProperties>) defaults;
		wps.setProperty( property, value);
	}

	@Override
	public boolean isPropertySet(Object id) {
		Object defaultValue = this.defaults.getProperty( (IJp2pProperties) id );
		if( defaultValue == null )
			return false;
		return ( !this.getPropertyValue(id).equals( defaultValue ));
	}

	@Override
	public void resetPropertyValue(Object id) {
		Object defaultValue = this.defaults.getProperty( (IJp2pProperties) id );
		if( defaultValue == null )
			return;
		this.setPropertyValue(id, defaultValue );
	}

	/**
	 * Parses a property and puts the results in a [id,value,category] table,
	 * which van be used to fill a property descriptor
	 * @param property
	 * @return
	 */
	protected static String[] parseProperty( Object property ) {
		if( property == null )
			return null;
		String[] retval = new String[3];
		retval[0] = property.toString();
		retval[1] = property.toString();
		retval[2] = S_PROPERTY_JP2P_TEXT;
		String[] split = property.toString().split("[.]");
		if( split.length > 1 ){
			retval[2] = split[0];
			retval[1] = retval[1].replace(retval[2] + ".", "");
		}
		return retval;
	}

	/**
	 * Get the (text) property descriptors from the given values
	 * @param properties
	 * @return
	 */
	protected static AbstractControlPropertyDescriptor<?>[] getPropertyDescriptors( Object[] properties ) {
		if( properties == null )
			return null;
		Collection<AbstractControlPropertyDescriptor<?>> descriptors = 
				new ArrayList<AbstractControlPropertyDescriptor<?>>();
		for( int i=0; i<properties.length; i++ ){
			String[] parsed = parseProperty(properties[i]);
			TextBoxPropertyDescriptor textDescriptor = new TextBoxPropertyDescriptor( properties[i], parsed[1]);
			textDescriptor.setCategory(parsed[2]);
			descriptors.add( textDescriptor );
		}
		return descriptors.toArray( new AbstractControlPropertyDescriptor[descriptors.size()]);
	}

}