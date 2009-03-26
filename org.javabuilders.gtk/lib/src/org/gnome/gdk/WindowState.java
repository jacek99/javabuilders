/*
 * WindowState.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

import org.freedesktop.bindings.Flag;

/*
 * FIXME this is a placeholder stub for what will become the public API for
 * this type. Replace this comment with appropriate javadoc including author
 * and since tags. Note that the class may need to be made abstract, implement
 * interfaces, or even have its parent changed. No API stability guarantees
 * are made about this class until it has been reviewed by a hacker and this
 * comment has been replaced.
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
