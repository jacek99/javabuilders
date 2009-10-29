/**
 * 
 */
package org.javabuilders.swing.handler.type;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;

/**
 * Handles creating Color objects
 * @author Jacek Furmankiewicz
 *
 */
public class ColorAsValueHandler implements ITypeAsValueHandler<Color> {

	private final static String COLOR_NAME_REGEX = "^[a-zA-Z]*$";
	private final static String COLOR_VALUE_REGEX = "^([a-fA-F0-9]){6}$";
	private final static String COLOR_VALUE_SHORT_REGEX = "^([a-fA-F0-9]){3}$"; //hex color as 3 chars e.g. F0E = FF00EE
	public final static String COLOR_REGEX = COLOR_NAME_REGEX + "|" + COLOR_VALUE_REGEX + "|" + COLOR_VALUE_SHORT_REGEX;
	
	private final static Map<String,String> colorValues = new HashMap<String, String>();
	private final static Map<Integer,Color> colors = new HashMap<Integer, Color>(); //map of all the created colors...lazy populated as a cache
	
	static {
		colorValues.put("aliceblue","f0f8ff");
		colorValues.put("antiquewhite","faebd7");
		colorValues.put("aqua","00ffff");
		colorValues.put("aquamarine","7fffd4");
		colorValues.put("azure","f0ffff");
		colorValues.put("beige","f5f5dc");
		colorValues.put("bisque","ffe4c4");
		colorValues.put("black","000000");
		colorValues.put("blanchedalmond","ffebcd");
		colorValues.put("blue","0000ff");
		colorValues.put("blueviolet","8a2be2");
		colorValues.put("brown","a52a2a");
		colorValues.put("burlywood","deb887");
		colorValues.put("cadetblue","5f9ea0");
		colorValues.put("chartreuse","7fff00");
		colorValues.put("chocolate","d2691e");
		colorValues.put("coral","ff7f50");
		colorValues.put("cornflowerblue","6495ed");
		colorValues.put("cornsilk","fff8dc");
		colorValues.put("crimson","dc143c");
		colorValues.put("cyan","00ffff");
		colorValues.put("darkblue","00008b");
		colorValues.put("darkcyan","008b8b");
		colorValues.put("darkgoldenrod","b8860b");
		colorValues.put("darkgray","a9a9a9");
		colorValues.put("darkgrey","a9a9a9");
		colorValues.put("darkgreen","006400");
		colorValues.put("darkkhaki","bdb76b");
		colorValues.put("darkmagenta","8b008b");
		colorValues.put("darkolivegreen","556b2f");
		colorValues.put("darkorange","ff8c00");
		colorValues.put("darkorchid","9932cc");
		colorValues.put("darkred","8b0000");
		colorValues.put("darksalmon","e9967a");
		colorValues.put("darkseagreen","8fbc8f");
		colorValues.put("darkslateblue","483d8b");
		colorValues.put("darkslategray","2f4f4f");
		colorValues.put("darkslategrey","2f4f4f");
		colorValues.put("darkturquoise","00ced1");
		colorValues.put("darkviolet","9400d3");
		colorValues.put("deeppink","ff1493");
		colorValues.put("deepskyblue","00bfff");
		colorValues.put("dimgray","696969");
		colorValues.put("dimgrey","696969");
		colorValues.put("dodgerblue","1e90ff");
		colorValues.put("firebrick","b22222");
		colorValues.put("floralwhite","fffaf0");
		colorValues.put("forestgreen","228b22");
		colorValues.put("fuchsia","ff00ff");
		colorValues.put("gainsboro","dcdcdc");
		colorValues.put("ghostwhite","f8f8ff");
		colorValues.put("gold","ffd700");
		colorValues.put("goldenrod","daa520");
		colorValues.put("gray","808080");
		colorValues.put("grey","808080");
		colorValues.put("green","008000");
		colorValues.put("greenyellow","adff2f");
		colorValues.put("honeydew","f0fff0");
		colorValues.put("hotpink","ff69b4");
		colorValues.put("indianred","cd5c5c");
		colorValues.put("indigo","4b0082");
		colorValues.put("ivory","fffff0");
		colorValues.put("khaki","f0e68c");
		colorValues.put("lavender","e6e6fa");
		colorValues.put("lavenderblush","fff0f5");
		colorValues.put("lawngreen","7cfc00");
		colorValues.put("lemonchiffon","fffacd");
		colorValues.put("lightblue","add8e6");
		colorValues.put("lightcoral","f08080");
		colorValues.put("lightcyan","e0ffff");
		colorValues.put("lightgoldenrodyellow","fafad2");
		colorValues.put("lightgray","d3d3d3");
		colorValues.put("lightgrey","d3d3d3");
		colorValues.put("lightgreen","90ee90");
		colorValues.put("lightpink","ffb6c1");
		colorValues.put("lightsalmon","ffa07a");
		colorValues.put("lightseagreen","20b2aa");
		colorValues.put("lightskyblue","87cefa");
		colorValues.put("lightslategray","778899");
		colorValues.put("lightslategrey","778899");
		colorValues.put("lightsteelblue","b0c4de");
		colorValues.put("lightyellow","ffffe0");
		colorValues.put("lime","00ff00");
		colorValues.put("limegreen","32cd32");
		colorValues.put("linen","faf0e6");
		colorValues.put("magenta","ff00ff");
		colorValues.put("maroon","800000");
		colorValues.put("mediumaquamarine","66cdaa");
		colorValues.put("mediumblue","0000cd");
		colorValues.put("mediumorchid","ba55d3");
		colorValues.put("mediumpurple","9370d8");
		colorValues.put("mediumseagreen","3cb371");
		colorValues.put("mediumslateblue","7b68ee");
		colorValues.put("mediumspringgreen","00fa9a");
		colorValues.put("mediumturquoise","48d1cc");
		colorValues.put("mediumvioletred","c71585");
		colorValues.put("midnightblue","191970");
		colorValues.put("mintcream","f5fffa");
		colorValues.put("mistyrose","ffe4e1");
		colorValues.put("moccasin","ffe4b5");
		colorValues.put("navajowhite","ffdead");
		colorValues.put("navy","000080");
		colorValues.put("oldlace","fdf5e6");
		colorValues.put("olive","808000");
		colorValues.put("olivedrab","6b8e23");
		colorValues.put("orange","ffa500");
		colorValues.put("orangered","ff4500");
		colorValues.put("orchid","da70d6");
		colorValues.put("palegoldenrod","eee8aa");
		colorValues.put("palegreen","98fb98");
		colorValues.put("paleturquoise","afeeee");
		colorValues.put("palevioletred","d87093");
		colorValues.put("papayawhip","ffefd5");
		colorValues.put("peachpuff","ffdab9");
		colorValues.put("peru","cd853f");
		colorValues.put("pink","ffc0cb");
		colorValues.put("plum","dda0dd");
		colorValues.put("powderblue","b0e0e6");
		colorValues.put("purple","800080");
		colorValues.put("red","ff0000");
		colorValues.put("rosybrown","bc8f8f");
		colorValues.put("royalblue","4169e1");
		colorValues.put("saddlebrown","8b4513");
		colorValues.put("salmon","fa8072");
		colorValues.put("sandybrown","f4a460");
		colorValues.put("seagreen","2e8b57");
		colorValues.put("seashell","fff5ee");
		colorValues.put("sienna","a0522d");
		colorValues.put("silver","c0c0c0");
		colorValues.put("skyblue","87ceeb");
		colorValues.put("slateblue","6a5acd");
		colorValues.put("slategray","708090");
		colorValues.put("slategrey","708090");
		colorValues.put("snow","fffafa");
		colorValues.put("springgreen","00ff7f");
		colorValues.put("steelblue","4682b4");
		colorValues.put("tan","d2b48c");
		colorValues.put("teal","008080");
		colorValues.put("thistle","d8bfd8");
		colorValues.put("tomato","ff6347");
		colorValues.put("turquoise","40e0d0");
		colorValues.put("violet","ee82ee");
		colorValues.put("wheat","f5deb3");
		colorValues.put("white","ffffff");
		colorValues.put("whitesmoke","f5f5f5");
		colorValues.put("yellow","ffff00");
		colorValues.put("yellowgreen","9acd32");
	}
	
