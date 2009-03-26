/*
 * DataColumnPixbuf.java
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
 * A column containing image data in a TreeModel. See {@link DataColumn} for
 * the full discussion of the role of how to employ these. This column type is
 * used to drive display of images in TreeViews using
 * {@link CellRendererPixbuf CellRendererPixbuf}s.
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
public final class DataColumnPixbuf extends DataColumn
{
    public DataColumnPixbuf() {
        super(org.gnome.gdk.Pixbuf.class);
    }
}
