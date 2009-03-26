/*
 * GlibException.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2007 Vreixo Formoso
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.glib;

/**
 * An exception thrown by the underlying library.
 * 
 * <p>
 * <b>It is inappropriate for a public API wrapper method to throw this
 * Exception. It is to be caught and re-thrown as a new Exception of an
 * appropriate Java type.</b> For example, if a function uses this mechanism
 * to report being unable to locate a file on disk, then the wrapper method
 * should do the following:
 * 
 * <pre>
 * public String getModificationDate(String filename) {
 *     try {
 *         NativeLibrary.getModificationDate(this, filename);
 *     } catch (GlibException ge) {
 *         throw new FileNotFoundException(ge.getMessage());
 *     }
 * }
 * </pre>
 * 
 * <p>
 * <i> We map native functions that take a <code>GError**</code> argument to
 * throwing this Exception if the function actually returns an error via that
 * parameter; the error parameter is masked from the binding hacker's view by
 * being handled in the C side JNI code.</i>
 * 
 * <p>
 * <i>Note that <code>GError</code>s are meant as Exceptions in the Java sense
 * of the term; they do not represent crashes nor RuntimeExceptions; they are
 * conditions that the programmer will need to create appropriate user
 * interface code for to allow the <i>user</i> to deal with.</i>
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.4
 */
public class GlibException extends Exception
{
    private static final long serialVersionUID = 1;

    protected GlibException() {
        super();
    }

    protected GlibException(String msg) {
        super(msg);
    }
}
