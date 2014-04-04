/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.provider;


import org.chaupal.jp2p.ui.image.LabelProviderImages;
import org.chaupal.jp2p.ui.image.LabelProviderImages.Images;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class CheckBoxLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element)
	{
		LabelProviderImages images = LabelProviderImages.getInstance();
		return Boolean.TRUE.equals(element) ? images.getImage( Images.CHECKED) : images.getImage( Images.UNCHECKED);
	}

	@Override
	public String getText(Object element)
	{
		return null;
	}

}
