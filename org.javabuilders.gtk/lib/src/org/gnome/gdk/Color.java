/*
 * Color.java
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

/**
 * Representation of an RGB colour. Used by GDK in drawing Widgets and related
 * elements.
 * 
 * <p>
 * <i>Regrettably, colour is spelt wrong in the underlying GTK library, so
 * sticking to our algorithmic API mapping we are forced to present it as
 * Color here.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
public final class Color extends Boxed
{
    protected Color(long pointer) {
        super(pointer);
    }

    /**
     * Construct a new Color object. The <code>red</code>, <code>green</code>,
     * and <code>blue</code> parameters take values <code>0</code> to
     * <code>65535</code>.
     */
    public Color(int red, int green, int blue) {
        super(GdkColorOverride.createColor(red, green, blue));
    }

    protected void release() {
        GdkColor.free(this);
    }

    /**
     * Get the red component of this Color.
     */
    public int getRed() {
        return GdkColor.getRed(this);
    }

    /**
     * Get the green component of this Color.
     */
    public int getGreen() {
        return GdkColor.getGreen(this);
    }

    /**
     * Get the blue component of this Color.
     */
    public int getBlue() {
        return GdkColor.getBlue(this);
    }

    public static final Color BLACK = new Color(0, 0, 0);

    public static final Color WHITE = new Color(65535, 65535, 65535);

    public static final Color RED = new Color(65535, 0, 0);

    public boolean equals(Object obj) {
        final Color other;

        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Color)) {
            return false;
        }

        other = (Color) obj;

        return GdkColor.equal(this, other);
    }
}
