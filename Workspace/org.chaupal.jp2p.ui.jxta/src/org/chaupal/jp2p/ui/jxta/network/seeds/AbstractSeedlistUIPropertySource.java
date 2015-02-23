/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.seeds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.chaupal.jp2p.ui.property.AbstractUIPropertySource;
import org.chaupal.jp2p.ui.property.descriptors.CheckBoxPropertyDescriptor;
import org.chaupal.jp2p.ui.property.descriptors.SpinnerPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import net.jp2p.chaupal.jxta.platform.seeds.ISeedInfo;
import net.jp2p.chaupal.jxta.platform.seeds.ISeedInfo.SeedTypes;
import net.jp2p.container.properties.IJp2pDirectives.Directives;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.utils.StringProperty;
import net.jp2p.container.utils.StringStyler;
import net.jxta.platform.NetworkConfigurator;

abstract class AbstractSeedlistUIPropertySource extends AbstractUIPropertySource<NetworkConfigurator> {

	public static final String S_NO_READ_VALUE = "<Not a readable property>";

	private String category;
	
	private Collection<IPropertySource> seeds;

	public enum SeedListProperties implements IJp2pProperties{
		MAX_CLIENTS,
		USE_ONLY,
		SEED_URI,
		SEEDING_URI;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString() );
		}	

		/**
		 * Returns true if the given property is valid for this enumeration
		 * @param property
		 * @return
		 */
		public static boolean isValidProperty( IJp2pProperties property ){
			if( property == null )
				return false;
			for( SeedListProperties prop: values() ){
				if( prop.equals( property ))
					return true;
			}
			return false;
		}

		public static SeedListProperties convertTo( String str ){
			String enumStr = StringStyler.styleToEnum( str );
			return valueOf( enumStr );
		}
	}

	public enum SeedProperties implements IJp2pProperties{
		RDV_SEED,
		RDV_SEEDING_URI,
		RELAY_SEED,
		RELAY_SEEDING_URI;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString() );
		}	
	}
	
	private ISeedInfo.SeedTypes type;
	
	protected AbstractSeedlistUIPropertySource( SeedTypes type, NetworkConfigurator configurator) {
		super( configurator );
		this.category = type.toString();
		this.type = type;
		seeds = new ArrayList<IPropertySource>();
	}

	protected void addPropertySource( IPropertySource source ){
		seeds.add( source );
	}
	
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor descriptor = null;
		SpinnerPropertyDescriptor spd = null;
		StringProperty id = null;
		for( SeedListProperties property: SeedListProperties.values() ){
			id = new StringProperty( category + property.toString());
			switch( property ){
			case MAX_CLIENTS:
				descriptor = new SpinnerPropertyDescriptor( id, property.toString(), 8080, 65535 );
				spd = ( SpinnerPropertyDescriptor )descriptor;
				spd.setEnabled( this.isEditable(property));
				break;				
			case USE_ONLY:
				descriptor = new CheckBoxPropertyDescriptor( id, property.toString() );
				break;
			case SEED_URI:
				//descriptor = new CollectionPropertySource<URI>();
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

	protected abstract Object onGetPropertyValue( SeedListProperties property, NetworkConfigurator configurator );
	
	@Override
	public Object onGetPropertyValue( IJp2pProperties id ) {
		if( !this.isValidProperty( id ))
			return null;
		NetworkConfigurator configurator = super.getModule();
		IJp2pProperties transport = convert( id );
		if( transport != null ){
			SeedListProperties property = ( SeedListProperties )id;
			return this.onGetPropertyValue(property, configurator );
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
		return SeedListProperties.isValidProperty(transport );
	}

	protected abstract void onSetPropertyValue( SeedListProperties property, NetworkConfigurator configurator, Object value );

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(!( this.isEditable(id)))
			return;
		NetworkConfigurator configurator = super.getModule();
		if( this.isEnabledProperty( (IJp2pProperties) id )){
			configurator.setHttpEnabled( (boolean) value);
			return;
		}
		SeedListProperties property = convert( (IJp2pProperties) id );
		if( property == null )
			return;
		this.onSetPropertyValue(property, configurator, value );
		super.setPropertyValue(id, value);
	}
	
	/**
	 * Convert a property to a transport property, or null if it isn't one
	 * @param property
	 * @return
	 */
	protected SeedListProperties convert( IJp2pProperties property ){
		String id = property.name();
		if(!id.startsWith( category ))
			return null;
		id = id.replace( category, "");
		return SeedListProperties.valueOf( id );
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
		return SeedListProperties.isValidProperty(property);
	}
}