/*
 * Format.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.cairo;

import org.freedesktop.bindings.Constant;

/**
 * Constants specifying what the bit depth of the pixels in an ImageSurface.
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
/*
 * TODO do we need to ramble on about bit orders, pre multiplication of alpha
 * in ARGB, etc?
 */
public class Format extends Constant
{
    private Format(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * 32 bits per pixel, being 8 bits for each of red, green, blue,
     * <i>and</i> and 8 bit alpha (transparency) channel. This is the standard
     * choice for most work.
     */
    public static final Format ARGB32 = new Format(CairoFormat.ARGB32, "ARGB32");

    /**
     * 24 bits per pixel, being 8 bits in each of red, green, and blue. No
     * alpha channel.
     */
    public static final Format RGB24 = new Format(CairoFormat.RGB24, "RGB24");

    /**
     * 8 bits per pixel, holding an alpha value (only).
     */
    public static final Format A8 = new Format(CairoFormat.A8, "A8");

    /**
     * A traditional bitmask with a 1 bit alpha value (on or off).
     */
    public static final Format A1 = new Format(CairoFormat.A1, "A1");
}
