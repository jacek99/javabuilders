/*
 * Entity.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.cairo;

import org.freedesktop.bindings.Proxy;

/**
 * Opaque objects in the Cairo Graphics library.
 * 
 * <p>
 * <i>This is just a base class to provide common functionality to various
 * classes, notably checking status in a uniform way.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
abstract class Entity extends Proxy
{
    protected Entity(long pointer) {
        super(pointer);
    }

    /**
     * Check that the internal state of Cairo is ok, throwing an exception
     * otherwise. This is to be called by the bindings after every operation.
     */
    /*
     * FUTURE It might be nice to find a way to get the code generator to
     * insert this into the JNI code automatically. That's non trivial, if for
     * no other reason than different parts of Cairo (ie Surface, Font) use
     * different status checking functions. It doesn't hurt to have it here.
     */
    protected void checkStatus(Status status) {
        if (status != Status.SUCCESS) {
            throw new FatalError(status.toString() + "\n" + CairoContext.statusToString(status));
        }
    }
}
