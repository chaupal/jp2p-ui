/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.property.advertisement;

import net.jp2p.container.utils.StringStyler;
import net.jxta.document.Element;
import net.jxta.platform.ModuleSpecID;
import net.jxta.protocol.ModuleSpecAdvertisement;

import org.chaupal.jp2p.ui.jxta.property.AbstractJp2pUIPropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class ModuleSpecAdvPropertySource extends AbstractJp2pUIPropertySource<ModuleSpecAdvertisement> {

	public enum ModuleSpecAdvProperties{
		AUTHENTICATION_SPECIFICATION_ID,
		BASE_ADVERTISEMENT_TYPE,
		CREATOR,
		DOCUMENT,
		DESCRIPTION,
		INDEX_FIELDS,
		MODULE_SPEC_ID,
		NAME,
		PARAM,
		PROXY_SPEC_ID,
		SIGNATURE,
		SIGNED_DOCUMENT,
		SPEC_URI,
		VERSION;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString());
		}
	}

	public ModuleSpecAdvPropertySource( ModuleSpecAdvertisement source ) {
		super( source );
	}

	/**
	 * Provides an abstract description of the object, used for displays
	 */
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return super.getPropertyDescriptors( ModuleSpecAdvProperties.values());
	}

	@Override
	public Object getPropertyValue(Object id) {
		ModuleSpecAdvProperties property = ( ModuleSpecAdvProperties )id;
		ModuleSpecAdvertisement adv = super.getModule();
		switch( property ){
		case AUTHENTICATION_SPECIFICATION_ID:
			return adv.getAuthSpecID();
		case BASE_ADVERTISEMENT_TYPE:
			return adv.getBaseAdvType();
		case CREATOR:
			return adv.getCreator();
		case DESCRIPTION:
			return adv.getDescription();
		case DOCUMENT:
			return adv.getDesc();
		case INDEX_FIELDS:
			return adv.getIndexFields();
		case MODULE_SPEC_ID:
			return adv.getModuleSpecID();
		case NAME:
			return adv.getName();
		case PARAM:
			return adv.getParam();
		case PROXY_SPEC_ID:
			return adv.getProxySpecID();
		case SIGNATURE:
			return adv.getSignature();
		case SIGNED_DOCUMENT:
			return adv.getSignedDocument();
		case SPEC_URI:
			return adv.getSpecURI();
		case VERSION:
			return adv.getVersion();
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
		ModuleSpecAdvProperties property = ( ModuleSpecAdvProperties )id;
		switch( property ){
		case BASE_ADVERTISEMENT_TYPE:
		case INDEX_FIELDS:
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
		ModuleSpecAdvProperties property = ( ModuleSpecAdvProperties )id;
		ModuleSpecAdvertisement adv = super.getModule();
		switch( property ){
		case AUTHENTICATION_SPECIFICATION_ID:
			adv.setAuthSpecID((ModuleSpecID) value);
			break;
		case BASE_ADVERTISEMENT_TYPE:
			break;
		case CREATOR:
			adv.setCreator((String) value);
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
		case NAME:
			adv.setName((String) value);
			break;
		case PARAM:
			adv.setParam((Element) value);
			break;
		case PROXY_SPEC_ID:
			adv.setProxySpecID((ModuleSpecID) value);
			break;
		case SIGNATURE:
			break;
		case SIGNED_DOCUMENT:
			break;
		case SPEC_URI:
			adv.setSpecURI((String) value);
			break;
		case VERSION:
			adv.setVersion((String) value);
			break;
		}
		
	}

}
