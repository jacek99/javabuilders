/*
 * MouseButton.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" See the LICENCE file for the terms governing usage and
 * redistribution.
 */
package org.gnome.gdk;

import org.freedesktop.bindings.Constant;

/**
 * Constants representing which mouse button was pressed.
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
/*
 * This is something we just cooked up locally. It's not in GDK.
 */
public class MouseButton extends Constant
{
    protected MouseButton(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * A "left click", mouse button <code>1</code>.
     */
    public static final MouseButton LEFT = new MouseButton(1, "LEFT");

    /**
     * A "centre click", mouse button <code>2</code>. Some mice don't have a
     * middle button; in such cases your X server may be configured to
     * generate the middle button press if you press both right and left
     * simultaneously. Mice with scroll wheels will often generated this
     * button if the wheel is clicked (not scrolled, but pressed).
     */
    public static final MouseButton MIDDLE = new MouseButton(2, "MIDDLE");

    /**
     * A "right click", mouse button <code>3</code>.
     */
    public static final MouseButton RIGHT = new MouseButton(3, "RIGHT");

    /**
     * Mouse button <code>4</code>.
     */
    public static final MouseButton FOURTH = new MouseButton(4, "FOURTH");

    /**
     * Mouse button <code>5</code>.
     */
    public static final MouseButton FIFTH = new MouseButton(5, "FOURTH");
}
