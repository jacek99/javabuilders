/**
 *
 */
package org.javabuilders.handler.type;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;

/**
 * Handles creating Class objects
 *
 * @author anavarro
 * @author Jacek Furmankiewicz
 *
 * @version 1.0 (modified to make it generic, not just Swing-specific)
 */
public final class ClassAsValueHandler implements ITypeAsValueHandler<Class<?>>
{

    /**
     * CLASS_VALUE_REGEX
     */
    private final static String CLASS_VALUE_REGEX = "^(.)+$";

    /**
     * singleton
     */
    private final static ClassAsValueHandler SINGLETON = new ClassAsValueHandler();

    /**
     * @return Singleton
     */
    public static ClassAsValueHandler getInstance()
    {
        return SINGLETON;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.javabuilders.handler.ITypeAsValueHandler#getInputValueSample()
     */
    public String getInputValueSample()
    {
        return "java.lang.String";
    }

    /**
     * (non-Javadoc)
     *
     * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
     */
    public Class<?> getValue(BuildProcess process, Node node, String key, Object inputValue) throws BuildException
    {
        final String classValue = (String) inputValue;
        if (classValue.matches(CLASS_VALUE_REGEX))
        {
            try
            {
                return Class.forName(classValue);
            }
            catch (ClassNotFoundException e)
            {
                throw new BuildException("\"{0}\" is not a valid class", classValue);
            }
        }
        // else
        throw new BuildException("\"{0}\" is not a valid class", classValue);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
     */
    public String getRegex()
    {
        return CLASS_VALUE_REGEX;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.javabuilders.IApplicable#getApplicableClass()
     */
    public Class<?> getApplicableClass()
    {
        return Class.class;
    }

}
