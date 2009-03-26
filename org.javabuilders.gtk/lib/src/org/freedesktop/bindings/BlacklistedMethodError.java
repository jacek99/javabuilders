/*
 * BlacklistedMethodError.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.bindings;

/**
 * Thrown by a generated method which is actually (dangerously) incomplete due
 * to the fact that information about one or more native types was lacking
 * when the bindings were generated. This Error is made visible as a last
 * ditch programatic measure to draw attention to the fact that this method
 * cannot be called; hopefully the {@link FIXME} in the method signature would
 * catch your attention first. This represents a wonderful opportunity for you
 * to contribtue to the java-gnome bindings.
 * 
 * <p>
 * hackers: don't even <b>think</b> about exposing a method that throws this
 * in java-gnome's public API.
 * 
 * @author Andrew Cowie
 * @since 4.0.3
 */
public final class BlacklistedMethodError extends Error
{
    private static final long serialVersionUID = 1L;

    public BlacklistedMethodError(String gType) {
        super("\nNo information about " + gType);
    }

}
