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
 * Handles min numeric values
 * @author Jacek Furmankiewicz
 *
 */
public class MinValueValidator  extends AbstractValidator {

	private Object minValue = null;
	private Long minValueLong = null;
	private Double minValueDouble = null;
	
	/**
	 * Constructor
	 * @param property
	 * @param label
	 * @param messageFormat
	 * @param result
	 * @param minValue
	 */
	public MinValueValidator(NamedObjectProperty property, String label,
			String messageFormat, BuildResult result, Object minValue) {
		super(property, label, messageFormat, result);
		this.minValue = minValue;

		try {
			minValueDouble = Double.parseDouble(minValue.toString());
		} catch (Exception ex) {}
		
		try {
			if (minValueDouble == null) {
				minValueLong = Long.parseLong(minValue.toString());
			}
		} catch (Exception ex) {}
		
		if (minValueDouble == null && minValueLong == null) {
			throw new BuildException("{0} is not a valid Long or Double comparison value",minValue);
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.validation.IPropertyValidator#validate(java.lang.Object, org.javabuilders.handler.validation.ValidationMessageList)
	 */
	public void validate(Object value, ValidationMessageList list) {
	
		String sValue = String.valueOf(value);
		
		if (minValueDouble != null) {
			try {
				Double dbl = Double.parseDouble(sValue);
				if (dbl < minValueDouble) {
					list.add(new ValidationMessage(getProperty(),
							getMessage(getLabel(),minValue)));
				}
			} catch (NumberFormatException ex) {
				list.add(new ValidationMessage(getProperty(),
						getMessageForFormat(BuilderValidators.getDefaultNumericMessage(),getLabel())));
			}
		} else if (minValueLong != null) {
			try {
				Long lng = Long.parseLong(sValue);
				if (lng < minValueLong) {
					list.add(new ValidationMessage(getProperty(),
							getMessage(getLabel(),minValue)));
				}
			} catch (NumberFormatException ex) {
				list.add(new ValidationMessage(getProperty(),
						getMessageForFormat(BuilderValidators.getDefaultNumericMessage(),getLabel())));
			}
		}
	}
}
