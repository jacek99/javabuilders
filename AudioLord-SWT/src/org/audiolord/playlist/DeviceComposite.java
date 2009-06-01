/**
 * 
 */
package org.audiolord.playlist;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.javabuilders.swt.SwtJavaBuilder;

/**
 * Library playlist composite
 * @author Jacek Furmankiewicz
 *
 */
public class DeviceComposite extends Composite {

	/**
	 * @param parent
	 */
	public DeviceComposite(Composite parent) {
		super(parent, SWT.NONE);
		SwtJavaBuilder.build(this);
	}

}
