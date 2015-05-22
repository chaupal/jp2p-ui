package org.chaupal.jp2p.ui.jxta.view;

<<<<<<< HEAD
import java.util.Iterator;

import net.jp2p.container.component.IJp2pComponent;
import net.jxta.peergroup.core.Module;
=======
import net.jp2p.container.component.IJp2pComponent;
>>>>>>> branch 'master' of https://github.com/chaupal/jp2p-ui.git

import org.chaupal.jp2p.ui.container.Jp2pContainerNavigator;
import org.chaupal.jp2p.ui.jxta.container.JxseContainerNavigator;
import org.eclipse.jface.viewers.ISelection;
<<<<<<< HEAD
import org.eclipse.jface.viewers.StructuredSelection;
=======
>>>>>>> branch 'master' of https://github.com/chaupal/jp2p-ui.git
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.PropertySheet;

public class JxseModulePropertySheet extends PropertySheet{

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
		ISelection selection = sel;
		if( part instanceof JxseContainerNavigator )
			super.selectionChanged(part, selection);			
<<<<<<< HEAD
		else if( part instanceof Jp2pContainerNavigator ){
			Iterator<?> iterator = ((StructuredSelection) sel).iterator();
			boolean visible = false;
			while( iterator.hasNext() ){
				Object component = iterator.next();
				if( component instanceof IJp2pComponent ){
					IJp2pComponent<?> comp = (IJp2pComponent<?>) component;
					if( comp.getModule() instanceof Module ){
						visible = true;
						break;
					}
				}
			}
			if( !visible ){
				selection = new TreeSelection();
			}
			super.selectionChanged(part, /* new Selection( (TreeSelection) */ selection );
		}
=======
		if( part instanceof Jp2pContainerNavigator )
			super.selectionChanged(part, /* new Selection( (TreeSelection) */ selection );
>>>>>>> branch 'master' of https://github.com/chaupal/jp2p-ui.git
		else
			super.selectionChanged(part, null);
	}

	/**
	 * Only respond to events from the module navigator
	 */
	@Override
	protected boolean isImportant(IWorkbenchPart part) {
		String id = part.getSite().getId();
		return ( JxseContainerNavigator.S_JXSE_NAVIGATOR_ID.equals( id ) || 
				( Jp2pContainerNavigator.S_JP2P_NAVIGATOR_ID.equals( id )) );
	}

	private class Selection extends TreeSelection{
	
		public Selection( TreeSelection treeSelection ) {
			super( convertPaths( treeSelection ));
		}
	}
	
	/**
	 * Convert the paths from IJp2pCpmonent
	 * @return
	 */
	static TreePath[] convertPaths( TreeSelection selection ) {
		TreePath[] tps = new TreePath[ selection.getPaths().length ];
		for( int i=0; i<selection.getPaths().length; i++  ){
			TreePath tp = selection.getPaths()[i];
			for( int j=0; j < tp.getSegmentCount(); j++ ){
				IJp2pComponent<?> comp = (IJp2pComponent<?>) tp.getSegment(j);
				if( comp.getModule() != null )
					tp.createChildPath(comp.getModule());
			}
		}
		return tps;
	}

}
