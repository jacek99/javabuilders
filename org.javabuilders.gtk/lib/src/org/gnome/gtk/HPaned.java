/*
 * HPaned.java
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
 * A {@link Paned Paned} that disposes horizontally the two children, one at
 * the left, the other at the right.
 * 
 * @see Paned
 * @see VPaned
 * @author Vreixo Formoso
 * @since 4.0.7
 */
public class HPaned extends Paned
{
    protected HPaned(long pointer) {
        super(pointer);
    }

    /**
     * Create a new HPaned.
     */
    public HPaned() {
        super(GtkHPaned.createHPaned());
    }

    /**
     * Create a new HPaned and set its children.
     * 
     * <p>
     * Each child will be added with the correspondent <code>add()</code>
     * function.
     */
    public HPaned(Widget child1, Widget child2) {
        super(GtkHPaned.createHPaned());
        add1(child1);
        add2(child2);
    }
}
