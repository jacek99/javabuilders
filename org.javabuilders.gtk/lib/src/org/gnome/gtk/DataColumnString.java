/*
 * DataColumnString.java
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
 * A column containing textual data in a TreeModel. See {@link DataColumn} for
 * the full discussion of the role of how to employ these.
 * 
 * <p>
 * DataColumnStrings are by far the most commonly used DataColumn type, as
 * even numerical data is often presented in text form with formatting applied
 * on the Java side before being passed in to drive presentation in the
 * TreeView.
 * 
 * <p>
 * Furthermore, there are numerous CellRenderer properties which take String
 * arguments, and those values are also stored in your model in columns of
 * type DataColumnString; see
 * {@link CellRendererText#setForeground(DataColumnString) setForeground()} on
 * CellRendererText for an example.
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
public final class DataColumnString extends DataColumn
{
    public DataColumnString() {
        super(String.class);
    }
}
