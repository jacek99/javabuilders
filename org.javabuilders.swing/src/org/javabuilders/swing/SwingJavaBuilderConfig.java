package org.javabuilders.swing;

import static org.javabuilders.swing.SwingJavaBuilder.*;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.TextArea;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Window;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.JWindow;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;

import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.ICustomCommand;
import org.javabuilders.IStringLiteralControlConfig;
import org.javabuilders.TypeDefinition;
import org.javabuilders.handler.type.FontAsValueHandler;
import org.javabuilders.handler.type.IconAsValueHandler;
import org.javabuilders.handler.type.ImageAsValueHandler;
import org.javabuilders.layout.DefaultResize;
import org.javabuilders.swing.controls.JBSeparator;
import org.javabuilders.swing.handler.SwingValidationMessageHandler;
import org.javabuilders.swing.handler.binding.BeansBindingTypeHandler;
import org.javabuilders.swing.handler.event.CommonActionListenerHandler;
import org.javabuilders.swing.handler.event.ComponentFocusListenerHandler;
import org.javabuilders.swing.handler.event.ComponentKeyListenerHandler;
import org.javabuilders.swing.handler.event.ComponentMouseListenerHandler;
import org.javabuilders.swing.handler.event.ComponentMouseMotionListenerHandler;
import org.javabuilders.swing.handler.event.ComponentMouseWheelListenerHandler;
import org.javabuilders.swing.handler.event.JFrameWindowListenerHandler;
import org.javabuilders.swing.handler.event.JTabbedPaneChangeListenerHandler;
import org.javabuilders.swing.handler.event.JTableSelectionListenerHandler;
import org.javabuilders.swing.handler.event.JTreeSelectionListenerHandler;
import org.javabuilders.swing.handler.event.WindowListenerHandler;
import org.javabuilders.swing.handler.event.background.SwingBackgroundProcessingHandler;
import org.javabuilders.swing.handler.property.AbstractButtonActionCommandHandler;
import org.javabuilders.swing.handler.property.ComponentSizeHandler;
import org.javabuilders.swing.handler.property.FrameExtendedStateHandler;
import org.javabuilders.swing.handler.property.JComponentGroupTitleHandler;
import org.javabuilders.swing.handler.property.JMenuItemAcceleratorHandler;
import org.javabuilders.swing.handler.property.JMenuItemTextHandler;
import org.javabuilders.swing.handler.property.JTextFieldActionCommandHandler;
import org.javabuilders.swing.handler.property.SwingActionTextHandler;
import org.javabuilders.swing.handler.type.ActionAsValueHandler;
import org.javabuilders.swing.handler.type.BorderAsValueHandler;
import org.javabuilders.swing.handler.type.ButtonGroupTypeHandler;
import org.javabuilders.swing.handler.type.ColorAsValueHandler;
import org.javabuilders.swing.handler.type.ContainerTypeHandler;
import org.javabuilders.swing.handler.type.JComboBoxFinishProcessor;
import org.javabuilders.swing.handler.type.JDialogTypeHandler;
import org.javabuilders.swing.handler.type.JFormattedTextFieldTypeHandler;
import org.javabuilders.swing.handler.type.JFrameTypeHandler;
import org.javabuilders.swing.handler.type.JListFinishProcessor;
import org.javabuilders.swing.handler.type.JSpiltPaneTypeHandler;
import org.javabuilders.swing.handler.type.JTabbedPaneTypeHandler;
import org.javabuilders.swing.handler.type.JTableFinishProcessor;
import org.javabuilders.swing.handler.type.SwingActionHandler;
import org.javabuilders.swing.handler.type.TableColumnTypeHandler;
import org.javabuilders.swing.handler.type.glazedlists.EventComboBoxModelTypeHandler;
import org.javabuilders.swing.handler.type.glazedlists.EventListModelTypeHandler;
import org.javabuilders.swing.handler.type.glazedlists.EventTableModelTypeHandler;
import org.javabuilders.swing.handler.type.layout.CardLayoutTypeHandler;
import org.javabuilders.swing.handler.type.layout.FlowLayoutTypeHandler;
import org.javabuilders.swing.handler.type.layout.MigLayoutHandler;
import org.javabuilders.swing.handler.type.model.DefaultComboBoxModelHandler;

