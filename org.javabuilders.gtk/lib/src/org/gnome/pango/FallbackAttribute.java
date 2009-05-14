/*
 * FallbackAttribute.java
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
 * Use this Attribute to inhibit Pango from falling back to another font if
 * the glyph it needs is not available in the currently selected font.
 * 
 * @author Andrew Cowie
 * @since 4.0.11
 */
public final class FallbackAttribute extends Attribute
{
    /**
     * Create a FallbackAttribute. Since the ordinary default is to fallback
     * according to your system's fontconfig settings, you only need to call
     * this to set it <code>false</code>.
     * 
     * @since 4.0.11
     */
    public FallbackAttribute(boolean setting) {
        super(PangoAttribute.createAttributeFallback(setting));
    }
}
