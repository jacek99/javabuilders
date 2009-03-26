/*
 * VPaned.java
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
 * A {@link Paned Paned} where the two children are disposed vertically, i.e.
 * one at the top of the other.
 * 
 * @see Paned
 * @see HPaned
 * @author Vreixo Formoso
 * @since 4.0.7
 */
public class VPaned extends Paned
{
    protected VPaned(long pointer) {
        super(pointer);
    }

    /**
     * Create a new VPaned.
     */
    public VPaned() {
        super(GtkVPaned.createVPaned());
    }

    /**
     * Create a new VPaned and set its children.
     * 
     * <p>
     * Each child will be added with the correspondent <code>add()</code>
     * function.
     */
    public VPaned(Widget child1, Widget child2) {
        super(GtkVPaned.createVPaned());
        add1(child1);
        add2(child2);
    }
}
