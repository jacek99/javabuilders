/*
 * SeparatorMenuItem.java
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
 * A separator between groups of related MenuItems.
 * 
 * <p>
 * A SeparatorMenuItem is a special type of MenuItem that is shown to the user
 * as a horizontal line. Its unique usage is to visually group together
 * related MenuItem's within a Menu.
 * 
 * <p>
 * These are added to a Menu in the same way as any other MenuItem. You
 * usually will want to add it between two different sets of related
 * MenuItems; it's considered bad form to led or end a Menu with a separator.
 * 
 * @author Vreixo Formoso
 * @since 4.0.4
 */
public class SeparatorMenuItem extends MenuItem
{
    protected SeparatorMenuItem(long pointer) {
        super(pointer);
    }

    /**
     * Create a new SeparatorMenuItem.
     */
    public SeparatorMenuItem() {
        super(GtkSeparatorMenuItem.createSeparatorMenuItem());
    }
}
