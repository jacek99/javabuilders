/*
 * SurfacePattern.java
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
 * A source Pattern that is derived from another Surface. Creating on of these
 * allows you to use another Surface you have or are working with as the
 * "paint" when drawing on a different Surface.
 * 
 * <p>
 * These are created internally if you call
 * {@link Context#setSource(Surface, double, double) setSource()}.
 * 
 * @since 4.0.7
 */
public class SurfacePattern extends Pattern
{
    protected SurfacePattern(long pointer) {
        super(pointer);
    }

    /**
     * Create a Pattern based on an existing Surface.
     * 
     * @since 4.0.7
     */
    public SurfacePattern(Surface surface) {
        super(CairoPattern.createPatternForSurface(surface));
        checkStatus();
    }
}
