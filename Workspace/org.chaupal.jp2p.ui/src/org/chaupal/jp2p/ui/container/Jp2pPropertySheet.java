package org.chaupal.jp2p.ui.container;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.PropertySheet;

public class Jp2pPropertySheet extends PropertySheet{
	
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
		ISelection selection = sel;
		if(!( part instanceof Jp2pContainerNavigator ))
			selection = null;
		super.selectionChanged(part, selection);
	}

	/**
	 * Only respond to events from the module navigator
	 */
	@Override
	protected boolean isImportant(IWorkbenchPart part) {
		String id = part.getSite().getId();
		return Jp2pContainerNavigator.S_JP2P_NAVIGATOR_ID.equals( id );
	}
}