/*
 * ImageSurface.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.cairo;

/**
 * A Surface which is an image in memory and can be written to disk. If
 * drawing to an image you intend to write to a PNG, you would end up doing
 * something like:
 * 
 * <pre>
 * surface = new ImageSurface(Format.ARGB32, 100, 100);
 * cr = new Context(surface);
 * 
 * // do drawing 
 * 
 * surface.writeToPNG(filename);
 * </pre>
 * 
 * <p>
 * While ImageSurfaces are good for writing images out to disk, they are not
 * optimized per se to be efficient as a back end, nor are they accelerated by
 * your graphics card. So they are not an appropriate intermediate in drawing
 * operations; don't be calling <code>setSource()</code> on one of these.
 * 
 * <p>
 * More importantly, ImageSurface is <b>not</b> an image loader! Remember that
 * Surfaces are what Cairo draws <i>to</i>. If what you are doing is building
 * up images for display to the screen, then load your images into
 * {@link XlibSurface}s and use those as sources.
 * 
 * <p>
 * <i>Deep in the guts, Cairo's ImageSurface is like GDK's Pixbuf, a format
 * that C programmers can directly address directly in memory via pointers.
 * That's useful for very low level programming, but not needed for
 * application development. If you're drawing, use Cairo's higher level
 * drawing primitives; if you need to introspect an image, then load it with
 * Pixbuf and use Pixbuf's</i> {@link org.gnome.gdk.Pixbuf#getPixels()
 * getPixels()} <i>to peek around in its data.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
public class ImageSurface extends Surface
{
    protected ImageSurface(long pointer) {
        super(pointer);
    }

    /**
     * Construct an ImageSurface of the specified visual depth and size.
     * 
     * @since 4.0.7
     */
    public ImageSurface(Format format, int width, int height) {
        super(CairoSurface.createSurface(format, width, height));
        checkStatus();
    }
}
