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
import org.javabuilders.gtk.handler.property.TreeViewColumnNameHandler;
import org.javabuilders.gtk.handler.property.WidgetNameHandler;
import org.javabuilders.gtk.handler.type.ConstantAsValueHandler;
import org.javabuilders.gtk.handler.type.ContainerFinishProcessor;
import org.javabuilders.gtk.handler.type.HBoxTypeHandler;
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
				Calendar.class,
				CellView.class,
				Alignment.class,
				Button.class,
				ColorButton.class,
				FontButton.class,
				LinkButton.class,
				ScaleButton.class,
				ToggleButton.class,
				CheckButton.class,
				RadioButton.class,
				ComboBox.class,
				ComboBoxEntry.class,
				TextComboBoxEntry.class,
				TextComboBox.class,
				EventBox.class,
				Expander.class,
				Frame.class,
				AspectFrame.class,
				HandleBox.class,
				MenuItem.class,
				CheckMenuItem.class,
				RadioMenuItem.class,
				ImageMenuItem.class,
				SeparatorMenuItem.class,
				TearoffMenuItem.class,
				ScrolledWindow.class,
				ToolItem.class,
				SeparatorToolItem.class,
				ToolButton.class,
				MenuToolButton.class,
				ToggleToolButton.class,
//				RadioButton.class,
				Viewport.class,
				Window.class,
				Assistant.class,
				Dialog.class,
				Plug.class,
				Box.class,
				ButtonBox.class,
				HButtonBox.class,
				VButtonBox.class,
				HBox.class,
				FileChooserButton.class,
				Statusbar.class,
				VBox.class,
				ColorSelection.class,
				FileChooserWidget.class,
				FontSelection.class,
				RecentChooserWidget.class,
				Fixed.class,
				IconView.class,
				Layout.class,
				Menu.class,
				RecentChooserMenu.class,
				MenuBar.class,
				Notebook.class,
				HPaned.class,
				VPaned.class,
				Socket.class,
				Table.class,
				TextView.class,
				Toolbar.class,
				TreeView.class,
				TreeViewColumn.class,
				DrawingArea.class,
				Entry.class,
				SpinButton.class,
				Arrow.class,
				Image.class,
				Label.class,
				AccelLabel.class,
				ProgressBar.class,
				HScale.class,
				VScale.class,
				HScrollbar.class,
				VScrollbar.class,
				Ruler.class,
				HRuler.class,
				VRuler.class,
				HSeparator.class,
				VSeparator.class);
	
		//type customizations
		forType(Button.class).defaultResize(DefaultResize.NONE);
		forType(Container.class).finishProcessor(new ContainerFinishProcessor());
		forType(Constant.class).valueHandler(new ConstantAsValueHandler());
		forType(Entry.class).defaultResize(DefaultResize.X_AXIS);
		forType(MenuShell.class).finishProcessor(new MenuShellFinishProcessor());
		forType(Notebook.class).defaultResize(DefaultResize.BOTH).finishProcessor(new NotebookFinishProcessor());
		forType(Paned.class).defaultResize(DefaultResize.BOTH).finishProcessor(new PanedFinishProcessor());
		forType(ScrolledWindow.class).finishProcessor(new ScrolledWindowFinishProcessor());
		forType(TextView.class).defaultResize(DefaultResize.BOTH);
		
		forType(Widget.class).localize(LABEL, NotebookFinishProcessor.TAB_LABEL).ignore(NotebookFinishProcessor.IS_TAB_LABEL,
				NotebookFinishProcessor.TAB_LABEL);
		
		//type handlers
		addTypeHandler(new HBoxTypeHandler());
		addTypeHandler(new LabelHandler());
		addTypeHandler(new MenuItemTypeHandler());
		addTypeHandler(new TableTypeHandler());
		addTypeHandler(new TreeViewColumnTypeHandler());
		addTypeHandler(new VBoxTypeHandler());
		
		//property handlers
		addPropertyHandler(new WidgetNameHandler());
		addPropertyHandler(new TreeViewColumnNameHandler());
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.BuilderConfig#createPropertyChangeSupport(java.lang.Object)
	 */
	@Override
	public PropertyChangeSupport createPropertyChangeSupport(Object source) {
		return new PropertyChangeSupport(source);
	}

}
