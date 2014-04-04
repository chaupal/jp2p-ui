/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.tray;

import java.util.ArrayList;
import java.util.Collection;

import org.chaupal.jp2p.ui.util.SWTInfoUtils;
import org.chaupal.jp2p.ui.util.SWTInfoUtils.InfoTypes;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchWindow;

public class TrayController {

	private static final String S_EXIT = "Exit";

	private static final int SLEEP_TIME = 100;
	private static final int MAX_COUNT = 100;
	
	private IWorkbenchWindow window;
	private TrayItem trayItem;
	private ToolTip tip;
	private Menu menu;
	
	private Image image = null;
	private int config;
	private boolean clicked;
	
	private Collection<IMenuContribution> contributions;
	
	public TrayController( IWorkbenchWindow window, Image image, int config ) {
		super();
		this.window = window;
		this.config = config;
		this.image = image;
		this.clicked = false;
		contributions = new ArrayList<IMenuContribution>();
	}

	public void addMenuContribution( IMenuContribution contribution ){
		this.contributions.add( contribution );
	}

	public void removeMenuContribution( IMenuContribution contribution ){
		this.contributions.remove( contribution );
	}

	public boolean isClicked() {
		return clicked;
	}
	/**
	 * set the text for the balloon
	 * @param text
	 * @param type
	 */
	public void setText( final InfoTypes type, final String text){
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
			    Shell shell = window.getShell();
				int imageCode = SWTInfoUtils.getImageCode( type ); 
			    tip = new ToolTip( shell, config | imageCode );
			    tip.addSelectionListener( new SelectionListener(){

					@Override
					public void widgetSelected(SelectionEvent e) {
						clicked = true;
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {/* DO NOTHING */}
			    	
			    });
				tip.setText( text );
				tip.setVisible(true);
				trayItem.setToolTip(tip);	
			    Display.getDefault().asyncExec( new Runnable(){

					@Override
					public void run() {
						waitForCompletion();
						tip.setVisible(false);
						trayItem.setToolTip(null);
					}
			    });
			}
		});
	}
	
	/**
	 * Wait until the balloon has been clicked., or the timeout has passed
	 */
	private void waitForCompletion(){
		int count = MAX_COUNT;
		while((!clicked ) && ( count > 0 )){
			try{
			  Thread.sleep( SLEEP_TIME );
			  count--;
			}
			catch( InterruptedException ex ){
				
			}
		}
	}	
	
	/**
	 * execute the update handler
	 * @param agent
	 * @param monitor
	 */
	public void createTray( ) {

	    trayItem = initTaskItem(window);
	    // Some OS might not support tray items
	    if (trayItem != null) {
	      minimizeBehavior();
	      // create exit and about action on the icon
	      hookPopupMenu();
	    }
	}

	  // add a listener to the shell

	  private void minimizeBehavior() {
	    window.getShell().addShellListener(new ShellAdapter() {
	      // If the window is minimized hide the window
	      @Override
		public void shellIconified(ShellEvent e) {
	        window.getShell().setVisible(false);
	      }
	    });
	    
	    // If user double-clicks on the tray icons the application will be
	    // visible again
	    trayItem.addSelectionListener(new SelectionAdapter() {
	      @Override
	      public void widgetSelected(SelectionEvent e) {
	        Shell shell = window.getShell();
	        if (!shell.isVisible()) {
	          window.getShell().setMinimized(false);
	          shell.setVisible(true);
	        }
	      }
	    });
	  }

	  // We hook up on menu entry which allows to close the application
	  private void hookPopupMenu() {
		  trayItem.addMenuDetectListener(new MenuDetectListener() {

			  @Override
			  public void menuDetected(MenuDetectEvent e) {
				  menu = new Menu(window.getShell(), SWT.POP_UP);
				  // creates a new menu item that terminates the program
				  MenuItem exit = new MenuItem(menu, SWT.NONE);
				  exit.setText( S_EXIT );
				  exit.addSelectionListener(new SelectionAdapter() {
					  @Override
					  public void widgetSelected(SelectionEvent e) {
						  window.getWorkbench().close();
					  }
				  });
				  for( final IMenuContribution contribution: contributions ){
					  if( contribution.isEnabled() ){
						  MenuItem item = new MenuItem(menu, SWT.NONE);
						  item.setData(contribution);
						  item.setText( contribution.getMenuLabel() );
						  item.addSelectionListener(new SelectionAdapter() {
							  @Override
							  public void widgetSelected(SelectionEvent e) {
								  contribution.performmMenuAction();
							  }
						  });
	
					  }
				  }
				  // make the menu visible
				  menu.setVisible(true);
			  }
		  });
	  }

	  // This methods create the tray item and return a reference
	  private TrayItem initTaskItem(IWorkbenchWindow window) {
	    Shell shell = window.getShell();
		final Tray tray = shell.getDisplay().getSystemTray();
	    TrayItem trayItem = new TrayItem(tray, SWT.NONE);
	    trayItem.setImage( image );
	    trayItem.setVisible(true);
	    return trayItem;

	  }

	  // clean-up after ourself
	  public void dispose() {
		  if ( image != null) {
			  image.dispose();
		  }
		  if (trayItem != null) {
			  trayItem.dispose();
		  }
	  }
}
