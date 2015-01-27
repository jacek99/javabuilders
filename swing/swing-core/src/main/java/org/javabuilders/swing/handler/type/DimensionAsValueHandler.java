/**
 *
 */
package org.javabuilders.swing.handler.type;

import java.awt.Dimension;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;

/**
 * Handles creating Dimension objects
 *
 * @author anavarro
 * @author Jacek Furmankiewicz
 *
 */
public final class DimensionAsValueHandler implements ITypeAsValueHandler<Dimension>
{

    /**
     * DIMENSION_VALUE_REGEX
     */
    private final static String DIMENSION_VALUE_REGEX = "^[0-9]+x{1}[0-9]+$";

    /**
     * singleton
     */
    private final static DimensionAsValueHandler SINGLETON = new DimensionAsValueHandler();

    /**
     * @return Singleton
     */
    public static DimensionAsValueHandler getInstance()
    {
        return SINGLETON;
    }

    public String getInputValueSample()
    {
        return "800x600";
    }

    /* (non-Javadoc)
     * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
     */
    @SuppressWarnings("boxing")
    public Dimension getValue(BuildProcess process, Node node, String key, Object inputValue) throws BuildException
    {
        final String dimensionValue = (String) inputValue;
        if (dimensionValue.matches(DIMENSION_VALUE_REGEX))
        {
            final String[] widthHeight = dimensionValue.split("x");
            return new Dimension(Integer.valueOf(widthHeight[0]), Integer.valueOf(widthHeight[1]));
        }
        //else
        throw new BuildException("\"{0}\" is not a valid dimension", dimensionValue);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
     */
    public String getRegex()
    {
        return DIMENSION_VALUE_REGEX;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.javabuilders.IApplicable#getApplicableClass()
     */
    public Class<?> getApplicableClass()
    {
        return Dimension.class;
    }

}
