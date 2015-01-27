/**
 * 
 */
package org.javabuilders.swt.handler.property;

import org.eclipse.swt.widgets.Sash;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;

/**
 * Handler for Sash bounds
 * @author Jacek Furmankiewicz
 *
 */
public class SashBoundsHandler extends AbstractPropertyHandler {

	public static final String X = "x";
	public static final String Y = "y";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	
	private static final SashBoundsHandler singleton = new SashBoundsHandler();
	
	/**
	 * @return Singleton
	 */
	public static SashBoundsHandler getInstance() {return singleton;}
	
	/**
	 * Constructor
	 */
	private SashBoundsHandler() {
		super(X,Y,WIDTH,HEIGHT);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess process, Node node,
			String key) throws BuildException {

		Long x = node.getLongProperty(X);
		if (x == null) {
			x = 100L;
		}
		Long y = node.getLongProperty(Y);
		if (y == null) {
			y = 100L;
		}
		Long width = node.getLongProperty(WIDTH);
		if (width == null) {
			width = 400L;
		}
		Long height = node.getLongProperty(HEIGHT);
		if (height == null) {
			height = 800L;
		}
		Sash sash = (Sash) node.getMainObject();
		sash.setBounds(x.intValue(), y.intValue(), width.intValue(), height.intValue());
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Sash.class;
	}

}
