package org.javabuilders.fx;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.web.HTMLEditor;
import org.controlsfx.control.*;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.spreadsheet.SpreadsheetView;
import org.controlsfx.dialog.Dialogs;
import org.javabuilders.*;
import org.javabuilders.fx.handler.FXBackgroundProcessingHandler;
import org.javabuilders.fx.handler.FXMigLayoutHandler;
import org.javabuilders.fx.handler.FXValidationMessageHandler;
import org.javabuilders.fx.handler.event.CommonActionListenerHandler;
import org.javabuilders.fx.handler.type.PaneHandlerFinishProcessor;
import org.javabuilders.layout.DefaultResize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.*;
import java.util.List;

import static org.javabuilders.fx.FXJB.*;

/**
 * Configuration for JavaFX JavaBuilder
 */
public class FXJBConfig extends BuilderConfig implements IStringLiteralControlConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(FXJBConfig.class);
	
	private Map<String,ScrollPane.ScrollBarPolicy> scrollbars = new HashMap();
	{
		scrollbars.put("always", ScrollPane.ScrollBarPolicy.ALWAYS);
		scrollbars.put("asNeeded", ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollbars.put("never", ScrollPane.ScrollBarPolicy.NEVER);
	}
	
	/**
	 * Constructor
	 */
	public FXJBConfig() {
		super(new FXBackgroundProcessingHandler(),
			new FXValidationMessageHandler(),
			new ConfirmCommand(), Builder.ID);

		//define aliases for basic FX types and models
		addType(
                Label.class,
                Button.class,
                RadioButton.class,
                ToggleButton.class,
                CheckBox.class,
                ChoiceBox.class,
                TextField.class,
                PasswordField.class,
                ScrollBar.class,
                ScrollPane.class,
                ListView.class,
                TableView.class,
                TreeView.class,
                TreeTableView.class,
                ComboBox.class,
                Separator.class,
                Scene.class, // TODO: maybe not needed
                Slider.class,
                ProgressBar.class,
                ProgressIndicator.class,
                Hyperlink.class,
                Tooltip.class,
                HTMLEditor.class,
                TitledPane.class,
                Accordion.class,
                MenuBar.class,
                MenuItem.class,
                Menu.class,
                CheckMenuItem.class,
                RadioMenuItem.class,
                CustomMenuItem.class,
                SeparatorMenuItem.class,
                ColorPicker.class,
                DatePicker.class,
                Pagination.class
				);

        // add aliases for ControlFX controls
        addType(
                BreadCrumbBar.class,
                ButtonBar.class,
                CheckComboBox.class,
                CheckListView.class,
                CheckTreeView.class,
                GridView.class,
                HiddenSidesPane.class,
                HyperlinkLabel.class,
                InfoOverlay.class,
                ListSelectionView.class,
                PlusMinusSlider.class,
                PopOver.class,
                RangeSlider.class,
                Rating.class,
                SegmentedButton.class,
                SnapshotView.class,
                SpreadsheetView.class,
                TaskProgressView.class
        );
		
		//define aliases for FX layout panes
		addType(MigPane.class,
                BorderPane.class,
                HBox.class,
                VBox.class,
                StackPane.class,
                GridPane.class,
                FlowPane.class,
                TilePane.class,
                AnchorPane.class,
                // Control FX
                MasterDetailPane.class,
                NotificationPane.class,
                PropertySheet.class
                );

        // hacks
        addType(FXMigLayoutHandler.MigLayout.class);

        // control setup
        forType(ButtonBase.class)
                .localize(TEXT)
                .propertyHandler(new CommonActionListenerHandler());


        // pane setup
        forType(Pane.class)
                .ignore(Builder.CONSTRAINTS, Builder.LAYOUT)
                .finishProcessor(new PaneHandlerFinishProcessor())
                .defaultResize(DefaultResize.BOTH)
                .children(Control.class, 0,Integer.MAX_VALUE);

        forType(MigPane.class)
                .children(FXMigLayoutHandler.MigLayout.class,0,1);
        forType(FXMigLayoutHandler.MigLayout.class)
                .typeHandler(new FXMigLayoutHandler());

		//setStringLiteralControlSuffix("Label"); 
		setStringLiteralControlPrefix("lbl");
		
		//auto-creation of controls
		//PrefixControlDefinition.addReservedPrefix("add"); // to avoid conflict with JPanel.add(JContainer)
		
		Map<String,String> buttonDefaults = new HashMap();
		buttonDefaults.put("onAction", PrefixControlDefinition.SUFFIX_PASCAL_CASE);
		buttonDefaults.put("text", PrefixControlDefinition.SUFFIX_LABEL);

		prefix("btn", Button.class, buttonDefaults);
		prefix("tgl",ToggleButton.class, buttonDefaults);
		prefix("txt",TextField.class);
		prefix("cbx",CheckBox.class);
		prefix("rb", RadioButton.class);
		prefix("cmb",ComboBox.class);
		prefix("lst",List.class);
		prefix("txa",TextArea.class);
		prefix("tbl",TableView.class);
		prefix("tr", TreeView.class);
		prefix("sld",Slider.class);
		prefix("prg",ProgressBar.class);
		prefix("pwd",PasswordField.class);
		// prefix("spn",Spinner.class);
		prefix("sep",Separator.class);
	}


	private static  class ConfirmCommand implements ICustomCommand<Boolean>  {

		public Boolean process(BuildResult result, Object source) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(result.getResource("title.confirmation"));
            alert.setContentText(result.getResource("question.areYouSure"));

            Optional<ButtonType> selection = alert.showAndWait();
            if (selection.get() == ButtonType.OK){
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
