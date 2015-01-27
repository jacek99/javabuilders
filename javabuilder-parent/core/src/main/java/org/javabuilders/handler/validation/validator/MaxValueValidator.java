/**
 * 
 */
package org.javabuilders.handler.validation.validator;

import org.javabuilders.BuildException;
import org.javabuilders.BuildResult;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.handler.validation.BuilderValidators;
import org.javabuilders.handler.validation.ValidationMessage;
import org.javabuilders.handler.validation.ValidationMessageList;

/**
 * Max value validator
 * @author Jacek Furmankiewicz
 *
 */
public class MaxValueValidator extends AbstractValidator {

	private Object maxValue = null;
	private Long maxValueLong = null;
	private Double maxValueDouble = null;
	
	/**
	 * Constructor
	 * @param property
	 * @param label
	 * @param messageFormat
	 * @param result
	 * @param maxValue
	 */
	public MaxValueValidator(NamedObjectProperty property, String label,
			String messageFormat, BuildResult result, Object maxValue) {
		super(property, label, messageFormat, result);
		this.maxValue = maxValue;
		
		try {
			maxValueDouble = Double.parseDouble(maxValue.toString());
		} catch (Exception ex) {}
		
		try {
			if (maxValueDouble == null) {
				maxValueLong = Long.parseLong(maxValue.toString());
			}
		} catch (Exception ex) {}
		
		if (maxValueDouble == null && maxValueLong == null) {
			throw new BuildException("{0} is not a valid Long or Double comparison value",maxValue);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.validation.IPropertyValidator#validate(java.lang.Object, org.javabuilders.handler.validation.ValidationMessageList)
	 */
	public void validate(Object value, ValidationMessageList list) {

		String sValue = String.valueOf(value);
		
		if (maxValueDouble != null) {
			try {
				Double dbl = Double.parseDouble(sValue);
				if (dbl > maxValueDouble) {
					list.add(new ValidationMessage(getProperty(),
							getMessage(getLabel(),maxValue)));
				}
			} catch (NumberFormatException ex) {
				list.add(new ValidationMessage(getProperty(),
						getMessageForFormat(BuilderValidators.getDefaultNumericMessage(),getLabel())));
			}
			
		} else if (maxValueLong != null) {
			try {
				Long lng = Long.parseLong(sValue);
				if (lng > maxValueLong) {
					list.add(new ValidationMessage(getProperty(),
							getMessage(getLabel(),maxValue)));
				}
			} catch (NumberFormatException ex) {
				list.add(new ValidationMessage(getProperty(),
						getMessageForFormat(BuilderValidators.getDefaultNumericMessage(),getLabel())));
			}
		}
	}
}
