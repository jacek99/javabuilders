/**
 * 
 */
package org.javabuilders.swing.handler.type.layout;

import java.awt.Component;
import java.awt.Container;
import java.util.Map;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.layout.mig.AbstractMigLayoutHandler;
import org.javabuilders.swing.SwingJavaBuilderUtils;



/**
 * Mig Layout handler
 * @author Jacek Furmankiewcz
 *
 */
public class MigLayoutHandler extends AbstractMigLayoutHandler {

	private final static MigLayoutHandler singleton = new MigLayoutHandler();
	
	/**
	 * @return Singleton
	 */
	public static MigLayoutHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	private MigLayoutHandler() {
		super(JLabel.class,"text");
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
	public Class<?> getApplicableClass() {
		return MigLayout.class;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.layout.mig.AbstractMigLayoutHandler#applyControlConstraints(org.javabuilders.BuildResult, org.javabuilders.Node, org.javabuilders.Node, java.util.Map)
	 */
	@Override
	protected void applyControlConstraints(BuildProcess process, Node node, Node components, Map<String, String> layoutConstraints) throws BuildException {
		Container parentContainer = (Container)node.getParent().getParent().getMainObject();
		for(String componentName : layoutConstraints.keySet()) {
			String componentConstraint = layoutConstraints.get(componentName);
			Component component = SwingJavaBuilderUtils.getComponent(components,String.valueOf(componentName));

			if (logger.isDebugEnabled()) {
				logger.debug("MigLayout constraints for " + componentName + " : " + componentConstraint);
			}
			parentContainer.add(component,componentConstraint);
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.layout.mig.AbstractMigLayoutHandler#getComponent(org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	@Override
	protected Object getComponent(BuildProcess result, Node components,
			String name) throws BuildException {
		Component component = SwingJavaBuilderUtils.getComponent(components,String.valueOf(name));
		return component;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.layout.mig.AbstractMigLayoutHandler#setLayout(org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.Object)
	 */
	@Override
	protected void setLayout(BuildProcess result, Node node, Object migLayout) {
		Node parent = node.getParent().getParent();
		Container parentContainer = (Container)parent.getMainObject();
		MigLayout layout = (MigLayout)node.getMainObject();
		parentContainer.setLayout(layout);
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

	/* (non-Javadoc)
	 * @see org.javabuilders.layout.mig.AbstractMigLayoutHandler#setControlName(java.lang.Object, java.lang.String)
	 */
	@Override
	protected void setControlName(Object control, String name) {
		((Component)control).setName(name);
	}

}
