/*
 * Orientation.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd, and Others
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
 * Determine the orientation of some Widgets, such as Toolbar, that can be
 * switched between horizontal and vertical orientation during execution.
 * 
 * @author Vreixo Formoso
 * @since 4.0.4
 */
public final class Orientation extends Constant
{
    private Orientation(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Horizontal orientation.
     */
    public static final Orientation HORIZONTAL = new Orientation(GtkOrientation.HORIZONTAL, "HORIZONTAL");

    /**
     * Vertical orientation.
     */
    public static final Orientation VERTICAL = new Orientation(GtkOrientation.VERTICAL, "VERTICAL");

}
