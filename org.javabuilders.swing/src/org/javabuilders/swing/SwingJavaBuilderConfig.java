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
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.table.TableColumn;
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
import org.javabuilders.swing.handler.event.AbstractButtonActionListenerHandler;
import org.javabuilders.swing.handler.event.ComponentFocusListenerHandler;
import org.javabuilders.swing.handler.event.ComponentKeyListenerHandler;
import org.javabuilders.swing.handler.event.ComponentMouseListenerHandler;
import org.javabuilders.swing.handler.event.ComponentMouseMotionListenerHandler;
import org.javabuilders.swing.handler.event.ComponentMouseWheelListenerHandler;
import org.javabuilders.swing.handler.event.JComboBoxActionListenerHandler;
import org.javabuilders.swing.handler.event.JFrameWindowListenerHandler;
import org.javabuilders.swing.handler.event.JTabbedPaneChangeListenerHandler;
import org.javabuilders.swing.handler.event.JTableListSelectionListenerHandler;
import org.javabuilders.swing.handler.event.JTreeSelectionListenerHandler;
import org.javabuilders.swing.handler.event.SwingActionOnActionHandler;
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
import org.javabuilders.swing.handler.type.JDialogTypeHandler;
import org.javabuilders.swing.handler.type.JFormattedTextFieldTypeHandler;
import org.javabuilders.swing.handler.type.JFrameTypeHandler;
import org.javabuilders.swing.handler.type.JSpiltPaneTypeHandler;
import org.javabuilders.swing.handler.type.JTabbedPaneTypeHandler;
import org.javabuilders.swing.handler.type.JTableTypeHandler;
import org.javabuilders.swing.handler.type.SwingActionHandler;
import org.javabuilders.swing.handler.type.layout.CardLayoutTypeHandler;
import org.javabuilders.swing.handler.type.layout.FlowLayoutTypeHandler;
import org.javabuilders.swing.handler.type.layout.MigLayoutHandler;
import org.javabuilders.swing.handler.type.model.DefaultComboBoxModelHandler;

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

		//allow alternate implementations of data binding for Swing
		try {
			Class.forName("org.jdesktop.beansbinding.Bindings");
			//Beans Binding was found in path, use default binding handler
			addTypeHandler(BeansBindingTypeHandler.getInstance());
		} catch (ClassNotFoundException e) {
			LOGGER.info("Beans Binding (JSR 295) not found in path, default BeansBindingTypeHandler not initialized.");
		}
		
		
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
				JBSeparator.class
				);
		
		//define aliases for Swing layout managers
		addType(MigLayout.class,CardLayout.class,FlowLayout.class);
		addType("Action",SwingAction.class);
		
		//define metadata about types
		forType(Container.class)
			.ignore(Builder.CONSTRAINTS)
			.delay(TypeDefinition.DEFAULT_DELAY_WEIGHT,LayoutManager.class)  
			.finishProcessor(ContainerTypeHandler.getInstance())
			.defaultResize(DefaultResize.BOTH);

		forType(AbstractButton.class).localize(TEXT);
		forType(ButtonGroup.class).finishProcessor(ButtonGroupTypeHandler.getInstance()).ignore(Builder.CONTENT);
		forType(Component.class).ignore(LAYOUT_DATA);
		forType(Font.class).valueHandler(FontAsValueHandler.getInstance());
		forType(Frame.class).localize(TITLE)
			.delay(Integer.MAX_VALUE,ComponentSizeHandler.SIZE);
		forType(TableColumn.class).ignore(Builder.NAME).localize("headerValue");
		forType(Window.class).localize(TITLE)
			.delay(Integer.MAX_VALUE,ComponentSizeHandler.SIZE);
		
		forType(JComboBox.class).defaultResize(DefaultResize.X_AXIS);
		forType(DefaultComboBoxModel.class).afterCreationProcessor(new DefaultComboBoxModelHandler());
		forType(JComponent.class)
			.localize(TOOL_TIP_TEXT, JComponentGroupTitleHandler.GROUP_TITLE,
				JTabbedPaneTypeHandler.TAB_ICON, 
				JTabbedPaneTypeHandler.TAB_TITLE, 
				JTabbedPaneTypeHandler.TAB_TOOLTIP)
			.ignore(JTabbedPaneTypeHandler.TAB_ICON, JTabbedPaneTypeHandler.TAB_TITLE, 
				JTabbedPaneTypeHandler.TAB_TOOLTIP, JTabbedPaneTypeHandler.TAB_ENABLED);
		forType(JDialog.class).typeAsMethod(JMenuBar.class, "setJMenuBar").finishProcessor(JDialogTypeHandler.getInstance());
		forType(JFormattedTextField.class).defaultResize(DefaultResize.X_AXIS);
		forType(JLabel.class).localize(TEXT);
		forType(JList.class).defaultResize(DefaultResize.BOTH);
		forType(JMenuBar.class).allowParent(JFrame.class,JDialog.class);
		forType(JMenuItem.class).localize(ACCELERATOR);
		forType(JFrame.class).typeAsMethod(JMenuBar.class, "setJMenuBar").finishProcessor(JFrameTypeHandler.getInstance());
		forType(JPanel.class).defaultResize(DefaultResize.BOTH);
		forType(JProgressBar.class).defaultResize(DefaultResize.X_AXIS);
		forType(JSeparator.class).defaultResize(DefaultResize.BOTH);
		forType(JScrollPane.class).defaultResize(DefaultResize.BOTH)	.asMapped("verticalScrollBarPolicy", vScrollbars)
			.asMapped("horizontalScrollBarPolicy", hScrollbars).typeAsMethod(Component.class, "setViewportView")
			.propertyAlias("verticalScrollBarPolicy","vScrollBar").propertyAlias("horizontalScrollBarPolicy", "hScrollBar");
		forType(JSlider.class).defaultResize(DefaultResize.BOTH);
		forType(JSplitPane.class).defaultResize(DefaultResize.BOTH)
			.afterCreationProcessor(JSpiltPaneTypeHandler.getInstance()).finishProcessor(JSpiltPaneTypeHandler.getInstance());
		forType(JTabbedPane.class).finishProcessor(JTabbedPaneTypeHandler.getInstance()).defaultResize(DefaultResize.BOTH);
		forType(JTable.class).finishProcessor(JTableTypeHandler.getInstance())
			.propertyConstants("selectionMode", ListSelectionModel.class);
		forType(JTextComponent.class).defaultResize(DefaultResize.BOTH);
		forType(JTextField.class).defaultResize(DefaultResize.X_AXIS)
			.propertyAlias("horizontalAlignment","hAlign");
		
		forType(CardLayout.class).ignore(Builder.CONTENT,Builder.NAME).finishProcessor(CardLayoutTypeHandler.getInstance());
		forType(FlowLayout.class).ignore(Builder.CONTENT,Builder.NAME).finishProcessor(FlowLayoutTypeHandler.getInstance());
		
		forType(Color.class).valueHandler(ColorAsValueHandler.getInstance());
		forType(Icon.class).valueHandler(IconAsValueHandler.getInstance());
		forType(Image.class).valueHandler(ImageAsValueHandler.getInstance());
		forType(Border.class).valueHandler(BorderAsValueHandler.getInstance());
		
		forType(SwingAction.class).localize(SwingJavaBuilder.TEXT, SwingJavaBuilder.TOOL_TIP_TEXT, SwingActionHandler.LONG_DESCRIPTION)
				.propertyAlias(SwingActionHandler.LONG_DESCRIPTION, SwingActionHandler.LONG_DESC);
		forType(Action.class).valueHandler(ActionAsValueHandler.getInstance());
		
		forType(JBSeparator.class).localize(TEXT);
		
		//define custom type handlers
		addTypeHandler(MigLayoutHandler.getInstance());
		addTypeHandler(SwingActionHandler.getInstance());
		addTypeHandler(JFormattedTextFieldTypeHandler.getInstance());
				
		//define custom property handlers
		addPropertyHandler(ComponentSizeHandler.getInstance())
			.addPropertyHandler(FrameExtendedStateHandler.getInstance())
			.addPropertyHandler(JMenuItemTextHandler.getInstance())
			.addPropertyHandler(JMenuItemAcceleratorHandler.getInstance())
			.addPropertyHandler(JComponentGroupTitleHandler.getInstance())
			.addPropertyHandler(AbstractButtonActionCommandHandler.getInstance())
			.addPropertyHandler(JTextFieldActionCommandHandler.getInstance());
		
		//define property handlers for events
		addPropertyHandler(AbstractButtonActionListenerHandler.getInstance())
			.addPropertyHandler(ComponentFocusListenerHandler.getInstance())
			.addPropertyHandler(ComponentKeyListenerHandler.getInstance())
			.addPropertyHandler(ComponentMouseListenerHandler.getInstance())
  		    .addPropertyHandler(ComponentMouseWheelListenerHandler.getInstance())
		    .addPropertyHandler(ComponentMouseMotionListenerHandler.getInstance())
		    .addPropertyHandler(JComboBoxActionListenerHandler.getInstance())
		    .addPropertyHandler(JTreeSelectionListenerHandler.getInstance())
		    .addPropertyHandler(JTableListSelectionListenerHandler.getInstance())
		    .addPropertyHandler(WindowListenerHandler.getInstance())
		    .addPropertyHandler(JFrameWindowListenerHandler.getInstance())
		    .addPropertyHandler(JTabbedPaneChangeListenerHandler.getInstance())
		    .addPropertyHandler(SwingActionOnActionHandler.getInstance())
		    .addPropertyHandler(SwingActionTextHandler.getInstance())
		    .addPropertyHandler(JTextFieldActionCommandHandler.getInstance());
	
		//define which object types should be treated as named and based on what property value
		addNamedObjectCriteria(Component.class,"name");
		
		setStringLiteralControlSuffix("Label"); 
		
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.BuilderConfig#getPropertyChangeSupport(java.lang.Object)
	 */
	@Override
	public PropertyChangeSupport createPropertyChangeSupport(Object source) {
		return new SwingPropertyChangeSupport(source);
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
