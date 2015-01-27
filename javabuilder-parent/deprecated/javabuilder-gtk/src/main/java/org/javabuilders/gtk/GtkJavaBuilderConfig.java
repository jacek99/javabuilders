package org.javabuilders.gtk;

import static org.javabuilders.gtk.GtkConstants.*;

import java.beans.PropertyChangeSupport;
import java.util.Calendar;

import org.freedesktop.bindings.Constant;
import org.gnome.gtk.AccelLabel;
import org.gnome.gtk.Action;
import org.gnome.gtk.Alignment;
import org.gnome.gtk.Arrow;
import org.gnome.gtk.AspectFrame;
import org.gnome.gtk.Assistant;
import org.gnome.gtk.Box;
import org.gnome.gtk.Button;
import org.gnome.gtk.ButtonBox;
import org.gnome.gtk.CellView;
import org.gnome.gtk.CheckButton;
import org.gnome.gtk.CheckMenuItem;
import org.gnome.gtk.ColorButton;
import org.gnome.gtk.ColorSelection;
import org.gnome.gtk.ComboBox;
import org.gnome.gtk.ComboBoxEntry;
import org.gnome.gtk.Container;
import org.gnome.gtk.Dialog;
import org.gnome.gtk.DrawingArea;
import org.gnome.gtk.Entry;
import org.gnome.gtk.EventBox;
import org.gnome.gtk.Expander;
import org.gnome.gtk.FileChooserAction;
import org.gnome.gtk.FileChooserButton;
import org.gnome.gtk.FileChooserWidget;
import org.gnome.gtk.Fixed;
import org.gnome.gtk.FontButton;
import org.gnome.gtk.FontSelection;
import org.gnome.gtk.Frame;
import org.gnome.gtk.HBox;
import org.gnome.gtk.HButtonBox;
import org.gnome.gtk.HPaned;
import org.gnome.gtk.HRuler;
import org.gnome.gtk.HScale;
import org.gnome.gtk.HScrollbar;
import org.gnome.gtk.HSeparator;
import org.gnome.gtk.HandleBox;
import org.gnome.gtk.IconView;
import org.gnome.gtk.Image;
import org.gnome.gtk.ImageMenuItem;
import org.gnome.gtk.Label;
import org.gnome.gtk.Layout;
import org.gnome.gtk.LinkButton;
import org.gnome.gtk.Menu;
import org.gnome.gtk.MenuBar;
import org.gnome.gtk.MenuItem;
import org.gnome.gtk.MenuShell;
import org.gnome.gtk.MenuToolButton;
import org.gnome.gtk.Notebook;
import org.gnome.gtk.Paned;
import org.gnome.gtk.Plug;
import org.gnome.gtk.ProgressBar;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.RadioMenuItem;
import org.gnome.gtk.RecentChooserMenu;
import org.gnome.gtk.RecentChooserWidget;
import org.gnome.gtk.Ruler;
import org.gnome.gtk.Scale;
import org.gnome.gtk.ScaleButton;
import org.gnome.gtk.ScrolledWindow;
import org.gnome.gtk.SeparatorMenuItem;
import org.gnome.gtk.SeparatorToolItem;
import org.gnome.gtk.Socket;
import org.gnome.gtk.SpinButton;
import org.gnome.gtk.Statusbar;
import org.gnome.gtk.Table;
import org.gnome.gtk.TearoffMenuItem;
import org.gnome.gtk.TextComboBox;
import org.gnome.gtk.TextComboBoxEntry;
import org.gnome.gtk.TextView;
import org.gnome.gtk.ToggleButton;
import org.gnome.gtk.ToggleToolButton;
import org.gnome.gtk.ToolButton;
import org.gnome.gtk.ToolItem;
import org.gnome.gtk.Toolbar;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.TreeViewColumn;
import org.gnome.gtk.VBox;
import org.gnome.gtk.VButtonBox;
import org.gnome.gtk.VPaned;
import org.gnome.gtk.VRuler;
import org.gnome.gtk.VScale;
import org.gnome.gtk.VScrollbar;
import org.gnome.gtk.VSeparator;
import org.gnome.gtk.Viewport;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;
import org.javabuilders.BuilderConfig;
import org.javabuilders.ICustomCommand;
import org.javabuilders.event.IBackgroundProcessingHandler;
import org.javabuilders.gtk.handler.property.ScaleDrawValueHandler;
import org.javabuilders.gtk.handler.type.ConstantAsValueHandler;
import org.javabuilders.gtk.handler.type.ContainerFinishProcessor;
import org.javabuilders.gtk.handler.type.FileChooserActionAsValue;
import org.javabuilders.gtk.handler.type.FileChooserButtonTypeHandler;
import org.javabuilders.gtk.handler.type.HBoxTypeHandler;
import org.javabuilders.gtk.handler.type.HScaleTypeHandler;
import org.javabuilders.gtk.handler.type.LabelHandler;
import org.javabuilders.gtk.handler.type.MenuItemTypeHandler;
import org.javabuilders.gtk.handler.type.MenuShellFinishProcessor;
import org.javabuilders.gtk.handler.type.NotebookFinishProcessor;
import org.javabuilders.gtk.handler.type.PanedFinishProcessor;
import org.javabuilders.gtk.handler.type.ScrolledWindowFinishProcessor;
import org.javabuilders.gtk.handler.type.TableTypeHandler;
import org.javabuilders.gtk.handler.type.TreeViewColumnTypeHandler;
import org.javabuilders.gtk.handler.type.VBoxTypeHandler;
import org.javabuilders.handler.ITypeHandler;
import org.javabuilders.handler.validation.IValidationMessageHandler;
import org.javabuilders.layout.DefaultResize;

