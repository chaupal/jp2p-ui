/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.util;

import net.jp2p.container.properties.IJp2pProperties;
import net.jp2p.container.utils.StringProperty;

public class CategoryStringProperty extends StringProperty {

	private String category;

	public CategoryStringProperty( String key, String category ) {
		super( key );
		this.category = category;
	}

	/**
	 * Get the name extended with the category
	 * @return
	 */
	public String getExtendedName() {
		return category + "." + super.name();
	}

	public String getCategory(){
		return this.category;
	}
	
	@Override
	public String toString() {
		return getExtendedName();
	}
	
	/**
	 * Retuens true if the given property is a categoery string property and the categories match
	 * @param property
	 * @param category
	 * @return
	 */
	public static boolean hasCategory( IJp2pProperties property, String category ){
		if( !( property instanceof CategoryStringProperty))
			return false;
		CategoryStringProperty csp = (CategoryStringProperty) property;
		return csp.getCategory().equals( category );
	}
}