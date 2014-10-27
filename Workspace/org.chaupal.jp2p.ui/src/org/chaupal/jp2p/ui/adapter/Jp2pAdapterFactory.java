package org.chaupal.jp2p.ui.adapter;

import java.util.Collection;

import net.jp2p.container.component.IJp2pComponent;
import net.jp2p.container.properties.AbstractJp2pPropertySource;

import org.chaupal.jp2p.ui.osgi.PropertySourcePetitioner;
import org.chaupal.jp2p.ui.property.CollectionPropertySource;
import org.chaupal.jp2p.ui.property.IJp2pPropertySourceProvider;
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
		  if( adaptableObject instanceof Collection )
			  return new CollectionPropertySource( "list", (Collection<?>) adaptableObject, "list2" );
		  return new SimpleUIPropertySource( adaptableObject );
	}

	/**
	 * Get the correct property source
	 * @param adaptableObject
	 * @return
	 */
	protected IPropertySource getPropertySource( IJp2pComponent<Object> adaptableObject ){
		PropertySourcePetitioner petitioner = PropertySourcePetitioner.getInstance();
		petitioner.petition(adaptableObject );
		String bundle_id = AbstractJp2pPropertySource.getBundleId( adaptableObject.getPropertySource());
		String component_name = adaptableObject.getPropertySource().getComponentName();
		IJp2pPropertySourceProvider<?> psp = petitioner.getPropertyDescriptorProvider(bundle_id, component_name);
		if( psp != null ){
			IPropertySource ps = psp.getPropertySource();
			if( ps != null )
				return ps;
		}
		return new Jp2pComponentUIPropertySource<Object>( adaptableObject, Jp2pComponentUIPropertySource.S_PROPERTY_JP2P_TEXT );			
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class[] getAdapterList() {
		return new Class[]{IPropertySource.class };
	}

}
