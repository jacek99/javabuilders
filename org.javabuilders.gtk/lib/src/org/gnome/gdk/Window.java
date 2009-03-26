/*
 * Window.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

/**
 * The underlying native resource driving a rectangular region on a screen.
 * These are notable as being what powers the display of Widgets, being both
 * that upon which drawing is done, and that to which user actions are
 * delivered.
 * 
 * <p>
 * In theory, some, but not all, Widgets have their own native windows to draw
 * upon and from whence events originate. Thus Widgets such as Buttons have
 * their own underlying native resource, whereas Labels and various Containers
 * do not. In practise, this distinction is blurred and newer versions of GTK
 * do all sorts of strange things under the hood for greater efficiency. You
 * don't need to worry about any of this.
 * 
 * <p>
 * These are wrappers around Xlib's <code>Window</code> object. They can be
 * arranged in tree structures, wrapping and overlapping one another, with
 * parents cropping children, etc. You don't need to worry about any of this
 * either.
 * 
 * <p>
 * What this <i>is</i> useful for is as a way to get to the state of top level
 * windows and various low level drawing functions.
 * 
 * <p>
 * <i>Since the C name of this class is <code>GdkWindow</code>, the
 * unavoidable consequence of the java-gnome mapping algorithm is that the
 * name of this class Java is Window. This can be a bit of a pain if you're
 * working in a piece of code where</i> <code>org.gnome.gtk.Window</code>
 * <i>is already imported, but c'est la vie.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.4
 */
public class Window extends Drawable
{
    protected Window(long pointer) {
        super(pointer);
    }

    /**
     * Return a flags object representing the current state of the Window
     * (maximized, iconified, etc). See {@link WindowState WindowState} for
     * the individual constants, obviously, but be aware that an unmapped
     * Window will have no WindowState bits set.
     * 
     * @since 4.0.4
     */
    public WindowState getState() {
        return GdkWindow.getState(this);
    }

    /**
     * Set the Cursor that will be shown when the pointer hovers over this
     * Drawable Window.
     * 
     * @param cursor
     *            Passing <code>null</code> will cause this Window to [revert
     *            to] using the Cursor default inherited from its parent.
     * @since 4.0.6
     */
    public void setCursor(Cursor cursor) {
        GdkWindow.setCursor(this, cursor);
    }

    /**
     * Get the horizontal position of this Window in terms of the root
     * Window's co-ordinates. The root window is what you might think of as
     * the desktop background, although in X terms it really is the parent of
     * all windows.
     * 
     * @since 4.0.6
     */
    public int getOriginX() {
        final int x[], y[];

        x = new int[1];
        y = new int[1];

        GdkWindow.getOrigin(this, x, y);

        return x[0];
    }

    /**
     * Get the vertical position of this Window in terms of the root Window's
     * co-ordinates.
     * 
     * @since 4.0.6
     */
    public int getOriginY() {
        final int x[], y[];

        x = new int[1];
        y = new int[1];

        GdkWindow.getOrigin(this, x, y);

        return y[0];
    }

    /**
     * Get the horizontal position of this Window relative to its parent
     * Window. Given that many Widgets draw directly on their parent's
     * <code>org.gnome.gdk</code> Window, you may at times be surprised at
     * what this reports.
     * 
     * @since 4.0.6
     */
    public int getPositionX() {
        final int x[], y[];

        x = new int[1];
        y = new int[1];

        GdkWindow.getPosition(this, x, y);

        return x[0];
    }

    /**
     * Get the vertical position of this Window relative to its parent Window.
     * 
     * @since 4.0.6
     */
    public int getPositionY() {
        final int x[], y[];

        x = new int[1];
        y = new int[1];

        GdkWindow.getPosition(this, x, y);

        return y[0];
    }

    /**
     * Mark the given area as damaged and needing redrawing. Calling this
     * method will ultimately result in <code>Widget.ExposeEvent</code> being
     * emitted on Widgets that are present in the area being invalidated.
     * 
     * @param recursive
     *            If <code>true</code>, calling this method will invalidate
     *            not only the described area in this [org.gnome.gdk] Window,
     *            but also the corresponding areas of any child
     *            [org.gnome.gdk] Windows that overlap it. This is mostly an
     *            implementation detail, but occasionally you need to find
     *            tune the control. We tend to use <code>true</code>.
     * @since 4.0.8
     */
    /*
     * TODO this needs a much stronger description, linked to wherever else we
     * end up discussing drawing, regions, and invalidation.
     */
    /*
     * If we expose Region then there will be an invalidate(Region, boolean)
     * in due course corresponding to this method, hence the name change to
     * invalidate().
     */
    public void invalidate(Rectangle area, boolean recursive) {
        GdkWindow.invalidateRect(this, area, recursive);
    }

    /**
     * Has this underlying resouce been mapped? This will return
     * <code>true</code> if <code>show()</code> has been called on the Widget
     * that draws on this [org.gnome.gdk] Window <i>and on all its parents, if
     * this Window happens to be a sub-Window.</i>
     * 
     * @since 4.0.10
     */
    public boolean isViewable() {
        return GdkWindow.isViewable(this);
    }
}
