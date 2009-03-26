/*
 * HScale.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * A horizontal slider allowing you to visually represent data and offer the
 * user the ability to manipulate it. <img src="HScale.png" class="snapshot">
 * See {@link Scale} and {@link Range} for the methods used to manipulate
 * instances of these classes.
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public class HScale extends Scale
{
    protected HScale(long pointer) {
        super(pointer);
    }

    /**
     * Create a new HScale allowing the user to enter a number a number
     * between <code>min</code> and <code>max</code>, sliding in increments of
     * <code>step</code>.
     * 
     * <p>
     * The internal algorithms work best if <code>step</code> is specified as
     * a power of 10. That shouldn't hassle you, as you can round the value
     * showing in the HScale with {@link Scale#setDigits(int) setDigits()}.
     * And in any case, <code>step</code> only impacts the jumps that are made
     * if the HScale is changed via the <b><code>Left</code></b> and <b>
     * <code>Right</code></b> key strokes.
     * 
     * @since 4.0.6
     */
    public HScale(int min, int max, int step) {
        super(GtkHScale.createHScaleWithRange(min, max, step));
    }
}
