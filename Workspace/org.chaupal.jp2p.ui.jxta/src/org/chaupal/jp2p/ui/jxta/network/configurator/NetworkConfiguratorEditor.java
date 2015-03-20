/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
/**
 * 
 */
package org.chaupal.jp2p.ui.jxta.network.configurator;

import net.jp2p.container.properties.AbstractJp2pPropertySource;

import org.chaupal.jp2p.ui.jxta.network.endpoint.TcpPropertiesEditor;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.EditorPart;

/**
 * @author roel
 *
 */
public class NetworkConfiguratorEditor extends FormEditor {

	 public static final String ID = "nl.cultuurinzicht.eetmee.rcp.editors.MultiPageGastEditor";
	 private NetworkConfiguratorEditorInput ncEditorInput;
	 
	 @Override
	protected FormToolkit createToolkit(Display display) {
		  // Create a toolkit that shares colors between editors.
		  return new FormToolkit(display);
		 }


		/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#getPartName()
	 */
	@Override
	public String getPartName() {
		String retval = "";
		if (ncEditorInput != null) {
			retval = ncEditorInput.getName();
		}
		return retval;
	}

		/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormEditor#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		ncEditorInput = ((NetworkConfiguratorEditorInput) input);
	}


		@Override
		protected void addPages() {
		  try {
		     addPage(0, new TcpPropertiesEditor(), ncEditorInput);
		     //addPage(1, new ContactgegevensEditor(), ncEditorInput);
		     //addPage(2, new CharacteristicsEditor(), new EterEditorInput(null, ncEditorInput.getGast()));
		     //addPage(3, new StudieBeroepEditor(), new EterEditorInput(null, ncEditorInput.getGast()));
		    // addPage(4, new ProjectEditor(), ncEditorInput);
		     //addPage(5, new DiversenEditor(), new EterEditorInput(null, ncEditorInput.getGast()));
		     //addPage(6, new ContactmomentenEditor(), ncEditorInput);
		     //addPage(7, new HuisgenotenEditor(), ncEditorInput);
		     //addPage(8, new EethistorieEditor(), ncEditorInput);
		     //addPage(9, new GasthistorieEditor(), ncEditorInput);
		     //addPage(10, new BlackListEditor(), ncEditorInput);
		     
		     setPageText(0, TcpPropertiesEditor.TITLE);
		     //setPageText(1, ContactgegevensEditor.TITLE);
		     //setPageText(2, CharacteristicsEditor.TITLE);
		     //setPageText(3, StudieBeroepEditor.TITLE);
		     //setPageText(4, ProjectEditor.TITLE);
		     //setPageText(5, DiversenEditor.TITLE);
		     //setPageText(6, ContactmomentenEditor.TITLE);
		     //setPageText(7, HuisgenotenEditor.TITLE);
		     //setPageText(8, EethistorieEditor.TITLE);
		     //setPageText(9, GasthistorieEditor.TITLE);
		     //setPageText(10, BlackListEditor.TITLE);
		  }
		  catch (PartInitException e) {
		   //
		  }
		 }

		 @Override
		public void doSave(IProgressMonitor monitor) {
				// Check if one of the pages is dirty
				for (Object p: this.pages) {
					if (((AbstractEditor) p).isDirty()) {
						((AbstractEditor) p).doSave(monitor);
					}
				}
				String identifier = AbstractJp2pPropertySource.getIdentifier( ncEditorInput.getSource());
				getEditorSite().getActionBars().getStatusLineManager().setMessage("Wijzigingen " + identifier + " zijn opgeslagen.");
			 }

			 
		 /* (non-Javadoc)
		 * @see org.eclipse.ui.forms.editor.FormEditor#isDirty()
		 */
		@Override
		public boolean isDirty() {
			boolean isDirty = false;
			
			// Check if one of the pages is dirty
			for (Object p: this.pages) {
				if (((EditorPart) p).isDirty()) {
					isDirty = true;
					break;
				}
			}
			return isDirty;
		}

		@Override
		public void doSaveAs() {
		 }

		 @Override
		public boolean isSaveAsAllowed() {
		  return false;
		 }

}
