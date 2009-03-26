/*
 * Misc.java
 *
 * Copyright (c) 2006 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * Base class for Widgets that have notions of alignment and padding.
 * 
 * @author Andrew Cowie
 * @author Nat Pryce
 * @since 4.0.0
 */
public abstract class Misc extends Widget
{
    protected Misc(long pointer) {
        super(pointer);
    }

    /**
     * Set the horizontal and vertical alignment attributes, enabling the
     * Widget to be positioned within its allocated area. Note that if the
     * Widget is added to a Container in such a way that it expands
     * automatically to fill its allocated area, the alignment settings will
     * not alter the Widget's position.
     * 
     * @param xalign
     *            the horizontal alignment, from <code>0.0f</code> (full left)
     *            to <code>1.0f</code> (full right).
     * @param yalign
     *            the vertical alignment, from <code>0.0f</code> (top) to
     *            <code>1.0f</code> (bottom).
     * @since 4.0.4
     */
    /*
     * TODO add reference to Container (or where ever) when we get around to
     * documenting the Request to Allocation process.
     */
    public void setAlignment(float xalign, float yalign) {
        GtkMisc.setAlignment(this, xalign, yalign);
    }

    /**
     * Gets the horizontal alignment of the Widget within its allocation. See
     * {@link #setAlignment(float, float) setAlignment()}.
     * 
     * @since 4.0.4
     */
    public float getAlignmentX() {
        float[] xalign = new float[1];
        float[] yalign = new float[1];
        GtkMisc.getAlignment(this, xalign, yalign);
        return xalign[0];
    }

    /**
     * Gets the vertical alignment of the Widget within its allocation. See
     * {@link #setAlignment(float, float) setAlignment()}.
     * 
     * @since 4.0.4
     */
    public float getAlignmentY() {
        float[] xalign = new float[1];
        float[] yalign = new float[1];
        GtkMisc.getAlignment(this, xalign, yalign);
        return yalign[0];
    }

    /**
     * Set the amount of extra horizontal and vertical padding space to added
     * around the Widget.
     * 
     * @param xpad
     *            the amount of space to add on the left and right of the
     *            Widget, in pixels.
     * @param ypad
     *            the amount of space to add on the top and bottom of the
     *            Widget, in pixels.
     * @since 4.0.4
     */
    public void setPadding(int xpad, int ypad) {
        GtkMisc.setPadding(this, xpad, ypad);
    }

    /**
     * Returns the horizontal padding of the Widget, in pixels.
     * 
     * @since 4.0.4
     */
    public int getPaddingX() {
        int[] xpad = new int[1];
        int[] ypad = new int[1];
        GtkMisc.getPadding(this, xpad, ypad);
        return xpad[0];
    }

    /**
     * Returns the vertical padding of the Widget, in pixels.
     * 
     * @since 4.0.4
     */
    public int getPaddingY() {
        int[] xpad = new int[1];
        int[] ypad = new int[1];
        GtkMisc.getPadding(this, xpad, ypad);
        return ypad[0];
    }
}
