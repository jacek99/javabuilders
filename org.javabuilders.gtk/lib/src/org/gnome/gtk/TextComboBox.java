/*
 * TextComboBox.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" See the LICENCE file for the terms governing usage and
 * redistribution.
 */
package org.gnome.gtk;

/**
 * A ComboBox for displaying simple Strings. <img src="TextComboBox.png"
 * class="snapshot" /> A common use case for ComboBoxes is to just choose from
 * a list of textual options. This subclass of ComboBox takes care of the
 * mechanics of setting up a the ListStore and the appropriate CellRenderers
 * so as to allow a straight forward interface with a limited number of
 * convenience methods for adding Strings.
 * 
 * <p>
 * Usage is very straight forward:
 * 
 * <pre>
 * combo = new TextComboBox();
 * combo.appendText(&quot;SYD&quot;);
 * combo.appendText(&quot;YYZ&quot;);
 * combo.appendText(&quot;JFK&quot;);
 * combo.appendText(&quot;LHR&quot;);
 * ...
 * </pre>
 * 
 * You can still use the {@link ComboBox#getActive() getActive()} from
 * ComboBox, but for simple lists of Strings being displayed be this Widget,
 * the String specific {@link #getActiveText() getActiveText()} that is
 * introduced here is probably what you want.
 * 
 * <p>
 * <i>These text-only methods are actually found in GTK on
 * <code>GtkComboBox</code>, but using them collides with the normal ComboBox
 * methods. We have therefore moved them to a custom subclass to make the API
 * safe.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public class TextComboBox extends ComboBox
{
    /**
     * Construct a new text-only ComboBox.
     * 
     * @since 4.0.6
     */
    public TextComboBox() {
        super(GtkComboBox.createComboBoxText());
    }

    /**
     * Unsupported.
     * 
     * <p>
     * <i>If you're using a text mode ComboBox, GTK internally allocates a
     * ListStore of a certain structure and the methods are designed to work
     * against it. Things will blow up if you swap it for something different,
     * unless it had exactly the correct signature. As this signature is not
     * something that is part of the GTK API, it is not something we're going
     * to expose; this override is here to prevent you from shooting yourself
     * in the foot.</i>
     * 
     * @throws UnsupportedOperationException
     *             if called.
     */
    /*
     * Is there a better way of managing the completion visibility and it
     * appearing in JavaDoc? Using the deprecated tag works for the latter,
     * but not the former. Hm.
     */
    public void setModel(TreeModel model) {
        throw new UnsupportedOperationException(
                "Sorry, but you can't change the model being used to back a TextComboBox.");
    }

    /**
     * Append an item to the list.
     * 
     * @since 4.0.6
     */
    public void appendText(String text) {
        GtkComboBox.appendText(this, text);
    }

    /**
     * Append an item at the supplied position. Positions are zero origin.
     * 
     * @since 4.0.6
     */
    public void insertText(int position, String text) {
        GtkComboBox.insertText(this, position, text);
    }

    /**
     * Prepend an item to the beginning of the list.
     * 
     * @since 4.0.6
     */
    public void prependText(String text) {
        GtkComboBox.prependText(this, text);
    }

    /**
     * Returns the text of the active item.
     * 
     * @since 4.0.6
     */
    public String getActiveText() {
        return GtkComboBox.getActiveText(this);
    }
}
