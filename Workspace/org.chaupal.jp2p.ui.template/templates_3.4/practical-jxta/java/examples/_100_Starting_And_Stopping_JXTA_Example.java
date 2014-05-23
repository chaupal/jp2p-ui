/*
 * Copyright (c) 2010 DawningStreams, Inc.  All rights reserved.
 *  
 *  Redistribution and use in source and binary forms, with or without 
 *  modification, are permitted provided that the following conditions are met:
 *  
 *  1. Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *  
 *  2. Redistributions in binary form must reproduce the above copyright notice, 
 *     this list of conditions and the following disclaimer in the documentation 
 *     and/or other materials provided with the distribution.
 *  
 *  3. The end-user documentation included with the redistribution, if any, must 
 *     include the following acknowledgment: "This product includes software 
 *     developed by DawningStreams, Inc." 
 *     Alternately, this acknowledgment may appear in the software itself, if 
 *     and wherever such third-party acknowledgments normally appear.
 *  
 *  4. The name "DawningStreams,Inc." must not be used to endorse or promote
 *     products derived from this software without prior written permission.
 *     For written permission, please contact DawningStreams,Inc. at 
 *     http://www.dawningstreams.com.
 *  
 *  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 *  INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 *  DAWNINGSTREAMS, INC OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 *  OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 *  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  
 *  DawningStreams is a registered trademark of DawningStreams, Inc. in the United 
 *  States and other countries.
 *  
 */
package $packageName$.examples;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import $packageName$.Activator;
import net.jp2p.container.Jp2pContainerPropertySource;
import net.jp2p.container.context.Jp2pContainerPreferences;
import net.jp2p.container.properties.AbstractJp2pPropertySource;
import net.jp2p.jxse.compatibility.container.AbstractJxseContainer;
import net.jp2p.jxse.compatibility.utils.Tools;
import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.PeerGroup;
import net.jxta.platform.NetworkManager;
import net.jxta.platform.NetworkManager.ConfigMode;

public class _100_Starting_And_Stopping_JXTA_Example extends AbstractJxseContainer{
    
    public static final String Name = "Example 100";
   
    private NetworkManager MyNetworkManager;
    private PeerGroup ConnectedVia;
    
    
	public _100_Starting_And_Stopping_JXTA_Example() {
		super( Activator.S_PLUGIN_ID, Name );
	}

	@Override
	public void main( String[] args ) {       
        try {
        	Jp2pContainerPropertySource source = new Jp2pContainerPropertySource( Name );
        	Jp2pContainerPreferences preferences = new Jp2pContainerPreferences( source );
        	File file = new File( preferences.getHomeFolder());
        	if(!file.exists() )
        		file.mkdirs();
        	
            // Creation of the network manager
            this.MyNetworkManager = new NetworkManager( ConfigMode.EDGE, AbstractJp2pPropertySource.getIdentifier(source));
            this.MyNetworkManager.setInstanceHome(preferences.getHomeFolder());
            addModule( this, this.MyNetworkManager );
            
            // Starting JXTA
            Tools.PopInformationMessage(Name, "Starting JXTA network");
            this.ConnectedVia = MyNetworkManager.startNetwork();
            addModule( this, this.ConnectedVia );
            
            // Displaying peer group information
            Tools.PopInformationMessage(Name, "Connected via Peer Group: " + ConnectedVia.getPeerGroupName());
            
        } catch (IOException Ex) {            
            // Raised when access to local file and directories caused an error
            Tools.PopErrorMessage(Name, Ex.toString());           
        } catch (PeerGroupException Ex) {           
            // Raised when the net peer group could not be created
            Tools.PopErrorMessage(Name, Ex.toString());            
        } catch (URISyntaxException e) {
            Tools.PopErrorMessage(Name, e.toString());            
		}
    }

    // Stopping JXTA
	@Override
	public void stop() {
        Tools.PopInformationMessage(Name, "Stopping JXTA network");
        MyNetworkManager.stopNetwork();
		super.stop();
	}

	@Override
	public NetworkManager getModule() {
		return this.MyNetworkManager;
	}
}
