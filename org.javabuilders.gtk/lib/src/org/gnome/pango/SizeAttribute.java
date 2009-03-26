/*
 * SizeAttribute.java
 *
 * Copyright (c) 2009 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.pango;

/**
 * An Attribute that modifies the size of the text it is applied to.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
public final class SizeAttribute extends Attribute
{
    /**
     * Create a SizeAttribute.
     * 
     * <p>
     * This is equivalent to using FontDescription's
     * {@link FontDescription#setSize(double) setSize()} when requesting fonts
     * and TextTag's {@link org.gnome.gtk.TextTag#setSize(double) setSize()}
     * when formatting text in a TextBuffer.
     * 
     * <p>
     * <i>The SizeAttribute is, indeed, ultimately what powers both these
     * properties.</i>
     * 
     * @since 4.0.10
     */
    public SizeAttribute(double size) {
        super(PangoAttribute.createAttributeSize((int) (size * Pango.SCALE)));
    }
}
