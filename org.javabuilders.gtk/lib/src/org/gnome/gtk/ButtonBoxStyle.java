/*
 * ButtonBoxStyle.java
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
 * Control layout of Buttons in a {@link ButtonBox}. In general you shouldn't
 * need to use this too much; in GNOME we leave presentation style decisions
 * up to the the theme engine currently selected by the user.
 * 
 * @author Nat Pryce
 * @see HButtonBox
 * @see VButtonBox
 * @since 4.0.4
 */
public final class ButtonBoxStyle extends Constant
{
    /**
     * Default packing, allowing the theme engine to control the presentation
     * of ButtonBoxes.
     */
    public static final ButtonBoxStyle DEFAULT_STYLE = new ButtonBoxStyle(
            GtkButtonBoxStyle.DEFAULT_STYLE, "DEFAULT_STYLE");

    /**
     * Buttons are to be evenly spread across the Box.
     */
    public static final ButtonBoxStyle SPREAD = new ButtonBoxStyle(GtkButtonBoxStyle.SPREAD, "SPREAD");

    /**
     * Buttons are to be placed at the edges of the Box.
     */
    public static final ButtonBoxStyle EDGE = new ButtonBoxStyle(GtkButtonBoxStyle.EDGE, "EDGE");

    /**
     * Buttons are to be grouped towards the start of the box (on the left for
     * a HButtonBox, or the top for a VButtonBox).
     */
    public static final ButtonBoxStyle START = new ButtonBoxStyle(GtkButtonBoxStyle.START, "START");

    /**
     * Buttons are to be grouped towards the end of the box (on the right for
     * a HButtonBox, or the bottom for a VButtonBox).
     */
    public static final ButtonBoxStyle END = new ButtonBoxStyle(GtkButtonBoxStyle.END, "END");

    private ButtonBoxStyle(int ordinal, String nickname) {
        super(ordinal, nickname);
    }
}
