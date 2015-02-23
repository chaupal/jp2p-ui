/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.seeds;

import java.util.List;
import java.util.Set;

import org.chaupal.jp2p.ui.property.CollectionPropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import net.jp2p.chaupal.jxta.platform.seeds.ISeedInfo.SeedTypes;
import net.jxta.platform.NetworkConfigurator;

public class RdvUIPropertySource extends AbstractSeedlistUIPropertySource {

	public static final String S_CATEGORY = "Rendezvous";
	public static final String S_NO_READ_VALUE = "<Not a readable property>";

	public RdvUIPropertySource( NetworkConfigurator configurator) {
		super( SeedTypes.RDV, configurator );
		super.addPropertySource( new CollectionPropertySource<Object>( SeedTypes.RDV.toString(), configurator.getRdvSeedURIs(), SeedProperties.RDV_SEED.toString()));
		super.addPropertySource( new CollectionPropertySource<Object>( SeedTypes.RDV.toString(), configurator.getRdvSeedingURIs(), SeedProperties.RDV_SEEDING_URI.toString()));
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
			return configurator.getRendezvousMaxClients();
		case USE_ONLY:
			return configurator.getUseOnlyRendezvousSeedsStatus();
		case SEED_URI:
			return configurator.getRdvSeedURIs();
		case SEEDING_URI:
			return configurator.getRdvSeedingURIs();
		default:
			break;
		}
		return null;
	}

	
	@Override
	protected void onSetPropertyValue(SeedListProperties property,
			NetworkConfigurator configurator, Object value ) {
		switch( property ){
		case MAX_CLIENTS:
			configurator.setRendezvousMaxClients((int) value );
			return;
		case USE_ONLY:
			configurator.setUseOnlyRendezvousSeeds(( boolean )value );
			return;
		case SEED_URI:
			configurator.setRendezvousSeeds( (Set<String>) value);
			return;
		case SEEDING_URI:
			configurator.setRendezvousSeedingURIs( (List<String>) value);
			return;
		default:
			break;
		}
	}
}