	private final static ColorAsValueHandler singleton = new ColorAsValueHandler();
	
	/**
	 * @return Singleton
	 */
	public static ColorAsValueHandler getInstance() {return singleton;}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getInputValueSample()
	 */
	public String getInputValueSample() {
		return "AliceBlue | F0F8FF | F0E";
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
	 */
	public String getRegex() {
		return COLOR_REGEX;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
	 */
	public Color getValue(BuildProcess process, Node node, String key,
			Object inputValue) throws BuildException {
		
		String colorValue = (String)inputValue;
		Color color = null;
		
		if (colorValue.matches(COLOR_VALUE_SHORT_REGEX)) {
			color = getHexColor(colorValue);
		} else if (colorValue.matches(COLOR_VALUE_REGEX)) {
			color = getHexColor(colorValue);
		} else if (colorValue.matches(COLOR_NAME_REGEX)) {
			colorValue = colorValue.toLowerCase();
			if (colorValues.containsKey(colorValue)) {
				color = getHexColor(colorValues.get(colorValue));
			} else {
				throw new BuildException("\"{0}\" is not a valid color name",colorValue);
			}
		} else {
			throw new BuildException("\"{0}\" is not a valid color definition",colorValue);
		}
		
		return color;
	}
	
	private static Color getHexColor(String colorValue) {
		//handle 3-digit color
		if (colorValue.length() == 3) {
			StringBuilder builder = new StringBuilder(6);
			for(int i = 0; i < 3;i++) {
				builder.append(colorValue.charAt(i)).append(colorValue.charAt(i));
			}
			colorValue = builder.toString();
		}
		
		int rgb = Integer.parseInt(colorValue,16);
		
		Color color = null;
		//each created color is cached, lazily
		if (colors.containsKey(rgb)) {
			color = colors.get(rgb);
		} else {
			color = new Color(rgb);
			colors.put(rgb, color);
		}
		
		return color;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Color.class;
	}

}
