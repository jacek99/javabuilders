package org.javabuilders;

/**
 * Interface for configuring builders that support string literal controls
 * @author Jacek Furmankiewicz
 *
 */
public interface IStringLiteralControlConfig {

	/**
	 * @return Suffix
	 */
	String getStringLiteralControlSuffix();
	/**
	 * @return Prefix
	 */
	String getStringLiteralControlPrefix();
	/**
	 * @param suffix Suffix
	 */
	void setStringLiteralControlSuffix(String suffix);
	/**
	 * @param prefix Prefix
	 */
	void setStringLiteralControlPrefix(String prefix);
	
}
