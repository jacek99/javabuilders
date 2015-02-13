package org.javabuilders.fx.handler.type;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import org.javabuilders.*;
import org.javabuilders.fx.handler.FXMigLayoutHandler;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Takes care of automatically adding all child objects to a pane
 */
public class PaneHandlerFinishProcessor implements ITypeHandlerFinishProcessor {

    private Set<Class<?>> ignored = new HashSet<Class<?>>();

    @Override
    public void finish(BuilderConfig config, BuildProcess process, Node current, String key, Map<String, Object> typeDefinition) throws BuildException {

        Pane pane = (Pane) current.getMainObject();
        Node content = current.getChildNode(Builder.CONTENT) ;
        if (content != null && !content.containsType(ILayoutHandler.class)) {

            content.getChildNodes(Control.class).stream()
                    .filter(child -> child != null)
                    .filter(child -> !ignored.stream().anyMatch((Class<?> ignoredClass) -> ignoredClass.isAssignableFrom(child.getClass())))
                    .forEach(child -> pane.getChildren().add((javafx.scene.Node) child.getMainObject()));
        }
    }

}
