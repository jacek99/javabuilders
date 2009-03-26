/*
 * Cursor.java
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

import org.gnome.glib.Boxed;

/*
 * FIXME this is a placeholder stub for what will become the public API for
 * this type. Replace this comment with appropriate javadoc including author
 * and since tags. Note that the class may need to be made abstract, implement
 * interfaces, or even have its parent changed. No API stability guarantees
 * are made about this class until it has been reviewed by a hacker and this
 * comment has been replaced.
 */
/**
 * That which indicateth where your mouse is pointing!
 * 
 * <p>
 * A Cursor object must be bound to a underlying Window before [changing it]
 * will actually cause what the user sees to change; see
 * {@link Window#setCursor(Cursor) setCursor()} on the Window here in
 * <code>org.gnome.gdk</code>.
 * 
 * <p>
 * Quite frequently you want to change the Cursor for the entire application
 * (in a manner reminiscent of modal behaviour). This is trickier than it
 * should be, but you've got a couple possibilities. You can either
 * <ul>
 * <li>maintain a list of all the significant <code>[org.gnome.gtk]</code>
 * Windows being displayed by your application and then call
 * <code>getWindow().setCursor(BLAH)</code> on each of them, or you can
 * <li>use {@link Window#getToplevels() getToplevels()} on
 * <code>[org.gnome.gdk]</code> Window and then similarly just call
 * <code>setCursor(BLAH)</code> as you iterate over the returned set.
 * </ul>
 * The first option is a bit more cumbersome, but many people find themselves
 * maintaining a list of "major" Windows for other purposes, so it can serve.
 * 
 * <p>
 * Almost inevitably the Cursor your want is {@link CursorType#WATCH WATCH}
 * which is the "busy" pointer. You can revert to "normal" by setting
 * {@link CursorType#LEFT_PTR LEFT_PTR} directly (which is the default cursor
 * you spend most of your time looking at), or by passing <code>null</code> to
 * <code>setCursor()</code>.
 * 
 * <p>
 * Note that different theme engines (let alone different Linux vendors) tend
 * to screw with the default pointer icons set quite a bit, so you may find
 * that pointers appear very different for users on different distributions.
 * 
 * <p>
 * <i>Our implementation of Cursor assumes you want to manipulate
 * <code>GdkCursors</code> on the "default" <code>GdkDisplay</code>. Where
 * else would you be working?</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public final class Cursor extends Boxed
{
    protected Cursor(long pointer) {
        super(pointer);
    }

    /**
     * Create a new Cursor with the specified CursorType.
     * 
     * @since 4.0.6
     */
    public Cursor(CursorType type) {
        super(GdkCursor.createCursor(type));
    }

    protected void release() {
        GdkCursor.unref(this);
    }
}
