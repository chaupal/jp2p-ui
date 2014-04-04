/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.image;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import net.jp2p.container.utils.Utils;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

public abstract class AbstractImages {

	private static final String S_ERR_INVALID_BUNDLE_NAME = "The bundle name is invalid: ";
	
	private Collection<ImageData> imageMap;
	private String path;
	
	private String bundleName;

	protected AbstractImages( String path ) {
		this( path, null);
	}

	protected AbstractImages( String path, String bundleName ) {
		Bundle bundle = Platform.getBundle( bundleName );
		if( bundle == null )
			throw new NullPointerException( S_ERR_INVALID_BUNDLE_NAME + bundleName);
		imageMap = new ArrayList<ImageData>();
		this.path = path;
		this.bundleName = bundleName;
		this.initialise();
	}
	
	protected ImageData setImage( String name ){
		ImageData data = new ImageData();
		data.location = path + name;
		data.descriptor = getImageDescriptor( data.location ); 
		data.image = data.descriptor.createImage();
		imageMap.add( data );	
		return data;
	}

	/**
	 * Get the image with the given 
	 * @param identifier
	 * @return
	 */
	public Image getImageFromName( String identifier ){
		for( ImageData data: imageMap ){
			if( data.location.endsWith( identifier )){
				return data.image;
			}
		}
		ImageData data = setImage( identifier );
		return ( data == null )? null: data.image;
	}
	
	public abstract void initialise();
	
	public void dispose(){
		for( ImageData data: imageMap )
			data.image.dispose();
		imageMap.clear();
	}

	/**
	 * Get the URL where the image is located
	 * @param location
	 * @return
	 */
	protected URL getImageURL( String location ){
		URL url = null;
		if( Utils.isNull( this.bundleName )){
			if( !location.startsWith("/"))
				location = "/" + location;
			url = this.getClass().getResource( location );
			if( url == null )
				url = AbstractImages.class.getResource( location );
		}else{
			Bundle bundle = Platform.getBundle( this.bundleName ); 
			url = bundle.getResource( location );      	 
		}
		return url;
	}
	
	/**
	 * Get the image descriptor
	 * @param location
	 * @return
	 */
    protected ImageDescriptor getImageDescriptor(String location ) {
       URL url = this.getImageURL(location);
       return ImageDescriptor.createFromURL(url);
    }
}

class ImageData{	
	String location;
	ImageDescriptor descriptor;
	Image image;
}
