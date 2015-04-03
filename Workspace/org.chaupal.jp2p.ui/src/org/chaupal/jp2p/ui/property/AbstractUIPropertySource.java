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

import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.component.IJp2pComponent.ModuleProperties;
import net.jp2p.container.properties.DefaultPropertySource;
import net.jp2p.container.properties.IJp2pDirectives;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.properties.IJp2pPropertySource;
import net.jp2p.container.properties.IJp2pWritePropertySource;

import org.chaupal.jp2p.ui.property.descriptors.TextBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public abstract class AbstractUIPropertySource<T extends Object> implements IJp2pUIPropertySource<T> {

	public static final String S_JP2P_DIRECTIVES_TEXT = "Directives";
	public static final String S_JP2P_PROPERTY_TEXT = "Properties";
	public static final String S_MODULE_CATEGORY = "Module";

	private T module;
	private IJp2pPropertySource<IJp2pProperties> source ;
	private boolean addModuleData;

	protected AbstractUIPropertySource( T module ) {
		this( module, new DefaultPropertySource( S_JP2P_PROPERTY_TEXT, module.getClass().getName() ), true );
	}

	protected AbstractUIPropertySource( IJp2pComponent<T> component ) {
		this( component.getModule(), component.getPropertySource(), false );
	}

	protected AbstractUIPropertySource( T module, IJp2pPropertySource<IJp2pProperties> defaults, boolean addModuleData ) {
		this.module = module;
		this.source = defaults;
		this.addModuleData = addModuleData;
	}

	final boolean addModuleData() {
		return addModuleData;
	}

	final void setAddModuleData(boolean addModuleData) {
		this.addModuleData = addModuleData;
	}

	public T getModule() {
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
			if( source.getProperty( property ) != null )
				properties.add(property);
		}
		Collection<IPropertyDescriptor> descriptors = getPropertyDescriptors( properties.toArray( new ModuleProperties[ properties.size()]));
		if( source != null )
			descriptors.addAll( getDirectiveDescriptors(source));
		if( this.addModuleData )
			SimpleUIPropertySource.addPropertyDescriptorsForModule( this, descriptors);
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size()]);
	}

	public abstract Object onGetPropertyValue( IJp2pProperties id );
	
	@Override
	public final Object getPropertyValue(Object id) {
		Object value = null;
		if( id instanceof IJp2pProperties ){
			if( id instanceof ObjectProperty ){
				SimpleUIPropertySource ss = new SimpleUIPropertySource( this.module, this.addModuleData );
				value = ss.getPropertyValue(id);
				return value;
			}
			value = this.onGetPropertyValue((IJp2pProperties) id);
			if( value != null )
				return value;
			else
				value = source.getProperty(( IJp2pProperties)id );
		}
		if( id instanceof IJp2pDirectives ){
			return source.getDirective((IJp2pDirectives) id);
		}
		return null;
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(!( id instanceof ModuleProperties ))
			return;
		ModuleProperties property = ( ModuleProperties )id;
		if(!( source instanceof IJp2pWritePropertySource ))
			return;
		IJp2pWritePropertySource<IJp2pProperties> wps = (IJp2pWritePropertySource<IJp2pProperties>) source;
		wps.setProperty( property, value);
	}

	@Override
	public boolean isPropertySet(Object id) {
		Object defaultValue = this.source.getProperty( (IJp2pProperties) id );
		if( defaultValue == null )
			return false;
		return ( !this.getPropertyValue(id).equals( defaultValue ));
	}

	@Override
	public void resetPropertyValue(Object id) {
		Object defaultValue = this.source.getProperty( (IJp2pProperties) id );
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
	protected static String[] parseProperty( String category, Object property ) {
		if( property == null )
			return null;
		String[] retval = new String[3];
		retval[0] = property.toString();
		retval[1] = property.toString();
		retval[2] = category;
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
	protected static Collection<IPropertyDescriptor> getPropertyDescriptors( Object[] properties ) {
		if( properties == null )
			return null;
		Collection<IPropertyDescriptor> descriptors = 
				new ArrayList<IPropertyDescriptor>();
		for( int i=0; i<properties.length; i++ ){
			String[] parsed = parseProperty( S_JP2P_PROPERTY_TEXT, properties[i]);
			TextBoxPropertyDescriptor textDescriptor = new TextBoxPropertyDescriptor( properties[i], parsed[1]);
			textDescriptor.setCategory(S_JP2P_PROPERTY_TEXT);
			descriptors.add( textDescriptor );
		}
		return descriptors;
	}

	/**
	 * Get the (text) property descriptors from the given values
	 * @param properties
	 * @return
	 */
	protected static Collection<IPropertyDescriptor> getDirectiveDescriptors( IJp2pPropertySource<?> source ) {
		if( source == null )
			return null;
		Collection<IPropertyDescriptor> descriptors = 
				new ArrayList<IPropertyDescriptor>();
		Iterator<IJp2pDirectives> iterator = source.directiveIterator();
		while( iterator.hasNext() ){
			IJp2pDirectives directive = iterator.next();
			String[] parsed = parseProperty( S_JP2P_DIRECTIVES_TEXT, directive );
			TextBoxPropertyDescriptor textDescriptor = new TextBoxPropertyDescriptor( directive, parsed[1]);
			textDescriptor.setCategory(S_JP2P_DIRECTIVES_TEXT);
			descriptors.add( textDescriptor );
		}
		return descriptors;
	}

}