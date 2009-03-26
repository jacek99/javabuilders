/*
 * PolicyType.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd, and Others
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
 * Constants to determines when a scrollbar will be visible.
 * 
 * @author Sebastian Mancke
 * @author Andrew Cowie
 * @since 4.0.3
 */
public final class PolicyType extends Constant
{
    private PolicyType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Force the scrollbar to be always visible.
     */
    public static final PolicyType ALWAYS = new PolicyType(GtkPolicyType.ALWAYS, "ALWAYS");

    /**
     * The scrollbar will appear when necessary and disappear when the child
     * widget is smaller than the allocated region.
     */
    public static final PolicyType AUTOMATIC = new PolicyType(GtkPolicyType.AUTOMATIC, "AUTOMATIC");

    /**
     * Prevent the scrollbar from ever appearing. You probably want to be
     * fairly careful about choosing this as the user will have no way to get
     * to the information that will likely be obscured as a result of turning
     * off panning.
     */
    public static final PolicyType NEVER = new PolicyType(GtkPolicyType.NEVER, "NEVER");
}
