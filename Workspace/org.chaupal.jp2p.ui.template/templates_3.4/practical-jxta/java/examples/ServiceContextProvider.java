/*******************************************************************************
 * Copyright (c) 2013 Condast.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Condast - initial API and implementation
 ******************************************************************************/
package $packageName$.examples;

import net.jp2p.container.AbstractJp2pContainer;
import net.jp2p.container.utils.StringStyler;

public class ServiceContextProvider{

	//private static final String S_DERBY_DRIVER_STRING = "org.apache.derby.jdbc.EmbeddedDriver"; 

	public enum Examples{
		EX_JXTA_BOOTSTRAPPING,
		EXAMPLE_100,
		EXAMPLE_110,
		EXAMPLE_120,
		EXAMPLE_150,
		EXAMPLE_160;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString() );
		}
	}
	
	/**
	 * Get the selected example
	 * @param example
	 * @param moduleContainer
	 * @return
	 */
	public static AbstractJp2pContainer<?> getExample( Examples example ){
		switch( example ){
		case EX_JXTA_BOOTSTRAPPING:
			return new JXTA_Bootstrapping();
		case EXAMPLE_100:
			return new _100_Starting_And_Stopping_JXTA_Example();
		case EXAMPLE_110:
			return new _110_Creating_A_Local_Configuration_Example();
		case EXAMPLE_120:
			return new _120_Retrieving_Modifying_And_Saving_An_Existing_Configuration_Example();
		case EXAMPLE_150:
			return new _150_Configuration_Objects();
		default:
			return null;
		}
	}
}
