/*
 * Drawable.java
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

import org.gnome.glib.Object;

/**
 * Drawable is notable for being the parent class of both
 * <code>[org.gnome.gdk]</code> Window (representing the native server-side
 * on-screen resources behind a Widget) and <code>[org.gnome.gdk]</code>
 * Pixmap (a general server-side but off-screen area you can draw to).
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public abstract class Drawable extends Object
{
    protected Drawable(long pointer) {
        super(pointer);
    }

    /**
     * Get the bits per pixel of the data being used to back this Drawable.
     * 
     * @since 4.0.7
     */
    public int getDepth() {
        return GdkDrawable.getDepth(this);
    }

    Colormap getColormap() {
        return GdkDrawable.getColormap(this);
    }

    /**
     * Get the width of this Drawable.
     * 
     * @since 4.0.9
     */
    /*
     * TODO document the impact of this reporting the most recent
     * CONFIGURE_EVENT, not necesarily live X server information.
     */
    public int getWidth() {
        int[] width;

        width = new int[1];

        GdkDrawable.getSize(this, width, null);

        return width[0];
    }

    /**
     * Get the height of this Drawable.
     * 
     * @since 4.0.9
     */
    public int getHeight() {
        int[] height;

        height = new int[1];

        GdkDrawable.getSize(this, null, height);

        return height[0];
    }
}
