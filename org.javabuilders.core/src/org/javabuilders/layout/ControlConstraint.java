package org.javabuilders.layout;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the constraint of a single control
 * @author Jacek Furmankiewicz
 */
public class ControlConstraint {

	public static final char HALIGN_RIGHT = '>';
	public static final char HALIGN_CENTER = '|';
	public static final char HALIGN_LEFT = '<';
	
	public static final char VALIGN_MIDDLE = '-';
	public static final char VALIGN_BOTTOM = '/';
	public static final char VALIGN_TOP = '^';
	
	public final static char SIZE_GROUP_INDICATOR = '=';
	public final static char SIZE_GROUP_X_INDICATOR = 'x';
	public final static char SIZE_GROUP_Y_INDICATOR = 'y';
	
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
	
	/**
	 * Constructor
	 * @param controlName Control name
	 */
	public ControlConstraint(String constraintText) throws LayoutException {
		
		StringBuilder nameBld = new StringBuilder(constraintText.length());
		StringBuilder vspanBld = new StringBuilder(2);
		StringBuilder hspanBld = new StringBuilder(2);
		StringBuilder sizeGroupBld = new StringBuilder(2);
		this.constraintText = constraintText;
		
		//early sanity checks
		checkEndingFormat(constraintText);
		
		//need to parse the constraint text
		ConstraintType type = ConstraintType.ALIGNMENT;
		
		for(int i = 0; i < constraintText.length(); i++) {
			
			char character = constraintText.charAt(i);
			
			switch (character) {
			case HALIGN_LEFT:
			case HALIGN_CENTER:
			case HALIGN_RIGHT:
			case VALIGN_BOTTOM:
			case VALIGN_MIDDLE:
			case VALIGN_TOP:
				if (type != ConstraintType.ALIGNMENT) {
					throw new LayoutException("Alignment goes before the control name: " + constraintText);
				}				
				break;
			case LayoutCell.SPAN_INDICATOR:
				if (type == ConstraintType.HSPAN) {
					type = ConstraintType.VSPAN;
				} else if (type == ConstraintType.CONTROL_NAME){
					type = ConstraintType.HSPAN;
				} else {
					throw new LayoutException("Span information must go after control name: " + constraintText);
				}
				continue;
			case SIZE_GROUP_INDICATOR:
				if (type.getOrder() >= ConstraintType.CONTROL_NAME.getOrder()) {
					type = ConstraintType.SIZE_GROUP;
				} else {
					throw new LayoutException("Size group indicator found in unexpected location: " + constraintText);
				}
				continue;
			default:
				if (type == ConstraintType.ALIGNMENT) {
					type = ConstraintType.CONTROL_NAME;
				}
				break;
			}
			
			//now that we know what we are dealing with
			if (type == ConstraintType.ALIGNMENT) {
				//we're still at the beginning - optional alignment indicators go first
				switch (character) {
				case HALIGN_LEFT:
					hAlign = HAlign.LEFT;
					break;
				case HALIGN_CENTER:
					hAlign = HAlign.CENTER;
					break;
				case HALIGN_RIGHT:
					hAlign = HAlign.RIGHT;
					break;
				case VALIGN_BOTTOM:
					vAlign = VAlign.BOTTOM;
					break;
				case VALIGN_MIDDLE:
					vAlign = VAlign.MIDDLE;
					break;
				case VALIGN_TOP:
					vAlign = VAlign.TOP;
					break;
				default:
					throw new LayoutException("Unable to parse alignment: " + constraintText);
				}
			} else if (type == ConstraintType.CONTROL_NAME) {
				nameBld.append(character);
			} else if (type == ConstraintType.HSPAN) {
				hspanBld.append(character);
			} else if (type == ConstraintType.VSPAN) {
				vspanBld.append(character);
			} else if (type == ConstraintType.SIZE_GROUP) {
				if (character == SIZE_GROUP_X_INDICATOR) {
					sizeGroupX = true;
				} else if (character == SIZE_GROUP_Y_INDICATOR) {
					sizeGroupY = true;
				} else {
					sizeGroupBld.append(character);
				}
			}
			
		}
		
		//apply all the parsed values
		this.controlName = nameBld.toString();
		try {
			if (hspanBld.length() == 1 && hspanBld.toString().equals(String.valueOf(LayoutCell.MAX_SPAN_VALUE))) {
				this.setMaxHSpan(true);
			} else if (hspanBld.length() > 0) {
				this.hSpan = Integer.parseInt(hspanBld.toString());
			} 

			if (vspanBld.length() == 1 && vspanBld.toString().equals(String.valueOf(LayoutCell.MAX_SPAN_VALUE))) {
				this.setMaxVSpan(true);
			} else if (vspanBld.length() > 0) {
				this.vSpan = Integer.parseInt(vspanBld.toString());
			}
			
			if (sizeGroupBld.length() > 0) {
				this.sizeGroup = Integer.parseInt(sizeGroupBld.toString());
			}
		} catch (NumberFormatException ex) {
			throw new LayoutException("Unable to parse expected integer value : " + constraintText,ex);
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
	 * Enum used for parsing
	 */
	private enum ConstraintType {
		
		ALIGNMENT(0), CONTROL_NAME(1), HSPAN(2), VSPAN (3), SIZE_GROUP (4);
		
		private int order = 0;
		
		ConstraintType(int order) {
			this.order = order;
		}
		
		private int getOrder() {
			return order;
		}
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
	//additional checks
	private void checkEndingFormat(String constraintText) {
		String last = String.valueOf(constraintText.charAt(constraintText.length() - 1));
		if (postIdentifiers.contains(last)) {
			throw new LayoutException("Constraint text {0} cannot end with {1}", 
					constraintText, last);
			
		}
	}
}
