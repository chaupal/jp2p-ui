/*******************************************************************************
 * Copyright 2014 Chaupal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Kees Pieters - initial API and implementation
 *******************************************************************************/
package org.chaupal.jp2p.ui.property;

import java.lang.reflect.Type;

import org.eclipselabs.osgi.ds.broker.util.StringStyler;

import net.jp2p.container.properties.IJp2pProperties;

public class ObjectProperty implements IJp2pProperties  {

	public enum SupportedTypes{
		BOOLEAN,
		ENUM,
		INTEGER,
		STRING;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString());
		}
	}
	
	private Object value;
	private SupportedTypes type;
	private String key;

	public ObjectProperty(String key, Type type, Object value ) {
		this.key = key;
		this.value = value;
		this.type = getSupportedTypes( type );
	}

	final SupportedTypes getType() {
		return type;
	}


	final Object getValue() {
		return value;
	}
	
	
	@Override
	public String name() {
		return key.toUpperCase();
	}

	@Override
	public String toString() {
		return this.key;
	}

	/**
	 * Convert the reflection type to a supported type
	 * @param type
	 * @return
	 */
	public static final SupportedTypes getSupportedTypes( Type type ){
		if(( type.equals( boolean.class )) || ( type.equals( Boolean.TYPE )))
			return SupportedTypes.BOOLEAN;
		if( type.equals( int.class ) || type.equals( Integer.TYPE ))
			return SupportedTypes.INTEGER;
		if( type.equals( Enum.class ))
			return SupportedTypes.ENUM;
		return SupportedTypes.STRING;
	}
}
