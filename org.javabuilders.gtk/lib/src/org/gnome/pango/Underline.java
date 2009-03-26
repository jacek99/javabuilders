/*
 * Underline.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.pango;

import org.freedesktop.bindings.Constant;

/*
 * FIXME this is a placeholder stub for what will become the public API for
 * this type. Replace this comment with appropriate javadoc including author
 * and since tags. Note that the class may need to be made abstract, implement
 * interfaces, or even have its parent changed. No API stability guarantees
 * are made about this class until it has been reviewed by a hacker and this
 * comment has been replaced.
 */
/**
 * Constants specifying the type of underlining to be used in a given span of
 * text.
 * 
 * @author Andrew Cowie
 * @since 4.0.9
 */
public final class Underline extends Constant
{
    private Underline(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * No underline.
     */
    public static final Underline NONE = new Underline(PangoUnderline.NONE, "NONE");

    /**
     * A single horizontal underline stroke below the text.
     */
    public static final Underline SINGLE = new Underline(PangoUnderline.SINGLE, "SINGLE");

    /**
     * A double underline stroke below the text.
     */
    public static final Underline DOUBLE = new Underline(PangoUnderline.DOUBLE, "DOUBLE");

    /**
     * An interesting special case of single underlining, provide an underline
     * decoration that is absolutely below and clear of the bottom edge of the
     * drawn glyphs. This is exclusively intended for marking up mnemonic
     * characters in Labels.
     * 
     * <p>
     * Note that this is <b>not</b> to be applied for extended spans of text.
     * Use {@link #SINGLE SINGLE} for normal single underlining.
     */
    public static final Underline LOW = new Underline(PangoUnderline.LOW, "LOW");

    /**
     * The squiggly underline typically used to note spelling mistakes or
     * compile errors.
     */
    public static final Underline ERROR = new Underline(PangoUnderline.ERROR, "ERROR");
}
