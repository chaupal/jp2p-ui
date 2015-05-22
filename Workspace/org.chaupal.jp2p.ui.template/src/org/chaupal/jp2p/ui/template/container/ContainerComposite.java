/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.template.container;

import java.net.URI;

import net.jp2p.container.Jp2pContainerPropertySource;
import net.jp2p.container.IJp2pContainer.ContainerProperties;
import net.jp2p.container.properties.AbstractJp2pPropertySource;
import net.jp2p.container.properties.IJp2pDirectives;
import net.jp2p.container.properties.IJp2pDirectives.Directives;
import net.jp2p.container.properties.IJp2pProperties.Jp2pProperties;
import net.jp2p.container.utils.Utils;
import net.jp2p.container.validator.StringValidator;

import org.chaupal.jp2p.ui.property.databinding.StringDataBinding;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Spinner;

public class ContainerComposite extends Composite {
	
	private static final String S_JP2P_CONTAINER = ".jp2p.container";
	private Text text_identifier;
	private Label lbl_plugin_id;
	private Text text_home_folder;
	private Combo combo;
	
	private Button btnAutoStart;
	private Combo combo_peer_id;
	private Button btnGenerate;
	private Button btnPersist;
	
	private Jp2pContainerPropertySource source;
	private Label lblPort;
	private Spinner spinner;
	private Group grpSecurity;
	private Label lblPass;
	private Text text_pass1;
	private Label lblPass_1;
	private Text text_pass2;
	private Label lblId;
	private Text text_id;
		
	public ContainerComposite(Composite parent, int span) {
		super( parent, span);
		this.createControl(parent, span);
	}
	
	protected void createControl(Composite parent, int span ) {
		parent.setLayout( new FillLayout());
		Composite container = this;
		container.setLayout(new GridLayout(2, false));
				
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Plugin-Id:");
		
		lbl_plugin_id = new Label(container, SWT.BORDER);
		lbl_plugin_id.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblId = new Label(this, SWT.NONE);
		lblId.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblId.setText("id:");
		
		text_id = new Text(this, SWT.BORDER);
		text_id.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				source.setDirective( Directives.ID, (( Text )e.item ).getText() );
			}
		});
		text_id.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblName = new Label(container, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name:");
		
		text_identifier = new Text(container, SWT.BORDER);
		text_identifier.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				source.setDirective( Directives.NAME, (( Text )e.item ).getText() );
			}
		});
		text_identifier.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		Label lblHomeFolder = new Label(container, SWT.NONE);
		lblHomeFolder.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblHomeFolder.setText("Home Folder:");
		
		text_home_folder = new Text(container, SWT.BORDER);
		text_home_folder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				source.setProperty( ContainerProperties.HOME_FOLDER, (( Text )e.item ).getText() );
			}
		});
		text_home_folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label lblConfigMode = new Label(container, SWT.NONE);
		lblConfigMode.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblConfigMode.setText("Config Mode:");
		
		combo = new Combo(container, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblPort = new Label(container, SWT.NONE);
		lblPort.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPort.setText("Port:");
		
		spinner = new Spinner(container, SWT.BORDER);
		spinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(this, SWT.NONE);
		
		btnAutoStart = new Button(container, SWT.CHECK);
		btnAutoStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				source.setDirective( IJp2pDirectives.Directives.AUTO_START, Boolean.toString((( Button )e.item ).getSelection() ));
			}
		});
		btnAutoStart.setText("Auto Start");
		
		Group grpPeerId = new Group(container, SWT.NONE);
		grpPeerId.setText("Peer ID:");
		grpPeerId.setLayout(new GridLayout(2, false));
		grpPeerId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		combo_peer_id = new Combo(grpPeerId, SWT.NONE);
		combo_peer_id.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		btnGenerate = new Button(grpPeerId, SWT.CHECK);
		btnGenerate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button selected = (Button) e.getSource();
