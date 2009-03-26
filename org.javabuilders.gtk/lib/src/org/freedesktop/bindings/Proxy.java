/*
 * Proxy.java
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
 * A proxy object representing a native resource that has a unique
 * identity and can be looked up.
 *
 * <p>
 * <i><b>This is implementation, and you will never need to use it directly</b></i>
 * 
 * @author Andrew Cowie
 * @author Vreixo Formoso
 * @since 4.0.0
 */
public abstract class Proxy extends Pointer
{
    /**
     * Create a new proxy object with the specified address as its pointer.
     */
    protected Proxy(long pointer) {
        super(pointer);
        Plumbing.registerProxy(this);
    }

    /*
     * This is a placeholder to remind us of the cleanup actions that will be
     * necessary, irrespective of the finalizer technique used.
     */
    protected void finalize() {
        Plumbing.unregisterProxy(this);
        super.finalize();
    }
}
