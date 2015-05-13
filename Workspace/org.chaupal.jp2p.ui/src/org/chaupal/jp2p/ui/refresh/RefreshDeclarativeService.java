/*******************************************************************************
 * Copyright 2014 Chaupal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Kees Pieters - initial API and implementation
 *******************************************************************************/
package org.chaupal.jp2p.ui.refresh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.osgi.framework.BundleContext;

import net.jp2p.chaupal.dispatcher.IServiceChangedListener;
import net.jp2p.chaupal.dispatcher.ServiceChangedEvent;
import net.jp2p.chaupal.utils.AbstractDeclarativeService;
import net.jp2p.container.Jp2pContainer.ServiceChange;

public class RefreshDeclarativeService extends AbstractDeclarativeService<IServiceChangedListener> {

	//in the component.xml file you will use target="(jp2p.context=contextName)"
	private static String filter = "(jp2p.ui.refresh=*)"; 

	public static final long TIME_OUT = 1000;
	
	private static final String S_WRN_SERVICE_INTERRUPTED = "The service is interrupted: ";
	
	private Collection<IServiceChangedListener> listeners;
	private long time_out;
	private boolean started;
	
	private Runnable runnable = new Runnable(){

		@Override
		public void run() {
			while( started ){
				for( IServiceChangedListener listener: listeners )
					listener.notifyServiceChanged( new ServiceChangedEvent( this, ServiceChange.REFRESH));
				try{
					Thread.sleep(time_out);
				}
				catch( InterruptedException ex ){
					Logger logger = Logger.getLogger( this.getClass().getName() );
					logger.info( S_WRN_SERVICE_INTERRUPTED);
				}	
			}
		}
	};

	private ExecutorService service;

	public RefreshDeclarativeService() {
		this( TIME_OUT );
	}

	public RefreshDeclarativeService(long time_out) {
		super(filter, IServiceChangedListener.class);
		this.time_out = time_out;
		this.started = false;
		listeners = new ArrayList<IServiceChangedListener>();
		service = Executors.newCachedThreadPool();
	}

	@Override
	public void start(BundleContext bc ){
		super.start(bc);
		this.started = true;
		service.execute( runnable );
	}
	
	@Override
	public void stop( BundleContext bc){
		this.started = false;
		Thread.currentThread().interrupt();
		super.stop(bc);
	}
	
	@Override
	protected void onDataRegistered(IServiceChangedListener data) {
		listeners.add( data );
	}

	@Override
	protected void onDataUnRegistered( IServiceChangedListener data) {
		listeners.remove( data );
	}
}
