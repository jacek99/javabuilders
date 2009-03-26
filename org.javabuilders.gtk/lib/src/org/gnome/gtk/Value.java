/*
 * Value.java
 *
 * Copyright (c) 2006-2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import org.freedesktop.bindings.Constant;
import org.gnome.gdk.Pixbuf;

/**
 * Wrap real Value class so that we can keep the visibility of its methods
 * restricted. See {@link org.gnome.glib.Value Value} for all the details.
 * 
 * @author Andrew Cowie
 * @author Vreixo Formoso
 * @since 4.0.4
 */
/*
 * This is cut and paste hell, so be careful. Make sure unit tests in
 * org.gnome.gtk do not import org.gnome.glib.Value.
 */
class Value extends org.gnome.glib.Value
{
    protected Value(long pointer, boolean proxy) {
        super(pointer, proxy);
    }

    Value() {
        super();
    }

    Value(String value) {
        super(value);
    }

    protected String getString() {
        return super.getString();
    }

    Value(int value) {
        super(value);
    }

    protected int getInteger() {
        return super.getInteger();
    }

    Value(boolean value) {
        super(value);
    }

    protected boolean getBoolean() {
        return super.getBoolean();
    }

    Value(float value) {
        super(value);
    }

    Value(double value) {
        super(value);
    }

    Value(org.gnome.glib.Object obj) {
        super(obj);
    }

    Value(Pixbuf pixbuf) {
        super(pixbuf);
    }

    /*
     * Used by GObject, not GtkObject!
     */
    protected org.gnome.glib.Object getObject() {
        return super.getObject();
    }

    protected Pixbuf getPixbuf() {
        return super.getPixbuf();
    }

    protected float getFloat() {
        return super.getFloat();
    }

    protected double getDouble() {
        return super.getDouble();
    }

    Value(long value) {
        super(value);
    }

    protected long getLong() {
        return super.getLong();
    }

    Value(Constant value) {
        super(value);
    }

    protected Constant getEnum() {
        return super.getEnum();
    }
}
