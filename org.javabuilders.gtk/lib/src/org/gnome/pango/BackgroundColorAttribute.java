/*
 * BackgroundColorAttribute.java
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
 * Set the background ("highlight") colour for the text.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
public final class BackgroundColorAttribute extends Attribute
{
    /**
     * Create a BackgroundColorAttribute. The three colour parameters are in
     * the same [0,1] range as other Cairo drawing functions use.
     * 
     * @since 4.0.10
     */
    public BackgroundColorAttribute(double red, double green, double blue) {
        super(PangoAttribute.createAttributeBackground((char) (red * 65535), (char) (green * 65535),
                (char) (blue * 65535)));
    }
}
