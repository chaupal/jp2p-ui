/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
/**
 * 
 */
package org.chaupal.jp2p.ui.template;

import java.util.Locale;

/**
 * @author Marine
 *
 */
public class TemplateUtil {
	
	/**
	 * Formate package name
	 * 
	 * @param id
	 * @return
	 */
	public static String getFormattedPackageName(String id) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < id.length(); i++) {
			char ch = id.charAt(i);
			if (buffer.length() == 0) {
				if (Character.isJavaIdentifierStart(ch))
					buffer.append(Character.toLowerCase(ch));
			} else {
				if (Character.isJavaIdentifierPart(ch) || ch == '.')
					buffer.append(ch);
			}
		}
		return buffer.toString().toLowerCase(Locale.ENGLISH);
	}
	
	/**
	 * Validate input class name
	 * @param className
	 */
	public static boolean validateClassName(String className) {
		if (className == null || className.trim().equals("")) {
			return false;
		}
		for (int i = 0; i < className.length(); i++) {
			char ch = className.charAt(i);
			if ( ! Character.isJavaIdentifierPart(ch)) {
				return false;
			}
		}
		return true;
	}
}
