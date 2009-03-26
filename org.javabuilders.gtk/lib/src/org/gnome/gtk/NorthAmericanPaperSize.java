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
 * Constants for the other standard paper types typically in use in Canada and
 * the United States, as well as Mexico, The Philippines, Chile, and Columbia.
 * 
 * <pre>
 * use = NorthAmericanPaperSize.LETTER;
 * </pre>
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
/*
 * TODO someone should do up "BusinessCard"
 */
public class NorthAmericanPaperSize extends PaperSize
{
    protected NorthAmericanPaperSize(String name) {
        super(name);
    }

    /**
     * A piece of paper which is 8.5 inch x 14 inch in size. This is sometimes
     * mistakenly referred to as "foolscap".
     */
    public static final NorthAmericanPaperSize LEGAL = new NorthAmericanPaperSize("na_legal");

    public static final NorthAmericanPaperSize EXECUTIVE = new NorthAmericanPaperSize("na_executive");
}
