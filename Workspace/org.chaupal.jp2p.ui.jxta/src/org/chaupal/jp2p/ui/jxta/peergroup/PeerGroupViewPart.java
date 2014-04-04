/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.peergroup;

import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import net.jp2p.container.utils.INode;
import net.jp2p.container.utils.SimpleNode;
import net.jp2p.container.utils.StringStyler;
import net.jp2p.jxta.peergroup.PeerGroupNode;
import net.jp2p.jxta.peergroup.PeerGroupPropertySource.PeerGroupProperties;
import net.jxta.document.Advertisement;
import net.jxta.peergroup.PeerGroup;

//import org.chaupal.jp2p.ui.jxta.advertisement.AdvertisementComposite;
import org.chaupal.jp2p.ui.jxta.view.AbstractJp2pServiceViewPart;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.custom.SashForm;

import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.swt.layout.FillLayout;

public class PeerGroupViewPart extends AbstractJp2pServiceViewPart<INode<PeerGroup,PeerGroup>>{

	public static final String ID = "org.chaupal.jp2p.ui.peergroup.view"; //$NON-NLS-1$
	public static final String S_PEERGROUP_VIEWER = "Peergroup Viewer";

	static enum PeerGroupColumns{
		NAME,
		VALUE;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.toString() );
		}
	}
	
 	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	
	private TableViewer tableViewer;
	private StyledText styledText;
	
 	private Table table;
 	private SashForm sashForm_1;
 	private Composite composite;
 	private Tree tree;
 	private TreeViewer treeViewer;
 	private PeerGroupViewPart viewpart; 

	private ISelectionListener listener = new ISelectionListener() {
		@SuppressWarnings("unchecked")
		@Override
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
			if (!( sourcepart.equals( viewpart )))
				return;
			if( selection instanceof TreeSelection){
				TreeSelection ss = (TreeSelection) selection;
				Object element = ss.getFirstElement();

				//We check for service decorators coming from the service navigator
				if(!( element instanceof SimpleNode<?,?>))
					return;
				SimpleNode<PeerGroup,PeerGroup> node = (SimpleNode<PeerGroup, PeerGroup> )element;
				setInput( node );
				return;
			}
			if( selection instanceof StructuredSelection){
				StructuredSelection ss = (StructuredSelection) selection;
				Object element = ss.getFirstElement();
				if(( element == null ) || (!( element instanceof Advertisement )))
					return;
				styledText.setText( element.toString() );
			}
		}
	};
	private Composite composite_1;
	//private AdvertisementComposite adv_comp;


	public PeerGroupViewPart() {
		super( S_PEERGROUP_VIEWER );
		this.viewpart = this;
	}


	@Override
	protected void onCreatePartControl(Composite parent) {		
		Composite container = new Composite(parent, SWT.BORDER);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		sashForm_1 = new SashForm(container, SWT.NONE);
		toolkit.adapt(sashForm_1);
		toolkit.paintBordersFor(sashForm_1);

		composite = new Composite(sashForm_1, SWT.BORDER);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		composite.setLayout(new TreeColumnLayout());

		treeViewer = new TreeViewer(composite, SWT.NONE );
		treeViewer.setAutoExpandLevel( AbstractTreeViewer.ALL_LEVELS);
		treeViewer.setContentProvider( new PeerGroupContentProvider());
		treeViewer.setLabelProvider( new PeerGroupLabelProvider());
		tree = treeViewer.getTree();
		tree.setHeaderVisible(true);
		toolkit.paintBordersFor(tree);
		
		composite_1 = new Composite(sashForm_1, SWT.NONE);
		toolkit.adapt(composite_1);
		toolkit.paintBordersFor(composite_1);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		SashForm sashForm = new SashForm(composite_1, SWT.VERTICAL);
		Composite composite_2 = new Composite(sashForm, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite tableComposite = new Composite(composite_2, SWT.NONE);
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnLayout);

		tableViewer = new TableViewer(tableComposite, SWT.FULL_SELECTION);
		TableViewerColumn column = createColumn( PeerGroupColumns.NAME.toString(), tableViewer);
		tableColumnLayout.setColumnData(column.getColumn(), new ColumnWeightData(30, 50, true)); 		
		column = createColumn(PeerGroupColumns.VALUE.toString(), tableViewer);
		tableColumnLayout.setColumnData(column.getColumn(), new ColumnWeightData(70, 200, true)); 		
		
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		
		//adv_comp = new AdvertisementComposite(sashForm, SWT.NONE);

		styledText = new StyledText(sashForm, SWT.BORDER);
		toolkit.adapt(styledText);
		toolkit.paintBordersFor(styledText);

		sashForm.setWeights(new int[] {3, 2});
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(listener);
		getSite().setSelectionProvider( this.treeViewer );
		sashForm_1.setWeights(new int[] {2, 3});
	}

	@Override
	protected TableViewerColumn createColumn( final String name, TableViewer viewer ){
		TableViewerColumn col = createTableViewerColumn( viewer, name, 100, 0 );
		col.setLabelProvider(new ColumnLabelProvider() {


			@SuppressWarnings("unchecked")
			@Override
			public String getText(Object element) {
				if(!( element instanceof Map.Entry ))
					return super.getText(element);
				Map.Entry<String, String> entry = ( Map.Entry<String, String> )element;
				PeerGroupColumns column = PeerGroupColumns.valueOf( StringStyler.styleToEnum( name ));
				switch( column ){
				case NAME:
					return entry.getKey();
				default:
					return entry.getValue();					
				}
			}
		});		
		return col;
	}

	public void setInput( SimpleNode<PeerGroup, PeerGroup> node ){
		PeerGroup peergroup = node.getData();
		Properties props = new Properties();
		for( PeerGroupProperties pgp: PeerGroupProperties.values() ){
			switch( pgp ){
			case NAME:
				props.setProperty( pgp.toString(), peergroup.getPeerGroupName() );
				break;
			case GROUP_ID:
				props.setProperty( pgp.toString(), peergroup.getPeerGroupID().toString());
				break;
			case PEER_ID:
				props.setProperty( pgp.toString(), peergroup.getPeerID().toString() );
				break;
			case PEERGROUP_ID:
				props.setProperty( pgp.toString(), peergroup.getPeerGroupID().toString());
				break;
			case PEER_NAME:
				props.setProperty( pgp.toString(), peergroup.getPeerName());
				break;
			case STORE_HOME:
				props.setProperty( pgp.toString(), peergroup.getStoreHome().getPath() );
				break;
			default:
				break;					
			}
		}
		tableViewer.setInput( props.entrySet() );
		Stack<Advertisement> stack = new Stack<Advertisement>();
		stack.push( peergroup.getImplAdvertisement() );
		stack.push( peergroup.getPeerAdvertisement() );
		stack.push( peergroup.getPeerGroupAdvertisement() );
		//adv_comp.setInput(stack.toArray( new Advertisement[ stack.size()]));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected INode<PeerGroup,PeerGroup> onSetService(PeerGroup peergroup) {
		PeerGroupNode leaf = new PeerGroupNode( peergroup );
		PeerGroup current = peergroup;
		SimpleNode node = leaf;
		while( current.getParentGroup() != null ){
			current = current.getParentGroup();
			node = new PeerGroupNode( current );
			node.addChild( leaf );
			leaf = (PeerGroupNode) node;
		}
		this.treeViewer.setInput(node);
		return node;
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	protected void onRefresh() {
		PeerGroup peergroup = super.getPeerGroup();
		String msg = peergroup.getPeerName();
		if( peergroup.getPeerID() != null )
			msg += "[" + peergroup.getPeerID().toString() + "]";
		setContentDescription( msg );
	}

	@Override
	protected void onFinalize() {
        stopMonitorTask();
	}
}