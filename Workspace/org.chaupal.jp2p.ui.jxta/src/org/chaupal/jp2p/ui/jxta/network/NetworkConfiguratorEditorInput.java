/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
/**
 * 
 */
package org.chaupal.jp2p.ui.jxta.network;

import net.jp2p.container.Jp2pContainerPropertySource;
import net.jp2p.container.properties.AbstractJp2pPropertySource;
import net.jp2p.jxta.network.configurator.NetworkConfigurationPropertySource;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * @author roel
 *
 */
public class NetworkConfiguratorEditorInput implements IEditorInput {
	private NetworkConfigurationPropertySource source;

	public NetworkConfiguratorEditorInput(NetworkConfigurationPropertySource source) {
		this.source = source;
	}

	@Override
	public boolean exists() {
		return (this.source != null );
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return AbstractJp2pPropertySource.getIdentifier( source );
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	public NetworkConfigurationPropertySource getSource() {
		return source;
	}

	@Override
	public String getToolTipText() {
		return getName();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof NetworkConfiguratorEditorInput) {
			NetworkConfiguratorEditorInput b = (NetworkConfiguratorEditorInput) arg0;
			return b.equals(b);
		}
		return false;
	}
}
