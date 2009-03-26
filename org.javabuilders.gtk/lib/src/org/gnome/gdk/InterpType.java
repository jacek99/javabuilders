/*
 * InterpType.java
 *
 * Copyright (c) 1999-2003 Free Software Foundation
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 *      
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 * 
 * Descriptions of the interpolation algorithms are adapted from text in the
 * gdk-pixbuf library's documentation, which is licenced by its authors under
 * the terms of the "GNU Free Documentation Licence, version 1.1 or later".
 */
package org.gnome.gdk;

import org.freedesktop.bindings.Constant;

/**
 * Interpolation algorithms available in GDK. These are used to control the
 * choice of algorithm that will be applied when scaling an image with
 * Pixbuf's {@link Pixbuf#scale(int, int, InterpType) scale()} method. In
 * general use it is recommended that you use the {@link #BILINEAR BILINEAR}
 * algorithm.
 * 
 * <p>
 * <i>The details of the implementations of the interpolation techniques made
 * available here are obviously highly technical, and so are adapted directly
 * from the gdk-pixbuf library's documentation.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
public final class InterpType extends Constant
{
    private InterpType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Interpolate by "nearest neighbour" sampling. This is a fast algorithm
     * which, unfortunately, results in really ugly images when scaling down.
     */
    public static final InterpType NEAREST = new InterpType(GdkInterpType.NEAREST, "NEAREST");

    /**
     * Use the bilinear interpolation method. When scaling up, this choice is
     * equivalent to point-sampling the ideal bilinear-interpolated image. For
     * scaling down, it is equivalent to laying down small tiles and
     * integrating over the coverage area. This is considered the best choice
     * in normal situations, providing a decent balance between speed and
     * quality.
     */
    public static final InterpType BILINEAR = new InterpType(GdkInterpType.BILINEAR, "BILINEAR");

    /**
     * Each pixel is rendered as a tiny parallelogram of solid color, the
     * edges of which are implemented with antialiasing. The result is similar
     * to {@link #NEAREST NEAREST} if scaling up, and to {@link #BILINEAR
     * BILINEAR} if scaling down.
     * 
     * <p>
     * <i>This is supposedly an accurate simulation of the PostScript's image
     * operator (without any interpolation enabled).</i>
     */
    public static final InterpType TILES = new InterpType(GdkInterpType.TILES, "TILES");

    /**
     * A hyperbolic filtering algorithm. This is a refinement of Wolberg's
     * digital image warping. This method is the slowest of the available
     * choices, but generally gives the highest quality results.
     * 
     * <p>
     * The filter is idempotent for 1:1 pixel mapping.
     * 
     * <p>
     * <i>According to the GDK documentation, this algorithm is formally
     * defined as the "hyperbolic-filter sampling the ideal hyperbolic-filter
     * interpolated image". Good thing they let us know that.</i>
     */
    public static final InterpType HYPER = new InterpType(GdkInterpType.HYPER, "HYPER");
}
