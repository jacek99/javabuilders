/*
 * ArrowType.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd, and Others
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
 * Used to indicate the direction in which the arrow graphic on an
 * {@link Arrow} Widget will point.
 * 
 * @author Serkan Kaba
 * @author Andrew Cowie
 * @since 4.0.10
 */
public final class ArrowType extends Constant
{
    private ArrowType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Represents an upward pointing arrow.
     */
    public static final ArrowType UP = new ArrowType(GtkArrowType.UP, "UP");

    /**
     * Represents an downward pointing arrow.
     */
    public static final ArrowType DOWN = new ArrowType(GtkArrowType.DOWN, "DOWN");

    /**
     * Represents a left pointing arrow.
     */
    public static final ArrowType LEFT = new ArrowType(GtkArrowType.LEFT, "LEFT");

    /**
     * Represents an arrow pointing right.
     */
    public static final ArrowType RIGHT = new ArrowType(GtkArrowType.RIGHT, "RIGHT");

    /**
     * Don't draw an arrow.
     * 
     * <p>
     * <code>NONE</code> is a special mode which causes an Arrow Widget to
     * occupies the space that an normal Arrow would, but without having an
     * arrow graphic drawn in it. This can be useful in cases where you are
     * trying to normalize the size requests for a series of Widgets.
     */
    public static final ArrowType NONE = new ArrowType(GtkArrowType.NONE, "NONE");
}
