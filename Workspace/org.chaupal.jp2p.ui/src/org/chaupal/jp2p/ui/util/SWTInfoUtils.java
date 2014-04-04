/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.util;

import org.eclipse.swt.SWT;
import org.eclipselabs.osgi.ds.broker.util.StringStyler;

public class SWTInfoUtils {

	/**
	 * Get the various info types
	 * @author Kees
	 *
	 */
	public enum InfoTypes{
		CANCEL,
		ERROR,
		SEARCH,
		INFO,
		QUESTION,
		WARNING,
		WORKING;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString());
		}
	}

	/**
	 * Get the image code
	 * @param type
	 * @return
	 */
	public static int getImageCode( InfoTypes type ){
		switch( type ){
		case QUESTION:
			return SWT.ICON_QUESTION;
		case ERROR:
			return SWT.ICON_ERROR;			  
		case CANCEL:
			return SWT.ICON_CANCEL;
		case INFO:
			return SWT.ICON_INFORMATION;
		case SEARCH:
			return SWT.ICON_SEARCH;
		case WARNING:
			return SWT.ICON_WARNING;
		case WORKING:
			return SWT.ICON_WORKING;
		default:
			return SWT.ICON_ERROR;
		}
	}
}