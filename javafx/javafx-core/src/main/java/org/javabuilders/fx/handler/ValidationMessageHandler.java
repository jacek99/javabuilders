package org.javabuilders.fx.handler;

import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import org.apache.commons.lang.NotImplementedException;
import org.controlsfx.control.decoration.StyleClassDecoration;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import org.javabuilders.BuildResult;
import org.javabuilders.handler.validation.IValidationMessageHandler;
import org.javabuilders.handler.validation.ValidationMessage;
import org.javabuilders.handler.validation.ValidationMessageList;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JavaFX Input validation handler
 */
public class ValidationMessageHandler implements IValidationMessageHandler {

    // from ControlFX
    private StyleClassValidationDecoration errorStyleDecoration = new StyleClassValidationDecoration();

    @Override
    public void handleValidationMessages(ValidationMessageList list, BuildResult result) {
        if (list.size() > 0) {

            applyValidationDecorators(list,result);

            // show error dialog
            String title = (list.size() == 1) ? DEFAULT_VALIDATION_ERROR_TITLE : DEFAULT_VALIDATION_ERRORS_TITLE;

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(result.getResource(title));
            alert.setContentText(
                    // display one error message per line
                    list.stream().map(ValidationMessage::getMessage)
                    .collect(Collectors.joining("\n")));
            alert.showAndWait();

            //set the focus back to the first field in error
            list.stream()
                    .filter(msg -> msg.getProperty().isPresent())
                    .filter(msg -> result.get(msg.getProperty().get()) instanceof Control)
                    .findFirst()
                    .ifPresent(msg -> ((Control) result.get(msg.getProperty().get())).requestFocus());
        }

    }

    @Override
    public String getNamedObjectLabel(Object namedObject) {
        //TODO: figure this out
        return null;
    }

    // uses the ControlFX validators to highlight all the controls
    // that have errors
    private void applyValidationDecorators(ValidationMessageList list, BuildResult result) {
        // apply the decorator
        list.stream()
                .filter(msg -> msg.getProperty().isPresent())
                .filter(msg -> result.get(msg.getProperty().get()) instanceof Control)
                .forEach(msg -> {
                    Control control = (Control) result.get(msg.getProperty().get());

                    errorStyleDecoration.applyValidationDecoration(
                            org.controlsfx.validation.ValidationMessage.error(
                                    control,msg.getMessage()
                            )
                    );
                });
    }
}
