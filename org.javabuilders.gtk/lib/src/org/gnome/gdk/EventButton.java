/*
 * EventButton.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

/**
 * Event data describing a button on a pointing device that was pressed or
 * released. Notably, you can find out which button on the device was clicked
 * with {@link #getButton() getButton()}, and whether any modifier keys were
 * being held down by the user with {@link #getState() getState()}.
 * 
 * @author Andrew Cowie
 * @author Srichand Pendyala
 * @since 4.0.6
 */
public final class EventButton extends Event
{
    protected EventButton(long pointer) {
        super(pointer);
    }

    /**
     * Which button on the pointing device was pressed?
     * 
     * @since 4.0.6
     */
    public MouseButton getButton() {
        return GdkMouseButtonOverride.enumFor(GdkEventButton.getButton(this));
    }

    /**
     * Get the state of the modifier keys. This will be
     * {@link ModifierType#NONE NONE} if no modifiers are being held down. See
     * EventKey's {@link EventKey#getState() getState()} and
     * {@link ModifierType} for usage details.
     * 
     * @since 4.0.6
     */
    public ModifierType getState() {
        return GdkKeyvalOverride.flagFor(GdkEventButton.getState(this));
    }

    /**
     * Get the horizontal location that this Event occured at, relative to the
     * <code>[org.gnome.gdk]</code> Window. In most cases you will get an
     * integral return; in any case, most usages of this return value will
     * want a whole number of pixels, so cast to <code>int</code> as
     * necessary.
     * 
     * @since 4.0.9
     */
    public double getX() {
        return GdkEventButton.getX(this);
    }

    /**
     * Get the vertical location that this Event occured at, relative to the
     * <code>[org.gnome.gdk]</code> Window.
     * 
     * @since 4.0.9
     */
    public double getY() {
        return GdkEventButton.getY(this);
    }
}
