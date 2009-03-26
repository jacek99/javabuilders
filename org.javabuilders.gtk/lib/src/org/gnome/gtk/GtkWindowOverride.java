/*
 * GtkWindowOverride.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * Hand crafted to allow us to reconfigure GtkWindow memory management. See
 * implementing C file for a detailed discussion.
 * 
 * @author Andrew Cowie
 */
final class GtkWindowOverride extends Plumbing
{
    private GtkWindowOverride() {}

    static final void dropUserRef(Window self) {
        synchronized (lock) {
            gtk_window_drop_user_ref(pointerOf(self));
        }
    }

    private static native final void gtk_window_drop_user_ref(long self);
}
