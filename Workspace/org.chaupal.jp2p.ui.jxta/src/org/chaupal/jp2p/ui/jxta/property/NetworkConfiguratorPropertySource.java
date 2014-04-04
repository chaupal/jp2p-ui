/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.property;

import java.io.File;
import java.net.URI;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.chaupal.jp2p.ui.jxta.property.descriptors.CheckBoxPropertyDescriptor;
import org.chaupal.jp2p.ui.jxta.property.descriptors.SpinnerPropertyDescriptor;
import org.chaupal.jp2p.ui.property.CollectionPropertySource;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import net.jp2p.chaupal.jxta.root.network.configurator.NetworkConfigurationPropertySource.NetworkConfiguratorProperties;
import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.utils.EnumUtils;
import net.jxta.id.ID;
import net.jxta.peer.PeerID;
import net.jxta.refplatform.platform.NetworkConfigurator;
import net.jxta.refplatform.platform.NetworkManager.ConfigMode;

public class NetworkConfiguratorPropertySource extends AbstractJp2pUIPropertySource<NetworkConfigurator> {

	public static final String S_NO_READ_VALUE = "<Not a readable property>";
	
	public NetworkConfiguratorPropertySource(NetworkConfigurator configurator) {
		super( configurator );
	}

	public NetworkConfiguratorPropertySource( IJp2pComponent<NetworkConfigurator> component ) {
		super( component );
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		for( NetworkConfiguratorProperties property: NetworkConfiguratorProperties.values() ){
			String[] parsed = super.parseProperty(property);
			PropertyDescriptor descriptor;
			SpinnerPropertyDescriptor spd;
			switch( property ){
/*
			case CERTFICATE:
				configurator.setCertificate( (X509Certificate) value);
				return;
			case CERTIFICATE_CHAIN:
				configurator.setCertificateChain( (X509Certificate[]) value);
				return;
			case HOME:
				configurator.setHome( (File) value);
				return;
			case HTTP_8PUBLIC_ADDRESS:
				combined = ( Object[] )value;
				return;
			case INFRASTRUCTURE_8ID:
				configurator.setInfrastructureID( (ID) value);
				return;
			case KEY_STORE_LOCATION:
				configurator.setKeyStoreLocation( (URI) value);
				return;
			case PEER_ID:
				configurator.setPeerID( (PeerID) value);
				return;
			case PRIVATE_KEY:
				configurator.setPrivateKey( (PrivateKey) value);
				return;
			case RELAY_8SEED_URIS:
				configurator.setRelaySeedURIs( (List<String>) value);
				return;
			case RELAY_8SEEDING_URIS:
				configurator.setRelaySeedingURIs( (Set<String>) value);
				return;
			case RENDEZVOUS_8SEED_URIS:
				configurator.setRendezvousSeeds( (Set<String>) value);
				return;
			case RENDEZVOUS_8SEEDING_URIS:
				configurator.setRendezvousSeedingURIs( (List<String>) value);
				return;
			case STORE_HOME:
				configurator.setStoreHome( (URI) value);
				return;
			case TCP_8PUBLIC_ADDRESS:
				combined = ( Object[] )value;
				configurator.setTcpPublicAddress( ( String )combined[0], ( boolean)combined[1]);
				return;
				configurator.setHttp2EndPort( (int) value);
				return;
			case TCP_8INTERFACE_ADDRESS:
				configurator.setTcpInterfaceAddress( (String) value);
				return;
				configurator.setTcpOutgoing( (boolean) value);
				return;
*/				
			case MULTICAST_8SIZE:
			case MULTICAST_8POOL_SIZE:
				descriptor = new SpinnerPropertyDescriptor( property, parsed[1], 0, Integer.MAX_VALUE );
				spd = ( SpinnerPropertyDescriptor )descriptor;
				spd.setEnabled( this.isEditable(property));
				break;				
			case RENDEZVOUS_8MAX_CLIENTS:
			case RELAY_8MAX_CLIENTS:
				descriptor = new SpinnerPropertyDescriptor( property, parsed[1], -1, Integer.MAX_VALUE );
				spd = ( SpinnerPropertyDescriptor )descriptor;
				spd.setEnabled( this.isEditable(property));
				break;				
			case MULTICAST_8PORT:
			case HTTP_8PORT:
			case HTTP2_8PORT:
			case HTTP2_8START_PORT:
			case HTTP2_8END_PORT:
			case TCP_8START_PORT:
			case TCP_8END_PORT:
			case TCP_8PORT:
				descriptor = new SpinnerPropertyDescriptor( property, parsed[1], 8080, 65535 );
				spd = ( SpinnerPropertyDescriptor )descriptor;
				spd.setEnabled( this.isEditable(property));
				break;				
			case CONFIG_MODE:
				descriptor = new ComboBoxPropertyDescriptor( property, parsed[1], EnumUtils.toString( ConfigMode.values() ));				
				break;
			case HTTP_8ENABLED:
			case HTTP_8INCOMING_STATUS:
			case HTTP_8OUTGOING_STATUS:
			case HTTP_8TO_PUBLIC_ADDRESS_EXCLUSIVE:
			case HTTP_8PUBLIC_ADDRESS_EXCLUSIVE:
			case HTTP2_8ENABLED:
			case HTTP2_8INCOMING_STATUS:
			case HTTP2_8OUTGOING_STATUS:
			case HTTP2_8TO_PUBLIC_ADDRESS_EXCLUSIVE:
			case HTTP2_8PUBLIC_ADDRESS_EXCLUSIVE:
			case TCP_8ENABLED:
			case TCP_8INCOMING_STATUS:
			case TCP_8OUTGOING_STATUS:
			case TCP_8PUBLIC_ADDRESS_EXCLUSIVE:
			case MULTICAST_8ENABLED:
			case USE_ONLY_RELAY_SEEDS:
			case USE_ONLY_RENDEZVOUS_SEEDS:
				descriptor = new CheckBoxPropertyDescriptor( property, parsed[1] );
				break;
			default:
				descriptor = new TextPropertyDescriptor( property, parsed[1]);
				break;
			}	
			descriptor.setCategory(parsed[2]);
			descriptors.add(descriptor);
		}
		if( super.getPropertyDescriptors() != null )
			descriptors.addAll( Arrays.asList( super.getPropertyDescriptors()));
		return descriptors.toArray( new IPropertyDescriptor[ descriptors.size()]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		NetworkConfigurator configurator = super.getModule();
		if( !NetworkConfiguratorProperties.isValidProperty( (IJp2pProperties) id ))
			return null;
		NetworkConfiguratorProperties property = ( NetworkConfiguratorProperties )id;
		switch( property ){
		case DESCRIPTION:
			return S_NO_READ_VALUE;
		case HOME:
			return configurator.getHome();
		case CONFIG_MODE:
			return configurator.getMode();
		case HTTP_8PUBLIC_ADDRESS:
			return configurator.getHttpPublicAddress();
		case HTTP_8ENABLED:
			return configurator.isHttpEnabled();
		case HTTP_8PUBLIC_ADDRESS_EXCLUSIVE:
			return configurator.isHttpPublicAddressExclusive();
		case HTTP_8INCOMING_STATUS:
			return configurator.getHttpIncomingStatus();
		case HTTP_8INTERFACE_ADDRESS:
			return configurator.getHttpInterfaceAddress();
		case HTTP_8OUTGOING_STATUS:
			return configurator.getHttpOutgoingStatus();
		case HTTP_8PORT:
			return configurator.getHttp2Port();
		case HTTP_8TO_PUBLIC_ADDRESS_EXCLUSIVE:
			return configurator.isHttp2PublicAddressExclusive();
		case HTTP2_8PUBLIC_ADDRESS:
			return configurator.getHttp2PublicAddress();
		case HTTP2_8ENABLED:
			return configurator.isHttp2Enabled();
		case HTTP2_8PUBLIC_ADDRESS_EXCLUSIVE:
			return configurator.isHttp2PublicAddressExclusive();
		case HTTP2_8INCOMING_STATUS:
			return configurator.getHttp2IncomingStatus();
		case HTTP2_8INTERFACE_ADDRESS:
			return configurator.getHttp2InterfaceAddress();
		case HTTP2_8OUTGOING_STATUS:
			return configurator.getHttp2OutgoingStatus();
		case HTTP2_8PORT:
			return configurator.getHttp2Port();
		case HTTP2_8END_PORT:
			return configurator.getHttp2EndPort();
		case HTTP2_8START_PORT:
			return configurator.getHttp2StartPort();
		case HTTP2_8TO_PUBLIC_ADDRESS_EXCLUSIVE:
			return configurator.isHttp2PublicAddressExclusive();

		case INFRASTRUCTURE_8DESCRIPTION:
			return configurator.getInfrastructureDescriptionStr();
		case INFRASTRUCTURE_8ID:
			return configurator.getInfrastructureID();
		case INFRASTRUCTURE_8NAME:
			return configurator.getInfrastructureName();
		case SECURITY_8AUTHENTICATION_TYPE:
			return configurator.getAuthenticationType();
		case SECURITY_8CERTFICATE:
			return configurator.getCertificate();
		case SECURITY_8CERTIFICATE_CHAIN:
			return configurator.getCertificateChain();
		case SECURITY_8PASSWORD:
			return configurator.getPassword();
		case SECURITY_8KEY_STORE_LOCATION:
			return configurator.getKeyStoreLocation();
		case SECURITY_8PRINCIPAL:
			return configurator.getPrincipal();
		case SECURITY_8PRIVATE_KEY:
			return configurator.getPrivateKey();
		case MULTICAST_8ADDRESS:
			return configurator.getMulticastAddress();
		case MULTICAST_8INTERFACE:
			return configurator.getMulticastInterface();
		case MULTICAST_8POOL_SIZE:
			return configurator.getMulticastPoolSize();
		case MULTICAST_8PORT:
			return configurator.getMulticastPort();
		case MULTICAST_8SIZE:
			return configurator.getMulticastSize();
		case MULTICAST_8STATUS:
			return configurator.getMulticastStatus();
		case NAME:
			return configurator.getName();
		case PEER_ID:
			return configurator.getPeerID();
		case RELAY_8MAX_CLIENTS:
			return configurator.getRelayMaxClients();
		case RELAY_8SEED_URIS:
			return  new CollectionPropertySource( property.toString(), configurator.getRelaySeedURIs(), "default");
		case RELAY_8SEEDING_URIS:
			return new CollectionPropertySource( property.toString(), configurator.getRelaySeedingURIs(), "default");
		case RENDEZVOUS_8MAX_CLIENTS:
			return configurator.getRendezvousMaxClients();
		case RENDEZVOUS_8SEED_URIS:
			return  new CollectionPropertySource( property.toString(), configurator.getRdvSeedURIs(), "default");
		case RENDEZVOUS_8SEEDING_URIS:
			return new CollectionPropertySource( property.toString(), configurator.getRdvSeedingURIs(), "default" );
		case STORE_HOME:
			return configurator.getStoreHome();
		case TCP_8PUBLIC_ADDRESS:
			return configurator.getTcpPublicAddress();
		case TCP_8ENABLED:
			return configurator.isTcpEnabled();
		case TCP_8END_PORT:
			return configurator.getTcpEndport();
		case TCP_8PUBLIC_ADDRESS_EXCLUSIVE:
			return configurator.isTcpPublicAddressExclusive();
		case TCP_8INCOMING_STATUS:
			return configurator.getTcpIncomingStatus();
		case TCP_8INTERFACE_ADDRESS:
			return configurator.getTcpInterfaceAddress();
		case TCP_8OUTGOING_STATUS:
			return configurator.getTcpOutgoingStatus();
		case TCP_8PORT:
			return configurator.getTcpPort();
		case TCP_8START_PORT:
			return configurator.getTcpStartPort();
		case MULTICAST_8ENABLED:
			return configurator.getMulticastStatus();
		case USE_ONLY_RELAY_SEEDS:
			return configurator.getUseOnlyRelaySeedsStatus();
		case USE_ONLY_RENDEZVOUS_SEEDS:
			return configurator.getUseOnlyRendezvousSeedsStatus();
		default:
			break;
		}
		return super.getPropertyValue(id);
	}

	/**
	 * Returns true if the given property can be modified
	 * @param id
	 * @return
	 */
	@Override
	public boolean isEditable( Object id ){
		if(!( id instanceof NetworkConfiguratorProperties ))
			return false;
		return this.isEditable( ( NetworkConfiguratorProperties )id);
	}

	/**
	 * Returns true if the given property can be modified
	 * @param id
	 * @return
	 */
	public boolean isEditable( NetworkConfiguratorProperties property ){
		switch( property ){
		case HTTP_8PUBLIC_ADDRESS_EXCLUSIVE:
		case HTTP_8TO_PUBLIC_ADDRESS_EXCLUSIVE:
		case TCP_8PUBLIC_ADDRESS_EXCLUSIVE:
			return false;
		default:
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPropertyValue(Object id, Object value) {
		if(!( this.isEditable(id)))
			return;
		NetworkConfigurator configurator = super.getModule();
		NetworkConfiguratorProperties property = ( NetworkConfiguratorProperties )id;
		Object[] combined;
		switch( property ){
		case DESCRIPTION:
			configurator.setDescription((String) value);
			return;
		case HOME:
			configurator.setHome( (File) value);
			return;
		case HTTP_8PUBLIC_ADDRESS:
			combined = ( Object[] )value;
			configurator.setHttpPublicAddress(( String )combined[0], ( boolean)combined[1] );
			return;
		case HTTP_8ENABLED:
			configurator.setHttpEnabled( (boolean) value);
			return;
		case HTTP_8PUBLIC_ADDRESS_EXCLUSIVE:
			//configurator.setHttpPublicAddressExclusive(( String )combined[0], ( boolean )combined[1]);
			return;
		case HTTP_8INCOMING_STATUS:
			configurator.setHttpIncoming( (boolean) value);
			return;
		case HTTP_8INTERFACE_ADDRESS:
			configurator.setHttpInterfaceAddress( (String) value);
			return;
		case HTTP_8OUTGOING_STATUS:
			configurator.setHttpOutgoing( (boolean) value);
			return;
		case HTTP_8PORT:
			configurator.setHttpPort( (int) value);
			return;
		case HTTP_8TO_PUBLIC_ADDRESS_EXCLUSIVE:
			//configurator.setHttp2PublicAddressExclusive( value);
			return;
		case HTTP2_8PUBLIC_ADDRESS:
			combined = ( Object[] )value;
			configurator.setHttp2PublicAddress(( String )combined[0], ( boolean)combined[1] );
			return;
		case HTTP2_8ENABLED:
			configurator.setHttp2Enabled( (boolean) value);
			return;
		case HTTP2_8END_PORT:
			configurator.setHttp2EndPort( (int) value);
			return;
		case HTTP2_8PUBLIC_ADDRESS_EXCLUSIVE:
			//configurator.setHttpPublicAddressExclusive(( String )combined[0], ( boolean )combined[1]);
			return;
		case HTTP2_8INCOMING_STATUS:
			configurator.setHttp2Incoming( (boolean) value);
			return;
		case HTTP2_8INTERFACE_ADDRESS:
			configurator.setHttp2InterfaceAddress( (String) value);
			return;
		case HTTP2_8OUTGOING_STATUS:
			configurator.setHttp2Outgoing( (boolean) value);
			return;
		case HTTP2_8PORT:
			configurator.setHttp2Port( (int) value);
			return;
		case HTTP2_8START_PORT:
			configurator.setHttp2StartPort( (int) value);
			return;
		case HTTP2_8TO_PUBLIC_ADDRESS_EXCLUSIVE:
			//configurator.setHttp2PublicAddressExclusive( value);
			return;
		case INFRASTRUCTURE_8DESCRIPTION:
			configurator.setInfrastructureDescriptionStr( (String) value);
			return;
		case INFRASTRUCTURE_8ID:
			configurator.setInfrastructureID( (ID) value);
			return;
		case INFRASTRUCTURE_8NAME:
			configurator.setInfrastructureName( (String) value);
			return;
		case CONFIG_MODE:
			configurator.setMode( (int) value);
			return;
		case MULTICAST_8ADDRESS:
			configurator.setMulticastAddress( (String) value);
			return;
		case MULTICAST_8INTERFACE:
			configurator.setMulticastInterface( (String) value);
			return;
		case MULTICAST_8POOL_SIZE:
			configurator.setMulticastPoolSize( (int) value);
			return;
		case MULTICAST_8PORT:
			configurator.setMulticastPort( (int) value);
			return;
		case MULTICAST_8SIZE:
			configurator.setMulticastSize( (int) value);
			return;
		case MULTICAST_8STATUS:
			configurator.setMulticastInterface( (String) value);
			return;
		case NAME:
			configurator.setName( (String) value);
			return;
		case PEER_ID:
			configurator.setPeerID( (PeerID) value);
			return;
		case SECURITY_8KEY_STORE_LOCATION:
			configurator.setKeyStoreLocation( (URI) value);
			return;
		case SECURITY_8PASSWORD:
			configurator.setPassword( (String) value);
			return;
		case SECURITY_8AUTHENTICATION_TYPE:
			configurator.setAuthenticationType( (String) value );
			return;
		case SECURITY_8CERTFICATE:
			configurator.setCertificate( (X509Certificate) value);
			return;
		case SECURITY_8CERTIFICATE_CHAIN:
			configurator.setCertificateChain( (X509Certificate[]) value);
			return;
		case SECURITY_8PRINCIPAL:
			configurator.setPrincipal( (String) value);
			return;
		case SECURITY_8PRIVATE_KEY:
			configurator.setPrivateKey( (PrivateKey) value);
			return;
		case RELAY_8MAX_CLIENTS:
			configurator.setRelayMaxClients( (int) value);
			return;
		case RELAY_8SEED_URIS:
			configurator.setRelaySeedURIs( (List<String>) value);
			return;
		case RELAY_8SEEDING_URIS:
			configurator.setRelaySeedingURIs( (Set<String>) value);
			return;
		case RENDEZVOUS_8MAX_CLIENTS:
			configurator.setRendezvousMaxClients( (int) value);
			return;
		case RENDEZVOUS_8SEED_URIS:
			configurator.setRendezvousSeeds( (Set<String>) value);
			return;
		case RENDEZVOUS_8SEEDING_URIS:
			configurator.setRendezvousSeedingURIs( (List<String>) value);
			return;
		case STORE_HOME:
			configurator.setStoreHome( (URI) value);
			return;
		case TCP_8PUBLIC_ADDRESS:
			combined = ( Object[] )value;
			configurator.setTcpPublicAddress( ( String )combined[0], ( boolean)combined[1]);
			return;
		case TCP_8ENABLED:
			configurator.setTcpEnabled( (boolean) value);
			return;
		case TCP_8END_PORT:
			configurator.setHttp2EndPort( (int) value);
			return;
		case TCP_8PUBLIC_ADDRESS_EXCLUSIVE:
			//configurator.setTcpPublicAddressExclusive( value);
			return;
		case TCP_8INCOMING_STATUS:
			configurator.setTcpIncoming( (boolean) value);
			return;
		case TCP_8INTERFACE_ADDRESS:
			configurator.setTcpInterfaceAddress( (String) value);
			return;
		case TCP_8OUTGOING_STATUS:
			configurator.setTcpOutgoing( (boolean) value);
			return;
		case TCP_8PORT:
			configurator.setTcpPort( (int) value);
			return;
		case TCP_8START_PORT:
			configurator.setTcpStartPort( (int) value);
			return;
		case MULTICAST_8ENABLED:
			configurator.setUseMulticast( (boolean) value );
			return;
		case USE_ONLY_RELAY_SEEDS:
			configurator.setUseOnlyRelaySeeds( (boolean) value);
			return;
		case USE_ONLY_RENDEZVOUS_SEEDS:
			configurator.setUseOnlyRendezvousSeeds( (boolean) value);
			return;
		default:
			break;
		}
		super.setPropertyValue(id, value);
	}
}