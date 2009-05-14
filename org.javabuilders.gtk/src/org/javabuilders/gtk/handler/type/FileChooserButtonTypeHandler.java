package org.javabuilders.gtk.handler.type;

import java.util.Map;

import org.gnome.gtk.FileChooserAction;
import org.gnome.gtk.FileChooserButton;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.gtk.GtkConstants;
import org.javabuilders.handler.AbstractTypeHandler;

/**
 * FileChooserButton handler
 * @author Jacek Furmankiewicz
 */
public class FileChooserButtonTypeHandler extends AbstractTypeHandler {

	/**
	 * Constructor
	 */
	public FileChooserButtonTypeHandler() {
		super(GtkConstants.ACTION,GtkConstants.TITLE);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> typeDefinition) throws BuildException {
		
		String title = (String) typeDefinition.get(GtkConstants.TITLE);
		String action = (String) typeDefinition.get(GtkConstants.ACTION);
		if (action == null) {
			action = "OPEN";
		}
		
		FileChooserAction actionInstance = FileChooserAction.SELECT_FOLDER;
		
		FileChooserButton instance = new FileChooserButton(title,actionInstance);
		return useExistingInstance(config, process, parent, key, typeDefinition, instance);
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> typeDefinition, Object instance) throws BuildException {
		
		Node node = new Node(parent,key,typeDefinition,instance);
		return node;
		
	}

	public Class<FileChooserButton> getApplicableClass() {
		return FileChooserButton.class;
	}

}
