/*
 * EventVisibility.java
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

/**
 * The data regarding an event related to the visibility of a Widget. In
 * general this is only relevant when hooked up to a Window, but the
 * capability is general nevertheless. See
 * {@link org.gnome.gtk.Widget.VisibilityNotifyEvent
 * Widget.VisibilityNotifyEvent} for further details.
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
public final class EventVisibility extends Event
{
    protected EventVisibility(long pointer) {
        super(pointer);
    }

    /**
     * This is largely the point of this Event subclass: return the Constant
     * describing the current visibility of the Window. This is what you use
     * in a <code>Widget.VisibilityNotifyEvent</code> handler.
     */
    public VisibilityState getState() {
        return GdkEventVisibility.getState(this);
    }
}
