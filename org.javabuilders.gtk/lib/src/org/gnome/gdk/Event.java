/*
 * Event.java
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

import org.gnome.glib.Boxed;

/**
 * The events used to communicate data describing the internal details of
 * activities that occur to or between GDK resources.
 * 
 * <p>
 * <i>C side, <code>GdkEvent</code> is a union of various event structs such
 * as <code>GdkEventExpose</code> and <code>GdkEventKey</code>. Each struct in
 * this family starts with the same fields, and these fields are represented
 * by <code>GdkEventAny</code>. We have exposed those fields here on
 * Event.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.3
 */
public abstract class Event extends Boxed
{
    protected Event(long pointer) {
        super(pointer);
    }

    protected void release() {
        GdkEvent.free(this);
    }

    /**
     * Get the type of event that occurred. There are fairly tight
     * relationships between these type constants and the concrete Event
     * subclasses, see {@link EventType EventType} for an example.
     * 
     * @since 4.0.3
     */
    public EventType getType() {
        return GdkEventAny.getType(this);
    }

    /**
     * Get the underlying [GDK] Window which received the event.
     * 
     * @since 4.0.3
     */
    public Window getWindow() {
        return GdkEventAny.getWindow(this);
    }
}
