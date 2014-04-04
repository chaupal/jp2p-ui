/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.jxta.perspective;

import org.chaupal.jp2p.ui.console.Jp2pConsole;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;

public class Perspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);

		MessageConsole console = new Jp2pConsole();
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { console });
		ConsolePlugin.getDefault().getConsoleManager().showConsoleView(console);

		
		layout.setFixed(false);
		
	}

}
