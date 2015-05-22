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

import net.jp2p.chaupal.dispatcher.IServiceChangedListener;
import net.jp2p.chaupal.dispatcher.ServiceChangedEvent;

public abstract class AbstractRefreshDispatcher implements IServiceChangedListener{

	private Collection<IServiceChangedListener> listeners; 

	private boolean started;
	private String name;

	protected AbstractRefreshDispatcher( String name ) {
		listeners = new ArrayList<IServiceChangedListener>();
	}

	public void start(){
		this.started = true;
	}
	
	public void stop(){
		this.started = false;
		this.listeners.clear();
	}

	public final String getName() {
		return name;
	}

	public void addServiceChangeListener( IServiceChangedListener listener ){
		this.listeners.add( listener );
	}

	public void removeServiceChangeListener( IServiceChangedListener listener ){
		this.listeners.remove( listener );
	}

	@Override
	public void notifyServiceChanged(ServiceChangedEvent event) {
		if( !this.started )
			return;
		for( IServiceChangedListener listener: listeners ){
			listener.notifyServiceChanged(event);
		}		
	}
}