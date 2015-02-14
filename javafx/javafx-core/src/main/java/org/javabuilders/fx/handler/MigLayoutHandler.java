package org.javabuilders.fx.handler;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import net.miginfocom.layout.ConstraintParser;
import org.apache.commons.lang.NotImplementedException;
import org.javabuilders.*;
import org.javabuilders.fx.FXJB;
import org.javabuilders.fx.FXJBUtils;
import org.javabuilders.layout.mig.AbstractMigLayoutHandler;
import org.tbee.javafx.scene.layout.MigPane;

import java.awt.*;
import java.util.Map;

/**
 * JavaFX MigLayout handler
 */
public class MigLayoutHandler extends AbstractMigLayoutHandler {

    public MigLayoutHandler() {
        super(Label.class,"text");
    }

    @Override
    protected void setLayout(BuildProcess result, Node node, Object migLayout) throws BuildException {
        // nothing to do...it is already a MigPane
    }

    @Override
    protected Object getComponent(BuildProcess result, Node components, String name) throws BuildException {
        return FXJBUtils.getControl(components,name);
    }

    @Override
    protected void setLayoutConstraints(Object layout, String constraints) throws BuildException {
        throw new BuildException("Not implemented yet");
    }

    @Override
    protected void setRowConstraints(Object layout, String constraints) throws BuildException {
        MigPane pane = ((MigLayout) layout).getParent();
        pane.setRowConstraints(ConstraintParser.parseRowConstraints((String) constraints));
    }

    @Override
    protected void setColumnConstraints(Object layout, String constraints) throws BuildException {
        MigPane pane = ((MigLayout) layout).getParent();
        pane.setColumnConstraints(ConstraintParser.parseRowConstraints((String) constraints));
    }

    @Override
    protected void applyControlConstraints(BuildProcess result, Node node, Node components, Map<String, String> layoutConstraints) throws BuildException {
        MigPane parentContainer = (MigPane)node.getParent().getParent().getMainObject();
        for(String controlName : layoutConstraints.keySet()) {
            String controlConstraint = layoutConstraints.get(controlName);
            Control control = FXJBUtils.getControl(components,String.valueOf(controlName));

            if (control == null) {
                throw new BuildException("Unable to find component for name: {0}",controlName);
            }

            if (logger.isDebugEnabled()) {
                logger.debug("MigLayout constraints for " + controlName + " : " + controlConstraint);
            }
            parentContainer.add(control,controlConstraint);
        }
    }

    @Override
    protected void setControlName(Object control, String name) {
        Control c = (Control) control;
        c.setId(name);
    }

    @Override
    public Node createNewInstance(BuilderConfig config, BuildProcess process, Node parent, String key, Map<String, Object> properties) throws BuildException {
        // content -> MigPane
        MigPane pane = (MigPane) parent.getParent().getMainObject();
        return useExistingInstance(config, process, parent, key, properties, new MigLayout(pane));
    }

    @Override
    public Class<?> getApplicableClass() {
        return MigLayout.class;
    }

    /**
     * Fake class to keep the MigLayout node in YML
     */
    @Value
    @RequiredArgsConstructor
    public static class MigLayout implements ILayoutHandler {
        private final MigPane parent;
    }
}
