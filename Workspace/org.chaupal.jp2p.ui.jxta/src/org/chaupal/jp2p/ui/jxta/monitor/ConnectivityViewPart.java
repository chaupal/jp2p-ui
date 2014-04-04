/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.monitor;

import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.log.Jp2pLevel;
import net.jxta.endpoint.EndpointService;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.refplatform.platform.NetworkManager.ConfigMode;
import net.jxta.rendezvous.RendezVousService;
import net.jxta.rendezvous.RendezvousEvent;
import net.jxta.rendezvous.RendezvousListener;

import org.chaupal.jp2p.ui.jxta.util.ColorUtils;
import org.chaupal.jp2p.ui.jxta.util.ColorUtils.SupportedColors;
import org.chaupal.jp2p.ui.log.Jp2pLog;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;

public class ConnectivityViewPart extends ViewPart{

	public static final String ID = "net.equinox.jxta.ui.monitor.ConnectivityViewPart"; //$NON-NLS-1$
	public static final String S_CONNECTIVITY_MONITOR = "Connectivity Monitor";
	
	private Group grpStatuspane;
	private Text peerNameTextField;
	private Text peerGroupNameTextField;
	private Text parentGroupNameTextField;
	private Text peerIDTextField;
	private Text peerGroupIDTextField;
	private Text parentGroupIDTextField;
	
	private Group grpDisplaypanel;
	private Button aliveRadioButton;
	private Button isRDVCheckBox;
	private Button isConnectedToRDVCheckBox;
	private Button isConnectedToRelayCheckBox;
	private Text relayIDTextField;
	
	private TableViewer tableViewer;

    private PeerGroup peerGroup = null;
    private RdvEventMonitor rdvMonitor;
    
    private Future<?> theMonitorFuture = null;
	public static final ScheduledExecutorService theExecutor = Executors.newScheduledThreadPool(5);

	private Table table;
	private Composite composite_1;
	private SashForm sashForm_1;
	
	private Runnable runner = new Runnable(){

		@Override
		public void run() {
	    	if( peerGroup != null )
	            refresh();
		}
	};
	
	public ConnectivityViewPart() {
		setPartName( S_CONNECTIVITY_MONITOR );
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(new ISelectionListener() {
			@Override
			public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
				// we ignore our own selections
				if ( sourcepart instanceof ConnectivityViewPart )
					return;
				showSelection( sourcepart, selection);
			}
		});
		SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
		
		sashForm_1 = new SashForm(sashForm, SWT.NONE);
		
		this.grpStatuspane = new Group(sashForm_1, SWT.NONE);
		grpStatuspane.setText("StatusPane");
		GridLayout gl_grpStatuspane = new GridLayout(2, false);
		grpStatuspane.setLayout(gl_grpStatuspane);
		
		Label lblPeer = new Label(grpStatuspane, SWT.NONE);
		lblPeer.setBounds(0, 0, 55, 15);
		lblPeer.setText("Peer");
		
		peerNameTextField = new Text(grpStatuspane, SWT.BORDER);
		peerNameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		peerNameTextField.setEditable(false);
		peerNameTextField.setBounds(0, 0, 76, 21);
		
		peerIDTextField = new Text(grpStatuspane, SWT.BORDER);
		peerIDTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		peerIDTextField.setEditable(false);
		
		Label lblPeerGroup = new Label(grpStatuspane, SWT.NONE);
		lblPeerGroup.setText("Peer Group");
		lblPeerGroup.setBounds(0, 0, 36, 15);
		
		peerGroupNameTextField = new Text(grpStatuspane, SWT.BORDER);
		peerGroupNameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		peerGroupNameTextField.setEditable(false);
		peerGroupNameTextField.setBounds(0, 0, 238, 21);
		
		peerGroupIDTextField = new Text(grpStatuspane, SWT.BORDER);
		peerGroupIDTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		peerGroupIDTextField.setEditable(false);
		
		Label lblParentGroup = new Label(grpStatuspane, SWT.NONE);
		lblParentGroup.setText("Parent Group");
		lblParentGroup.setBounds(0, 0, 36, 15);
		
		parentGroupNameTextField = new Text(grpStatuspane, SWT.BORDER);
		parentGroupNameTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		parentGroupNameTextField.setEditable(false);
		parentGroupNameTextField.setBounds(0, 0, 238, 21);
		
		parentGroupIDTextField = new Text(grpStatuspane, SWT.BORDER);
		parentGroupIDTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		parentGroupIDTextField.setEditable(false);
		
