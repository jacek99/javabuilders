/*
 * EventKey.java
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
 * Event data describing a key press.
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public final class EventKey extends Event
{
    protected EventKey(long pointer) {
        super(pointer);
    }

    /**
     * Get the key that was pressed.
     * 
     * @since 4.0.6
     */
    public Keyval getKeyval() {
        return GdkKeyvalOverride.enumFor(GdkEventKey.getKeyval(this));
    }

    /**
     * Get the Flags object representing what modifiers are being held down,
     * if any.
     * 
     * <p>
     * If you're only expecting a single modifier to be pressed then you could
     * compare by equality:
     * 
     * <pre>
     * if (mod == ModifierType.SHIFT_MASK) {
     *     ...
     * }
     * </pre>
     * 
     * but if you're looking for more complex combinations, or worried that
     * more than one modifier could be down, then use <code>contains()</code>:
     * 
     * <pre>
     * if (mod.contains(ModifierType.CONTROL_MASK)) {
     *     ...
     * }
     * </pre>
     * 
     * which will always do what you want.
     * 
     * @since 4.0.6
     */
    public ModifierType getState() {
        return GdkKeyvalOverride.flagFor(GdkEventKey.getState(this));
    }
}
