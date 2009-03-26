/*
 * HBox.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * A Container which holds a variable number of Widgets in a single horizontal
 * row. All the children of this HBox are allocated the same height - that of
 * the tallest Widget packed into the HBox.
 * 
 * <p>
 * All the methods you need to add Widgets to HBoxes are on the parent class;
 * to get started, see Box's {@link Box#packStart(Widget) packStart()}, no pun
 * intended.
 * 
 * @author Andrew Cowie
 * @see VBox
 * @since 4.0.1
 */
public class HBox extends Box
{
    protected HBox(long pointer) {
        super(pointer);
    }

    /**
     * Creates a new HBox.
     * 
     * @param homogeneous
     *            If <code>true</code>, all children will be given equal space
     *            allotments.
     * @param spacing
     *            the number of pixels to place (by default) between children.
     */
    public HBox(boolean homogeneous, int spacing) {
        super(GtkHBox.createHBox(homogeneous, spacing));
    }
}
