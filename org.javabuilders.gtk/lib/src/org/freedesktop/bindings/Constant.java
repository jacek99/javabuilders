/*
 * Constant.java
 *
 * Copyright (c) 2006-2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.bindings;

/**
 * Representation of enumerated constants. Enums are very fundamental in most
 * libraries but are ordinal integers handled by the compiler as constants;
 * they are <i>not</i> passed around by pointer. For that, we have the
 * {@link Proxy} class.
 * 
 * <p>
 * Note that this has nothing to do with the <code>enum</code> mechanism
 * introduced in Java 1.5.
 * 
 * @author Andrew Cowie
 * @since 4.0.0
 */
/*
 * Note for GLib based libraries: these are our representation of integer
 * constants and are not org.gnome.glib.Values instances. So for the case of a
 * GValue containing an G_TYPE_ENUM, we have extract it from the Value; see
 * getEnum() in org.gnome.glib.GValue
 */
public abstract class Constant
{

    /*
     * This is package visible so that Plumbing can see it, and final so that
     * only the constructor here can set it.
     */
    final int ordinal;

    /**
     * A text version of the name of the constant representing this enum. This
     * is mostly registered so that if the developer has need to output
     * <i>which</i> particular constant they are using while debugging, they
     * can do so.
     * 
     * <p>
     * The pattern of passing a name up when constructing constants as an
     * explicit debugging measure is inspired by "Constant Objects" in Robert
     * Simmons, Jr's <i>Hardcore Java<i> (O'Reilly, 2004), page 170.
     */
    /*
     * In GTK, enums are frequently registered as <code>GEnumValues</code>
     * with <code>g_enum_register_static()</code>. This, however, is not done
     * consistently, and cannot be relied upon.
     */
    final String nickname;

    /**
     * The order is ordinal, nickname so that the code completion comes first
     * and copying the name of the constant to the nickname becomes a trivial
     * exercise, as opposed to having to figure out what the nickname should
     * be before you've completed from the generated code.
     */
    protected Constant(int ordinal, String nickname) {
        this.ordinal = ordinal;
        this.nickname = nickname;

        Plumbing.registerConstant(this);
    }

    /**
     * Return the name of the Constant class and the name of the constant
     * itself. For example,
     * 
     * <pre>
     * WindowType.POPUP
     * </pre>
     * 
     * @since 4.0.1
     */
    public String toString() {
        return this.getClass().getSimpleName() + "." + nickname;
    }
}
