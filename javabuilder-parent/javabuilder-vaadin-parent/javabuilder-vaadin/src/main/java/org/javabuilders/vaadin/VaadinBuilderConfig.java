package org.javabuilders.vaadin;

import org.javabuilders.BuilderConfig;
import org.javabuilders.ICustomCommand;
import org.javabuilders.IStringLiteralControlConfig;
import org.javabuilders.event.IBackgroundProcessingHandler;
import org.javabuilders.handler.validation.IValidationMessageHandler;

import com.vaadin.data.Container;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.terminal.gwt.client.ui.Action;
import com.vaadin.terminal.gwt.client.ui.Icon;
import com.vaadin.terminal.gwt.client.ui.SimpleFocusablePanel;
import com.vaadin.terminal.gwt.client.ui.TreeAction;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Select;
import com.vaadin.ui.Slider;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Window;

/**
 * Main configuration for the Vaadin domain
 */
public class VaadinBuilderConfig extends BuilderConfig implements IStringLiteralControlConfig 
{

	public VaadinBuilderConfig(IBackgroundProcessingHandler backgroundProcessingHandler,
			IValidationMessageHandler validationMessageHandler, ICustomCommand<Boolean> confirmCommand) {
		super(backgroundProcessingHandler, validationMessageHandler, confirmCommand);
		
		//types
		addType(Accordion.class,
				Action.class,
				Button.class,
				CheckBox.class,
				ComboBox.class,
				Container.class,
				CustomComponent.class,
				DateField.class,
				Form.class,
				FormLayout.class,
				GridLayout.class,
				HierarchicalContainer.class,
				HorizontalLayout.class,
				Icon.class,
				IndexedContainer.class,
				InlineDateField.class,
				Label.class,
				Link.class,
				ListSelect.class,
				LoginForm.class,
				MenuBar.class,
				NativeButton.class,
				NativeSelect.class,
				Panel.class,
				PopupDateField.class,
				PopupView.class,
				ProgressIndicator.class,
				RichTextArea.class,
				Select.class,
				ShortcutAction.class,
				SimpleFocusablePanel.class,
				Slider.class,
				SplitPanel.class,
				Table.class,
				TabSheet.class,
				TextField.class,
				Tree.class,
				TreeAction.class,
				TwinColSelect.class,
				Upload.class,
				Window.class);
		
		//customize types
		forType(AbstractField.class).localize("caption","description","requiredError");
		forType(CustomComponent.class).children(Component.class,0,1)
			.typeAsMethod(Component.class, "setCompositionRoot");
		forType(ComponentContainer.class).typeAsMethod(Component.class, "addComponent")
			.children(Component.class, 0, Integer.MAX_VALUE)
			.children(ComponentContainer.class, 0, 1);
		
		//customize layout managers
		
		
				
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
