/*
 * Plumbing.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.cairo;

import org.gnome.gdk.Gdk;

public abstract class Plumbing extends org.freedesktop.bindings.Plumbing
{
    protected Plumbing() {}

    /*
     * Expose access to the global GDK lock.
     */
    /*
     * TODO I'm not sure we need this. In fact, I rather expect that this is
     * in the way, but we'd have to make the synchronized block output by the
     * code generator conditional.
     */
    protected static final java.lang.Object lock;

    static {
        lock = Gdk.lock;

        assert (Status.SUCCESS != null);
    }

    protected static Entity entityFor(Class<?> type, long pointer) {
        Entity obj;

        obj = (Entity) org.freedesktop.bindings.Plumbing.instanceFor(pointer);

        if (obj != null) {
            return obj;
        } else {
            if (type == Surface.class) {
                obj = createSurface(pointer);
            } else if (type == Pattern.class) {
                obj = createPattern(pointer);
            }
            return obj;
        }
    }

    /*
     * Similar to createProxy() in the superclass, but in Cairo's case we are
     * forced to go to native in order to navigate the type "forest". Unlike
     * the g_type_name() based mechanism in org.gnome.glib, we only know the
     * abstract class as the type argument; however, C side, cairo allows us
     * to look up the fully qualified type given the abstract and so we can
     * then map that to the appropriate Java Proxy.
     */

    private static native Entity createSurface(long pointer);

    private static native Entity createPattern(long pointer);
}
