/**
 * 
 */
package org.javabuilders.handler.validation;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.handler.ITypeChildrenHandler;
import org.javabuilders.util.BuilderUtils;

/**
 * Handler for "validate:" - integrates with Commons Validators
 * @author Jacek Furmankiewicz
  */
public class DefaultValidatorTypeHandler extends AbstractTypeHandler implements ITypeChildrenHandler {

	private static final DefaultValidatorTypeHandler singleton = new DefaultValidatorTypeHandler();
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(DefaultValidatorTypeHandler.class.getSimpleName());
	
	/**
	 * @return Singleton
	 */
	public static DefaultValidatorTypeHandler getInstance() {return singleton;}
	
	/**
	 * Constructor
	 */
	private DefaultValidatorTypeHandler() {
		super(Builder.CONTENT);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		BuilderValidators instance = process.getBuildResult().getValidators();
		return useExistingInstance(config, process, parent, key, typeDefinition, instance);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public Node useExistingInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition,
			Object instance) throws BuildException {
		
		Node node = new Node(parent, key, typeDefinition);
		node.setMainObject(instance);
		
		List<Object> contents = (List<Object>)typeDefinition.get(Builder.CONTENT);
		for(Object validatorData : contents) {
			if (validatorData instanceof Map)  {
				Map<String,Object> data = (Map<String,Object>)validatorData;
				for(String dataKey : data.keySet()) {
					
					NamedObjectProperty property = BuilderUtils.getParsedProperty(dataKey);
					PropertyValidations validator = new PropertyValidations(property);
					Map<String,Object> validatorProperties = (Map<String,Object>)data.get(dataKey);
					
					BuilderUtils.populateObjectPropertiesFromMap(validator, validatorProperties);
					
					process.getBuildResult().getValidators().add(validator);
				}
				
			} else {
				throw new BuildException("Unable to parse validator data: " + validatorData);
			}
		}
		
		return node;
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return BuilderValidators.class;
	}
}
