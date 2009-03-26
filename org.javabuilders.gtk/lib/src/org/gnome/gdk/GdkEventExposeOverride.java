/*
 * GdkEventExposeOverride.java
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
 * 
 * @author Andrew Cowie
 */
final class GdkEventExposeOverride extends Plumbing
{
    private GdkEventExposeOverride() {}

    /**
     * area is part of the GdkEventExpose struct and NOT a pointer. We are
     * employing the same hack as Allocation and Requisition. This makes
     * three.
     * 
     * @see org.gnome.gtk.Widget#getAllocation()
     */
    static final Rectangle getArea(EventExpose self) {
        long result;

        synchronized (lock) {
            result = gdk_event_expose_get_area(pointerOf(self));

            return (Rectangle) boxedFor(Rectangle.class, result);
        }
    }

    private static native final long gdk_event_expose_get_area(long self);
}
