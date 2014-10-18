/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.property;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.properties.IJp2pProperties;

import org.chaupal.jp2p.ui.Activator;
import org.chaupal.jp2p.ui.property.descriptors.CompositePropertyDescriptor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class SimpleUIPropertySource implements IJp2pUIPropertySource<Object> {

	public static final String S_PROPERTY_JP2P_TEXT = "JP2P";
	public static final String S_MODULE_CATEGORY = "Module";

	private Object module;
	private boolean addModuleData;
	private Map<Object, Object> changed;

	public SimpleUIPropertySource( Object module ) {
		this( module, true );
	}

	protected SimpleUIPropertySource( IJp2pComponent<Object> component ) {
		this( component.getModule(), false );
	}

	protected SimpleUIPropertySource( Object module, boolean addModuleData ) {
		this.module = module;
		this.addModuleData = addModuleData;
		this.changed = new HashMap<Object, Object>();
	}

	final boolean addModuleData() {
		return addModuleData;
	}

	final void setAddModuleData(boolean addModuleData) {
		this.addModuleData = addModuleData;
	}

	public Object getModule() {
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
	public boolean isEditable( Object id ){
		if( this.module == null )
			return false;
		Method[] methods = getMethod( this.module, id.toString());
		if( methods.length == 0 )
			return false;
		String sequence = "set" + id.toString();
		for( Method method: methods ){
			if( method.getName().contains(sequence))
				return true;
		}
		return false;
	}
	
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		if( this.addModuleData )
			addPropertyDescriptorsForModule( this, descriptors);
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size()]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		if(!( id instanceof IJp2pProperties ))
			return null;
		Method[] methods = this.module.getClass().getMethods();
		for( Method method: methods ){
			if( !method.getName().endsWith( id.toString() ))
				continue;
			if( method.getName().startsWith("set"))
				continue;
			return getValue( this.module, method );
			
		}
		return null;
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(!( id instanceof IJp2pProperties ))
			return;
		Method[] methods = this.module.getClass().getMethods();
		for( Method method: methods ){
			if( !method.getName().endsWith( id.toString() ))
				continue;
			if( !method.getName().startsWith("set"))
				continue;
			Object def = getValue(methods, method);
			if(( def == null) && ( value == null ))
				return;
			if( def.equals( value ))
				return;
			setValue( this.module, method, value );		
			this.changed.put(id, def);
		}
	}

	@Override
	public boolean isPropertySet(Object id) {
		Object changed = this.changed.get( id );
		return ( changed != null );
	}

	@Override
	public void resetPropertyValue(Object id) {
		Object changed = this.changed.get( id );
		if( changed == null )
			return;
		this.setPropertyValue(id, changed );
	}

	/**
	 * try to retrieve the fields for the underlying module through reflection
	 * @param descriptors
	 */
	public static void addPropertyDescriptorsForModule( IJp2pUIPropertySource<?> source, Collection<IPropertyDescriptor> descriptors ) {
		Object module = source.getModule();
		if( module == null )
			return;
		Method[] methods = module.getClass().getMethods();
		CompositePropertyDescriptor descriptor; 
		String category = S_MODULE_CATEGORY;
		for( Method method: methods ){
			if( !filterMethod( method, false ))
				continue;
			
			Type type = method.getGenericReturnType();
			Class<?>[] parameters = method.getParameterTypes();
			Object value = null;
			try{
				value = method.invoke(module, new Object[parameters.length]);
			}
			catch( Exception ex ){
				Activator.getLog().log( IStatus.WARNING, ex.getMessage());
			}
			String name = method.getName().replace("get", "");
			IJp2pProperties id = new ObjectProperty( name, type, value );
			descriptor = new CompositePropertyDescriptor( id, name, type );
			descriptor.setCategory( category);
			descriptor.setEnabled( source.isEditable(name));
			descriptors.add( descriptor);
		}
	}

	/**
	 * Filter the method name, to detect getters and setters
	 * @param method
	 * @return
	 */
	protected static boolean filterMethod( Method method, boolean includeSetter ){
		if( includeSetter && !method.getName().startsWith("set"))
			return true;
		if(( !method.getName().startsWith("get")) && 
				( !method.getName().startsWith("is") && 
				( !method.getName().startsWith("has"))))
			return false;
		if(( method.getName().startsWith("getClass")) || 
				( method.getName().startsWith("hash")))
			return false;
		return true;
	}
	
	/**
	 * Get the methods that end with the given displayName. 
	 * @param module
	 * @param displayName
	 * @return
	 */
	protected static Method[] getMethod( Object module, String displayName ){
		if(( module == null ) || ( displayName == null ))
			return null;
		Collection<Method> results = new ArrayList<Method>();
		Method[] methods = module.getClass().getMethods();
		for( Method method: methods ){
			if(!filterMethod( method, true ))
				continue;
			if(method.getName().endsWith( displayName ))
				results.add( method );
		}
		return results.toArray( new Method[ results.size()]);
	}
	
	/**
	 * Try to get the value of the given method through reflection
	 * @param module
	 * @param method
	 * @return
	 */
	protected static Object getValue( Object module, Method method){
		Class<?>[] parameters = method.getParameterTypes();
		Object value = null;
		try{
			value = method.invoke(module, new Object[parameters.length]);
		}
		catch( Exception ex ){
			Activator.getLog().log( IStatus.WARNING, ex.getMessage());
		}
		return value;
		
	}

	/**
	 * Try to get the value of the given method through reflection
	 * @param module
	 * @param method
	 * @return
	 */
	protected static Object setValue( Object module, Method method, Object value){
		Class<?>[] parameters = method.getParameterTypes();
		Object[] args = new Object[parameters.length];
		args[0] = value;
		try{
			value = method.invoke(module, args );
		}
		catch( Exception ex ){
			Activator.getLog().log( IStatus.WARNING, ex.getMessage());
		}
		return value;
		
	}

}