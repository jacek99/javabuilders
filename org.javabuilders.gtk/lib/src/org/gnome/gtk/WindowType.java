/*
 * WindowType.java
 *
 * Copyright (c) 2006 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import org.freedesktop.bindings.Constant;

/**
 * The type of Window. When constructing {@link org.gnome.gtk.Window Window}s
 * you need to specify what kind of Window it is to be. Since TOPLEVEL is
 * almost always what you want the default constructor of Window chooses this
 * automatically.
 * 
 * @author Andrew Cowie
 * @since 4.0.0
 */
public final class WindowType extends Constant
{
    private WindowType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Most things you'd consider a "window" should have type TOPLEVEL;
     * windows with this type are managed by the window manager and have a
     * frame by default. [The frame is what you might think of the "border",
     * although border is actually a characteristic of Widget. You can call
     * {@link org.gnome.gtk.Window#setDecorated(boolean) setDecorated(false)}
     * to turn the window manager's frame off]
     */
    public static final WindowType TOPLEVEL = new WindowType(GtkWindowType.TOPLEVEL, "TOPLEVEL");

    /**
     * Windows with type POPUP are ignored by the window manager; window
     * manager keybindings won't work on them, the window manager won't
     * decorate the window with a frame (ie borders), and many GTK features
     * that rely on the window manager will not work (for example resize grips
     * and maximize/minimize). WindowType POPUP is used to implement widgets
     * such as menus and tooltips - things that you wouldn't think of as
     * Windows and don't program as such. In particular, do not use this to
     * turn off Window borders! That's what
     * {@link org.gnome.gtk.Window#setDecorated(boolean) setDecorated(false)}
     * is for.
     */
    public static final WindowType POPUP = new WindowType(GtkWindowType.POPUP, "POPUP");
}
