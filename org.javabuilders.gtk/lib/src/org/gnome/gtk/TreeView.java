/*
 * TreeView.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * Display the data from a {@link TreeModel} in a tabular form. TreeViews are
 * ubiquitous in most applications, being used to both output data in list
 * form, as well as allowing the user to select one or more items from a list.
 * TreeView is the view part of GTK's model-view-controller pattern list
 * Widget, with one of the TreeModel subclasses supplying the underlying data
 * model. <img src="TreeView.png" class="snapshot">
 * 
 * <p>
 * The TreeView API is very powerful, but with that power comes considerable
 * complexity. To build a working TreeModel backed TreeView you will need to
 * follow the instructions presented in the documentation quite carefully. The
 * remainder of this page discusses the presentation side of the API; see
 * {@link DataColumn DataColumn} for a detailed overview of the contents side.
 * 
 * <p>
 * A TreeView is composed of one or more vertical columns called
 * {@link TreeViewColumn TreeViewColumn}s. Into these are packed
 * {@link CellRenderer CellRenderer}s. A CellRenderer does the job of taking
 * data from the underlying TreeModel and rendering it in the TreeViewColumn.
 * There is a family of CellRenderers for different underlying data types, but
 * you'll use CellRendererText almost exclusively.
 * 
 * <p>
 * Let's assume we have a ListStore with a String DataColumn in it, ie
 * 
 * <pre>
 * final ListStore model;
 * final DataColumnString countryNameColumn;
 * final TreeView view;
 * final TreeSelection selection;
 * TreeViewColumn vertical;
 * CellRendererText text;
 * 
 * ...
 * model = new ListStore(new DataColumn[] {
 *     countryNameColumn,
 *     ...
 * }
 * </pre>
 * 
 * Note that there is nothing that requires you to <i>populate</i> your model
 * before building your TreeView. You can do that later - indeed, that might
 * be the whole point of your application.
 * 
 * <p>
 * You start creating your view by instantiating a TreeView and then using it
 * to get TreeViewColumn instances:
 * 
 * <pre>
 * view = new TreeView(model);
 * vertical = view.appendColumn();
 * vertical.setTitle(&quot;Country&quot;);
 * </pre>
 * 
 * Now you construct a CellRenderer, specifying what TreeViewColumn it's going
 * to be a part of, and then the most important part, specifying where its
 * data is going to come from. This is the step that binds TreeView and
 * TreeModel.
 * 
 * <pre>
 * text = new CellRendererText(vertical);
 * text.setText(countryNameColumn);
 * </pre>
 * 
 * along with setting any other properties on the CellRenderer as necessary.
 * And that's it! You will of course need to do this for each TreeViewColumn
 * of information you wish to have showing in your TreeView. (We tend to find
 * it easier if you reuse the TreeViewColumn and CellRenderer variable names;
 * there is usually no real reason to keep a reference to them individually;
 * otherwise you've got to come up with unique names for everything and that
 * tends to make for ugly code).
 * 
 * <p>
 * Dealing with the events generated on the TreeView is either straight
 * forward or quite complicated, depending on what you are trying to
 * accomplish. If you just need a callback when the user activates a row in
 * the display, then the {@link TreeView.RowActivated} signal will do the
 * trick fairly simply; see its documentation for an example. For anything
 * else, you will need to use the {@link TreeSelection TreeSelection} helper
 * class (every TreeView automatically has one). It has a
 * {@link TreeSelection.Changed} signal which you hook up to which will tell
 * you what row(s) are currently selected.
 * 
 * <pre>
 * selection = view.getSelection();
 * </pre>
 * 
 * <p>
 * The design of the TreeView API is such that you can have more than one view
 * for a given TreeModel, but we tend to only create TreeModels as the place
 * to push the text that we wish displayed, so in general you'll have one
 * TreeModel per TreeView.
 * 
 * <p>
 * <i>We have departed a fair way from the method call sequence used in the
 * underlying GTK library, in particular by assuming default behaviour and
 * combining calls where possible. This is in an effort to make the TreeView
 * API somewhat easier to learn, more appropriate in a Java context, and
 * easier to use for the common cases which dominate its usage.</i>
 * 
 * @author Andrew Cowie
 * @author Srichand Pendyala
 * @author Vreixo Formoso
 * @since 4.0.5
 */
