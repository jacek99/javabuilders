/*
 * Alignment.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2008 Vreixo Formoso
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.pango;

import org.freedesktop.bindings.Constant;

/**
 * How the line is aligned.
 * 
 * <p>
 * If you wonder why there is not a JUSTIFY alignment, that is configured with
 * the Layout {@link Layout#setJustify(boolean) setJustify()} method.
 * 
 * @author Vreixo Formoso
 * @since 4.0.8
 */
public final class Alignment extends Constant
{
    private Alignment(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Center the line within the available space.
     */
    public final static Alignment CENTER = new Alignment(PangoAlignment.CENTER, "CENTER");

    /**
     * Align the line at the left.
     */
    public final static Alignment LEFT = new Alignment(PangoAlignment.LEFT, "LEFT");

    /**
     * Align the line at the right.
     */
    public final static Alignment RIGHT = new Alignment(PangoAlignment.RIGHT, "RIGHT");
}
