package org.javabuilders.handler.validation;

import java.util.LinkedList;
import java.util.List;

import org.javabuilders.BuildException;
import org.javabuilders.BuildResult;
import org.javabuilders.handler.validation.validator.DateFormatValidator;
import org.javabuilders.handler.validation.validator.EmailAddressValidator;
import org.javabuilders.handler.validation.validator.MandatoryValidator;
import org.javabuilders.handler.validation.validator.MaxLengthValidator;
import org.javabuilders.handler.validation.validator.MaxValueValidator;
import org.javabuilders.handler.validation.validator.MinLengthValidator;
import org.javabuilders.handler.validation.validator.MinValueValidator;
import org.javabuilders.handler.validation.validator.RegexValidator;
import org.javabuilders.handler.validation.validator.TypeValidator;
import org.javabuilders.util.PropertyUtils;



/**
 * A collection of validators for a build file
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class BuilderValidators extends LinkedList<PropertyValidations> {
	
	private BuildResult result;
	
	private static String defaultMandatoryMessage = "message.error.mandatory";
	private static String defaultMinLengthMessage = "message.error.minLength";
	private static String defaultMaxLengthMessage = "message.error.maxLength";
	private static String defaultDateFormatMessage = "message.error.dateFormat";
	private static String defaultMinValueMessage = "message.error.minValue";	
	private static String defaultMaxValueMessage = "message.error.maxValue";
	private static String defaultNumericMessage = "message.error.numeric";
	private static String defaultEmailAddressMessage = "message.error.emailAddress";
	private static String defaultRegexMessage = "message.error.regex";
	
	private String mandatoryMessage = defaultMandatoryMessage;
	private String minLengthMessage = defaultMinLengthMessage;
	private String maxLengthMessage = defaultMaxLengthMessage;
	private String dateFormatMessage = defaultDateFormatMessage;
	private String minValueMessage = defaultMinValueMessage;
	private String maxValueMessage = defaultMaxValueMessage;
	private String numericMessage = defaultNumericMessage;
	private String emailAddressMessage = defaultEmailAddressMessage;
	private String regexMessage = defaultRegexMessage;
	
	private List<IValidator> validators = new LinkedList<IValidator>();
	
	private boolean validatorsCreated = false;
	
	/**
	 * @param result Current build result
	 */
	public BuilderValidators(BuildResult result) {
		this.result = result;
	}
	
	/**
	 * Validates the properties as per the property validators
	 * @param validationMessageHandler Current validation message handler
	 */
	public ValidationMessageList getValidationMessages(IValidationMessageHandler validationMessageHandler) {
		ValidationMessageList list = new ValidationMessageList();
		
		createValidators(validationMessageHandler);
		
		for(PropertyValidations validator : this) {
			
			Object namedObject = result.get(validator.getProperty().getName());
			Object value;
			boolean isEmptyValue = false;
			try {
				value = PropertyUtils.getNestedProperty(namedObject, validator.getProperty().getPropertyExpression());
				if (value == null || String.valueOf(value).trim().length() == 0) {
					isEmptyValue = true;
				}
			} catch (Exception e) {
				throw new BuildException("Error while processing validator for \"{0}\": {1}",
						namedObject, e);
			} 

			//validate standard validators first
			boolean isMandatory = (getValidator(validator.getProperty().getName(),
					validator.getProperty().getPropertyExpression(),MandatoryValidator.class) != null);
			
			//do not run the validatons if value is empty and mandatory validation is not activated
			if (isMandatory || !isEmptyValue) {
				for(IValidator routine : getValidators()) {
					if (routine instanceof IPropertyValidator) {
						IPropertyValidator pRoutine = (IPropertyValidator) routine;
						if (pRoutine.getProperty().equals(validator.getProperty())) {
							pRoutine.validate(value, list);
						}
					} 
				}
			}
		}
		
		//execute the custom validator routines last
		for(IValidator routine : getValidators()) {
			if (routine instanceof ICustomValidator) {
				((ICustomValidator)routine).validate(list);
			} 
		}
		
		return list;
	}

	/**
	 * @return the default Mandatory Message
	 */
	public static String getDefaultMandatoryMessage() {
		return defaultMandatoryMessage;
	}

	/**
	 * @param defaultMandatoryMessage the default Mandatory Message to set
	 */
	public static void setDefaultMandatoryMessage(String defaultMandatoryMessage) {
		BuilderValidators.defaultMandatoryMessage = defaultMandatoryMessage;
	}

	/**
	 * @return the mandatory Message
	 */
	public String getMandatoryMessage() {
		return mandatoryMessage;
	}

	/**
	 * @param mandatoryMessage the mandatory message to set (must have a {0} parameter for the field label)
	 */
	public BuilderValidators setMandatoryMessage(String mandatoryMessage) {
		this.mandatoryMessage = mandatoryMessage;
		return this;
	}

	/**
	 * @return the minLength message
	 */
	public String getMinLengthMessage() {
		return minLengthMessage;
	}

	/**
	 * @param minLengthMessage the minLength message to set (must have {0} for field label and {1} for min length value)
	 */
	public BuilderValidators setMinLengthMessage(String minLengthMessage) {
		this.minLengthMessage = minLengthMessage;
		return this;
	}

	/**
	 * @return the max length message
	 */
	public String getMaxLengthMessage() {
		return maxLengthMessage;
	}

	/**
	 * @param maxLengthMessage the maxLength message to set (must have {0} for field label and {1} for min length value)
	 */
	public BuilderValidators setMaxLengthMessage(String maxLengthMessage) {
		this.maxLengthMessage = maxLengthMessage;
		return this;
	}

	/**
	 * @return the default Min Length Message
	 */
	public static String getDefaultMinLengthMessage() {
		return defaultMinLengthMessage;
	}

	/**
	 * @param defaultMinLengthMessage the default Min Length Message to set
	 */
	public static void setDefaultMinLengthMessage(String defaultMinLengthMessage) {
		BuilderValidators.defaultMinLengthMessage = defaultMinLengthMessage;
	}

	/**
	 * @return the default Max Length Message
	 */
	public static String getDefaultMaxLengthMessage() {
		return defaultMaxLengthMessage;
	}

	/**
	 * @param defaultMaxLengthMessage the default Max Length Message to set
	 */
	public static void setDefaultMaxLengthMessage(String defaultMaxLengthMessage) {
		BuilderValidators.defaultMaxLengthMessage = defaultMaxLengthMessage;
	}

	/**
	 * @return the defaultDateFormatMessage
	 */
	public static String getDefaultDateFormatMessage() {
		return defaultDateFormatMessage;
	}

	/**
	 * @param defaultDateFormatMessage the defaultDateFormatMessage to set
	 */
	public static void setDefaultDateFormatMessage(String defaultDateFormatMessage) {
		BuilderValidators.defaultDateFormatMessage = defaultDateFormatMessage;
	}

	/**
	 * @return the dateFormatMessage
	 */
	public String getDateFormatMessage() {
		return dateFormatMessage;
	}

	/**
	 * @param dateFormatMessage the dateFormatMessage to set
	 */
	public BuilderValidators setDateFormatMessage(String dateFormatMessage) {
		this.dateFormatMessage = dateFormatMessage;
		return this;
	}

	/**
	 * @return the defaultNumericMessage
	 */
	public static String getDefaultNumericMessage() {
		return defaultNumericMessage;
	}

	/**
	 * @param defaultNumericMessage the defaultNumericMessage to set
	 */
	public static void setDefaultNumericMessage(String defaultNumericMessage) {
		BuilderValidators.defaultNumericMessage = defaultNumericMessage;
	}

	/**
	 * @return the numericMessage
	 */
	public String getNumericMessage() {
		return numericMessage;
	}

	/**
	 * @param numericMessage the numericMessage to set
	 */
	public BuilderValidators setNumericMessage(String numericMessage) {
		this.numericMessage = numericMessage;
		return this;
	}
	
	/**
	 * @return List of custom validators
	 */
	public List<IValidator> getValidators() {
		return validators;
	}
	
	/**
	 * Adds a custom validator
	 * @param validator Custom validator
	 * @return This
	 */
	public BuilderValidators add(ICustomValidator validator) {
		getValidators().add(validator);
		return this;
	}

	/**
	 * Adds a standard property validator
	 * @param validator Property validator
	 * @return This
	 */
	public BuilderValidators add(IPropertyValidator validator) {
		getValidators().add(validator);
		return this;
	}

	//creates the validators
	private void createValidators(IValidationMessageHandler validationMessageHandler) {
		
		if (!validatorsCreated) {
		
			for(PropertyValidations validator : this) {
				
				Object namedObject = result.get(validator.getProperty().getName());
				
				String parsedLabel = validator.getLabel();
				if (parsedLabel == null || parsedLabel.length() == 0) {
					//no label defined .... try to get it in a domain-specific way
					parsedLabel = validationMessageHandler.getNamedObjectLabel(namedObject);
				} else {
					//attempt to localize it
					parsedLabel = result.getResource(parsedLabel);
				}
				
				if (validator.isMandatory()) {
					MandatoryValidator v = new MandatoryValidator(validator.getProperty(),parsedLabel,
							getMandatoryMessage(),result);
					validators.add(v);
				}
				
				if (validator.getType() != null) {
					TypeValidator v = new TypeValidator(validator.getProperty(),parsedLabel,
							null, result, validator.getType());
					validators.add(v);
				}
	 			
				if (validator.getMinLength() != null) {
					MinLengthValidator v = new MinLengthValidator(validator.getProperty(),parsedLabel,
							getMinLengthMessage(),result, validator.getMinLength());
					validators.add(v);
				}
				
				if (validator.getMaxLength() != null) {
					MaxLengthValidator v = new MaxLengthValidator(validator.getProperty(),parsedLabel,
							getMaxLengthMessage(),result, validator.getMaxLength());
					validators.add(v);
				}
				
				if (validator.getDateFormat() != null) {
					DateFormatValidator v = new DateFormatValidator(validator.getProperty(),parsedLabel,
							getDateFormatMessage(),result, validator.getDateFormat(),
							validator.getLocaleInstance());
					validators.add(v);
				}
				
				if (validator.isEmailAddress()) {
					EmailAddressValidator v = new EmailAddressValidator(validator.getProperty(),
							parsedLabel,
							getEmailAddressMessage(),result);
					validators.add(v);
				}
				
				if (validator.getRegex() != null) {
					String msgFormat = (validator.getRegexMessage() == null) ? getRegexMessage() : validator.getRegexMessage();
					RegexValidator v = new RegexValidator(validator.getProperty(),parsedLabel,
							msgFormat, result, validator.getRegex());
					validators.add(v);
				}
				
				if (validator.getMinValue() != null) {
					MinValueValidator v = new MinValueValidator(validator.getProperty(),parsedLabel,
							getMinValueMessage(),result,validator.getMinValue());
					validators.add(v);
				}
	
				if (validator.getMaxValue() != null) {
					MaxValueValidator v = new MaxValueValidator(validator.getProperty(),parsedLabel,
							getMaxValueMessage(),result,validator.getMaxValue());
					validators.add(v);
				}
	
			}
			
			//create validators only one
			validatorsCreated = true;
		}
	}

	/**
	 * @return the defaultEmailAddressMessage
	 */
	public static String getDefaultEmailAddressMessage() {
		return defaultEmailAddressMessage;
	}

	/**
	 * @param defaultEmailAddressMessage the defaultEmailAddressMessage to set
	 */
	public static void setDefaultEmailAddressMessage(
			String defaultEmailAddressMessage) {
		BuilderValidators.defaultEmailAddressMessage = defaultEmailAddressMessage;
	}

	/**
	 * @return the emailAddress message
	 */
	public String getEmailAddressMessage() {
		return emailAddressMessage;
	}

	/**
	 * @param emailAddress the emailAddress message to set
	 */
	public void setEmailAddress(String emailAddressMessage) {
		this.emailAddressMessage = emailAddressMessage;
	}

	/**
	 * @return the defaultRegexMessage
	 */
	public static String getDefaultRegexMessage() {
		return defaultRegexMessage;
	}

	/**
	 * @param defaultRegexMessage the defaultRegexMessage to set
	 */
	public static void setDefaultRegexMessage(String defaultRegexMessage) {
		BuilderValidators.defaultRegexMessage = defaultRegexMessage;
	}

	/**
	 * @return the regexMessage
	 */
	public String getRegexMessage() {
		return regexMessage;
	}

	/**
	 * @param regexMessage the regexMessage to set
	 */
	public void setRegexMessage(String regexMessage) {
		this.regexMessage = regexMessage;
	}

	/**
	 * @return the defaultMaxValueMessage
	 */
	public static String getDefaultMaxValueMessage() {
		return defaultMaxValueMessage;
	}

	/**
	 * @param defaultMaxValueMessage the defaultMaxValueMessage to set
	 */
	public static void setDefaultMaxValueMessage(String defaultMaxValueMessage) {
		BuilderValidators.defaultMaxValueMessage = defaultMaxValueMessage;
	}

	/**
	 * @return the defaultMinValueMessage
	 */
	public static String getDefaultMinValueMessage() {
		return defaultMinValueMessage;
	}

	/**
	 * @param defaultMinValueMessage the defaultMinValueMessage to set
	 */
	public static void setDefaultMinValueMessage(String defaultMinValueMessage) {
		BuilderValidators.defaultMinValueMessage = defaultMinValueMessage;
	}

	/**
	 * @return the minValueMessage
	 */
	public String getMinValueMessage() {
		return minValueMessage;
	}

	/**
	 * @param minValueMessage the minValueMessage to set
	 */
	public void setMinValueMessage(String minValueMessage) {
		this.minValueMessage = minValueMessage;
	}

	/**
	 * @return the maxValueMessage
	 */
	public String getMaxValueMessage() {
		return maxValueMessage;
	}

	/**
	 * @param maxValueMessage the maxValueMessage to set
	 */
	public void setMaxValueMessage(String maxValueMessage) {
		this.maxValueMessage = maxValueMessage;
	}
	
	/**
	 * Looks for a particular validator in the list
	 * @param propertyName Property name
	 * @param validatorType Exact class of validator
	 * @return Found validator or null
	 */
	public IValidator getValidator(String objectName, String propertyExpression, Class<?> validatorType ) {
		IValidator v = null;
		
		for(IValidator validator : getValidators()) {
			if (validator instanceof IPropertyValidator) {
				IPropertyValidator pv = (IPropertyValidator) validator;
				if (objectName.equals(pv.getProperty().getName()) &&
						propertyExpression.equals(pv.getProperty().getPropertyExpression()) &&
						validator.getClass().equals(validatorType)) {
					v = validator;
					break;
				}
			}
		}
 		
		return v;
	}
	
}