public class TreeView extends Container
{
    protected TreeView(long pointer) {
        super(pointer);
    }

    /**
     * Construct a new TreeView with an already established TreeModel as its
     * data.
     */
    public TreeView(TreeModel store) {
        super(GtkTreeView.createTreeViewWithModel(store));
    }

    /**
     * Construct a new TreeView. If you use this constructor, you will need to
     * call {@link #setModel(TreeModel) setModel()} before any data will be
     * displayable!
     */
    public TreeView() {
        super(GtkTreeView.createTreeView());
    }

    /**
     * Set the TreeModel being used to source data for this TreeView. If a
     * model has already been set, calling this will replace it.
     * 
     * @param store
     *            a value of <code>null</code> will remove the data model
     *            underlying this TreeView, leaving it unset for the present.
     */
    public void setModel(TreeModel store) {
        GtkTreeView.setModel(this, store);
    }

    /**
     * Get the TreeModel currently providing the data powering this TreeView
     * Widget, or <code>null</code> if not yet set.
     */
    public TreeModel getModel() {
        return GtkTreeView.getModel(this);
    }

    /**
     * Create a new TreeViewColumn and add it to right-hand edge of this
     * TreeView.
     */
    /*
     * It is quite easy to screw up by creating a TreeViewColumn, configuring
     * it and its CellRenderer, only to forget to add it to the TreeView. We
     * get around this by making appendColumn() return a new TreeViewColumn.
     * Nicely complements appendRow() returning a TreeIter in the TreeModels()
     * too.
     */
    public TreeViewColumn appendColumn() {
        final TreeViewColumn vertical;

        vertical = new TreeViewColumn();

        GtkTreeView.appendColumn(this, vertical);

        return vertical;
    }

    /**
     * Set whether this TreeView has a header row at the top of the Widget
     * showing the titles of each of the TreeViewColumns packed into it. The
     * default is <code>true</code>, for headers to be visible.
     */
    public void setHeadersVisible(boolean setting) {
        GtkTreeView.setHeadersVisible(this, setting);
    }

    /**
     * Set whether the column titles in the header row can be clicked to
     * change the sorting of the displayed data. While the default is
     * <code>false</code> (since you frequently have the rows ordered the way
     * they are for a reason and don't want to let the user be reordering the
     * display and getting lost in the process), calling TreeViewColumn's
     * {@link TreeViewColumn#setSortColumn(DataColumn) setSortColumn()} will
     * make the headers clickable. Use this method after your column setup to
     * turn it off [again].
     */
    public void setHeadersClickable(boolean setting) {
        GtkTreeView.setHeadersClickable(this, setting);
    }

    /**
     * Emitted when a row in the TreeView has been activated. Activation
     * occurs when a row in the view is double-clicked, or when <b>
     * <code>Space</code></b> or <b><code>Enter</code></b> are pressed while a
     * row is selected.
     * 
     * <p>
     * In general, you've got the TreeModel and especially its DataColumns
     * visible, so to use <code>TreeView.RowActivated</code> you can just:
     * 
     * <pre>
     * final TreeModel model;
     * final DataColumnString column;
     * 
     * view.connect(new TreeView.RowActivated() {
     *     public void onRowActivated(TreeView source, TreePath path, TreeViewColumn vertical) {
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
     * <p>
     * <code>TreeView.RowActivated</code> is perfectly sufficient for basic
     * situations, but you may need to see TreeSelection's
     * {@link TreeSelection.Changed} to for more complicated selection and
     * activation expressions. In practise you'll use both.
     * 
     * @author Andrew Cowie
     * @since 4.0.5
     */
    public interface RowActivated extends GtkTreeView.RowActivatedSignal
    {
        /**
         * The useful parameter is usually <code>path</code> which can be
         * converted into a TreeIter with your TreeModel's
         * {@link TreeModel#getIter(TreePath) getIter()} allowing you to then
         * lookup a particular value from the data model. You rarely need
         * <code>vertical</code> but it can give you some indication in which
         * column the click happened.
         */
        public void onRowActivated(TreeView source, TreePath path, TreeViewColumn vertical);
    }

