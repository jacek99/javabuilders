/*
 * HintMetrics.java
 *
 * Copyright (c) 2009 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.cairo;

import org.freedesktop.bindings.Constant;

/**
 * Whether to hint the font rendering based on alignment to the integer pixel
 * grid. Forcing this {@link #OFF OFF} is necessary if you want perfect linear
 * scaling of your rendered fonts.
 * 
 * <p>
 * The default setting is to be inherited, and while probably <code>ON</code>;
 * the presence of {@link #DEFAULT DEFAULT} means you can generally leave this
 * alone and not worry about it.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
public class HintMetrics extends Constant
{
    private HintMetrics(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Default is like "unset"; the existing value from the surrounding
     * environment (Context, Surface, Font in use, Font rendering back end,
     * etc) will be used.
     * 
     * @since 4.0.10
     */
    public static final HintMetrics DEFAULT = new HintMetrics(CairoHintMetrics.DEFAULT, "DEFAULT");

    /**
     * Turn <b>metric</b> hinting
     * 
     * @since 4.0.10
     */
    public static final HintMetrics OFF = new HintMetrics(CairoHintMetrics.OFF, "OFF");

    /**
     * Hinting font metrics means <i>"quantizing them so that they are integer
     * values"</i> in the target Surface's physical rendering co-ordinate
     * space. This is good for visual appearance but breaks perfect smoothness
     * when doing linear scaling (such as in animation if zooming in).
     * 
     * @since 4.0.10
     */
    public static final HintMetrics ON = new HintMetrics(CairoHintMetrics.ON, "ON");
}
