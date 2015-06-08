/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.provider;

import net.jp2p.chaupal.utils.Utils;
import net.jp2p.container.component.IJp2pComponent;
import net.jxta.peergroup.core.Module;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;

import org.chaupal.jp2p.ui.jxta.image.ModuleImages;
import org.chaupal.jp2p.ui.jxta.image.ModuleImages.Images;
import org.chaupal.jp2p.ui.provider.Jp2pLabelProvider;
import org.eclipse.swt.graphics.Image;

public class Jp2pJxseLabelProvider extends Jp2pLabelProvider{

	@Override
	public Image getImage(Object element) {
		if( element == null )
			return null;
		if( element instanceof IJp2pComponent<?> )
			return super.getImage(element);
		ModuleImages images = new ModuleImages();
		Image image = null;
		if(element instanceof Module )
			image = images.getImage( Images.MODULE );
		if(element instanceof NetworkManager )
			image = images.getImage( Images.MANAGER );
		if(element instanceof NetworkConfigurator )
			image = images.getImage( Images.CONFIGURATOR );
		return image;
	}

	@Override
	public String getText(Object element) {
		if( element == null )
			return "no module";
		if(!( element instanceof IJp2pComponent<?>))
			return element.getClass().getSimpleName();
		IJp2pComponent<?> component = (IJp2pComponent<?> )element;
		if(element instanceof Module )
			return Utils.getLabel(component); 
		if(element instanceof NetworkManager )
			return Utils.getLabel(component); 
		if(element instanceof NetworkConfigurator )
			return Utils.getLabel(component); 
		return null;
	}	
}