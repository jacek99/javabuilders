/*
 * WindowState.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

import org.freedesktop.bindings.Flag;

/**
 * Constants describing the state of an underlying resource. You can access
 * most of these by calling methods available on [org.gnome.gtk] Window.
 * 
 * @author Vreixo Formoso
 * @since 4.0.3
 */
/*
 * How on earth did these get in without documentation? FIXME!
 */
public final class WindowState extends Flag
{
    private WindowState(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    public static final WindowState WITHDRAWN = new WindowState(GdkWindowState.WITHDRAWN, "WITHDRAWN");

    public static final WindowState ICONIFIED = new WindowState(GdkWindowState.ICONIFIED, "ICONIFIED");

    public static final WindowState MAXIMIZED = new WindowState(GdkWindowState.MAXIMIZED, "MAXIMIZED");

    public static final WindowState STICKY = new WindowState(GdkWindowState.STICKY, "STICKY");

    public static final WindowState FULLSCREEN = new WindowState(GdkWindowState.FULLSCREEN, "FULLSCREEN");

    public static final WindowState ABOVE = new WindowState(GdkWindowState.ABOVE, "ABOVE");

    public static final WindowState BELOW = new WindowState(GdkWindowState.BELOW, "BELOW");

    /**
     * Creates a new WindowState flag as the OR'ing or combination of two
     * WindowState flags.
     */
    public static WindowState or(WindowState one, WindowState two) {
        return (WindowState) Flag.orTwoFlags(one, two);
    }
}
