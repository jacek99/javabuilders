/*
 * TreeSelection.java
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

import org.gnome.glib.Object;

/**
 * Manipulate the selection state of a TreeView. Every TreeView has an
 * accompanying TreeSelection object which is used to manage whether or not
 * rows can be selected, and to return to the programmer the current state of
 * which rows are selected, if any.
 * 
 * <p>
 * TreeSelection can be used to programmatically select rows by either
 * TreeIter or TreePath; see the <code>selectRow()</code> methods. For
 * example, to select the first row you could do:
 * 
 * <pre>
 * final TreeSelection selection;
 * 
 * selection = view.getSelection();
 * selection.selectRow(new TreePath(&quot;0&quot;));
 * </pre>
 * 
 * <p>
 * Using TreeSelection to determine the currently selected row is fairly
 * straight forward:
 * 
 * <pre>
 * selection.connect(new TreeSelection.Changed() {
 *     public void onChanged(TreeSelection source) {
 *         final TreeIter row;
 * 
 *         row = selection.getSelected();
 * 
 *         if (row != null) {
 *             // do something!
 *         }
 *     }
 * });
 * </pre>
 * 
 * Unfortunately, the <code>TreeSelection.Changed</code> signal is not
 * entirely deterministic; it is sometimes emitted more than once or for no
 * change at all. You'll need to allow for this in your code.
 * 
 * <p>
 * <i>Mostly this is an API helper; the underlying documentation notes that
 * these could have all been methods on <code>GtkTreeView</code>.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
public class TreeSelection extends Object
{
    protected TreeSelection(long pointer) {
        super(pointer);
    }

    /**
     * Get the mode that is governing selection behaviour on this TreeView.
     */
    public SelectionMode getMode() {
        return GtkTreeSelection.getMode(this);
    }

    /**
     * Set what kinds of selections are allowed. The interesting constants
     * you'll use most often are {@link SelectionMode#NONE NONE} and
     * {@link SelectionMode#MULTIPLE MULTIPLE} since
     * {@link SelectionMode#SINGLE SINGLE} is the default. See SelectionMode
     * for the details of the behaviour implied by each option.
     */
    public void setMode(SelectionMode type) {
        GtkTreeSelection.setMode(this, type);
    }

    /**
     * Get the selected row from the TreeView. Note that this only works when
     * the selection mode is {@link SelectionMode#SINGLE SINGLE} or
     * {@link SelectionMode#BROWSE BROWSE}.
     * 
     * @return <code>null</code> if there is no currently selected row.
     */
    /*
     * Second parameter to native call is an out-parameter that gets filled
     * with a pointer to the GtkTreeModel. It's only for convenience, and is
     * unnecessary for us. Looking at the GtkTreeSelection C code it ignores
     * it if NULL. We'll skip it.
     */
    public TreeIter getSelected() {
        final TreeModel model;
        final TreeIter row;

        model = getView().getModel();
        row = new TreeIter(model);

        if (GtkTreeSelection.getSelected(this, null, row)) {
            return row;
        } else {
            return null;
        }
    }

    /**
     * Get the rows currently selected from the TreeView. This is specially
     * useful when the selection mode is {@link SelectionMode#MULTIPLE
     * MULTIPLE}. Otherwise {@link #getSelected() getSelected()} offers a more
     * convenient way to obtain the selected row.
     * 
     * <p>
     * You can use the TreeModel's {@link TreeModel#getIter(TreePath)
     * getIter()} method to convert the returned TreePaths to the more
     * convenient TreeIter:
     * 
     * <pre>
     * TreePath[] rows;
     * TreeSelection selection;
     * TreeModel model;
     * 
     * rows = selection.getSelectedRows();
     * for (int i = 0; i &lt; rows.length; ++i) {
     *     TreeIter row = model.getIter(rows[i]);
     *     // do something with the row
     * }
     * </pre>
     * 
     * <p>
     * Also remember that both TreeIter and TreePath are temporally objects no
     * longer valid once you make any change to the model. Thus, if you plan
     * to modify the model, you may want to convert the returned TreePaths to
     * {@link TreeRowReference TreeRowReferences}.
     * 
     * @return An array with the selected rows. If no row is selected, the
     *         arrays will be empty.
     * @since 4.0.7
     */
    /*
     * Second parameter to native call is an out-parameter that gets filled
     * with a pointer to the GtkTreeModel. It's only for convenience, and is
     * unnecessary for us.
     */
    public TreePath[] getSelectedRows() {
        return GtkTreeSelection.getSelectedRows(this, null);
    }

    TreeView getView() {
        return GtkTreeSelection.getTreeView(this);
    }

    /**
     * Emitted when the selection state of the TreeView changes.
     * 
     * <p>
     * Beware that this is considered a hint by GTK, so you sometimes get
     * false positives or false negatives relative to how you are interpreting
     * "change". You'll be calling {@link TreeSelection#getSelected()
     * getSelected()} anyway, but it's a good idea to keep in mind that the
     * state may not have changed in quite the way you think it might have.
     * Have a look at the return from that method fairly closely to decide for
     * yourself whether the selection has "changed" or not.
     * 
     * <p>
     * <i>The nonsense about the <code>TreeSelection.Changed</code> signal is
     * supposedly due to the fact that there are multiple actors in the
     * TreeModel environment, and both internal actions within GTK and events
     * due to window manager activity can result in the signal being emitted.
     * What a load of crap; either the selection changed or it didn't. Sorry
     * we can't do better for you.</i>
     * 
     * @author Andrew Cowie
     * @since 4.0.5
     */
    public interface Changed extends GtkTreeSelection.ChangedSignal
    {
        void onChanged(TreeSelection source);
    }

    /**
     * Hook up a <code>TreeSelection.Changed</code> signal handler.
     */
    public void connect(TreeSelection.Changed handler) {
        GtkTreeSelection.connect(this, handler, false);
    }

    /** @deprecated */
    public interface CHANGED extends GtkTreeSelection.ChangedSignal
    {
    }

    /** @deprecated */
    public void connect(CHANGED handler) {
        assert false : "use TreeSelection.Changed instead";
        GtkTreeSelection.connect(this, handler, false);
    }

    /**
     * Select a row in the TreeView. We offer two forms; this one which takes
     * a TreePath and one which takes a TreeIter; see
     * {@link #selectRow(TreeIter)} for the other. Use this one if you have
     * want to express expressing the row you want selected in abstract,
     * logical form.
     */
    public void selectRow(TreePath path) {
        GtkTreeSelection.selectPath(this, path);
    }

    /**
     * Select a row in the TreeView. We offer two forms; this one which takes
     * a TreeIter corresponding to a row in the underlying model, and another
     * which takes a TreePath; see {@link #selectRow(TreePath)}.
     */
    public void selectRow(TreeIter row) {
        GtkTreeSelection.selectIter(this, row);
    }

    /**
     * Unselect all the rows in the TreeView. Useful to ensure the initial
     * state of the TreeView is that no rows are selected, however you may
     * find you have to wait until after the Window is mapped.
     */
    public void unselectAll() {
        GtkTreeSelection.unselectAll(this);
    }
}
