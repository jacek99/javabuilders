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
package org.gnome.pango;

import org.gnome.glib.Boxed;

/**
 * Information about the size of an area rendered by Pango. These are returned
 * by the various extents methods in Layout and related classes. See
 * LayoutLine's {@link LayoutLine#getExtentsLogical() getExtentsLogical()} for
 * an example.
 * 
 * <p>
 * The origin of a Rectangle is the base line of the rendered glyphs, with
 * positive directions being to the right and down. This means that in
 * left-to-right text, a Rectangle representing a glyph that lies above the
 * base line (which most do) will have a negative <code>y</code> value.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
public final class Rectangle extends Boxed
{
    protected Rectangle(long pointer) {
        super(pointer);
    }

    Rectangle() {
        super(PangoRectangleOverride.createRectangle());
    }

    protected void release() {
        PangoRectangleOverride.free(this);
    }

    /**
     * The width of the box described by this Rectangle.
     * 
     * @since 4.0.10
     */
    public double getWidth() {
        return PangoRectangle.getWidth(this) / Pango.SCALE;
    }

    /**
     * The height of the box described by this Rectangle.
     * 
     * @since 4.0.10
     */
    public double getHeight() {
        return PangoRectangle.getHeight(this) / Pango.SCALE;
    }

    /**
     * The horizontal co-ordinate of the top left corner of the box described
     * by this Rectangle.
     * 
     * @since 4.0.10
     */
    public double getX() {
        return PangoRectangle.getX(this) / Pango.SCALE;
    }

    /**
     * The vertical co-ordinate of the box described by this Rectangle.
     * 
     * @since 4.0.10
     */
    public double getY() {
        return PangoRectangle.getY(this) / Pango.SCALE;
    }

    /*
     * these are a series of conveniences and equate to various C side macros.
     */

    /**
     * Get the <var>ascent</var>, which is the distance that this Rectangle
     * [describing one or more glyphs] rises above the font's base line.
     * 
     * @since 4.0.10
     */
    public double getAscent() {
        return -getY();
    }

    /**
     * Get the <var>descent</var>, which is the distance that this Rectangle
     * [describing one or more glyphs] descends below the font's base line.
     * 
     * @since 4.0.10
     */
    public double getDescent() {
        return getHeight() + getY();
    }

    private static String oneDecimal(double d) {
        return String.format("%1.1f", d);
    }

    public String toString() {
        return this.getClass().getSimpleName() + ": " + oneDecimal(getWidth()) + "x"
                + oneDecimal(getHeight()) + " at " + oneDecimal(getX()) + "," + oneDecimal(getY());
    }
}
