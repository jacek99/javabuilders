package org.javabuilders.swing.plugin.glazedlists;

import org.javabuilders.BuilderConfig;
import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.plugin.glazedlists.handler.EventComboBoxModelTypeHandler;
import org.javabuilders.swing.plugin.glazedlists.handler.EventListModelTypeHandler;
import org.javabuilders.swing.plugin.glazedlists.handler.EventTableModelTypeHandler;

import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.swing.EventComboBoxModel;
import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventTableModel;

/**
 * Init for GlazedLists plugin
 */
public class SwingGlazedListsConfig {
	
	private static boolean initDone = false;
	
	/**
	 * Adds GlazedLists support
	 */
	public static void init() {
		if (!initDone) {
			init(SwingJavaBuilder.getConfig());
			initDone = true;
		}
	}

	/**
	 * Adds GlazedLists support
	 */
	public static void init(BuilderConfig config) {
		config.addType(EventListModel.class, EventComboBoxModel.class,EventTableModel.class,
				TextFilterator.class);
		
		config.forType(EventListModel.class).typeHandler(new EventListModelTypeHandler());
		config.forType(EventComboBoxModel.class).typeHandler(new EventComboBoxModelTypeHandler());
		config.forType(EventTableModel.class).asList(EventTableModelTypeHandler.COLUMNS, EventTableModelTypeHandler.SORT_BY)
				.typeHandler(new EventTableModelTypeHandler());
	}
}
