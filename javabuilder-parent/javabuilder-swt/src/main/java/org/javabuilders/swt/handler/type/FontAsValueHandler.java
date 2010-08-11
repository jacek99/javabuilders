/**
 * 
 */
package org.javabuilders.swt.handler.type;



import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;
import org.javabuilders.swt.cache.FontCache;

/**
 * Font handler. Caches the fonts to avoid re-creating them
 * @author Jacek Furmankiewicz
 *
 */
public class FontAsValueHandler implements ITypeAsValueHandler<Font> {

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getInputValueSample()
	 */
	public String getInputValueSample() {
		return "Monospaced 12 bold";
	}

	public String getRegex() {
		return FontCache.REGEX;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
	 */
	public Font getValue(BuildProcess process, Node node, String key,
			Object inputValue) throws BuildException {
		Font font = FontCache.getFont(Display.getCurrent(), String.valueOf(inputValue));
		return font;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<Font> getApplicableClass() {
		return Font.class;
	}

}
