/*
 * WindowPosition.java
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

import org.freedesktop.bindings.Constant;

/**
 * Request that this Window be placed at a specific location on the screen.
 * Note that this is only a strong suggestion; as ever with window placement
 * issues, it is up to the window manager to actually decide where and how to
 * locate a new Window on the display.
 * 
 * @author Andrew Cowie
 * @since 4.0.3
 */
public final class WindowPosition extends Constant
{
    private WindowPosition(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * No need to request altered placement.
     */
    public static final WindowPosition NONE = new WindowPosition(GtkWindowPosition.NONE, "NONE");

    /**
     * The window should be placed at the center of the screen.
     */
    public static final WindowPosition CENTER = new WindowPosition(GtkWindowPosition.CENTER, "CENTER");

    /**
     * The window should be placed where the mouse pointer is presently.
     */
    public static final WindowPosition MOUSE = new WindowPosition(GtkWindowPosition.MOUSE, "MOUSE");

    /**
     * The window should be kept at the center even as it resizes.
     */
    public static final WindowPosition CENTER_ALWAYS = new WindowPosition(
            GtkWindowPosition.CENTER_ALWAYS, "CENTER_ALWAYS");

    /**
     * The window should be kept centered on its (
     * {@link Window#setTransientFor(Window) transient}) parent.
     */
    public static final WindowPosition CENTER_ON_PARENT = new WindowPosition(
            GtkWindowPosition.CENTER_ON_PARENT, "CENTER_ON_PARENT");
}
