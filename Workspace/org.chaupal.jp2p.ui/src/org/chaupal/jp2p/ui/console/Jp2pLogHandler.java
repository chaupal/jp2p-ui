/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.console;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import net.jp2p.container.log.Jp2pLevel;

public class Jp2pLogHandler extends Handler {

	public Jp2pLogHandler() {
		this.setLevel( Jp2pLevel.getJxtaLevel() );
		super.setFormatter( new Formatter() {

		    private final SimpleDateFormat dateFormat =
		            new SimpleDateFormat("'['HH:mm:ss.SSS']'");

		    @Override
			public String format(LogRecord record) {
		    	Object[] parameters = record.getParameters();
		    	String identifier = (String) ((( parameters != null ) && ( parameters.length > 0 ) &&( parameters[0] instanceof String))? parameters[0]: null);  
		    	Calendar calendar = Calendar.getInstance();
		    	String logMessage = "JXTA ";
		    	if( identifier != null )
		    		logMessage += identifier;
		    	logMessage += " " + dateFormat.format( calendar.getTime() ) + ": " + record.getMessage() + "\n";
		    	return logMessage;
		    }
		});
	}

	@Override
	public void close() throws SecurityException {
	}

	@Override
	public void flush() {
	}

	@Override
	public void publish(LogRecord record) {
		if (!isLoggable(record))
			return;
		if( record.getLevel().intValue() > Jp2pLevel.getJxtaLevel().intValue() )
			return;
		// Output the formatted data to the file
		Jp2pConsole console = Jp2pConsole.getInstance();
		if( console == null )
			return;
     	console.println(getFormatter().format(record));
	}

}