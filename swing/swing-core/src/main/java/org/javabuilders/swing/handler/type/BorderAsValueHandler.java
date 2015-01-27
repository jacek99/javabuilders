/**
 * 
 */
package org.javabuilders.swing.handler.type;

import static org.javabuilders.swing.handler.type.ColorAsValueHandler.COLOR_REGEX;

import java.awt.Color;
import java.awt.SystemColor;
import java.text.MessageFormat;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;

/**
 * Handlels creating borders as property values
 * @author Jacek Furmankiewicz
 */
public class BorderAsValueHandler implements ITypeAsValueHandler<Border> {

	public final static String LOWERED_BEVEL = "loweredBevel";
	public final static String RAISED_BEVEL = "raisedBevel";
	public final static String LOWERED_ETCHED = "loweredEtched";
	public final static String RAISED_ETCHED = "raisedEtched";
	
	public final static String LINE_BORDER_REGEX = "\\d+";
	public final static String COLOR_LINE_BORDER_REGEX = "^([a-zA-Z0-9]+)\\s+\\d+$";
	
	private final static String regex = MessageFormat.format("{0}|{1}|{2}|{3}|{4}|({5})|{6}", 
			LOWERED_BEVEL, RAISED_BEVEL, LOWERED_ETCHED, RAISED_ETCHED, LINE_BORDER_REGEX, COLOR_REGEX, COLOR_LINE_BORDER_REGEX);
	
	private final static BorderAsValueHandler singleton = new BorderAsValueHandler();
	
	/**
	 * @return Singleton
	 */
	public static BorderAsValueHandler getInstance() {return singleton;}
	
	//private constructor only
	private BorderAsValueHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getInputValueSample()
	 */
	public String getInputValueSample() {
		return "olive 3 | 3 | raisedEtched | loweredBevel";
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
	 */
	public String getRegex() {
		return regex;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
	 */
	public Border getValue(BuildProcess process, Node node, String key,
			Object inputValue) throws BuildException {
		
		Border border = null;
		
		if (RAISED_BEVEL.equals(inputValue)) {
			border = BorderFactory.createRaisedBevelBorder();
		} else if (LOWERED_BEVEL.equals(inputValue)) {
			border = BorderFactory.createLoweredBevelBorder();
		} else if (RAISED_ETCHED.equals(inputValue)) {
			border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		} else if (LOWERED_ETCHED.equals(inputValue)) {
			border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		} else if (inputValue instanceof Long || inputValue instanceof Integer) {
			//just a line
			int input = Integer.parseInt(String.valueOf(inputValue));
			
			border = BorderFactory.createLineBorder(new Color(SystemColor.ACTIVE_CAPTION_BORDER), input);
		} else {
			//color + line
			String sValue = (String)inputValue;
			String[] parts = sValue.split(" ");
			String colorValue = null;
			int width = 1;
			for(String part : parts) {
				if (colorValue == null) {
					colorValue = part;
				} else if (part.length() > 0 && !part.equals(" ")) {
					width = Integer.parseInt(part);
				}
			}
			Color color = ColorAsValueHandler.getInstance().getValue(process, node, key, colorValue);
			border = BorderFactory.createLineBorder(color, width);
		}
		
		return border;
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Border.class;
	}

}
