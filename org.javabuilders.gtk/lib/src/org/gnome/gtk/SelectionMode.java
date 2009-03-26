/*
 * SelectionMode.java
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

import org.freedesktop.bindings.Constant;

/**
 * What kinds of selections are possible on a TreeView? These are used by
 * {@link TreeSelection#setMode(SelectionMode) setMode()} on TreeSelection,
 * which in turn you get by calling TreeView's {@link TreeView#getSelection()
 * getSelection()}.
 * 
 * <p>
 * The default is {@link #SINGLE SINGLE}.
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
public final class SelectionMode extends Constant
{
    private SelectionMode(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Rows may <b>not</b> be selected.
     */
    public static final SelectionMode NONE = new SelectionMode(GtkSelectionMode.NONE, "NONE");

    /**
     * One element may be selected; zero selected rows is also permitted. This
     * it the default.
     */
    public static final SelectionMode SINGLE = new SelectionMode(GtkSelectionMode.SINGLE, "SINGLE");

    /**
     * Normally exactly one row will be selected; the user cannot deselect
     * this row without selecting another. This is not all encompassing,
     * however, as no row selected is possible as an initial state and also
     * during interactive searches.
     */
    public static final SelectionMode BROWSE = new SelectionMode(GtkSelectionMode.BROWSE, "BROWSE");

    /**
     * Multiple rows may be selected. The behaviour is the same as with
     * multiple selections elsewhere in the GNOME desktop (file browsing,
     * etc). Mouse clicks toggle the selected row to the location of the
     * click. The <code>Shift</code> key will cause the selection to go from
     * the focus to the click location, and the <code>Ctrl</code> key will
     * enlarge the selection by adding the clicked row to the other rows
     * already selected.
     */
    public static final SelectionMode MULTIPLE = new SelectionMode(GtkSelectionMode.MULTIPLE, "MULTIPLE");
}
