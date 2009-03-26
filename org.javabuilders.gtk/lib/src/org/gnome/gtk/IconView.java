/*
 * IconView.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2008      Vreixo Formoso
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * A Widget that displays the data of a {@link TreeModel} as a grid of icons
 * with labels. It is thus an alternative to TreeView, and like it, IconView
 * is the view part of GTK's model-view-controller pattern list, and thus it
 * needs a TreeModel where the data to be displayed is actually stored.
 * 
 * <p>
 * It is however, much simpler and less powerful than TreeView. First of all,
 * you cannot display a hierarchical model (such as {@link TreeStore}) in an
 * IconView. Only list models, such as {@link ListStore} can be used.
 * 
 * <p>
 * In an IconView the rows of the underlying TreeModel are represented as
 * items with an icon and a text label. The icon is taken from a
 * {@link DataColumnPixbuf} column of the model, and the label is taken from a
 * {@link DataColumnString}. As you may guess, only those two columns of the
 * model can be displayed in an IconView.
 * 
 * <p>
 * On the other side, configuring an IconView is easier than a TreeView. First
 * of all, you should configure the model:
 * 
 * <pre>
 * final ListStore model;
 * final DataColumnString labelColumn;
 * final DataColumnPixbuf iconColumn;
 * final IconView view;
 * 
 * ...
 * model = new ListStore(new DataColumn[] {
 *     labelColumn,
 *     ...
 * }
 * </pre>
 * 
 * Of course, you don't need to populate the model before configuring the
 * IconView. That configuration is easy, as you only need to set the data
 * columns from which the icon and label will be taken.
 * 
 * <pre>
 * view = new IconView(model);
 * view.setPixbufColumn(iconColumn);
 * 
 * // you can use setMarkupColumn() as an alternative
 * view.setTextColumn(labelColumn);
 * </pre>
 * 
 * Once the IconView is configured, you may want to hook up a handler for the
 * {@link IconView.ItemActivated} signal, emitted when the user activates an
 * item (for example, by double-clicking over it).
 * 
 * <p>
 * Finally, an IconView has methods to handle the selection, so you don't need
 * to use the TreeSelection object.
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.7
 */
public class IconView extends Container implements CellLayout
{
    protected IconView(long pointer) {
        super(pointer);
    }

    /**
     * Create a new IconView
     * 
     * <p>
     * Remember you cannot use a TreeStore model with an IconView.
     * 
     * @since 4.0.7
     */
    public IconView(TreeModel model) {
        super(GtkIconView.createIconViewWithModel(checkModel(model)));
    }

    /**
     * Construct an IconView whose backing model will be assigned later.
     * You'll need to use {@link #setModel(TreeModel) setModel()} to set it.
     * 
     * @since 4.0.8
     */
    public IconView() {
        super(GtkIconView.createIconView());
    }

    /**
     * Set or change the TreeModel from which this IconView draws its data.
     * 
     * @since 4.0.7
     */
    public void setModel(TreeModel model) {
        GtkIconView.setModel(this, checkModel(model));
    }

    private static TreeModel checkModel(final TreeModel model) {
        if (model instanceof TreeStore) {
            throw new IllegalArgumentException("model has to be a ListStore");
            // or what else?
        }
        return model;
    }

    /**
     * Set the DataColumn in the TreeModel containing the plain text to use as
     * label of each item. Usually you will use this method to choose the
     * model column to map to the label. Alternatively you can use
     * {@link #setMarkupColumn(DataColumnString) setMarkupColumn()} to provide
     * a text with Pango markup.
     * 
     * @since 4.0.7
     */
    public void setTextColumn(DataColumnString column) {
        GtkIconView.setTextColumn(this, column.getOrdinal());
    }

    /**
     * Set the DataColumn, containing Pango markup, to render as the label of
     * each item.
     * 
     * @since 4.0.7
     */
    public void setMarkupColumn(DataColumnString column) {
        GtkIconView.setMarkupColumn(this, column.getOrdinal());
    }

    /**
     * Set the DataColumn of the model that contains the Pixbuf to use as the
     * icon of each item.
     * 
     * @since 4.0.7
     */
    public void setPixbufColumn(DataColumnPixbuf column) {
        GtkIconView.setPixbufColumn(this, column.getOrdinal());
    }

