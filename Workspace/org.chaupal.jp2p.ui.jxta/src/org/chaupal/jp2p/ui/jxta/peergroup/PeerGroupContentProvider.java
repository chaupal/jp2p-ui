/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.peergroup;

import net.jp2p.container.utils.IDescendant;
import net.jp2p.container.utils.ILeaf;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class PeerGroupContentProvider implements ITreeContentProvider {

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(( parentElement == null ) || !( parentElement instanceof IDescendant<?,?> ))
			return null;
		IDescendant<?,?> descendant = (IDescendant<?,?>)parentElement;
		return descendant.getChildren();
	}

	@Override
	public boolean hasChildren(Object element) {
		return ( element instanceof IDescendant );
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
		if(!(element instanceof ILeaf<?> )){
			return null;
		}
		ILeaf<?> leaf = (ILeaf<?>) element;
		return leaf.getParent();
	}
}