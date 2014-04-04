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
import net.jp2p.chaupal.dispatcher.ServiceEventDispatcher;
import net.jp2p.chaupal.utils.Utils;
import net.jp2p.container.component.IJp2pComponent;

import org.chaupal.jp2p.ui.osgi.Jp2pServiceContainerPetitioner;
import org.eclipselabs.osgi.ds.broker.service.IParlezListener;
import org.eclipselabs.osgi.ds.broker.service.ParlezEvent;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

public class Jp2pContainerNavigator extends CommonNavigator{

	public static final String PATH_ID = "org.eclipselabs.jxse.ui.context.view";
	
	private CommonViewer viewer;
	
	private Jp2pServiceContainerPetitioner petitioner;
	private ServiceEventDispatcher dispatcher;
	private Jp2pContainerNavigator navigator;
	
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
		dispatcher = ServiceEventDispatcher.getInstance();
		dispatcher.addServiceChangeListener( new IServiceChangedListener(){

			@Override
			public void notifyServiceChanged(ServiceChangedEvent event) {
				if( ServiceChange.REFRESH.equals( event.getChange()))
					navigator.refresh();
			}
			
		});
	}

	/**
	 * Declare a new root (standard this is the workspace).
	 * @see http://www.techjava.de/topics/2009/04/eclipse-common-navigator-framework/
	 */
	@Override
	protected Object getInitialInput() {
        petitioner = Jp2pServiceContainerPetitioner.getInstance();
		petitioner.addParlezListener( new IParlezListener(){

			@Override
			public void notifyChange(ParlezEvent<?> event) {
				navigator.refresh();
			}
			
		});
		petitioner.petition("containers");
		return petitioner;
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
		IJp2pComponent<?> component = (net.jp2p.container.component.IJp2pComponent<Object>)element;
		setContentDescription( Utils.getLabel(component));
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
				if(( propertyPage == null ) || ( propertyPage.getSite().getShell().isDisposed() ))
					return;
				
				if( propertyPage != null )
					propertyPage.refresh();
				viewer.refresh();
			}
		});			
	}
}