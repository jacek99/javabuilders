package org.javabuilders.gtk.handler.type;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.gnome.gtk.Label;
import org.gnome.gtk.Notebook;
import org.gnome.gtk.Widget;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.IKeyValueConsumer;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

/**
 * Notebook finish processor
 * @author Jacek Furmankiewicz
 */
public class NotebookFinishProcessor implements ITypeHandlerFinishProcessor, IKeyValueConsumer {

	/**
	 * Property to set to true if the Widget is supposed to be used as a label for the following Widget, e.g. "isTabLabel=true"
	 */
	public static final String IS_TAB_LABEL = "isTabLabel";
	/**
	 * The property to set if you want to automatically create a Label widget to use as a tab label, e.g. ("tabLabel=My first tab") 
	 */
	public static final String TAB_LABEL = "tabLabel";
	
	private static final Set<String> consumed = new HashSet<String>();
	static {
		consumed.add(IS_TAB_LABEL);
		consumed.add(TAB_LABEL);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		Set<Node> nodes = current.getContentNodes(Widget.class);
		Notebook notebook  = (Notebook) current.getMainObject();
		Widget lastLabelWidget = null;
		
		for(Node node : nodes) {
			
			if (node.getProperties().containsKey(IS_TAB_LABEL)) {
				
				lastLabelWidget = (Widget) node.getMainObject();
				
			} else {
				
				Widget tabLabel = null;
				if (lastLabelWidget != null) {
					tabLabel = lastLabelWidget;
					lastLabelWidget = null;
				}
				
				Widget child = (Widget) node.getMainObject();
				if (node.getProperties().containsKey(TAB_LABEL)) {
					tabLabel = new Label(node.getStringProperty(TAB_LABEL));
				}
				notebook.appendPage(child, tabLabel);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getConsumedKeys()
	 */
	public Set<String> getConsumedKeys() {
		return consumed;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<Notebook> getApplicableClass() {
		return Notebook.class;
	}

}
