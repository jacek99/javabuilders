package org.javabuilders.swing.samples;

import org.javabuilders.BuildResult;
import org.javabuilders.handler.validation.ICustomValidator;
import org.javabuilders.handler.validation.ValidationMessage;
import org.javabuilders.handler.validation.ValidationMessageList;
import org.javabuilders.swing.SwingJavaBuilder;

/**
 * Validator samples
 * @author Jacek Furmankiewicz
 *
 */
@SuppressWarnings("serial")
public class ValidatorsPanel extends SamplePanel {

	@SuppressWarnings("unused")
	private BuildResult result = SwingJavaBuilder.build(this);
	
	public ValidatorsPanel() throws Exception {
		super();
		
		result.getValidators().add(new ICustomValidator() {
			public void validate(ValidationMessageList list) {
				if (list.size() > 0) {
					list.add(new ValidationMessage("If other errors exist, a new custom validator message is added as well!"));
				}
			}
		});
	}

}