    /**
     * Hook up a <code>TreeView.RowActivated</code> handler.
     */
    public void connect(TreeView.RowActivated handler) {
        GtkTreeView.connect(this, handler, false);
    }

    /**
     * Cause a <code>TreeView.RowActivated</code> signal to be emitted for the
     * given TreePath. The TreeViewColumn is optional; use <code>null</code>
     * if you don't want to specify it.
     * 
     * @since 4.0.9
     */
    public void emitRowActivated(TreePath path, TreeViewColumn vertical) {
        GtkTreeView.rowActivated(this, path, vertical);
    }

    /** @deprecated */
    public interface ROW_ACTIVATED extends GtkTreeView.RowActivatedSignal
    {
    }

    /** @deprecated */
    public void connect(ROW_ACTIVATED handler) {
        assert false : "use TreeView.RowActivated instead";
        GtkTreeView.connect(this, handler, false);
    }

    /**
     * Emitted when a row in the TreeView has been expanded, i.e. when its
     * child nodes are shown. A row is expanded either by clicking in the
     * little arrow near it, or by pressing the <code>+</code> key when a row
     * is selected. Of course, a row can be only expanded when it has child
     * rows, and so it can be only emitted when the TreeView is used with a
     * hierarchical model such as {@link TreeStore}.
     * 
     * <p>
     * In general, you've got the TreeModel and especially its DataColumns
     * visible, so to use <code>TreeView.RowExpanded</code> you can just:
     * 
     * <pre>
     * final TreeModel model;
     * final DataColumnString column;
     * 
     * view.connect(new TreeView.RowExpanded() {
     *     public void onRowExpanded(TreeView source, TreeIter iter, TreePath path) {
     *         ... = model.getValue(iter, column);
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
    public interface RowExpanded
    {
        public void onRowExpanded(TreeView source, TreeIter iter, TreePath path);
    }

    /**
     * Hook up a <code>TreeView.RowExpanded</code> handler.
     * 
     * @since 4.0.7
     */
    public void connect(TreeView.RowExpanded handler) {
        GtkTreeView.connect(this, new RowExpandedHandler(handler), false);
    }

    /*
     * This internal class is needed because the TreeIter passed to the
     * handler does not have the model field properly set, so we need to set
     * it before passing the TreeIter to the user.
     */
    private static class RowExpandedHandler implements GtkTreeView.RowExpandedSignal
    {
        private final TreeView.RowExpanded handler;

        private RowExpandedHandler(TreeView.RowExpanded handler) {
            super();
            this.handler = handler;
        }

        public void onRowExpanded(TreeView source, TreeIter iter, TreePath path) {
            iter.setModel(source.getModel());
            handler.onRowExpanded(source, iter, path);
        }
    }

    /** @deprecated */
    public interface ROW_EXPANDED extends GtkTreeView.RowExpandedSignal
    {
    }

    /** @deprecated */
    public void connect(ROW_EXPANDED handler) {
        assert false : "use TreeView.RowExpanded instead";
        GtkTreeView.connect(this, new RowExpandedHandler0(handler), false);
    }

    /** @deprecated */
    private static class RowExpandedHandler0 implements GtkTreeView.RowExpandedSignal
    {
        private final ROW_EXPANDED handler;

        /** @deprecated */
        private RowExpandedHandler0(ROW_EXPANDED handler) {
            super();
            this.handler = handler;
        }

        public void onRowExpanded(TreeView source, TreeIter iter, TreePath path) {
            iter.setModel(source.getModel());
            handler.onRowExpanded(source, iter, path);
        }
    }

    /**
     * Check whether the given row is expanded, i.e. whether its children are
     * shown.
     * 
     * @param path
     *            The row we want to check.
     * @return <code>true</code> if the row is expanded, <code>false</code> if
     *         not.
     * @since 4.0.7
     */
    public boolean rowExpanded(TreePath path) {
        return GtkTreeView.rowExpanded(this, path);
    }

