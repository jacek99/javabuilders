/*
 * TextWindowType.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
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
 * Each TextView is actually composed from a number of underlying
 * [org.gnome.gdk] Windows. In addition to the core one where the text entry
 * happens, there are a number of optional Windows which can be used as side
 * panels. This class specifies constants identifying these underlying
 * resources.
 * 
 * <p>
 * The {@link #TEXT TEXT} Window is where most of the work happens; use
 * Textview's {@link TextView#getWindow(TextWindowType) getWindow(TEXT)}
 * rather than Widget's <code>getWindow()</code> and can be used if you need
 * to work out relative positioning.
 * 
 * <p>
 * On the other hand there are border windows, specified by <code>LEFT</code>,
 * <code>TOP</code>, etc. <b>These border Windows are an obscure, advanced,
 * and not wholly functional feature of GTK.</b> The accompanying illustration
 * is a TextView with each border window specified to a width of 50 pixels and
 * a Label placed at <i>window co-ordinates</i> <code>0</code>,<code>0</code>
 * in each one. <img class="snapshot" src="TextView-BorderWindows.png">
 * 
 * Code to create this snapshot consisted of a series of calls like:
 * 
 * <pre>
 * left = new Label(&quot;LEFT&quot;);
 * view.setBorderWindowSize(TextWindowType.LEFT, 50);
 * view.add(left, TextWindowType.LEFT, 0, 0);
 * </pre>
 * 
 * one for each side. Note the positions and relative sizes of the scrollbars
 * added by the ScrolledWindow into which the TextView was placed. As you can
 * see, only the left border window one makes very much sense, as it could be
 * used for things like breakpoint annotations in a programming editor.
 * 
 * <p>
 * <b>WARNING</b><br>
 * <i style="color:red">By all accounts, this feature is still evolving in
 * GTK; it is entirely possible that it was not completely implemented in the
 * first place, and in any case the interface for using it seems ... unsteady.
 * We have included this in java-gnome largely because we had already gone to
 * all the work of exposing it only to discover that it is exceedingly hard to
 * use and somewhat buggy. Hopefully GTK will improve, but if you use this,
 * expect the behaviour to be somewhat unpredictable.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.9
 */
public final class TextWindowType extends Constant
{
    private TextWindowType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * The optional border Window on the left hand side of a TextView. This is
     * the interesting one, as you can use it to place annotations relating to
     * the line of text being shown in the TextView beside it. Tying this to
     * scrolling (if enabled) is <b>very</b> tricky, though. See FIXME for an
     * example.
     * 
     * @since 4.0.9
     */
    public static TextWindowType LEFT = new TextWindowType(GtkTextWindowType.LEFT, "LEFT");

    /**
     * The optional border Window on the left hand side of a TextView.
     * 
     * @since 4.0.9
     */
    public static TextWindowType RIGHT = new TextWindowType(GtkTextWindowType.RIGHT, "RIGHT");

    /**
     * The optional border Window above a TextView.
     * 
     * @since 4.0.9
     */
    public static TextWindowType TOP = new TextWindowType(GtkTextWindowType.TOP, "TOP");

    /**
     * The optional border Window below a TextView.
     * 
     * @since 4.0.9
     */
    public static TextWindowType BOTTOM = new TextWindowType(GtkTextWindowType.BOTTOM, "BOTTOM");

    /**
     * The main part of the TextView which displays the text to the user. This
     * is, quite likely, the only Window you will need to get at.
     * 
     * @since 4.0.9
     */
    public static TextWindowType TEXT = new TextWindowType(GtkTextWindowType.TEXT, "TEXT");

    /*
     * Do we need PRIVATE or WIDGET?
     */
}
