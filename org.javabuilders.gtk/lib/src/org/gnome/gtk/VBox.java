/*
 * VBox.java
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
 * A Container which holds a variable number of Widgets in a single vertical
 * row. All the children of this VBox are allocated the same width - that of
 * the widest Widget packed into the VBox.
 * 
 * <p>
 * A VBox is almost always the first thing (and only thing, of course) added
 * to a new Window; doing so gives you a natural mechanism to layout the
 * contents of a Window in logical sequence from Menu through to Buttons and
 * Statusbar.
 * 
 * <p>
 * All the methods you need add widgets to VBoxes and to manipulate their
 * characteristics are on parent class Box. See its
 * {@link Box#packStart(Widget) packStart()} as a good first step.
 * 
 * @author Andrew Cowie
 * @see HBox
 * @since 4.0.1
 */
public class VBox extends Box
{
    protected VBox(long pointer) {
        super(pointer);
    }

    /**
     * Creates a new VBox.
     * 
     * @param homogeneous
     *            If <code>true</code>, all children will be given equal space
     *            allotments.
     * @param spacing
     *            the number of pixels to place (by default) between children.
     */
    public VBox(boolean homogeneous, int spacing) {
        super(GtkVBox.createVBox(homogeneous, spacing));
    }
}
