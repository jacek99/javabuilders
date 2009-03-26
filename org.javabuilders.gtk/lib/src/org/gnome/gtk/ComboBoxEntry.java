/*
 * ComboBoxEntry.java
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

/**
 * An Entry which also offers a range of pre-configured options to choose
 * from. <img src="TextComboBoxEntry.png" class="snapshot" /> It is a
 * composite Widget with an Entry wrapped in a mechanism to activate a popup
 * dialog allowing the user to pick from a list of options.
 * 
 * <p>
 * ComboBoxEntry is powered by the same mechanism as the parent class
 * ComboBox; the option list is driven by a TreeModel. When constructing a
 * ComboBoxEntry, you specify the column from the model that will supply the
 * data to be used as the list of options.
 * 
 * <p>
 * Almost everyone dealing with ComboBoxEntries wonders how to get the text
 * showing in the Widget. You do this by very simply getting by getting it
 * from the Entry. Call {@link Bin#getChild() getChild()} and call that
 * Entry's {@link Entry#getText() getText()}; given:
 * 
 * <pre>
 * ComboBoxEntry combo;
 * Entry e;
 * String str;
 * ...
 * </pre>
 * 
 * you simply write:
 * 
 * <pre>
 * e = (Entry) combo.getChild();
 * str = e.getText();
 * </pre>
 * 
 * and then do something with the returned String.
 * 
 * <p>
 * <i>Like <code>GtkComboBox</code>, <code>GtkComboBoxEntry</code> has a
 * second text-only API that is lumped into the same classes while not being
 * able to be used together. We have similarly spliced this functionality out
 * of this class into our subclass</i> {@link TextComboBoxEntry}.
 * 
 * @since 4.0.6
 * @author Andrew Cowie
 */
public class ComboBoxEntry extends ComboBox implements CellEditable, CellLayout
{
    protected ComboBoxEntry(long pointer) {
        super(pointer);
    }

    /**
     * Construct a new ComboBoxEntry, indicating which DataColumn in the
     * TreeModel is to provide the list of options for the Widget to display.
     * 
     * @since 4.0.6
     */
    public ComboBoxEntry(TreeModel model, DataColumnString column) {
        super(GtkComboBoxEntry.createComboBoxEntryWithModel(model, column.getOrdinal()));
    }
}
