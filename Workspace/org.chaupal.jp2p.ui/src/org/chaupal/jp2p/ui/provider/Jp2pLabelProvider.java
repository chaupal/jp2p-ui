/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.provider;

import net.jp2p.chaupal.utils.Utils;
import net.jp2p.container.IJp2pContainer;
import net.jp2p.container.activator.IActivator.Status;
import net.jp2p.container.component.AbstractJp2pService;
import net.jp2p.container.component.IJp2pComponent;

import org.chaupal.jp2p.ui.image.StatusImages;
import org.chaupal.jp2p.ui.image.StatusImages.Images;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class Jp2pLabelProvider extends LabelProvider{

	@SuppressWarnings("unchecked")
	@Override
	public Image getImage(Object element) {
		if(!( element instanceof IJp2pComponent<?> ))
			return super.getImage(element);
		IJp2pComponent<?> component = (IJp2pComponent<?> )element;
		StatusImages images = StatusImages.getInstance();
		if( component instanceof AbstractJp2pService ){
			AbstractJp2pService<?> service = (AbstractJp2pService<Object> )component;
			if( service.getModule() == null )
				return images.getImage( Status.DISABLED );
			return images.getImage( service.getStatus() );
		}
		if( component instanceof IJp2pContainer )
			return images.getImage( Images.CONTAINER );	
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