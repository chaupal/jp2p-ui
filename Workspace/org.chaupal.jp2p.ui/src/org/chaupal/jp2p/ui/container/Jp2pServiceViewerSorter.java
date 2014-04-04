/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.container;

import java.text.Collator;

import org.chaupal.jp2p.ui.comparator.Jp2pServiceComparator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class Jp2pServiceViewerSorter extends ViewerSorter {

	public Jp2pServiceViewerSorter() {
	}

	public Jp2pServiceViewerSorter(Collator collator) {
		super(collator);
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		Jp2pServiceComparator<Object> comparator = new Jp2pServiceComparator<Object>();
		return comparator.compare( e1, e2);
	}

	@Override
	public boolean isSorterProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return super.isSorterProperty(element, property);
	}
	
	

}