//TODO				properties.setDirective( IJxseDirectives.Directives.PEER_ID_CREATE, selected.getSelection() );
				combo_peer_id.setEnabled(!selected.getSelection());
			}
		});
		btnGenerate.setText("Generate");
		
		btnPersist = new Button(grpPeerId, SWT.CHECK);
		btnPersist.setText("Persist");
		btnPersist.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//TODO				properties.setDirective( IJxseDirectives.Directives.PEER_ID_PERSIST, (( Button )e.item ).getSelection() );
			}
		});

		grpSecurity = new Group(this, SWT.NONE);
		grpSecurity.setText("Security:");
		grpSecurity.setLayout(new GridLayout(2, false));
		grpSecurity.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		
		lblPass = new Label(grpSecurity, SWT.NONE);
		lblPass.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPass.setText("Pass 1:");
		
		text_pass1 = new Text(grpSecurity, SWT.BORDER);
		text_pass1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				source.setProperty( ContainerProperties.PASS_1, (( Text )e.item ).getText() );
			}
		});
		text_pass1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblPass_1 = new Label(grpSecurity, SWT.NONE);
		lblPass_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPass_1.setText("Pass 2:");
		
		text_pass2 = new Text(grpSecurity, SWT.BORDER);
		text_pass2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				source.setProperty( ContainerProperties.PASS_1, (( Text )e.item ).getText() );
			}
		});
		text_pass2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}
	
	
	public Jp2pContainerPropertySource getPropertySource() {
		return source;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init( Jp2pContainerPropertySource properties ){
		this.source = properties;
		Object obj = properties.getProperty( ContainerProperties.HOME_FOLDER );
		if( obj != null )
			this.text_home_folder.setText( obj.toString() );
		
		obj = properties.getProperty( Jp2pProperties.BUNDLE_ID );
		if( obj != null ){
			this.lbl_plugin_id.setText( " " + obj.toString() );
			this.text_id.setText( obj.toString() + S_JP2P_CONTAINER);
		}
		StringDataBinding sdb = new StringDataBinding<Jp2pProperties>( Jp2pProperties.BUNDLE_ID, properties, this.text_id );  
		sdb.setValidator( new StringValidator<Jp2pProperties>( Jp2pProperties.BUNDLE_ID, StringValidator.S_BUNDLE_ID_REGEX ));

		obj = AbstractJp2pPropertySource.getIdentifier( source);
		//sdb = new StringDataBinding<Directives>( Directives.NAME, properties, this.text_identifier );  
		//sdb.setValidator( new StringValidator<Jp2pProperties>( Jp2pProperties.BUNDLE_ID, StringValidator.S_NAME_REGEX ));

		//TODO CP: this.btnAutoStart.setSelection((boolean) properties.getDefaultDirectives( IJxseDirectives.Directives.AUTO_START ));
		//TODO CP: this.btnPersist.setSelection((boolean) properties.getDefaultDirectives( IJxseDirectives.Directives.PEER_ID_PERSIST ));
		//TODO CP: obj = properties.getDirective( IJxseDirectives.Directives.PEER_ID_CREATE );
		boolean create = ( obj == null )?false: (boolean )obj;
		this.btnGenerate.setSelection(create);
		this.combo_peer_id.setEnabled(!create);
	}

	/**
	 * Complete the view by filling in the properties and directives
	 */
	public boolean complete() throws Exception{
		if( Utils.isNull( this.text_id.getText()))
			return false;
		source.setDirective( Directives.ID, text_id.getText() );
		if( !source.setProperty( ContainerProperties.HOME_FOLDER, URI.create( this.text_home_folder.getText() )))
			return false;
		if( !source.setProperty( Jp2pProperties.BUNDLE_ID, this.lbl_plugin_id.getText() ))
			return false;
		if( !source.setProperty( ContainerProperties.PASS_1, this.text_pass1.getText() ))
			return false;
		if( !source.setProperty( ContainerProperties.PASS_2, this.text_pass2.getText() ))
			return false;
		if( !source.setDirective( IJp2pDirectives.Directives.AUTO_START, Boolean.toString( this.btnAutoStart.getSelection() )))
			return false;
		return true;
		//if( !properties.setDirective( IJxseDirectives.Directives.PEER_ID_CREATE, this.btnGenerate.getSelection() ))
		//	return false;
		//return properties.setDirective( IJxseDirectives.Directives.PEER_ID_PERSIST, this.btnPersist.getSelection() );
	}
}
