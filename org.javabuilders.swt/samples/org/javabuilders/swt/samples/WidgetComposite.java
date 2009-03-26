/**
 * 
 */
package org.javabuilders.swt.samples;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.javabuilders.BuildResult;
import org.javabuilders.swt.SWTBuilder;

/**
 * Widgets Composite
 * @author Jacek Furmankiewicz
 *
 */
public class WidgetComposite extends Composite {

	private Text text1;
	private Text text3;
	
	private BuildResult result = SWTBuilder.build(this);
	
	/**
	 * @param parent
	 * @param style
	 */
	public WidgetComposite(Composite parent, int style) throws Exception {
		super(parent, style);
	}

}
