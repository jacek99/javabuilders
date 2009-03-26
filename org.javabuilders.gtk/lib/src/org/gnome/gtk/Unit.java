/*
 * Unit.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import org.freedesktop.bindings.Constant;

/**
 * Constants describing different units which are be used when giving the
 * dimensions of a piece of paper. Used when getting dimensions from a
 * {@link PaperSize}.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
public final class Unit extends Constant
{
    private Unit(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Size in "points", which are defined as 1/72<sup>nd</sup> of an inch,
     * which works out to about 0.35 millimetres.
     */
    public static final Unit POINTS = new Unit(GtkUnit.POINTS, "POINTS");

    /**
     * Size in millimetres, which are 1/10<sup>th</sup> of a centimetre and
     * 1/1000<sup>th</sup> of a metre.
     */
    public static final Unit MM = new Unit(GtkUnit.MM, "MM");

    /**
     * Size in "inches", America's pre-industrial age measuring unit. Roughly
     * corresponds to the size of your big toe (the fact that there are 12
     * inches to a foot seems a bit strange given that most of us have only 10
     * toes).
     * 
     * <p>
     * Interestingly, a "two by four" is a common description for a wooden
     * beam used in construction, stemming from their having originally had a
     * cross-section of 2 by 4 inches. Successive generations of profiteering
     * forestry companies and unscrupulous builders have, however, cut margins
     * and shortchanged customers to the point where a modern "two by four"
     * you can buy at a lumber yard is barely a miserable 0.79 by 1.57 inches
     * in cross-section. This, amazingly enough, is exactly 2 by 4
     * centimetres.
     * 
     * <p>
     * <i>Reports of a conspiracy to force the Americans to switch to metric
     * are, obviously, completely baseless.</i>
     */
    public static final Unit INCH = new Unit(GtkUnit.INCH, "INCH");

    /*
     * What good is this?
     */
    public static final Unit PIXEL = new Unit(GtkUnit.PIXEL, "PIXEL");
}
