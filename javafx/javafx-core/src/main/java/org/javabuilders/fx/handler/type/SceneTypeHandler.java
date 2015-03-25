package org.javabuilders.fx.handler.type;

import javafx.scene.Scene;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.InvalidTypeException;
import org.javabuilders.Node;
import org.javabuilders.handler.EmptyTypeHandler;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.Map;

/**
 * Emtpty handler for Scene
 *
 * @author Jacek Furmankiewicz
 */
public class SceneTypeHandler extends EmptyTypeHandler {

    static final Scene DUMMY_SCENE = new Scene(new MigPane());

    @Override
    public Node createNewInstance(BuilderConfig config, BuildProcess result, Node parent, String key, Map<String, Object> typeDefinition) throws InvalidTypeException {
        return new Node(parent, key, typeDefinition, DUMMY_SCENE);
    }

    @Override
    public Class<?> getApplicableClass() {
        return Scene.class;
    }
}
