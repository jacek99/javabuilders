/**
 * 
 */
package org.javabuilders.handler.validation.validator;


import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.LongValidator;
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
			
			if (DoubleValidator.getInstance().isValid(String.valueOf(value))) {
				
				double dValue = Double.parseDouble(sValue);
				
				if (!DoubleValidator.getInstance().isInRange(dValue, minValueDouble.doubleValue(), Double.MAX_VALUE)) {
					list.add(new ValidationMessage(getProperty(),
							getMessage(getLabel(),minValue)));
				}
				
			} else {
				//not a valid double
				list.add(new ValidationMessage(getProperty(),
						getMessageForFormat(BuilderValidators.getDefaultNumericMessage(),getLabel())));
			}
			
			
		} else if (minValueLong != null) {

			if (LongValidator.getInstance().isValid(String.valueOf(value))) {
				
				long lValue = Long.parseLong(sValue);
				
				if (!LongValidator.getInstance().isInRange(lValue, minValueLong.longValue(), Long.MAX_VALUE)) {
					list.add(new ValidationMessage(getProperty(),
							getMessage(getLabel(),minValue)));
				}
				
			} else {
				//not a valid long
				list.add(new ValidationMessage(getProperty(),
						getMessageForFormat(BuilderValidators.getDefaultNumericMessage(),getLabel())));
			}

			
		}
		
	}

}
