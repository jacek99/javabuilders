/*
 * TreeModel.java
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

import org.gnome.gdk.Pixbuf;

/**
 * The data use as the model backing a {@link TreeView}. TreeModel comes in
 * two flavours which actually store data: {@link ListStore}, for a list of
 * rows, and {@link TreeStore}, for data which has a hierarchical relationship
 * between the rows.
 * 
 * <p>
 * TreeModels are tabular, and as such have "columns", each of which is
 * strongly typed, and which are represented in java-gnome by the
 * {@link DataColumn DataColumn} classes.
 * 
 * <p>
 * While the "columns" (and their types) must be declared when instantiating a
 * TreeModel, the "rows" in a TreeModel are dynamic; it grows as you add data
 * to the model. An instance of {@link TreeIter TreeIter} points to an
 * individual row in a TreeModel; these are used both when adding new rows and
 * when dealing with identifying which row has been selected in a TreeView. Be
 * warned that TreeIters are <i>very</i> transient and are only valid so long
 * as you haven't changed the structure of the model (ie, by adding another
 * row, sorting it, filtering it, etc) so when populating a TreeModel we tend
 * to do so one complete row at a time.
 * 
 * <h2>Populating TreeModels</h2>
 * 
 * <p>
 * You add data to a TreeModel by first calling <code>appendRow()</code> which
 * returns a TreeIter pointing to the new row, and then using the
 * <code>setValue()</code> method appropriate to the data type of each column
 * [<code>setValue()</code> has an overload for each concrete DataColumn type,
 * so if you've declared the columns as fully derived DataColumnString or
 * DataColumnInteger or whatever (as recommended), the following will Just
 * Work]:
 * 
 * <pre>
 * final DataColumnString column;
 * final ListStore model;
 * TreeIter row;
 * 
 * row = model.appendRow();
 * model.setValue(row, column, &quot;King George V&quot;);
 * </pre>
 * 
 * You'll note that in this example we called the TreeIter <code>row</code>
 * and the DataColumn <code>column</code>; doing so made the first two
 * arguments of each of the <code>setValue()</code> methods make sense: you
 * are setting a <var>value</var> in the ListStore or TreeStore at the
 * co-ordinates <var>row</var>, <var>column</var>. In practise, of course, you
 * have many DataColumns,
 * 
 * <pre>
 * final DataColumnString monarchNameColumn;
 * final DataColumnInteger coronatedYearColumn;
 * final DataColumnPixbuf portraitColumn;
 * final DataColumnReference monarchObjectColumn;
 * final ListStore model;
 * TreeIter row;
 * Pixbuf portrait;
 * ...
 * 
 * row = model.appendRow();
 * model.setValue(row, monarchNameColumn, &quot;King George V&quot;);
 * model.setValue(row, coronatedYearColumn, 1910);
 * model.setValue(row, portraitColumn, portrait);
 * </pre>
 * 
 * There is a special kind of DataColumn for storing references to Java
 * objects, DataColumnReference. This is used so that you can find your way
 * back to the domain object model that your data came from in the first
 * place. Indeed, the above code would probably have been done as follows:
 * 
 * <pre>
 * Monarch george;
 * ...
 * 
 * row = model.appendRow();
 * model.setValue(row, monarchNameColumn, george.getFormalName());
 * model.setValue(row, coronatedYearColumn, george.getCoronationYear());
 * model.setValue(row, portraitColumn, george.getPortrait());
 * model.setValue(row, monarchObjectColumn, george);
 * </pre>
 * 
 * (assuming we created our model with such a DataColumnReference in the first
 * place). The key part is the last line, where we store [a reference to] the
 * object itself in the model.
 * 
 * <h2>Retrieving data</h2>
 * 
 * You can retrieve data from a TreeModel with the same <var>row</var>,
 * <var>column</var> co-ordinates used when storing data:
 * 
 * <pre>
 * String name;
 * 
 * name = model.getValue(row, column);
 * </pre>
 * 
 * The <code>row</code> TreeIter in this case usually comes from a TreeView
 * {@link TreeView.RowActivated} signal or a TreeSelection
 * {@link TreeSelection.Changed} signal. You can also get a TreeIter for a
 * specific row via <code>getIter()</code>. Less frequently you will want to
 * iterate over all the rows in the model, which is possible as follows:
 * 
 * <pre>
 * row = model.getIterFirst();
 * do {
 *     name = model.getValue(row, column);
 *     // do something with name
 * } while (row.iterNext());
 * </pre>
 * 
 * Although we have illustrated getting a String out of the TreeModel here,
 * you will normally only need to retrieve the Java object from your domain
 * model from which the data in this row was derived and which it represents:
 * 
 * <pre>
 * ruler = (Monarch) model.getValue(row, monarchObjectColumn);
 * </pre>
 * 
 * once you have the Java object that has been "selected" by the user in your
 * TreeView in hand, you can carry on from there with your application logic.
 * 
 * <p>
 * As discussed in the documentation for DataColumn, TreeModels are only
 * really meant as the backing store for a TreeView. By in large, you only use
 * them as the means to drive what is being displayed by a TreeView; there's
 * no reason to try and store a complex domain model in a GTK TreeModel. [By
 * analogy, the String you pass to Label's <code>setLabel()</code> is merely
 * setting the <var>label</var> property which is the "data store" backing the
 * text displayed by the Label. You only push down what you want displayed;
 * the rest of your data model stays in Java, of course. It's the same with
 * TreeView]
 * 
 * @author Andrew Cowie
 * @author Peter Miller
 * @author Vreixo Formoso
 * @since 4.0.5
 * 
 */
