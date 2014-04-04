/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.advertisement;

import net.jxta.document.Advertisement;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipselabs.osgi.ds.broker.util.StringStyler;

public class AdvertisementComposite extends Composite {

	static enum AdvertisementColumns{
		ID,
		NAME,
		TYPE;
	
		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString() );
		}
	}

	private Table table;
	private TableViewer tableViewer;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AdvertisementComposite(Composite parent, int style) {
		super(parent, style);
		this.createPartControl(parent, style);

	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	public void createPartControl(Composite parent, int style ) {
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		setLayout( tableColumnLayout );
		
		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		TableViewerColumn column = createColumn( AdvertisementColumns.TYPE.toString(), tableViewer);
		tableColumnLayout.setColumnData(column.getColumn(), new ColumnWeightData(20, 20, true)); 		

		column = createColumn(AdvertisementColumns.NAME.toString(), tableViewer);
		tableColumnLayout.setColumnData(column.getColumn(), new ColumnWeightData(20, 20, true)); 		

		column = createColumn(AdvertisementColumns.ID.name(), tableViewer);
		tableColumnLayout.setColumnData(column.getColumn(), new ColumnWeightData(60, 200, true)); 		

		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
	}

	
	TableViewer getTableViewer() {
		return tableViewer;
	}

	public void setInput( Advertisement[] advertisements ){
		this.tableViewer.setInput(advertisements);
	}
	
	private TableViewerColumn createColumn( final String name, TableViewer viewer ){
		TableViewerColumn col = createTableViewerColumn( viewer, name, 100, 0 );
		col.setLabelProvider(new ColumnLabelProvider() {


			@Override
			public String getText(Object element) {
				if(!( element instanceof Advertisement ))
					return super.getText(element);
				Advertisement adv = ( Advertisement )element;
				AdvertisementComposite.AdvertisementColumns column = AdvertisementComposite.AdvertisementColumns.valueOf( name.toUpperCase() );
				switch( column ){
				case ID:
					if( adv.getID() == null )
						return super.getText(element);
					return adv.getID().toString();
				case NAME:
					return adv.getAdvType();
				case TYPE:
					return adv.getAdvType();
				}
				return super.getText(element);

			}
		});		
		return col;
	}

	private TableViewerColumn createTableViewerColumn( TableViewer viewer, String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,  SWT.NONE);
		TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}
}
