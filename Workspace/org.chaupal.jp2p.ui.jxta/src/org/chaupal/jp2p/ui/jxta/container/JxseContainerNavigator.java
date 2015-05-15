/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.container;

import net.jp2p.chaupal.dispatcher.IServiceChangedListener;
import net.jp2p.chaupal.dispatcher.ServiceChangedEvent;
import net.jp2p.chaupal.utils.Utils;
import net.jp2p.container.component.IJp2pComponent;

import org.chaupal.jp2p.ui.container.Jp2pContainerNavigator;
import org.chaupal.jp2p.ui.jxta.osgi.service.RefreshDispatcher;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

public class JxseContainerNavigator extends CommonNavigator{

	public static final String PATH_ID = "org.chaupal.jp2p.ui.jxta.container";
	public static final String S_NAVIGATOR_TEXT = "JXTA Module: ";
	
	private CommonViewer viewer;
	
	private RefreshDispatcher dispatcher = RefreshDispatcher.getInstance();
	private IServiceChangedListener refreshlistener = new IServiceChangedListener(){

		@Override
		public String getName() {
			return this.getClass().getName();
		}

		@Override
		public void notifyServiceChanged(ServiceChangedEvent event) {
			//refresh();
		}
		
	};
	
	//private JxseContainerNavigator navigator;
	
	private ISelectionListener listener = new ISelectionListener() {
		@Override
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
			// we deal with only our own selections
			if (!( sourcepart instanceof Jp2pContainerNavigator ))
				return;
			showSelection( sourcepart, selection);
		}
	};

	public JxseContainerNavigator() {
		super();
		dispatcher.addServiceChangeListener( refreshlistener);
	}

	/**
	 * Declare a new root (standard this is the workspace).
	 * @see http://www.techjava.de/topics/2009/04/eclipse-common-navigator-framework/
	 */
	@Override
	protected Object getInitialInput() {
		return null;
	}

	@Override
	public void createPartControl(Composite aParent) {
		getSite().setSelectionProvider(this.getCommonViewer());
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(listener);
		
		super.createPartControl(aParent);
	}

	@Override
	protected CommonViewer createCommonViewer(Composite aParent) {
		this.viewer = super.createCommonViewer(aParent);
//		this.viewer.setSorter( new Jp2pServiceViewerSorter() );
		this.viewer.setAutoExpandLevel( AbstractTreeViewer.ALL_LEVELS );
		return viewer;
	}

	/**
	 * Shows the given selection in this view.
	 */
	@SuppressWarnings("unchecked")
	void showSelection(IWorkbenchPart sourcepart, ISelection selection) {
		IStructuredSelection ss = (IStructuredSelection) selection;
		Object element = ss.getFirstElement();
		if(( element == null ) || !( element instanceof IJp2pComponent<?>))
			return;
		IJp2pComponent<?> component = (IJp2pComponent<Object>)element;
		setContentDescription( S_NAVIGATOR_TEXT + Utils.getLabel(component));
		this.viewer.setInput(component);
	}
	
	/**
	 * Refresh the property page
	 */
	protected synchronized void refresh(){
		if( Display.getDefault().isDisposed() )
			return;

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				viewer.refresh();
			}
		});			
	}

	@Override
	public void dispose() {
		dispatcher.removeServiceChangeListener(refreshlistener);
		super.dispose();
	}


}