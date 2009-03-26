/*
 * GdkColorOverride.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

final class GdkColorOverride extends Plumbing
{
    /*
     * Create a GdkColor via gdk_color_copy() to support subsequent release
     * via gdk_color_free(). TODO this is another Boxed type that should
     * really have its allocator generated.
     */
    static final long createColor(int red, int green, int blue) {
        synchronized (lock) {
            return gdk_color_new(red, green, blue);
        }
    }

    private static native final long gdk_color_new(int red, int green, int blue);
}
