/*
 * CursorType.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

import org.freedesktop.bindings.Flag;

/**
 * Constants representing the different forms of Cursor that can indicate
 * where the mouse is pointing. Note that this is distinct from that blinking
 * vertical bar that is the text position cursor in Entry and TextView
 * Widgets.
 * 
 * <p>
 * The "normal" Cursor is {@link #LEFT_PTR LEFT_PTR}. That's the one you want
 * to switch back to if you've changed the Cursor to something exceptional.
 * 
 * <p>
 * <i>The fact that "cursor" is used to name this class and the Cursor class
 * which manipulates them is a bit strange seeing as how the term used in user
 * interface design for the thing where your mouse is indicating is
 * "pointer".</i>
 * 
 * <p>
 * <i>It was quite tempting to mess with the constant names here; after all
 * <code>LEFT_PTR</code> is a poor substitute for "normal", but that would
 * screw up the algorithmic mapping of the underlying library that we have
 * worked so hard to maintain. The names are less than ideal in GDK, but so be
 * it.</i>.
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
/*
 * The underlying enum is a zoo, so this is just a start. Frankly, most of the
 * preexisting Cursor constants are completely unnecessary cruft left over
 * from the early days of X Windows. Yeech! There are some Cursors that still
 * have modern application, but I've skipped things like the Window resizing
 * handles because, yo, your program isn't a window manager. Someone else can
 * add other CursorTypes if they really need one.
 */
public final class CursorType extends Flag
{
    private CursorType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * The standard arrow pointer used by default for most user interface
     * purposes. This is the one you want to reset the cursor to if you have
     * been using {@link #WATCH WATCH} as the "busy" pointer.
     */
    public static final CursorType LEFT_PTR = new CursorType(GdkCursorType.LEFT_PTR, "LEFT_PTR");

    /**
     * This is the cursor you change to when you want to show that the
     * application is "busy"; be aware that different themes shipped by
     * various Linux distributions override it in many different ways.
     * 
     * @since 4.0.6
     */
    public static final CursorType WATCH = new CursorType(GdkCursorType.WATCH, "WATCH");

    /**
     * This is the standard "text" Cursor, used in Widgets which display or
     * edit text such as Entry and TextView.
     * 
     * @since 4.0.6
     */
    public static final CursorType XTERM = new CursorType(GdkCursorType.XTERM, "XTERM");
}
