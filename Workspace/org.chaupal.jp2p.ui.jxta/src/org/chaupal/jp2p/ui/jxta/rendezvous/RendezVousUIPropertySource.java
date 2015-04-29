/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.rendezvous;

import java.util.ArrayList;
import java.util.Collection;

import org.chaupal.jp2p.ui.enm.EnumPropertyDescriptor;
import org.chaupal.jp2p.ui.property.AbstractUIPropertySource;
import org.chaupal.jp2p.ui.property.CollectionPropertySource;
import org.chaupal.jp2p.ui.property.descriptors.CheckBoxPropertyDescriptor;
import org.chaupal.jp2p.ui.util.CategoryStringProperty;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import net.jp2p.jxta.rendezvous.RendezVousPropertySource.RendezVousProperties;
import net.jp2p.container.properties.IJp2pDirectives;
import net.jp2p.container.properties.IJp2pDirectives.Directives;
import net.jp2p.container.properties.IJp2pProperties;
import net.jxta.peer.PeerID;
import net.jxta.rendezvous.RendezVousService;
import net.jxta.rendezvous.RendezVousStatus;

public class RendezVousUIPropertySource extends AbstractUIPropertySource<RendezVousService> {

	public static final String S_CATEGORY = "Rendezvous";
	
	private String category = S_CATEGORY;
	
	public RendezVousUIPropertySource( RendezVousService service ) {
		super( service );
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor descriptor = null;
		CategoryStringProperty id = null;
		for( RendezVousProperties property: RendezVousProperties.values() ){
			id = new CategoryStringProperty( property.name(), category );
			switch( property ){
			case IS_CONNECTED_TO_RDV:
			case IS_RDV:
				descriptor = new CheckBoxPropertyDescriptor(id, property.toString());
				break;
			case RDV_STATUS:
				descriptor = new EnumPropertyDescriptor( id, property.toString(), RendezVousStatus.values() );
				break;
			default:
				descriptor = new TextPropertyDescriptor( id, property.toString());	
				break;			
			}
			descriptor.setCategory( category );
			descriptors.add(descriptor);
		}
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size()]);
	}

	@Override
	public Object onGetPropertyValue( IJp2pProperties id) {
		if( !this.isValidProperty( id ))
			return null;
		RendezVousService service = super.getModule();
		if( this.isAutostartProperty( id )){
			return null;
		}
		RendezVousProperties property = convert( id );
		if( property != null ){
			switch( property ){
			case IMPL_ADVERTISEMENT:
				return service.getImplAdvertisement();
			case IS_CONNECTED_TO_RDV:
				return service.isConnectedToRendezVous();
			case IS_RDV:
				return service.isRendezVous();
			case LOCAL_EDGE_VIEW:
				return new CollectionPropertySource<PeerID>( this.category, service.getLocalEdgeView(), property.toString() );
			case LOCAL_RDV_VIEW:
				return new CollectionPropertySource<PeerID>( this.category, service.getLocalRendezVousView(), property.toString() );
			case RDV_STATUS:
				return service.getRendezVousStatus();
			default:
				break;
			}
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
		return RendezVousProperties.isValidProperty(transport );
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(!( this.isEditable(id)))
			return;
		RendezVousService service = super.getModule();
		if( this.isAutostartProperty( (IJp2pProperties) id )){
			service.setAutoStart( (boolean) value);
		}
		super.setPropertyValue(id, value);
	}
	
	/**
	 * Convert a property to a transport property, or null if it isn't one
	 * @param property
	 * @return
	 */
	protected RendezVousProperties convert( IJp2pProperties property ){
		if(! RendezVousProperties.isValidProperty(property))
			return null;
		return RendezVousProperties.valueOf( property.name() );
	}
	
	protected boolean isEnabledProperty( IJp2pProperties property ){
		if( this.isValidProperty( property ))
			return false;
		return property.name().endsWith( Directives.ENABLED.name());
	}

	protected boolean isAutostartProperty( IJp2pProperties property ){
		if( this.isValidProperty( property ))
			return false;
		return property.name().equals( IJp2pDirectives.Directives.AUTO_START.name());
	}

	public boolean isValidProperty( Object id ){
		if(!( id instanceof CategoryStringProperty ))
			return false;
		if( !CategoryStringProperty.hasCategory((IJp2pProperties) id, category ))
			return false;
		return RendezVousProperties.isValidProperty((IJp2pProperties) id);
	}
}