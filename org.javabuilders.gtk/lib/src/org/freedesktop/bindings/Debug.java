/*
 * Debug.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" See the LICENCE file for the terms governing usage and
 * redistribution.
 */

package org.freedesktop.bindings;

/**
 * Conditionally control internal aspects of the java-gnome libraries. Much as
 * conditional compilation is frowned upon, sometimes you have to switch
 * things on or off. <b>This is not for ./configure time selection of
 * features!</b>
 * 
 * <p>
 * You use these as follows:
 * 
 * <pre>
 * if (Debug.WALK) {
 *     // on the wild side
 * }
 * </pre>
 * 
 * Remember that you <b>must</b> <code>make clean</code> and rebuild the
 * <i>entire</i> project if you change any value in here, otherwise classes
 * that are already compiled with these constants will not be affected.
 * 
 * @author Andrew Cowie
 * @since 4.0.2
 */
public final class Debug
{
    private Debug() {}

    /**
     * Do you want debug output every time a Proxy object is finalized?
     */
    /*
     * FIXME for the moment, this has no effect on the JNI side. It would be
     * much better if we could turn this into a compile time constant in a
     * header file. For now, the twin of this is in bindings_java.h
     */
    public static final boolean MEMORY_MANAGEMENT = false;
}
