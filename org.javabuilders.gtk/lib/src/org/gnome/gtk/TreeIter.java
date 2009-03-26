/*
 * TreeIter.java
 *
 * Copyright (c) 2006-2008 Operational Dynamics Consulting Pty Ltd, and Others
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import org.gnome.glib.Boxed;

/**
 * A temporary pointer to a row in a TreeModel. TreeIters are used to indicate
 * a row in a TreeModel, either the "current" row if you are iterating over
 * the data, or as an indication of which row a given event occurred on.
 * 
 * <p>
 * To obtain a new TreeIter, use one of the following:
 * <ul>
 * <li>ListStore's {@link ListStore#appendRow() appendRow()} (to add a new
 * record to the end of the data set in the model);
 * <li>TreeModel's {@link TreeModel#getIterFirst() getIterFirst()} (to start
 * iterating through the rows in the model); or
 * <li>TreeSelection's {@link TreeSelection#getSelected() getSelected()}
 * (allowing you to identify the selected row and subsequently read data from
 * it, usually with TreeModel's
 * {@link TreeModel#getValue(TreeIter, DataColumnReference) getValue()}.
 * </ul>
 * 
 * <p>
 * Like other iterators in Java, a TreeIter becomes invalid the moment the
 * underlying model changes. If you need a persistent pointer to a particular
 * row, create a {@link TreeRowReference TreeRowReference}.
 * 
 * <p>
 * <i>Note that although one use case for TreeIter is to iterate through all
 * the rows in a model, more often they are used to point to a single row (the
 * one you are presently adding data to, or the one that was selected by the
 * user); these aren't <code>java.util.Iterator</code>s.</i>
 * 
 * @author Andrew Cowie
 * @author Srichand Pendyala
 * @author Vreixo Formoso
 * @since 4.0.5
 */
public final class TreeIter extends Boxed
{
    private TreeModel model;

    /*
     * Standard no-arg constructor. In general, our TreeIters are instantiated
     * by us, and so the model is set. If this constructor is used (ie by a
     * signal handler delegate), then, you will need to call setModel() as
     * well.
     */
    protected TreeIter(long pointer) {
        super(pointer);

        model = null;
    }

    /**
     * Allocate a blank TreeIter structure. This is done by declaring one
     * locally, copying it, and returning the pointer to the copy.
     * 
     * <p>
     * <b>For use by bindings hackers only!</b>
     */
    TreeIter(TreeModel model) {
        super(GtkTreeIterOverride.createTreeIter());

        this.model = model;
    }

    void setModel(TreeModel model) {
        this.model = model;
    }

    TreeModel getModel() {
        if (model == null) {
            throw new IllegalStateException(
                    "\nSorry, this TreeIter doesn't have its internal reference to its parent TreeModel set");
        }
        return model;
    }

    protected void release() {
        model = null;
        GtkTreeIter.free(this);
    }

    /**
     * Change this TreeIter to point to the row following the current one. In
     * a ListStore, this is simply the next row in the model, and what you use
     * in conjunction with {@link TreeModel#getIterFirst() getIterFirst()} to
     * iterate through the entire model. In a TreeStore, however, it will
     * return the next row <i>at this level</i>.
     * 
     * <p>
     * This will return <code>true</code> if it was able to change this
     * TreeIter to the next row.
     * 
     * <p>
     * Be aware that when this returns <code>false</code> the TreeIter is no
     * longer valid.
     * 
     * @since 4.0.5
     */
    public boolean iterNext() {
        if (model == null) {
            return false;
        }
        return GtkTreeModel.iterNext(model, this);
    }
}
