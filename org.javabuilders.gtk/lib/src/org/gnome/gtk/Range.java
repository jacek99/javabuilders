/*
 * Range.java
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

/**
 * Base class for Widgets which present an adjustable quantity in some form of
 * slider. The most obvious feature of this class is the ability to manage the
 * "value" being shown by the Widget, but there are also facilities for
 * exercising fine-grained control over the behaviour of the Widget when the
 * user attempts to adjust the slider.
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
/*
 * TODO Add coverage of the step and increment controls, obviously. As I
 * recall, the interactions of these with Adjustment are more than complex, so
 * please test carefully and document your experiences well.
 */
public abstract class Range extends Widget
{
    protected Range(long pointer) {
        super(pointer);
    }

    /**
     * Retreive the value currently indicated by this Range instance.
     * 
     * @since 4.0.6
     */
    public double getValue() {
        return GtkRange.getValue(this);
    }

    /**
     * Change the value showingin the Range. As you would expect, the
     * <code>Range.ValueChanged</code> signal will be emitted if the new value
     * is different from the present setting.
     * 
     * @since 4.0.6
     */
    public void setValue(double value) {
        GtkRange.setValue(this, value);
    }

    /**
     * The value showing in the Range instance has changed.
     * 
     * @author Andrew Cowie
     * @since 4.0.6
     */
    public interface ValueChanged extends GtkRange.ValueChangedSignal
    {
        public void onValueChanged(Range source);
    }

    /**
     * Connect a <code>Range.ValueChanged</code> handler to this Range
     * instance.
     * 
     * @since 4.0.6
     */
    public void connect(Range.ValueChanged handler) {
        GtkRange.connect(this, handler, false);
    }

    /** @deprecated */
    public interface VALUE_CHANGED extends GtkRange.ValueChangedSignal
    {
        public void onValueChanged(Range source);
    }

    /** @deprecated */
    public void connect(Range.VALUE_CHANGED handler) {
        assert false : "use Range.ValueChanged instead";
        GtkRange.connect(this, handler, false);
    }

}
