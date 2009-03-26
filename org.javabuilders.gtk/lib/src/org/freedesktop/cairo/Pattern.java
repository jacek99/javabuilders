/*
 * Pattern.java
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
 * A Pattern source.
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
public abstract class Pattern extends Entity
{
    protected Pattern(long pointer) {
        super(pointer);
    }

    protected void release() {
        CairoPattern.destroy(this);
    }

    protected void checkStatus() {
        checkStatus(CairoPattern.status(this));
    }

    /**
     * Add a colour stop to a Pattern gradient. Equivalent to calling:
     * 
     * <pre>
     * addColorStopRGBA(offset, red, green, blue, 1.0);
     * </pre>
     * 
     * See {@link #addColorStopRGBA(double, double, double, double, double)
     * addColorStopRGBA()} for documentation of the <code>offset</code>
     * parameter. The colour parameters are the same as for
     * {@link Context#setSource(double, double, double) setSource()}.
     * 
     * @since 4.0.7
     */
    public void addColorStopRGB(double offset, double red, double green, double blue) {
        CairoPattern.addColorStopRgb(this, offset, red, green, blue);
        checkStatus();
    }

    /**
     * Add a colour stop to a Pattern gradient.
     * 
     * <p>
     * The <code>offset</code> parameter provides for the ordering of stops.
     * When a Pattern applies its colour stops, it works through them in the
     * order specified. If two stops are specified with identical
     * <code>offset</code> values, they will be sorted according to the order
     * in which the stops are added, is used for making sharp color
     * transitions instead of a blend.
     * 
     * <p>
     * Colour stops handle colour arguments the same way as
     * {@link Context#setSource(double, double, double) setSource()} does.
     * 
     * @since 4.0.7
     */
    public void addColorStopRGBA(double offset, double red, double green, double blue, double alpha) {
        CairoPattern.addColorStopRgba(this, offset, red, green, blue, alpha);
        checkStatus();
    }
}
