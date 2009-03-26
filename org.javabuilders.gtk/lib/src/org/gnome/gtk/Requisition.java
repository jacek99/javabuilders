/*
 * Requisition.java
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

import org.gnome.glib.Boxed;

/**
 * The size that will be (is being) requested by a Widget. You get the
 * Requisition object for a given Widget by calling that its
 * {@link Widget#getRequisition() getRequisition()} method.
 * 
 * <p>
 * As a general principle across GTK programming, we encourage people to leave
 * Widgets alone to work out their own needs. You will <i>not</i> be able to
 * forecast the impact of a user's fonts, accessibility settings, and theme
 * engine, not to mention the actual implementation of the Containers you are
 * packing your Widget into. The great part is that you don't need to; the
 * GNOME libraries are really good at Doing The Right Thing (tm) and you will
 * do well by them.
 * 
 * <p>
 * When an occasion does arise where you need to force one or both dimensions,
 * you can either:
 * 
 * <ul>
 * <li>directly influence the size of an individual Widget using
 * {@link Widget#setSizeRequest(int, int) setSizeRequest()};
 * <li>use a {@link SizeGroup} to influence a set of related Widgets to have a
 * dimension in common; or
 * <li>if all you're trying to do is add padding, call Container's
 * {@link Container#setBorderWidth(int) setBorderWidth()}.
 * </ul>
 * 
 * <p>
 * GTK's box packing model works on a size-request/size-allocation process.
 * Each Widget is asked by it's parent Container how much space it wants on
 * the screen, in pixels. These <var>requests</var> are aggregated and in turn
 * represented up the tree until the top-level Window is reached. The Window
 * then negotiates with the window manager and with the X server, with the
 * result being that the Window is <var>allocated</var>. The Containers start
 * divvying up the allocation they were given amongst their children according
 * to whatever algorithms and settings are in place. Eventually your Widget
 * will be told how much space it has been given in it's {@link Allocation}
 * object, and it will have to then carry on accordingly. The important point
 * to note here is that requests are just that; a Widget must be able to cope
 * with any size down to <code>1x1</code>. Ideally it will degrade gracefully,
 * although that's not always easy.
 * 
 * <p>
 * <i>This object is a live reference to the <code>requisition</code> field in
 * the <code>GtkWidget</code> struct and so calling the getter methods will
 * yield correct current values once you've got the Requisition object for the
 * Widget you are interested in.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public final class Requisition extends Boxed
{
    Widget widget;

    protected Requisition(long pointer) {
        super(pointer);
    }

    protected void release() {
        widget = null;
    }

    /**
     * The width that has been requested.
     */
    public int getWidth() {
        return GtkRequisition.getWidth(this);
    }

    /**
     * The height that has been requested.
     */
    public int getHeight() {
        return GtkRequisition.getHeight(this);
    }

    public String toString() {
        return this.getClass().getName() + ": " + getWidth() + "x" + getHeight();
    }
}
