/*
 * GtkTreeModelFilterOverride.java
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

/**
 * Manual code allowing us to hookup the TreeModelFilter callback function as
 * if it were a signal.
 * 
 * @author Andrew Cowie
 */
final class GtkTreeModelFilterOverride extends Plumbing
{
    /**
     * Manually hookup the function that will emit our custom visible signal.
     */
    static final void setVisibleFunc(TreeModelFilter self) {
        gtk_tree_model_filter_set_visible_func(pointerOf(self));
    }

    private static native final void gtk_tree_model_filter_set_visible_func(long self);
}
