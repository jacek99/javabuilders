/**
 * 
 */
package org.javabuilders.handler.type;

import java.awt.Component;
import java.awt.Font;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;

/**
 * Handles methods where the parameter is a Font
 * @author Jacek Furmankiewic
 *
 */
public class FontAsValueHandler implements ITypeAsValueHandler<Font> {

	public static final String REGEX = ".*";
	
	private static final String REGEX_ITALIC = "italic";
	private static final String REGEX_BOLD = "bold";
	private static final String REGEX_SIZE= "[0-9]{1,3}pt";
	
	public final static FontAsValueHandler singleton = new FontAsValueHandler();
	
	/**
	 * Constructor
	 */
	private FontAsValueHandler() {}
	
	/**
	 * @return Singleton
	 */
	public static FontAsValueHandler getInstance() {return singleton;}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getInputValueSample()
	 */
	public String getInputValueSample() {
		return "italic 12pt Arial";
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
	 */
	public String getRegex() {
		return REGEX;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
	 */
	public Font getValue(BuildProcess process, Node node, String key,
			Object inputValue) throws BuildException {
		
		Font font = null;
		int size = 12;
		String name = "Arial";
		
		//try to get default font property
		if (node.getMainObject() instanceof Component) {
			Component c= (Component) node.getMainObject();
			size = c.getFont().getSize();
			name = c.getFont().getName();
		}
		
		
		boolean italic = false;
		boolean bold = false;
		
		String sValue = String.valueOf(inputValue);
		String[] parts = sValue.split("\\s");
		for(String part : parts) {
			if (part.matches(REGEX_BOLD)) {
				bold = true;
			} else if (part.matches(REGEX_ITALIC)) {
				italic = true;
			} else if (part.matches(REGEX_SIZE)) {
				size = Integer.parseInt(part.substring(0,part.length() - 2));
			} else {
				name = part;
			}
		}
		
		int style = Font.PLAIN;
		if (italic == true) {
			style = Font.ITALIC;
		}
		if (bold == true) {
			style = style | Font.BOLD;
		}
		
		font = new Font(name,style,size);
		return font;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Font.class;
	}

}

