/*
 * Style.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2008      Vreixo Formoso
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
 * Constants specifying the style of a font. Style is the typographic term
 * describing whether the slant of the characters. The upright default we're
 * all used to is {@link #NORMAL NORMAL}; when this is tipped over on its side
 * it is the {@link #OBLIQUE OBLIQUE} style, whereas the more fancy script
 * like lettering is {@link #ITALIC ITALIC}s.
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.9
 */
public final class Style extends Constant
{
    private Style(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Ordinary text we use by default.
     */
    public static final Style NORMAL = new Style(PangoStyle.NORMAL, "NORMAL");

    /**
     * Text written in an elegant script, not just slanted over but usually a
     * more sculpted, rounded, and flowing appearance.
     */
    public static final Style ITALIC = new Style(PangoStyle.ITALIC, "ITALIC");

    /**
     * Normal text slanted over to the side but otherwise appearing the same
     * as the {@link #NORMAL NORMAL} roman style.
     */
    public static final Style OBLIQUE = new Style(PangoStyle.OBLIQUE, "OBLIQUE");
}
