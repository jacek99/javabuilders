/*
 * DataColumnLong.java
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
 * A column of <code>long</code> values in a TreeModel. This class complements
 * DataColumnInteger by adding the ability to use <code>long</code> data when
 * sorting or for CellRenderer properties.
 * 
 * <p>
 * See {@link DataColumnInteger} for discussion of why you may not necessarily
 * need this column type even though you might think you do.
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public final class DataColumnLong extends DataColumn
{
    public DataColumnLong() {
        super(Long.class);
    }
}
