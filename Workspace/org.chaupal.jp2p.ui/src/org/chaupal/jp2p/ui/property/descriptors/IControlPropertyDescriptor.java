package org.chaupal.jp2p.ui.property.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public interface IControlPropertyDescriptor<T extends Object> extends IPropertyDescriptor {

	public abstract CellEditor createPropertyEditor(Composite parent);

	public abstract boolean isEnabled();

	public abstract void setEnabled(boolean enabled);

}