/*
 * GtkEntryOverride.java
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

final class GtkEntryOverride extends Plumbing
{
    private GtkEntryOverride() {}

    static final void setInnerBorder(Entry self, int left, int right, int top, int bottom) {
        synchronized (lock) {
            gtk_entry_set_inner_border(pointerOf(self), left, right, top, bottom);
        }
    }

    private static native final void gtk_entry_set_inner_border(long self, int left, int right, int top,
            int bottom);
}
