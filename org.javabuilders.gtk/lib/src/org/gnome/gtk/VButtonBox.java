/*
 * VButtonBox.java
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
 * Lay Buttons out horizontally with consistent spacing properties. See
 * {@link ButtonBox}.
 * 
 * @author Nat Pryce
 * @since 4.0.4
 */
public class VButtonBox extends ButtonBox
{
    public VButtonBox() {
        this(GtkVButtonBox.createVButtonBox());
    }

    protected VButtonBox(long pointer) {
        super(pointer);
    }
}
