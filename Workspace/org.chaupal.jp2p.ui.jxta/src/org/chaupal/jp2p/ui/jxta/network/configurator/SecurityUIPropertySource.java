/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.configurator;

import java.net.URI;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;

import org.chaupal.jp2p.ui.property.AbstractUIPropertySource;
import org.chaupal.jp2p.ui.property.descriptors.CheckBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import net.jp2p.chaupal.jxta.platform.security.SecurityPropertySource.SecurityProperties;
import net.jp2p.container.properties.IJp2pDirectives.Directives;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.utils.StringProperty;
import net.jxta.platform.NetworkConfigurator;

public class SecurityUIPropertySource extends AbstractUIPropertySource<NetworkConfigurator> {

	public static final String S_CATEGORY = "Security";
	public static final String S_NO_READ_VALUE = "<Not a readable property>";
	
	private String category = S_CATEGORY;
	
	public SecurityUIPropertySource(NetworkConfigurator configurator) {
		super( configurator );
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor descriptor = null;
		StringProperty id = null;
		for( SecurityProperties property: SecurityProperties.values() ){
			id = new StringProperty( category + property.toString());
			descriptor = new TextPropertyDescriptor( id, property.toString());	
			descriptor.setCategory( category );
			descriptors.add(descriptor);
		}
		id = new StringProperty( category + Directives.ENABLED.toString());
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
			SecurityProperties property = ( SecurityProperties )id;
			switch( property ){
			case AUTHENTICATION_TYPE:
				return configurator.getAuthenticationType();
			case CERTFICATE:
				return configurator.getCertificate();
			case CERTIFICATE_CHAIN:
				return configurator.getCertificateChain();
			case KEY_STORE_LOCATION:
				return configurator.getKeyStoreLocation();
			case PASSWORD:
				return configurator.getMulticastSize();
			case PRINCIPAL:
				return configurator.getPrincipal();
			case PRIVATE_KEY:
				return configurator.getPrivateKey();
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
		return SecurityProperties.isValidProperty(transport );
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
		SecurityProperties property = convert( (IJp2pProperties) id );
		if( property == null )
			return;
		switch( property ){
		case AUTHENTICATION_TYPE:
			configurator.setAuthenticationType(( String )value );
			return;
		case CERTFICATE:
			configurator.setCertificate( (X509Certificate) value );
			return;
		case CERTIFICATE_CHAIN:
			configurator.setCertificateChain( (X509Certificate[]) value);
			return;
		case KEY_STORE_LOCATION:
			configurator.setKeyStoreLocation( (URI) value);
			return;
		case PASSWORD:
			configurator.setPassword(  (String) value);
			return;
		case PRINCIPAL:
			configurator.setPrincipal( (String) value);
			return;
		case PRIVATE_KEY:
			configurator.setPrivateKey( (PrivateKey) value);
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
	protected SecurityProperties convert( IJp2pProperties property ){
		String id = property.name();
		if(!id.startsWith( category ))
			return null;
		id = id.replace( category, "");
		return SecurityProperties.valueOf( id );
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
		return SecurityProperties.isValidProperty(property);
	}
}