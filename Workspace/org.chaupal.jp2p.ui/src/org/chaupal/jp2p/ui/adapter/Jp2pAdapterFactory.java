package org.chaupal.jp2p.ui.adapter;

import net.jp2p.container.component.IJp2pComponent;
import org.chaupal.jp2p.ui.property.Jp2pComponentUIPropertySource;
import org.chaupal.jp2p.ui.property.SimpleUIPropertySource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;

public class Jp2pAdapterFactory implements IAdapterFactory {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		  if(adapterType != IPropertySource.class )
			  return null;
		  if( adaptableObject instanceof IJp2pComponent )
			  return this.getPropertySource(((IJp2pComponent<Object>) adaptableObject) );
		  return new SimpleUIPropertySource( adaptableObject );
	}

	/**
	 * Get the correct property source
	 * @param adaptableObject
	 * @return
	 */
	protected IPropertySource getPropertySource( IJp2pComponent<Object> adaptableObject ){
		return new Jp2pComponentUIPropertySource<Object>( adaptableObject, Jp2pComponentUIPropertySource.S_PROPERTY_JP2P_TEXT );			
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class[] getAdapterList() {
		return new Class[]{IPropertySource.class };
	}

}
