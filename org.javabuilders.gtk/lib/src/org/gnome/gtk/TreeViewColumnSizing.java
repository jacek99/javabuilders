/*
 * TreeViewColumnSizing.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd, and Others
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
 * Constants controlling the [re]sizing behaviour of TreeViewColumns in a
 * TreeView.
 * 
 * @since 4.0.6
 */
public final class TreeViewColumnSizing extends Constant
{
    private TreeViewColumnSizing(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Columns will (only) get bigger in reaction to changes in data in the
     * model. If new data is set into the model which requires more space to
     * render, the column will get wider. Otherwise things will be left alone.
     * This is the default.
     */
    public static final TreeViewColumnSizing GROW_ONLY = new TreeViewColumnSizing(
            GtkTreeViewColumnSizing.GROW_ONLY, "GROW_ONLY");

    /**
     * Columns will resize both larger and smaller to accomodate any changes
     * to the model. This is "cool" but is horribly inefficient on large
     * datasets, where the cascade of size allocations will cause your
     * TreeView to become really choppy.
     * 
     * <p>
     * Choosing this column sizing method will implicitly set
     * {@link TreeViewColumn#setResizable(boolean) setResizeable()} to
     * <code>false</code>.
     */
    public static final TreeViewColumnSizing AUTOSIZE = new TreeViewColumnSizing(
            GtkTreeViewColumnSizing.AUTOSIZE, "AUTOSIZE");

    /**
     * The column will be a predetemined width. The number of pixels wide is
     * set with TreeViewColumn's {@link TreeViewColumn#setFixedWidth(int)
     * setFixedWidth()}.
     */
    public static final TreeViewColumnSizing FIXED = new TreeViewColumnSizing(
            GtkTreeViewColumnSizing.FIXED, "FIXED");
}
