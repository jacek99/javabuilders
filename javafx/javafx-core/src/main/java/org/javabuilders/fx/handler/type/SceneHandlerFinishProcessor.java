package org.javabuilders.fx.handler.type;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

import java.util.Map;

/**
 * Scene finish processor
 * Delays the scene creation until all the children have been created
 */
public class SceneHandlerFinishProcessor implements ITypeHandlerFinishProcessor {
    @Override
    public void finish(BuilderConfig config, BuildProcess process, Node current, String key, Map<String, Object> typeDefinition) throws BuildException {
        // create scene if necessary
        Scene scene = (Scene) current.getMainObject();
        if (scene == null || scene.equals(SceneTypeHandler.DUMMY_SCENE)) {
            // dummy scene was created, so we have to replace it with a new one

            for(Node node: current.getChildNodes(Pane.class)) {
                scene = new Scene((Parent) node.getMainObject());
                current.setMainObject(scene);
                break;
            }
        }

        // set scene if required on the parent stage
        Stage stage = (Stage) current.getParentObject(Stage.class);
        if (stage != null) {
            stage.setScene(scene);
        }
    }
}
