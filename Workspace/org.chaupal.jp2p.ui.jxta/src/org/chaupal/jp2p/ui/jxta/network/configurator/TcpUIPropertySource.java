/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.configurator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.chaupal.jp2p.ui.property.AbstractUIPropertySource;
import org.chaupal.jp2p.ui.property.descriptors.CheckBoxPropertyDescriptor;
import org.chaupal.jp2p.ui.property.descriptors.SpinnerPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import net.jp2p.container.properties.IJp2pDirectives.Directives;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.utils.StringProperty;
import net.jp2p.jxta.transport.TransportPropertySource.TransportProperties;
import net.jxta.platform.NetworkConfigurator;

public class TcpUIPropertySource extends AbstractUIPropertySource<NetworkConfigurator> {

	public static final String S_CATEGORY = "Tcp";
	public static final String S_NO_READ_VALUE = "<Not a readable property>";
	
	private String category = S_CATEGORY;
	
	public TcpUIPropertySource(NetworkConfigurator configurator) {
		super( configurator );
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor descriptor = null;
		SpinnerPropertyDescriptor spd = null;
		StringProperty id = null;
		for( TransportProperties property: TransportProperties.values() ){
			id = new StringProperty( category + property.toString());
			switch( property ){
			case PUBLIC_ADDRESS:
				//combined = ( Object[] )value;
				break;
			case PORT:
			case START_PORT:
			case END_PORT:
				descriptor = new SpinnerPropertyDescriptor( id, property.toString(), 8080, 65535 );
				spd = ( SpinnerPropertyDescriptor )descriptor;
				spd.setEnabled( this.isEditable(property));
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
			descriptor.setCategory( category );
			descriptors.add(descriptor);
		}
		id = new StringProperty( category + Directives.ENABLED.toString());
		descriptor = new CheckBoxPropertyDescriptor( id, Directives.ENABLED.toString() );
		descriptor.setCategory(category);
		descriptors.add( descriptor );
		if( super.getPropertyDescriptors() != null )
			descriptors.addAll( Arrays.asList( super.getPropertyDescriptors()));
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
			TransportProperties property = ( TransportProperties )id;
			switch( property ){
			case PUBLIC_ADDRESS:
				return configurator.getTcpPublicAddress();
			case PUBLIC_ADDRESS_EXCLUSIVE:
				return configurator.isTcpPublicAddressExclusive();
			case INCOMING_STATUS:
				return configurator.getTcpIncomingStatus();
			case INTERFACE_ADDRESS:
				return configurator.getTcpInterfaceAddress();
			case OUTGOING_STATUS:
				return configurator.getTcpOutgoingStatus();
			case PORT:
				return configurator.getTcpPort();
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
		return TransportProperties.isValidProperty(transport );
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
		TransportProperties property = convert( (IJp2pProperties) id );
		if( property == null )
			return;
		Object[] combined;
		switch( property ){
		case PUBLIC_ADDRESS:
			combined = ( Object[] )value;
			configurator.setTcpPublicAddress(( String )combined[0], ( boolean)combined[1] );
			return;
		case PUBLIC_ADDRESS_EXCLUSIVE:
			//configurator.setHttpPublicAddressExclusive(( String )combined[0], ( boolean )combined[1]);
			return;
		case INCOMING_STATUS:
			configurator.setTcpIncoming( (boolean) value);
			return;
		case INTERFACE_ADDRESS:
			configurator.setTcpInterfaceAddress( (String) value);
			return;
		case OUTGOING_STATUS:
			configurator.setTcpOutgoing( (boolean) value);
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
	protected TransportProperties convert( IJp2pProperties property ){
		String id = property.name();
		if(!id.startsWith( category ))
			return null;
		id = id.replace( category, "");
		return TransportProperties.valueOf( id );
	}
	
	protected boolean isEnabledProperty( IJp2pProperties property ){
		if( !property.name().startsWith( category ))
			return false;
		return property.name().endsWith( Directives.ENABLED.name().toLowerCase() );
	}

	public boolean isValidProperty( Object id ){
		if(!( id instanceof IJp2pProperties ))
			return false;
		IJp2pProperties property = (IJp2pProperties) id;
		if( !property.name().startsWith( category ))
			return false;
		if( this.isEnabledProperty(property))
			return true;
		return TransportProperties.isValidProperty(property);
	}
}