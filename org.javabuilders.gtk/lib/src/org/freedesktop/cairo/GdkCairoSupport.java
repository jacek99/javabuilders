/*
 * GdkCairoSupport.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.cairo;

import org.gnome.gdk.Drawable;
import org.gnome.gdk.Pixbuf;

/**
 * Hack to allow us to get at various gdk_cairo_*() functions.
 * 
 * @author Andrew Cowie
 */
/*
 * Playing with an alternate naming pattern, suffix "Support" for the really
 * weird corner cases. We are not, after all, overriding some capability in
 * CairoContext's generated layers.
 */
final class GdkCairoSupport extends Plumbing
{
    private GdkCairoSupport() {}

    static final long createContextFromDrawable(Drawable drawable) {
        if (drawable == null) {
            /*
             * This check is, unfortunately, particularly important. If you've
             * gotten this far with a null Drawable, that means that the state
             * you think you're in isn't what it should be - in otherwords,
             * you don't _really_ have a Drawable yet.
             */
            throw new IllegalArgumentException("drawable can't be null");
        }

        synchronized (lock) {
            return gdk_cairo_create(pointerOf(drawable));
        }
    }

    private static native final long gdk_cairo_create(long drawable);

    static final void setSourcePixbuf(Context self, Pixbuf pixbuf, double x, double y) {
        if (pixbuf == null) {
            throw new IllegalArgumentException("pixbuf can't be null");
        }

        synchronized (lock) {
            gdk_cairo_set_source_pixbuf(pointerOf(self), pointerOf(pixbuf), x, y);
        }
    }

    private static native final void gdk_cairo_set_source_pixbuf(long context, long pixbuf, double x,
            double y);
}