/*
 * This is a departure from a strict mapping of the underlying library; in GTK
 * TreeModel is an interface implemented by things like ListStore and
 * TreeStore; by using it as an abstract superclass instead we can avoid
 * duplicating the implementation of quite a lot of code.
 */
public abstract class TreeModel extends org.gnome.glib.Object
{
    protected TreeModel(long pointer) {
        super(pointer);
    }

    /**
     * Used by the constructors. Convert from public DataColumn entities to
     * the Class array we'll be passing into the translation layer, carrying
     * out the crucial step of setting the column number ordinals along the
     * way.
     */
    protected static final Class<?>[] typesToClassNames(DataColumn[] types) {
        final Class<?>[] names;

        if (types == null) {
            throw new IllegalArgumentException("Array passed to TreeModel constructor must not be null");
        }

        if (types.length == 0) {
            throw new IllegalArgumentException(
                    "Must specify at least one column when constructing a TreeModel");
        }

        names = new Class[types.length];

        for (int i = 0; i < types.length; i++) {
            names[i] = types[i].getType();
            types[i].setOrdinal(i);
        }

        return names;
    }

    /**
     * The concrete TreeModels have their own setValue() methods that take a
     * typed ListStore or TreeStore , so we concentrate the calls and delegate
     * from here so they can call their specific translation method
     * accordingly. Putting it here avoids recursive overload problems we ran
     * into.
     */
    protected void dispatch(TreeIter row, DataColumn column, Value value) {
        throw new UnsupportedOperationException(
                "You need to implement setValue() for your TreeModel subclass");
    }

    /*
     * Check that the given iter is valid for this model. Throw an
     * IllegalArgumentException if it is not valid.
     */
    protected void checkIter(TreeIter iter) {
        if (iter == null) {
            throw new IllegalArgumentException("TreeIter can't be null");
        }
        if (this != iter.getModel()) {
            throw new IllegalArgumentException("TreeIter not valid for this TreeModel");
        }
    }

    /**
     * Store a String in this TreeModel at the specified <code>row</code> and
     * <code>column</code>.
     */
    public void setValue(TreeIter row, DataColumnString column, String value) {
        checkIter(row);
        dispatch(row, column, new Value(value));
    }

    /**
     * Get the String stored in this TreeModel at the specified
     * <code>row</code> and <code>column</code>.
     */
    public String getValue(TreeIter row, DataColumnString column) {
        final Value result;

        checkIter(row);

        result = new Value();

        GtkTreeModel.getValue(this, row, column.getOrdinal(), result);

        return result.getString();
    }

