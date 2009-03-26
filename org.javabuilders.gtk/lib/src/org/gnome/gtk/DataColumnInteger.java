/*
 * DataColumnInteger.java
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
 * A column of <code>int</code> values in a TreeModel.
 * 
 * <p>
 * Note that DataColumnInteger is used for passing information to CellRenderer
 * property setters which take numeric values, not for rendering the numbers
 * themselves.
 * 
 * <p>
 * To actually present numeric data in a TreeView, convert it to a String on
 * the Java side first and to store the data in a DataColumnString. This,
 * however, sometimes disrupts the natural sorting order of the numeric data
 * (and certainly will if arbitrary formatting or markup is applied) and so an
 * additional column of type DataColumnInteger can be added containing the
 * original numeric data as a sort index and applied to the TreeViewColumn
 * presenting that data with its
 * {@link TreeViewColumn#setSortColumn(DataColumn) setSortColumn()}.
 * 
 * <p>
 * See {@link DataColumn} for the full discussion of how to employ this family
 * of classes.
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
public final class DataColumnInteger extends DataColumn
{
    public DataColumnInteger() {
        super(Integer.class);
    }
}
