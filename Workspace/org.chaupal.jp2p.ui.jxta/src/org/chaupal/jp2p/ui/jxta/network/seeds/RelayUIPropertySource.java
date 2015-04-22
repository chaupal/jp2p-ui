/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.seeds;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.chaupal.jp2p.ui.property.CollectionPropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import net.jp2p.chaupal.jxta.platform.seeds.ISeedInfo.SeedTypes;
import net.jxta.platform.NetworkConfigurator;

public class RelayUIPropertySource extends AbstractSeedlistUIPropertySource {

	public static final String S_CATEGORY = "Relay";
	public static final String S_NO_READ_VALUE = "<Not a readable property>";

	public RelayUIPropertySource( NetworkConfigurator configurator) {
		super( SeedTypes.RELAY, configurator );
		super.addPropertySource( new CollectionPropertySource<Object>( SeedTypes.RELAY.toString(), configurator.getRelaySeedURIs(), super.getType().toString()));
		super.addPropertySource( new CollectionPropertySource<Object>( SeedTypes.RELAY.toString(), configurator.getRelaySeedingURIs(), super.getType().toString()));
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return super.getPropertyDescriptors();
	}


	@Override
	protected Object onGetPropertyValue(SeedListProperties property,
			NetworkConfigurator configurator) {
		switch( property ){
		case MAX_CLIENTS:
			return configurator.getRelayMaxClients();
		case USE_ONLY:
			return configurator.getUseOnlyRelaySeedsStatus();
		case SEED_URI:
			return new CollectionPropertySource<URI>( super.getCategory(), configurator.getRelaySeedURIs(), SeedTypes.RELAY.toString() );
		case SEEDING_URI:
			return new CollectionPropertySource<URI>( super.getCategory(), configurator.getRelaySeedingURIs(), SeedTypes.RELAY.toString() );
		default:
			break;
		}
		return null;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	protected void onSetPropertyValue(SeedListProperties property,
			NetworkConfigurator configurator, Object value ) {
		switch( property ){
		case MAX_CLIENTS:
			configurator.setRelayMaxClients((int) value );
			return;
		case USE_ONLY:
			configurator.setUseOnlyRelaySeeds(( boolean )value );
			return;
		case SEED_URI:
			configurator.setRelaySeedURIs ( (List<String>) value);
			return;
		case SEEDING_URI:
			configurator.setRelaySeedingURIs( (Set<String>) value);
			return;
		default:
			break;
		}
	}
}