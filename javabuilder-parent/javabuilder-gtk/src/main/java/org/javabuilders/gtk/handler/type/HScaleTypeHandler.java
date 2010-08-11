/**
 * 
 */
package org.javabuilders.gtk.handler.type;

import java.util.Map;

import org.gnome.gtk.HScale;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;

/**
 * @author Jacek Furmankiewicz
 *
 */
public class HScaleTypeHandler extends AbstractTypeHandler {

	public HScaleTypeHandler() {
		super("min","max","step");
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> typeDefinition) throws BuildException {
		
		Integer min = (Integer) typeDefinition.get("min");
		Integer max = (Integer) typeDefinition.get("max");
		Integer step = (Integer) typeDefinition.get("step");
		
		HScale instance = new HScale(min,max,step);
		return useExistingInstance(config, process, parent, key, typeDefinition, instance);
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> typeDefinition, Object instance) throws BuildException {
		
		Node node = new Node(parent,key,typeDefinition,instance);
		return node;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<HScale> getApplicableClass() {
		return HScale.class;
	}

}
