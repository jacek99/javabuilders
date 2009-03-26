/**
 * 
 */
package org.javabuilders.swing.handler.type;

import java.awt.Component;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;
import org.javabuilders.swing.IconUtils;
import org.javabuilders.swing.SwingJavaBuilder;

/**
 * Handles JTabbedPane and creating tab pages
 * @author Jacek Furmankiewicz
 */
public class JTabbedPaneTypeHandler implements ITypeHandlerFinishProcessor {

	private static final JTabbedPaneTypeHandler singleton = new JTabbedPaneTypeHandler();
	
	public final static String TAB_TITLE = "tabTitle";
	public final static String TAB_ICON = "tabIcon";
	public final static String TAB_TOOLTIP ="tabToolTip";
	public final static String TAB_ENABLED ="tabEnabled";
	
	/**
	 * @return Singleton
	 */
	public static JTabbedPaneTypeHandler getInstance() {return singleton;}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerPostProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		
		JTabbedPane tabs = (JTabbedPane) current.getMainObject();
		Node content = current.getChildNode(Builder.CONTENT);
		if (content != null) {
			current.getCustomProperties().put(SwingJavaBuilder.PROPERTY_IGNORE_LAYOUT_MANAGER, Boolean.TRUE);
			
			int tabCounter = 0;
			for(Node child : content.getChildNodes()) {
				
				tabCounter++;
				Component c = (Component) child.getMainObject();
				
				String tabTitle = child.getStringProperty(TAB_TITLE);
				if (tabTitle == null) {
					//no title? put the component name as default
					tabTitle = c.getName();
					
					if (tabTitle == null) {
						//still no title? create default tab title then...
						tabTitle = String.format("tab%s",tabCounter);
					}
				}
				
				String tabIcon = child.getStringProperty(TAB_ICON);
				Icon icon = null;
				if (tabIcon != null) {
					icon = IconUtils.getIcon(process, tabIcon);
				}
				String tabTooltip = child.getStringProperty(TAB_TOOLTIP);
				tabs.addTab(tabTitle, icon, c, tabTooltip);
				
				String tabEnabled = child.getStringProperty(TAB_ENABLED);
				if (Builder.BOOLEAN_FALSE.equals(tabEnabled)) {
					tabs.setEnabledAt(tabs.getTabCount() - 1, false);
				}
				
			}
		}
		
	}

}
