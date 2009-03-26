/*
 * GdkRectangleOverride.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

/**
 * @author Andrew Cowie
 */
final class GdkRectangleOverride extends Plumbing
{
    private GdkRectangleOverride() {}

    /*
     * As an implementation detail, somewhat unusually we have overloaded
     * here. Our allocator takes four arguments in case you need to set the
     * fields, but most of the time you're just allocating a blank struct to
     * pass in to be set by something else.
     */
    static final long createRectangle() {
        synchronized (lock) {
            return gdk_rectangle_new(0, 0, 0, 0);
        }
    }

    static final long createRectangle(int x, int y, int width, int height) {
        synchronized (lock) {
            return gdk_rectangle_new(x, y, width, height);
        }
    }

    private static native final long gdk_rectangle_new(int x, int y, int width, int height);

    static final void free(Rectangle self) {
        synchronized (lock) {
            gdk_rectangle_free(pointerOf(self));
        }
    }

    private static native final void gdk_rectangle_free(long self);
}
