/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.container;

import net.jp2p.chaupal.dispatcher.IServiceChangedListener;
import net.jp2p.chaupal.dispatcher.ServiceChangedEvent;
import net.jp2p.chaupal.utils.Utils;
import net.jp2p.container.component.ComponentChangedEvent;
import net.jp2p.container.component.IComponentChangedListener;
import net.jp2p.container.component.IJp2pComponent;

import org.chaupal.jp2p.ui.Activator;
import org.chaupal.jp2p.ui.osgi.Jp2pContainerService;
import org.chaupal.jp2p.ui.refresh.RefreshDispatcher;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

public class Jp2pContainerNavigator extends CommonNavigator{

	public static final String S_JP2P_NAVIGATOR_ID = "org.chaupal.jp2p.ui.container.view";

	public static final String S_NAVIGATOR_ID = "User Service: ";
	
	private CommonViewer viewer;
	
	private Jp2pContainerNavigator navigator;
	
	private RefreshDispatcher dispatcher = RefreshDispatcher.getInstance();
	private IServiceChangedListener refreshlistener = new IServiceChangedListener(){

		@Override
		public String getName() {
			return this.getClass().getName();
		}

		@Override
		public void notifyServiceChanged(ServiceChangedEvent event) {
			refresh();
		}
		
	};
	
	private Jp2pContainerService<Object> containerService = Activator.getJp2pContainerService();
	private IComponentChangedListener<IJp2pComponent<Object>> componentListener = new IComponentChangedListener<IJp2pComponent<Object>>() {

		@Override
		public void notifyServiceChanged(ComponentChangedEvent<IJp2pComponent<Object>> event) {
			navigator.refresh();
		}
	};
	
	//IPropertySheetPage doesn't implement refresh()
	private PropertySheetPage propertyPage;

	private ISelectionListener listener = new ISelectionListener() {
		@Override
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
			// we deal with only our own selections
			if (!( sourcepart instanceof Jp2pContainerNavigator ))
				return;
			showSelection( sourcepart, selection);
		}
	};

	public Jp2pContainerNavigator() {
		super();
		navigator = this;
		containerService.addServiceChangeListener( componentListener);
		dispatcher.addServiceChangeListener(refreshlistener);
	}

	/**
	 * Declare a new root (standard this is the workspace).
	 * @see http://www.techjava.de/topics/2009/04/eclipse-common-navigator-framework/
	 */
	@Override
	protected Object getInitialInput() {
		return containerService;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySheetPage.class) {
	        if (propertyPage == null) {
	            propertyPage = new PropertySheetPage();
	        }
	        return propertyPage;
	    }
	    return super.getAdapter(adapter);
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
		this.viewer.setSorter( new Jp2pServiceViewerSorter() );
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
		if(!( element instanceof IJp2pComponent<?>))
			return;
		IJp2pComponent<?> component = (IJp2pComponent<Object>)element;
		setContentDescription( S_NAVIGATOR_ID + Utils.getLabel(component));
	}

	
	@Override
	public void dispose() {
		dispatcher.removeServiceChangeListener(refreshlistener);
		containerService.removeServiceChangeListener( componentListener);
		super.dispose();
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
				if( Display.getDefault().isDisposed() )
					return;

				if(( propertyPage == null ) || ( propertyPage.getSite() == null ) ||
						( propertyPage.getSite().getShell() == null ) || 
						( propertyPage.getSite().getShell().isDisposed() ))
					return;
				
				if( propertyPage != null )
					propertyPage.refresh();
				viewer.refresh();
			}
		});			
	}
}