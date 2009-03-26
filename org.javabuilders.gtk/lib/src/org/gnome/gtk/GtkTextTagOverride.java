/*
 * GtkTextTagOverride.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.

 */
package org.gnome.gtk;

import org.freedesktop.bindings.Constant;
import org.freedesktop.bindings.DoubleConstant;
import org.gnome.pango.Weight;

final class GtkTextTagOverride extends Plumbing
{
    private GtkTextTagOverride() {}

    /*
     * The usual visibility workaround; this one more complicated since it is
     * cross package which is why org.gnome.pango.Scale is a DoubleConstant.
     */
    static final double valueOf(DoubleConstant reference) {
        return org.freedesktop.bindings.Plumbing.numOf(reference);
    }

    static final int valueOf(Constant reference) {
        return org.freedesktop.bindings.Plumbing.numOf(reference);
    }

    static final Weight weightFor(int value) {
        return (Weight) org.freedesktop.bindings.Plumbing.flagFor(Weight.class, value);
    }
}
