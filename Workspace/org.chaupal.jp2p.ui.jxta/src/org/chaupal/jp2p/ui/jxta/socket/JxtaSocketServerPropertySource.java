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
import net.jxta.socket.JxtaServerSocket;

import org.chaupal.jp2p.ui.property.descriptors.CheckBoxPropertyDescriptor;
import org.chaupal.jp2p.ui.property.descriptors.IControlPropertyDescriptor;
import org.chaupal.jp2p.ui.property.descriptors.SpinnerPropertyDescriptor;
import org.chaupal.jp2p.ui.property.descriptors.TextBoxPropertyDescriptor;
import org.chaupal.jp2p.ui.property.AbstractUIPropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class JxtaSocketServerPropertySource extends AbstractUIPropertySource<JxtaServerSocket> {

	public enum JxtaSocketProperties implements IJp2pProperties{
		BOUND,
		CLOSED,
		ENCRYPTED,
		CHANNEL,
		PEER_GROUP,
		INET_ADDRESS,
		LOCAL_PORT,
		LOCAL_SOCKET_ADDRESS,
		PIPE_ADVERTISEMENT,
		RECEIVED_BUFFER_SIZE,
		REUSE_ADDRESS,
		SO_TIME_OUT;

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

	public JxtaSocketServerPropertySource( JxtaServerSocket source ) {
		super( source );
		//source.
	}

	/**
	 * Provides an abstract description of the object, used for displays
	 */
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		for( JxtaSocketProperties property: JxtaSocketProperties.values() ){
			String[] parsed = super.parseProperty(property);
			IControlPropertyDescriptor<?> tpd;
			switch( property ){
			case BOUND:
			case CLOSED:
			case ENCRYPTED:
			case REUSE_ADDRESS:
				tpd = new CheckBoxPropertyDescriptor( property, parsed[1] );
				break;				
			case LOCAL_PORT:
			case RECEIVED_BUFFER_SIZE:
			case SO_TIME_OUT:
				tpd = new SpinnerPropertyDescriptor( property, parsed[1], 0, Integer.MAX_VALUE );
				break;				
			case PIPE_ADVERTISEMENT:
			case INET_ADDRESS:
				tpd = new TextBoxPropertyDescriptor( property, parsed[1]);
				break;
			default:
				tpd = new TextBoxPropertyDescriptor( property, parsed[1]);
				break;
			}	
			tpd.setEnabled( this.isEditable( property));
			PropertyDescriptor descriptor = (PropertyDescriptor) tpd;
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
			return super.getPropertyValue(id);
		if( !JxtaSocketProperties.isValidProperty( (IJp2pProperties) id ))
			return super.getPropertyValue(id);
		JxtaSocketProperties property = ( JxtaSocketProperties )id;
		JxtaServerSocket socket = (JxtaServerSocket) super.getModule();
		if( socket == null )
			return null;
		try{
			switch( property ){
			case BOUND:
				return socket.isBound();
			case CHANNEL:
				return socket.getChannel();
			case CLOSED:
				return socket.isClosed();
			case ENCRYPTED:
				return socket.isEncrypted();
			case INET_ADDRESS:
				return socket.getInetAddress();
			case LOCAL_PORT:
				return socket.getLocalPort();
			case LOCAL_SOCKET_ADDRESS:
				return socket.getLocalSocketAddress();
			case PEER_GROUP:
				return socket.getGroup();
			case PIPE_ADVERTISEMENT:
				return socket.getPipeAdv();
			case RECEIVED_BUFFER_SIZE:
				return socket.getReceiveBufferSize();
			case REUSE_ADDRESS:
				return socket.getReuseAddress();
			case SO_TIME_OUT:
				return socket.getSoTimeout();
			}		
		}
		catch( Exception ex ){
			return ex.getMessage();
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
		if( !JxtaSocketProperties.isValidProperty( (IJp2pProperties) id ))
			return false;
		JxtaSocketProperties property = ( JxtaSocketProperties )id;
		boolean retval = false;
		switch( property ){
		case ENCRYPTED:
		case RECEIVED_BUFFER_SIZE:
		case REUSE_ADDRESS:
		case SO_TIME_OUT:
			retval = true;
			break;
		default:
			break;
		}
		return retval;
	}

	/**
	 * Currently not needed, there is no editing of properties
	 * @param id
	 * @param value
	 */
	@Override
	public void setPropertyValue(Object id, Object value) {
		if(!this.isEditable(id))
			return;
		JxtaSocketProperties property = ( JxtaSocketProperties )id;
		JxtaServerSocket socket = super.getModule();
		try{
			switch( property ){
			case ENCRYPTED:
				socket.setEncrypted((boolean) value);
				break;
			case RECEIVED_BUFFER_SIZE:
				socket.setReceiveBufferSize( (int) value);
				break;
			case REUSE_ADDRESS:
				socket.setReuseAddress( (boolean) value);
				break;
			case SO_TIME_OUT:
				socket.setSoTimeout((int) value);
				break;
			default:
				break;
			}
		}
		catch( Exception ex){
			ex.printStackTrace();
		}
	}
}