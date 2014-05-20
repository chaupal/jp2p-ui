/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.peergroup;

import net.jp2p.container.utils.SimpleNode;
import net.jp2p.jxta.peergroup.PeerGroupPropertySource;
import net.jxta.peergroup.PeerGroup;

import org.chaupal.jp2p.ui.jxta.image.ModuleImages;
import org.chaupal.jp2p.ui.jxta.image.ModuleImages.Images;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class PeerGroupLabelProvider extends LabelProvider{

	@SuppressWarnings("unchecked")
	@Override
	public Image getImage(Object element) {
		ModuleImages images = new ModuleImages();
		if(!( element instanceof SimpleNode ))
			return super.getImage(element);
		SimpleNode<PeerGroup, PeerGroup> node = (SimpleNode<PeerGroup, PeerGroup>) element;
		if( node.getData().getPeerGroupName().equals( PeerGroupPropertySource.S_NET_PEER_GROUP ))
			return images.getImage( Images.WORLD );
		else
			return images.getImage( Images.COMPONENT );
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getText(Object element) {
		if(!( element instanceof SimpleNode ))
			return super.getText(element);
		SimpleNode<PeerGroup, PeerGroup> node = (SimpleNode<PeerGroup, PeerGroup>) element;
		return node.getData().getPeerGroupName();
	}
}	