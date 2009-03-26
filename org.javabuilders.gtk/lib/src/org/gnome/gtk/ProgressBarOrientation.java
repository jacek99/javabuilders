/*
 * ProgressBarOrientation.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd, and Others
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
 * Constants that specify the direction a ProgressBar is to progress. The
 * default you'll be accustomed to seeing is {@link #LEFT_TO_RIGHT
 * LEFT_TO_RIGHT}.
 * 
 * @since 4.0.10
 * @author Miloud Bel
 */
public final class ProgressBarOrientation extends Constant
{
    /**
     * Perform progress from left to right
     */
    public static final ProgressBarOrientation LEFT_TO_RIGHT = new ProgressBarOrientation(
            GtkProgressBarOrientation.LEFT_TO_RIGHT, "LEFT_TO_RIGHT");

    /**
     * Perform progress from right to left
     */
    public static final ProgressBarOrientation RIGHT_TO_LEFT = new ProgressBarOrientation(
            GtkProgressBarOrientation.RIGHT_TO_LEFT, "RIGHT_TO_LEFT");

    /**
     * Perform progress from top to bottom
     */
    public static final ProgressBarOrientation TOP_TO_BOTTOM = new ProgressBarOrientation(
            GtkProgressBarOrientation.TOP_TO_BOTTOM, "TOP_TO_BOTTOM");

    /**
     * Perform progress from bottom to top
     */
    public static final ProgressBarOrientation BOTTOM_TO_TOP = new ProgressBarOrientation(
            GtkProgressBarOrientation.BOTTOM_TO_TOP, "BOTTOM_TO_TOP");

    private ProgressBarOrientation(int ordinal, String nickname) {
        super(ordinal, nickname);
    }
}
