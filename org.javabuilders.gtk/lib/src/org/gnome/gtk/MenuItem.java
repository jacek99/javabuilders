/*
 * MenuItem.java
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
 * MenuItems are the basic elements that form a Menu.
 * 
 * While MenuItems are Containers and are thus capable of containing other
 * Widgets; in practise only the specialized MenuItem classes will work
 * properly as they are what support highlighting, alignment, submenus, etc.
 * 
 * <p>
 * MenuItems can be either left justified or right justified, but in general
 * you should just leave this alone and it will do the right thing for your
 * locale (left aligned for western languages, right aligned for right-to-left
 * languages like Arabic).
 * 
 * <p>
 * <i>Right justification was initially designed to be used for "Help" Menus,
 * but this is now considered a bad idea and is no longer used in GNOME.</i>
 * 
 * @author Sebastian Mancke
 * @author Andrew Cowie
 * @author Srichand Pendyala
 * @author Vreixo Formoso
 * @since 4.0.3
 */
public class MenuItem extends Item
{
    protected MenuItem(long pointer) {
        super(pointer);
    }

    /**
     * Construct a MenuItem
     * 
     * @since 4.0.3
     */
    public MenuItem() {
        super(GtkMenuItem.createMenuItem());
    }

    /**
     * Construct a MenuItem with a given text label. The label may contain
     * underscores (the <code>_</code> character) which, if present, will
     * indicate the mnemonic which will activate that MenuItem directly if
     * that key is pressed (whilst the Menu is showing, obviously).
     * 
     * @since 4.0.3
     */
    public MenuItem(String mnemonicLabel) {
        super(GtkMenuItem.createMenuItemWithMnemonic(mnemonicLabel));
    }

    /**
     * Construct a MenuItem with a given text label, and additionally connect
     * a handler to its <code>MenuItem.Activate</code> signal.
     * 
     * <p>
     * This is equivalent to:
     * 
     * <pre>
     * item = new MenuItem(&quot;_My menu item&quot;);
     * item.connect(handler);
     * </pre>
     * 
     * and affords you the convenience of being able to add a MenuItem fairly
     * compactly:
     * 
     * <pre>
     * Menu editMenu;
     *    
     * editMenu.append(new MenuItem(&quot;_Paste&quot;, new MenuItem.Activate() {
     *     public void onActivate(MenuItem source) {
     *         ...
     *     }
     * }));
     * </pre>
     * 
     * @since 4.0.4
     */
    public MenuItem(String mnemonicLabel, MenuItem.Activate handler) {
        super(GtkMenuItem.createMenuItemWithMnemonic(mnemonicLabel));
        connect(handler);
    }

    /**
     * Sets or replaces the MenuItem's submenu, or removes it if a
     * <code>null</code> Menu is passed.
     * 
     * @since 4.0.3
     */
    public void setSubmenu(Menu child) {
        GtkMenuItem.setSubmenu(this, child);
    }

    /**
     * Returns the submenu underneath this menu item, if any
     * 
     * @return submenu for this menu item, or <code>null</code> if none.
     * @since 4.0.3
     */
    public Widget getSubmenu() {
        return GtkMenuItem.getSubmenu(this);
    }

    /**
     * The handler interface for an activation. An activation is triggered
     * either when the user clicks the MenuItem, or activates it with the
     * keyboard either by typing that MenuItem's mnemonic character (if it has
     * one) or selecting the MenuItem via the arrow keys and then pressing <b>
     * <code>Enter</code></b>.
     * 
     * @since 4.0.3
     */
    public interface Activate extends GtkMenuItem.ActivateSignal
    {
        public void onActivate(MenuItem source);
    }

    /**
     * Connect an <code>MenuItem.Activate</code> handler to the widget.
     * 
     * @since 4.0.3
     */
    public void connect(MenuItem.Activate handler) {
        GtkMenuItem.connect(this, handler, false);
    }

    /** @deprecated */
    public interface ACTIVATE extends GtkMenuItem.ActivateSignal
    {
    }

    /** @deprecated */
    public void connect(ACTIVATE handler) {
        assert false : "use MenuItem.Activate instead";
        GtkMenuItem.connect(this, handler, false);
    }

    /** @deprecated */
    public MenuItem(String mnemonicLabel, ACTIVATE handler) {
        super(GtkMenuItem.createMenuItemWithMnemonic(mnemonicLabel));
        connect(handler);
    }
}
