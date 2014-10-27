package org.chaupal.jp2p.ui.jxta.view;

import org.chaupal.jp2p.ui.jxta.container.JxseContainerNavigator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.PropertySheet;

public class JxseModulePropertySheet extends PropertySheet{

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
		if(!( part instanceof JxseContainerNavigator ))
			return;

		super.selectionChanged(part, sel);
	}

	public JxseModulePropertySheet() {
		//super.getSite().set
	}
	
	
}
