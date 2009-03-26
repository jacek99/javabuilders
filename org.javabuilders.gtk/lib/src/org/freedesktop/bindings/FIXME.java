/*
 * FIXME.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.bindings;

/**
 * A type for which we had no data at bindings generation time! In order to
 * fix this, you will need to either
 * <ol>
 * <li>Add an appropriate (define...) block to define the type in a new .defs
 * file
 * <li>Add a hardcoded type in Thing's <code>static {}</code> block (probably
 * a FundamentalThing), or
 * <li>worst case, you will have to improve the architecture of java-gnome as
 * a whole to deal with the situation represented by this parameter or return
 * type.
 * </ol>
 * This is marked as a public type, but clearly this is an artifact of the
 * code generation process and is not for developer consumption.
 * 
 * @author Andrew Cowie
 * @since 4.0.3
 */
public final class FIXME
{
    private FIXME() {}
}
