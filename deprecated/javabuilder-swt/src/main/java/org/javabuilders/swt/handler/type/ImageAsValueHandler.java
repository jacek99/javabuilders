/**
 * 
 */
package org.javabuilders.swt.handler.type;

import java.io.File;
import java.io.InputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;
import org.javabuilders.swt.SwtBuilderUtils;
import org.javabuilders.util.BuilderUtils;

/**
 * Handler for creating images that are arguments into methods
 * @author Jacek Furmankiewicz
 */
public class ImageAsValueHandler implements ITypeAsValueHandler<Image> {

	private static final ImageAsValueHandler singleton = new ImageAsValueHandler();
	
	/**
	 * @return Singleton
	 */
	public static ImageAsValueHandler getInstance() {return singleton;}
	
	/**
	 * Constructor
	 */
	private ImageAsValueHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getInputValueSample()
	 */
	public String getInputValueSample() {
		return "images/swt.png";
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
	 */
	public String getRegex() {
		return ".+";
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
	 */
	public Image getValue(BuildProcess process, Node node, String key,
			Object inputValue) throws BuildException {
		Shell shell = SwtBuilderUtils.getShell(node);
		
		String name = String.valueOf(inputValue);
		InputStream stream = BuilderUtils.getResourceInputStream(process, name);
		
		Image image = null;
		if (stream != null) {
			 image = new Image(shell.getDisplay(),stream);
		} else {
			//not an image in the classpath...maybe one on the physical disk?
			File file = new File(name);
			if (file.exists()) {
				image = new Image(shell.getDisplay(),name);
			} else {
				throw new BuildException("Unable to find Image \"{0}\" in neither the classpath nor the disk.",inputValue);
			}
		}
		return image;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Image.class;
	}

}
