/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.socket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.utils.StringStyler;
import net.jxta.socket.JxtaSocket;

import org.chaupal.jp2p.ui.property.AbstractUIPropertySource;
import org.chaupal.jp2p.ui.property.descriptors.CheckBoxPropertyDescriptor;
import org.chaupal.jp2p.ui.property.descriptors.SpinnerPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class JxtaSocketPropertySource extends AbstractUIPropertySource<JxtaSocket> {

	public enum JxtaSocketProperties implements IJp2pProperties{
		BOUND,
		CLOSED,
		CONNECTED,
		CHANNEL,
		CREDENTIAL_DOC,
		INET_ADDRESS,
		INPUT_SHUTDOWN,
		KEEP_ALIVE,
		LOCAL_ADDRESS,
		LOCAL_PORT,
		LOCAL_SOCKET_ADDRESS,
		OOB_INLINE,
		OUTPUT_SHUTDOWN,
		PORT,
		RECEIVED_BUFFER_SIZE,
		REMOTE_SOCKET_ADDRESS,
		RETRY_TIME_OUT,
		REUSE_ADDRESS,
		SEND_BUFFER_SIZE,
		SO_LINGER,
		SO_TIME_OUT,
		TCP_NO_DELAY,
		TRAFFIC_CLASS,
		VERIFIED_ADDRESS_SET,
		VERIFIED_CERT_SET,
		WINDOW_SIZE;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString());
		}
		/**
		 * Returns true if the given property is valid for this enumeration
		 * @param property
		 * @return
		 */
		public static boolean isValidProperty( IJp2pProperties property ){
			if( property == null )
				return false;
			for( JxtaSocketProperties prop: values() ){
				if( prop.equals( property ))
					return true;
			}
			return false;
		}

	}

	public JxtaSocketPropertySource( JxtaSocket source ) {
		super( source );
	}

	/**
	 * Provides an abstract description of the object, used for displays
	 */
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		for( JxtaSocketProperties property: JxtaSocketProperties.values() ){
			String[] parsed = super.parseProperty(property);
			PropertyDescriptor descriptor;
			SpinnerPropertyDescriptor spd;
			switch( property ){
			case BOUND:
			case CLOSED:
			case CONNECTED:
			case INPUT_SHUTDOWN:
			case KEEP_ALIVE:
			case OOB_INLINE:
			case OUTPUT_SHUTDOWN:
			case REUSE_ADDRESS:
			case TCP_NO_DELAY:
				descriptor = new CheckBoxPropertyDescriptor( property, parsed[1] );
				//spd = ( SpinnerPropertyDescriptor )descriptor;
				//spd.setEnabled( this.isEditable(property));
				break;				
			case LOCAL_PORT:
			case PORT:
			case RECEIVED_BUFFER_SIZE:
			case RETRY_TIME_OUT:
			case SEND_BUFFER_SIZE:
			case SO_LINGER:
			case SO_TIME_OUT:
			case TRAFFIC_CLASS:
			case WINDOW_SIZE:
				descriptor = new SpinnerPropertyDescriptor( property, parsed[1], 0, Integer.MAX_VALUE );
				spd = ( SpinnerPropertyDescriptor )descriptor;
				spd.setEnabled( this.isEditable(property));
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
		if(!( id instanceof JxtaSocketProperties ))
			return null;
		if( !JxtaSocketProperties.isValidProperty( (IJp2pProperties) id ))
			return null;
		JxtaSocketProperties property = ( JxtaSocketProperties )id;
		JxtaSocket socket = (JxtaSocket) super.getModule();
		if( socket == null )
			return null;
		try{
		switch( property ){
		case BOUND:
			return socket.isBound();
		case CLOSED:
			return socket.isClosed();
		case CONNECTED:
			return socket.isConnected();
		case INPUT_SHUTDOWN:
			return socket.isInputShutdown();
		case KEEP_ALIVE:
			return socket.getKeepAlive();
		case OOB_INLINE:
			return socket.getOOBInline();
		case OUTPUT_SHUTDOWN:
			return socket.isOutputShutdown();
		case REUSE_ADDRESS:
			return socket.getReuseAddress();
		case TCP_NO_DELAY:
			return socket.getTcpNoDelay();
		case LOCAL_PORT:
			return socket.getLocalPort();
		case PORT:
			return socket.getPort();
		case RECEIVED_BUFFER_SIZE:
			return socket.getReceiveBufferSize();
		case RETRY_TIME_OUT:
			return socket.getRetryTimeout();
		case SEND_BUFFER_SIZE:
			return socket.getSendBufferSize();
		case SO_LINGER:
			return socket.getSoLinger();
		case SO_TIME_OUT:
			return socket.getSoTimeout();
		case TRAFFIC_CLASS:
			return socket.getTrafficClass();
		case WINDOW_SIZE:
			return socket.getWindowSize();
		case CHANNEL:
			return socket.getChannel();
		case CREDENTIAL_DOC:
			return socket.getCredentialDoc();
		case INET_ADDRESS:
			return socket.getInetAddress();
		case LOCAL_ADDRESS:
			return socket.getLocalAddress();
		case LOCAL_SOCKET_ADDRESS:
			return socket.getLocalSocketAddress();
		case REMOTE_SOCKET_ADDRESS:
			return socket.getRemoteSocketAddress();
		case VERIFIED_ADDRESS_SET:
			return socket.getVerifiedAddressSet();
		case VERIFIED_CERT_SET:
			return socket.getVerifiedCertSet();
		default:
			return null;
		}
		}
		catch( Exception ex ){
			ex.printStackTrace();
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
		if(!( id instanceof JxtaSocketProperties ))
			return false;
		return this.isEditable( ( JxtaSocketProperties )id);
	}

	/**
	 * Currently not needed, there is no editing of properties
	 * @param id
	 * @param value
	 */
	@Override
	public void setPropertyValue(Object id, Object value) {
		if( !JxtaSocketProperties.isValidProperty( (IJp2pProperties) id ))
			return;
		JxtaSocketProperties property = ( JxtaSocketProperties )id;
		JxtaSocket socket = super.getModule();
		switch( property ){
		case CHANNEL:
			break;
		case CREDENTIAL_DOC:
			//socket.setCode((String) value);
			break;
		case INET_ADDRESS:
			//socket.setadv.setCompat( (Element) value);
			break;
		case LOCAL_ADDRESS:
			//adv.setDescription((String) value);
			break;
		case LOCAL_PORT:
			break;
		case LOCAL_SOCKET_ADDRESS:
			//adv.setModuleSpecID((ModuleSpecID) value);
			break;
		case PORT:
			//adv.setParam((Element) value);
			break;
		case RECEIVED_BUFFER_SIZE:
			break;
		case REMOTE_SOCKET_ADDRESS:
			//adv.setProvider((String) value);
			break;
		case SEND_BUFFER_SIZE:
			break;
		case SO_LINGER:
			break;
		case SO_TIME_OUT:
			//adv.setUri((String) value);
			break;
		}		
	}
}