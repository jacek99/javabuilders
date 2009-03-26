/*
 * SizeGroupMode.java
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

import org.freedesktop.bindings.Constant;

/**
 * Constants indicating how a given SizeGroup is to operate.
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public final class SizeGroupMode extends Constant
{
    private SizeGroupMode(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * The SizeGroup will (no longer have) an effect.
     */
    public static final SizeGroupMode NONE = new SizeGroupMode(GtkSizeGroupMode.NONE, "NONE");

    /**
     * All the Widgets in this SizeGroup will share the width requested by the
     * widest Widget in the group.
     */
    public static final SizeGroupMode HORIZONTAL = new SizeGroupMode(GtkSizeGroupMode.HORIZONTAL,
            "HORIZONTAL");

    /**
     * All the Widgets in this SizeGroup will share the height requested by
     * the tallest Widget in the group.
     */
    public static final SizeGroupMode VERTICAL = new SizeGroupMode(GtkSizeGroupMode.VERTICAL, "VERTICAL");

    /**
     * Widgets in this SizeGroup will have the same size request (both width
     * and height) as the largest Widget in the group.
     */
    public static final SizeGroupMode BOTH = new SizeGroupMode(GtkSizeGroupMode.BOTH, "BOTH");
}
