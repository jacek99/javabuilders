/*
 * EventCrossing.java
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
 * Event data describing a mouse entering or leaving a Window.
 * 
 * <p>
 * This is used by {@link org.gnome.gtk.Widget.EnterNotifyEvent} and
 * {@link org.gnome.gtk.Widget.LeaveNotifyEvent}.
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
public final class EventCrossing extends Event
{
    protected EventCrossing(long pointer) {
        super(pointer);
    }

    /**
     * Describes the mode of this EventCrossing: whether the event is a
     * {@link CrossingMode#NORMAL NORMAL} one or a pseudo-motion one resulting
     * from a {@link CrossingMode#GRAB GRAB} or {@link CrossingMode#UNGRAB
     * UNGRAB}.
     * 
     * @since 4.0.7
     */
    public CrossingMode getMode() {
        return GdkEventCrossing.getMode(this);
    }

    /**
     * Describes the relationship between the Window from which the mouse
     * pointer left, and the Window which the mouse pointer entered. Most
     * often you'll see is {@link NotifyType#NONLINEAR NONLINEAR}, which tells
     * you that the pointer moved between unrelated X Windows.
     * 
     * @since 4.0.7
     */
    public NotifyType getDetail() {
        return GdkEventCrossing.getDetail(this);
    }
}
