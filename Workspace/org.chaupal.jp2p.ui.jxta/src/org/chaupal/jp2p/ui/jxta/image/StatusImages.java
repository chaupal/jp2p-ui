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

import org.chaupal.jp2p.ui.jxta.Activator;
import org.eclipse.swt.graphics.Image;

public class StatusImages extends AbstractImages{

	public static final String S_ICON_PATH = "/resources/";

	public static final String S_DISABLED_ICON  = "component-disabled.png";
	public static final String S_IDLE_ICON  = "idle.png";
	public static final String S_AVAILABLE_ICON  = "available.png";
	public static final String S_INITIALISING_ICON  = "initialising.png";
	public static final String S_INITIALISED_ICON  = "initialised.png";
	public static final String S_ACTIVATING_ICON = "activating.png";
	public static final String S_ACTIVE_ICON = "active.png";
	public static final String S_PAUSED_ICON = "player_pause.png";
	public static final String S_STOPPING_ICON = "stop.png";
	public static final String S_CHECK_ICON = "check.png";
	public static final String S_NOT_FOUND_ICON = "not_found.png";
	public static final String S_FINALISING_ICON = "shuttingdown.png";
	public static final String S_FINALISED_ICON = "shutdown.png";

	public static final String S_COMPONENT_ICON = "component.png";
	public static final String S_WORLD_ICON = "world.png";

	public enum Images{
		COMPONENT,
		WORLD;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString() );
		}
	}
	
	private static StatusImages images = new StatusImages();
	
	public StatusImages() {
		super( S_ICON_PATH, Activator.BUNDLE_ID );
	}

	/**
	 * Get an instance of this map
	 * @return
	 */
	public static StatusImages getInstance(){
		return images;
	}
	
	@Override
	public void initialise(){
		setImage( S_DISABLED_ICON );
		setImage( S_IDLE_ICON );
		setImage( S_AVAILABLE_ICON );
		setImage( S_INITIALISING_ICON );
		setImage( S_INITIALISED_ICON );
		setImage( S_ACTIVATING_ICON );
		setImage( S_ACTIVE_ICON );
		setImage( S_PAUSED_ICON );
		setImage( S_STOPPING_ICON );
		setImage( S_CHECK_ICON );
		setImage( S_NOT_FOUND_ICON );
		setImage( S_FINALISING_ICON );
		setImage( S_FINALISED_ICON );
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
		case DISABLED:
			return getImageFromName( S_DISABLED_ICON );			
		case IDLE:
			return getImageFromName( S_IDLE_ICON );
		case INITIALISING:
			return getImageFromName( S_INITIALISING_ICON );
		case INITIALISED:
			return getImageFromName( S_INITIALISED_ICON );
		case ACTIVATING:
			return getImageFromName( S_ACTIVATING_ICON );
		case ACTIVE:
			return getImageFromName( S_ACTIVE_ICON );
		case PAUSED:
			return getImageFromName( S_PAUSED_ICON );
		case AVAILABLE:
			return getImageFromName( S_AVAILABLE_ICON );
		case SHUTTING_DOWN:
			return getImageFromName( S_STOPPING_ICON );
		case COMPLETED:
			return getImageFromName( S_CHECK_ICON );
		case FAILED:
			return getImageFromName( S_NOT_FOUND_ICON );
		case FINALISING:
			return getImageFromName( S_FINALISING_ICON );
		case FINALISED:
			return getImageFromName( S_FINALISED_ICON );
		default:
			return getImageFromName( S_IDLE_ICON );				
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
		default:
			return getImageFromName( S_COMPONENT_ICON );				
		}
	}

}