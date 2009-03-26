/*
 * Antialias.java
 *
 * Copyright (c) 2008 Vreixo Formoso
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
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
 * The type of antialiasing to do when rendering text or shapes.
 * 
 * @author Vreixo Formoso
 * @since 4.0.7
 */
public class Antialias extends Constant
{
    private Antialias(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Use the default antialiasing for the subsystem and target device.
     * Assuming your X server is hehaving, this will Just Work (tm) properly,
     * and is what you want.
     */
    public static final Antialias DEFAULT = new Antialias(CairoAntialias.DEFAULT, "DEFAULT");

    /**
     * Perform single-color antialiasing.
     * 
     * <p>
     * <i>This means (for example) "using shades of gray for black text on a
     * white background".</i>
     */
    /*
     * FIXME what does this actually mean, really?
     */
    public static final Antialias GRAY = new Antialias(CairoAntialias.GRAY, "GRAY");

    /**
     * Don't do antialiasing.
     * 
     * <p>
     * <i>Strictly, this means using a "bilevel alpha mask".</i>
     */
    public static final Antialias NONE = new Antialias(CairoAntialias.NONE, "NONE");

    /**
     * Perform antialiasing based on the subpixel ordering.
     * 
     * <p>
     * When the layout of the individual colour elements making up each
     * individual pixel on an LCD panel is known, then Cairo is able to do an
     * even more subtle job of antialiasing.
     * 
     * <p>
     * This has no effect on CRT monitors, where the pixels are rendered as
     * unique points by the ray gun, and are not the result of the cumulative
     * effect of three different co-located light sources at each pixel.
     */
    public static final Antialias SUBPIXEL = new Antialias(CairoAntialias.SUBPIXEL, "SUBPIXEL");
}