    /**
     * Expand the given row, making its children visible to the user. This has
     * no effect if the row has no child nodes. Of course, this is always the
     * case if you use a ListStore model.
     * 
     * @param path
     *            The row to expand.
     * @param openAll
     *            <code>true</code> to recursively expand all children,
     *            <code>false</code> to expand only the given row.
     * @return <code>true</code> if the path refers to a valid row, and it has
     *         child nodes. <code>false</code> otherwise.
     * @since 4.0.7
     */
    public boolean expandRow(TreePath path, boolean openAll) {
        return GtkTreeView.expandRow(this, path, openAll);
    }

    /**
     * Collapse the given row, thus hiding its children if the row was
     * previously expanded.
     * 
     * @param path
     *            The row to collapse.
     * @since 4.0.7
     */
    public void collapseRow(TreePath path) {
        GtkTreeView.collapseRow(this, path);
    }

    private TreeSelection selection;

    /**
     * Get the TreeSelection object corresponding to this TreeView. Every
     * TreeView has a TreeSelection which is a utility instance allowing you
     * to manipulate the state of the selected row(s) in the TreeView. This
     * method gives you access to it.
     */
    public TreeSelection getSelection() {
        if (selection == null) {
            selection = GtkTreeView.getSelection(this);
        }
        return selection;
    }

    /**
     * Set whether or not the built-in quick search capability will be enabled
     * for this TreeView. If the user focuses the TreeView and starts typing
     * characters a small popup Entry will appear and the characters entered
     * will be used to interactively search through the list and will
     * progressively select the row which is the closest match.
     * 
     * <p>
     * Use {@link #setSearchColumn(DataColumn) setSearchColumn()} to indicate
     * which data source in your TreeModel is actually what the interactive
     * text search will seek through.
     * 
     * <p>
     * The default is <code>true</code>, so this method is only called when
     * you wish to disable type-ahead find.
     */
    public void setEnableSearch(boolean setting) {
        GtkTreeView.setEnableSearch(this, setting);
    }

    /**
     * Check if the built-in quick search capability is enabled for this
     * TreeView. The default is <code>true</code>.
     * 
     * <p>
     * Use {@link #setEnableSearch(boolean) setEnableSearch()} to disable or
     * enable the search feature.
     * 
     * @return <code>true</code> if the quick search capability is enabled;
     *         <code>false</code> otherwise.
     */

    public boolean getEnableSearch() {
        return GtkTreeView.getEnableSearch(this);
    }

    /**
     * Set the column in your TreeModel which will searched through if the
     * user starts an interactive search. Ordinarily this can just be the
     * DataColumnString of whichever column you want as the index, but if you
     * have applied extensive formatting then you may need to supply an
     * auxiliary column with the data in cleaner form (the use of
     * DataColumnIntegers to provide sort order for verticals that are
     * displaying formatting via
     * {@link TreeViewColumn#setSortColumn(DataColumn) setSortColumn()} is
     * analogous).
     */
    public void setSearchColumn(DataColumn column) {
        GtkTreeView.setSearchColumn(this, column.getOrdinal());
    }

    /**
     * Get the current Entry widget being used for the interactive search
     * feature for this TreeView. If the built-in widget is being used for
     * search, then <code>null</code> is returned.
     */
    public Entry getSearchEntry() {
        return GtkTreeView.getSearchEntry(this);
    }

    /**
     * Set an Entry to be used as an alternative to the default built-in popup
     * used by the the interactive search. This is useful for occasions when
     * you want to put the search Entry at some fixed location elsewhere in
     * your UI.
     * 
     * <p>
     * To reset the TreeView to use the built-in popup Entry, pass in
     * <code>null</code>.
     */
    public void setSearchEntry(Entry entry) {
        GtkTreeView.setSearchEntry(this, entry);
    }

    /**
     * Set whether rubber banding is enabled in this TreeView.
     * 
     * <p>
     * Rubber banding affects how selections work when the selection mode is
     * set to {@link SelectionMode#MULTIPLE MULTIPLE}. When set to
     * <code>true</code> then rubber banding will allow the user to select
     * multiple rows with the mouse. Rubber banding is off by default.
     */
    public void setRubberBanding(boolean enable) {
        GtkTreeView.setRubberBanding(this, enable);
    }

    /**
     * Get the current status of the rubber banding property of the TreeView.
     * See {@link #setRubberBanding(boolean) setRubberBanding()} for a
     * detailed description of how rubber banding works.
     */
    public boolean getRubberBanding() {
        return GtkTreeView.getRubberBanding(this);
    }

