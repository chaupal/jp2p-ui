/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.util;

import net.jp2p.container.utils.StringStyler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class ColorUtils {

	public enum SupportedColors{
		COLOR_BLACK,
		COLOR_DARK_RED,
		COLOR_DARK_GREEN,
		COLOR_DARK_BLUE,
		COLOR_DARK_MAGENTA,
		COLOR_DARK_CYAN,
		COLOR_DARK_GRAY,
		COLOR_GRAY,
		COLOR_RED,
		COLOR_GREEN,
		COLOR_YELLOW,
		COLOR_BLUE,
		COLOR_MAGENTA,
		COLOR_CYAN,
		COLOR_WHITE;

		public static int convertToSWT( SupportedColors color ){
			switch( color ){
			case COLOR_BLACK:
				return SWT.COLOR_BLACK;
			case COLOR_BLUE:
				return SWT.COLOR_BLUE;
			case COLOR_CYAN:
				return SWT.COLOR_CYAN;
			case COLOR_DARK_BLUE:
				return SWT.COLOR_DARK_BLUE;
			case COLOR_DARK_CYAN:
				return SWT.COLOR_DARK_CYAN;
			case COLOR_DARK_GRAY:
				return SWT.COLOR_DARK_GRAY;
			case COLOR_DARK_GREEN:
				return SWT.COLOR_DARK_GREEN;
			case COLOR_DARK_MAGENTA:
				return SWT.COLOR_DARK_MAGENTA;
			case COLOR_DARK_RED:
				return SWT.COLOR_DARK_RED;
			case COLOR_GRAY:
				return SWT.COLOR_GRAY;
			case COLOR_GREEN:
				return SWT.COLOR_GREEN;
			case COLOR_MAGENTA:
				return SWT.COLOR_MAGENTA;
			case COLOR_RED:
				return SWT.COLOR_RED;
			case COLOR_YELLOW:
				return SWT.COLOR_YELLOW;
			case COLOR_WHITE:
				return SWT.COLOR_WHITE;
			default:
				return SWT.COLOR_BLACK;
			}
		}

		public static SupportedColors convertfromSWT( int swtColor ){
			switch( swtColor ){
			case SWT.COLOR_BLACK:
				return SupportedColors.COLOR_BLACK;
			case SWT.COLOR_BLUE:
				return SupportedColors.COLOR_BLUE;
			case SWT.COLOR_CYAN:
				return SupportedColors.COLOR_CYAN;
			case SWT.COLOR_DARK_BLUE:
				return SupportedColors.COLOR_DARK_BLUE;
			case SWT.COLOR_DARK_CYAN:
				return SupportedColors.COLOR_DARK_CYAN;
			case SWT.COLOR_DARK_GRAY:
				return SupportedColors.COLOR_DARK_GRAY;
			case SWT.COLOR_DARK_GREEN:
				return SupportedColors.COLOR_DARK_GREEN;
			case SWT.COLOR_DARK_MAGENTA:
				return SupportedColors.COLOR_DARK_MAGENTA;
			case SWT.COLOR_DARK_RED:
				return SupportedColors.COLOR_DARK_RED;
			case SWT.COLOR_GRAY:
				return SupportedColors.COLOR_GRAY;
			case SWT.COLOR_GREEN:
				return SupportedColors.COLOR_GREEN;
			case SWT.COLOR_MAGENTA:
				return SupportedColors.COLOR_MAGENTA;
			case SWT.COLOR_RED:
				return SupportedColors.COLOR_RED;
			case SWT.COLOR_YELLOW:
				return SupportedColors.COLOR_YELLOW;
			case SWT.COLOR_WHITE:
				return SupportedColors.COLOR_WHITE;
			default:
				return SupportedColors.COLOR_BLACK;
			}
		}

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString() );
		}
		
		
	}
	
	public static Color getSWTColor( Display display, SupportedColors color ) {
		return display.getSystemColor( SupportedColors.convertToSWT( color ));
	}

	/**
	 * Get the next color, but skipping the white
	 * @param display
	 * @param color
	 * @return
	 */
	public static SupportedColors getNextColor( SupportedColors color ){
		if( color == null )
			return SupportedColors.COLOR_BLUE;
		int ordinal = color.ordinal() + 1;
		if( ordinal >= SupportedColors.COLOR_WHITE.ordinal() )
			ordinal = 0;
		return SupportedColors.values()[ ordinal ];
	}

	/**
	 * Get the next color, but skipping the white
	 * @param display
	 * @param color
	 * @return
	 */
	public static Color getNextColor( Display display, SupportedColors color ){
		if( color == null )
			return getSWTColor( display, SupportedColors.COLOR_BLACK );
		return getSWTColor( display, getNextColor( color ));
	}
}
