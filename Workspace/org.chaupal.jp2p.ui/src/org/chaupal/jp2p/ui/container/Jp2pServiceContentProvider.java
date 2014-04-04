/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.container;

import net.jp2p.container.component.IJp2pComponentNode;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class Jp2pServiceContentProvider implements ITreeContentProvider {

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(( parentElement == null ) || !( parentElement instanceof IJp2pComponentNode<?> ))
			return null;
		 IJp2pComponentNode<?> decorator = (IJp2pComponentNode<?>)parentElement;
		if( decorator.getChildren() == null )
			return null;
		//Collections.sort( decorator.getChildren(), new JxtaServiceComparator< IJxtaServiceNode<?>>());
		return decorator.getChildren();
	}

	@Override
	public boolean hasChildren(Object element) {
		if(!( element instanceof IJp2pComponentNode ))
			return false;
		IJp2pComponentNode<?> decorator = (IJp2pComponentNode<?>)element;
		return decorator.hasChildren();
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren( inputElement );
	}

	@Override
	public Object getParent(Object element) {
		if(!(element instanceof IJp2pComponentNode )){
			return null;
		}
		IJp2pComponentNode<?> component = (IJp2pComponentNode<?>)element;
		if( component.isRoot() )
			return null;
		return component.getPropertySource().getParent();
	}
}