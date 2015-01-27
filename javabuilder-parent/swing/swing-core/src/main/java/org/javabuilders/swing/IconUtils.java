package org.javabuilders.swing;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;

/**
 * General utilities for dealing with Icon objects
 * @author Jacek Furmankiewicz
 */
public class IconUtils {

	/**
	 * Gets the Icon object from the defined key
	 * @param result Build Result
	 * @param node Node
	 * @param key Key
	 * @return Icon
	 */
	public static Icon getIcon(BuildProcess result, Node node, String key) throws BuildException {
		String path = String.valueOf(node.getProperties().get(key));
		return getIcon(result,path);
	}
	
	/**
	 * Gets the Icon object from the defined key
	 * @param result Build Result
	 * @param path Icon path
	 * @return Icon
	 */
	public static Icon getIcon(BuildProcess result, String path) throws BuildException {
		java.net.URL imgURL = result.getCaller().getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL);
	    } else {
	    	throw new BuildException("Unable to find Icon defined by path: " + path);
	    }
	}
	
}
