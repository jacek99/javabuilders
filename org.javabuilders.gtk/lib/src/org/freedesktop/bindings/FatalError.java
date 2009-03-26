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
package org.freedesktop.bindings;

/**
 * A general error. These are thrown when an unexpected state is encountered
 * in the process of binding an underlying native library, and are intended to
 * be fatal. We throw a Java Error this in hopes of getting a stack trace
 * rather than the segmentation fault that will inevitably occur if execution
 * goes much further. Sometimes we even get it right.
 * 
 * <p>
 * In most cases these are instantiated by java-gnome's internal native code
 * and thrown from there.
 * 
 * <p>
 * See {@link org.gnome.glib.FatalError} for the Throwable emitted when
 * "critical" and other fatal problems occur within GLib, GTK, and the other
 * GNOME libraries.
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
public class FatalError extends java.lang.Error
{
    private static final long serialVersionUID = 1;

    protected FatalError() {
        super();
    }

    protected FatalError(String msg) {
        super(msg);
    }
}