    /**
     * Emitted when an item in the IconView has been activated. Activation
     * occurs when an item in the view is double-clicked, or when <b>
     * <code>Space</code></b> or <b><code>Enter</code></b> are pressed while
     * an item is selected.
     * 
     * <p>
     * In general, you've got the TreeModel and especially its DataColumns
     * visible, so to use <code>IconView.ItemActivated</code> you can just:
     * 
     * <pre>
     * final TreeModel model;
     * final DataColumnString column;
     * 
     * view.connect(new IconView.ItemActivated() {
     *     public void onItemActivated(IconView source, TreePath path) {
     *         final TreeIter row;
     * 
     *         row = model.getIter(path);
     * 
     *         ... = model.getValue(row, column);
     *     }
     * });
     * </pre>
     * 
     * Remember that TreeIters and TreePaths are not stable over changes to
     * the model, so get on with using <code>path</code> right away.
     * 
     * @author Vreixo Formoso
     * @since 4.0.7
     */
    public interface ItemActivated extends GtkIconView.ItemActivatedSignal
    {
        /**
         * The useful parameter is usually <code>path</code> which can be
         * converted into a TreeIter with your TreeModel's
         * {@link TreeModel#getIter(TreePath) getIter()} allowing you to then
         * lookup a particular value from the data model.
         */
        public void onItemActivated(IconView source, TreePath path);
    }

    /**
     * Hook up a <code>IconView.ItemActivated</code> handler.
     * 
     * @since 4.0.7
     */
    public void connect(IconView.ItemActivated handler) {
        GtkIconView.connect(this, handler, false);
    }

    /** @deprecated */
    public interface ITEM_ACTIVATED extends GtkIconView.ItemActivatedSignal
    {
    }

    /** @deprecated */
    public void connect(ITEM_ACTIVATED handler) {
        assert false : "use IconView.ItemActivated instead";
        GtkIconView.connect(this, handler, false);
    }

    /**
     * Set what kinds of selections are allowed. The interesting constants
     * you'll use most often are {@link SelectionMode#NONE NONE} and
     * {@link SelectionMode#MULTIPLE MULTIPLE} since
     * {@link SelectionMode#SINGLE SINGLE} is the default. See SelectionMode
     * for the details of the behaviour implied by each option.
     * 
     * @since 4.0.7
     */
    public void setSelectionMode(SelectionMode mode) {
        GtkIconView.setSelectionMode(this, mode);
    }

    /**
     * Get the items currently selected.
     * 
     * <p>
     * You can use the TreeModel's {@link TreeModel#getIter(TreePath)
     * getIter()} method to convert the returned TreePaths to the more
     * convenient TreeIter:
     * 
     * <pre>
     * IconView view;
     * TreeModel model;
     * 
     * for (TreePath path : view.getSelectedItems()) {
     *     TreeIter item = model.getIter(path);
     *     // do something with the item
     * }
     * </pre>
     * 
     * <p>
     * Also remember that both TreeIter and TreePath are temporally objects no
     * longer valid once you make any change to the model. Thus, if you plan
     * to modify the model, you may want to convert the returned TreePaths to
     * {@link TreeRowReference TreeRowReferences}.
     * 
     * @return An arrays with the selected items. If no item is selected, an
     *         empty array is returned.
     * 
     * @since 4.0.7
     */
    public TreePath[] getSelectedItems() {
        return GtkIconView.getSelectedItems(this);
    }

    /**
     * Signal emitted when the selection changes, i.e. when a new item is
     * selected or a previously selected item was unselected.
     * 
     * @author Vreixo Formoso
     * @since 4.0.7
     */
    public interface SelectionChanged extends GtkIconView.SelectionChangedSignal
    {
        public void onSelectionChanged(IconView source);
    }

    /**
     * Hook up a handler for the <code>IconView.SelectionChanged</code>
     * signal.
     * 
     * @since 4.0.7
     */
    public void connect(IconView.SelectionChanged handler) {
        GtkIconView.connect(this, handler, false);
    }

    /** @deprecated */
    public interface SELECTION_CHANGED extends GtkIconView.SelectionChangedSignal
    {
    }

    /** @deprecated */
    public void connect(SELECTION_CHANGED handler) {
        assert false : "use IconView.SelectionChanged instead";
        GtkIconView.connect(this, handler, false);
    }

    /**
     * The number of columns in which icons should be displayed. The default
     * is <code>-1</code> which indicates that the IconView will determine
     * this automatically based on the size allocated to it.
     * 
     * @since 4.0.8
     */
    public void setColumns(int num) {
        if ((num < 1) && (num != -1)) {
            throw new IllegalArgumentException("num must be positive, or -1 to indicate automatic");
        }
        GtkIconView.setColumns(this, num);
    }

    /**
     * Set the maximum width that will be given to each column of icons. The
     * width is measured in pixels. The default value is <code>-1</code> which
     * indicates
     * 
     * @since 4.0.8
     */
    public void setItemWidth(int width) {
        if ((width < 1) && (width != -1)) {
            throw new IllegalArgumentException("width must be positive, or -1 to indicate automatic");
        }
        GtkIconView.setItemWidth(this, width);
    }
}
