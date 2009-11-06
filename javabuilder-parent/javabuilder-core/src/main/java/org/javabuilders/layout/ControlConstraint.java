package org.javabuilders.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents the constraint of a single control
 * @author Jacek Furmankiewicz
 */
public class ControlConstraint {

	//regex: ^([\<\|\>\^\-/]*)?(".+")?([a-zA-Z0-9]+)?(\+\*)?(\+[0-9]+)?(\+\*)?(\+[0-9]+)?(=[0-9])?(x*y*)?$
	private static final Pattern pattern = Pattern.compile("^([\\<\\|\\>\\^\\-/]*)?(\".+\")?([a-zA-Z0-9_]+)?(\\+\\*)?(\\+[0-9]+)?(\\+\\*)?(\\+[0-9]+)?(=[0-9])?(x*y*)?([\\<\\|\\>\\^\\-/]*)?$");
	
	/**
	 * REGEX GROUPS
	 * 1: alignment
	 * 2: name if embedded in quotes
	 * 3: name if not embedded in quotes
	 * 4: +* for rows
	 * 5: +X for rows
	 * 6: +* for columns
	 * 7: +Y for columns
	 * 8: =X size group
	 * 9 = x/y size group indicator
	 */
	
	public static final char HALIGN_RIGHT ='>';
	public static final char HALIGN_CENTER = '|';
	public static final char HALIGN_LEFT = '<';
	
	public static final char VALIGN_MIDDLE = '-';
	public static final char VALIGN_BOTTOM = '/';
	public static final char VALIGN_TOP = '^';
	
	public final static char SIZE_GROUP_INDICATOR = '=';
	public final static String SIZE_GROUP_X_INDICATOR = "x";
	public final static String SIZE_GROUP_Y_INDICATOR = "y";
	
	public static final char WIDTH_MAX ='>';
	public static final char WIDTH_PREF = '|';
	public static final char WIDTH_MIN = '<';
	
	public final static char QUOTE = '"';
	
	private final static List<String> postIdentifiers = new ArrayList<String>();
	
	static {
		postIdentifiers.add(String.valueOf(SIZE_GROUP_INDICATOR));
		postIdentifiers.add(String.valueOf(LayoutCell.SPAN_INDICATOR));
	}
	
	private String controlName = "";
	@SuppressWarnings("unused")
	private String constraintText = "";
	
	private int hSpan = 1;
	private int vSpan = 1;
	
	private HAlign hAlign = HAlign.DEFAULT;
	private VAlign vAlign = VAlign.DEFAULT;
	
	private Integer sizeGroup = null; 
	private boolean sizeGroupX = false;
	private boolean sizeGroupY = false;
	
	private boolean isMaxHSpan = false;
	private boolean isMaxVSpan = false;
	
	private Size hSize = Size.DEFAULT;
	private Size vSize = Size.DEFAULT;
	
	/**
	 * Constructor
	 * @param controlName Control name
	 */
	public ControlConstraint(String constraintText) throws LayoutException {
		
		this.constraintText = constraintText;
		
		Matcher m = pattern.matcher(constraintText);
		if (m.find()) {
			
			//hAlign & valign
			if (m.group(1) != null) {
				char[] chars = m.group(1).toCharArray();
				for(char c : chars) {

					switch(c) {
					case HALIGN_LEFT:
						hAlign = HAlign.LEFT;
						break;
					case HALIGN_CENTER:
						hAlign = HAlign.CENTER;
						break;
					case HALIGN_RIGHT:
						hAlign = HAlign.RIGHT;
						break;
					case VALIGN_TOP:
						vAlign = VAlign.TOP;
						break;
					case VALIGN_MIDDLE:
						vAlign = VAlign.MIDDLE;
						break;
					case VALIGN_BOTTOM:
						vAlign = VAlign.BOTTOM;
						break;
					}
				}
			}
			
			//name (in quotes or not)
			if (m.group(2) != null) {
				this.controlName = m.group(2);
			}
			if (m.group(3) != null) {
				this.controlName = m.group(3);
			}
			
			//row span
			if (m.group(4) != null) {
				this.setMaxHSpan(true);
			}
			if (m.group(5) != null) {
				this.setHSpan(Integer.parseInt(m.group(5).substring(1)));
			}
			
			//column span
			if (m.group(6) != null) {
				this.setMaxVSpan(true);
			}
			if (m.group(7) != null) {
				this.setVSpan(Integer.parseInt(m.group(7).substring(1)));
			}
			
			//size group
			if (m.group(8) != null) {
				this.setSizeGroup(Integer.parseInt(m.group(8).substring(1)));
			}
			
			if (SIZE_GROUP_X_INDICATOR.equals(m.group(9))) {
				sizeGroupX = true;
			} else if (SIZE_GROUP_Y_INDICATOR.equals(m.group(9))) {
				sizeGroupY = true;
			}
			
			if (m.group(10) != null) {
				char[] chars = m.group(10).toCharArray();
				for(char c : chars) {
					switch(c) {
					case HALIGN_LEFT:
						hSize = Size.MIN;
						break;
					case HALIGN_CENTER:
						hSize = Size.PREF;
						break;
					case HALIGN_RIGHT:
						hSize = Size.MAX;
						break;
					case VALIGN_TOP:
						vSize = Size.MIN;
						break;
					case VALIGN_MIDDLE:
						vSize = Size.PREF;
						break;
					case VALIGN_BOTTOM:
						vSize = Size.MAX;
						break;
					}
				}
			}
			
		} else {
			throw new LayoutException("Unable to parse {0} control constraint",constraintText);
		}
		
	}

