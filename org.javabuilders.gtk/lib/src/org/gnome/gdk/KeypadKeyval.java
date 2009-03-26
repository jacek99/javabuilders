/*
 * KeypadKeyval.java
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
 * Constants describing the keys on a keypad (also known as a number pad).
 * 
 * <p>
 * This file serves mostly as an example of how Keyval can be subclassed to
 * make additional Key constants available that you may need.
 * 
 * <p>
 * <i>Beware that this class has to be loaded for these constants to be
 * recognized!</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public class KeypadKeyval extends Keyval
{
    protected KeypadKeyval(int keyval, String name) {
        super(keyval, name);
    }

    public static final Keyval KPSpace = new Keyval(0xff80, "KPSpace");

    public static final Keyval KPTab = new Keyval(0xff89, "KPTab");

    public static final Keyval KPEnter = new Keyval(0xff8d, "KPEnter");

    public static final Keyval KPHome = new Keyval(0xff95, "KPHome");

    public static final Keyval KPLeft = new Keyval(0xff96, "KPLeft");

    public static final Keyval KPUp = new Keyval(0xff97, "KPUp");

    public static final Keyval KPRight = new Keyval(0xff98, "KPRight");

    public static final Keyval KPDown = new Keyval(0xff99, "KPDown");

    public static final Keyval KPPageUp = new Keyval(0xff9a, "KPPageUp");

    public static final Keyval KPPageDown = new Keyval(0xff9b, "KPPageDown");

    public static final Keyval KPEnd = new Keyval(0xff9c, "KPEnd");

    public static final Keyval KPInsert = new Keyval(0xff9e, "KPInsert");

    public static final Keyval KPDelete = new Keyval(0xff9f, "KPDelete");

    public static final Keyval KPEqual = new Keyval(0xffbd, "KPEqual");

    public static final Keyval KPMultiply = new Keyval(0xffaa, "KPMultiply");

    public static final Keyval KPAdd = new Keyval(0xffab, "KPAdd");

    public static final Keyval KPSubtract = new Keyval(0xffad, "KPSubtract");

    public static final Keyval KPDecimal = new Keyval(0xffae, "KPDecimal");

    public static final Keyval KPDivide = new Keyval(0xffaf, "KPDivide");

    /**
     * The middle key (the one labeled <b>5</b>) on a keyboard with a numeric
     * keypad. This is distinct from {@link #KP5}, which is what is emitted if
     * <b>NumLock</b> is on.
     * 
     * <p>
     * <i>Why "Begin"? I would have thought "Center". Weird legacy crap, no
     * doubt.</i>
     */
    public static final Keyval KPBegin = new Keyval(0xff9d, "KPBegin");

    public static final Keyval KP0 = new Keyval(0xffb0, "KP0");

    public static final Keyval KP1 = new Keyval(0xffb1, "KP1");

    public static final Keyval KP2 = new Keyval(0xffb2, "KP2");

    public static final Keyval KP3 = new Keyval(0xffb3, "KP3");

    public static final Keyval KP4 = new Keyval(0xffb4, "KP4");

    public static final Keyval KP5 = new Keyval(0xffb5, "KP5");

    public static final Keyval KP6 = new Keyval(0xffb6, "KP6");

    public static final Keyval KP7 = new Keyval(0xffb7, "KP7");

    public static final Keyval KP8 = new Keyval(0xffb8, "KP8");

    public static final Keyval KP9 = new Keyval(0xffb9, "KP9");
}