    /**
     * Get the Adjustment currently being used for the horizontal aspect of
     * this TreeView. If no horizontal adjustment is being used, then a
     * <code>null</code> is returned. To set this value, see
     * {@link #setHAdjustment(Adjustment) setHAdjustment()}.
     */
    public Adjustment getHAdjustment() {
        return GtkTreeView.getHadjustment(this);
    }

    /**
     * Set the Adjustment for the horizontal aspect of this TreeView. To fetch
     * the current value of the horizontal adjustment aspect of this TreeView,
     * use {@link #getHAdjustment() getHAdjustment()}.
     */
    public void setHAdjustment(Adjustment adjustment) {
        GtkTreeView.setHadjustment(this, adjustment);
    }

    /**
     * Get the Adjustment for the vertical aspect of this TreeView. If the
     * vertical Adjustment has not been previously set, this value is
     * <code>null</code>.
     */
    public Adjustment getVAdjustment() {
        return GtkTreeView.getVadjustment(this);
    }

    /**
     * Set the Adjustment for the vertical aspect of this TreeView. To fetch
     * the current vertical adjustment aspect of this TreeView, see
     * {@link #getVAdjustment() getVAdjustment()}.
     */
    public void setVAdjustment(Adjustment adjustment) {
        GtkTreeView.setVadjustment(this, adjustment);
    }

    /**
     * Set the fixed height mode for the TreeView. When set to true, all
     * displayed rows in the TreeView are displayed with the same height. This
     * can have the effect of speeding up the TreeView, although you will have
     * to evaluate this in the specific circumstances particular to your
     * application.
     * 
     * <p>
     * To fetch the current height mode, see {@link #getFixedHeightMode()
     * getFixedHeightMode()}.
     * 
     * @param enable
     *            <code>true</code> if all rows in the TreeView are to be of
     *            the same height; <code>false</code> otherwise. The default
     *            is <code>false</code>.
     */
    public void setFixedHeightMode(boolean enable) {
        GtkTreeView.setFixedHeightMode(this, enable);
    }

    /**
     * Get the current fixed height mode for the TreeView. When set to true,
     * all displayed rows in the TreeView are displayed with the same height.
     * 
     * <p>
     * To set the current height mode, see
     * {@link #setFixedHeightMode(boolean) setFixedHeightMode()}
     * 
     * @return <code>true</code> if all rows are to be of the same height;
     *         <code>false</code> otherwise.
     */
    public boolean getFixedHeightMode() {
        return GtkTreeView.getFixedHeightMode(this);
    }

    /**
     * This signal is emitted when the user selects all the rows in the
     * TreeView. This usually occurs, when the user presses the
     * <code>Ctrl+A</code> key combination.
     * 
     * <p>
     * This signal is particularly useful, when you wish to be able to offer
     * the user an option to do some manipulation on the data, when all data
     * is selected. For instance, upon selecting all the rows of the TreeView,
     * in say, an email client, where each row represents an email, an option
     * to mark all emails as read can be made to pop up.
     * 
     * <p>
     * This signal should also be used with much care. The "Principle of Least
     * Surprise" is rather easy to violate by misusing this signal.
     * 
     * @author Srichand Pendyala
     * 
     */
    public interface SelectAll extends GtkTreeView.SelectAllSignal
    {
        public boolean onSelectAll(TreeView source);
    }

    /**
     * Hook up a <code>TreeView.SelectAll</code> signal handler.
     */
    public void connect(TreeView.SelectAll handler) {
        GtkTreeView.connect(this, handler, false);
    }

    /** @deprecated */
    public interface SELECT_ALL extends GtkTreeView.SelectAllSignal
    {
    }

    /** @deprecated */
    public void connect(SELECT_ALL handler) {
        assert false : "use TreeView.SelectAll instead";
        GtkTreeView.connect(this, handler, false);
    }

