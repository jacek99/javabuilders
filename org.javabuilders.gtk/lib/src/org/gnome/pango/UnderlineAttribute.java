/*
 * UnderlineAttribute.java
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
 * An Attribute that specifies the underlinging to be drawn under the text it
 * is applied to. See {@link Underline}.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
public final class UnderlineAttribute extends Attribute
{
    /**
     * Create an UnderlineAttribute.
     * 
     * <p>
     * This is equivalent to using TextTag's
     * {@link org.gnome.gtk.TextTag#setUnderline(Underline) setUnderline()}
     * when formatting text in a TextBuffer.
     * 
     * @since 4.0.10
     */
    public UnderlineAttribute(Underline underline) {
        super(PangoAttribute.createAttributeUnderline(underline));
    }
}
