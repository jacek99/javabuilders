/*
 * ScrolledWindow.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * Add scrollbars to a Widget. There are times when you have a Widget which is
 * larger than the area you wish to constrain it to, and the usual way to deal
 * with this is to enhance the Widget with scrollbars. ScrolledWindow is a Bin
 * which enables you to control the scrollbars added in such cases.
 * 
 * <p>
 * Some Widgets have built in support for scrolling; in such cases you add
 * them directly with the usual {@link Container#add(Widget) add()} method;
 * however, if other Widgets need to be enhanced to support scrolling; in such
 * cases you must nest it inside a Viewport; use
 * {@link #addWithViewport(Widget) addWithViewport()} as a quick way to
 * achieve this.
 * 
 * <p>
 * <i>This is very poorly named. It is <b>not</b> a subclass of Window; it
 * refers instead to a viewport scrolling around on an underlying canvas.</i>
 * 
 * @author Sebastian Mancke
 * @author Andrew Cowie
 * @since 4.0.3
 */
/*
 * TODO document the relationship between the subordinate Adjustments and
 * Scrollbars.
 */
public class ScrolledWindow extends Bin
{
    protected ScrolledWindow(long pointer) {
        super(pointer);
    }

    /**
     * Construct a ScrolledWindow. This will automatically create default
     * Adjustments for managing the horizontal and vertical ScrollBars.
     * 
     * @since 4.0.3
     */
    public ScrolledWindow() {
        // call createScrolledWindow and let it create the adjustments
        super(GtkScrolledWindow.createScrolledWindow(null, null));
    }

    /**
     * Construct a ScrolledWindow with the specified Adjustments
     */
    /*
     * FIXME This doesn't work, at least not the way I expect it to. In fact,
     * every time I've tried it the other ScrolledWindow whose Adjustments I
     * borrow breaks. If someome can figure out the proper use of this, and
     * document it, we can make this public.
     */
    ScrolledWindow(Adjustment hadjustment, Adjustment vadjustment) {
        super(GtkScrolledWindow.createScrolledWindow(hadjustment, vadjustment));
    }

    /**
     * Set the scrollbar policy for the horizontal and vertical scrollbars.
     * 
     * @since 4.0.3
     */
    public void setPolicy(PolicyType hscrollbarPolicy, PolicyType vscrollbarPolicy) {
        GtkScrolledWindow.setPolicy(this, hscrollbarPolicy, vscrollbarPolicy);
    }

    /**
     * Create a new Viewport and embeds the child Widget in it before adding
     * it to the ScrolledWindow. This is a convenience function; you could
     * always create the Viewport yourself if you really wanted. Note that
     * this method is only for Widgets which do not support scrolling directly
     * themselves; use {@link Container#add(Widget) add()} directly for those
     * Widgets that do.
     */
    public void addWithViewport(Widget child) {
        if ((child instanceof TextView) || (child instanceof TreeView) || (child instanceof Layout)) {
            // any others?
            throw new IllegalArgumentException(
                    "You must not addWithViewport() a Widget that already has scrolling support built in. Use Container's add() instead.");
        }
        GtkScrolledWindow.addWithViewport(this, child);
    }

    /**
     * Get the Scrollbar Widget that is being used to draw the horizontal
     * scroll bar on the bottom edge of this ScrolledWindow.
     * 
     * @since 4.0.8
     */
    public Scrollbar getHScrollbar() {
        return (Scrollbar) GtkScrolledWindow.getHscrollbar(this);
    }

    /**
     * Get the Scrollbar Widget that is being used to draw the vertical scroll
     * bar on the right hand side of this ScrolledWindow.
     * 
     * @since 4.0.8
     */
    public Scrollbar getVScrollbar() {
        return (Scrollbar) GtkScrolledWindow.getVscrollbar(this);
    }

    /**
     * Get the Adjustment that is being used to drive the horizontal position
     * of the scroll bar on the bottom edge of this ScrolledWindow.
     * 
     * @since 4.0.8
     */
    public Adjustment getHAdjustment() {
        return GtkScrolledWindow.getHadjustment(this);
    }

    /**
     * Get the Adjustment that is being used to drive the vertical position of
     * the scroll bar on the right hand side of this ScrolledWindow.
     * 
     * @since 4.0.8
     */
    public Adjustment getVAdjustment() {
        return GtkScrolledWindow.getVadjustment(this);
    }

    /**
     * Set the type of decoration you want around the child Widget in the
     * ScrolledWindow.
     */
    /*
     * FIXME This doesn't work!?! GTK bug?
     */
    void setShadowType(ShadowType type) {
        GtkScrolledWindow.setShadowType(this, type);
    }
}
