/*
 * PixbufFormat.java
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

/**
 * Constants representing the image file formats that GDK is capable of
 * writing to. This is used by Pixbuf's
 * {@link Pixbuf#save(String, PixbufFormat) save()}.
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
/*
 * This is a total hack at the moment. There is a GdkPixbufFormat, but we'll
 * need to clean things up so that these constants are actually properly
 * instantiated from the underlying structs. The only thing that will stay the
 * same is that the constants here will be for the writable formats only.
 */
public class PixbufFormat // FIXME is actually a Boxed
{
    // TODO drop this and fetch authentic data
    private String name;

    private PixbufFormat(String type) {
        this.name = type;
    }

    // TODO ok to make public
    String getName() {
        return name;
    }

    /*
     * This will all have to be redone when we get the real GdkPixbufFormat
     * hooked up.
     */

    /**
     * The Portable Network Graphic image format. The filename extension for
     * PNGs is <code>.png</code> and should be used when saving images of this
     * type.
     */
    public static PixbufFormat PNG = new PixbufFormat("png");

    /**
     * The Joint Photographic Group image format. Often used for digital
     * photographs, JPEG uses frequency compression to store enough of the
     * image as to be able to capture its essence, but does so using a
     * transform that is lossy. The common filename extension is
     * <code>.jpg</code>.
     */
    public static PixbufFormat JPEG = new PixbufFormat("jpeg");

    /**
     * The Tagged Image File Format, used as a container format for storing
     * images including photographs and line art. It is a lossless format, but
     * is incredibly space-inefficient. Images saved in this format should be
     * given the filename extension <code>.tiff</code>.
     * 
     * @see <a href="http://en.wikipedia.org/wiki/TIFF">TIFF entry at
     *      Wikipedia</a>
     */
    public static PixbufFormat TIFF = new PixbufFormat("tiff");

    public static PixbufFormat ICO = new PixbufFormat("ico");

    public static PixbufFormat BMP = new PixbufFormat("bmp");
}