		grpDisplaypanel = new Group(sashForm_1, SWT.NONE);
		grpDisplaypanel.setText("DisplayPanel");
		grpDisplaypanel.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite_2 = new Composite(grpDisplaypanel, SWT.NONE);
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
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		isConnectedToRDVCheckBox = new Button(composite_2, SWT.CHECK);
		isConnectedToRDVCheckBox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		isConnectedToRDVCheckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IsConnectedToRDVCheckBoxActionPerformed(e);
			}
		});
		isConnectedToRDVCheckBox.setText("is connected to RDV");

		isConnectedToRelayCheckBox = new Button(composite_2, SWT.CHECK);
		isConnectedToRelayCheckBox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		isConnectedToRelayCheckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IsConnectedToRelayCheckBoxActionPerformed( e );
			}
		});
		isConnectedToRelayCheckBox.setText("is connected to Relay");
				
				new Label(composite_2, SWT.NONE);
				new Label(composite_2, SWT.NONE);
				Label lblRelayId = new Label(composite_2, SWT.NONE);
				lblRelayId.setText("Relay ID");
				new Label(composite_2, SWT.NONE);
				
				relayIDTextField = new Text(composite_2, SWT.BORDER);
				relayIDTextField.setEditable(false);
				relayIDTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		sashForm_1.setWeights(new int[] {1, 1});
		
		composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_4 = new Composite(composite_1, SWT.NONE);
		composite_4.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_3 = new Composite(composite_1, SWT.NONE);
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		tableViewer = new TableViewer(composite_3, SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumn("Relays", tableViewer);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		sashForm.setWeights(new int[] {1, 1});

		createActions();
		initializeToolBar();
		initializeMenu();
        theMonitorFuture = theExecutor.scheduleAtFixedRate(runner, 5, 1, TimeUnit.SECONDS);
	}

	private void createColumn( String name, TableViewer viewer ){
		TableViewerColumn col = createTableViewerColumn( viewer, name, 100, 0 );
		col.setLabelProvider(new ColumnLabelProvider() {
		  @Override
		  public String getText(Object element) {
		    return super.getText(element);
		  }
		});		
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
	
	void setInput( ConfigMode mode, List<String> values ){
		tableViewer.setInput(values);
	}
	
    // Resetting frame value
    public void resettingFrameValues() {        
        this.setPartName("Connectivity Monitor");
        this.peerNameTextField.setText("<unknown>");
        this.peerIDTextField.setText("");
        this.peerGroupNameTextField.setText("<unknown>");
        this.peerGroupIDTextField.setText("");
        this.parentGroupNameTextField.setText("<unknown>");
        this.parentGroupIDTextField.setText("");
    }

	/**
	 * Shows the given selection in this view.
	 */
	void showSelection(IWorkbenchPart sourcepart, ISelection selection) {
		if(!( selection instanceof IStructuredSelection))
			return;
		
		IStructuredSelection ss = (IStructuredSelection) selection;
		Object element = ss.getFirstElement();
		
		//We check for service decorators coming from the service navigator
		if(!( element instanceof IJp2pComponent<?>))
			return;
		IJp2pComponent<?> component = (IJp2pComponent<?> )element;
		if(!( component.getModule() instanceof PeerGroup ))
			this.setPeerGroup(null );
		else
		  this.setPeerGroup( (PeerGroup) component.getModule());
	}

    /** Creates new form ConnectivityMonitor */
    private void setPeerGroup(final PeerGroup inGroup) {

        // Initialization
        peerGroup = inGroup;
		Display.getDefault().asyncExec(new Runnable() {
            @Override
			public void run() {
                // Displaying the frame on the awt queue
                if( peerGroup == null )
                	resettingFrameValues();

            	grpStatuspane.setVisible( inGroup != null );
            	grpDisplaypanel.setVisible( inGroup != null );
            }
         });			
         if( peerGroup == null )
        	 return;
         
         // Registering as rendezvous event listener
         if( this.rdvMonitor != null )
         	inGroup.getRendezVousService().removeListener(this.rdvMonitor );
         this.rdvMonitor = new RdvEventMonitor(this);
         inGroup.getRendezVousService().addListener(this.rdvMonitor);

        // Starting the monitor
        logJxta( peerGroup, "Starting to monitor the peergroup " + peerGroup.getPeerGroupName() );
    }

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	@SuppressWarnings("unused")
	private void initializeToolBar() {
		IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	@SuppressWarnings("unused")
	private void initializeMenu() {
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	protected void refresh(){
		if( Display.getDefault().isDisposed() )
			return;
		Display.getDefault().asyncExec(new Runnable() {
            @Override
			public void run() {
                if(( peerGroup == null ) || ( peerNameTextField.isDisposed() ))
                	return;
                
            	String msg = peerGroup.getPeerName();
                if( peerGroup.getPeerID() != null )
                	msg += "[" + peerGroup.getPeerID().toString() + "]";
            	setContentDescription( msg );
                        

                peerNameTextField.setText(peerGroup.getPeerGroupName());
                peerIDTextField.setText(peerGroup.getPeerID().toString());

                peerGroupNameTextField.setText(peerGroup.getPeerGroupName());
                peerGroupIDTextField.setText(peerGroup.getPeerGroupID().toString());

                PeerGroup ParentPG = peerGroup.getParentGroup();

                if ( ParentPG != null) {
                    parentGroupNameTextField.setText(ParentPG.getPeerGroupName());
                    parentGroupIDTextField.setText(ParentPG.getPeerGroupID().toString());

                }

                RendezVousService TmpRDVS = peerGroup.getRendezVousService();
                if ( TmpRDVS != null ) {
                    isRDVCheckBox.setSelection(TmpRDVS.isRendezVous());
                    isConnectedToRDVCheckBox.setSelection(TmpRDVS.isConnectedToRendezVous());

                    List<PeerID> Items = TmpRDVS.getLocalRendezVousView();
                    // Sorting Peer IDs
                    List<String> StrItems = new ArrayList<String>();
                    for (int i=0;i<Items.size();i++) 
                        StrItems.add(Items.get(i).toString());
                    //statusPanel.updateRDVs( StrItems );
                    setInput( ConfigMode.RELAY, StrItems );
                } else {
                	logJxta( peerGroup, "Rendezvous service is NULL");
                }
                
                EndpointService TmpES =peerGroup.getEndpointService();
                if ( TmpES != null ) {
                    Collection<PeerID> x = TmpES.getConnectedRelayPeers();
                    if ( x.isEmpty() ) {
                        isConnectedToRelayCheckBox.setSelection(false);
                        relayIDTextField.setText("");
                    } else {
                        isConnectedToRelayCheckBox.setSelection(true);
                        PeerID[] TmpPID = x.toArray(new PeerID[x.size()]);
                        relayIDTextField.setText(TmpPID[0].toString());
                    }

                    List<PeerID> Items = TmpRDVS.getLocalEdgeView();
                    // Sorting Peer IDs
                    List<String> StrItems = new ArrayList<String>();
                    for (int i=0;i<Items.size();i++) 
                        StrItems.add(Items.get(i).toString());

                    setInput( ConfigMode.EDGE, StrItems );

                } else {
                	logJxta( peerGroup, "Endpoint service is NULL");
                }
            }
         });			
	}

    private void IsConnectedToRelayCheckBoxActionPerformed( SelectionEvent evt) {//GEN-FIRST:event_IsConnectedToRelayCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IsConnectedToRelayCheckBoxActionPerformed

    private void IsConnectedToRDVCheckBoxActionPerformed(SelectionEvent e) {//GEN-FIRST:event_IsConnectedToRDVCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IsConnectedToRDVCheckBoxActionPerformed

    private void IsRDVCheckBoxActionPerformed(SelectionEvent e) {//GEN-FIRST:event_IsRDVCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IsRDVCheckBoxActionPerformed

    private synchronized void stopMonitorTask() {

        if ( theMonitorFuture != null ) {
            theMonitorFuture.cancel(false);
        }
    }

    @Override
    protected void finalize() {
        stopMonitorTask();
    }

    public class RdvEventMonitor implements RendezvousListener {

        public RdvEventMonitor( ConnectivityViewPart inCM) {
        }

        @Override
		public void rendezvousEvent(RendezvousEvent event) {
            if ( event == null ) return;
            String Log = null;

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
            logJxta( peerGroup, Log );

        }
    }
    
    private void logJxta( final PeerGroup peerGroup, final String message ){
    	if( Display.getDefault().isDisposed() )
    		return;
    	Display.getDefault().asyncExec(new Runnable() {
    		@Override
    		public void run() {
    			LogRecord record = new LogRecord( Jp2pLevel.getJxtaLevel(), message );
    			record.setSourceClassName( this.getClass().getName() );
    			Object[] parameters = new Object[2];
    			parameters[0] = peerGroup.getPeerName();
    			Color color = ColorUtils.getSWTColor( Display.getDefault(), SupportedColors.COLOR_CYAN );//coding.get( peerGroup ));
    			parameters[1] = color;
    			record.setParameters(parameters);
    			Jp2pLog.logJp2p( record );
    		}
    	});
    }
}
