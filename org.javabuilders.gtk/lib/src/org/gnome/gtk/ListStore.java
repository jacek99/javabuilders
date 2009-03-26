/*
 * ListStore.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * The model storing a list of data. ListStores are the concrete TreeModel
 * subclass used as the backing data store by a TreeView displaying tabular
 * data, storing both the material to be presented as well as meta-information
 * used to fine tune the presentation of that data. ListStores are also used
 * as the backing store for the list of options in a ComboBoxes.
 * 
 * <p>
 * Detailed discussion of how to instantiate ListStores is included in the
 * {@link DataColumn} class. In summary, given
 * 
 * <pre>
 * final DataColumnString monarchName;
 * final DataColumnInteger coronatedYear;
 * final DataColumnPixbuf portrait;
 * final ListStore model;
 * ...
 * </pre>
 * 
 * you build a three column model as follows:
 * 
 * <pre>
 * model = new ListStore(new DataColumn[] {
 *         monarchName = new DataColumnString(),
 *         coronatedYear = new DataColumnInteger(),
 *         portrait = new DataColumnPixbuf()
 * });
 * </pre>
 * 
 * <p>
 * Although we talk about TreeModels all the time as the base superclass, it's
 * not a good idea to declare your model as a TreeModel when instantiating
 * because you'll need {@link #appendRow() appendRow()} method which is here,
 * on the concrete type ListStore. In other words, do:
 * 
 * <pre>
 * ListStore model = new ListStore(...);
 * </pre>
 * 
 * as shown above, not:
 * 
 * <pre>
 * TreeModel model = new ListStore(...);
 * </pre>
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
public class ListStore extends TreeModel implements TreeDragSource, TreeDragDest, TreeSortable
{
    protected ListStore(long pointer) {
        super(pointer);
    }

    /**
     * Construct a new ListStore with the given column types. See
     * {@link DataColumn DataColumn} for details. You must include at least
     * one DataColumn in your <code>types</code> array (you can't have a
     * ListStore with no columns).
     */
    public ListStore(DataColumn[] types) {
        super(GtkTreeModelOverride.createListStore(typesToClassNames(types)));
    }

    protected void dispatch(TreeIter row, DataColumn column, Value value) {
        GtkListStore.setValue(this, row, column.getOrdinal(), value);
    }

    /**
     * Add a new row to the ListStore. You'll need to fill in the various
     * columns with one of the various
     * {@link TreeModel#setValue(TreeIter, DataColumnString, String)
     * setValue()} methods, of course.
     */
    public TreeIter appendRow() {
        final TreeIter iter;

        iter = new TreeIter(this);

        GtkListStore.append(this, iter);

        return iter;
    }

    /**
     * Remove all rows (and their contents) from this ListStore.
     * 
     * @since 4.0.6
     */
    public void clear() {
        GtkListStore.clear(this);
    }

    public void setSortColumn(DataColumn column, SortType ordering) {
        GtkTreeSortable.setSortColumnId(this, column.getOrdinal(), ordering);
    }

    /**
     * Delete a row from the ListStore. If there is another row after this
     * then <code>true</code> will be returned and <code>row</code> will still
     * be valid. Otherwise, <code>false</code> is returned and
     * <code>row</code> is invalid from here on.
     * 
     * @since 4.0.7
     */
    public boolean removeRow(TreeIter row) {
        return GtkListStore.remove(this, row);
    }

    /**
     * Insert a new row in the ListStore. The row will be placed at
     * <code>position</code>, which must be between <code>0</code> and the
     * number or rows in the model.
     * 
     * <p>
     * If you have a TreeIter pointing at a row already you can instead use
     * the other form of {@link #insertRow(TreeIter) insertRow()} to inject an
     * new row there.
     * 
     * <p>
     * As with {@link #appendRow() appendRow()} the new row will be empty;
     * you'll need to call one of the various <code>setValue()</code> methods
     * to populate it.
     * 
     * @since 4.0.7
     */
    public TreeIter insertRow(int position) {
        final TreeIter iter;

        if (position < 0) {
            throw new IllegalArgumentException("position can't be negative");
        }

        iter = new TreeIter(this);

        GtkListStore.insert(this, iter, position);

        return iter;
    }

    /**
     * Insert a new row in the ListStore. The empty row will be placed in
     * front of the supplied <code>sibling</code>.
     * 
     * <p>
     * Alternately, see {@link #insertRow(int) insertRow()} to insert a row at
     * a given position, and {@link #appendRow() appendRow()} to add a blank
     * row at the end of the model.
     * 
     * @since 4.0.7
     */
    public TreeIter insertRow(TreeIter sibling) {
        final TreeIter iter;

        iter = new TreeIter(this);

        GtkListStore.insertBefore(this, iter, sibling);

        return iter;
    }
}
