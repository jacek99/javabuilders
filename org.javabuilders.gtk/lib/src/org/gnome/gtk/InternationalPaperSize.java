/*
 * InternationalPaperSize.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * Constants for the ISO standard paper types, used everywhere in the world
 * except south, central, and northern North America.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
/*
 * TODO someone should do up "BusinessCard"
 */
public class InternationalPaperSize extends PaperSize
{
    protected InternationalPaperSize(String name) {
        super(name);
    }

    public static final InternationalPaperSize A6 = new InternationalPaperSize("iso_a6");

    public static final InternationalPaperSize A5 = new InternationalPaperSize("iso_a5");

    public static final InternationalPaperSize A3 = new InternationalPaperSize("iso_a3");

    public static final InternationalPaperSize A2 = new InternationalPaperSize("iso_a2");

    public static final InternationalPaperSize A1 = new InternationalPaperSize("iso_a1");

    /**
     * The largest standard paper size, and has an area of 1 m&#178;.
     */
    public static final InternationalPaperSize A0 = new InternationalPaperSize("iso_a0");
}
