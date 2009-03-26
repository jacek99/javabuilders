/*
 * GtkTreeIterOverride.java
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
 * Expose an allocator for blank TreeIters. See the detailed comment in
 * GtkTreeIterOverride.c for the mechanics of how this is done. FIXME Indeed,
 * this should probably be something that the code generator creates for us,
 * although that has to be done with some care as it would need to be aware of
 * the copy function, and would have to not collide with an existing _new()
 * function if one exists (as it does in Pango)
 * 
 * @author Andrew Cowie
 */
final class GtkTreeIterOverride extends Plumbing
{
    static final long createTreeIter() {
        synchronized (lock) {
            return gtk_tree_iter_new();
        }
    }

    private static native final long gtk_tree_iter_new();
}
