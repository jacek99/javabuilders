package org.javabuilders.handler.validation;

import java.util.LinkedList;

/**
 * List of validation messages
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class ValidationMessageList extends LinkedList<ValidationMessage> {

	/* (non-Javadoc)
	 * @see java.util.LinkedList#add(java.lang.Object)
	 */
	@Override
	public boolean add(ValidationMessage o) {
		
		boolean duplicate = false;
		
		//add only if not a duplicate
		for(ValidationMessage msg : this) {
			if (msg.getProperty() != null) {
				if (msg.getProperty().equals(o.getProperty()) && msg.getMessage().equals(o.getMessage())) {
					duplicate = true;
					break;
				}
			}
		}
		
		if (duplicate) {
			return false;
		} else {
			return super.add(o);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(ValidationMessage msg : this) {
			builder.append(msg.getMessage()).append("\n");
		}
		return builder.toString();
	}
	
}
