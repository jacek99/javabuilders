/*
 * ToggleButton.java
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
 * A ToggleButton is a Button which retains its state.
 * 
 * @author Sebastian Mancke
 * @author Andrew Cowie
 * @since 4.0.3
 */
public class ToggleButton extends Button
{
    protected ToggleButton(long pointer) {
        super(pointer);
    }

    /**
     * Constructs a new ToggleButton
     * 
     * @since 4.0.3
     */
    public ToggleButton() {
        super(GtkToggleButton.createToggleButton());
    }

    /**
     * Constructs a new ToggleButton with a label.
     * 
     * <p>
     * The label may contain underscores (<code>_<code>) to indicate
     * the mnemonic for the Button.
     * 
     * @since 4.0.3
     */
    public ToggleButton(String label) {
        super(GtkToggleButton.createToggleButtonWithMnemonic(label));
    }

    /**
     * Set the state of the ToggleButon.
     * 
     * @param active
     *            The new state of the ToggleButton
     * @since 4.0.3
     */
    public void setActive(boolean active) {
        GtkToggleButton.setActive(this, active);
    }

    /**
     * Returns the current state of the ToggleButton
     * 
     * @return returns <code>true</code> if the ToggleButton is pressed,
     *         <code>false</code> otherwise.
     * @since 4.0.3
     */
    public boolean getActive() {
        return GtkToggleButton.getActive(this);
    }

    /**
     * Signal indicating the Button has changed state.
     * 
     * @since 4.0.3
     */
    public interface Toggled extends GtkToggleButton.ToggledSignal
    {
        public void onToggled(ToggleButton source);
    }

    /**
     * Hook up a <code>ToggleButton.Toggled</code> signal handler.
     * 
     * @since 4.0.3
     */
    public void connect(ToggleButton.Toggled handler) {
        GtkToggleButton.connect(this, handler, false);
    }

    /** @deprecated */
    public interface TOGGLED extends GtkToggleButton.ToggledSignal
    {
    }

    /** @deprecated */
    public void connect(TOGGLED handler) {
        assert false : "use ToggleButton.Toggled instead";
        GtkToggleButton.connect(this, handler, false);
    }
}
