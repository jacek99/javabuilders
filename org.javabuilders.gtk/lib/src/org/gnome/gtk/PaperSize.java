/*
 * PaperSize.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import org.gnome.glib.Boxed;

/**
 * Representations of different sizes of paper used in printing operations.
 * 
 * <p>
 * Usage is straight forward:
 * 
 * <pre>
 * paper = PaperSize.A4;
 * width = paper.getWidth(Unit.MM);
 * height = paper.getHeight(Unit.MM);
 * </pre>
 * 
 * <p>
 * The &#8730;2 relationships between the various ISO sizes are shown in the
 * following diagram (the imperialist paper sizes are included for
 * comparison):
 * 
 * <p>
 * <a href="http://en.wikipedia.org/wiki/Paper_size"><img src="http://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/A_size_illustration2_with_letter_and_legal.svg/439px-A_size_illustration2_with_letter_and_legal.svg.png"
 * border="0" width="439" height="599"></a><br>
 * 
 * <p>
 * Constants for the two common page sizes are here. If you need one of the
 * more obscure cases, see subclasses {@link InternationalPaperSize} or
 * {@link NorthAmericanPaperSize}.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 * @see <a href="http://en.wikipedia.org/wiki/Paper_size">Wikipedia's article
 *      on paper sizes</a>
 */
/*
 * This class has the two most common sizes, with the rest delegated off to
 * subclasses. It would seem that loading a paper size causes a significant
 * amount of initializing to go on in GTK, and it seems a bit silly to do all
 * of that for each weirdo size when you're probably just here to get A4 or
 * LETTER. If we're wrong about the cost (ie, if they're all already loaded by
 * default) then the cost of having all the constants here would be far less.
 */
public class PaperSize extends Boxed
{
    protected PaperSize(long pointer) {
        super(pointer);
    }

    protected final void release() {
        GtkPaperSize.free(this);
    }

    /**
     * Standard sheet size in use worldwide. Corresponds to a piece of paper
     * 210 mm x 297 mm. See {@link InternationalPaperSize} for other ISO paper
     * size constants.
     */
    public static final PaperSize A4 = new PaperSize("iso_a4");

    /**
     * Standard sheet size used in North America. Corresponds to a piece of
     * paper which is roughly 216 mm x 279 mm (which is 8.5 inch x 11 inch in
     * pre-SI units). See {@link NorthAmericanPaperSize} for other imperialist
     * paper sizes.
     */
    public static final PaperSize LETTER = new PaperSize("na_letter");

    private static PaperSize platform;

    /**
     * Returns the PaperSize corresponding to the default in effect for the
     * current locale.
     */
    /*
     * FUTURE it would be nice if this returned one of the already initialized
     * constants above, although Boxeds aren't really something you can
     * compare by identity, and in any case finding out what the default is
     * implies creating a boxed anyway.
     */
    public static PaperSize getDefault() {
        if (platform == null) {
            platform = new PaperSize(null);
        }
        return platform;
    }

    /**
     * Get a PaperSize corresponding to a PWG 5101.1-2002 paper size
     * descriptor.
     * 
     * <p>
     * <b>WARNING</b><br>
     * <i>You're on your own to provide a correct descriptor if you use
     * this!</i>
     * 
     * @since 4.0.10
     */
    protected PaperSize(String name) {
        super(GtkPaperSize.createPaperSize(name));
    }

    /**
     * Get the width of a piece of paper in the specified units. You can call
     * this as:
     * 
     * <pre>
     * w = paper.getWidth(Unit.MM);
     * </pre>
     * 
     * or
     * 
     * <pre>
     * w = paper.getWidth(Unit.POINTS);
     * </pre>
     * 
     * depending on your need.
     * 
     * 
     * @since 4.0.10
     */
    public double getWidth(Unit in) {
        return GtkPaperSize.getWidth(this, in);
    }

    /**
     * Get the height of a piece of paper in the specified units.
     * 
     * @since 4.0.10
     */
    public double getHeight(Unit in) {
        return GtkPaperSize.getHeight(this, in);
    }

    public boolean equals(java.lang.Object obj) {
        final PaperSize size;

        if (!(obj instanceof PaperSize)) {
            return false;
        }
        size = (PaperSize) obj;

        return GtkPaperSize.isEqual(this, size);
    }

    public String getDisplayName() {
        return GtkPaperSize.getDisplayName(this);
    }

    public String toString() {
        return this.getClass().getSimpleName() + "'s " + getDisplayName();
    }
}
