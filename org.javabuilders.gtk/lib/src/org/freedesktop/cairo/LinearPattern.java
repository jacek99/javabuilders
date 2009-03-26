/*
 * LinearPattern.java
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
 * A linear gradient.
 * 
 * <p>
 * Before using the LinearPattern you need to call
 * {@link Pattern#addColorStopRGBA(double, double, double, double, double)
 * addColorStopRGBA()} a few times to set up the gradient. For example:
 * 
 * <pre>
 * pat = new LinearPattern(40, 25, 120, 100);
 * pat.addColorStopRGB(0.0, 0.0, 0.3, 0.8);
 * pat.addColorStopRGB(1.0, 0.0, 0.8, 0.3);
 * </pre>
 * 
 * and then you can get on with using the Pattern in drawing operations.
 * 
 * <pre>
 * cr.setSource(pat);
 * </pre>
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
public class LinearPattern extends Pattern
{
    protected LinearPattern(long pointer) {
        super(pointer);
    }

    /**
     * Create a Pattern with a linear gradient between co-ordinates
     * <code>x0</code>,<code>y0</code> and <code>x1</code>,<code>y1</code>.
     * 
     * @since 4.0.7
     */
    public LinearPattern(double x0, double y0, double x1, double y1) {
        super(CairoPattern.createPatternLinear(x0, y0, x1, y1));
        checkStatus();
    }
}
