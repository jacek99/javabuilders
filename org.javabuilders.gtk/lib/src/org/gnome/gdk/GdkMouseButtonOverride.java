/*
 * GdkMouseButtonOverride.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" See the LICENCE file for the terms governing usage and
 * redistribution.
 */
package org.gnome.gdk;

final class GdkMouseButtonOverride extends Plumbing
{
    private GdkMouseButtonOverride() {}

    static final MouseButton enumFor(int ordinal) {
        return (MouseButton) org.freedesktop.bindings.Plumbing.enumFor(MouseButton.class, ordinal);
    }
}
