/*
 * DataColumnReference.java
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
 * A column containing references back to Java objects in a TreeModel.
 * 
 * <p>
 * If your TreeView is a GUI representation of a List of Account objects, say,
 * and someone selects one of the rows, you quite reasonably want to know
 * <i>which</i> Account was selected. While you could extract the account name
 * or some other text field displayed in the TextView and attempt to use that
 * to look up the Account object back on the Java side, a much better solution
 * is to merely store a reference to the object that was the source of the
 * data in that row in the first place. That's what DataColumnReference is
 * for.
 * 
 * <p>
 * Simply call {@link TreeModel#getValue(TreeIter, DataColumnReference)
 * getValue()} with the TreeIter the selection gave you and the
 * DataColumnReference indicating the column you stashed your object in, and
 * then ta-da you have the Account which was selected, allowing you to carry
 * on with your application logic in your application's domain model's terms.
 * 
 * <p>
 * Beyond this, see {@link DataColumn} for the full discussion of the role of
 * these classes and examples of how to employ them.
 * 
 * <p>
 * <b>Don't try to use this in an attempt to somehow magically render a Java
 * object in a TreeView!</b> That's not what this is for; 99.9% of the time
 * DataColumnString driving a TreeViewColumn with a CellRendererText, or a
 * DataColumnPixbuf driving a TreeViewColumn containing a CellRendererPixbuf
 * will more than handle the job for you.
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
public final class DataColumnReference extends DataColumn
{
    public DataColumnReference() {
        super(java.lang.Object.class);
    }
}
