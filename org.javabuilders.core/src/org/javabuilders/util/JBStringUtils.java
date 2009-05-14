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
	
}
