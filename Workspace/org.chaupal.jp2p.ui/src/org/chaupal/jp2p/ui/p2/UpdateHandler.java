/*******************************************************************************
 * Copyright (c) 2014 Chaupal.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.html
 *******************************************************************************/
package org.chaupal.jp2p.ui.p2;

import java.net.URI;
import java.net.URISyntaxException;

import org.chaupal.jp2p.ui.tray.IMenuContribution;
import org.chaupal.jp2p.ui.tray.TrayController;
import org.chaupal.jp2p.ui.util.SWTInfoUtils.InfoTypes;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.operations.ProvisioningJob;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.Update;
import org.eclipse.equinox.p2.operations.UpdateOperation;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;

public class UpdateHandler implements IMenuContribution{

	private static final String S_UPDATE = "Update";

	// repository location needs to be adjusted for your 
	// location
	private static final String REPOSITORY_LOC = 
			System.getProperty("UpdateHandler.Repo", 
					"file://home/vogella/repository");

	private TrayController controller;
	private IWorkbench workbench;

	/* 1. Prepare update plumbing */
	private UpdateOperation operation;

	private boolean updatesFound;

	public UpdateHandler( TrayController controller ) {
		super();
		this.controller = controller;
		controller.addMenuContribution(this);
	}

	@Override
	public String getMenuLabel() {
		return S_UPDATE;
	}


	@Override
	public boolean isEnabled() {
		return updatesFound;
	}


	@Override
	public void performmMenuAction() {
		this.update();
	}


	/**
	 * Returns true if updates have been found
	 * @return
	 */
	public boolean updatesFound() {
		return updatesFound;
	}

	/**
	 * execute the update handler
	 * @param agent
	 * @param monitor
	 */
	public void checkForUpdates(  final IWorkbench workbench, final IProvisioningAgent agent) {
		this.workbench = workbench;
		
		Job j = new Job("Update Job") {

			@Override
			protected IStatus run(final IProgressMonitor monitor) {

				/* 1. Prepare update plumbing */
				final ProvisioningSession session = new ProvisioningSession(agent);
				operation = new UpdateOperation(session);

				// create uri
				URI uri = null;
				try {
					uri = new URI(REPOSITORY_LOC);
				} catch (final URISyntaxException e) {
					controller.setText( InfoTypes.ERROR, "URI invalid" + e.getMessage() );
					return Status.CANCEL_STATUS;
				}

				// set location of artifact and metadata repo
				operation.getProvisioningContext().setArtifactRepositories(new URI[] { uri });
				operation.getProvisioningContext().setMetadataRepositories(new URI[] { uri });

				/* 2. check for updates */

				// run update checks causing I/O
				final IStatus status = operation.resolveModal(monitor);

				// failed to find updates (inform user and exit)
				if (status.getCode() == UpdateOperation.STATUS_NOTHING_TO_UPDATE) {
					controller.setText( InfoTypes.INFO, "No updates for the current installation have been found");
					return Status.CANCEL_STATUS;
				}

				/* 3. Ask if updates should be installed and run installation */

				// found updates, ask user if to install?
				if (status.isOK() && status.getSeverity() != IStatus.ERROR) {
					String updates = "";
					Update[] possibleUpdates = operation
							.getPossibleUpdates();
					for (Update update : possibleUpdates) {
						updates += "\t"+ update + "\n";
					}
					//if( updates.length() == 0 )
					//	return Status.CANCEL_STATUS;

					controller.setText( InfoTypes.QUESTION, "Updates were found. Install them?\n" + updates);
					updatesFound = true;//controller.isClicked();
				}
				return Status.OK_STATUS;
			}
		};
		j.schedule();
	}

	/**
	 * execute the update handler
	 * @param agent
	 * @param monitor
	 */
	public void update() {

		final Shell parent = workbench.getDisplay().getActiveShell();
		Job j = new Job("Update Job") {

			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				if (!updatesFound)
					return Status.CANCEL_STATUS;

				final ProvisioningJob provisioningJob = operation
						.getProvisioningJob(monitor);
				// updates cannot run from within Eclipse IDE!!!
				if (provisioningJob == null) {
					System.err.println("Running update from within Eclipse IDE? This won't work!!!");
					throw new NullPointerException();
				}

				// register a job change listener to track
				// installation progress and notify user upon success
				provisioningJob
				.addJobChangeListener(new JobChangeAdapter() {
					@Override
					public void done(IJobChangeEvent event) {
						if (event.getResult().isOK()) {
							Display.getDefault().asyncExec(new Runnable() {

								@Override
								public void run() {
									boolean restart = MessageDialog
											.openQuestion(parent,
													"Updates installed, restart?",
													"Updates have been installed successfully, do you want to restart?");
									if (restart) {
										workbench.restart();
									}
								}
							});

						}
						super.done(event);
					}
				});
				provisioningJob.schedule();
				return Status.OK_STATUS;
			}
		};
		j.schedule();
	}
}
