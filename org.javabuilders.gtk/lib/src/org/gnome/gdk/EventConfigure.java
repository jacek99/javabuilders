/*
 * EventConfigure.java
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
 * 
 * Information about the (possibly changed) size and position of a Window on
 * screen. This is the object used to convey such data in the
 * {@link org.gnome.gtk.Window.ConfigureEvent Window.ConfigureEvent} signal.
 * 
 * <p>
 * All dimensions are in pixels.
 * 
 * @author Andrew Cowie
 * @since 4.0.8
 */
public final class EventConfigure extends Event
{
    protected EventConfigure(long pointer) {
        super(pointer);
    }

    /**
     * The width of the Window.
     * 
     * @since 4.0.8
     */
    public int getWidth() {
        return GdkEventConfigure.getWidth(this);
    }

    /**
     * The height of the Window.
     * 
     * @since 4.0.8
     */
    public int getHeight() {
        return GdkEventConfigure.getHeight(this);
    }

    /**
     * The horizontal co-ordinate relative to the top left corner of the
     * screen.
     * 
     * @since 4.0.8
     */
    public int getX() {
        return GdkEventConfigure.getX(this);
    }

    /**
     * The vertical co-ordinate relative to the top left corner of the screen.
     * 
     * @since 4.0.8
     */
    public int getY() {
        return GdkEventConfigure.getY(this);
    }
}
