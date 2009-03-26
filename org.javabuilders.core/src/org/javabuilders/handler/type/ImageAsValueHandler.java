/**
 * 
 */
package org.javabuilders.handler.type;

import java.awt.Component;
import java.awt.Image;
import java.net.URL;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderUtils;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;

/**
 * Handles Image objects in a fashion similar to Icon
 * @author Jacek Furmankiewicz
 *
 */
public class ImageAsValueHandler implements ITypeAsValueHandler<Image> {

	private static final ImageAsValueHandler singleton = new ImageAsValueHandler();
	
	/**
	 * @return Singleton
	 */
	public static ImageAsValueHandler getInstance() {return singleton;}
	
	private ImageAsValueHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getInputValueSample()
	 */
	public String getInputValueSample() {return "images/save48x48.png";}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
	 */
	public String getRegex() {
		return IconAsValueHandler.IMAGE_REGEX;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
	 */
	public Image getValue(BuildProcess process, Node node, String key,
			Object inputValue) throws BuildException {
		String path = (String)inputValue;
		
		URL imgURL = BuilderUtils.getResourceURL(process, path); 
	    if (imgURL != null) {
	    	
			Object c = node.getMainObject();
			if (c instanceof Component) {
				Component comp = (Component) c;
				return comp.getToolkit().getImage(imgURL);
			} else {
				throw new BuildException("Unable to obtain Toolkit required for Image: " + path);
			}
	        
	    } else {
	    	throw new BuildException("Unable to find Image defined by path: " + path);
	    }
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Image.class;
	}

}
