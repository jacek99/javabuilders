/*
 * WeightAttribute.java
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
 * An Attribute that modifies the weight of the text it is applied to. See
 * {@link Weight}.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
public final class WeightAttribute extends Attribute
{
    /**
     * Create a WeightAttribute.
     * 
     * @since 4.0.10
     */
    public WeightAttribute(Weight weight) {
        super(PangoAttribute.createAttributeWeight(weight));
    }
}
