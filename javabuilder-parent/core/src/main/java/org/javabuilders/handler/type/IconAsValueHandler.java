/**
 * 
 */
package org.javabuilders.handler.type;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;
import org.javabuilders.util.BuilderUtils;

/**
 * Handler for all Icon objects
 * @author Jacek Furmankiewicz
 *
 */
public class IconAsValueHandler implements ITypeAsValueHandler<Icon> {

	private final static IconAsValueHandler singleton = new IconAsValueHandler();
	
	public static IconAsValueHandler getInstance() {return singleton;}
	public static final String IMAGE_REGEX = ".+"; //all are allowed, as path could be a resource key name
	
	//private constructor only
	private IconAsValueHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getInputValueSample()
	 */
	public String getInputValueSample() {
		return "images/save48x48.png";
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
	 */
	public String getRegex() {
		//valid icon path : thanks to regexlib.com
		//http://regexlib.com/REDetails.aspx?regexp_id=1457
		return IMAGE_REGEX; 
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
	 */
	public Icon getValue(BuildProcess process, Node node, String key,
			Object inputValue) throws BuildException {
		String path = (String)inputValue;
		URL imgURL = BuilderUtils.getResourceURL(process, path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL);
	    } else {
	    	throw new BuildException("Unable to find Icon defined by path: " + path);
	    }
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Icon.class;
	}

}
