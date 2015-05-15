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

import net.jp2p.container.activator.IActivator.Status;
import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.component.IJp2pComponent.ModuleProperties;
import net.jp2p.container.properties.IJp2pDirectives;
import net.jp2p.container.properties.IJp2pDirectives.Directives;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.properties.IJp2pPropertySource;
import net.jp2p.container.properties.IJp2pWritePropertySource;

import org.chaupal.jp2p.ui.enm.EnumPropertyDescriptor;
import org.chaupal.jp2p.ui.property.descriptors.CheckBoxPropertyDescriptor;
import org.chaupal.jp2p.ui.property.descriptors.TextBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public abstract class AbstractUIJp2pPropertySource<T extends Object> implements IPropertySource {

	public static final String S_JP2P_DIRECTIVES_TEXT = "Directives";
	public static final String S_JP2P_PROPERTY_TEXT = "Properties";
	public static final String S_JP2P_MISCELLANEOUS = "Misc";

	private IJp2pPropertySource<IJp2pProperties> source ;

	protected AbstractUIJp2pPropertySource( IJp2pComponent<T> component ) {
		this( component.getPropertySource() );
	}

	protected AbstractUIJp2pPropertySource( IJp2pPropertySource<IJp2pProperties> source ) {
		this.source = source;
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
	public boolean isEditable( Object id ){
		return false;
	}
	
	/**
	 * Get the property descriptor for the given property
	 * @param id
	 * @return
	 */
	public abstract IPropertyDescriptor onGetPropertyDescriptor( IJp2pProperties id );

	/**
	 * Get the property descriptor for the given directive
	 * @param id
	 * @return
	 */
	public PropertyDescriptor onGetPropertyDescriptor( IJp2pDirectives id ){
		if( !( id instanceof Directives ))
			return null;
		Directives directive = (Directives) id;
		switch( directive ){
		case AUTO_START:
		case CREATE:
		case ENABLED:
			return new CheckBoxPropertyDescriptor(id, id.toString());
		default:
			return new TextBoxPropertyDescriptor( id, id.toString() );
		}
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor desc = null;

		//First get the directives
		Iterator<IJp2pDirectives> diriterator = source.directiveIterator();
		while( diriterator.hasNext() ){
			IJp2pDirectives directive = diriterator.next();
			desc = this.onGetPropertyDescriptor( directive ); 
			if( desc == null )
			    desc = new TextBoxPropertyDescriptor( directive, directive.toString() );
				
			desc.setCategory(S_JP2P_DIRECTIVES_TEXT);
			descriptors.add( desc);
		}

		//Then the properties
		Iterator<IJp2pProperties> iterator = source.propertyIterator();
		while( iterator.hasNext() ){
			IJp2pProperties property = iterator.next();
			desc = (PropertyDescriptor) this.onGetPropertyDescriptor(property ); 
			if( desc == null ){
				if( ModuleProperties.isValid( property )){
					switch( ModuleProperties.valueOf( property.name() )){
					case STATUS:
						EnumPropertyDescriptor edesc = new EnumPropertyDescriptor( property, property.toString(), Status.values());
						edesc.setEnabled(false);
						desc=  edesc;
						break;
					default:
						desc = new TextBoxPropertyDescriptor( property, property.toString() );
						break;
					}
				}else
					desc = new TextBoxPropertyDescriptor( property, property.toString() );
			}
			desc.setCategory(S_JP2P_PROPERTY_TEXT );
			descriptors.add( desc);
		}
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size()]);
	}

	/**
	 * Get the property descriptor for the given directive
	 * @param id
	 * @return
	 */
	public Object onGetPropertyValue( IJp2pDirectives id, String value ){
		if( !( id instanceof Directives ))
			return null;
		Directives directive = (Directives) id;
		switch( directive ){
		case AUTO_START:
		case CREATE:
		case ENABLED:
			return Boolean.valueOf( value );
		default:
			return value;
		}
	}

	@Override
	public final Object getPropertyValue(Object id) {
		Object value = null;
		if( id instanceof IJp2pProperties ){
			value = source.getProperty(( IJp2pProperties)id );
		}else if( id instanceof IJp2pDirectives ){
			value = this.onGetPropertyValue((IJp2pDirectives)id, source.getDirective( (IJp2pDirectives) id ));
		}
		return value;
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
}