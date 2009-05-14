package org.beansaver.util;

import java.util.LinkedList;
import java.util.List;

public class NameUtils {

	/**
	 * Converts a JavaBean property to its part, with a specific separator
	 * @param propertyName Property name
	 * @param separator Separator
	 * @return Converted value
	 */
	public static String getConvertedPropertyName(String propertyName, char separator) {
		List<String> parts = new LinkedList<String>();
		StringBuilder bld = new StringBuilder();
		
		for(int i = 0; i < propertyName.length();i++) {
			String part = propertyName.substring(i,1);
			if (part.matches("[A-Z]")) {
				parts.add(bld.toString());
				bld.setLength(0);
				bld.append(part.toLowerCase());
			} else {
				bld.append(part);
			}
		}
		parts.add(bld.toString());
		
		bld.setLength(0);
		for(String part : parts) {
			if (bld.length() > 0) {
				bld.append(separator);
			}
			bld.append(part);
		}
		return bld.toString().toUpperCase();
	}
	
}
