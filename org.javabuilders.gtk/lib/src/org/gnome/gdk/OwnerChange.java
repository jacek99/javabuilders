/*
 * OwnerChange.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

import org.freedesktop.bindings.Constant;

/**
 * Constants describing the reason a selection has changed ownership.
 * 
 * <p>
 * This is, essentially, internal to GTK; using our binding of Clipboard
 * you'll get a {@link #NEW_OWNER NEW_OWNER} event every time Clipboard's
 * {@link org.gnome.gtk.Clipboard#setText(String) setText()} is called.
 * 
 * <p>
 * <i>That may be a bug.</i>
 */
public final class OwnerChange extends Constant
{
    private OwnerChange(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    public static final OwnerChange NEW_OWNER = new OwnerChange(GdkOwnerChange.NEW_OWNER, "NEW_OWNER");

    public static final OwnerChange DESTROY = new OwnerChange(GdkOwnerChange.DESTROY, "DESTROY");

    public static final OwnerChange CLOSE = new OwnerChange(GdkOwnerChange.CLOSE, "CLOSE");
}
