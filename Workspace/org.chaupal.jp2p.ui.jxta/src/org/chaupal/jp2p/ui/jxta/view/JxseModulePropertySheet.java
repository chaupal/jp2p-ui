package org.chaupal.jp2p.ui.jxta.view;

import org.chaupal.jp2p.ui.jxta.container.JxseContainerNavigator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.PropertySheet;

public class JxseModulePropertySheet extends PropertySheet{

	private static final String S_MODULE_NAVIGATOR_ID = "org.chaupal.jp2p.ui.jxse.module.view";
	
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
		ISelection selection = sel;
		if(!( part instanceof JxseContainerNavigator ))
			selection = null;

		super.selectionChanged(part, selection);
	}

	/**
	 * Only respond to events from the module navigator
	 */
	@Override
	protected boolean isImportant(IWorkbenchPart part) {
		String id = part.getSite().getId();
		if ( !id.equals( S_MODULE_NAVIGATOR_ID))
			return false;
		return true;
	}
}