/*
 * CellRendererPixbuf.java
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

/**
 * Display an image in a TreeView. The image data is sourced either from a
 * DataColumn of type {@link DataColumnPixbuf DataColumnPixbuf} in your
 * TreeModel, or from a DataColumn of type {@link DataColumnStock
 * DataColumnStock}.
 * 
 * @author Andrew Cowie
 * @author Vreixo Formoso
 * @since 4.0.5
 */
public class CellRendererPixbuf extends CellRenderer
{
    /**
     * Construct a new CellRendererPixbuf.
     */
    public CellRendererPixbuf(CellLayout vertical) {
        super(GtkCellRendererPixbuf.createCellRendererPixbuf(), vertical, false);
    }

    /**
     * Indicate the DataColumn containing the Pixbuf to render as an image.
     */
    public void setPixbuf(DataColumnPixbuf column) {
        GtkCellLayout.addAttribute(vertical, this, "pixbuf", column.getOrdinal());
    }

    /**
     * Indicate the DataColumn containing a Stock icon which you want to have
     * this CellRendererPixbuf render as an image.
     * 
     * @since 4.0.7
     */
    public void setStock(DataColumnStock column) {
        GtkCellLayout.addAttribute(vertical, this, "stock-id", column.getOrdinal());
    }
}
