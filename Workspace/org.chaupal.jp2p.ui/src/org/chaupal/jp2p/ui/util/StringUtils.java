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
package org.chaupal.jp2p.ui.util;

public class StringUtils {

	public static final String S_HASH = "#";
	public static final String S_DOUBLE_SLASH = "//";
	public static final String S_COMMENT = "/*";

	/**
	 * 
	 * @param expression
	 * @param line
	 * @return
	 */
	public static String getComment( String expression, String line ){
		int comment = line.indexOf( expression );
		if (comment == -1)
			return null;
		
		String str = line.substring(0, comment);
		str = str.trim();
		if (0 == line.length())
			return null;
		return str;	
	}

	/**
	 * 
	 * @param expression
	 * @param line
	 * @return
	 */
	public static boolean isComment( String expression, String line ){
		int comment = line.indexOf( expression );
		if (comment == -1)
			return false;
		
		String str = line.substring(0, comment);
		str = str.trim();
		return (0 == line.length());
	}

}
