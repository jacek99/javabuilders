/**
 * 
 */
package org.javabuilders.gtk.handler.type;

import java.util.HashMap;
import java.util.Map;

import org.gnome.gtk.FileChooserAction;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;

/**
 * FileChooserAction value handler
 * @author Jacek Furmankiewicz
 *
 */
public class FileChooserActionAsValue implements ITypeAsValueHandler<FileChooserAction> {

	private static final String regex = "CREATE_FOLDER|OPEN|SAVE|SELECT_FOLDER|createFolder|open|save|selectFolder";
	private static final Map<String,FileChooserAction> values = new HashMap<String, FileChooserAction>();
	
	static {
		values.put("CREATE_FOLDER", FileChooserAction.CREATE_FOLDER);
		values.put("createFolder", FileChooserAction.CREATE_FOLDER);
		values.put("OPEN", FileChooserAction.OPEN);
		values.put("open", FileChooserAction.OPEN);
		values.put("SAVE", FileChooserAction.SAVE);
		values.put("save", FileChooserAction.SAVE);
		values.put("SELECT_FOLDER", FileChooserAction.SELECT_FOLDER);
		values.put("selectFolder", FileChooserAction.SELECT_FOLDER);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getInputValueSample()
	 */
	public String getInputValueSample() {
		return regex;
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
	public FileChooserAction getValue(BuildProcess process, Node node, String key, Object inputValue) throws BuildException {
		return values.get(inputValue);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<FileChooserAction> getApplicableClass() {
		return FileChooserAction.class;
	}

}
