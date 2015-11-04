/**
 *
 */
package org.javabuilders.swing.handler.type;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;

/**
 * Handles creating Rectangle objects. Useful for setting both location and size within a single property
 *
 * @author Luca Domenichini
 */
public final class RectangleAsValueHandler implements ITypeAsValueHandler<Rectangle>
{

    /**
     * RECTANGLE_VALUE_REGEX
     */
    private final static String RECTANGLE_VALUE_REGEX = "^[0-9]+x{1}[0-9]+x{1}[0-9]+x{1}[0-9]+$";

    /**
     * singleton
     */
    private final static RectangleAsValueHandler SINGLETON = new RectangleAsValueHandler();

    /**
     * @return Singleton
     */
    public static RectangleAsValueHandler getInstance()
    {
        return SINGLETON;
    }

    public String getInputValueSample()
    {
        return "100x50x200x200";
    }

    /* (non-Javadoc)
     * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
     */
    public Rectangle getValue(BuildProcess process, Node node, String key, Object inputValue) throws BuildException
    {
        final String rectangleValue = (String) inputValue;
        if (rectangleValue.matches(RECTANGLE_VALUE_REGEX))
        {
            final String[] xywh = rectangleValue.split("x");
            return new Rectangle(new Point(Integer.valueOf(xywh[0]), Integer.valueOf(xywh[1])), new Dimension(Integer.valueOf(xywh[2]), Integer.valueOf(xywh[3])));
        }
        //else
        throw new BuildException("\"{0}\" is not a valid rectangle", rectangleValue);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
     */
    public String getRegex()
    {
        return RECTANGLE_VALUE_REGEX;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.javabuilders.IApplicable#getApplicableClass()
     */
    public Class<?> getApplicableClass()
    {
        return Rectangle.class;
    }

}
