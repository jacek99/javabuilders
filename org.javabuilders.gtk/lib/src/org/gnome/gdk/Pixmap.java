/*
 * Pixmap.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

/**
 * An image as stored in the X server's memory.
 * 
 * <p>
 * This class is easily confused with {@link Pixbuf}, which is what you are
 * probably looking for. Pixbuf is used for representing images on the client
 * side in your application. Loading images and passing them for use in
 * Widgets such as Image and MenuItem is done there. Pixmap, on the other
 * hand, is a wrapper around a resource in the X server.
 * 
 * <p>
 * You should not generally need to use this class. When we do draw Widgets
 * with Cairo, we do so directly to a Window in the X server which it then
 * manages for getting on screen. If you really are looking to work directly
 * with a Pixmap, then see also {@link org.freedesktop.cairo.XlibSurface
 * XlibSurface}, which ultimately is how we get data into Drawables, though
 * there too we don't work with them directly but rather let Cairo and GDK do
 * the heavy lifting.
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
public class Pixmap extends Drawable
{
    protected Pixmap(long pointer) {
        super(pointer);
    }

    /**
     * Create a Pixmap with qualities matching that of an existing Drawable.
     * In other words, if you already have a GDK Window (ie, you're in a
     * Widget.ExposeEvent), you can create a Pixmap that will be compatible
     * with it by using this constructor.
     * 
     * @since 4.0.7
     */
    public Pixmap(Drawable example, int width, int height) {
        super(GdkPixmap.createPixmap(validateDrawable(example), width, height, -1));
    }

    /**
     * Create a new Pixmap with a given pixel depth.
     * 
     * @since 4.0.7
     */
    public Pixmap(int width, int height, int depth) {
        super(GdkPixmap.createPixmap(null, width, height, validateDepth(depth)));
        // GdkDrawable.setColormap(this,
        // GdkScreen.getDefault().getDefaultColormap());
        // GdkDrawable.setColormap(this,
        // GdkWindow.getDefaultRootWindow().getColormap());
    }

    static final Drawable validateDrawable(final Drawable pixmap) {
        if (pixmap == null) {
            throw new IllegalArgumentException("The reference drawable must be non-null");
        }
        return pixmap;
    }

    static final int validateDepth(final int depth) {
        if (depth < 1) {
            throw new IllegalArgumentException("depth has to be set");
        }
        return depth;
    }
}
