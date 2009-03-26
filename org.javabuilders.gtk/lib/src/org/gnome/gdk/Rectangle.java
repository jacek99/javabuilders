/*
 * Rectangle.java
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

import org.gnome.glib.Boxed;

/**
 * An object describing a rectangular area. While superficially similar to
 * {@link org.gnome.gtk.Allocation Allocation}, this class is in fact
 * different. It's primary use is in describing an area that has been exposed
 * and needs to be [re]drawn. You normally get one of these from the
 * {@link EventExpose#getArea() getArea()} method on EventExpose, though in
 * rare situations you need to describe an area based on your own calculations
 * and there is a constructor for that case.
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
public final class Rectangle extends Boxed
{
    protected Rectangle(long pointer) {
        super(pointer);
    }

    /**
     * Create a Rectanlge. This is principally used so that you can describe
     * an area that needs to be redrawn, passing it to the
     * {@link Window#invalidate(Rectangle, boolean) invalidate()} method of an
     * [org.gnome.gdk] Window.
     * 
     * <p>
     * As usual, measurements are in pixels.
     * 
     * @since 4.0.8
     */
    public Rectangle(int x, int y, int width, int height) {
        super(GdkRectangleOverride.createRectangle(x, y, width, height));
    }

    protected void release() {
        GdkRectangleOverride.free(this);
    }

    /**
     * The width of the box described by this Rectangle.
     * 
     * @since 4.0.7
     */
    public int getWidth() {
        return GdkRectangle.getWidth(this);
    }

    /**
     * The height of the box described by this Rectangle.
     * 
     * @since 4.0.7
     */
    public int getHeight() {
        return GdkRectangle.getHeight(this);
    }

    /**
     * The horizontal co-ordinate of the top left corner of the box described
     * by this Rectangle.
     * 
     * @since 4.0.7
     */
    public int getX() {
        return GdkRectangle.getX(this);
    }

    /**
     * The vertical co-ordinate of the box described by this Rectangle.
     * 
     * @since 4.0.7
     */
    public int getY() {
        return GdkRectangle.getY(this);
    }

    public String toString() {
        return this.getClass().getSimpleName() + ": " + getWidth() + "x" + getHeight() + " at " + getX()
                + "," + getY();
    }
}
