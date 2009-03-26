/*
 * EventExpose.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

/**
 * Event generated when part or all of a [GDK] Window needs to be redrawn.
 * 
 * @author Andrew Cowie
 * @since 4.0.3
 * @see org.gnome.gtk.Widget.ExposeEvent
 */
public final class EventExpose extends Event
{
    protected EventExpose(long pointer) {
        super(pointer);
    }

    /**
     * Get a Rectangle describing the box bounding the Region that has been
     * exposed and needs redrawing.
     * 
     * @since 4.0.7
     */
    public Rectangle getArea() {
        return GdkEventExposeOverride.getArea(this);
    }
}
