/*
 * TreeViewColumn.java
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
 * A vertical visible column as presented in a {@link TreeView TreeView}
 * Widget. A TreeViewColumn's primary role is to connect one or more
 * CellRenderers to a vertical position in the tabular layout of a TreeView
 * and then to assign which attributes of that CellRenderer are powered by
 * what rows from the data TreeModel that will underlie the TreeView.
 * 
 * <p>
 * You get a TreeViewColumn by calling TreeView's
 * {@link TreeView#appendColumn() appendColumn()}.
 * 
 * @author Andrew Cowie
 * @author Stefan Prelle
 * @since 4.0.5
 */
/*
 * At considerable odds with the underlying library, we have moved the logic
 * relating to packing CellRenderers and mapping attributes to TreeModel
 * columns over to the CellRenderer classes.
 */
public class TreeViewColumn extends Object implements CellLayout
{
    protected TreeViewColumn(long pointer) {
        super(pointer);
    }

    /**
     * Construct a new TreeViewColumn. This doesn't need to be publicly
     * visible; you create one by appending a column to a TreeView with
     * appendColumn()
     */
    protected TreeViewColumn() {
        super(GtkTreeViewColumn.createTreeViewColumn());
    }

    /**
     * Set the title to be used for this TreeViewColumn in the TreeView's
     * header row. In addition to being descriptive, this title is what you
     * click to cause this column to be the one that is sorting the TreeView
     * [assuming you've enabled the columns to be sortable by calling the
     * enclosing TreeView's {@link TreeView#setHeadersClickable(boolean)
     * setHeadersClickable(true)} and this TreeViewColumn's
     * {@link TreeViewColumn#setSortColumn(DataColumn) setSortColumn()}, and
     * that the titles are visible in the first place, ie the header row
     * hasn't been turned off via TreeView's
     * {@link TreeView#setHeadersVisible(boolean) setHeadersVisible(false)}].
     */
    public void setTitle(String title) {
        GtkTreeViewColumn.setTitle(this, title);
    }

    /**
     * Gets the title of this TreeViewColumn. Please see
     * {@link #setTitle(String) setTitle()} for further information about
     * TreeViewColumn titles.
     * 
     * @since 4.0.8
     */
    public String getTitle() {
        return GtkTreeViewColumn.getTitle(this);
    }

    /**
     * Set whether the column header allows itself to be clicked. The default
     * is <code>false</code>, not to be clickable, also meaning that header
     * won't take the keyboard focus either. Since calling
     * {@link #setSortColumn(DataColumn) setSortColumn()} will set
     * <var>clickable</var> to <code>true</code>, so you only need this for
     * the case where you assign a sort order to a column, but don't want
     * users to be able to change the sorting column or its order.
     */
    public void setClickable(boolean setting) {
        GtkTreeViewColumn.setClickable(this, setting);
    }

    /**
     * Set the DataColumn that will be used to sort this TreeViewColumn should
     * sorting be enabled.
     * 
     * <p>
     * Calling this also makes the TreeViewColumn <var>clickable</var>, but it
     * does not automatically engage the sorting (maybe you only want sorting
     * to be optional and only active if the user clicks a header, certainly
     * if you've set sort information for many columns you will have to
     * indicate explicitly which one is to be active first, etc). Use
     * {@link #emitClicked() emitClicked()} to activate this column as the one
     * doing the sorting.
     * 
     * <p>
     * <b>Warning:</b><br/>
     * This only works if the TreeModel being presented by this TreeView is
     * <var>sortable</var>, that is, if that model implements TreeSortable.
     * Since almost everything in GTK does so, you ordinarily don't need to
     * worry about this. The one that doesn't is TreeModelFilter, so if you're
     * using a filtered model make sure you wrap it in a TreeModelSort before
     * adding it to the TreeView.
     */
    public void setSortColumn(DataColumn column) {
        GtkTreeViewColumn.setSortColumnId(this, column.getOrdinal());
    }

    /**
     * Activate this column's sorting by sending a click the TreeViewColumn
     * header. Causes <code>TreeViewColumn.Clicked</code> to fire, but more
     * importantly this causes this vertical to become the active column
     * dictating the ordering of the TreeView as a whole (assuming the
     * vertical is {@link #setClickable(boolean) setClickable(true)} and
     * assuming a DataColumn has been set with
     * {@link #setSortColumn(DataColumn) setSortColumn()} to indicate the
     * ordering).
     * 
     * @since 4.0.8
     */
    public void emitClicked() {
        GtkTreeViewColumn.clicked(this);
    }

    /** @deprecated */
    public void clicked() {
        assert false : "use emitClicked() instead";
        GtkTreeViewColumn.clicked(this);
    }

