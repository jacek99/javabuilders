/*
 * TextChildAnchor.java
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
 * The location of a Widget placed in a TextView so that it appears at a given
 * position in the text.
 * 
 * @since 4.0.9
 */
class TextChildAnchor extends Object
{
    protected TextChildAnchor(long pointer) {
        super(pointer);
    }
}
