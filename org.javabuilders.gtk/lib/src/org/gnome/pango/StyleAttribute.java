/*
 * StyleAttribute.java
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
 * An Attribute that modifies the style of the text it is applied to. See
 * {@link Style}.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
public final class StyleAttribute extends Attribute
{
    /**
     * Create a StyleAttribute. {@link Style#ITALIC ITALIC} is usually the
     * Style people want since <code>NORMAL</code> is the default.
     * 
     * <p>
     * This is equivalent to using FontDescription's
     * {@link FontDescription#setStyle(Style) setStyle()} when requesting
     * fonts and TextTag's {@link org.gnome.gtk.TextTag#setStyle(Style)
     * setStyle()} when formatting text in a TextBuffer.
     * 
     * <p>
     * <i>The StyleAttribute is actually the mechanism that underlies both
     * properties.</i>
     * 
     * 
     * @since 4.0.10
     */
    public StyleAttribute(Style style) {
        super(PangoAttribute.createAttributeStyle(style));
    }
}
