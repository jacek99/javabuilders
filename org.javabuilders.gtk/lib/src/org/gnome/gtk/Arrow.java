/*
 * Arrow.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * Arrow is a widget to draw simple arrows pointing to up, down, left, or
 * right. Its style can be either bevelled inwards, bevelled outwards, sunken
 * or raised. <img src="Arrow.png" class="snapshot">
 * 
 * @author Serkan Kaba
 * @author Andrew Cowie
 * @since 4.0.10
 */
/*
 * TODO Mention of defaults only makes sense if we have a no-arg constructor
 * using those defaults.
 */
public class Arrow extends Misc
{
    protected Arrow(long pointer) {
        super(pointer);
    }

    /**
     * Create a new Arrow widget with given direction and shadow type.
     * 
     * @since 4.0.10
     */
    public Arrow(ArrowType direction, ShadowType type) {
        super(GtkArrow.createArrow(direction, type));
    }

    /**
     * Sets the direction to one of {@link ArrowType#UP UP},
     * {@link ArrowType#DOWN DOWN}, {@link ArrowType#LEFT LEFT},
     * {@link ArrowType#RIGHT RIGHT}. There's also a special mode,
     * {@link ArrowType#NONE NONE} which is an Arrow Widget but with no
     * graphic presently drawn on it.
     * 
     * <p>
     * The default is <code>RIGHT</code>.
     * 
     * @since 4.0.10
     */
    public void setArrowType(ArrowType direction) {
        setPropertyEnum("arrow-type", direction);
    }

    /**
     * Returns the direction of Arrow. See {@link #setArrowType(ArrowType)
     * setArrowType()} for possible values.
     * 
     * @since 4.0.10
     */
    public ArrowType getArrowType() {
        return (ArrowType) getPropertyEnum("arrow-type");
    }

    /**
     * Sets the shadow type to one of {@link ShadowType#IN IN},
     * {@link ShadowType#OUT OUT}, {@link ShadowType#ETCHED_IN ETCHED_IN} or
     * {@link ShadowType#ETCHED_OUT ETCHED_OUT}.
     * 
     * <p>
     * The default is <code>OUT</code>.
     * 
     * @since 4.0.10
     */
    public void setShadowType(ShadowType type) {
        setPropertyEnum("shadow-type", type);
    }

    /**
     * Returns the shadow type of Arrow. See
     * {@link #setShadowType(ShadowType) setShadowType()} for possible values.
     * 
     * @since 4.0.10
     */
    public ShadowType getShadowType() {
        return (ShadowType) getPropertyEnum("shadow-type");
    }

    /**
     * Returns amount of spaced used by arrow in the widget. This is the
     * <var>arrow-scaling</var> style property. Values will be in the range of
     * <code>0.0</code> to <code>1.0</code>. Its default is <code>0.7</code>,
     * though really, it's actual value will entirely be up to the theme
     * engine.
     * 
     * @since 4.0.10
     */
    public float getArrowScaling() {
        return getPropertyFloat("arrow-scaling");
    }
}
