/*
 * PangoRectangleOverride.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.pango;

/**
 * @author Andrew Cowie
 */
final class PangoRectangleOverride extends Plumbing
{
    private PangoRectangleOverride() {}

    static final long createRectangle() {
        synchronized (lock) {
            return pango_rectangle_new();
        }
    }

    private static native final long pango_rectangle_new();

    static final void free(Rectangle self) {
        synchronized (lock) {
            pango_rectangle_free(pointerOf(self));
        }
    }

    private static native final void pango_rectangle_free(long self);
}
