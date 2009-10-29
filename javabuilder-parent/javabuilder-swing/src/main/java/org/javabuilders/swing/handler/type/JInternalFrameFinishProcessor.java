package org.javabuilders.swing.handler.type;

import java.util.Map;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

/**
 * JInternalFrame finish processor
 * @author Jacek Furmankiewicz
 */
public class JInternalFrameFinishProcessor implements ITypeHandlerFinishProcessor {

	public void finish(BuilderConfig config, BuildProcess process, Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		JInternalFrame f = (JInternalFrame) current.getMainObject();
		f.pack();
	}

}
