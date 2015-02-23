/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.component.IJp2pComponentNode;
import net.jxta.platform.NetworkConfigurator;

import org.chaupal.jp2p.ui.comparator.Jp2pServiceComparator;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class Jp2pServiceContentProvider implements ITreeContentProvider {

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(( parentElement == null ) || !( parentElement instanceof IJp2pComponent<?> ))
			return null;
		IJp2pComponent<?> decorator = (IJp2pComponent<?>)parentElement;
		return getDecoratedChildren( decorator );
	}

	@Override
	public boolean hasChildren(Object element) {
		if(( element == null ) || !( element instanceof IJp2pComponent<?> ))
			return false;
		IJp2pComponent<?> decorator = (IJp2pComponent<?>)element;
		Object[] children = getChildren( decorator );
		return ( children == null )? false: ( children.length > 0 );
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if(( inputElement == null ) || !( inputElement instanceof IJp2pComponent<?> ))
			return null;
		IJp2pComponent<?> decorator = (IJp2pComponent<?>)inputElement;
		if( decorator.getModule() instanceof NetworkConfigurator ){
			ITreeContentProvider provider = new NetworkConfiguratorContentProvider();
			return provider.getElements(inputElement);
		}
		return getChildren( inputElement );
	}

	@Override
	public Object getParent(Object element) {
		if(!( element instanceof IJp2pComponentNode ))
			return null;
		IJp2pComponentNode<?> decorator = (IJp2pComponentNode<?>)element;
		if( decorator.getModule() instanceof NetworkConfigurator ){
			ITreeContentProvider provider = new NetworkConfiguratorContentProvider( decorator.getPropertySource().getParent() );
			return provider.getParent(element);
		}
		if( decorator.getPropertySource() == null )
			return null;
		return decorator.getPropertySource().getParent();
	}
	
	/**
	 * Get the correct provider
	 * @param component
	 * @return
	 */
	public static NetworkConfiguratorContentProvider getNetworkConfiguratorContentProvider( IJp2pComponent<?> component ){
		if(!( component.getModule() instanceof NetworkConfigurator ))
			return null;
		Object parent = null;
		if( component instanceof IJp2pComponentNode ){
			IJp2pComponentNode<?> node = (IJp2pComponentNode<?> )component;
			parent = node.getPropertySource().getParent();	
		}
		return new NetworkConfiguratorContentProvider( parent );
	}

	/**
	 * Get the correct provider
	 * @param component
	 * @return
	 */
	public static Object[] getChildren( IJp2pComponent<?> component ){
		NetworkConfiguratorContentProvider provider = getNetworkConfiguratorContentProvider(component);
		if( provider != null ){
			return provider.getChildren( component );
		}
		if(!( component instanceof IJp2pComponentNode ))
			return null;
		IJp2pComponentNode<?> node = (IJp2pComponentNode<?> )component;
		return node.getChildren();
	}

	/**
	 * This helper method decorates a compoent;
	 * 1: If the component is node, then nothing changes.
	 * 2: If the component is not a node, then the module determines
	 *    whether additional children are added
	 * @param element
	 * @return
	 */

	public static Object[] getDecoratedChildren( IJp2pComponent<?> component ) {
		List<Object> results = new ArrayList<Object>();
		Object[] children = getChildren( component );
		if(( children == null ) || ( children.length == 0 ))
			return children;
		for( Object child: children )
			results.add( child );
		Collections.sort( results, new Jp2pServiceComparator<Object>());
		return results.toArray();
	}
}