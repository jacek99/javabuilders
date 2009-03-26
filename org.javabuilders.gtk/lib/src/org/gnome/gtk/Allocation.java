/*
 * Allocation.java
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
 * An object with information about the size of the rectangle that has been
 * allocated to a Widget and its position within its parent Container as a
 * result of consideration of the Widget's size request. See Widget's
 * {@link Widget#setSizeRequest(int, int) setSizeRequest()} for a more
 * detailed discussion of the size request-allocation process. You get the
 * Allocation currently given to a Widget with Widget's
 * {@link Widget#getAllocation() getAllocation()}.
 * 
 * <p>
 * Before the request-allocation process has occurred, you can expect this
 * class to report a size of <code>1</code>x<code>1</code> at position
 * <code>-1</code>,<code>-1</code>. You probably don't want to rely on those
 * numbers; but that's what the initial values are.
 * 
 * <p>
 * <i>This object is a live reference to the <code>allocation</code> field in
 * the <code>GtkWidget</code> struct and so calling the getter methods will
 * yield correct current values once you've got the Allocation object for the
 * Widget in question.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
/*
 * Our Allocation is a direct reference to the live GtkAllocation struct in
 * the GtkWidget, and not a dynamically allocated copy that will become out of
 * date. I don't know how well this will hold up but the back reference should
 * keep things safe.
 */
public final class Allocation extends Boxed
{
    /**
     * Hold a reference to the parent Widget so that the Allocation doesn't
     * survive longer than the Widget (ie, is collected first or at the same
     * time).
     */
    /*
     * If the Allocation came from GTK originally, then this won't be
     * populated, but there's not much we can do about that. FIXME, if you
     * expose Allocation in a signal handler, see about populating this field
     * in an override of the handler (like we do in Dialog for ResponseType).
     */
    Widget widget;

    protected Allocation(long pointer) {
        super(pointer);
    }

    protected void release() {
        widget = null;
    }

    /**
     * The width that has been allocated.
     */
    public int getWidth() {
        return GtkAllocation.getWidth(this);
    }

    /**
     * The height that has been allocated.
     */
    public int getHeight() {
        return GtkAllocation.getHeight(this);
    }

    /**
     * The horizontal co-ordinate of the top left corner of the rectangle.
     * This is relative to (ie, an offset from the top left corner of) the
     * parent's Allocation).
     */
    public int getX() {
        return GtkAllocation.getX(this);
    }

    /**
     * The vertical co-ordinate of the top left corner of the rectangle. This
     * is relative to (ie, an offset from the top left corner of) the parent's
     * Allocation).
     */
    public int getY() {
        return GtkAllocation.getY(this);
    }

    public String toString() {
        return this.getClass().getName() + ": " + getWidth() + "x" + getHeight() + " at " + getX() + ","
                + getY();
    }
}
