/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.console;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.chaupal.jp2p.ui.util.ColorUtils;
import org.chaupal.jp2p.ui.util.ColorUtils.SupportedColors;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class Jp2pConsole extends MessageConsole {

	public static final String S_CONSOLE_NAME = "Jxta Console";

	private PrintStream ps;
	private StreamData current;

	private static Jp2pConsole console;
	
	private String source;
	
	private Map<String, StreamData> coding;
	
	public Jp2pConsole() {
		this( S_CONSOLE_NAME );
		coding = new HashMap<String, StreamData>();
	}

	public Jp2pConsole(String name ) {
		super(name, null );
		console = this;
	}

	public static Jp2pConsole getInstance(){
		if( console == null ){
			console = new Jp2pConsole();
			console.init();
			console.activate();
		}
		return console;
	}
	
	public Jp2pConsole(String name, ImageDescriptor imageDescriptor,
			boolean autoLifecycle) {
		super(name, imageDescriptor, autoLifecycle);
	}

	public Jp2pConsole(String name, String consoleType,
			ImageDescriptor imageDescriptor, boolean autoLifecycle) {
		super(name, consoleType, imageDescriptor, autoLifecycle);
	}

	public Jp2pConsole(String name, String consoleType,
			ImageDescriptor imageDescriptor, String encoding,
			boolean autoLifecycle) {
		super(name, consoleType, imageDescriptor, encoding, autoLifecycle);
	}

	public String getSource() {
		return source;
	}

	public void setSource( String source ) {
		this.source = source;
	}

	@Override
	protected void init() {
		this.source = S_CONSOLE_NAME;
		this.current = getStream( this.source );
		super.init();
	}

	/**
	 * Get a new stream
	 * @param identifier
	 * @return
	 */
	protected StreamData getStream( String identifier ){
		StreamData data = this.coding.get( identifier );
		if( data != null ){
			if( identifier.equals( this.source ))
				return data;
			this.changeStream(data);
			return data;
		}
		data = new StreamData();
		data.stream = this.newMessageStream();
		data.stream.setActivateOnWrite(true);
		SupportedColors color = ( this.current == null )? SupportedColors.COLOR_BLACK: this.current.color; 
		data.color = ColorUtils.getNextColor( color );
		this.current = data;
		
		data.stream.setColor( ColorUtils.getSWTColor( Display.getDefault(), this.current.color ));
		coding.put( identifier, data );
		this.changeStream(data);
		return data;	
	}
	
	/**
	 * Change the stream by rerouting the sys.err and sys.out to a different print stream
	 * @param stream
	 */
	protected void changeStream( StreamData data ){
		if( ps != null ){
			ps.flush();
			ps.close();
		}
		ps = new PrintStream( data.stream);
		System.setOut( ps );
		System.setErr( ps );		
	}
	
	@Override
	protected void dispose() {
		Iterator<StreamData> iterator = this.coding.values().iterator();
		StreamData data;
		while( iterator.hasNext()){
			data = iterator.next();
			try {
				Color color = data.stream.getColor();
				data.stream.flush();
				data.stream.close();
				color.dispose();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.dispose();
	}

	public void print( String msg ){
		current.stream.print( msg );
	}

	public void println( String msg ){
		current.stream.println( msg );
	}

	public void println(){
		current.stream.println();
	}
}

class StreamData{
	MessageConsoleStream stream;
	SupportedColors color;
}
