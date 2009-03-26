/*
 * FontDescription.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2008      Vreixo Formoso
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.pango;

import org.gnome.glib.Boxed;

/**
 * An abstract description of a font. This class is used both to list what
 * fonts are available on the system, and also for specifying the
 * characteristics of a font you'd like to load.
 * 
 * <p>
 * The most common use is to indicate the font you want to use when drawing
 * text:
 * 
 * <pre>
 * cr = new Context(surface);
 * layout = new Layout(cr);
 * 
 * desc = new FontDescription(&quot;Monospace, 12&quot;);
 * layout.setFontDescription(desc);
 * </pre>
 * 
 * The {@link #FontDescription(String) constructor} has an extensive
 * discussion of the format of the textual descriptions you can use when
 * hunting for that perfect font to render with.
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.10
 */
public final class FontDescription extends Boxed
{
    protected FontDescription(long pointer) {
        super(pointer);
    }

    /**
     * Create a blank FontDescription with all fields unset.
     * 
     * <p>
     * {@link FontDescription#FontDescription(String) FontDescription(String)}
     * is usually a better way to initialize the FontDescription, specially if
     * you get the description from a {@link org.gnome.gtk.FontSelection
     * FontSelection} Widget.
     * 
     * @since 4.0.10
     */
    public FontDescription() {
        super(PangoFontDescription.createFontDescription());
    }

    /**
     * Create a FontDescription from a textual description. The form is:
     * 
     * <pre>
     * &quot;family[,] options size&quot;
     * </pre>
     * 
     * where:
     * 
     * <ul>
     * <li><code>family</code> is a comma separated list of one or more font
     * <var>families</var>. The trailing <code>','</code> on the family list
     * is optional but a good idea when a font family you're targeting
     * includes a space in its name;</li>
     * <li><code>options</code> is a whitespace separated list of words each
     * describing a <var>style</var>, <var>variant</var>, <var>weight</var>,
     * <var>stretch</var>, or <var>gravity</var>; and</li>
     * <li><code>size</code> is the vertical <var>size</var> of the font,
     * expressed in points You can use a suffix "px" to specify pixels
     * instead, although this is only applicable if you're drawing to the
     * screen or to an image backend.</li>
     * </ul>
     * 
     * <p>
     * All three are optional when using FontDescription to request a font; if
     * default values will be used for any parameters which are absent.
     * 
     * <p>
     * Font size is traditionally expressed in "points". Be aware, however,
     * that having a "10.5" point font doesn't actually mean that lines will
     * be precisely 10.5 points high. If you need to know their rendered size,
     * see a resultant LayoutLine's {@link LayoutLine#getExtentsLogical()
     * getExtentsLogical()}. You can instead follow the number with
     * <code>"px"</code> to interpret the size argument as an absolute number
     * of pixels .
     * 
     * <p>
     * Examples include:
     * 
     * <ul>
     * <li>
     * <p>
     * <code>"Sans Bold 24"</code><br>
     * Your system's sans-serif application font at a size that'll probably be
     * about right for a headline.
     * </p>
     * </li>
     * <li>
     * <p>
     * <code>"Monospace 10"</code><br>
     * Your system's terminal font. Note that it's <code>Monospace</code>, not
     * <code>Monospaced</code>!
     * </p>
     * </li>
     * <li>
     * <p>
     * <code>"Liberation Serif 12"</code><br>
     * These are an excellent series of fonts obtained for the global
     * community by Red Hat as libre replacements for the common, but
     * nevertheless encumbered non-free Arial, Courier, and Times fonts.
     * Liberation Serif in particular is ideal for output being sent to
     * printed paper.
     * </p>
     * </li>
     * <li>
     * <p>
     * <code>"DejaVu Sans, Book 9"</code><br>
     * An upgraded version of Bitstream Vera. DejaVu Sans is optimized as a
     * screen font (and hopefully is what you have set as your application
     * font in your<b> <code>gnome-appearance-properties</code></b>). "Book"
     * version is the term used in DejaVu for what other fonts call "Normal".
     * </p>
     * </li>
     * </ul>
     * 
     * @since 4.0.10
     */
    /*
     * FIXME The original author or this method ripped it off from the gtkdoc,
     * and it still reads poorly as a result. Feel free to take a hatchet to
     * this documentation if you have better information about anything
     * described here.
     */
    public FontDescription(String str) {
        super(PangoFontDescription.createFontDescriptionFromString(str));
    }

