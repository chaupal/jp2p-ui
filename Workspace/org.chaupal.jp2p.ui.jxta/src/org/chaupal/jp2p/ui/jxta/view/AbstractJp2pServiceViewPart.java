/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.view;

import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.log.Jp2pLevel;
import net.jxta.peergroup.PeerGroup;

import org.chaupal.jp2p.ui.jxta.monitor.ConnectivityViewPart;
import org.chaupal.jp2p.ui.util.ColorUtils;
import org.chaupal.jp2p.ui.util.ColorUtils.SupportedColors;
import org.chaupal.jp2p.ui.log.Jp2pLog;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;

import org.eclipse.swt.graphics.Color;
import org.eclipse.jface.viewers.TableViewer;

public abstract class AbstractJp2pServiceViewPart<T extends Object> extends ViewPart{

    private Future<?> theMonitorFuture = null;
	public static final ScheduledExecutorService theExecutor = Executors.newScheduledThreadPool(5);

	private T service;
	private PeerGroup peergroup;
	
	private boolean isrunning;

	private Runnable runner = new Runnable(){

		@Override
		public void run() {
			isrunning = true;
			if( service != null )
	            refresh();
		}
	};

	/**
	 * Returns true if the monitor task is running
	 * @return
	 */
	protected boolean isRunning() {
		return isrunning;
	}

	private ISelectionListener listener = new ISelectionListener() {
		@Override
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
			// we ignore our own selections
			if ( sourcepart instanceof ConnectivityViewPart )
				return;
			showSelection( sourcepart, selection);
		}
	};

	protected AbstractJp2pServiceViewPart( String partName) {
		setPartName( partName );
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(new ISelectionListener() {
			@Override
			public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
				// we ignore our own selections
				if ( sourcepart instanceof AbstractJp2pServiceViewPart )
					return;
				showSelection( sourcepart, selection);
			}
		});
		this.onCreatePartControl(parent);
		createActions();
		initializeToolBar();
		initializeMenu();
        theMonitorFuture = theExecutor.scheduleAtFixedRate( runner, 5, 1, TimeUnit.SECONDS);
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(listener);
	}

	protected abstract void onCreatePartControl( Composite parent );
	
	protected TableViewerColumn createColumn( String name, TableViewer viewer ){
		TableViewerColumn col = createTableViewerColumn( viewer, name, 100, 0 );
		col.setLabelProvider(new ColumnLabelProvider() {
		  @Override
		  public String getText(Object element) {
		    return super.getText(element);
		  }
		});	
		return col;
	}

	protected TableViewerColumn createTableViewerColumn( TableViewer viewer, String title, int bound, final int colNumber) {
	    final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,  SWT.NONE);
	    TableColumn column = viewerColumn.getColumn();
	    column.setText(title);
	    column.setWidth(bound);
	    column.setResizable(true);
	    column.setMoveable(true);
	    return viewerColumn;
	}
	
	/**
	 * Get the peergroup
	 * @return
	 */
	protected PeerGroup getPeerGroup(){
		return this.peergroup;
	}
	
	/**
	 * Get the service
	 * @return
	 */
	protected T getService() {
		return service;
	}

	/**
	 * Shows the given selection in this view.
	 */
	void showSelection(IWorkbenchPart sourcepart, ISelection selection) {
		if(!( selection instanceof IStructuredSelection))
			return;
		
		IStructuredSelection ss = (IStructuredSelection) selection;
		Object element = ss.getFirstElement();
		
		//We check for service decorators coming from the service navigator
		if(!( element instanceof IJp2pComponent<?>))
			return;
		IJp2pComponent<?> component = (IJp2pComponent<?> )element;
		if(!( component.getModule() instanceof PeerGroup ))
			return;
		this.peergroup = ((PeerGroup) component.getModule());
		this.service = this.onSetService( peergroup );

        // Starting the monitor
        logJxta( peergroup, "Starting to monitor the " + service.getClass().getSimpleName() + " of peergroup " + peergroup.getPeerGroupName() );
	}

	/**
	 * Set the required service
	 * @param peeergroup
	 * @return
	 */
	protected abstract T onSetService( PeerGroup peergroup );
	
	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	@SuppressWarnings("unused")
	private void initializeToolBar() {
		IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	@SuppressWarnings("unused")
	private void initializeMenu() {
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	/**
	 * Refresh the view
	 */
	protected void refresh(){
		if( Display.getDefault().isDisposed() )
			return;
		Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
            	if(( peergroup == null ) || ( service == null ))
            		return;
            	onRefresh();
            }
		});		
	}
	protected abstract void onRefresh();
	
    protected synchronized void stopMonitorTask() {

        if ( theMonitorFuture != null ) {
            theMonitorFuture.cancel(false);
        }
    	this.isrunning = false;;
  }

    @Override
    protected void finalize() {
        stopMonitorTask();
        this.onFinalize();
    }
	protected abstract void onFinalize();
    
    protected void logJxta( final PeerGroup peerGroup, final String message ){
    	if( Display.getDefault().isDisposed() )
    		return;
    	Display.getDefault().asyncExec(new Runnable() {
    		@Override
    		public void run() {
    			LogRecord record = new LogRecord( Jp2pLevel.getJxtaLevel(), message );
    			record.setSourceClassName( this.getClass().getName() );
    			Object[] parameters = new Object[2];
    			parameters[0] = peerGroup.getPeerName();
    			Color color = ColorUtils.getSWTColor( Display.getDefault(), SupportedColors.COLOR_CYAN );//coding.get( peerGroup ));
    			parameters[1] = color;
    			record.setParameters(parameters);
    			Jp2pLog.logJp2p( record );
    		}
    	});
    }
}
