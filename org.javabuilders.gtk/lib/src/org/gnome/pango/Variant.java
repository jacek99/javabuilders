/*
 * Variant.java
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
 * Capitalization variant of the font.
 * 
 * <p>
 * This controls how lower case characters are displayed: either as normal
 * lowercase characters or as small capital characters.
 * 
 * @author Vreixo Formoso
 * @since 4.0.8
 */
public final class Variant extends Constant
{
    private Variant(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Normal font.
     */
    public final static Variant NORMAL = new Variant(PangoVariant.NORMAL, "NORMAL");

    /**
     * A font with the lower case characters replaced by smaller variants of
     * the capital characters.
     */
    public final static Variant SMALL_CAPS = new Variant(PangoVariant.SMALL_CAPS, "SMALL_CAPS");
}
