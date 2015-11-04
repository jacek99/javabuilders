/**
 *
 */
package org.javabuilders.swing.handler.type;

import java.awt.Point;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;

/**
 * Handles creating Point objects
 *
 * @author Luca Domenichini
 */
public final class PointAsValueHandler implements ITypeAsValueHandler<Point>
{

    /**
     * POINT_VALUE_REGEX
     */
    private final static String POINT_VALUE_REGEX = "^[0-9]+;{1}[0-9]+$";

    /**
     * singleton
     */
    private final static PointAsValueHandler SINGLETON = new PointAsValueHandler();

    /**
     * @return Singleton
     */
    public static PointAsValueHandler getInstance()
    {
        return SINGLETON;
    }

    public String getInputValueSample()
    {
        return "100;50";
    }

    /* (non-Javadoc)
     * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
     */
    public Point getValue(BuildProcess process, Node node, String key, Object inputValue) throws BuildException
    {
        final String pointValue = (String) inputValue;
        if (pointValue.matches(POINT_VALUE_REGEX))
        {
            final String[] xy = pointValue.split(";");
            return new Point(Integer.valueOf(xy[0]), Integer.valueOf(xy[1]));
        }
        //else
        throw new BuildException("\"{0}\" is not a valid point", pointValue);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
     */
    public String getRegex()
    {
        return POINT_VALUE_REGEX;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.javabuilders.IApplicable#getApplicableClass()
     */
    public Class<?> getApplicableClass()
    {
        return Point.class;
    }

}