/**
 * GTK+ config
 * @author Jacek Furmankiewicz
 */
public class GtkJavaBuilderConfig extends BuilderConfig{

	public GtkJavaBuilderConfig(
			IBackgroundProcessingHandler backgroundProcessingHandler,
			ITypeHandler bindingTypeHandler,
			IValidationMessageHandler validationMessageHandler,
			ICustomCommand<Boolean> confirmCommand) {
		super(null,  null, null);

		//widgets
		addType(
				Action.class,
				AccelLabel.class,
				Alignment.class,
				Arrow.class,
				AspectFrame.class,
				Assistant.class,
				Box.class,
				Button.class,
				ButtonBox.class,
				Calendar.class,
				CellView.class,
				CheckButton.class,
				CheckMenuItem.class,
				ColorButton.class,
				ColorSelection.class,
				ComboBox.class,
				ComboBoxEntry.class,
				Dialog.class,
				DrawingArea.class,
				Entry.class,
				EventBox.class,
				Expander.class,
				FileChooserButton.class,
				FileChooserWidget.class,
				Fixed.class,
				FontButton.class,
				FontSelection.class,
				Frame.class,
				HandleBox.class,
				HBox.class,
				HButtonBox.class,
				HPaned.class,
				HRuler.class,
				HScale.class,
				HScrollbar.class,
				HSeparator.class,
				IconView.class,
				Image.class,
				ImageMenuItem.class,
				Label.class,
				Layout.class,
				LinkButton.class,
				Menu.class,
				MenuBar.class,
				MenuItem.class,
				MenuToolButton.class,
				Notebook.class,
				Plug.class,
				ProgressBar.class,
				RadioButton.class,
				RadioMenuItem.class,
				RecentChooserMenu.class,
				RecentChooserWidget.class,
				Ruler.class,
				ScaleButton.class,
				ScrolledWindow.class,
				SeparatorMenuItem.class,
				SeparatorToolItem.class,
				Socket.class,
				SpinButton.class,
				Statusbar.class,
				Table.class,
				TearoffMenuItem.class,
				TextComboBox.class,
				TextComboBoxEntry.class,
				TextView.class,
				ToggleButton.class,
				ToggleToolButton.class,
				Toolbar.class,
				ToolButton.class,
				ToolItem.class,
				TreeView.class,
				TreeViewColumn.class,
				VBox.class,
				VButtonBox.class,
				Viewport.class,
				VPaned.class,
				VRuler.class,
				VScale.class,
				VScrollbar.class,
				VSeparator.class,
				Window.class);	
		//type customizations
		forType(Button.class).defaultResize(DefaultResize.NONE);
		forType(Container.class)
			.finishProcessor(new ContainerFinishProcessor())
			.children(Widget.class,0,Integer.MAX_VALUE);
		forType(Constant.class).valueHandler(new ConstantAsValueHandler());
		forType(Entry.class).defaultResize(DefaultResize.X_AXIS);
		forType(FileChooserAction.class)
			.valueHandler(new FileChooserActionAsValue());
		forType(FileChooserButton.class)
			.typeHandler(new FileChooserButtonTypeHandler());
		forType(HBox.class).typeHandler(new HBoxTypeHandler());
		forType(HScale.class).typeHandler(new HScaleTypeHandler());
		forType(Label.class).typeHandler(new LabelHandler());
		forType(Menu.class)
			.children(MenuItem.class,0,Integer.MAX_VALUE);
		forType(MenuBar.class)
			.children(MenuItem.class, 0, Integer.MAX_VALUE);
		forType(MenuItem.class)
			.typeHandler(new MenuItemTypeHandler())
			.children(Menu.class,0,Integer.MAX_VALUE);		
		forType(MenuShell.class).finishProcessor(new MenuShellFinishProcessor());
		forType(Notebook.class).defaultResize(DefaultResize.BOTH).finishProcessor(new NotebookFinishProcessor());
		forType(Paned.class).defaultResize(DefaultResize.BOTH).finishProcessor(new PanedFinishProcessor());
		forType(Scale.class)
			.defaultValue("min", 1).defaultValue("max",100).defaultValue("step", 1)
			.propertyHandler(new ScaleDrawValueHandler());
		forType(ScrolledWindow.class).finishProcessor(new ScrolledWindowFinishProcessor());
		forType(Table.class).typeHandler(new TableTypeHandler());
		forType(TextView.class).defaultResize(DefaultResize.BOTH);
		forType(TreeView.class)
			.defaultResize(DefaultResize.BOTH)
			.childrenOverride(true)
			.children(TreeViewColumn.class, 0, Integer.MAX_VALUE);
		forType(TreeViewColumn.class)
			.typeHandler(new TreeViewColumnTypeHandler());
		forType(VBox.class).typeHandler(new VBoxTypeHandler());
		forType(Widget.class)
			.localize(LABEL, NotebookFinishProcessor.TAB_LABEL)
			.ignore(NotebookFinishProcessor.IS_TAB_LABEL,NotebookFinishProcessor.TAB_LABEL);
		forType(Window.class)
			.localize("title");
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.BuilderConfig#createPropertyChangeSupport(java.lang.Object)
	 */
	@Override
	public PropertyChangeSupport createPropertyChangeSupport(Object source) {
		return new PropertyChangeSupport(source);
	}

}
