/*
 * SizeGroup.java
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

import org.gnome.glib.Object;

/**
 * Cause a group of Widgets to have the same size request in either the
 * horizontal dimension, the vertical dimension, or both. A SizeGroup is
 * somewhat like a Container, although it is a helper class for Widgets and
 * not itself a Widget. When you create a SizeGroup, you specify the mode
 * indicating the way you want it to act upon the Widgets placed into it, and
 * then add Widgets. All the Widgets "in" the SizeGroup will then all have
 * have their width request in common ({@link SizeGroupMode#HORIZONTAL
 * HORIZONTAL}) or their height request in common (
 * {@link SizeGroupMode#VERTICAL VERTICAL}), or, in somewhat rarer
 * circumstances, have both their width and height requests in common
 * {@link SizeGroupMode#BOTH BOTH}). The size requested will be that of the
 * Widget in the group that is the largest in that dimension.
 * 
 * <p>
 * When doing data entry across a large number of fields, it is a GNOME
 * usability standard that the Entry boxes be common width and aligned
 * vertically. While there are certainly cases where you have a good reason
 * for doing otherwise, if you are filling in a form then using a SizeGroup
 * can help you create a nice uniform appearance.
 * 
 * <p>
 * SizeGroups can be fantastically useful to create the "table" effect but
 * across an uneven series of HBoxes nested in VBoxes with but with other
 * things in between.
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public class SizeGroup extends Object
{
    protected SizeGroup(long pointer) {
        super(pointer);
    }

    /**
     * Instantiate a new SizeGroup, constraining per the <code>mode</code>
     * parameter.
     * 
     * @since 4.0.6
     */
    public SizeGroup(SizeGroupMode mode) {
        super(GtkSizeGroup.createSizeGroup(mode));
    }

    /**
     * Specify a Widget to be constrained as a member of this SizeGroup.
     * 
     * @since 4.0.6
     */
    /*
     * This, and remove(), have been adjusted from exact API mapping to GTK in
     * order to be parallel to the Container API style.
     */
    public void add(Widget widget) {
        GtkSizeGroup.addWidget(this, widget);
    }

    /**
     * Remove a Widget that was previously added to the SizeGroup.
     * 
     * @since 4.0.6
     */
    public void remove(Widget widget) {
        GtkSizeGroup.removeWidget(this, widget);
    }
}
