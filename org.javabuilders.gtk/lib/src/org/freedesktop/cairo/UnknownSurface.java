/*
 * UnknownSurface.java
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

/**
 * Surface type for internal backends. These are not made public by the Cairo
 * library, so we cannot create a more appropriately typed class.
 * 
 * @author Andrew Cowie
 */
/*
 * In java-gnome if we offer accurate fully derived types, and normally it is
 * an error if we can't do so (ie, we haven't coverage for something yet).
 * Cairo has a number of cases where internal types are exposed (notably
 * MetaSurface for vector backends) for which there is no public
 * identification. An instance of this class is created in such cases. Be
 * warned that getting one of these is NOT a workaround for failing to create
 * a proper Proxy subclass for something that IS public.
 */
class UnknownSurface extends Surface
{
    protected UnknownSurface(long pointer) {
        super(pointer);
    }
}