    /**
     * Get the <code>int</code> value stored in this TreeModel at the
     * specified <code>row</code> and <code>column</code>.
     */
    public int getValue(TreeIter row, DataColumnInteger column) {
        final Value result;

        checkIter(row);

        result = new Value();

        GtkTreeModel.getValue(this, row, column.getOrdinal(), result);

        return result.getInteger();
    }

    /**
     * Store an <code>int</code> in this TreeModel at the specified
     * <code>row</code> and <code>column</code>.
     */
    public void setValue(TreeIter row, DataColumnInteger column, int value) {
        checkIter(row);
        dispatch(row, column, new Value(value));
    }

    /**
     * Get the <code>long</code> value stored in this TreeModel at the
     * specified <code>row</code> and <code>column</code>.
     */
    public long getValue(TreeIter row, DataColumnLong column) {
        final Value result;

        checkIter(row);

        result = new Value();

        GtkTreeModel.getValue(this, row, column.getOrdinal(), result);

        return result.getLong();
    }

    /**
     * Store an <code>int</code> in this TreeModel at the specified
     * <code>row</code> and <code>column</code>.
     */
    public void setValue(TreeIter row, DataColumnLong column, long value) {
        checkIter(row);
        dispatch(row, column, new Value(value));
    }

    /**
     * Get the <code>boolean</code> value stored in this TreeModel at the
     * specified <code>row</code> and <code>column</code>.
     */
    public boolean getValue(TreeIter row, DataColumnBoolean column) {
        final Value result;

        checkIter(row);

        result = new Value();

        GtkTreeModel.getValue(this, row, column.getOrdinal(), result);

        return result.getBoolean();
    }

    /**
     * Store a <code>boolean</code> in this TreeModel at the specified
     * <code>row</code> and <code>column</code>.
     */
    public void setValue(TreeIter row, DataColumnBoolean column, boolean value) {
        checkIter(row);
        dispatch(row, column, new Value(value));
    }

    Pixbuf getValue(TreeIter row, DataColumnPixbuf column) {
        final Value result;

        checkIter(row);

        result = new Value();

        GtkTreeModel.getValue(this, row, column.getOrdinal(), result);

        return result.getPixbuf();
    }

    /**
     * Store a Stock icon in this TreeModel at the specified <code>row</code>
     * and <code>column</code>.
     * 
     * @since 4.0.7
     */
    public void setValue(TreeIter row, DataColumnStock column, Stock value) {
        checkIter(row);
        dispatch(row, column, new Value(value.getStockId()));
    }

    /**
     * Get the Stock icon stored in this TreeModel at the specified
     * <code>row</code> and <code>column</code>.
     * 
     * @since 4.0.7
     */
    public Stock getValue(TreeIter row, DataColumnStock column) {
        final Value result;

        checkIter(row);

        result = new Value();

        GtkTreeModel.getValue(this, row, column.getOrdinal(), result);

        return Stock.instanceFor(result.getString());
    }

    /**
     * Get a reference to the Java object stored in this TreeModel at the
     * specified <code>row</code> and <code>column</code>. You'll have to cast
     * the return value to whatever type you put in there in the first place,
     * obviously.
     */
    /*
     * TODO would making this generic help?
     */
    public java.lang.Object getValue(TreeIter row, DataColumnReference column) {
        checkIter(row);
        return GtkTreeModelOverride.getReference(this, row, column.getOrdinal());
    }

    /**
     * Store a reference to a Java object in the TreeModel at the specified
     * <code>row</code> and <code>column</code>. This is used so you can get
     * <i>back</i> to your Java side domain object model in response to an
     * event on the TreeView.
     */
    /*
     * Calls a custom override as we manually manage a global reference to the
     * passed object on the JNI side. This also avoids the ambiguity collision
     * in the signatures of Value(org.gnome.glib.Object) and
     * Value(java.lang.Object) that otherwise arose and prevented compilation.
     */
    public void setValue(TreeIter row, DataColumnReference column, java.lang.Object value) {
        checkIter(row);
        GtkTreeModelOverride.setReference(this, row, column.getOrdinal(), value);
    }

