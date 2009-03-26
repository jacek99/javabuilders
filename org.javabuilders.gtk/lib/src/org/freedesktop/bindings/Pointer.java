/*
 * Pointer.java
 *
 * Copyright (c) 2006-2008 Operational Dynamics Consulting Pty Ltd, and Others
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.bindings;

/**
 * A proxy object representing a native resource. Specifically, this is a
 * wrapper around a pointer.
 * 
 * <p>
 * <i><b>This is implementation, and you will never need to use it
 * directly</b></i>
 * 
 * @author Andrew Cowie
 * @author Vreixo Formoso
 * @since 4.0.9
 */
/*
 * And so the magic happens. This is where the pointer that this class proxies
 * is held. The JNI layer reaches up to this class and reads the pointer field
 * to get the address of the proxied GValue, etc. This is in its own library
 * as a means to keep the pointer handling isolated and cleanly installable.
 * Also useful given that Cairo doesn't actually depend on Glib.
 */
public abstract class Pointer
{
    /*
     * This is an opaque representation of a memory address. It's a Java long,
     * which means it's 64 bits wide which in turn means it can hold an
     * address on a 64 bit system, but any interpretation that the Java
     * language might assign to a long (ie, that it's signed) is meaningless
     * and incorrect! This is package public so that Plumbing can see it, and
     * final so that once constructed it is immutable.
     */
    final long pointer;

    /**
     * Create a new proxy object with the specified address as its pointer.
     * This it the top of the constructor chain.
     */
    protected Pointer(long pointer) {
        if (pointer == 0L) {
            throw new RuntimeException("Cannot make a Java proxy for the NULL pointer!");
        }
        this.pointer = pointer;
    }

    /**
     * Parent release function. Will be called by the Java garbage collector
     * when it invokes the finalizer, so this is the time to release
     * references and free memory on the C side.
     */
    protected abstract void release();

    /*
     * This is a placeholder to remind us of the cleanup actions that will be
     * necessary, irrespective of the finalizer technique used.
     */
    protected void finalize() {
        release();
    }

    public String toString() {
        if (Debug.MEMORY_MANAGEMENT) {
            StringBuilder result;
            result = new StringBuilder();
            result.append(Plumbing.toHexString(pointer));
            result.append("*");
            result.append(this.getClass().getName());

            return result.toString();
        } else {
            return this.getClass().getName();
        }
    }
}
