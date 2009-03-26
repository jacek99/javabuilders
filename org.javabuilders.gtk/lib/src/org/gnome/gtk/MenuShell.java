/*
 * MenuShell.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * The MenuShell is the abstract super class of {@link Menu} and
 * {@link MenuBar}. It provides the common methods for adding and organizing
 * {@link MenuItem}s.
 * <p>
 * The following example creates one simple menu with a submenu and adds it to
 * a menu bar:
 * 
 * <pre>
 * Menu subMenu = new Menu();
 * subMenu.append(new MenuItem(&quot;Sub Item _1&quot;));
 * subMenu.append(new MenuItem(&quot;Sub Item _2&quot;));
 * MenuItem subMenuItem = new MenuItem(&quot;Sub menu ..&quot;);
 * subMenuItem.setSubmenu(subMenu);
 * 
 * Menu aMenu = new Menu();
 * aMenu.append(new MenuItem(&quot;Item _1&quot;));
 * aMenu.append(new MenuItem(&quot;Item _2&quot;));
 * aMenu.append(subMenuItem);
 * MenuItem aMenuItem = new MenuItem(&quot;_Other menu ..&quot;);
 * aMenuItem.setSubmenu(aMenu);
 * 
 * MenuBar menuBar = new MenuBar();
 * menuBar.append(aMenuItem);
 * // finally add menuBar to the Window's VBox
 * </pre>
 * 
 * @author Sebastian Mancke
 * @author Andrew Cowie
 * @since 4.0.3
 */
public abstract class MenuShell extends Container
{
    protected MenuShell(long pointer) {
        super(pointer);
    }

    /**
     * Append one Widget to the MenuShell.
     */
    public void append(Widget child) {
        GtkMenuShell.append(this, child);
    }

    /**
     * Prepend one Widget to the MenuShell
     */
    public void prepend(Widget child) {
        GtkMenuShell.prepend(this, child);
    }

    /**
     * Insert one Widget to the MenuShell at the specified position.
     */
    public void insert(Widget child, int position) {
        GtkMenuShell.insert(this, child, position);
    }

    /**
     * Deactivate the MenuShell. <i>According to the GTK API documentation,
     * this "typically" results in the Menu being erased from the screen. TODO
     * what other effect could it have?</i>
     */
    public void deactivate() {
        GtkMenuShell.deactivate(this);
    }
}
