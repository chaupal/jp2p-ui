/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.provider;

import java.util.ArrayList;
import java.util.Collection;

import net.jp2p.container.utils.Utils;
import net.jxta.refplatform.platform.NetworkConfigurator;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class NetworkConfiguratorContentProvider implements ITreeContentProvider {

	private Object parent = null;
	
	public NetworkConfiguratorContentProvider() {
		super();
	}

	public NetworkConfiguratorContentProvider( Object parent ) {
		super();
		this.parent = parent;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(( parentElement == null ) || !( parentElement instanceof NetworkConfigurator ))
			return null;
		NetworkConfigurator configurator = ( NetworkConfigurator )parentElement;
		Collection<Object> children = new ArrayList<Object>();
		Utils.addArray( children, configurator.getCertificateChain() );
		Utils.addArray( children, configurator.getRdvSeedingURIs());
		Utils.addArray( children, configurator.getRdvSeedURIs());
		Utils.addArray( children, configurator.getRelaySeedingURIs());
		Utils.addArray( children, configurator.getRelaySeedURIs());
		return children.toArray( new Object[ children.size()]);
	}
	
	@Override
	public boolean hasChildren(Object element) {
		return (( element != null ) && ( element instanceof NetworkConfigurator ));
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
		if(( element == null ) || !( element instanceof NetworkConfigurator ))
			return null;
		return parent;
	}
}