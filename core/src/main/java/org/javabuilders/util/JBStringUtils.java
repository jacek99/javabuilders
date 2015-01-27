package org.javabuilders.util;

import java.util.LinkedList;
import java.util.List;

import org.javabuilders.BuildProcess;
import org.javabuilders.IResourceFallback;

/**
 * JB-specific string utilities
 * @author Jacek Furmankiewicz
 *
 */
public class JBStringUtils {

	//poor man's version of functional programming
	private static IResourceFallback resourceFallbackFun = new IResourceFallback() {
		public String get(String key) {
			int pos = key.indexOf(".");
			if (pos < 0) {
				//just a property name
				return getDisplayName(key);
			} else {
				//name in ClassName.Property format
				return getDisplayName(key.substring(pos + 1));
			}
		}
	};
	
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
	 * Looks up a JavaBean property name in the resource bundles using the following algorithm:
	 * <ol>
	 * <li>look for ClassName.PropertyName resource key (e.g. "Person.firstName")</li>
	 * <li>look for PropertyName resource key (e.g.  "firstName")</li>
	 * <li>construct a display name from the property name, e.g. "firstName -> First Name")
	 * </ol>
	 * @param process
	 * @param type
	 * @param propertyName
	 * @return
	 */
	public static String getDisplayLabel(BuildProcess process, Class<?> type, String propertyName) {
		String label = null;
		if (process.getBuildResult().isInternationalizationActive()) {
			label = process.getBuildResult().getResource(String.format("%s.%s", type.getSimpleName(), propertyName),resourceFallbackFun);
		} else {
			label = resourceFallbackFun.get(propertyName);
		}
		return label;
	}
	
	/**
	 * Turns a JavaBean property into a display name, e.g. "firstName => First Name"
	 * @param propertyName
	 * @return Display name
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