    /**
     * Store a Pixbuf in this TreeModel at the specified <code>row</code> and
     * <code>column</code>. This is used to provide the image data needed by a
     * TreeViewColumn with a {@link CellRendererPixbuf CellRendererPixbuf} in
     * it.
     */
    public void setValue(TreeIter row, DataColumnPixbuf column, Pixbuf value) {
        checkIter(row);
        dispatch(row, column, new Value(value));
    }

    /**
     * Initialize a new iterator at the beginning of the model. Since you
     * presumably want to iterate through the remaining rows, use the
     * {@link TreeIter#iterNext() iterNext()} method you'll find on TreeIter
     * as follows:
     * 
     * <pre>
     * TreeIter row;
     * 
     * row = model.getIterFirst();
     * do {
     *     // do something with row
     * } while (row.iterNext());
     * </pre>
     * 
     * @return <code>null</code> if the model is presently empty.
     */
    public TreeIter getIterFirst() {
        final TreeIter iter;

        iter = new TreeIter(this);

        if (GtkTreeModel.getIterFirst(this, iter)) {
            return iter;
        } else {
            return null;
        }
    }

    /**
     * Convert a TreePath to a TreeIter appropriate for this TreeModel. See
     * {@link TreePath TreePath} for a full explanation of how to specify
     * paths into ListStores and TreeStores.
     * 
     * @return <code>null</code> if it can't figure out how to make the
     *         conversion of the given TreePath into a TreeIter pointing into
     *         this TreeModel.
     */
    public TreeIter getIter(TreePath path) {
        final TreeIter iter;

        iter = new TreeIter(this);

        if (GtkTreeModel.getIter(this, iter, path)) {
            return iter;
        } else {
            return null;
        }
    }

    /**
     * Get a TreePath corresponding to the row being pointed at by the given
     * TreeIter.
     * 
     * <p>
     * Remember that TreePaths, like TreeIters, are not stable across changes
     * to the model; if you need to reliably point to a given row use
     * {@link TreeRowReference} instead.
     * 
     * @since 4.0.6
     */
    public TreePath getPath(TreeIter row) {
        checkIter(row);
        return GtkTreeModel.getPath(this, row);
    }

    /**
     * The signal emitted when a row in the model is changed.
     * 
     * @author Andrew Cowie
     * @since 4.0.6
     */
    public interface RowChanged extends GtkTreeModel.RowChangedSignal
    {
        /**
         * The <code>path</code> and <code>row</code> arguments give you valid
         * a TreePath and TreeIter respectively pointing at the row that
         * changed. Be wary, though, that these are not going to be stable
         * beyond the invocation of this callback; if one row has changed, you
         * can bet others are changing too. Do what you need to do and leave
         * the variables to be collected.
         * 
         * <p>
         * For subtle implementation reasons, you can't iterate using the
         * <code>row</code> TreeIter. If you need to cycle over the model, get
         * a TreeIter pointing to the beginning of the model as follows:
         * 
         * <pre>
         * row = source.getIterFirst();
         * do {
         *     // do whatever with each row
         * } while (row.iterNext());
         * </pre>
         */
        public void onRowChanged(TreeModel source, TreePath path, TreeIter row);
    }

    /**
     * Hook up a handler for <code>TreeModel.RowChanged</code> signals.
     * 
     * @since 4.0.6
     */
    public void connect(TreeModel.RowChanged handler) {
        GtkTreeModel.connect(this, handler, false);
    }

    /** @deprecated */
    public interface ROW_CHANGED extends GtkTreeModel.RowChangedSignal
    {
    }

    /** @deprecated */
    public void connect(ROW_CHANGED handler) {
        assert false : "use TreeModel.RowChanged instead";
        GtkTreeModel.connect(this, handler, false);
    }

}
