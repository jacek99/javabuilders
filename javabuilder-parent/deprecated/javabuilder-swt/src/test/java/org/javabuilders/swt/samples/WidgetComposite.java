/**
 * 
 */
package org.javabuilders.swt.samples;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.javabuilders.BuildResult;
import org.javabuilders.swt.SwtJavaBuilder;

/**
 * Widgets Composite
 * @author Jacek Furmankiewicz
 *
 */
public class WidgetComposite extends SamplesComposite {

	private Text text1;
	private Text text3;
	
	private BuildResult result = SwtJavaBuilder.build(this);
	
	/**
	 * @param parent
	 * @param style
	 */
	public WidgetComposite(Composite parent, int style) throws Exception {
		super(parent, style);
	}

}
