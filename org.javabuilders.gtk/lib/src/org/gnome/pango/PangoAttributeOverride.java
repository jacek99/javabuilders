/*
 * PangoAttributeOverride.java
 *
 * Copyright (c) 2009 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.pango;

/**
 * @author Andrew Cowie
 */
final class PangoAttributeOverride extends Plumbing
{
    private PangoAttributeOverride() {}

    static final void setIndexes(Attribute self, Layout layout, int offset, int width) {
        synchronized (lock) {
            pango_attribute_set_indexes(pointerOf(self), pointerOf(layout), offset, width);
        }
    }

    private static native final void pango_attribute_set_indexes(long self, long layout, int offset,
            int width);

    static int getLengthUTF8(String str) {
        return strlen(str);
    }

    private static native final int strlen(String str);
}
