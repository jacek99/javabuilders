package org.javabuilders.fx.handler;

import org.apache.commons.lang.NotImplementedException;
import org.javabuilders.BuildResult;
import org.javabuilders.handler.validation.IValidationMessageHandler;
import org.javabuilders.handler.validation.ValidationMessageList;

/**
 * Input validation handler
 */
public class FXValidationMessageHandler implements IValidationMessageHandler {
    @Override
    public void handleValidationMessages(ValidationMessageList list, BuildResult result) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public String getNamedObjectLabel(Object namedObject) {
        throw new NotImplementedException("Not implemented yet");
    }
}
