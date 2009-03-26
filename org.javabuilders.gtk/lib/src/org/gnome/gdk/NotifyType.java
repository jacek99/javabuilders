/*
 * NotifyType.java
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
 * The kind of motion in an EventCrossing. These Constants describe the
 * relationship between the GDK Window that the mouse pointer left, and the
 * GDK Window that the mouse pointer entered.
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 * @see <a
 *      href="http://xorg.freedesktop.org/releases/X11R7.0/doc/PDF/xlib.pdf">The
 *      XLib programming manual, section 10.6</a>
 */
public final class NotifyType extends Constant
{
    private NotifyType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * The mouse has moved from an inferior Window to a superior or enclosing
     * one.
     */
    public static final NotifyType ANCESTOR = new NotifyType(GdkNotifyType.ANCESTOR, "ANCESTOR");

    /**
     * This event is generated for Windows that lay between the receiving
     * Window and the departing Window.
     */
    public static final NotifyType VIRTUAL = new NotifyType(GdkNotifyType.VIRTUAL, "VIRTUAL");

    /**
     * The mouse has moved from a superior (parent, enclosing) Window to an
     * inferior (child) one.
     */
    public static final NotifyType INFERIOR = new NotifyType(GdkNotifyType.INFERIOR, "INFERIOR");

    /**
     * The mouse moved between unrelated Windows. This is what seems to occur
     * most of the time when you exit the app and cross to the root X Window
     * or some other application's Window. Not terribly helpful otherwise.
     */
    public static final NotifyType NONLINEAR = new NotifyType(GdkNotifyType.NONLINEAR, "NONLINEAR");

    public static final NotifyType NONLINEAR_VIRTUAL = new NotifyType(GdkNotifyType.NONLINEAR_VIRTUAL,
            "NONLINEAR_VIRTUAL");

    public static final NotifyType UNKNOWN = new NotifyType(GdkNotifyType.UNKNOWN, "UNKNOWN");
}
