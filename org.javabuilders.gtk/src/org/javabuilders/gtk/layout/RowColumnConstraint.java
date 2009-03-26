package org.javabuilders.gtk.layout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javabuilders.layout.HAlign;
import org.javabuilders.layout.VAlign;

/**
 * A row or column constraint that can handle 3 separate modes:
 * <pre>
 * pref = preffered width
 * grow = grows (resizes)
 * number = explicit width in pixels
 * + also a spacer indicator in pixels (that would be after the row or column)
 * spacer = pixels
 * </pre>
 * Examples:
 * <pre>
 * [pref]
 * [grow]
 * [200]
 * [grow] 20
 * </pre>
 * @author Jacek Furmankiewicz
 *
 */
public class RowColumnConstraint {

	private static final String REGEX = "\\[(.+)\\]\\s*([0-9]*)";
	private static final Pattern pattern = Pattern.compile(REGEX);
	
	private static final String PREF = "pref";
	private static final String GROW = "grow";
	private static final String RIGHT = "right";
	private static final String LEFT = "left";
	private static final String CENTER = "center";
	private static final String TOP = "top";
	private static final String BOTTOM = "botton";
	private static final String MIDDLE = "middle";
	
	private HAlign hAlign = HAlign.DEFAULT;
	private VAlign vAlign = VAlign.DEFAULT;
	
	private Integer value = Integer.MIN_VALUE; //MIN VALUE = pref, MAX VALUE = max
	private int index = 0;
	private int spacerWidth = 0;
	
	/**
	 * Constructor
	 * @param rowOrColumnIndex
	 * @param constraints
	 */
	public RowColumnConstraint(int index, String constraints) {
		this.index = index;
		parseConstraints(constraints);
	}
	
	//parses the info
	private void parseConstraints(String constraints) {
		Matcher m = pattern.matcher(constraints);
		if (m.find()) {
			if (m.groupCount() >= 2) {
				String group = m.group(1);
				String[] parts = group.split(",");
				for(String part : parts) {
					if (PREF.equals(part)) {
						value = Integer.MIN_VALUE;
					} else if (GROW.equals(part)) {
						value = Integer.MAX_VALUE;
					} else if (RIGHT.equals(part)) {
						hAlign = HAlign.RIGHT;
					} else if (LEFT.equals(part)) {
						hAlign = HAlign.LEFT;
					} else if (CENTER.equals(part)) {
						hAlign = HAlign.CENTER;
					} else if (TOP.equals(part)) {
						vAlign = VAlign.TOP;
					} else if (BOTTOM.equals(part)) {
						vAlign = VAlign.BOTTOM;
					} else if (MIDDLE.equals(part)) {
						vAlign = VAlign.MIDDLE;
					} else if (part.length() > 0 ){
						value = Integer.parseInt(part);
					}
				}
				
			}
			if (m.groupCount() >= 3) {
				spacerWidth = Integer.parseInt(m.group(2));
			}
		}
	}
	
	/**
	 * @return If this row or column is supposed to have the largest preferred width of its children widgets
	 */
	public boolean isPreferredSize() {
		return value.equals(Integer.MIN_VALUE);
	}
	
	/**
	 * @return @return If this row or column is supposed to grow when resized
	 */
	public boolean isGrowing() {
		return value.equals(Integer.MAX_VALUE);
	}
	
	/**
	 * @return Explicit width (if preferredSize and grow are false)
	 */
	public int getWidth() {
		if (!isGrowing() && !isPreferredSize()) {
			return value.intValue();
		} else {
			return -1; //default size
		}
	}
	
	/**
	 * @return if specified, specifies any spacers that should be added after the current row or column
	 */
	public int getSpacerWidth() {
		return spacerWidth;
	}
	
	/**
	 * @return Row or column index
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * @return Horizontal alignment info
	 */
	public HAlign getHAlign() {
		return hAlign;
	}
	
	/**
	 * @return Vertical alignment info
	 */
	public VAlign getVAlign() {
		return vAlign;
	}
	
}
