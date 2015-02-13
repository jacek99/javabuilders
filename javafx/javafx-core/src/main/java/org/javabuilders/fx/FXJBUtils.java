package org.javabuilders.fx;

import javafx.scene.control.Control;
import org.javabuilders.BuildException;
import org.javabuilders.Builder;
import org.javabuilders.Node;

import java.awt.*;

/**
 * Common utils
 */
public class FXJBUtils {

    /**
     * Utility method to find the component by its name
     * @return Component if found, null if not
     * @throws org.javabuilders.BuildException
     *
     */
    public static Control getControl(Node componentsNode, String name) throws BuildException {

        //prototypes are prefixed with $
        if (name != null && name.startsWith(Builder.PROTOTYPE_FIELD_PREFIX)) {
            name = name.substring(1);
        }

        if (Builder.CONTENT.equals(componentsNode.getKey())) {
            Control control = null;
            for(Node child  : componentsNode.getChildNodes()) {

                if (child.getMainObject() instanceof Control) {
                    Control temp = (Control)child.getMainObject();

                    if (name.equals(temp.getId())) {
                        control = temp;
                        break;
                    }
                }
            }
            return control;
        } else {
            throw new BuildException("componentsNode is not a valid Control node");
        }
    }

}
