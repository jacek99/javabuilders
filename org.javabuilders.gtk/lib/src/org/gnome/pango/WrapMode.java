/*
 * WrapMode.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd
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
 * Indicate how a Pango Layout is to wrap. Set the mode with Layout's
 * {@link Layout#setWrapMode(WrapMode) setWrapMode()} method.
 * 
 * <p>
 * <i>To our great frustration, these constants exist separately from the ones
 * used by GTK's TextView even though, quite clearly, the WrapMode Constants
 * there map to the ones here. Unfortunately the ordinals are different, so we
 * can't just present one set.</i>
 * 
 * @since 4.0.11
 */
public final class WrapMode extends Constant
{
    private WrapMode(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Wrap text, breaking lines anywhere the cursor can appear (between
     * characters, usually)
     */
    public static final WrapMode CHAR = new WrapMode(PangoWrapMode.CHAR, "CHAR");

    /**
     * Wrap text, breaking lines in between words. This is usually the one you
     * want if you are trying to enable word wrapping, but keep in mind that
     * you also must have set the width of the Layout with its
     * {@link Layout#setWidth(double) setWidth()}.
     */
    public static final WrapMode WORD = new WrapMode(PangoWrapMode.WORD, "WORD");

    /**
     * Wrap at word boundaries unless there isn't enough space for a full
     * word, in which case fall back to wrapping at a character boundary.
     */
    /*
     * TODO How is this different from CHAR?
     */
    public static final WrapMode WORD_CHAR = new WrapMode(PangoWrapMode.WORD_CHAR, "WORD_CHAR");
}
