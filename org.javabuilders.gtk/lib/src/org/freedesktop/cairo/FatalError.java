/*
 * FatalError.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.cairo;

/**
 * Cairo is in an illegal state.
 * 
 * <p>
 * In the java-gnome bindings of Cairo, we check the status of the library
 * after every call. This is the exception that will be thrown if an error has
 * occured.
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
public class FatalError extends org.freedesktop.bindings.FatalError
{
    private static final long serialVersionUID = 1;

    protected FatalError(String msg) {
        super(msg);
    }
}
