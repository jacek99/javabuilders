/*
 * Pango.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.pango;

import org.gnome.glib.Glib;

final class Pango extends Glib
{
    private Pango() {}

    /**
     * Conversion factor to go from Cairo device units to "Pango Units".
     */
    /*
     * The Pango library uses a fixed point system, scaled up by this
     * constant; Cairo on the other hand uses doubles. Since all the drawing
     * we're doing with Pango will be in the context of a Cairo drawing
     * operation, we expose our API as doubles to match Cairo, and quietly
     * convert internally.
     * 
     * Seeing as how Pango draws on Cairo, it'd be nice if Pango just exposed
     * the doubles and was done with it.
     * 
     * Since we divide by this frequently, we keep it here as a double to
     * reduce the risk of creating divide-by-integer errors.
     * 
     * FUTURE retreive from native as this is subject to change in the future.
     */
    static final double SCALE = 1024.0;
}
