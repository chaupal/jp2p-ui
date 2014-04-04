/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.osgi;

import net.jp2p.container.utils.StringStyler;

import org.chaupal.jp2p.ui.log.Jp2pLog;
import org.eclipselabs.osgi.ds.broker.service.AbstractPalaver;
import org.eclipselabs.osgi.ds.broker.service.AbstractProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class MessageBoxProvider extends AbstractProvider<String, String[], String> {

	public static final String S_MESSAGE_BOX_TITLE = "Message";

	public static final String S_WARNING_INTERRUPTED = "The thread was interrupted";
	public static final String S_ERR_TIME_OUT = "The bunlde takes too long to get a display";

	public static int TIME_OUT = 100;
	public static int MAX_TIME_OUT = 50;
	

	public enum MessageTypes{
		INFO,
		QUESTION,
		WARNING,
		ERROR;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString() );
		}	
	}

	public enum Answers{
		YES,
		NO;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString() );
		}	
	}

	private static MessageBoxProvider attendee = new MessageBoxProvider();
	
	private MessageBox dialog;
	boolean started = false;
	boolean response = false;

	private MessageBoxProvider() {
		super( new MessageBoxPalaver());
	}
	
	public static MessageBoxProvider getInstance(){
		return attendee;
	}

	@Override
	protected void onDataReceived( String[] datum ) {
		this.popupMessage( datum );
	}
	
	private int convertMessageTypeToSWTIcon( MessageTypes type ){
		int[] retval = new int[2];
		switch( type ){
		case INFO:
			retval[0] = SWT.ICON_INFORMATION;
			retval[1] =	SWT.OK;
			break;
		case WARNING:
			retval[0] = SWT.ICON_WARNING;
			retval[1] =	SWT.OK;
			break;
		case QUESTION:
			retval[0] = SWT.ICON_QUESTION;
			retval[1] =	SWT.YES | SWT.NO;
			break;
		case ERROR:
			retval[0] = SWT.ICON_ERROR;
			retval[1] =	SWT.OK;
			break;
		}
		return retval[0] | retval[1];
	}
	
	/**
	 * If the bundle that starts the display is not ready yet, then wait for this to complete
	 */
	private synchronized void waitForDisplay(){
		boolean displayReady = false;
		this.started = true;
		int index = 0;
		while( started ){
			displayReady = PlatformUI.isWorkbenchRunning();
			if( displayReady ){
				IWorkbench wb = PlatformUI.getWorkbench();
				displayReady = ( wb != null );
			}
			if( displayReady ){
				started = false;
				return;
			}
			try{
				Thread.sleep( TIME_OUT );
				if( index > MAX_TIME_OUT ){
					this.started = false;
					throw new RuntimeException( S_ERR_TIME_OUT );
				}
				index++;
			}
			catch( InterruptedException ex ){
				Jp2pLog.logInfo(S_WARNING_INTERRUPTED );
			}
		}
	}

	/**
	 * If the bundle that starts the display is not ready yet, then wait for this to complete
	 */
	private synchronized void waitForResponse(){
		while( !this.response ){		
			try{
				Thread.sleep( TIME_OUT );
			}
			catch( InterruptedException ex ){
				Jp2pLog.logInfo(S_WARNING_INTERRUPTED );
			}
		}
	}

	/**
	 * Handles a popup message in an orderly fashion
	 * @param message
	 */
	private synchronized void popupMessage( final String[] message ){
		this.waitForDisplay();
		response = false;
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				IWorkbench wb = PlatformUI.getWorkbench();
				final Shell shell = wb.getDisplay().getActiveShell();
				if( shell == null )
					return;
				
				MessageTypes type = MessageTypes.valueOf(message[0].toUpperCase());
				int setup = convertMessageTypeToSWTIcon( type );
				if( dialog == null ){
					dialog = new MessageBox( shell, setup );	
					dialog.setText( message[1] );
				}
				dialog.setMessage( message[2] );
				int retval = dialog.open();
				response = true;
				if(  retval != Window.CANCEL ){
					dialog = null; 
				}
			}
		});	
		this.waitForResponse();
	}
	
	public void finalise(){
		this.started = false;
		Thread.currentThread().interrupt();
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