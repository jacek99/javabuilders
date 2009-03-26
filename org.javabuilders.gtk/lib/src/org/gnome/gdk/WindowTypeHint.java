/*
 * WindowTypeHint.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
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
 * Constants indicating hints you can provide to the window manager about what
 * the nature and purpose of a given Window is. While this is in the GDK
 * package, its primary use is for designating the purpose of top level
 * <code>[org.gnome.gtk]</code> Windows via
 * {@link org.gnome.gtk.Window#setTypeHint(WindowTypeHint) setTypeHint()}.
 * 
 * @author Andrew Cowie
 * @since 4.0.8
 */
public final class WindowTypeHint extends Constant
{
    private WindowTypeHint(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * This is the default when constructing a new Window and you don't need
     * to set it.
     * 
     * @since 4.0.8
     */
    public static final WindowTypeHint NORMAL = new WindowTypeHint(GdkWindowTypeHint.NORMAL, "NORMAL");

    /**
     * Mark this Window as a utility window. Under Metacity you should expect
     * a Window so marked to not appear in the pager or taskbar. A utility
     * window will be raised when a normal Window from the same application
     * gains the focus.
     * 
     * <p>
     * This setting is excellent for secondary windows. The key question is
     * "should I be able to <b><code>Alt+Tab</code></b> to this Window?" If
     * it's not the real application, per se, then the answer is "probably
     * not" and the Window should be marked <code>UTILITY</code>.
     * 
     * @since 4.0.8
     */
    public static final WindowTypeHint UTILITY = new WindowTypeHint(GdkWindowTypeHint.UTILITY, "UTILITY");

    /**
     * This Window will be used to present something that is docked,
     * presumably to the panel. An example in GNOME is the calendar that the
     * clock applet displays as a popup. Once raised, it stays on top of all
     * other windows regardless of which workspace you are on or what other
     * applications you focus.
     * 
     * <p>
     * If you're thinking to use this you probably want Window's
     * {@link org.gnome.gtk.Window#setKeepAbove(boolean) setKeepAbove()}
     * instead.
     * 
     * @since 4.0.8
     */
    public static final WindowTypeHint DOCK = new WindowTypeHint(GdkWindowTypeHint.DOCK, "DOCK");

    /**
     * This Window is a dialog. Obviously if you've used Dialog or one of its
     * subclasses, the window manager will be informed properly for you. This
     * is for when you've created a Window that is acting as a dialog but
     * isn't a Dialog.
     * 
     * @since 4.0.8
     */
    public static final WindowTypeHint DIALOG = new WindowTypeHint(GdkWindowTypeHint.DIALOG, "DIALOG");
}
