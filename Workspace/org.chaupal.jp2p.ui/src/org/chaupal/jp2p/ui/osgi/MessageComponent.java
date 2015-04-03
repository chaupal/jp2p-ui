/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.osgi;

import org.chaupal.jp2p.ui.message.Jp2pMessageBox;

import net.jxse.osgi.message.IJxseMessagePrinter;

public class MessageComponent implements IJxseMessagePrinter{

	private Jp2pMessageBox mp = null;
	
	public void activate(){
		mp = new Jp2pMessageBox();
	}
	
	public void deactivate(){
		mp.finalise();
	}
	

	@Override
	public void printMessage(String[] message) {
		mp.popupMessage( message );
	}

	@Override
	public void printMessage(MessageTypes type, String title, String message) {
		String[] strarray = new String[3];
		strarray[0] = type.toString();
		strarray[1] = title;
		strarray[2] = message;
		this.printMessage( strarray );
	}

	@Override
	public int askQuestion(String title, String message) {
		String[] strarray = new String[3];
		strarray[0] = MessageTypes.QUESTION.name();
		strarray[1] = title;
		strarray[2] = message;
		mp.popupMessage( strarray );
		return mp.getResult();
	}

	@Override
	public int getPriority() {
		return 1;
	}

	@Override
	public String getIdentifier() {
		return "hallo"; //TODO CP: change
	}
}
