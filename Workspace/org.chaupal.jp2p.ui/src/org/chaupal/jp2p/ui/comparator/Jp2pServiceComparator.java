/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.comparator;

import java.util.Comparator;
import java.util.Date;

import net.jp2p.container.IJp2pContainer;
import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.properties.IJp2pProperties.Jp2pProperties;
import net.jp2p.jxta.utils.JxseModuleComparator;

public class Jp2pServiceComparator<T extends Object> implements
		Comparator<T> {

	@Override
	public int compare(T arg0, T arg1) {
		if(( arg0 == null ) && ( arg1 == null ))
				return 0;
		if( arg0 == null )
			return -1;
		if( arg1 == null )
			return 1;
	
		int compare =  getIndex( arg0 ) - getIndex( arg1 );
		if( compare != 0 )
			return compare;
		IJp2pComponent<?> node1 = (net.jp2p.container.component.IJp2pComponent<?>)arg0;
		Date date1 = (Date) node1.getPropertySource().getProperty( Jp2pProperties.CREATE_DATE );
		IJp2pComponent<?> node2 = (net.jp2p.container.component.IJp2pComponent<?>)arg1;
		Date date2 = (Date) node2.getPropertySource().getProperty( Jp2pProperties.CREATE_DATE );
		return this.compareDate( date1, date2 );
	}

	/**
	 * Create an index for the various modules
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected int getIndex( Object obj  ){
		if( obj == null )
			return -1;
		int index = 0;
		if( obj instanceof IJp2pContainer )
			return index;
		index++;
		if(!( obj instanceof IJp2pComponent<?>)){
			index = JxseModuleComparator.getIndex(obj);
			return index;
		}
		IJp2pComponent<Object> comp = (IJp2pComponent<Object> )obj;
		return JxseModuleComparator.getIndex(comp.getModule());
	}
	
	private int compareDate( Date date1, Date date2 ){
		if(( date1 == null ) && ( date2 == null ))
			return 0;
		if( date1 == null )
			return -1;
		if( date2 == null )
			return 1;
		return date1.compareTo( date2 );

	}

}
