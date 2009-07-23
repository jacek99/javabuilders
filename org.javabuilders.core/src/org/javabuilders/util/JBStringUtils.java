package org.javabuilders.util;

import java.util.LinkedList;
import java.util.List;

/**
 * JB-specific string utilities
 * @author Jacek Furmankiewicz
 *
 */
public class JBStringUtils {

	/**
	 * Splits the string, but ignores all delimiters between double quotes
	 * @param input Input
	 * @param delimiter Delimiter
	 * @return
	 */
	public static List<String> split(String input,char delimiter) {
		return split(input,delimiter,'"');
	}

	
	/**
	 * Splits the string, but ignores all delimiters between the specified quotes character
	 * @param input Input
	 * @param delimiter Delimiter
	 * @param quotes Quotes char
	 * @return
	 */
	public static List<String> split(String input,char delimiter, char quotes) {
		List<String> list = new LinkedList<String>();
		StringBuilder bld = new StringBuilder(input.length());
		
		boolean inQuotes = false;
		for(int i=0; i < input.length();i++) {
			char c= input.charAt(i);
			if (c == quotes) {
				inQuotes = !inQuotes;
			}
			if (c == delimiter && !inQuotes) {
				list.add(bld.toString());
				bld.setLength(0);
			} else {
				bld.append(c);
			}
		}
		if (bld.length() > 0) {
			list.add(bld.toString());
		}
		
		return list;
	}
	
	/**
	 * Splits a  property name into its component parts (e.g. "authorName" -> "Author Name")
	 * @param propertyName Property name
	 * @return List of component
	 */
	public static List<String> splitPropertyName(String propertyName) {
		List<String> list = new LinkedList<String>();
		StringBuilder bld = new StringBuilder(propertyName.length());
		
		for(int i = 0; i < propertyName.length(); i++) {
			String c = propertyName.substring(i,i+1);
			if (c.toUpperCase().equals(c)) {
				list.add(bld.toString());
				bld.setLength(0);
				bld.append(c);
			} else if (i == 0) {
				bld.append(c.toUpperCase());
			} else {
				bld.append(c);
			}
		}
		if (bld.length() > 0) {
			list.add(bld.toString());
		}
		
		return list;
	}
	
	/**
	 * Turns a JavaBean property name into a display value (e.g. "priceList" => "Price List")
	 * @param propertyName
	 * @return
	 */
	public static String getDisplayName(String propertyName) {
		List<String> parts  = splitPropertyName(propertyName);
		StringBuilder bld = new StringBuilder(propertyName.length());
		for(String p : parts) {
			if (bld.length() > 0) {
				bld.append(" ");
			}
			bld.append(p);
		}
		
		return bld.toString();
	}
	
}
