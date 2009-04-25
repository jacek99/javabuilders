package org.javabuilders.swt.cache;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

/**
 * Cache for all the unique fonts created by build files.
 * @author Jacek Furmankiewicz
 *
 */
public class FontCache {

	private static Map<String,Font> fonts = new HashMap<String, Font>();
	public static final String REGEX = "\\s*([a-zA-Z]*)\\s*([0-9]*)\\s*([bold|normal|italic]*)\\s*";
	private static Pattern pattern = Pattern.compile(REGEX);
	
	private static final String BOLD = "bold";
	private static final String NORMAL = "normal";
	private static final String ITALIC = "italic";
	
	/**
	 * @param device Device
	 * @param input Input string with font specs
	 * @return Font
	 */
	public static Font getFont(Device device, String input) {
		
		String fontName = null;
		Integer size = null;
		int normal = SWT.NORMAL;
		int italic = SWT.NONE;
		int bold = SWT.NONE;
		
		Matcher m = pattern.matcher(input);
		while (m.find()) {
			for(int i = 1; i < m.groupCount(); i++) {
				String group = m.group(i);
				if (group.length() > 0) {
					if (group.matches("[0-9]+")) {
						size = Integer.parseInt(group);
					} else if (BOLD.equals(group)) {
						bold = SWT.BOLD;
						normal = SWT.NONE;
					} else if (NORMAL.equals(group)) {
						normal = SWT.NORMAL;
					} else if (ITALIC.equals(group)) {
						italic = SWT.ITALIC;
						normal = SWT.NONE;
					} else {
						fontName = group;
					}
				}
			}
		}
		
		String key = MessageFormat.format("{0}_{1}_{2}_{3}_[4}", fontName,size,normal,italic,bold);
		if (!fonts.containsKey(key)) {
			FontData fd = new FontData();
			if (size != null) {
				fd.setHeight(size);
			}
			if (fontName != null) {
				fd.setName(fontName);
			}
			fd.setStyle(normal|italic|bold);
			Font font = new Font(device,fd);
			fonts.put(key, font);
		}
		return fonts.get(key);
	}
		
}
