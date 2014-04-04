/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.view.advertisements;

import net.jp2p.container.component.IJp2pComponent;
import net.jxta.document.Advertisement;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class JxseAdvertisementLabelProvider extends LabelProvider implements ILabelProvider {

	@Override
	public Image getImage(Object element) {
		//Images images = Images.getInstance();
		if( element instanceof IJp2pComponent<?>){
			//IJxtaServiceComponent<?> component = ( IJxtaServiceComponent<?> )element;
			return super.getImage(element);
		}
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if( element instanceof AbstractTreeDecorator ){
			AbstractTreeDecorator<?,?,?> decorator = (AbstractTreeDecorator<?,?,?>)element;
			return decorator.getLabel();
		}
		if( element instanceof Advertisement ){
			Advertisement ad = ( Advertisement )element;
			return ad.getClass().getSimpleName() + " (" + ad.getAdvType() + "): " + ad.getID();
		}
		return super.getText(element);
	}	
}