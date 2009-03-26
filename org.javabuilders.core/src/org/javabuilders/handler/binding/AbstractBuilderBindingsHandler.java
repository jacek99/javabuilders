package org.javabuilders.handler.binding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.BuilderUtils;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.handler.ITypeChildrenHandler;

/**
 * Abstract ancestor for binding support implementations
 * @author Jacek Furmankiewicz
 *
 */
public abstract class AbstractBuilderBindingsHandler extends
		AbstractTypeHandler implements ITypeChildrenHandler {

	public final static String READ = "read";
	public final static String READ_ONCE = "readOnce";
	public final static String READ_WRITE = "readWrite";
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess result,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		BuilderBindings instance = new BuilderBindings();
		return useExistingInstance(config, result, parent, key, typeDefinition, instance);
	}
	
	/**
	 * Parses the binding definitions to return them in a format that can easily processed
	 * by domain-specific implementations
	 * @param node Current node
	 * @param process Current build process
	 * @return List of target/source mappings
	 */
	@SuppressWarnings("unchecked")
	protected Map<NamedObjectProperty,BindingSourceDefinition> getBindingDefinitions(Node node, BuildProcess process) {
		Map<NamedObjectProperty,BindingSourceDefinition> defs = new LinkedHashMap<NamedObjectProperty, BindingSourceDefinition>();
		
		//process each definition
		List<Object> content = (List<Object>)node.getProperties().get(Builder.CONTENT);
		for(Object def : content) {
			if (def instanceof Map) {
				Map<String,Object> map = (Map<String,Object>)def;
				for(String target : map.keySet()) {
					//the expression can be a straight string or a List with two items
					Object expression = map.get(target);
					List<String> fullExpression = null;
					
					if (expression instanceof String) {
						//re-create it as if it were a List
						fullExpression = new ArrayList<String>();
						fullExpression.add((String)expression);
						fullExpression.add(READ_WRITE); //the default binding update strategy
					} else if (expression instanceof List) {
						
						fullExpression = (List<String>)expression;
						if (fullExpression.size() < 1 || fullExpression.size() > 2) {
							throw new BuildException("Binding definition expression in invalid format: " + expression + ". Must be a list with two elements, first the expression, second the update strategy");
						}
						
					} else {
						throw new BuildException("Binding definition expression in invalid format: " + expression);
					}
					String sourcePathText = fullExpression.get(0);

					//check if source paths are valid
					List<NamedObjectProperty> sources = BuilderUtils.getParsedPropertyExpression(sourcePathText);
					Set<String> uniqueSourceNames = new HashSet<String>();
					Object sourceObject = null;
					String sourceObjectName = null;
					for(NamedObjectProperty source : sources) {
						sourceObjectName = source.getName();
						sourceObject = process.getByName(sourceObjectName);
						if (sourceObject == null) {
							throw new BuildException("Binding source path expression does not refer to a known named object: "  + source);
						} else {
							uniqueSourceNames.add(sourceObjectName);
						}
					}
					//source path can only refer to properties from one object, if there are multiple ones
					String nameFormat = "%s.";
					if (uniqueSourceNames.size() == 1) {
						sourcePathText = sourcePathText.replace(String.format(nameFormat,sourceObjectName),"");
					} else {
						throw new BuildException("Binding source path expression cannot refer to properties of more than 1 unique named object: " +
								"{0}.\nRefers to multiple objects: {1}", sourcePathText, uniqueSourceNames);
					}
					
					//parse target expression
					List<NamedObjectProperty> targets = BuilderUtils.getParsedPropertyExpression(target);
					NamedObjectProperty targetProperty = null;
					Object targetObject = null;
					if (targets.size() == 1) {
						targetProperty = targets.get(0);
						targetObject = process.getByName(targetProperty.getName());
						if (targetObject == null) {
							throw new BuildException("Binding target propertyName does not refer to a known named object: "  + targetProperty);
						} 
					} else {
						throw new BuildException("Unable to parse target binding expresson. It must be in single 'objectName.propertyName' format. Invalid format: " + target);
					}
					
					//create the binding source definition
					BindingSourceDefinition sourceDef = new BindingSourceDefinition(sourceObject,
							sourcePathText, fullExpression.get(1));
					
					defs.put(targetProperty, sourceDef);
					
				}
			} else {
				throw new BuildException("Binding definition in invalid format: " + def);
			}
		}
		
		return defs;
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public final Class<?> getApplicableClass() {
		return BuilderBindings.class;
	}
	
	

}
