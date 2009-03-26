/*
 * Entry.java
 *
 * Copyright (c) 2006-2007 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gnome;

import org.gnome.gtk.Widget;

/**
 * This is the beginnings of showing what a class in another package would
 * look like. Not implemented! This widget is deprecated and it should not be
 * used.
 * 
 * @author Andrew Cowie
 * @author Shreyas Srinivasan
 * @deprecated
 */
public class Entry extends Widget
{
    protected Entry(long pointer) {
        super(pointer);
    }

    public String getText() {
        // return GnomeEntry.getText(this);
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
