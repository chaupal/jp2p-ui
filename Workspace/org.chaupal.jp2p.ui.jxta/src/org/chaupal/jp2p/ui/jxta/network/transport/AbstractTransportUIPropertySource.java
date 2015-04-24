/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.transport;

import java.util.ArrayList;
import java.util.Collection;

import org.chaupal.jp2p.ui.property.AbstractUIPropertySource;
import org.chaupal.jp2p.ui.property.descriptors.CheckBoxPropertyDescriptor;
import org.chaupal.jp2p.ui.property.descriptors.SpinnerPropertyDescriptor;
import org.chaupal.jp2p.ui.util.CategoryStringProperty;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import net.jp2p.container.properties.IJp2pDirectives.Directives;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.utils.StringProperty;
import net.jp2p.jxta.transport.TransportPropertySource.TransportProperties;
import net.jxta.platform.NetworkConfigurator;

abstract class AbstractTransportUIPropertySource extends AbstractUIPropertySource<NetworkConfigurator> {

	protected static final String S_NO_READ_VALUE = "<Not a readable property>";
	
	private String category;
	
	protected AbstractTransportUIPropertySource( NetworkConfigurator configurator, String category ) {
		super( configurator );
		this.category = category;
	}

	/**
	 * Returns true if the property is valid for the given type. Is usually overridden
	 * if the transport type is different than the standard
	 * @param property
	 * @return
	 */
	protected boolean isValidForTransportType( TransportProperties property ){
		return true;
	}
	
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor descriptor = null;
		StringProperty id = null;
		for( TransportProperties property: TransportProperties.values() ){
			if( !this.isValidForTransportType(property))
				continue;
			id = new CategoryStringProperty( property.name(), category );
			descriptor = null;
			switch( property ){
			case PUBLIC_ADDRESS:
				//combined = ( Object[] )value;
				break;
			case PORT:
			case START_PORT:
			case END_PORT:
				SpinnerPropertyDescriptor spd = new SpinnerPropertyDescriptor( id, property.toString(), 8080, 65535 );
				spd.setEnabled( this.isEditable(property));
				descriptor = spd;
				break;				
			case INCOMING_STATUS:
			case OUTGOING_STATUS:
			case PUBLIC_ADDRESS_EXCLUSIVE:
				descriptor = new CheckBoxPropertyDescriptor( id, property.toString() );
				break;
			default:
				descriptor = new TextPropertyDescriptor( id, property.toString());
				break;
			}	
			if( descriptor != null ){
				descriptor.setCategory( category );
				descriptors.add(descriptor);
			}
		}
		id = new CategoryStringProperty( Directives.ENABLED.name(), category );
		descriptor = new CheckBoxPropertyDescriptor( id, Directives.ENABLED.toString() );
		descriptor.setCategory(category);
		descriptors.add( descriptor );
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size()]);
	}

	protected abstract Object onGetPropertyValue( TransportProperties id );
	protected abstract boolean onIsEnabledValue( IJp2pProperties id );
	
	@Override
	public Object onGetPropertyValue( IJp2pProperties id) {
		if( !CategoryStringProperty.hasCategory((IJp2pProperties) id, category ))
			return null;
		if( this.isEnabledProperty( id )){
			return this.onIsEnabledValue(id);
		}
		if( !this.isValidProperty( id )){
			return null;
		}
		NetworkConfigurator configurator = super.getModule();
		IJp2pProperties transport = convert( id );
		if( transport != null ){
			TransportProperties property = ( TransportProperties )transport;
			if( !this.isValidForTransportType(property))
				return null;
			return this.onGetPropertyValue( property );
		}else{
			if( isEnabledProperty( id ))
				return configurator.isHttpEnabled();
		}
		return null;
	}

	/**
	 * Returns true if the given property can be modified
	 * @param id
	 * @return
	 */
	@Override
	public boolean isEditable( Object id ){
		if(!( id instanceof IJp2pProperties ))
			return false;
		if( !CategoryStringProperty.hasCategory((IJp2pProperties) id, category ))
			return false;
		if( this.isEnabledProperty((IJp2pProperties) id))
			return true;
		IJp2pProperties transport = convert( (IJp2pProperties) id );
		return TransportProperties.isValidProperty(transport );
	}

	/**
	 * Set the property value
	 * @param property
	 * @param value
	 */
	protected abstract boolean onSetPropertyValue( TransportProperties property, Object value);
	protected abstract boolean onSetEnabledValue( Object value);

	@Override
	public void setPropertyValue(Object id, Object value) {
		if( !CategoryStringProperty.hasCategory((IJp2pProperties) id, category ))
			return;
		if(!( this.isEditable(id)))
			return;
		if( this.isEnabledProperty( (IJp2pProperties) id )){
			this.onSetEnabledValue( (boolean) value);
			return;
		}
		TransportProperties property = convert( (IJp2pProperties) id );
		if(( property == null ) || ( !this.isValidForTransportType(property)))
			return;
		if( this.onSetPropertyValue(property, value))
			return;
		super.setPropertyValue(id, value);
	}
	
	/**
	 * Convert a property to a transport property, or null if it isn't one
	 * @param property
	 * @return
	 */
	protected TransportProperties convert( IJp2pProperties property ){
		if(! TransportProperties.isValidProperty(property))
			return null;
		return TransportProperties.valueOf( property.name() );
	}
	
	protected boolean isEnabledProperty( IJp2pProperties property ){
		if( this.isValidProperty( property ))
			return false;
		return property.name().endsWith( Directives.ENABLED.name());
	}

	public boolean isValidProperty( Object id ){
		if(!( id instanceof CategoryStringProperty ))
			return false;
		if( !CategoryStringProperty.hasCategory((IJp2pProperties) id, category ))
			return false;
		return TransportProperties.isValidProperty((IJp2pProperties) id);
	}
}