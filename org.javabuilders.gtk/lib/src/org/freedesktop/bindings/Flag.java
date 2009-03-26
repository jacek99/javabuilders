/*
 * Constant.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2007 Vreixo Formoso
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.bindings;

/**
 * Representation of an enum that is used to bit-pack option flags. A flag is
 * a Constant that can be bitwise OR'd with another flag of the same type
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.3
 */
/*
 * The presence of bitwise operations here is of some concern; given the
 * contract we have established to treat binary data from the native side as
 * opaque, I am not convinced that these instructions are safe. Vreixo has
 * contributed unit tests establishing some confidence, so we will keep it for
 * now. Move to JNI calls if any byte order problems are encountered.
 */
public abstract class Flag extends Constant
{

    protected Flag(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Helper method to enable the implementation of or() in subclasses.
     */
    protected final static Flag orTwoFlags(Flag a, Flag b) {
        if (a.getClass() != b.getClass()) {
            throw new IllegalArgumentException(
                    "Both arguments need to be an instances of the same concrete Flags class");
        }

        return Plumbing.flagFor(a.getClass(), a.ordinal | b.ordinal);
    }

    /**
     * Utility function to determine whether a Flags instance has the bit
     * embodied by <code>setting</code> set. An example of this in action is:
     * 
     * <pre>
     * WindowState s;
     * ...
     *     
     * if (s.contains(WindowState.STICKY)) {
     *     // get a cloth to clean up the mess
     * }
     * </pre>
     * 
     * You can only use this on instances of the same class!
     */
    public final boolean contains(Flag setting) {
        if (this.getClass() != setting.getClass()) {
            throw new IllegalArgumentException("Argument needs to be an instance of " + this.getClass());
        }

        return (this.ordinal & setting.ordinal) != 0;
    }
}
