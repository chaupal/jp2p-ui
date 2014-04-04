/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.view.advertisements;

public abstract class AbstractTreeDecorator<T,U,V extends Object>{

	private T object;
	
	private U parent;
	
	public AbstractTreeDecorator( T object ) {
		this( object, null );
	}

	public AbstractTreeDecorator( T object, U parent ) {
		this.object = object;
		this.parent = parent;
	}

	public U getParent(){
		if( this.parent == null )
			return null;
		return parent;
	}
	
	public T getObject(){
		return object;
	}
	
	public String getLabel(){
		return this.object.toString();
	}
	
	public boolean isRoot(){
		return (this.parent == null );
	}
	
	public boolean hasChildren(){
		return (( getChildren() != null ) && ( getChildren().length > 0 ));
	}
	
	public abstract V[] getChildren();
}
