/*
 * Object.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.atk;

/**
 * FIXME.
 * 
 * @author Andrew Cowie
 * @since 4.0.3
 */
/*
 * This class needed special attention due to the usual issues arising from
 * using Object as the class name.
 */
public class Object extends org.gnome.glib.Object
{
    protected Object(long pointer) {
        super(pointer);
    }
}
