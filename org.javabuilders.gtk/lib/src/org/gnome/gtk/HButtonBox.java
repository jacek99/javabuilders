/*
 * HButtonBox.java
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
 * Lay Buttons out horizontally with consistent spacing properties. This is
 * one of the principal mechanisms used to create (and indeed enforce) the
 * common look and feel between to Dialogs and other data entry Windows across
 * the GNOME desktop.
 * 
 * <p>
 * See {@link ButtonBox} for the methods giving you control over presentation.
 * 
 * @author Nat Pryce
 * @author Andrew Cowie
 * @since 4.0.4
 */
public class HButtonBox extends ButtonBox
{
    public HButtonBox() {
        this(GtkHButtonBox.createHButtonBox());
    }

    protected HButtonBox(long pointer) {
        super(pointer);
    }
}
