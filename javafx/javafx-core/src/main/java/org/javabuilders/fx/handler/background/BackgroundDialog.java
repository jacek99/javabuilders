/**
 * 
 */
package org.javabuilders.fx.handler.background;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import org.javabuilders.BuildException;
import org.javabuilders.BuildResult;
import org.javabuilders.annotations.BuildFile;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.event.CancelStatus;
import org.javabuilders.fx.FXJB;
import org.javabuilders.util.BuilderUtils;
import org.tbee.javafx.scene.layout.MigPane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

/**
 * Background dialog that binds to an underlying JavaFX background task
 * to provide progress UI and allow to cancel it if required
 * @author Jacek Furmankiewicz
 */
@BuildFile("BackgroundDialog.yml")
public class BackgroundDialog extends Stage {
	
    private final Task task;
	private final BuildResult result;
    @Getter
    private BooleanProperty cancelableProperty = new SimpleBooleanProperty();
	
	/**
	 * Constructor
	 * @throws org.javabuilders.BuildException
	 * @throws java.io.IOException
	 */
	public BackgroundDialog(BuildResult result, Task task, boolean cancelable) throws BuildException {
	    super(StageStyle.UTILITY);

        BuilderUtils.validateNotNullAndNotEmpty("task", task);
		this.task = task;
		this.result = result;
        this.cancelableProperty.set(cancelable);
		FXJB.build(this);
 	}

	/**
	 * Requests task to be cancelled.
	 */
	@SuppressWarnings("unused")
	private void requestCancel() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(result.getResource("title.cancelTask"));
        alert.setContentText(result.getResource("message.cancelConfirm"));

        Optional<ButtonType> selection = alert.showAndWait();
        if (selection.get() == ButtonType.OK){
            task.cancel();
        }
	}

}
