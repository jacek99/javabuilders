/*
 * MenuBar.java
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
 * A MenuBar is a Container widget for {@link Menu}s.
 * 
 * <p>
 * A MenuBar is added to a Container {@link Window} just like any other
 * Widget. The MenuBar holds only Menu subitems. It is generally a convention
 * to have a MenuBar at the top of the Window. Hence it must be the first
 * Widget you should add to the top of the VBox in a Window.
 * </p>
 * 
 * <p>
 * For a broader explanation of how to use the Menus APIs, see
 * {@link MenuShell}.
 * </p>
 * 
 * @author Sebastian Mancke
 * @author Andrew Cowie
 * @author Srichand Pendyala
 * @since 4.0.3
 */
public class MenuBar extends MenuShell
{
    protected MenuBar(long pointer) {
        super(pointer);
    }

    /**
     * Constructs a MenuBar
     */
    public MenuBar() {
        super(GtkMenuBar.createMenuBar());
    }
}
