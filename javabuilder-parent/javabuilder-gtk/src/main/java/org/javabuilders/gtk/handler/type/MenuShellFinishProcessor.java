package org.javabuilders.gtk.handler.type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.gnome.gtk.Menu;
import org.gnome.gtk.MenuBar;
import org.gnome.gtk.MenuItem;
import org.gnome.gtk.MenuShell;
import org.gnome.gtk.Widget;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.gtk.GtkConstants;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

/**
 * MenuBar handler
 * @author Jacek Furmankiewicz
 *
 */
public class MenuShellFinishProcessor implements ITypeHandlerFinishProcessor {

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {

		
		MenuShell bar = (MenuShell) current.getMainObject();

		if (bar instanceof MenuBar) {
			Set<Node> nodes = current.getContentNodes(MenuItem.class);
			for(Node node : nodes) {
				processMenuItem(bar, node);
			}
		}
		
		current.getCustomProperties().put(GtkConstants.INTERNAL_LAYOUT_HANDLED, true);

	}
	
	private void processMenuItem(MenuShell parent, Node node) {


		
		MenuItem mi = (MenuItem) node.getMainObject();
		parent.append(mi);

		Set<Node> items = node.getContentNodes(Menu.class);
		for(Node item : items) {
			Menu menu = (Menu) item.getMainObject();
			menu = new Menu();
			menu.append(new MenuItem("TEST"));
			mi.setSubmenu(menu);
		}
		

		
	}

}
