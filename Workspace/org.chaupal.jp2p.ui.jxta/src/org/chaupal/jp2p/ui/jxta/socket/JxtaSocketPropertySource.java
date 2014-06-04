/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.socket;

import net.jp2p.container.utils.StringStyler;
import net.jxta.socket.JxtaSocket;

import org.chaupal.jp2p.ui.jxta.property.AbstractJp2pUIPropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class JxtaSocketPropertySource extends AbstractJp2pUIPropertySource<JxtaSocket> {

	public enum JxtaSocketProperties{
		CHANNEL,
		CREDENTIAL_DOC,
		INET_ADDRESS,
		DOCUMENT,
		DESCRIPTION,
		INDEX_FIELDS,
		MODULE_SPEC_ID,
		PARAM,
		PRIVILIGED_DOCUMENT,
		PROVIDER,
		SIGNATURE,
		SIGNED_DOCUMENT,
		SPEC_URI;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString());
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
		return super.getPropertyDescriptors( JxtaSocketProperties.values());
	}

	@Override
	public Object getPropertyValue(Object id) {
		JxtaSocketProperties property = ( JxtaSocketProperties )id;
		JxtaSocket socket = super.getModule();
		switch( property ){
		case CHANNEL:
			return socket.getChannel();
		case CREDENTIAL_DOC:
			return socket.getCredentialDoc();
		case INET_ADDRESS:
			return socket.getCompat();
		case DESCRIPTION:
			return socket.getDescription();
		case DOCUMENT:
			return socket.getDesc();
		case INDEX_FIELDS:
			return socket.getIndexFields();
		case MODULE_SPEC_ID:
			return socket.getModuleSpecID();
		case PARAM:
			return socket.getParam();
		case PRIVILIGED_DOCUMENT:
			return socket.getDescPriv();
		case PROVIDER:
			return socket.getProvider();
		case SIGNATURE:
			return socket.getSignature();
		case SIGNED_DOCUMENT:
			return socket.getSignedDocument();
		case SPEC_URI:
			return socket.getUri();
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
		JxtaSocketProperties property = ( JxtaSocketProperties )id;
		switch( property ){
		case CHANNEL:
		case INDEX_FIELDS:
		case PRIVILIGED_DOCUMENT:
		case SIGNATURE:
		case SIGNED_DOCUMENT:
			return false;	
		default:
			return true;
		}
	}

	/**
	 * Currently not needed, there is no editing of properties
	 * @param id
	 * @param value
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void setPropertyValue(Object id, Object value) {
		JxtaSocketProperties property = ( JxtaSocketProperties )id;
		JxtaSocket adv = super.getModule();
		switch( property ){
		case CHANNEL:
			break;
		case CREDENTIAL_DOC:
			adv.setCode((String) value);
			break;
		case INET_ADDRESS:
			adv.setCompat( (Element) value);
			break;
		case DESCRIPTION:
			adv.setDescription((String) value);
			break;
		case DOCUMENT:
			adv.setDesc((Element) value);
			break;
		case INDEX_FIELDS:
			break;
		case MODULE_SPEC_ID:
			adv.setModuleSpecID((ModuleSpecID) value);
			break;
		case PARAM:
			adv.setParam((Element) value);
			break;
		case PRIVILIGED_DOCUMENT:
			break;
		case PROVIDER:
			adv.setProvider((String) value);
			break;
		case SIGNATURE:
			break;
		case SIGNED_DOCUMENT:
			break;
		case SPEC_URI:
			adv.setUri((String) value);
			break;
		}		
	}
}