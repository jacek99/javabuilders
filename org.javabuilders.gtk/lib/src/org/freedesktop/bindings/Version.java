/*
 * Version.java
 *
 * Copyright (c) 2006-2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 * 
 */
package org.freedesktop.bindings;

/**
 * Version constants for the java-gnome library. The top level
 * <code>.config</code> Makefile fragment depends on this file and the
 * <code>./configure</code> of Equivalence extracts the values for the library
 * API version and (pending) release version from the constants defined
 * herein.
 * 
 * @author Andrew Cowie
 * @since 4.0.2
 */
/*
 * This data was formerly in org.gnome.gtk.Gtk; moved here so that hacking on
 * that file's code does not require re-configuring every time it is saved.
 * Later renamed from org.gnome.gtk.Version
 * 
 * We now go to the trouble of wrapping these in getters because otherwise
 * foreign classes will inline these values, making propegating the changes a
 * nightmare.
 */
public final class Version
{
    private static final String APIVERSION = "4.0";

    private static final String VERSION = "4.0.10";

    /**
     * The full (usually three digit) version of java-gnome. This is used in a
     * number of the examples and screenshots, but far more critically it is
     * used to precisely identify which version of the shared library with the
     * native code is to be loaded.
     */
    public static final String getVersion() {
        return VERSION;
    }

    /**
     * The API version (also known as "SLOT") of the java-gnome library.
     * Notably, this is used to name the <code>.jar</code> file.
     */
    public static final String getAPI() {
        return APIVERSION;
    }

    private Version() {}
}
