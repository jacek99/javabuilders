/**
 * 
 */
package org.javabuilders.swt.handler.type;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.BuilderUtils;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;
import org.javabuilders.swt.SwtJavaBuilder;
import org.javabuilders.swt.SwtBuilderUtils;

/**
 * Generic handler for instantiating all SWT Widgets/Controls
 * @author Jacek Furmankiewicz
 */
public class WidgetTypeHandler extends AbstractTypeHandler implements ITypeHandlerFinishProcessor {

	private static final WidgetTypeHandler singleton = new WidgetTypeHandler();
	@SuppressWarnings("unused")
	private final static Logger logger = Logger.getLogger(WidgetTypeHandler.class.getSimpleName());
	
	/**
	 * Returns singleton
	 * @return Singleton
	 */
	public static WidgetTypeHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	protected WidgetTypeHandler() {}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		Object instance = null;
		
		//parent Control can be in a parent Node or sent explicitly
		if ((parent != null && parent.getMainObject() != null) ||
				process.getBuildResult().getProperties().get(SwtJavaBuilder.PARENT) != null) {
			
			Class<?> type = BuilderUtils.getClassFromAlias(process, key, null);

			int style = SwtBuilderUtils.getSWTStyle(typeDefinition.get(SwtJavaBuilder.STYLE));
			try {
				
				instance = executeConstructor(parent, type, style, process);
				if (instance == null) {
					throw new Exception("No valid constructor found for " + key);
				}
				
				//handle widgets embedded in a Scrolled Composite
				if (parent != null && parent.getMainObject() instanceof ScrolledComposite &&
						instance instanceof Control) {
					ScrolledComposite sc = (ScrolledComposite) parent.getMainObject();
					sc.setContent((Control) instance);
				}

			} catch (BuildException e) {
				throw e;
		    } catch (Exception e) {
				throw new BuildException("Failed to create instance of type {0} for a parent of type {1} : {2} {3}",
						type.getSimpleName(), parent.getMainObject().getClass().getSimpleName(),
						e.getClass(), e.getMessage());
			}
		}
		return useExistingInstance(config, process, parent, key, typeDefinition, instance);
	}
	
	//attempts to find the proper parent/constructor/style combination to execute
	private Widget executeConstructor(Node parent, Class<?> type, int style, BuildProcess process) throws BuildException {
		Widget instance = null;
		
		//parent Control can be from parent node or specified explicitly
		//the explicit parent should be used only on the root (first object being created)
		Object explicitParent = process.getBuildResult().getProperties().get(SwtJavaBuilder.PARENT);
		
		Object parentControl = (explicitParent != null && parent == null) ? 
				explicitParent : parent.getMainObject();

		try {
			Constructor<?> simpleConstructor = null;
			Constructor<?> styleConstructor = null;
			
			for (Constructor<?> c : type.getConstructors()) {
				if (c.getParameterTypes().length == 1 && 
						c.getParameterTypes()[0].isAssignableFrom(parentControl.getClass())) {
					simpleConstructor = c;
				} else if (c.getParameterTypes().length == 2 && 
						c.getParameterTypes()[0].isAssignableFrom(parentControl.getClass()) &&
						c.getParameterTypes()[1].equals(int.class)) {
					styleConstructor = c;
				}
			}
			
			if (styleConstructor != null) {
				instance = (Widget) styleConstructor.newInstance(parentControl,style);
			} else if (simpleConstructor != null) {
				instance = (Widget) simpleConstructor.newInstance(parentControl);
			} else {
				//keep looking for a proper parent
				instance = executeConstructor(parent.getParent(),type,style, process);
			}

		} catch (BuildException e) {
			throw e;
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof BuildException) {
				throw (BuildException)e.getTargetException();
			} else {
				throw new BuildException("Failed to create instance of type {0} for a parent of type {1}: {2} {3} {4}",
						type.getSimpleName(), parent.getMainObject().getClass().getSimpleName(),
						e.getClass(), e.getMessage(), e.getTargetException());
			}
		} catch (Exception e) {
			throw new BuildException("Failed to create instance of type {0} for a parent of type {1}: {2} {3}",
					type.getSimpleName(), parent.getMainObject().getClass().getSimpleName(),
					e.getClass(), e.getMessage());
		}

		
		return instance;
	}


	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition,
			Object instance) throws BuildException {
		Node node = new Node(parent,key,typeDefinition);
		node.setMainObject(instance);
		return node;
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {

		if (current.getMainObject() instanceof Control) {
			((Control)current.getMainObject()).pack();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<Widget> getApplicableClass() {
		return Widget.class;
	}


}
