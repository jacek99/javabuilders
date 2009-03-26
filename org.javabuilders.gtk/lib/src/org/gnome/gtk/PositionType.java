/*
 * PositionType.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
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
 * Which edge of a Widget an adornment is placed at. These constants are used
 * by Scale and Notebook to specify where the value is located or where the
 * tabs will be placed (see {@link Scale#setValuePosition(PositionType)
 * setValuePosition()} and {@link Notebook#setTabPosition(PositionType)
 * setTabPosition()} respectively).
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public final class PositionType extends Constant
{
    private PositionType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Feature will be placed to the left side of the Widget.
     */
    public static final PositionType LEFT = new PositionType(GtkPositionType.LEFT, "LEFT");

    /**
     * Feature will be placed to the right side of the Widget.
     */
    public static final PositionType RIGHT = new PositionType(GtkPositionType.RIGHT, "RIGHT");

    /**
     * Feature will be placed above the Widget.
     */
    public static final PositionType TOP = new PositionType(GtkPositionType.TOP, "TOP");

    /**
     * Feature will be placed below the Widget.
     */
    public static final PositionType BOTTOM = new PositionType(GtkPositionType.BOTTOM, "BOTTOM");
}