    /**
     * Request that alternating colours be drawn in the background of the
     * TreeView. You call this to let the theme engine know that the user
     * would really be helped in comprehending the data you are presenting if
     * the rows were drawn with alternating background colours so as to
     * emphasize the difference between each line. Most themes honour this
     * request.
     * 
     * <p>
     * The default is <code>false</code>, not drawing alternating row colours.
     */
    public void setRulesHint(boolean setting) {
        GtkTreeView.setRulesHint(this, setting);
    }

    /**
     * Scroll the TreeView so that the cell specified by <code>path</code>,
     * <code>vertical</code> is visible.
     * 
     * <p>
     * Only one of <code>path</code> or <code>vertical</code> need to be
     * specified if you only want to scroll in one dimension. If
     * <code>path</code> is <code>null</code>, then it will only scroll
     * horizontally; if no TreeViewColumn is specified in
     * <code>vertical</code> (ie, likewise <code>null</code>), then only
     * vertical scrolling will take place.
     * 
     * <p>
     * This all assumes that you've placed the TreeView is within a
     * ScrolledWindow to enable scrolling behaviour!
     * 
     * @param rowAlign
     *            Determines where in the view the row specified by
     *            <code>path</code> is placed, with <code>0.0f</code>
     *            representing top, and <code>1.0f</code> representing bottom,
     *            as usual. The constants in Alignment such as
     *            {@link Alignment#CENTER CENTER} can be used.
     * @param colAlign
     *            Determines where in the view the column specified by
     *            <code>vertical</code> will be placed; <code>0.0f</code> is
     *            fully left, <code>1.0f</code> is fully right.
     * @since 4.0.6
     */
    public void scrollToCell(TreePath path, TreeViewColumn vertical, float rowAlign, float colAlign) {
        GtkTreeView.scrollToCell(this, path, vertical, true, rowAlign, colAlign);
    }

    /**
     * Scroll the TreeView so that the cell specified by <code>path</code>,
     * <code>vertical</code> is visible. This variant ignores alignment values
     * and just scrolls the TreeView so that the cell specified is visible,
     * closest to whichever edge it came in from, and doing nothing if the
     * cell is already on screen.
     * 
     * <p>
     * See the discussion about <code>path</code> or <code>vertical</code> in
     * the other {@link #scrollToCell(TreePath, TreeViewColumn, float, float)
     * scrollToCell()} method to learn how you can scroll in a single
     * direction only if desired.
     * 
     * @since 4.0.6
     */
    public void scrollToCell(TreePath path, TreeViewColumn vertical) {
        GtkTreeView.scrollToCell(this, path, vertical, false, 0.0f, 0.0f);
    }

    /**
     * Set whether the TreeModel being shown by this TreeView can be reordered
     * by dragging and dropping the rows. If this is turned on, no control is
     * given over the ordering, so if you need to care about that you're
     * probably going to need to manage drag and drop manually.
     * 
     * <p>
     * The default is <code>false</code>.
     * 
     * <p>
     * Incidentally, you can observe these changes by connecting to
     * <code>TreeView.RowInserted</code> and <code>TreeView.RowDeleted</code>.
     * 
     * @since 4.0.6
     */
    /*
     * TODO test that the signals mooted actually do get emitted when drag and
     * drop re-ordering is done by the user when we get around to implementing
     * them, and then turn those mentions in to links.
     */
    public void setReorderable(boolean setting) {
        GtkTreeView.setReorderable(this, setting);
    }

    /**
     * Expand all the rows in this TreeStore backed TreeView, making
     * <i>all</i> children visible.
     * 
     * @since 4.0.7
     */
    public void expandAll() {
        GtkTreeView.expandAll(this);
    }

    /**
     * Collapse all the (child) rows in this TreeStore backed TreeView. Only
     * top level rows will be visible.
     * 
     * @since 4.0.7
     */
    public void collapseAll() {
        GtkTreeView.collapseAll(this);
    }

    /**
     * Get the TreeViewColumn at the given position in the TreeView, with
     * <code>0</code> being the left-most one.
     * 
     * <p>
     * If they know they are going to need it later, most people just keep a
     * reference to the TreeViewColumn around when they create it.
     * 
     * @since 4.0.8
     */
    public TreeViewColumn getColumn(int index) {
        return GtkTreeView.getColumn(this, index);
    }

