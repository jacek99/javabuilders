/**
 * 
 */
package org.javabuilders.swing.handler.binding;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.Node;
import org.javabuilders.handler.binding.AbstractBuilderBindingsHandler;
import org.javabuilders.handler.binding.BindingSourceDefinition;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.SwingBindings;

/**
 * Default handler for binding logic across all domains
 * 
 * @author Jacek Furmankiewicz
 */
public class BeansBindingTypeHandler extends AbstractBuilderBindingsHandler {

	/**
	 * Property name for model
	 */
	public static final String MODEL = "model";

	private final static Logger logger = Logger.getLogger(BeansBindingTypeHandler.class.getSimpleName());
	private static final BeansBindingTypeHandler singleton = new BeansBindingTypeHandler();

	private Map<String, UpdateStrategy> updateStrategies = new HashMap<String, UpdateStrategy>();

	/**
	 * @return Singleton
	 */
	public static BeansBindingTypeHandler getInstance() {
		return singleton;
	}

	/**
	 * Constructor
	 */
	private BeansBindingTypeHandler() {
		super();

		
		
		updateStrategies.put(READ, UpdateStrategy.READ);
		updateStrategies.put(READ_ONCE, UpdateStrategy.READ_ONCE);
		updateStrategies.put(READ_WRITE, UpdateStrategy.READ_WRITE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders
	 * .BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node,
	 * java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> typeDefinition, Object instance) throws BuildException {

		Node node = new Node(parent, key, typeDefinition);
		node.setMainObject(instance);
		BindingGroup bindingGroup = new BindingGroup();
		process.getBuildResult().setBindingContext(bindingGroup);

		Map<NamedObjectProperty, BindingSourceDefinition> defs = getBindingDefinitions(node, process);
		for (NamedObjectProperty targetProperty : defs.keySet()) {
			BindingSourceDefinition sourceDef = defs.get(targetProperty);

			UpdateStrategy strategy = updateStrategies.get(sourceDef.getUpdateStrategy());

			Object source = sourceDef.getSource();
			String sourceExpression = sourceDef.getBindingExpression();
			Object target = process.getByName(targetProperty.getName());
			String targetExpression = targetProperty.getPropertyExpression();

			Binding<Object, Object, Object, Object> binding = null;

			if (MODEL.equals(targetExpression)) {

				// request to bind to a mode - need to use special Swing binding
				// extension
				binding = createSwingBinding(source, sourceExpression, target, targetExpression, strategy, process);

			} else {

				// bind to regular JavaBean properties
				binding = createELPropertyBinding(source, sourceExpression, target, targetExpression, strategy, process);

			}
			if (binding != null) {
				bindingGroup.addBinding(binding);
			}
		}

		return node;
	}

	// create EL binding
	private Binding<Object, Object, Object, Object> createELPropertyBinding(Object sourceObject, String sourcePropertyPath,
			Object targetObject, String targetPropertyName, UpdateStrategy strategy, BuildProcess process) {

		ELProperty<Object, Object> sourceProperty = getELProperty(sourcePropertyPath);
		ELProperty<Object, Object> targetProperty = getELProperty(targetPropertyName);

		Binding<Object, Object, Object, Object> binding = Bindings.createAutoBinding(strategy, sourceObject, sourceProperty,
				targetObject, targetProperty);
		binding.bind();

		if (logger.isLoggable(Level.FINE)) {
			logger.fine(String.format("Bound [%s].%s to [%s].%s] with update strategy %s", sourceObject.getClass().getSimpleName(),
					sourceProperty, targetObject.getClass().getSimpleName(), targetProperty, strategy));
		}

		return binding;
	}

	// create Swing binding
	@SuppressWarnings("unchecked")
	private Binding<Object, Object, Object, Object> createSwingBinding(Object sourceObject, String sourcePropertyPath,
			Object targetObject, String targetPropertyName, UpdateStrategy strategy, BuildProcess result) {

		Binding binding = null;

		if (targetObject instanceof JList) {

			JList list = (JList) targetObject;
			ELProperty listProperty = getELProperty(sourcePropertyPath);
			binding = SwingBindings.createJListBinding(strategy, sourceObject, listProperty, list);
			binding.bind();

		} else if (targetObject instanceof JComboBox) {

			JComboBox box = (JComboBox) targetObject;
			ELProperty listProperty = getELProperty(sourcePropertyPath);

			binding = SwingBindings.createJComboBoxBinding(strategy, sourceObject, listProperty, box);
			binding.bind();

		} else if (targetObject instanceof JTable) {

			//the complex one- needs column specific info
			return createJTableBinding(sourceObject, sourcePropertyPath, (JTable) targetObject, targetPropertyName, strategy, result);
			
		}

		return binding;
	}

	// create Swing binding
	private Binding<Object, Object, Object, Object> createJTableBinding(Object sourceObject, String sourcePropertyPath,
			JTable targetObject, String targetPropertyName, UpdateStrategy strategy, BuildProcess result) {

		return null;
	}
	
	

	// standard logic for a property
	private ELProperty<Object, Object> getELProperty(String path) {
		ELProperty<Object, Object> property = path.indexOf("${") >= 0 ? ELProperty.create(path) : ELProperty.create(String.format(
				"${%s}", path));
		return property;
	}
	
	

}
