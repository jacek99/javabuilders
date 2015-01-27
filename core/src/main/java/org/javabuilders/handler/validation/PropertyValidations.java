package org.javabuilders.handler.validation;

import java.util.List;
import java.util.Locale;

import org.javabuilders.NamedObjectProperty;

/**
 * Represents a validator for a property
 * @author Jacek Furmankiewicz
 */
public class PropertyValidations {
	
	private NamedObjectProperty property;
	
	private boolean mandatory = false;
	private Integer minLength = null;
	private Integer maxLength = null;
	private String dateFormat = null;
	private String format = null;
	private Object minValue = null;
	private Object maxValue = null;
	private List<String> range = null;
	private boolean currency = false;
	private boolean percent = false;
	private String regex = null;
	private boolean regexCaseSensitive = true;
	private String regexMessage = null;
	private boolean luhnCheckDigit = false;
	private boolean ean13CheckDigit = false;
	private boolean isbnCheckDigit = false;
	private boolean isbn10CheckDigit = false;
	private boolean ipAddress = false;
	private boolean email = false;
	private boolean url = false;
	private boolean domain = false;
	private boolean topLevelDomain = false;
	private boolean genericTopLevelDomain = false;
	private boolean countryCodeTopLevelDomain = false;
	private String locale = null;
	private String type = null;
	
	private String label;
	
	/**
	 * @param source Source
	 * @param property Property name
	 */
	public PropertyValidations(NamedObjectProperty property) {
		this.property = property;
	}
	
	/**
	 * @return the property 
	 */
	public NamedObjectProperty getProperty() {
		return property;
	}
	/**
	 * @return the mandatory
	 */
	public boolean isMandatory() {
		return mandatory;
	}
	/**
	 * @param mandatory the mandatory to set
	 */
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	/**
	 * @return the minLength
	 */
	public Integer getMinLength() {
		return minLength;
	}
	/**
	 * @param minLength the minLength to set
	 */
	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}
	/**
	 * @return the maxLength
	 */
	public Integer getMaxLength() {
		return maxLength;
	}
	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}
	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the range
	 */
	public List<String> getRange() {
		return range;
	}
	/**
	 * @param range the range to set
	 */
	public void setRange(List<String> range) {
		this.range = range;
	}
	/**
	 * @return the currency
	 */
	public boolean isCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(boolean currency) {
		this.currency = currency;
	}
	/**
	 * @return the percent
	 */
	public boolean isPercent() {
		return percent;
	}
	/**
	 * @param percent the percent to set
	 */
	public void setPercent(boolean percent) {
		this.percent = percent;
	}
	/**
	 * @return the regex
	 */
	public String getRegex() {
		return regex;
	}
	/**
	 * @param regex the regex to set
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}
	/**
	 * @return the regexCaseSensitive
	 */
	public boolean isRegexCaseSensitive() {
		return regexCaseSensitive;
	}
	/**
	 * @param regexCaseSensitive the regexCaseSensitive to set
	 */
	public void setRegexCaseSensitive(boolean regexCaseSensitive) {
		this.regexCaseSensitive = regexCaseSensitive;
	}
	/**
	 * @return the luhnCheckDigit
	 */
	public boolean isLuhnCheckDigit() {
		return luhnCheckDigit;
	}
	/**
	 * @param luhnCheckDigit the luhnCheckDigit to set
	 */
	public void setLuhnCheckDigit(boolean luhnCheckDigit) {
		this.luhnCheckDigit = luhnCheckDigit;
	}
	/**
	 * @return the ean13CheckDigit
	 */
	public boolean isEan13CheckDigit() {
		return ean13CheckDigit;
	}
	/**
	 * @param ean13CheckDigit the ean13CheckDigit to set
	 */
	public void setEan13CheckDigit(boolean ean13CheckDigit) {
		this.ean13CheckDigit = ean13CheckDigit;
	}
	/**
	 * @return the isbnCheckDigit
	 */
	public boolean isIsbnCheckDigit() {
		return isbnCheckDigit;
	}
	/**
	 * @param isbnCheckDigit the isbnCheckDigit to set
	 */
	public void setIsbnCheckDigit(boolean isbnCheckDigit) {
		this.isbnCheckDigit = isbnCheckDigit;
	}
	/**
	 * @return the isbn10CheckDigit
	 */
	public boolean isIsbn10CheckDigit() {
		return isbn10CheckDigit;
	}
	/**
	 * @param isbn10CheckDigit the isbn10CheckDigit to set
	 */
	public void setIsbn10CheckDigit(boolean isbn10CheckDigit) {
		this.isbn10CheckDigit = isbn10CheckDigit;
	}
	/**
	 * @return the ipAddress
	 */
	public boolean isIpAddress() {
		return ipAddress;
	}
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(boolean ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @return the emailAddress
	 */
	public boolean isEmailAddress() {
		return email;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(boolean emailAddress) {
		this.email = emailAddress;
	}
	/**
	 * @return the url
	 */
	public boolean isUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(boolean url) {
		this.url = url;
	}
	/**
	 * @return the domain
	 */
	public boolean isDomain() {
		return domain;
	}
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(boolean domain) {
		this.domain = domain;
	}

	/**
	 * @return the topLevelDomain
	 */
	public boolean isTopLevelDomain() {
		return topLevelDomain;
	}

	/**
	 * @param topLevelDomain the topLevelDomain to set
	 */
	public void setTopLevelDomain(boolean topLevelDomain) {
		this.topLevelDomain = topLevelDomain;
	}

	/**
	 * @return the genericTopLevelDomain
	 */
	public boolean isGenericTopLevelDomain() {
		return genericTopLevelDomain;
	}

	/**
	 * @param genericTopLevelDomain the genericTopLevelDomain to set
	 */
	public void setGenericTopLevelDomain(boolean genericTopLevelDomain) {
		this.genericTopLevelDomain = genericTopLevelDomain;
	}

	/**
	 * @return the countryCodeTopLevelDomain
	 */
	public boolean isCountryCodeTopLevelDomain() {
		return countryCodeTopLevelDomain;
	}

	/**
	 * @param countryCodeTopLevelDomain the countryCodeTopLevelDomain to set
	 */
	public void setCountryCodeTopLevelDomain(boolean countryCodeTopLevelDomain) {
		this.countryCodeTopLevelDomain = countryCodeTopLevelDomain;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return property.toString();
	}

	/**
	 * @return the label that describes the property (e.g. "First Name"). Will be used in all error messages
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label describes the property (e.g. "First Name"). Will be used in all error messages
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	/**
	 * @return The locale instance. if getLocale() is null, returns default Locale
	 */
	public Locale getLocaleInstance() {
		if (getLocale() == null) {
			return Locale.getDefault();
		} else {
			String[] parts = getLocale().split("-");
			if (parts.length == 1) {
				return new Locale(parts[0]);
			} else {
				return new Locale(parts[0],parts[1]);
			}
		}
	}

	/**
	 * @return the email
	 */
	public boolean isEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(boolean email) {
		this.email = email;
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
	 * @return the minValue
	 */
	public Object getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(Object minValue) {
		this.minValue = minValue;
	}

	/**
	 * @return the maxValue
	 */
	public Object getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(Object maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
