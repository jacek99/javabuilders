package org.javabuilders.swt.handler.type;

import java.util.Map;
import java.util.Set;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

/**
 * CTabFolder finish processor
 * @author Jacek Furmankiewicz
 *
 */
public class CTabFolderFinishProcessor implements ITypeHandlerFinishProcessor {

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process, Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		CTabFolder tab = (CTabFolder) current.getMainObject();
		//select first folder automatically
		Set<CTabItem> tabItems = current.getContentObjects(CTabItem.class);
		for(CTabItem item : tabItems) {
			tab.setSelection(item);
			break;
		}
	}

}
