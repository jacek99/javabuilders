/*
 * VisibilityState.java
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
 * Constants indicating the current visibility of a Widget. See the
 * {@link org.gnome.gtk.Widget.VisibilityNotifyEvent
 * Widget.VisibilityNotifyEvent} signal for further details; these constants
 * come from the {@link EventVisibility#getState() getState()} method on an
 * EventVisibility.
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
/*
 * TODO should we normalize these names?
 */
public final class VisibilityState extends Constant
{
    private VisibilityState(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * The Widget is no longer obscured. Will also be fired on initial
     * presentation of a Window.
     */
    public static final VisibilityState UNOBSCURED = new VisibilityState(GdkVisibilityState.UNOBSCURED,
            "UNOBSCURED");

    /**
     * The Widget is partially obscured by another Window (be it this
     * application's or anther's).
     */
    public static final VisibilityState PARTIAL = new VisibilityState(GdkVisibilityState.PARTIAL,
            "PARTIAL");

    /**
     * The Widget is fully blocked from view.
     */
    public static final VisibilityState FULLY_OBSCURED = new VisibilityState(
            GdkVisibilityState.FULLY_OBSCURED, "FULLY_OBSCURED");
}
