/**
 * 
 */
package org.javabuilders.swing.handler.type;

import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles creation button groups
 * @author Jacek Furmankiewicz
 */
public class ButtonGroupTypeHandler implements ITypeHandlerFinishProcessor {

	private final static ButtonGroupTypeHandler singleton = new ButtonGroupTypeHandler();
	private final static Logger logger = LoggerFactory.getLogger(ButtonGroupTypeHandler.class);
	
	/**
	 * @return Singleton
	 */
	public static ButtonGroupTypeHandler getInstance() {return singleton;}
	
	/**
	 * Constructor
	 */
	private ButtonGroupTypeHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerPostProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {

		try {
			ButtonGroup group = (ButtonGroup)current.getMainObject();
	
			//add all the buttons in the list to the button group
			Object content = current.getProperty(Builder.CONTENT);
			if (content instanceof List) {
				List<String> buttons = (List<String>) content;
				for(String buttonName : buttons) {
					Object button = process.getByName(buttonName);
					if (button != null) {
						if (button instanceof AbstractButton) {
							AbstractButton btn = (AbstractButton) button;
							group.add(btn);
						} else {
							throw new BuildException("ButtonGroup: ''{0}'' is not an instance of AbstractButton",buttonName);
						}
					} else {
						throw new BuildException("ButtonGroup: ''{0}'' is not a valid named object",buttonName);
					}
				}
			} else {
				throw new BuildException("ButtonGroup:content should be a list instead of: {0}",content);
			}
		} catch (BuildException e) {
			logger.error(e.getMessage(),e);
			throw e;
		}

	}

	
}
