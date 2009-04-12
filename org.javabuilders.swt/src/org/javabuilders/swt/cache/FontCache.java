package org.javabuilders.swt.cache;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.graphics.FontData;

/**
 * Cache for all the unique fonts created by build files.
 * @author Jacek Furmankiewicz
 *
 */
public class FontCache {

	private static Map<String,FontData> fonts = new HashMap<String, FontData>();
	private static Pattern pattern = Pattern.compile("\\s*([a-zA-Z]*)\\s*([0-9]*)\\s*([bold|normal|italic]*)\\s*");
	
	public static FontData getFont(String input) {
		
		String fontName = null;
		Integer size = null;
		Boolean normal = null;
		Boolean italic = null;
		Boolean bold = null;
		
		Matcher m = pattern.matcher(input);
		
		String key = MessageFormat.format("{0}_{1}_{2}_{3}_[4}", fontName,size,normal,italic,bold);
		if (!fonts.containsKey(key)) {
			
			
			
		}
		return fonts.get(key);
		
	}
	
}
