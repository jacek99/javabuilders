/**
 * 
 */
package org.javabuilders.swt.handler.type.layout;

import java.util.Map;
import java.util.logging.Level;

import net.miginfocom.swt.MigLayout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.layout.mig.AbstractMigLayoutHandler;

/**
 * Handled for SWT MigLayout
 * @author Jacek Furmankiewicz
 */
public class MigSWTLayoutHandler extends AbstractMigLayoutHandler {

	private final static MigSWTLayoutHandler singleton = new MigSWTLayoutHandler();
	
	/**
	 * @return Singleton
	 */
	public static MigSWTLayoutHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	private MigSWTLayoutHandler() {
		super(Label.class,"text");
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess result,
			Node parent, String key, Map<String, Object> typeDefinition) throws BuildException {
		
		MigLayout instance = new MigLayout(); 
		return useExistingInstance(config, result, parent, key, typeDefinition, instance);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<MigLayout> getApplicableClass() {
		return MigLayout.class;
	}


	/* (non-Javadoc)
	 * @see org.javabuilders.layout.mig.AbstractMigLayoutHandler#setLayoutConstraints(java.lang.Object, java.lang.String)
	 */
	@Override
	protected void setLayoutConstraints(Object layout, String constraints) {
		((MigLayout)layout).setLayoutConstraints(constraints);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.layout.mig.AbstractMigLayoutHandler#setRowConstraints(java.lang.Object, java.lang.String)
	 */
	@Override
	protected void setRowConstraints(Object layout, String constraints) {
		((MigLayout)layout).setRowConstraints(constraints);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.layout.mig.AbstractMigLayoutHandler#setColumnConstraints(java.lang.Object, java.lang.String)
	 */
	@Override
	protected void setColumnConstraints(Object layout, String constraints) {
		((MigLayout)layout).setColumnConstraints(constraints);
	}

	@Override
	protected void applyControlConstraints(BuildProcess result, Node node,
			Node components, Map<String, String> layoutConstraints)
			throws BuildException {
		
		for(String componentName : layoutConstraints.keySet()) {
			String componentConstraint = layoutConstraints.get(componentName);
			Control component = (Control)result.getByName(componentName);
			component.setLayoutData(componentConstraint);
			
			if (logger.isLoggable(Level.FINE)) {
				logger.fine("MigLayout constraints for " + componentName + " : " + componentConstraint);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.layout.mig.AbstractMigLayoutHandler#getComponent(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	@Override
	protected Object getComponent(BuildProcess result, Node components,
			String name) throws BuildException {
		return result.getByName(name);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.layout.mig.AbstractMigLayoutHandler#setLayout(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.Object)
	 */
	@Override
	protected void setLayout(BuildProcess result, Node node, Object migLayout)
			throws BuildException {
		Node parent = node.getParent();
		if (parent.getMainObject() instanceof Composite) {
			Composite parentContainer = (Composite)parent.getMainObject();
			MigLayout layout = (MigLayout)node.getMainObject();
			parentContainer.setLayout(layout);
		} else {
			throw new BuildException("MigLayout can only be present under a parent Composite: {0}", node);
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.layout.mig.AbstractMigLayoutHandler#setControlName(java.lang.Object, java.lang.String)
	 */
	@Override
	protected void setControlName(Object control, String name) {
		//do nothing for SWT -> they do not have a name property
	}
}
