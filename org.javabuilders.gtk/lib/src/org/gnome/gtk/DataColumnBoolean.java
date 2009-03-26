/*
 * DataColumnBoolean.java
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
 * A column of <code>boolean</code> values in a TreeModel. These are
 * particularly useful to drive TreeViewColumns containing a
 * CellRendererToggle, although they are of course used to set the properties
 * of any <code>boolean</code> property offered by a CellRenderer.
 * 
 * <p>
 * See {@link DataColumn} for the full discussion of how this family of
 * classes is employed when working with TreeModels.
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
public final class DataColumnBoolean extends DataColumn
{
    public DataColumnBoolean() {
        super(Boolean.class);
    }
}
