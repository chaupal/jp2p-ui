/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.endpoint;

import net.jxta.endpoint.EndpointAddress;
import net.jxta.endpoint.EndpointListener;
import net.jxta.endpoint.EndpointService;
import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.refplatform.platform.NetworkManager.ConfigMode;

import org.chaupal.jp2p.ui.jxta.view.AbstractJp2pServiceViewPart;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.custom.StyledText;

public class EndPointViewPart extends AbstractJp2pServiceViewPart<EndpointService>{

	public static final String ID = "org.chaupal.jp2p.ui.jxta.endpoint.EndPointViewPart"; //$NON-NLS-1$
	public static final String S_ENDPOINT_VIEWER = "End Point Viewer";
	public static final String S_ENDPOINT = "EndPoint";

	private Button aliveRadioButton;
	
    private EndPointEventMonitor monitor;
    
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	private Button isConnectedToRelayCheckBox;
	private Text relayIDTextField;
	
	private TableViewer tableViewer;
	private StyledText styledText;
	
 	private Table table;
		
	public EndPointViewPart() {
		super( S_ENDPOINT_VIEWER );
	}


	@Override
	protected void onCreatePartControl(Composite parent) {		
		SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
		Composite composite_2 = new Composite(sashForm, SWT.NONE);
		composite_2.setLayout(new GridLayout(2, false));

		aliveRadioButton = new Button(composite_2, SWT.RADIO);
		aliveRadioButton.setBounds(0, 0, 90, 16);
		aliveRadioButton.setText("Alive");

		isConnectedToRelayCheckBox = new Button(composite_2, SWT.RADIO);
		isConnectedToRelayCheckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IsConnectedToRelayCheckBoxActionPerformed( e );
			}
		});
		isConnectedToRelayCheckBox.setText("is connected to Relay");
		Label lblRelayId = new Label(composite_2, SWT.NONE);
		lblRelayId.setText("Relay ID");

		relayIDTextField = new Text(composite_2, SWT.BORDER);
		relayIDTextField.setEditable(false);
		relayIDTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Composite tableComposite = new Composite(composite_2, SWT.NONE);
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnLayout);

		tableViewer = new TableViewer(tableComposite, SWT.BORDER | SWT.FULL_SELECTION);
		TableViewerColumn column = createColumn("Edges", tableViewer);
		tableColumnLayout.setColumnData(column.getColumn(), new ColumnWeightData(100, 200, true)); 		
		tableViewer.setColumnProperties(new String[] {"Relays"});
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		styledText = new StyledText(sashForm, SWT.BORDER);
		toolkit.adapt(styledText);
		toolkit.paintBordersFor(styledText);
		
		sashForm.setWeights(new int[] {3, 1});
	}

	public void setInput( ConfigMode mode, List<String> values ){
		tableViewer.setInput( values );
	}
	
	@Override
	protected EndpointService onSetService(PeerGroup peergroup) {
		// Registering as rendezvous event listener
		EndpointService service = peergroup.getEndpointService();
		if( super.getService() != null )
			peergroup.getEndpointService().removeIncomingMessageListener( S_ENDPOINT, S_ENDPOINT_VIEWER );
		service = peergroup.getEndpointService();
		this.monitor = new EndPointEventMonitor( peergroup, this);
		service.addIncomingMessageListener( this.monitor , S_ENDPOINT, S_ENDPOINT_VIEWER );
		return service;
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	protected void onRefresh() {
		if( aliveRadioButton.isDisposed() )
			return;
		aliveRadioButton.setSelection( super.isRunning() );
		PeerGroup peergroup = super.getPeerGroup();
		String msg = peergroup.getPeerName();
		if( peergroup.getPeerID() != null )
			msg += "[" + peergroup.getPeerID().toString() + "]";
		setContentDescription( msg );

		EndpointService endpointService =peergroup.getEndpointService();
		styledText.setText( endpointService.getImplAdvertisement().toString());
		if ( endpointService != null ) {
			Collection<PeerID> x = endpointService.getConnectedRelayPeers();
			if ( x.isEmpty() ) {
				isConnectedToRelayCheckBox.setSelection(false);
				relayIDTextField.setText("");
			} else {
				isConnectedToRelayCheckBox.setSelection(true);
				PeerID[] TmpPID = x.toArray(new PeerID[x.size()]);
				relayIDTextField.setText(TmpPID[0].toString());
			}

			Collection<PeerID> items = endpointService.getConnectedRelayPeers();
			// Sorting Peer IDs
			List<String> strItems = new ArrayList<String>();
			for( PeerID item: items ) 
				strItems.add( item.toString());

			//statusPanel.updateEdges( StrItems );
			setInput( ConfigMode.RELAY, strItems );
		}
	}

	@Override
	protected void onFinalize() {
        stopMonitorTask();
        super.getService().removeIncomingMessageListener( S_ENDPOINT, S_ENDPOINT_VIEWER );
	}

	private class EndPointEventMonitor implements EndpointListener {

		private PeerGroup peergroup;
		private EndPointViewPart viewpart;

		public EndPointEventMonitor( PeerGroup peergroup, EndPointViewPart viewpart) {
			this.peergroup = peergroup;
			this.viewpart = viewpart;
		}

		@Override
		public void processIncomingMessage(Message message,
				EndpointAddress srcAddr, EndpointAddress dstAddr) {
			if ( message == null ) return;

			// Adding the entry
			logJxta( peergroup, message.toString() );
			viewpart.refresh();
		}
	}

	private void IsConnectedToRelayCheckBoxActionPerformed( SelectionEvent evt) {//GEN-FIRST:event_IsConnectedToRelayCheckBoxActionPerformed
		// TODO add your handling code here:
	}
}