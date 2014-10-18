/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.peergroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import net.jxta.peergroup.PeerGroup;
import net.jp2p.jxta.peergroup.PeerGroupPropertySource.PeerGroupProperties;

import org.chaupal.jp2p.ui.property.AbstractUIPropertySource;
import org.chaupal.jp2p.ui.provider.DecoratorLabelProvider;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class PeerGroupPropertySource extends AbstractUIPropertySource<PeerGroup> {

	public PeerGroupPropertySource( PeerGroup module ) {
		super( module );
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		descriptors.addAll( super.getPropertyDescriptors( PeerGroupProperties.values() ));
		if( super.getPropertyDescriptors() != null )
			descriptors.addAll( Arrays.asList( super.getPropertyDescriptors()));
		for( IPropertyDescriptor descriptor: descriptors ){
			PropertyDescriptor desc = (PropertyDescriptor)descriptor;
			desc.setLabelProvider( new DecoratorLabelProvider( false ));
		}
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size()]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		PeerGroup peergroup = super.getModule();
		if(!( id instanceof PeerGroupProperties ))
			return super.getPropertyValue(id);
		PeerGroupProperties property  = ( PeerGroupProperties )id;
		switch( property ){
		case PEERGROUP_ID:
			return peergroup.getPeerGroupID();
		case NAME:
			return peergroup.getPeerGroupName();
		case PEER_ID:
			return peergroup.getPeerID();
		case PEER_NAME:
			return peergroup.getPeerName();
		case STORE_HOME:
			return peergroup.getStoreHome();
		default:
			return super.getPropertyValue(id);
		}
	}

	/**
	 * Returns true if the given property can be modified
	 * @param id
	 * @return
	 */
	@Override
	public boolean isEditable( Object id ){
		return false;
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
	}
}