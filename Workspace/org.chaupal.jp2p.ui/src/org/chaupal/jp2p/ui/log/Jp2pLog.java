/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.log;

import java.util.logging.LogRecord;

import org.chaupal.jp2p.ui.Activator;
import org.eclipse.core.runtime.IStatus;

public class Jp2pLog {

	public static final String BUNDLE_ID = "org.chaupal.jp2p.ui.jxta";

	/**
	 * Log an info message
	 * @param message
	 */
	public static void logInfo( String message ){
		log( IStatus.INFO, IStatus.OK, message );
	}

	/**
	 * Log a warning message
	 * @param message
	 */
	public static void logWarning( String message ){
		log( IStatus.WARNING, IStatus.OK, message );
	}

	/**
	 * Log an error message
	 * @param message
	 */
	public static void logError( String message, Throwable exception ){
		log( IStatus.ERROR, IStatus.OK, message, exception );
	}

	/**
	 * Log an error message
	 * @param message
	 */
	public static void logError( Throwable exception ){
		logError( "An unexpected excpetion was thrown", exception );
		exception.printStackTrace();
	}

	/**
	 * Log an error message
	 * @param message
	 */
	public static void logJp2p( LogRecord record ){
		log( IStatus.INFO, IStatus.OK, record.getMessage() );
	}

	public static void log( int severity, int code, String message ){
		
		Activator.getLog().log( severity, message );
	}

	public static void log( int severity, int code, String message, Throwable exception ){
		
		Activator.getLog().log( severity, message, exception);
	}
}