	/**
	 * @return the controlName
	 */
	public String getControlName() {
		return controlName;
	}

	/**
	 * @param controlName the controlName to set
	 */
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	/**
	 * @return the spanHorizontal
	 */
	public int getHSpan() {
		return hSpan;
	}

	/**
	 * @param spanHorizontal the spanHorizontal to set
	 */
	public void setHSpan(int spanHorizontal) {
		this.hSpan = spanHorizontal;
	}
	
	/**
	 * @return the spanVertical
	 */
	public int getVSpan() {
		return vSpan;
	}

	/**
	 * @param spanVertical the spanVertical to set
	 */
	public void setVSpan(int spanVertical) {
		this.vSpan = spanVertical;
	}

	/**
	 * @return the hAlign
	 */
	public HAlign getHAlign() {
		return hAlign;
	}

	/**
	 * @param align the hAlign to set
	 */
	public void setHAlign(HAlign align) {
		hAlign = align;
	}

	/**
	 * @return the vAlign
	 */
	public VAlign getVAlign() {
		return vAlign;
	}

	/**
	 * @param align the vAlign to set
	 */
	public void setVAlign(VAlign align) {
		vAlign = align;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Name: %s, hAlign: %s, vAlign: %s, hSpan: %s, vSpan: %s",
				controlName, hAlign, vAlign, hSpan, vSpan);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return controlName.hashCode();
	}

	/**
	 * @return The size group this control belongs to (or null if not defined)
	 */
	public Integer getSizeGroup() {
		return sizeGroup;
	}

	/**
	 * @param sizeGroup The size group that thus control belongs to
	 */
	public void setSizeGroup(Integer sizeGroup) {
		this.sizeGroup = sizeGroup;
	}

	/**
	 * @return the isMaxHSpan
	 */
	public boolean isMaxHSpan() {
		return isMaxHSpan;
	}

	/**
	 * @param isMaxHSpan the isMaxHSpan to set
	 */
	public void setMaxHSpan(boolean isMaxHSpan) {
		this.isMaxHSpan = isMaxHSpan;
	}

	/**
	 * @return the isMaxVSpan
	 */
	public boolean isMaxVSpan() {
		return isMaxVSpan;
	}

	/**
	 * @param isMaxVSpan the isMaxVSpan to set
	 */
	public void setMaxVSpan(boolean isMaxVSpan) {
		this.isMaxVSpan = isMaxVSpan;
	}

	/**
	 * @return If is a horizontal size group
	 */
	public boolean isSizeGroupX() {
		return sizeGroupX;
	}

	/**
	 * @return If is a vertical size group
	 */
	public boolean isSizeGroupY() {
		return sizeGroupY;
	}

	/**
	 * @return the horiz Size
	 */
	public Size getHSize() {
		return hSize;
	}

	/**
	 * @param hSize the horiz Size to set
	 */
	public void setHSize(Size hSize) {
		this.hSize = hSize;
	}

	/**
	 * @return the vertical Size
	 */
	public Size getVSize() {
		return vSize;
	}

	/**
	 * @param vSize the vertical Size to set
	 */
	public void setVSize(Size vSize) {
		this.vSize = vSize;
	}

}
