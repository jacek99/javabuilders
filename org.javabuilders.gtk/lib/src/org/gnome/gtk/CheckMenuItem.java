/*
 * CheckMenuItem.java
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
 * A MenuItem that maintains a binary state.
 * 
 * <p>
 * A CheckMenuItem is just like a MenuItem, but additionally it displays a
 * "check box" alongside the normal Label, indicating the state of the boolean
 * value it holds. When that value is set to <code>true</code>, the item is
 * <i>active</i> and the box shows the check mark.
 * 
 * <p>
 * You can use a CheckMenuItem as a way to allow users of your application
 * enable or disable a feature in an application. This is often used within to
 * toggle options, for example to let the user hide an optional Widget of your
 * user interface.
 * 
 * <p>
 * The active state is switched automatically when the user activates the
 * MenuItem. You can access the current state with the {@link #getActive()
 * getActive()} method. And while you can still connect to the
 * <code>MenuItem.Active</code> signal, CheckMenuItem provides the
 * {@link CheckMenuItem.Toggled} signal, emitted when the active state
 * changes.
 * 
 * <p>
 * See the {@link MenuItem parent} class for further details general to all
 * MenuItems.
 * 
 * @author Vreixo Formoso
 * @since 4.0.4
 */
/*
 * TODO need screenshot.
 */
public class CheckMenuItem extends MenuItem
{
    protected CheckMenuItem(long pointer) {
        super(pointer);
    }

    /**
     * Construct a CheckMenuItem
     * 
     * @since 4.0.4
     */
    public CheckMenuItem() {
        super(GtkCheckMenuItem.createCheckMenuItem());
    }

    /**
     * Construct a CheckMenuItem with a given text Label. The text may contain
     * underscores (<code>_<code>) which, if present, will indicate the
     * mnemonic which will activate that CheckMenuItem directly if that key is
     * pressed while viewing the Menu.
     * 
     * @since 4.0.4
     */
    public CheckMenuItem(String mnemonicLabel) {
        super(GtkCheckMenuItem.createCheckMenuItemWithMnemonic(mnemonicLabel));
    }

    /**
     * Construct a CheckMenuItem with a given text label, and additionally
     * connect a handler to its <code>CheckMenuItem.Toggled</code> signal.
     * This affords you the convenience of being able to add a MenuItem fairly
     * compactly:
     * 
     * <pre>
     * editMenu.append(new MenuItem(&quot;_Paste&quot;, new CheckMenuItem.Toggled() {
     *     public void onToggled(MenuItem source) {
     *         ...
     *     }
     * }));
     * </pre>
     * 
     * @since 4.0.4
     */
    public CheckMenuItem(String mnemonicLabel, CheckMenuItem.Toggled handler) {
        super(GtkCheckMenuItem.createCheckMenuItemWithMnemonic(mnemonicLabel));
        connect(handler);
    }

    /** @deprecated */
    public CheckMenuItem(String mnemonicLabel, TOGGLED handler) {
        super(GtkCheckMenuItem.createCheckMenuItemWithMnemonic(mnemonicLabel));
        connect(handler);
    }

    /**
     * Set the active state of the Item. This is switched automatically when
     * the user activates (clicks) the menu item, but in some situations you
     * will want to change it manually.
     * 
     * @since 4.0.4
     */
    public void setActive(boolean isActive) {
        GtkCheckMenuItem.setActive(this, isActive);
    }

    /**
     * Retrieve the active state of the item.
     * 
     * @since 4.0.4
     */
    public boolean getActive() {
        return GtkCheckMenuItem.getActive(this);
    }

    /**
     * Set the <var>inconsistent</var> state. This refers to an additional
     * third state meaning that currently it cannot be decided what is the
     * active state of the item.
     * 
     * <p>
     * Think, for example, in a text editor application, in which a
     * CheckMenuItem is used to choose between a bold or a normal font. If the
     * user selects a range of text where both normal and bold fonts are being
     * used, the state is inconsistent, and we want to mark it in a different
     * way.
     * 
     * <p>
     * However, note that, while such property can be really useful in a
     * {@link ToggleButton}, its utility in a CheckMenuItem is really unclear.
     * 
     * <p>
     * Notice also that this property only affects visual appearance, it
     * doesn't affect the semantics of the Widget.
     * 
     * @since 4.0.4
     */
    public void setInconsistent(boolean setting) {
        GtkCheckMenuItem.setInconsistent(this, setting);
    }

    /**
     * Get the <var>inconsistent</var> state.
     * 
     * @see #setInconsistent(boolean)
     * @since 4.0.4
     */
    public boolean getInconsistent() {
        return GtkCheckMenuItem.getInconsistent(this);
    }

    /**
     * The handler interface for a change in the active state. This is
     * triggered when the active state changes, either when the user activates
     * the MenuItem, or when it is changed with
     * {@link CheckMenuItem#setActive(boolean) setActive()}.
     * 
     * @see MenuItem.Activate
     * @since 4.0.4
     */
    public interface Toggled extends GtkCheckMenuItem.ToggledSignal
    {
        void onToggled(CheckMenuItem source);
    }

    /**
     * Connect a <code>CheckMenuItem.Toggled</code> handler to the Widget.
     * 
     * @since 4.0.4
     */
    public void connect(CheckMenuItem.Toggled handler) {
        GtkCheckMenuItem.connect(this, handler, false);
    }

    /** @deprecated */
    public interface TOGGLED extends GtkCheckMenuItem.ToggledSignal
    {
    }

    /** @deprecated */
    public void connect(TOGGLED handler) {
        assert false : "use CheckMenuItem.Toggled instead";
        GtkCheckMenuItem.connect(this, handler, false);
    }
}
