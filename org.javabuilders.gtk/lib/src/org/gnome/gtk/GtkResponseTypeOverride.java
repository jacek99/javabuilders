/*
 * GtkResponseTypeOverride.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * This gives us package visible access to the utility methods which are of of
 * course visible to the translation layer hierarchy but needed to permit
 * subclassing of ResponseType and its use by Dialog.
 * 
 * This smacks of being something that will be needed more generally, but I do
 * NOT want to expose numOf() or enumFor() from the top level
 * org.freedesktop.bindings.Plumbing class unncessarily.
 * 
 * @author Andrew Cowie
 */
final class GtkResponseTypeOverride extends Plumbing
{
    private GtkResponseTypeOverride() {}

    static ResponseType enumFor(int ordinal) {
        return (ResponseType) Plumbing.enumFor(ResponseType.class, ordinal);
    }

    static int numOf(ResponseType reference) {
        return Plumbing.numOf(reference);
    }
}
