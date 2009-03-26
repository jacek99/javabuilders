/*
 * Layout.java
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

/**
 * A Container that is able to hold present a limited view of Widgets laid out
 * on a larger canvas. Layouts are designed for scrolling and should be added
 * to a ScrolledWindow with {@link ScrolledWindow#add(Widget) add()}.
 * 
 * <p>
 * Note that this Widget has the same limitations as Fixed due to it
 * positioning child Widgets by co-ordinates and not by box packing (no layout
 * management, no resizing, likelihood of rendering collisions, etc). You are
 * better off adding a proper Container and adding scrolling with
 * ScrolledWindow's {@link ScrolledWindow#addWithViewport(Widget)
 * addWithViewport()}.
 * 
 * @author Andrew Cowie
 * @since 4.0.8
 */
/*
 * FIXME this is a placeholder stub for what will become the public API for
 * this type. Replace this comment with appropriate javadoc including author
 * and since tags. Note that the class may need to be made abstract, implement
 * interfaces, or even have its parent changed. No API stability guarantees
 * are made about this class until it has been reviewed by a hacker and this
 * comment has been replaced.
 */
public class Layout extends Container
{
    protected Layout(long pointer) {
        super(pointer);
    }

    /**
     * Construct a Layout with default adjustments.
     * 
     * @since 4.0.8
     */
    public Layout() {
        // call createScrolledWindow and let it create the adjustments
        super(GtkLayout.createLayout(null, null));
    }

    /**
     * Construct a Layout, supplying the Adjustment object used to control the
     * panning. You'd use this if you are manually managing the Adjustment
     * values yourself.
     * 
     * @since 4.0.8
     */
    public Layout(Adjustment hadjustment, Adjustment vadjustment) {
        super(GtkLayout.createLayout(hadjustment, vadjustment));
    }

    /**
     * Add a child Widget at the specified co-ordinates.
     * 
     * @since 4.0.8
     */
    public void put(Widget child, int x, int y) {
        GtkLayout.put(this, child, x, y);
    }

    /**
     * Relocate a child Widget to the specified co-ordinates.
     * 
     * @since 4.0.8
     */
    public void move(Widget child, int x, int y) {
        GtkLayout.move(this, child, x, y);
    }

    /**
     * Set the total extent of the canvas that this Layout will scroll its
     * viewport over.
     * 
     * @since 4.0.8
     */
    public void setSize(int width, int height) {
        GtkLayout.setSize(this, width, height);
    }

    /**
     * Get the Adjustment that is being used to drive the horizontal position
     * of the viewport showing over this Layout's canvas.
     * 
     * @since 4.0.8
     */
    public Adjustment getHAdjustment() {
        return GtkLayout.getHadjustment(this);
    }

    /**
     * Get the Adjustment that is being used to drive the vertical position of
     * the viewport showing over this Layout's canvas.
     * 
     * @since 4.0.8
     */
    public Adjustment getVAdjustment() {
        return GtkLayout.getVadjustment(this);
    }
}