    protected void release() {
        PangoFontDescription.free(this);
    }

    public int hashCode() {
        return PangoFontDescription.hash(this);
    }

    public boolean equals(Object obj) {
        final FontDescription desc;

        if (!(obj instanceof FontDescription)) {
            return false;
        }
        desc = (FontDescription) obj;

        return PangoFontDescription.equal(this, desc);
    }

    /**
     * Specify the family (of related font styles) you wish to pick.
     * 
     * <p>
     * See the <code>families</code> argument to the FontDescription
     * {@link #FontDescription(String) constructor} for details.
     * 
     * @since 4.0.10
     */
    public void setFamily(String family) {
        PangoFontDescription.setFamily(this, family);
    }

    /**
     * The family the font that this FontDescription represents belongs to.
     * Returns <code>null</code> if unset.
     * 
     * @since 4.0.10
     */
    public String getFamily() {
        return PangoFontDescription.getFamily(this);
    }

    /**
     * Set the style of the font you're looking for. This is
     * {@link Style#ITALIC ITALIC} and friends.
     * 
     * <p>
     * If you've worked with Textview then you might have seen this before as
     * TextTag's {@link org.gnome.gtk.TextTag#setStyle(Style) setStyle()}.
     * Same factor.
     * 
     * @since 4.0.10
     */
    public void setStyle(Style style) {
        PangoFontDescription.setStyle(this, style);
    }

    /**
     * Get the slant style of the font.
     * 
     * @since 4.0.10
     */
    public Style getStyle() {
        return PangoFontDescription.getStyle(this);
    }

    /**
     * Set the capitalization variant of the Font. This is where
     * {@link Variant#SMALL_CAPS SMALL_CAPS} lives. Normally you'll leave it
     * alone; not surprisingly the default is {@link Variant#NORMAL NORMAL}.
     * 
     * @since 4.0.10
     */
    public void setVariant(Variant variant) {
        PangoFontDescription.setVariant(this, variant);
    }

    /**
     * Get the capitalization variant of the font.
     * 
     * @since 4.0.10
     */
    public Variant getVariant() {
        return PangoFontDescription.getVariant(this);
    }

    /**
     * Indicate how bold or light the font should be.
     * 
     * @since 4.0.10
     */
    public void setWeight(Weight weight) {
        PangoFontDescription.setWeight(this, weight);
    }

    /**
     * Get the weight of the font.
     * 
     * @since 4.0.10
     */
    public Weight getWeight() {
        return PangoFontDescription.getWeight(this);
    }

    /**
     * Get the size of this font, in device units.
     * 
     * <p>
     * Note that this does <i>not</i> imply how high a line in this font will
     * be; that is up to the Layout doing the rendering. To find out the
     * height of a rendered line, use LayoutLine's
     * {@link LayoutLine#getExtentsLogical() getExtentsLogical()}.
     * 
     * @since 4.0.10
     */
    public double getSize() {
        return PangoFontDescription.getSize(this) / Pango.SCALE;
    }

    /**
     * Set the size of the font you are requesting. Size is expressed in
     * points.
     * 
     * <p>
     * Since you can indicate the size you want when constructing with a
     * textual description, you don't need this too often.
     * 
     * @since 4.0.10
     */
    public void setSize(double size) {
        PangoFontDescription.setSize(this, (int) (size * Pango.SCALE));
    }

    /**
     * Set the size in raw device units (ie pixels if working on screen).
     * 
     * <p>
     * This corresponds to providing a size in a textual description such as
     * <code>"Sans, 10px"</code> instead of <code>"Sans, 10"</code>; since you
     * can specify it there you problably don't need this much.
     * 
     * @since <span style="color: red">Unstable</span>
     */
    /*
     * TODO Why WOULD anyone would need this? FIXME what happens in the
     * presence of scaling? What is the real relationship to setSize() given
     * that Cairo is doing all the work?
     */
    public void setSizeAbsolute(double size) {
        PangoFontDescription.setAbsoluteSize(this, size * Pango.SCALE);
    }
}
