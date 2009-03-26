/*
 * WrapMode.java
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
 * Indicate if you want a TextView to wrap, and if so, where it should break
 * lines. These constants are used with TextView's
 * {@link TextView#setWrapMode(WrapMode) setWrapMode()} method.
 * 
 * @author Stefan Prelle
 * @author Andrew Cowie
 * @since 4.0.9
 */
public final class WrapMode extends Constant
{
    private WrapMode(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Do not wrap lines; attempt to make the TextView as wide as the widest
     * line in the underlying TextBuffer; lines will be truncated if they are
     * longer than the width allocated to the TextView.
     */
    public static final WrapMode NONE = new WrapMode(GtkWrapMode.NONE, "NONE");

    /**
     * Wrap text, breaking lines anywhere the cursor can appear (between
     * characters, usually)
     */
    public static final WrapMode CHAR = new WrapMode(GtkWrapMode.CHAR, "CHAR");

    /**
     * Wrap text, breaking lines in between words. This is usually the one you
     * want if you are trying to enable word wrapping, but keep in mind that
     * something <i>also</i> has to act to constrain the width of the TextView
     * as it is packed into a Container hierarchy or no wrapping will occur.
     */
    public static final WrapMode WORD = new WrapMode(GtkWrapMode.WORD, "WORD");

    /**
     * Wrap text, breaking lines in between words, or if that is not enough,
     * also between graphemes. FIXME What is a grapheme?
     */
    public static final WrapMode WORD_CHAR = new WrapMode(GtkWrapMode.WORD_CHAR, "WORD_CHAR");

}
