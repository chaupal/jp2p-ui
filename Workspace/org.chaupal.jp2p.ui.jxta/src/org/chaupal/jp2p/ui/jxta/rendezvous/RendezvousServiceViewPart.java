/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.rendezvous;

import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.refplatform.platform.NetworkManager.ConfigMode;
import net.jxta.rendezvous.RendezVousService;
import net.jxta.rendezvous.RendezvousEvent;
import net.jxta.rendezvous.RendezvousListener;

import org.chaupal.jp2p.ui.jxta.view.AbstractJp2pServiceViewPart;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.custom.StyledText;

public class RendezvousServiceViewPart extends AbstractJp2pServiceViewPart<RendezVousService>{

	public static final String ID = "org.chaupal.jp2p.ui.jxta.rendezvous.RendezVousServiceViewPart"; //$NON-NLS-1$
	public static final String S_RENDEZ_VOUS_VIEWER = "Rendezvous Viewer";
	
	private Button aliveRadioButton;
	private Button isRDVCheckBox;
	private Button isConnectedToRDVCheckBox;
	
	private TableViewer tableViewer;

    private RdvEventMonitor rdvMonitor;
	private Table table;
	private Composite composite_1;
	private StyledText styledText;

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	public RendezvousServiceViewPart() {
		super( S_RENDEZ_VOUS_VIEWER );
	}

	
	@Override
	protected void onCreatePartControl(Composite parent) {
		SashForm sashForm = new SashForm(parent, SWT.VERTICAL);

		Composite composite_2 = new Composite(sashForm, SWT.NONE);
		composite_2.setLayout(new GridLayout(2, false));

		aliveRadioButton = new Button(composite_2, SWT.RADIO);
		aliveRadioButton.setBounds(0, 0, 90, 16);
		aliveRadioButton.setText("Alive");

		isRDVCheckBox = new Button(composite_2, SWT.CHECK);
		isRDVCheckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IsRDVCheckBoxActionPerformed(e);			}
		});
		isRDVCheckBox.setText("is RDV");

		isConnectedToRDVCheckBox = new Button(composite_2, SWT.CHECK);
		isConnectedToRDVCheckBox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		isConnectedToRDVCheckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IsConnectedToRDVCheckBoxActionPerformed(e);
			}
		});
		isConnectedToRDVCheckBox.setText("is connected to RDV");

		composite_1 = new Composite( composite_2, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite tableComposite = new Composite(composite_1, SWT.NONE);
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnLayout);

		tableViewer = new TableViewer(tableComposite, SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		TableViewerColumn column = createColumn("RendezVous", tableViewer);
		tableColumnLayout.setColumnData(column.getColumn(), new ColumnWeightData(100, 200, true)); 		
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		styledText = new StyledText(sashForm, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.WRAP);
		toolkit.adapt(styledText);
		toolkit.paintBordersFor(styledText);

		sashForm.setWeights(new int[] {3, 1});
	}

	
	public void setInput( ConfigMode mode, List<String> values ){
		tableViewer.setInput(values);
	}
	
    // Resetting frame value
    public void resettingFrameValues() {        
        this.setPartName("Connectivity Monitor");
    }

	@Override
	protected RendezVousService onSetService(PeerGroup peergroup) {
		RendezVousService rdvService = peergroup.getRendezVousService();
         if( this.rdvMonitor != null ){
             rdvService.removeListener(this.rdvMonitor);        	 
         }
		 this.rdvMonitor = new RdvEventMonitor();
         rdvService.addListener(this.rdvMonitor);
         return rdvService;
	}


	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	protected void onRefresh() {
		aliveRadioButton.setSelection( super.isRunning() );
		isRDVCheckBox.setSelection( super.getService().isRendezVous());
		isConnectedToRDVCheckBox.setSelection( super.getService().isConnectedToRendezVous());

		List<PeerID> items = super.getService().getLocalRendezVousView();
		// Sorting Peer IDs
		List<String> StrItems = new ArrayList<String>();
		for (int i=0;i<items.size();i++) 
			StrItems.add(items.get(i).toString());
		setInput( ConfigMode.RENDEZVOUS, StrItems );
		styledText.setText( super.getService().getImplAdvertisement().toString());
	}

    private void IsConnectedToRDVCheckBoxActionPerformed(SelectionEvent e) {//GEN-FIRST:event_IsConnectedToRDVCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IsConnectedToRDVCheckBoxActionPerformed

    private void IsRDVCheckBoxActionPerformed(SelectionEvent e) {//GEN-FIRST:event_IsRDVCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IsRDVCheckBoxActionPerformed


    @Override
	protected void onFinalize() {
       super.getService().removeListener( this.rdvMonitor);
    }

    public class RdvEventMonitor implements RendezvousListener {

    	@Override
		public void rendezvousEvent(RendezvousEvent event) {
     		if ( event == null ) 
    			return;
            String Log = null;
            //RendezVousService ddvs = (RendezVousService) event.getSource();

            if ( event.getType() == RendezvousEvent.RDVCONNECT ) {
                Log = "Connection to RDV";
            } else if ( event.getType() == RendezvousEvent.RDVRECONNECT ) {
                Log = "Reconnection to RDV";
            } else if ( event.getType() == RendezvousEvent.CLIENTCONNECT ) {
                Log = "EDGE client connection";
            } else if ( event.getType() == RendezvousEvent.CLIENTRECONNECT ) {
                Log = "EDGE client reconnection";
            } else if ( event.getType() == RendezvousEvent.RDVDISCONNECT ) {
                Log = "Disconnection from RDV";
            } else if ( event.getType() == RendezvousEvent.RDVFAILED ) {
                Log = "Connection to RDV failed";
            } else if ( event.getType() == RendezvousEvent.CLIENTDISCONNECT ) {
                Log = "EDGE client disconnection from RDV";
            } else if ( event.getType() == RendezvousEvent.CLIENTFAILED ) {
                Log = "EDGE client connection to RDV failed";
            } else if ( event.getType() == RendezvousEvent.BECAMERDV ) {
                Log = "This peer became RDV";
            } else if ( event.getType() == RendezvousEvent.BECAMEEDGE ) {
                Log = "This peer became EDGE";
            }

            String TempPID = event.getPeer();
            if ( TempPID != null ) Log = Log + "\n  " + TempPID;

            // Adding the entry
            //logJxta( ddvs.gpeerGroup, Log );

        }
    }
}