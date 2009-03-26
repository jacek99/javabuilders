/**
 * 
 */
package org.javabuilders.swing.handler.type;

import javax.swing.Action;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;

/**
 * Handler for Action references
 * @author Jacek Furmankiewicz
 *
 */
public class ActionAsValueHandler implements ITypeAsValueHandler<Action> {

	private static final ActionAsValueHandler singleton = new ActionAsValueHandler();
	
	/**
	 * @return Singleton
	 */
	public static ActionAsValueHandler getInstance() {return singleton;}
	
	private ActionAsValueHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getInputValueSample()
	 */
	public String getInputValueSample() {
		return "saveAction";
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
	 */
	public String getRegex() {
		return ".+";
	}

	public Action getValue(BuildProcess process, Node node, String key,
			Object inputValue) throws BuildException {
		
		Action action = null;
		String name = node.getStringProperty(key);
		//may be internationalized
		name = process.getBuildResult().getResource(name);
		Object value = process.getByName(name);
		if (value == null) {
			throw new BuildException("No Action found for name {0}. Values named objects are: {1}",name, process.getBuildResult().toString());
		} else if (value instanceof Action) {
			action = (Action) value;
		} else {
			throw new BuildException("Object identified by {0} is not an Action, but a {1}.",name, value.getClass());
		}
		
		return action;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<Action> getApplicableClass() {
		return Action.class;
	}

}
