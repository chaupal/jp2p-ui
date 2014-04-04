/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.view;

import net.jp2p.container.component.IJp2pComponent;

import org.chaupal.jp2p.ui.jxta.monitor.ConnectivityViewPart;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;

public class ModuleTableViewer extends ViewPart {

	public static final String ID = "org.chaupal.jp2p.ui.jxta.view.ModuleTableViewer"; //$NON-NLS-1$
	
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Table table;

	public ModuleTableViewer() {
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
				if ( sourcepart instanceof ConnectivityViewPart )
					return;
				showSelection( sourcepart, selection);
			}
		});

		Composite container = toolkit.createComposite(parent, SWT.NONE);
		toolkit.paintBordersFor(container);
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		container.setLayout(tableColumnLayout);
		
		TableViewer tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		TableViewerColumn column = createColumn("Relays", tableViewer);
		tableColumnLayout.setColumnData(column.getColumn(), new ColumnWeightData(100, 200, true)); 		
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		toolkit.paintBordersFor(table);

		createActions();
		initializeToolBar();
		initializeMenu();
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
		//if(!( component.getModule() instanceof PeerGroup ))
		//	this.setPeerGroup(null );
		//else
		 // this.setPeerGroup( (PeerGroup) component.getModule());
	}

	@Override
	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}

	private TableViewerColumn createColumn( String name, TableViewer viewer ){
		TableViewerColumn col = createTableViewerColumn( viewer, name, 100, 0 );
		col.setLabelProvider(new ColumnLabelProvider() {
		  @Override
		  public String getText(Object element) {
		    return super.getText(element);
		  }
		});	
		return col;
	}

	private TableViewerColumn createTableViewerColumn( TableViewer viewer, String title, int bound, final int colNumber) {
	    final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,  SWT.NONE);
	    TableColumn column = viewerColumn.getColumn();
	    column.setText(title);
	    column.setWidth(bound);
	    column.setResizable(true);
	    column.setMoveable(true);
	    return viewerColumn;
	}

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
	private void initializeMenu() {
		@SuppressWarnings("unused")
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
