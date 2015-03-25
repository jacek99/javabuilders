package org.javabuilders.fx.handler.type;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.javabuilders.*;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

import java.util.Map;

/**
 * Takes care of adding the child scene (if present) to the Stage
 *
 * @author Jacek Furmankiewicz
 */
public class StageHandlerFinishProcessor implements ITypeHandlerFinishProcessor {
    @Override
    public void finish(BuilderConfig config, BuildProcess process, Node current, String key, Map<String, Object> typeDefinition) throws BuildException {

        Stage stage = (Stage) current.getMainObject();

        if (current.getChildNode(Builder.CONTENT) != null) {
            current.getChildNode(Builder.CONTENT)
                    .getChildNodes(Scene.class).stream()
                    // skip if the dummy default scene is present
                    // the scene handler finish processor will add the scene into the parent instead
                    .filter(node -> !((Scene) node.getMainObject()).equals(SceneTypeHandler.DUMMY_SCENE))
                    .findFirst()
                    .ifPresent(node -> stage.setScene((Scene) node.getMainObject()));
        }
    }
}
