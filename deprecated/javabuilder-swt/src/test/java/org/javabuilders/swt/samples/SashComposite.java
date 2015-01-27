/**
 * 
 */
package org.javabuilders.swt.samples;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.javabuilders.BuildResult;
import org.javabuilders.swt.SwtJavaBuilder;

/**
 * 
 * @author Jacek Furmankiewicz
 *
 */
public class SashComposite extends SashForm {

	@SuppressWarnings("unused")
	private BuildResult result = SwtJavaBuilder.build(this);
	/**
	 * @param parent
	 * @param style
	 */
	public SashComposite(Composite parent, int style) throws Exception {
		super(parent, style);
		
	}
}
