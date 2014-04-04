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
import net.jxta.protocol.ModuleImplAdvertisement;

import org.chaupal.jp2p.ui.jxta.property.AbstractJp2pUIPropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class ModuleImplAdvPropertySource extends AbstractJp2pUIPropertySource<ModuleImplAdvertisement> {

	public enum ModuleImplAdvProperties{
		BASE_ADVERTISEMENT_TYPE,
		CODE,
		COMPAT,
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

	public ModuleImplAdvPropertySource( ModuleImplAdvertisement source ) {
		super( source );
	}

	/**
	 * Provides an abstract description of the object, used for displays
	 */
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return super.getPropertyDescriptors( ModuleImplAdvProperties.values());
	}

	@Override
	public Object getPropertyValue(Object id) {
		ModuleImplAdvProperties property = ( ModuleImplAdvProperties )id;
		ModuleImplAdvertisement adv = super.getModule();
		switch( property ){
		case BASE_ADVERTISEMENT_TYPE:
			return adv.getBaseAdvType();
		case CODE:
			return adv.getCode();
		case COMPAT:
			return adv.getCompat();
		case DESCRIPTION:
			return adv.getDescription();
		case DOCUMENT:
			return adv.getDesc();
		case INDEX_FIELDS:
			return adv.getIndexFields();
		case MODULE_SPEC_ID:
			return adv.getModuleSpecID();
		case PARAM:
			return adv.getParam();
		case PRIVILIGED_DOCUMENT:
			return adv.getDescPriv();
		case PROVIDER:
			return adv.getProvider();
		case SIGNATURE:
			return adv.getSignature();
		case SIGNED_DOCUMENT:
			return adv.getSignedDocument();
		case SPEC_URI:
			return adv.getUri();
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
		ModuleImplAdvProperties property = ( ModuleImplAdvProperties )id;
		switch( property ){
		case BASE_ADVERTISEMENT_TYPE:
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
		ModuleImplAdvProperties property = ( ModuleImplAdvProperties )id;
		ModuleImplAdvertisement adv = super.getModule();
		switch( property ){
		case BASE_ADVERTISEMENT_TYPE:
			break;
		case CODE:
			adv.setCode((String) value);
			break;
		case COMPAT:
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