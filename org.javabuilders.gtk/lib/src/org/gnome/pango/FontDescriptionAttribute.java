/*
 * FontDescriptionAttribute.java
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
 * Create an Attribute that applies the specified FontDescription. This is a
 * baseline; all the other Attributes will supersede settings established here
 * by the presence of one of these.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
public final class FontDescriptionAttribute extends Attribute
{
    /**
     * Create a FondDescriptionAttribute.
     * 
     * @since 4.0.10
     */
    public FontDescriptionAttribute(FontDescription desc) {
        super(PangoAttribute.createAttributeFontDescription(desc));
    }
}
