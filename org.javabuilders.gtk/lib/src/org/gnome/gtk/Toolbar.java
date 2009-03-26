/*
 * Toolbar.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2007 Vreixo Formoso
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * A Toolbar is a broad horizontal bar with several controls (usually largish
 * graphical buttons) intended to provide a fast and convenient access to
 * operations commonly used in an application.
 * 
 * <p>
 * In most cases, you will want to add some {@link ToolButton}s to the
 * Toolbar, but you can also add other elements as well by creating a
 * {@link ToolItem} and adding your own customized elements to it.
 * 
 * <p>
 * You can also group related items together by using a
 * {@link SeparatorToolItem} to the Toolbar to create a separation between
 * them. Don't overdo that, however - too many separators result in a
 * cluttered appearance.
 * 
 * <p>
 * Note that the actual on screen appearance of the Toolbar is governed by the
 * user's theme and how they have configured Toolbars to appear. The choices
 * of "<var>Text below Icons</var>" (the usual default), "<var>Text beside
 * Icons</var>", "<var>Icons only</var>", and "<var>Text only</var>" are
 * available from the GNOME panel menu at <b>System <code>&gt;</code> Menus
 * &amp; Toolbars</b> which runs the <code>gnome-ui-properties</code> program.
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.4
 */
public class Toolbar extends Container
{
    protected Toolbar(long pointer) {
        super(pointer);
    }

    /**
     * Create a new, empty, Toolbar.
     */
    public Toolbar() {
        super(GtkToolbar.createToolbar());
    }

    public void add(Widget w) {
        if (!(w instanceof ToolItem)) {
            throw new IllegalArgumentException("You can only add ToolItems to a Toolbar");
        }
        GtkToolbar.insert(this, (ToolItem) w, -1);
    }

    /**
     * Insert a ToolItem in the Toolbar at a given position.
     * 
     * @param item
     *            The new item to add to the Toolbar.
     * @param pos
     *            The position where the new item will be inserted. You can
     *            use <code>0</code> to prepend the item at the beginning of
     *            the Toolbar, or a negative value to append the item at the
     *            end.
     */
    public void insert(ToolItem item, int pos) {
        GtkToolbar.insert(this, item, pos);
    }

    /**
     * Sets the orientation of the Toolbar on screen.
     * 
     * <p>
     * Horizontal Toolbars are commonly used. Usually you shouldn't use a
     * vertical Toolbar it is more difficult to search for the user to find a
     * specific control. When your application has several Toolbars, however,
     * a vertical orientation can become useful as a technique to make a
     * better usage of the available screen real estate.
     */
    public void setOrientation(Orientation orientation) {
        GtkToolbar.setOrientation(this, orientation);
    }

    /**
     * @deprecated This is now a toolkit wide policy setting, and no longer
     *             individually controllable via this method. We've made this
     *             a no-op.
     */
    /*
     * As of GTK 2.14, gtk_toolbar_set_tooltips() is deprecated, and replaced
     * by "gtk-enable-tooltips" via GtkSettings. We don't need to expose this
     * here anymore.
     */
    public void setTooltips(boolean enable) {}
}
