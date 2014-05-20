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

import org.chaupal.jp2p.ui.jxta.image.ModuleImages;
import org.chaupal.jp2p.ui.jxta.image.ModuleImages.Images;
import org.chaupal.jp2p.ui.provider.Jp2pLabelProvider;
import org.eclipse.swt.graphics.Image;

public class Jp2pJxseLabelProvider extends Jp2pLabelProvider{

	@Override
	public Image getImage(Object element) {
		if( element instanceof IJp2pComponent<?> )
			return super.getImage(element);
		ModuleImages images = new ModuleImages();
		return images.getImage( Images.COMPONENT );
	}

	@Override
	public String getText(Object element) {
		if(!( element instanceof IJp2pComponent<?>))
			return super.getText(element);
		IJp2pComponent<?> component = (IJp2pComponent<?> )element;
		return Utils.getLabel(component); 
	}	
}