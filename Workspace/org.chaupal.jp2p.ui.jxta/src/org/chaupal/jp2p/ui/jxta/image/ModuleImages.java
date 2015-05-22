/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.image;

import net.jp2p.container.activator.IActivator.Status;
import net.jp2p.container.utils.StringStyler;

import org.chaupal.jp2p.ui.image.AbstractImages;
import org.chaupal.jp2p.ui.jxta.Activator;
import org.eclipse.swt.graphics.Image;

public class ModuleImages extends AbstractImages{

	public static final String S_ICON_PATH = "/resources/";

	public static final String S_MODULE_ICON  = "module-16.png";
	public static final String S_MANAGER_ICON  = "manager-16.png";
	public static final String S_CONFIGURATOR_ICON  = "configuration-16.png";
	public static final String S_COMPONENT_ICON = "component.png";
	public static final String S_WORLD_ICON = "world.png";

	public enum Images{
		MODULE,
		MANAGER,
		CONFIGURATOR,
		COMPONENT,
		WORLD;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString() );
		}
	}
	
	private static ModuleImages images = new ModuleImages();
	
	public ModuleImages() {
		super( S_ICON_PATH, Activator.BUNDLE_ID );
	}

	/**
	 * Get an instance of this map
	 * @return
	 */
	public static ModuleImages getInstance(){
		return images;
	}
	
	@Override
	public void initialise(){
		setImage( S_MODULE_ICON );
		setImage( S_MANAGER_ICON );
		setImage( S_CONFIGURATOR_ICON );
		setImage( S_COMPONENT_ICON );
		setImage( S_WORLD_ICON );
	}

	/**
	 * Get the image
	 * @param desc
	 * @return
	 */
	public Image getImage( Status desc ){
		switch( desc ){
		default:
			return getImageFromName( S_MODULE_ICON );				
		}
	}

	/**
	 * Get the image
	 * @param desc
	 * @return
	 */
	public Image getImage( Images desc ){
		switch( desc ){
		case COMPONENT:
			return getImageFromName( S_COMPONENT_ICON );
		case WORLD:
			return getImageFromName( S_WORLD_ICON );
		case MANAGER:
			return getImageFromName( S_MANAGER_ICON );
		case CONFIGURATOR:	
			return getImageFromName( S_CONFIGURATOR_ICON );
		default:
			return getImageFromName( S_MODULE_ICON );				
		}
	}

}