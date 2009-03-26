/*
 * ShadowType.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import org.freedesktop.bindings.Constant;

/**
 * Appearance of the outline shown in some Widgets such as {@link Frame} and
 * {@link Arrow}.
 * 
 * @author Vreixo Formoso
 * @since 4.0.7
 */
public final class ShadowType extends Constant
{
    private ShadowType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * The Widget is shown without outline.
     */
    public static final ShadowType NONE = new ShadowType(GtkShadowType.NONE, "NONE");

    /**
     * The outline is bevelled inwards. That causes the visual effect that the
     * Widget is sunken on the screen.
     */
    public static final ShadowType IN = new ShadowType(GtkShadowType.IN, "IN");

    /**
     * The outline is bevelled outwards. That causes the visual effect that
     * the Widget is raised on the screen.
     */
    public static final ShadowType OUT = new ShadowType(GtkShadowType.OUT, "OUT");

    /**
     * The outline has a sunken 3D appearance.
     */
    public static final ShadowType ETCHED_IN = new ShadowType(GtkShadowType.ETCHED_IN, "ETCHED_IN");

    /**
     * The outline has a raised 3D appearance.
     */
    public static final ShadowType ETCHED_OUT = new ShadowType(GtkShadowType.ETCHED_OUT, "ETCHED_OUT");
}