    /**
     * Set whether this TreeViewColumn will share in additional space
     * available to the parent TreeView. Like Widgets packed into Containers,
     * ordinarily a TreeViewColumn will be allocated the space it needs but no
     * more, and the TreeView will be the size of the sum of these requests.
     * If the TreeView is allocated space over and above this, however, then
     * this additional space will be given to those TreeViewColumns with this
     * property set to <code>true</code>.
     * 
     * <p>
     * The default for new TreeViewColumns is <code>false</code>. The last
     * (right-most) column is treated specially, however. Extra space given to
     * the TreeView as a whole will automatically go to the last vertical
     * column (ie, ignoring this being set <code>false</code>) unless one or
     * more other columns have had <var>expand</var> set to <code>true</code>.
     */
    public void setExpand(boolean setting) {
        GtkTreeViewColumn.setExpand(this, setting);
    }

    /**
     * Set the horizontal alignment of the title (or custom Widget) within the
     * header of this TreeViewColumn.
     * 
     * @param xalign
     *            As with elsewhere in GTK, a value between <code>0.0f</code>
     *            for left and <code>1.0f</code> for right alignment. The
     *            constants in {@link Alignment} may serve.
     * @since 4.0.6
     */
    public void setAlignment(float xalign) {
        if ((xalign < 0.0) || (xalign > 1.0)) {
            throw new IllegalArgumentException("xalign must be between 0.0 and 1.0");
        }
        GtkTreeViewColumn.setAlignment(this, xalign);
    }

    /**
     * Permit the user to change the order of the columns in a TreeView by
     * clicking on one of the column headers and dragging it to a new
     * position. This can be useful for wide tables where a great deal of
     * information is being presented and the user might want to have a
     * different subset visible.
     * 
     * <p>
     * Most of the time, however, the order of presentation of the columns is
     * an integral part of your UI and you don't want the user messing with
     * it, which is why the default is <code>false</code>. More than that,
     * people don't much care for horizontal scrolling; if you are presenting
     * so much data that reordering the columns would be necessary, perhaps
     * you should rethink the way you are presenting things.
     * 
     * @since 4.0.6
     */
    public void setReorderable(boolean setting) {
        GtkTreeViewColumn.setReorderable(this, setting);
    }

    /**
     * Permit the user to change the width of the columns in a TreeView by
     * clicking on the boundary of a column header and dragging it. Setting
     * this <code>true</code> will implicitly set
     * {@link TreeViewColumnSizing#GROW_ONLY GROW_ONLY}. On the other hand
     * setting {@link TreeViewColumnSizing#AUTOSIZE AUTOSIZE} will implicitly
     * disable resizing.
     * 
     * @since 4.0.6
     */
    public void setResizable(boolean setting) {
        GtkTreeViewColumn.setResizable(this, setting);
    }

    /**
     * Set the mode constant that will be used to determine the width
     * behaviour of TreeViewColumns when data is changed in the model causing
     * a CellRenderer to need to redraw a cell. Note that this works in
     * conjunction with other sizing methods ({@link #setResizable(boolean)
     * setResizable()}, {@link #setMinWidth(int) setMinWidth()},
     * {@link #setFixedWidth(int) setFixedWidth()}, etc and there are quite a
     * number of permutations available as a result.
     * 
     * <p>
     * {@link TreeViewColumnSizing#GROW_ONLY GROW_ONLY} is the default.
     * 
     * @since 4.0.6
     */
    public void setSizing(TreeViewColumnSizing type) {
        GtkTreeViewColumn.setSizing(this, type);
    }

    /**
     * Sets the minimal width this column can shrink to. Works in all
     * {@link TreeViewColumnSizing} variants.
     * 
     * @param width
     *            Column width in pixel
     * 
     * @since 4.0.8
     */
    public void setMinWidth(int width) {
        GtkTreeViewColumn.setMinWidth(this, width);
    }

    /**
     * If (and only if) operating with the {@link TreeViewColumnSizing#FIXED
     * FIXED} TreeViewSizing mode, this defines the fixed column width in
     * pixels. If {@link #setMinWidth(int)} setMinWidth} or
     * {@link #setMaxWidth(int)} have been called and the fixed width is
     * outside these bounds Min- or MaxWidth take precedence.
     * 
     * <p>
     * The fixed width can be overridden by the user if
     * {@link #setResizable(boolean) setResizable()} has been set to
     * <code>true</code>.
     * 
     * @param width
     *            Column width in pixels
     * 
     * @since 4.0.8
     */
    public void setFixedWidth(int width) {
        GtkTreeViewColumn.setFixedWidth(this, width);
    }

    /**
     * Sets the maximum width this column can grow. Works in all
     * {@link TreeViewColumnSizing} variants. If not set, the column growth is
     * not limited.
     * 
     * @param width
     *            Column width in pixels
     * 
     * @since 4.0.8
     */
    public void setMaxWidth(int width) {
        GtkTreeViewColumn.setMaxWidth(this, width);
    }
}
