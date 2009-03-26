/*
 * CellRendererToggle.java
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
 * Renders a Toggle Button into a TreeViewColumn. This CellRenderer may be
 * used to present boolean data to the user. The fundamental mapping method is
 * {@link #setActive(DataColumnBoolean) setActive()} which you use to indicate
 * the particular DataColumnBoolean from the underlying TreeModel which will
 * provide the boolean values.
 * 
 * @author Andreas Kuehntopf
 * @since 4.0.8
 */
public class CellRendererToggle extends CellRenderer
{
    /**
     * Construct a new CellRendererToggle. Specify the TreeViewColumn it will
     * belong to.
     * 
     * @since 4.0.8
     */
    public CellRendererToggle(TreeViewColumn vertical) {
        // FIXME does expand to false actually make sense?
        super(GtkCellRendererToggle.createCellRendererToggle(), vertical, false);
    }

    /**
     * Indicate the appearance of this CellRenderer. If this is set to
     * <code>true</code> a radio button is used instead of the default check
     * box.
     * 
     * @since 4.0.8
     */
    public void setRadio(boolean setting) {
        GtkCellRendererToggle.setRadio(this, setting);
    }

    /**
     * Indicate if the CellRendererToggle's toggle button should be active.
     * 
     * <p>
     * If you want to bind the state of the toggle button to a DataColumn you
     * might want to use the overloaded method
     * {@link #setActive(DataColumnBoolean) setActive()}.
     * 
     * @since 4.0.8
     */
    public void setActive(boolean setting) {
        GtkCellRendererToggle.setActive(this, setting);
    }

    /**
     * Get the current state of the toggle button.
     * 
     * @since 4.0.8
     */
    public boolean getActive() {
        return GtkCellRendererToggle.getActive(this);
    }

    /**
     * Indicate a DataColumnBoolean you want the state of the
     * CellRendererToggle to be bound to. This is the method you should use if
     * you want make the toggle's state dynamic based on data in the TreeModel
     * underlying the TreeView you are presenting in.
     * 
     * @since 4.0.8
     */
    public void setActive(DataColumnBoolean column) {
        GtkCellLayout.addAttribute(vertical, this, "active", column.getOrdinal());
    }

    /**
     * Indicate if the CellRendererToggle's toggle button is actually
     * activatable, which means that clicking the toggle button causes a
     * change in the CellRendererToggle's state. Please note that toggling the
     * toggle button does not cause a change in the underlying model.
     * 
     * <p>
     * A CellRendererToggle is activatable by default.
     * 
     * @since 4.0.8
     */
    public void setActivatable(boolean setting) {
        setPropertyBoolean("activatable", setting);
    }

    /**
     * Signal emitted after user toggles the rendered toggle button in a cell.
     * 
     * <p>
     * Note that the act of toggling the cell does not cause a change in the
     * underlying model.
     * 
     * @author Andreas Kuehntopf
     * @since 4.0.8
     */
    public interface Toggled
    {
        public void onToggled(CellRendererToggle source, TreePath path);
    }

    /**
     * Hook up a signal handler to receive "toggled" events on this
     * CellRenderer. A typical example of how this is used is as follows:
     * 
     * <pre>
     * final DataColumnBoolean column;
     * final TreeView view;
     * final ListStore store;
     * final TreeViewColumn vertical;
     * 
     * store = new ListStore(new DataColumn[] {
     *     column = new DataColumnBoolean()
     * });
     * 
     * view = new TreeView(store);
     * vertical = view.appendColumn();
     * CellRendererToggle renderer = new CellRendererToggle(vertical);
     * renderer.setActive(column);
     * renderer.connect(new TOGGLED() {
     *     public void onToggled(CellRendererToggle renderer, TreePath path) {
     *         System.out.println(&quot;Path &quot; + path + &quot; toggled to &quot; + renderer.getActive());
     *     }
     * });
     * </pre>
     * 
     * @since 4.0.8
     */
    public void connect(CellRendererToggle.Toggled handler) {
        GtkCellRendererToggle.connect(this, new ToggledHandler(handler), false);
    }

    /*
     * Helper class to convert second parameter from String to TreePath
     */
    private static class ToggledHandler implements GtkCellRendererToggle.ToggledSignal
    {
        private CellRendererToggle.Toggled handler;

        private ToggledHandler(CellRendererToggle.Toggled handler) {
            this.handler = handler;
        }

        public void onToggled(CellRendererToggle source, String path) {
            handler.onToggled(source, new TreePath(path));
        }
    }
}