    /**
     * Set the current keyboard focus to be at a specific path in the
     * TreeView. This method selects the row at <code>path</code>, and as a
     * result is often used to draw a user's attention to a particular place.
     * 
     * <p>
     * If <code>vertical</code> is supplied, the specific TreeViewColumn
     * indicated is selected. This is usually used in concert with setting
     * <code>startEditing</code> to <code>true</code> which causes the
     * TreeView to immediately start editing at the the specified row and
     * column (assuming, of course, that that CellRenderer has been made
     * mutable. See {@link CellRendererText#setEditable(boolean)
     * setEditable()}).
     * 
     * @since 4.0.8
     */
    public void setCursor(TreePath path, TreeViewColumn vertical, boolean startEditing) {
        GtkTreeView.setCursor(this, path, vertical, startEditing);
    }

    /**
     * Get a TreePath indicating what row in the TreeView a given set of
     * co-ordinates corresponds to.
     * 
     * <p>
     * The position indicated by (<code>x</code>,<code>y</code>) is in
     * <var>bin co-ordinates</var>. Usually you are working in the context of
     * a handler hooked up to an Event and these values should be obtained
     * from that Event.
     * 
     * <p>
     * See also {@link #getColumnAtPos(int, int) getColumnAtPos()} for the
     * complementary method to find out what vertical the co-ordinates
     * correspond to.
     * 
     * <h2>Handling right-clicks</h2>
     * 
     * <p>
     * It is common to create a context menu as a result of a right-click on a
     * TreeView. Ordinarily, you would intercept the
     * <code>Widget.ButtonPressEvent</code> signal and then prepare your Menu
     * in the handler there, quite reasonably expecting that the row that you
     * have right-clicked on would be selected. Unfortunately, if you hook up
     * to that signal your code will run before the default handler and the
     * <i>previously</i> selected row will still be selected while your
     * handler runs. This is annoying. It is the default
     * <code>Widget.ButtonPressEvent</code> handler which selects the new row,
     * so you have to manually select it yourself before acting on the
     * right-click. It is <code>getPathAtPos()</code> which gives you the
     * ability to do so:
     * 
     * <pre>
     * view.connect(new Widget.ButtonPressEvent() {
     *     public boolean onButtonPressEvent(Widget source, EventButton event) {
     *         final int x, y;
     *         final TreePath path;
     *         final TreeSelection selection;
     * 
     *         if (event.getButton() != MouseButton.RIGHT) {
     *             return false;
     *         }
     * 
     *         x = (int) event.getX();
     *         y = (int) event.getY();
     *         path = view.getPathAtPos(x, y);
     * 
     *         selection = view.getSelection();
     *         selection.selectRow(path);
     * 
     *         // and now pop your context menu, doing
     *         // something with the row as appropriate.
     * 
     *         return true;
     *     }
     * });
     * </pre>
     * 
     * @return Will return <code>null</code> if the passed in co-ordinates do
     *         not correspond to a row in the TreeView.
     * @since 4.0.9
     */
    public TreePath getPathAtPos(int x, int y) {
        final TreePath[] path;
        final boolean result;

        path = new TreePath[1];

        result = GtkTreeView.getPathAtPos(this, x, y, path, null, null, null);

        if (result) {
            return path[0];
        } else {
            return null;
        }
    }

    /**
     * Figure out which TreeViewColumn a given event's co-ordinates correspond
     * to. See {@link #getPathAtPos(int, int) getPathAtPos()} for a detailed
     * discussion.
     * 
     * <p>
     * <i>In native GTK, this is implemented as an out parameter on the same
     * function that powers <code>getPathAtPos()</code>, but we've given it a
     * more coherent name here.</i>
     * 
     * @since 4.0.9
     */
    /*
     * This does not match the completion style we have elsewhere in
     * java-gnome where we use the return to differentiate the various
     * out-parameters. That is bad, but diverging from getPathAtPos() to
     * getAtPosPath() seems very distasteful. This is the less used code path.
     */
    public TreeViewColumn getColumnAtPos(int x, int y) {
        final TreeViewColumn[] column;
        final boolean result;

        column = new TreeViewColumn[1];

        result = GtkTreeView.getPathAtPos(this, x, y, null, column, null, null);

        if (result) {
            return column[0];
        } else {
            return null;
        }
    }
}
