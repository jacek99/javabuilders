/**
 * 
 */
package org.javabuilders.swt.handler;

import org.javabuilders.BuildResult;
import org.javabuilders.handler.validation.IValidationMessageHandler;
import org.javabuilders.handler.validation.ValidationMessageList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * SWT Validation message handler
 * @author Jacek Furmankiewicz
 *
 */
public class DefaultValidationMessageHandler implements
		IValidationMessageHandler {

	private final static DefaultValidationMessageHandler singleton = new DefaultValidationMessageHandler();
	
	/**
	 * @return Singleton
	 */
	public static DefaultValidationMessageHandler getInstance() {return singleton;}
	
	/**
	 * Private constructor
	 */
	private DefaultValidationMessageHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.validation.IValidationMessageHandler#getNamedObjectLabel(java.lang.Object)
	 */
	public String getNamedObjectLabel(Object namedObject) {
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.validation.IValidationMessageHandler#handleValidationMessages(org.javabuilders.handler.validation.ValidationMessageList, org.javabuilders.BuildResult)
	 */
	public void handleValidationMessages(ValidationMessageList list,
			BuildResult result) {
		throw new NotImplementedException();
	}

}
