/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.network.configurator;

import net.jp2p.container.utils.Utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.EditorPart;


public abstract class AbstractEditor extends EditorPart {
	
	private boolean isDirty = false;
	protected FormToolkit formToolkit;
	protected Form form;

	public AbstractEditor() {
		super();
	}

	@Override
	public final boolean isDirty() {
		return isDirty;
	}

	@Override
	public final boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public final void doSaveAs() {
	}
	
	@Override
	public void setFocus() {
		form.setFocus();
	}

	@Override
	public void dispose() {
		formToolkit.dispose();
		super.dispose();
	}

	class DirtyListener implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			isDirty = true;
			firePropertyChange(PROP_DIRTY);
		}
	}
	
	protected static final Group createGroup(Composite parent, int numOfColumns, String text) {
		Group retval = new Group(parent, SWT.NONE);
		retval.setLayout(new GridLayout(numOfColumns, false));
		retval.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		if (!Utils.isNull( text)) {
			retval.setText(text);
		}
		return retval;
	}

	protected final void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(PROP_DIRTY);

		if (! isDirty) {
			firePropertyChange(PROP_TITLE);
			setPartName(createPartname());
		}
	}

	/**
	 * @return
	 */
	protected abstract String createPartname();
	
}