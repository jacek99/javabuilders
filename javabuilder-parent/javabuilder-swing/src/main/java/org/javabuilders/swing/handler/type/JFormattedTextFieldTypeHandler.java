package org.javabuilders.swing.handler.type;

import java.text.Format;
import java.util.Map;

import javax.swing.JFormattedTextField;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;

/**
 * Handles created JFormattedText
 * @author Jacek Furmankiewicz
 */
public class JFormattedTextFieldTypeHandler extends AbstractTypeHandler {

	public static final String FORMAT = "format";
	private static final JFormattedTextFieldTypeHandler singleton = new JFormattedTextFieldTypeHandler();
	
	/**
	 * @return Singleton
	 */
	public static JFormattedTextFieldTypeHandler getInstance() {return singleton;}
	
	/**
	 * Constructor
	 */
	private JFormattedTextFieldTypeHandler() {
		super(FORMAT);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		JFormattedTextField instance = null;
		
		String formatName = (String)typeDefinition.get(FORMAT);
		if (formatName != null) {
			//formats should be global variables to avoid repetition
			if (formatName.matches(BuilderConfig.GLOBAL_VARIABLE_REGEX)) {
				Format format = (Format) config.getGlobalVariable(formatName, Format.class);
				instance = new JFormattedTextField(format);
			} else {
				throw new BuildException("{0} is not a valid global format name.",formatName);
			}
		} else {
			//no format specified
			instance = new JFormattedTextField();
		}
		return useExistingInstance(config, process, parent, key, typeDefinition, instance);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition,
			Object instance) throws BuildException {
		
		Node node = new Node(parent, key, typeDefinition);
		node.setMainObject(instance);
		
		return node;
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<JFormattedTextField> getApplicableClass() {
		return JFormattedTextField.class;
	}

}
