/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.provider;

import java.util.ArrayList;
import java.util.Collection;

import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.component.IJp2pComponentNode;

import org.chaupal.jp2p.ui.provider.Jp2pServiceContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class Jp2pJxseModuleProvider extends Jp2pServiceContentProvider implements ITreeContentProvider {

	private IJp2pComponent<?> root;
	
	@Override
	public Object[] getChildren(Object parentElement) {
		if( parentElement == null )
			return null;
		IJp2pComponent<?> decorator = null;
		if( parentElement instanceof IJp2pComponent<?> )
			decorator = (IJp2pComponent<?>)parentElement;
		else
			decorator = getComponent( root, parentElement );
		Collection<Object> results = new ArrayList<Object>();
		for( Object child: super.getChildren( decorator)){
			Object result;
			if( child instanceof IJp2pComponent<?>){
				IJp2pComponent<?> comp = (IJp2pComponent<?>) child;
				result = comp.getModule();
			}else{
				result = child;
			}
			if( result != null )
				results.add( result);
		}
		return results.toArray( new Object[ results.size() ]);
	}

	@Override
	public boolean hasChildren(Object element) {
		if( element == null )
			return false;
		IJp2pComponent<?> decorator = null;
		if( element instanceof IJp2pComponent<?> )
			decorator = (IJp2pComponent<?>)element;
		else
			decorator = getComponent( root, element );
		return super.hasChildren( decorator );
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if( inputElement == null ) 
			return null;
		Collection<Object> results = new ArrayList<Object>();
		if( inputElement instanceof IJp2pComponent<?> ){
			root = (IJp2pComponent<?>)inputElement;
			if( root.getModule() != null )
				results.add( root.getModule());
		}
		return results.toArray( new Object[ results.size()]);
	}

	@Override
	public Object getParent(Object element) {
		if( element == null )
			return null;
		IJp2pComponent<?> decorator = null;
		if( element instanceof IJp2pComponent<?> )
			decorator = (IJp2pComponent<?>)element;
		else
			decorator = getComponent( root, element );
		return super.getParent( decorator );
	}
		
	/**
	 * Parse through a tree recursively to find the component of an element
	 * @param root
	 * @param element
	 * @return
	 */
	public static IJp2pComponent<?> getComponent( IJp2pComponent<?> root, Object element ){
		if(( element == null ) || ( root == null ))
			return null;
		if( element.equals( root.getModule()))
			return root;
		if( !( root instanceof IJp2pComponentNode<?>))
			return null;
		IJp2pComponentNode<?> node = (IJp2pComponentNode<?>) root;
		IJp2pComponent<?> found = null;
		for( IJp2pComponent<?> child: node.getChildren()){
			found = getComponent( child, element );
			if( found != null )
				return found;
		}
		return null;
	}

	/**
	 * Parse through a tree recursively to find the component of an element
	 * @param root
	 * @param element
	 * @return
	 */
	public static IJp2pComponent<?> getParentComponent( IJp2pComponent<?> root, Object element ){
		if( element == null )
			return null;
		if( element.equals( root.getModule()))
			return root;
		if( !( root instanceof IJp2pComponentNode<?>))
			return null;
		IJp2pComponentNode<?> node = (IJp2pComponentNode<?>) root;
		IJp2pComponent<?> found = null;
		for( IJp2pComponent<?> child: node.getChildren()){
			found = getComponent( child, element );
			if( found != null )
				return node;
		}
		return null;
	}

}