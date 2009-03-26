/*
 * EventOwnerChange.java
 *
 * Copyright (c) 2009 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

/**
 * Details about a selection that has been taken over.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
public final class EventOwnerChange extends Event
{
    protected EventOwnerChange(long pointer) {
        super(pointer);
    }

    /**
     * Get the "reason" that this EventOwnerChange occured.
     * 
     * @since 4.0.10
     */
    public OwnerChange getReason() {
        return GdkEventOwnerChange.getReason(this);
    }
}
