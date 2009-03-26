/*
 * Operator.java
 *
 * Copyright (c) 2008-2009 Operational Dynamics Consulting Pty Ltd, and Others
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
 * Constants specifying the compositing operating mode in effect. These are
 * set for a drawing Context using {@link Context#setOperator(Operator)
 * setOperator()}, and take effect when commands like {@link Context#paint()
 * paint()} are invoked.
 * 
 * @author Andrew Cowie
 * @author Zak Fenton
 * @since 4.0.7
 */
public class Operator extends Constant
{
    private Operator(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Clear a surface to all transparent.
     * 
     * @since 4.0.7
     */
    public static final Operator CLEAR = new Operator(CairoOperator.CLEAR, "CLEAR");

    /**
     * Default operator: draw over existing pixels.
     * 
     * @since 4.0.10
     */
    public static final Operator OVER = new Operator(CairoOperator.OVER, "OVER");
}
