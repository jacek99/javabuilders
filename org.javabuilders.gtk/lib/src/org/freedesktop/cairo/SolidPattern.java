/*
 * SolidPattern.java
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

/**
 * A Pattern of a single colour. Note that "solid" does not imply completely
 * opaque; as with most other colour setting operations in Cairo, you can
 * specify an level of transparency.
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
public class SolidPattern extends Pattern
{
    protected SolidPattern(long pointer) {
        super(pointer);
    }

    /**
     * Create a Pattern of a given colour.
     * 
     * @since 4.0.7
     */
    public SolidPattern(double red, double green, double blue, double alpha) {
        super(CairoPattern.createPatternRgba(red, green, blue, alpha));
        checkStatus();
    }
}
