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
import org.chaupal.jp2p.ui.property.descriptors.CheckBoxPropertyDescriptor;
import org.chaupal.jp2p.ui.property.descriptors.SpinnerPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import net.jp2p.chaupal.jxta.platform.multicast.MulticastPropertySource.MulticastProperties;
import net.jp2p.container.properties.IJp2pDirectives.Directives;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.utils.StringProperty;
import net.jxta.platform.NetworkConfigurator;

public class MulticastUIPropertySource extends AbstractUIPropertySource<NetworkConfigurator> {

	public static final String S_CATEGORY = "Multicast";
	public static final String S_NO_READ_VALUE = "<Not a readable property>";
	
	private String category = S_CATEGORY;
	
	public MulticastUIPropertySource(NetworkConfigurator configurator) {
		super( configurator );
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor descriptor = null;
		SpinnerPropertyDescriptor spd = null;
		StringProperty id = null;
		for( MulticastProperties property: MulticastProperties.values() ){
			id = new StringProperty( category + property.toString());
			switch( property ){
			case PORT:
			case POOL_SIZE:
			case SIZE:
				descriptor = new SpinnerPropertyDescriptor( id, property.toString(), 8080, 65535 );
				spd = ( SpinnerPropertyDescriptor )descriptor;
				spd.setEnabled( this.isEditable( id));
				break;				
			default:
				descriptor = new TextPropertyDescriptor( id, property.toString());
				break;
			}	
			descriptor.setCategory( category );
			descriptors.add(descriptor);
		}
		id = new StringProperty( category + Directives.ENABLED.name() );
		descriptor = new CheckBoxPropertyDescriptor( id, Directives.ENABLED.toString() );
		descriptor.setCategory(category);
		descriptors.add( descriptor );
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size()]);
	}

	@Override
	public Object onGetPropertyValue( IJp2pProperties id) {
		if( !this.isValidProperty( id ))
			return null;
		NetworkConfigurator configurator = super.getModule();
		if( this.isEnabledProperty( id )){
			return configurator.isHttpEnabled();
		}
		IJp2pProperties transport = convert( id );
		if( transport != null ){
			MulticastProperties property = ( MulticastProperties )id;
			switch( property ){
			case ADDRESS:
				return configurator.getMulticastAddress();
			case INTERFACE:
				return configurator.getMulticastInterface();
			case POOL_SIZE:
				return configurator.getMulticastPoolSize();
			case STATUS:
				return configurator.getMulticastStatus();
			case SIZE:
				return configurator.getMulticastSize();
			case PORT:
				return configurator.getMulticastPort();
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
		IJp2pProperties transport = convert( (IJp2pProperties) id );
		return MulticastProperties.isValidProperty(transport );
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
		MulticastProperties property = convert( (IJp2pProperties) id );
		if( property == null )
			return;
		switch( property ){
		case ADDRESS:
			configurator.setMulticastAddress(( String )value );
			return;
		case INTERFACE:
			configurator.setMulticastInterface(( String )value );
			return;
		case POOL_SIZE:
			configurator.setMulticastPoolSize( (Integer) value);
			return;
		case SIZE:
			configurator.setMulticastSize( (Integer) value);
			return;
		case PORT:
			configurator.setTcpPort( (int) value);
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
	protected MulticastProperties convert( IJp2pProperties property ){
		String id = property.name();
		if(!id.startsWith( category ))
			return null;
		id = id.replace( category, "");
		return MulticastProperties.valueOf( id );
	}
	
	protected boolean isEnabledProperty( IJp2pProperties property ){
		if( this.isValidProperty( property ))
			return false;
		return property.name().endsWith( Directives.ENABLED.name());
	}

	public boolean isValidProperty( Object id ){
		if(!( id instanceof IJp2pProperties ))
			return false;
		IJp2pProperties property = (IJp2pProperties) id;
		if( !property.name().startsWith( category ))
			return false;
		if( this.isEnabledProperty(property))
			return true;
		return MulticastProperties.isValidProperty(property);
	}
}