/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.configurator;

import java.util.ArrayList;
import java.util.Collection;

import org.chaupal.jp2p.ui.property.AbstractUIPropertySource;
import org.chaupal.jp2p.ui.util.CategoryStringProperty;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import net.jp2p.chaupal.jxta.platform.infra.InfrastructurePropertySource.InfrastructureProperties;
import net.jp2p.container.properties.IJp2pDirectives.Directives;
import net.jp2p.container.properties.IJp2pProperties;
import net.jxta.document.XMLElement;
import net.jxta.id.ID;
import net.jxta.platform.NetworkConfigurator;

public class InfrastructureUIPropertySource extends AbstractUIPropertySource<NetworkConfigurator> {

	public static final String S_CATEGORY = "Infrastructure";
	public static final String S_NO_READ_VALUE = "<Not a readable property>";
	
	private String category = S_CATEGORY;
	
	public InfrastructureUIPropertySource(NetworkConfigurator configurator) {
		super( configurator );
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor descriptor = null;
		CategoryStringProperty id = null;
		for( InfrastructureProperties property: InfrastructureProperties.values() ){
			id = new CategoryStringProperty( property.name(), category );
			descriptor = new TextPropertyDescriptor( id, property.toString());	
			descriptor.setCategory( category );
			descriptors.add(descriptor);
		}
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size()]);
	}

	@Override
	public Object onGetPropertyValue( IJp2pProperties id) {
		if( !this.isValidProperty( id ))
			return null;
		NetworkConfigurator configurator = super.getModule();
		if( this.isEnabledProperty( id )){
			return null;
		}
		InfrastructureProperties property = convert( id );
		if( property != null ){
			switch( property ){
			case DESC:
				return configurator.getInfrastructureDescriptionStr();
			case DESCRIPTION:
				return configurator.getInfrastructureIDStr();
			case ID:
				return configurator.getInfrastructureID();
			case ID_AS_STRING:
				return configurator.getInfrastructureIDStr();				
			case NAME:
				return configurator.getInfrastructureName();
			default:
				break;
			}
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
		if( this.isEnabledProperty((IJp2pProperties) id))
			return true;
		IJp2pProperties property = convert( (IJp2pProperties) id );
		return InfrastructureProperties.isValidProperty( property );
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(!( this.isEditable(id)))
			return;
		NetworkConfigurator configurator = super.getModule();
		if( this.isEnabledProperty( (IJp2pProperties) id )){
			configurator.setHttpEnabled( (boolean) value);
			return;
		}
		InfrastructureProperties property = convert( (IJp2pProperties) id );
		if( property == null )
			return;
		switch( property ){
		case DESC:
			configurator.setInfrastructureDesc( (XMLElement<?>) value );
			return;
		case DESCRIPTION:
			configurator.setInfrastructureDescriptionStr( (String) value );
			return;
		case ID:
			configurator.setInfrastructureID( (ID) value);
			return;
		case ID_AS_STRING:
			configurator.setInfrastructureID( (ID) value);
			return;
		case NAME:
			configurator.setInfrastructureName( (String) value);
			return;
		default:
			break;
		}
		super.setPropertyValue(id, value);
	}
	
	/**
	 * Convert a property to a transport property, or null if it isn't one
	 * @param property
	 * @return
	 */
	protected InfrastructureProperties convert( IJp2pProperties property ){
		if(! InfrastructureProperties.isValidProperty(property))
			return null;
		return InfrastructureProperties.valueOf( property.name() );
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
		return InfrastructureProperties.isValidProperty((IJp2pProperties) id);
	}
}