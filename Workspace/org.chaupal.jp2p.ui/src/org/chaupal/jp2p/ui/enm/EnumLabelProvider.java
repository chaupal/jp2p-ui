/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.enm;


import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class EnumLabelProvider<T extends Enum<?>> extends LabelProvider {

	public EnumLabelProvider( Enum<?>[] base) {
		super();
	}

	@Override
	public Image getImage(Object element)
	{
		return super.getImage(element);
	}

	@Override
	public String getText(Object element)
	{
		if(!( element instanceof Enum ))
			return super.getText(element);
		Enum<?> enm = (Enum<?>) element;
		return enm.name();
	}
}