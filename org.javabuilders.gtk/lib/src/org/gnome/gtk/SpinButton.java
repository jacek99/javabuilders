/*
 * SpinButton.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2008      Vreixo Formoso
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * A SpinButton is a little text entry used as input for numeric values in a
 * given range. It incorporates two little arrow Buttons that user can click
 * to increment or decrement the value by a given amount.
 * 
 * <p>
 * It is used to let the user introduce the value of a numeric property. This
 * is specially useful when the range of allowed values is unlimited (well, in
 * practise limited by <code>Double.MAX_VALUE</code>!) or only limited at one
 * end. Otherwise, a control like {@link Scale} may be a better alternative.
 * 
 * <p>
 * It is also a good idea to add near the SpinButton a Label indicating the
 * units of measure of the property it refers to.
 * 
 * <p>
 * The user can modify the SpinButton either introducing a numeric value in
 * the required range in the text entry, or by clicking the arrow Buttons. The
 * keyboard can also be used, with both the <b><code>Up</code></b> and <b>
 * <code>Down</code></b>, or with the <b><code>PageUp</code></b> or <b>
 * <code>PageDown</code></b> keys. These last decrement or increment the value
 * of the entry by a greater amount (usually ten times the value of the arrow
 * Button step).
 * 
 * <p>
 * The programmer can get the value introduced by the user with the
 * {@link #getValue() getValue()} method. While this method returns a
 * <code>double</code>, by default the SpinButton only allows to introduce
 * integer values. You can use the {@link #setDigits(int) setDigits()} method
 * to change this behaviour.
 * 
 * @author Vreixo Formoso
 * @version 4.0.7
 */
public class SpinButton extends Entry
{
    protected SpinButton(long pointer) {
        super(pointer);
    }

    /**
     * Create a new SpinButton.
     * 
     * @param min
     *            The minimum value allowed.
     * @param max
     *            The maximum value allowed.
     * @param step
     *            The amount to increment/decrement when one of the arrow
     *            Buttons are clicked, or when the user press the <b>
     *            <code>Up</code></b> or <b><code>Down</code></b> keys.
     * @since 4.0.7
     */
    public SpinButton(double min, double max, double step) {
        super(GtkSpinButton.createSpinButtonWithRange(min, max, step));
    }

    /**
     * Set the number of decimal digits to allow in the entry. This affects to
     * both the precision of the value displayed and the number of decimal
     * digits the user can introduce in the text entry. You should always give
     * enough digits to represent the <code>step</code> you have chosen,
     * otherwise you will get an undesired behaviour.
     * 
     * @since 4.0.7
     */
    /*
     * According Gtk+ documentation, only up to 20 digits is allowed. However,
     * my tests shown that this limit does not actually exist. At the end, we
     * are limited by the precision of a double!
     */
    public void setDigits(int digits) {
        GtkSpinButton.setDigits(this, digits);
    }

    /**
     * Get the current numeric value of the SpinButton.
     * 
     * @since 4.0.7
     */
    public double getValue() {
        return GtkSpinButton.getValue(this);
    }

    /**
     * Set the value displayed in the text entry. If the given value can't be
     * represented with the selected decimal digits (see
     * {@link #setDigits(int) setDigits()}), the next time user increments or
     * decrements its values the additional digits will be lost, so take care
     * about this.
     * 
     * @since 4.0.7
     */
    public void setValue(double value) {
        GtkSpinButton.setValue(this, value);
    }

    /**
     * Signal emitted when the value of the SpinButton changes, either by
     * clicking the arrow buttons, by pressing the specified keys, by input a
     * new value on the text entry, or by setting it programmatically with
     * {@link SpinButton#setValue(double) setValue()} method.
     * 
     * @author Vreixo Formoso
     * @since 4.0.7
     */
    public interface ValueChanged extends GtkSpinButton.ValueChangedSignal
    {
        public void onValueChanged(SpinButton source);
    }

    /**
     * Hook up a handler for the <code>SpinButton.ValueChanged</code> signal.
     * 
     * @since 4.0.7
     */
    public void connect(SpinButton.ValueChanged handler) {
        GtkSpinButton.connect(this, handler, false);
    }

    /** @deprecated */
    public interface VALUE_CHANGED extends GtkSpinButton.ValueChangedSignal
    {
        public void onValueChanged(SpinButton source);
    }

    /** @deprecated */
    public void connect(VALUE_CHANGED handler) {
        assert false : "use SpinButton.ValueChanged instead";
        GtkSpinButton.connect(this, handler, false);
    }
}
