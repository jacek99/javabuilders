/*
 * CheckButton.java
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
 * A toggle button widget that consists of a Check box and a Text box.
 * 
 * <p>
 * CheckButtons are similar to ToggleButtons, but look very different.
 * Contrary to ToggleButtons, which are Buttons with text inside them, they
 * are small check squares, with descriptive text beside them. As with
 * ToggleButtons, they can be used to toggle options on or off.
 * 
 * <p>
 * CheckButtons derive from ToggleButtons and hence behave somewhat similarly.
 * See in particular the {@link ToggleButton.TOGGLED TOGGLED} Signal inherited
 * from ToggleButton to detect changes in state to the CheckButton.
 * 
 * <p>
 * The choice between a CheckButton and a {@link ToggleButton} is left to the
 * developer's discretion. A CheckButton can take considerably lesser space
 * than a ToggleButton. A CheckButton is more useful when you have a list of
 * related options that need to be set or unset. A ToggleButton could be used
 * when a single option needs to be toggled.
 * 
 * @author Sebastian Mancke
 * @author Andrew Cowie
 * @author Srichand Pendyala
 * @since 4.0.3
 */
public class CheckButton extends ToggleButton
{
    protected CheckButton(long pointer) {
        super(pointer);
    }

    /**
     * Construct a new CheckButton.
     */
    public CheckButton() {
        super(GtkCheckButton.createCheckButton());
    }

    /**
     * Construct a new CheckButton containing a label with the given text. If
     * the text contains an underscore (the
     * <code>_<code> character) it will be taken to be the
     * mnemonic for the Widget.
     */
    public CheckButton(String label) {
        super(GtkCheckButton.createCheckButtonWithMnemonic(label));
    }
}
