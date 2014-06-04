package org.chaupal.jp2p.ui.adapter;

import net.jp2p.container.component.IJp2pComponent;

import org.chaupal.jp2p.ui.property.SimpleUIPropertySource;
import org.eclipse.core.runtime.IAdapterFactory;

public class ExtendedAdapterFactory implements IAdapterFactory {

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		  if(adapterType != SimpleUIPropertySource.class )
			  return null;
		  if( adaptableObject instanceof IJp2pComponent )
			  return null;
		  return new SimpleUIPropertySource( adaptableObject );
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class[] getAdapterList() {
		return new Class[]{SimpleUIPropertySource.class };
	}

}
