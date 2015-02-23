/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.message;

import org.eclipselabs.osgi.ds.broker.service.AbstractPalaver;
import org.eclipselabs.osgi.ds.broker.service.AbstractProvider;

public class MessageBoxProvider extends AbstractProvider<String, String[], String> {

	public static final String S_MESSAGE_BOX_TITLE = "Message";

	public static final String S_WARNING_INTERRUPTED = "The thread was interrupted";
	public static final String S_ERR_TIME_OUT = "The bundle takes too long to get a display";

	public static int TIME_OUT = 100;
	public static int MAX_TIME_OUT = 50;
	

	private static MessageBoxProvider attendee = new MessageBoxProvider();
		
	private Jp2pMessageBox messageBox;

	private MessageBoxProvider() {
		super( new MessageBoxPalaver());
		this.messageBox = new Jp2pMessageBox();
	}
		
	public static MessageBoxProvider getInstance(){
		return attendee;
	}

	@Override
	protected void onDataReceived( String[] datum ) {
		this.messageBox.popupMessage( datum );
	}
	
	public void finalise(){
		this.messageBox.finalise();
	}
	
	private static class MessageBoxPalaver extends AbstractPalaver<String>{

		protected MessageBoxPalaver() {
			super("JXTAMessage");
		}

		@Override
		public String giveToken() {
			return "messageBoxToken";
		}

		@Override
		public boolean confirm(Object token) {
			return ( token instanceof String );
		}	
	}
	
	
}