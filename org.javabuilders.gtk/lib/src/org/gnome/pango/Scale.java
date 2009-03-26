/*
 * Scale.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.pango;

import org.freedesktop.bindings.DoubleConstant;

/**
 * Constants use to scale text by an amount relative to that around it. This
 * is used when specifying text size with TextTag's
 * {@link org.gnome.gtk.TextTag#setScale(Scale) setScale()}.
 * 
 * <p>
 * The default, such as it is, is {@link #NORMAL NORMAL} which represents a
 * scaling factor of <code>1.0</code>, (ie. no scaling).
 * 
 * <p>
 * <i>In case you're wondering, these scaling constants are</i>
 * <code>(1.2)<sup>n</sup></code> <i>where</i> <code>n</code> <i>is
 * <code>1,2,3</code> for</i> {@link #LARGE LARGE}, {@link #X_LARGE X_LARGE},
 * {@link #XX_LARGE XX_LARGE}<i>, and <code>-1,-2,-3</code> for</i>
 * {@link #SMALL SMALL}, {@link #X_SMALL X_SMALL}, <i>and</i>
 * {@link #XX_SMALL XX_SMALL} <i>respectively.</i>
 * 
 * @since 4.0.9
 */
/*
 * FUTURE Ordinarily we would go to native, but at present we don't have a
 * mechanism for extracting #defines of doubles. So we'll hard code it for
 * now. DANGER! Watch out for changes in Pango upstream; if that should even
 * be suggested, write an Override class to access these constants.
 */
public class Scale extends DoubleConstant
{
    protected Scale(double factor, String nickname) {
        super(factor, nickname);
    }

    public static final Scale XX_SMALL = new Scale(1.0 / (1.2 * 1.2 * 1.2), "XX_SMALL");

    public static final Scale X_SMALL = new Scale(1.0 / (1.2 * 1.2), "X_SMALL");

    public static final Scale SMALL = new Scale(1.0 / 1.2, "SMALL");

    private static final Scale MEDIUM = new Scale(1.0, "MEDIUM");

    /**
     * Normal (unscaled) text, scaling factor <code>1.0</code>.
     */
    public static final Scale NORMAL = MEDIUM;

    public static final Scale LARGE = new Scale(1.2, "LARGE");

    public static final Scale X_LARGE = new Scale(1.2 * 1.2, "X_LARGE");

    public static final Scale XX_LARGE = new Scale(1.2 * 1.2 * 1.2, "XX_LARGE");
}
