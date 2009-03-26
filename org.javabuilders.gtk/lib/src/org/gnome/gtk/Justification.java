/*
 * Justification.java
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
 * Constants that represent the justification of text. Most notably, this is
 * used by Label; see its {@link Label#setJustify(Justification) setJustify()}
 * .
 * 
 * @author Nat Pryce
 * @author Andrew Cowie
 * @since 4.0.4
 */
/*
 * TODO turns up in TextView too; perhaps add mention if significant.
 */
public final class Justification extends Constant
{
    /**
     * Indicate text should justify to the left edge of the Label. This is the
     * default with newly created Labels.
     */
    public static final Justification LEFT = new Justification(GtkJustification.LEFT, "LEFT");

    /**
     * Indicate text should be justified to the right edge of the Label.
     */
    public static final Justification RIGHT = new Justification(GtkJustification.RIGHT, "RIGHT");

    /**
     * Indicate text should be justified to the center of the Label.
     */
    public static final Justification CENTER = new Justification(GtkJustification.CENTER, "CENTER");

    /**
     * Indicate text should be distributed evenly across the width of Label.
     * This is sometimes known as "proper" or "even" justification.
     */
    /*
     * TODO couldn't duplicate this behaviour, even with setLineWrap(true).
     * That would seem to indicate we're missing some aspect or prerequisite
     * necessary for this justification mode.
     */
    public static final Justification FILL = new Justification(GtkJustification.FILL, "FILL");

    private Justification(int ordinal, String nickname) {
        super(ordinal, nickname);
    }
}