import ca.odell.glazedlists.swing.EventComboBoxModel;
import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventTableModel;

public class SwingJavaBuilderConfig extends BuilderConfig implements IStringLiteralControlConfig {

	private static final Logger LOGGER = Logger.getLogger(SwingJavaBuilderConfig.class.getSimpleName());
	
	private Map<String,Integer> hScrollbars = new HashMap<String, Integer>();
	private Map<String,Integer> vScrollbars = new HashMap<String, Integer>();
	
	{
		hScrollbars.put("always", JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		hScrollbars.put("asNeeded", JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		hScrollbars.put("never", JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	
	
		vScrollbars.put("always", JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		vScrollbars.put("asNeeded", JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		vScrollbars.put("never", JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	}
	
	/**
	 * Constructor
	 */
	public SwingJavaBuilderConfig() {
		super(SwingBackgroundProcessingHandler.getInstance(),
			SwingValidationMessageHandler.getInstance(), 
			new ConfirmCommand());

		//define aliases for AWT types
		addType(
				Applet.class,
				Button.class, 
				Canvas.class, 
				Checkbox.class,
				Choice.class,
				Container.class,
				Dialog.class,
				Frame.class,				
				Label.class,
				List.class,
				Panel.class, 
				Scrollbar.class,
				ScrollPane.class, 
				TableColumn.class,
				TextArea.class,
				TextComponent.class, 
				TextField.class, 
				Window.class);
		
		//define aliases for Swing types and models
		addType(
				Box.class,
				ButtonGroup.class,
				JButton.class,
				JCheckBox.class,
				JCheckBoxMenuItem.class,
				DefaultComboBoxModel.class,
				JColorChooser.class,
				JComboBox.class,
				JDialog.class,
				JEditorPane.class,
				JFrame.class,
				JFileChooser.class,
				JFormattedTextField.class,
				JInternalFrame.class,
				JLabel.class,
				JLayeredPane.class,
				JList.class,
				JMenu.class,
				JMenuBar.class,
				JMenuItem.class,
				JPanel.class,
				JPopupMenu.class,
				JProgressBar.class,
				JRadioButton.class,
				JRadioButtonMenuItem.class,
				JScrollBar.class,
				JScrollPane.class,
				JSeparator.class,
				JSlider.class,
				JSpinner.class,
				JSplitPane.class,
				JTabbedPane.class,
				JTable.class,
				JTextArea.class,
				JTextPane.class,
				JTextField.class,
				JToolBar.class,
				JTree.class,
				JToggleButton.class,
				JViewport.class,
				JWindow.class,
				JBSeparator.class,
				TableCellEditor.class,
				TableCellRenderer.class
				);
		
		//define aliases for Swing layout managers
		addType(MigLayout.class,CardLayout.class,FlowLayout.class);
		addType("Action",SwingAction.class);
		
		//define metadata about types
		forType(AbstractButton.class)
			.localize(TEXT)
			.propertyHandler(AbstractButtonActionCommandHandler.getInstance(),CommonActionListenerHandler.getInstance());
		forType(ButtonGroup.class)
			.finishProcessor(ButtonGroupTypeHandler.getInstance())
			.ignore(Builder.CONTENT)
			.childrenOverride(true)
			.children(AbstractButton.class,0,Integer.MAX_VALUE);
		forType(Component.class)
			.ignore(LAYOUT_DATA)
			.propertyHandler(ComponentSizeHandler.getInstance(), ComponentFocusListenerHandler.getInstance(),
					ComponentKeyListenerHandler.getInstance(), ComponentMouseListenerHandler.getInstance(), 
		  		    ComponentMouseWheelListenerHandler.getInstance(), ComponentMouseMotionListenerHandler.getInstance());
		forType(Container.class)
			.ignore(Builder.CONSTRAINTS)
			.delay(TypeDefinition.DEFAULT_DELAY_WEIGHT,LayoutManager.class)  
			.finishProcessor(ContainerTypeHandler.getInstance())
			.defaultResize(DefaultResize.BOTH)
			.children(Component.class, 0,Integer.MAX_VALUE)
			.children(LayoutManager.class, 0,1)
			.children(ButtonGroup.class, 0, Integer.MAX_VALUE);
		forType(Font.class)
			.valueHandler(FontAsValueHandler.getInstance());
		forType(Frame.class).localize(TITLE)
			.delay(Integer.MAX_VALUE,ComponentSizeHandler.SIZE)
			.propertyHandler(FrameExtendedStateHandler.getInstance());
		forType(TableColumn.class).ignore(Builder.NAME)
			.localize("headerValue")
			.ignore("source")
			.children(TableCellEditor.class, 0,1)
			.children(TableCellRenderer.class,0,2)
			.children(JComboBox.class,0,1)
			.children(JTextField.class,0,1)
			.children(JCheckBox.class,0,1)
			.typeHandler(TableColumnTypeHandler.getInstance());
		forType(Window.class).localize(TITLE)
			.delay(Integer.MAX_VALUE,ComponentSizeHandler.SIZE)
			.propertyHandler(WindowListenerHandler.getInstance());
		forType(JBSeparator.class)
			.localize(TEXT)
			.defaultResize(DefaultResize.X_AXIS)
			.childrenOverride(true).children(0);
		forType(JButton.class)
			.childrenOverride(true).children(0);
		forType(JComboBox.class)
			.defaultResize(DefaultResize.X_AXIS)
			.propertyHandler(CommonActionListenerHandler.getInstance())
			.finishProcessor(new JComboBoxFinishProcessor())
			.childrenOverride(true).children(ComboBoxModel.class, 0,1);
		forType(DefaultComboBoxModel.class)
			.afterCreationProcessor(new DefaultComboBoxModelHandler());
		forType(JComponent.class)
			.localize(TOOL_TIP_TEXT, JComponentGroupTitleHandler.GROUP_TITLE,
				JTabbedPaneTypeHandler.TAB_ICON, 
				JTabbedPaneTypeHandler.TAB_TITLE, 
				JTabbedPaneTypeHandler.TAB_TOOLTIP)
			.ignore(JTabbedPaneTypeHandler.TAB_ICON, JTabbedPaneTypeHandler.TAB_TITLE, 
				JTabbedPaneTypeHandler.TAB_TOOLTIP, JTabbedPaneTypeHandler.TAB_ENABLED)
			.propertyHandler(JComponentGroupTitleHandler.getInstance());
		forType(JDialog.class)
			.typeAsMethod(JMenuBar.class, "setJMenuBar")
			.finishProcessor(JDialogTypeHandler.getInstance())
			.children(Action.class,0,Integer.MAX_VALUE)
			.children(JMenuBar.class, 0,1);
		forType(JFormattedTextField.class)
			.defaultResize(DefaultResize.X_AXIS)
			.typeHandler(JFormattedTextFieldTypeHandler.getInstance());
		forType(JFrame.class)
			.typeAsMethod(JMenuBar.class, "setJMenuBar")
			.finishProcessor(JFrameTypeHandler.getInstance())
			.propertyHandler(JFrameWindowListenerHandler.getInstance())
			.children(Action.class, 0,Integer.MAX_VALUE)
			.children(JMenuBar.class, 0,1);
		forType(JLabel.class)
			.localize(TEXT)
			.childrenOverride(true).children(0);
		forType(JList.class)
			.defaultResize(DefaultResize.BOTH)
			.finishProcessor(new JListFinishProcessor())
			.childrenOverride(true).children(ListModel.class,0,1);
		forType(JMenu.class)
			.children(ButtonGroup.class,0,Integer.MAX_VALUE);
		forType(JMenuBar.class)
			.allowParent(JFrame.class,JDialog.class);
		forType(JMenuItem.class)
			.localize(ACCELERATOR)
			.propertyHandler(JMenuItemTextHandler.getInstance(),JMenuItemAcceleratorHandler.getInstance())
			.children(JMenuItem.class,0,Integer.MAX_VALUE);
		forType(JPanel.class)
			.defaultResize(DefaultResize.BOTH);
		forType(JPopupMenu.class)
			.children(JMenuItem.class,0,Integer.MAX_VALUE);
		forType(JProgressBar.class)
			.defaultResize(DefaultResize.X_AXIS)
			.childrenOverride(true).children(0);
		forType(JSeparator.class)
			.defaultResize(DefaultResize.BOTH)
			.childrenOverride(true).children(0);
		forType(JScrollPane.class)
			.defaultResize(DefaultResize.BOTH)	
			.asMapped("verticalScrollBarPolicy", vScrollbars)
			.asMapped("horizontalScrollBarPolicy", hScrollbars)
			.typeAsMethod(Component.class, "setViewportView")
			.propertyAlias("verticalScrollBarPolicy","vScrollBar").propertyAlias("horizontalScrollBarPolicy", "hScrollBar")
			.childrenOverride(true)
			.children(Component.class,0,1);
		forType(JSlider.class)
			.defaultResize(DefaultResize.BOTH)
			.childrenOverride(true).children(0);
		forType(JSpinner.class)
			.childrenOverride(true).children(0);
		forType(JSplitPane.class)
			.defaultResize(DefaultResize.BOTH)
			.afterCreationProcessor(JSpiltPaneTypeHandler.getInstance())
			.finishProcessor(JSpiltPaneTypeHandler.getInstance())
			.childrenOverride(true)
			.children(Component.class, 0, 2);
		forType(JTabbedPane.class)
			.finishProcessor(JTabbedPaneTypeHandler.getInstance())
			.defaultResize(DefaultResize.BOTH)
			.propertyHandler(JTabbedPaneChangeListenerHandler.getInstance());
		forType(JTable.class)
			.finishProcessor(JTableFinishProcessor.getInstance())
			.propertyConstants("selectionMode", ListSelectionModel.class)
			.typeAsMethod(TableModel.class, "setModel")
			.propertyHandler(JTableSelectionListenerHandler.getInstance())
			.children(TableColumn.class, 0, Integer.MAX_VALUE)
			.children(TableModel.class,0,1);
		forType(JTextComponent.class)
			.defaultResize(DefaultResize.BOTH);
		forType(JTextField.class)
			.defaultResize(DefaultResize.X_AXIS)
			.propertyAlias("horizontalAlignment","hAlign")
			.propertyHandler(JTextFieldActionCommandHandler.getInstance(),CommonActionListenerHandler.getInstance())
			.childrenOverride(true).children(0);
		forType(JToggleButton.class)
			.childrenOverride(true).children(0);
		forType(JTree.class)
			.defaultResize(DefaultResize.BOTH)
			.propertyHandler(JTreeSelectionListenerHandler.getInstance());
		forType(TableCellRenderer.class)
			.ignore(TableColumnTypeHandler.FOR_HEADER);

		forType(MigLayout.class)
			.typeHandler(MigLayoutHandler.getInstance());
		forType(CardLayout.class)
			.ignore(Builder.CONTENT,Builder.NAME)
			.finishProcessor(CardLayoutTypeHandler.getInstance());
		forType(FlowLayout.class)
			.ignore(Builder.CONTENT,Builder.NAME)
			.finishProcessor(FlowLayoutTypeHandler.getInstance());
		
		forType(Action.class)
			.valueHandler(ActionAsValueHandler.getInstance());
		forType(Border.class)
			.valueHandler(BorderAsValueHandler.getInstance());
		forType(Color.class)
		.valueHandler(ColorAsValueHandler.getInstance());
		forType(Icon.class)
			.valueHandler(IconAsValueHandler.getInstance());
		forType(Image.class)
			.valueHandler(ImageAsValueHandler.getInstance());
		forType(SwingAction.class)
			.localize(SwingJavaBuilder.TEXT, SwingJavaBuilder.TOOL_TIP_TEXT, SwingActionHandler.LONG_DESCRIPTION)
			.propertyAlias(SwingActionHandler.LONG_DESCRIPTION, SwingActionHandler.LONG_DESC)
			.typeHandler(SwingActionHandler.getInstance())
			.propertyHandler(CommonActionListenerHandler.getInstance(),SwingActionTextHandler.getInstance())
			.childrenOverride(true).children(0);
		
		setStringLiteralControlSuffix("Label"); 
		
		initializeBeansBinding();
		initializeGlazedLists();

	}

	/**
	 * Adds GlazedLists support, if in path
	 */
	private void initializeGlazedLists() {
		//allow GlazedLists as an option
		try {
			Class.forName("ca.odell.glazedlists.BasicEventList");
			//add GlazedLists support if in path
			addType(EventListModel.class,EventTableModel.class, EventComboBoxModel.class);
			forType(EventListModel.class)
				.typeHandler(new EventListModelTypeHandler());
			forType(EventComboBoxModel.class)
				.typeHandler(new EventComboBoxModelTypeHandler());
			forType(EventTableModel.class)
				.typeHandler(new EventTableModelTypeHandler());
			
			
		} catch (ClassNotFoundException e) {
			LOGGER.info("GlazedLists not found in path, GlazedLists support not initialized.");
		}
	}

	/**
	 * Adds BeansBinding support, if in path
	 */
	private void initializeBeansBinding() {
		//allow alternate implementations of data binding for Swing
		try {
			Class.forName("org.jdesktop.beansbinding.Bindings");
			//Beans Binding was found in path, use default binding handler
			forType(BeansBindingTypeHandler.getInstance().getApplicableClass()).typeHandler(BeansBindingTypeHandler.getInstance());
		} catch (ClassNotFoundException e) {
			LOGGER.info("Beans Binding (JSR 295) not found in path, default BeansBindingTypeHandler not initialized.");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.BuilderConfig#getPropertyChangeSupport(java.lang.Object)
	 */
	@Override
	public PropertyChangeSupport createPropertyChangeSupport(Object source) {
		return new org.jdesktop.swingworker.SwingPropertyChangeSupport(source);
	}

	private static  class ConfirmCommand implements ICustomCommand<Boolean>  {

		public Boolean process(BuildResult result, Object source) {
			Component c = null;
			if (result.getCaller() instanceof Component) {
				c = (Component) result.getCaller();
			}
			int value = JOptionPane.showConfirmDialog(c, Builder.getResourceBundle().getString("question.areYouSure"), 
					Builder.getResourceBundle().getString("title.confirmation"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if (value == JOptionPane.YES_OPTION) {
				return true;
			} else {
				return false;
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IStringLiteralControlConfig#getStringLiteralControlPrefix()
	 */
	public String getStringLiteralControlPrefix() {
		return (String) getCustomProperties().get(PROPERY_STRING_LITERAL_CONTROL_PREFIX);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IStringLiteralControlConfig#getStringLiteralControlSuffix()
	 */
	public String getStringLiteralControlSuffix() {
		return (String) getCustomProperties().get(PROPERY_STRING_LITERAL_CONTROL_SUFFIX);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IStringLiteralControlConfig#setStringLiteralControlPrefix(java.lang.String)
	 */
	public void setStringLiteralControlPrefix(String prefix) {
		getCustomProperties().put(PROPERY_STRING_LITERAL_CONTROL_PREFIX, prefix);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IStringLiteralControlConfig#setStringLiteralControlSuffix(java.lang.String)
	 */
	public void setStringLiteralControlSuffix(String suffix) {
		getCustomProperties().put(PROPERY_STRING_LITERAL_CONTROL_SUFFIX, suffix);
	}


}
