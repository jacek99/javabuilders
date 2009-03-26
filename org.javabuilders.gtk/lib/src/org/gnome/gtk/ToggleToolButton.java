/*
 * ToggleToolButton.java
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
 * A ToggleToolButton is a ToolButton that retains its state.
 * 
 * <p>
 * The usual usage of such kind of ToolButtons is to let users enable or
 * disable an application feature.
 * 
 * <p>
 * You can check the current state of the ToggleToolButton using the
 * {@link #getActive()} method. Additionally you can connect to the
 * {@link ToggleToolButton.TOGGLED TOGGLED} signal to respond to changes in
 * the <var>active</var> state.
 * 
 * <p>
 * Beware that ToogleToolButtons have sometimes fared poorly in usability
 * tests. The problem is that they are an exception: most ToolItems are
 * ToolButtons, and most of them you just press and something immediately
 * happens. Since there is no visual distinction between a ToolButton and an
 * inactive ToggleToolButton, users don't expect that this particular ToolItem
 * is going to behave differently and maintain its state. It's not a huge
 * problem (ie, if the user is paying attention they'll soon find out that the
 * button is a toggle), but it's something that usually takes a few iterations
 * before the user learns. Something to keep in mind.
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.4
 */
public class ToggleToolButton extends ToolButton
{
    protected ToggleToolButton(long pointer) {
        super(pointer);
    }

    /**
     * Create a new ToggleToolButton.
     * 
     * @since 4.0.4
     */
    public ToggleToolButton() {
        super(GtkToggleToolButton.createToggleToolButton());
    }

    /**
     * Create a new ToggleToolButton based on one of the stock items.
     * 
     * @since 4.0.4
     */
    public ToggleToolButton(Stock stock) {
        super(GtkToggleToolButton.createToggleToolButtonFromStock(stock.getStockId()));
    }

    /**
     * Set whether this ToggleToolButton is active, that is, whether it
     * appears pressed or not. Calling this will emit a <code>TOGGLED</code>
     * signal.
     * 
     * @since 4.0.4
     */
    public void setActive(boolean isActive) {
        GtkToggleToolButton.setActive(this, isActive);
    }

    /**
     * Is the ToggleToolButton currently activated on?
     * 
     * @since 4.0.4
     */
    public boolean getActive() {
        return GtkToggleToolButton.getActive(this);
    }

    /**
     * Signal indicating the ToggleToolButton state has changed.
     * 
     * @since 4.0.4
     */
    public interface Toggled extends GtkToggleToolButton.ToggledSignal
    {
        public void onToggled(ToggleToolButton source);
    }

    /**
     * Connect a handler to the <code>ToggleToolButton.Toggled</code> signal.
     * 
     * @since 4.0.4
     */
    public void connect(ToggleToolButton.Toggled handler) {
        GtkToggleToolButton.connect(this, handler, false);
    }

    /** @deprecated */
    public interface TOGGLED extends GtkToggleToolButton.ToggledSignal
    {
    }

    /** @deprecated */
    public void connect(TOGGLED handler) {
        assert false : "use ToggleToolButton.Toggled instead";
        GtkToggleToolButton.connect(this, handler, false);
    }
}
