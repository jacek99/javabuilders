/*
 * Gravity.java
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

import org.freedesktop.bindings.Constant;

/**
 * Gravity determines the location of the reference point in root window
 * co-ordinates and which point of the Window is positioned at the reference
 * point. This impacts the co-ordinates used when moving Windows with
 * {@link org.gnome.gtk.Window#move(int, int) move()}. The gravity for a
 * Window is set by {@link org.gnome.gtk.Window#setGravity(Gravity)
 * setGravity()}.
 * 
 * <p>
 * <i>It turns out the whole gravity concept is unreliable; luckily you don't
 * really need it for much. And in any case, if you're trying to move the
 * Window around; what you probably want is</i>
 * {@link org.gnome.gtk.Window#setPosition(org.gnome.gtk.WindowPosition)
 * setPosition()}.
 * 
 * @author Andrew Cowie
 * @since 4.0.4
 */
public final class Gravity extends Constant
{
    private Gravity(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Orientation to the top left corner of the screen and co-ordinates
     * running right and down. <b>This is the default</b> gravity and matches
     * what you'd normally expect <code>x</code>,<code>y</code> to mean on an
     * X display: horizontal and vertical distance from the top-left corner.
     */
    public static final Gravity NORTH_WEST = new Gravity(GdkGravity.NORTH_WEST, "NORTH_WEST");

    public static final Gravity SOUTH_WEST = new Gravity(GdkGravity.SOUTH_WEST, "SOUTH_WEST");

    public static final Gravity NORTH_EAST = new Gravity(GdkGravity.NORTH_EAST, "NORTH_EAST");

    public static final Gravity SOUTH_EAST = new Gravity(GdkGravity.SOUTH_EAST, "SOUTH_EAST");

    /**
     * Reference point is with respect to the center of the screen. If you're
     * trying to center your Window on the screen this probably isn't what you
     * want; see Window's
     * {@link org.gnome.gtk.Window#setPosition(org.gnome.gtk.WindowPosition)
     * setPosition()} with an argument of
     * {@link org.gnome.gtk.WindowPosition#CENTER CENTER} or
     * {@link org.gnome.gtk.WindowPosition#CENTER_ALWAYS CENTER_ALWAYS}
     * instead.
     */
    public static final Gravity CENTER = new Gravity(GdkGravity.CENTER, "CENTER");

    /**
     * Co-ordinates are with respect to the center of the top edge of the
     * Window. This is rarely useful.
     */
    public static final Gravity NORTH = new Gravity(GdkGravity.NORTH, "NORTH");

    /**
     * Co-ordinates are with respect to the center of the bottom edge of the
     * Window. This is rarely useful.
     */
    public static final Gravity SOUTH = new Gravity(GdkGravity.SOUTH, "SOUTH");

    /**
     * Co-ordinates are with respect to the center of the right-hand edge of
     * the Window. This is rarely useful.
     */
    public static final Gravity EAST = new Gravity(GdkGravity.EAST, "EAST");

    /**
     * Co-ordinates are with respect to the center of the left-hand edge of
     * the Window. This is rarely useful.
     */
    public static final Gravity WEST = new Gravity(GdkGravity.WEST, "WEST");

    /**
     * This is a weird one: it is in reference to the top-left corner of the
     * Window itself (like {@link #NORTH_WEST} but ignoring whatever
     * decorations etc have been added outside by the window manager. At first
     * this would seem brilliantly useful, but since it ignores window
     * decorations, moving to the co-ordinates returned by
     * {@link org.gnome.gtk.Window#getPositionX() getPosition()} will actually
     * cause the Window to move slightly on the screen, rather than staying
     * still as you might have expected. So even when you think you want this,
     * you really want NORTH_WEST, which is why it's the default.
     */
    public static final Gravity STATIC = new Gravity(GdkGravity.STATIC, "STATIC");

}
