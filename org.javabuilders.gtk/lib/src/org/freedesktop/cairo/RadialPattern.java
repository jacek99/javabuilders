/*
 * RadialPattern.java
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

/**
 * A radial gradient Pattern.
 * 
 * <p>
 * After calling this and before using the RadialPattern you need to call
 * {@link Pattern#addColorStopRGBA(double, double, double, double, double)
 * addColorStopRGBA()} a few times to set up the gradient. For example, to
 * create an circular alpha blend:
 * 
 * <pre>
 * pattern = new RadialPattern(75, 75, 25, 75, 75, 120);
 * pattern.addColorStopRGBA(0.0, 0.0, 0.0, 0.0, 0.0);
 * pattern.addColorStopRGBA(1.0, 0.0, 0.0, 0.0, 1.0);
 * </pre>
 * 
 * and then you can get on with using the Pattern in drawing operations:
 * 
 * <pre>
 * cr.mask(pattern);
 * </pre>
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
public class RadialPattern extends Pattern
{
    protected RadialPattern(long pointer) {
        super(pointer);
    }

    /**
     * Create a Pattern with a radial gradient between two circles. The first
     * circle is centered at <code>cx0</code>,<code>cy0</code> with a radius
     * of <code>radius0</code>, and the second circle is centered at
     * <code>cx1</code>,<code>cy1</code> with a radius of <code>radius1</code>
     * .
     * 
     * <p>
     * Quite typically, you will want a strict radial pattern from a common
     * centre, in which case have <code>cx1</code>,<code>cy1</code> equal to
     * <code>cx0</code>,<code>cy0</code>.
     * 
     * @since 4.0.7
     */
    public RadialPattern(double cx0, double cy0, double radius0, double cx1, double cy1, double radius1) {
        super(CairoPattern.createPatternRadial(cx0, cy0, radius0, cx1, cy1, radius1));
        checkStatus();
    }
}
