/*
 * Layout.java
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

import org.gnome.glib.Object;
import org.gnome.gtk.Widget;

/**
 * A Layout represents a paragraph of text, together with its attributes.
 * 
 * <p>
 * Drawing is done with Cairo; you get a Layout by passing the Cairo drawing
 * Context you're currently working in to the constructor. If you're drawing a
 * Widget, you'll be doing so in a <code>Widget.ExposeEvent</code> handler
 * where you'll typically see:
 * 
 * <pre>
 * w.connect(new Widget.ExposeEvent() {
 *     public boolean onExposeEvent(Widget source, EventExpose event) {
 *         final Context cr;
 *         final Layout layout;
 * 
 *         cr = new Context(source.getWindow());
 *         layout = new Layout(cr);
 * 
 *         // use layout to lay out the text you wish to draw
 * 
 *         cr.showLayout(layout);
 *     }
 * });
 * </pre>
 * 
 * <p>
 * Layout can indeed layout multiple paragraphs of text, but usually you need
 * more control over positioning, so most of the time you have to work a
 * paragraph at a time. You can, however, avoid creating a new Layout each
 * time by calling {@link #setText(String) setText()} with the new paragraph
 * text.
 * 
 * <p>
 * <i>A single paragraph at a time is how PangoLayout was designed and
 * intended to be used.</i>
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.10
 */
public class Layout extends Object
{
    protected Layout(long pointer) {
        super(pointer);
    }

    /**
     * Create a new Layout configured to draw using the given Cairo Context
     * backend.
     * 
     * <p>
     * This Layout can be used to set up the text to draw and its properties.
     * 
     * <p>
     * To actually draw the text, you call the
     * {@link org.freedesktop.cairo.Context#showLayout(Layout) showLayout()}
     * method on the Cairo Context you specified when constructing this
     * Layout.
     * 
     * <p>
     * Note that if you change the transformation or target Surface for the
     * Context, you <b>must</b> call
     * {@link org.freedesktop.cairo.Context#updateLayout(Layout)
     * updateLayout()} to signal this Layout that changes have taken place.
     * 
     * @since 4.0.10
     */
    public Layout(org.freedesktop.cairo.Context context) {
        super(PangoLayout.createLayoutFromCairo(context));
    }

    /**
     * Sets the text of the Layout. This is the text that will be drawn.
     * 
     * <p>
     * If you wish to pass text enhanced with Pango Markup, use
     * {@link #setMarkup(String) setMarkup()} instead.
     * 
     * <p>
     * Alternately, you can use this <code>setText()</code> method to set the
     * full textual content of the Layout and then build up a set of
     * Attributes describing which formats you wish to be in effect across
     * what ranges. You assemble this information in an AttributeList and then
     * apply it to this Layout by calling
     * {@link #setAttributes(AttributeList) setAttributes()}.
     * 
     * @since 4.0.10
     */
    public void setText(String text) {
        PangoLayout.setText(this, text, -1);
    }

    /**
     * Set the text of this Layout. Its format is specified using Pango Markup
     * format [TODO we need to document pango markup somewhere].
     * 
     * If you're just passing in normal straight-forward unformatted text, use
     * {@link #setText(String) setText()}.
     * 
     * @since 4.0.10
     */
    public void setMarkup(String markup) {
        PangoLayout.setMarkup(this, markup, -1);
    }

    /**
     * Get the width of the Layout. This is the width of the layout text,
     * taking its format into account (for example, the size of the Font will
     * influence the final size!).
     * 
     * <p>
     * Note that this is not necessarily related with the line wrap width you
     * set with {@link #setWidth(double) setWidth()} method.
     * 
     * @since 4.0.10
     */
    public double getSizeWidth() {
        int[] width = new int[1];
        PangoLayout.getSize(this, width, null);
        return width[0] / Pango.SCALE;
    }

    /**
     * Get the height of the Layout. This is the height of the layout text,
     * taking its format into account (for example, the size of the Font will
     * influence the final size!).
     * 
     * @since 4.0.10
     */
    public double getSizeHeight() {
        int[] height = new int[1];
        PangoLayout.getSize(this, null, height);
        return height[0] / Pango.SCALE;
    }

    /**
     * Get the width, in pixels, of the Layout. This is suitable, together
     * with {@link #getPixelHeight() getPixelHeight()}, to pass to a Widget's
     * {@link Widget#setSizeRequest(int, int) setSizeRequest()} in order to
     * ensure enough space is available for the text to actually be shown.
     * 
     * @since 4.0.10
     */
    public int getPixelWidth() {
        int[] width = new int[1];
        PangoLayout.getPixelSize(this, width, null);
        return width[0];
    }

    /**
     * Get the height, in pixels, of the Layout. See the corresponding method
     * {@link #getPixelWidth() getPixelWidth()} for details.
     * 
     * @since 4.0.10
     */
    public int getPixelHeight() {
        int[] height = new int[1];
        PangoLayout.getPixelSize(this, null, height);
        return height[0];
    }

    /**
     * Sets the default FontDescription for the Layout.
     * 
     * @since 4.0.10
     */
    /*
     * TODO the upstream documentation says
     * "If none is set, then the FontDescription from the Layout's Context is used."
     * This means what, exactly? Where does that get set?
     */
    public void setFontDescription(FontDescription desc) {
        PangoLayout.setFontDescription(this, desc);
    }

    /**
     * Set the width of the Layout to be used for word-wrapping purposes.
     * 
     * <p>
     * This will determine the positioning of the text and how the lines are
     * wrapped. If a text line is greater than the given size, it is split
     * into several lines.
     * 
     * @param width
     *            The width in Cairo terms (typically pixels if you're drawing
     *            a Widget or image, or points if you're drawing a PDF).
     * @since 4.0.10
     */
    public void setWidth(double width) {
        PangoLayout.setWidth(this, (int) (width * Pango.SCALE));
    }

    /**
     * Sets whether each complete line should be stretched to fill the entire
     * width of the layout.
     * 
     * <p>
     * This stretching is typically done by adding whitespace, but for some
     * scripts (such as Arabic), the justification may be done in more complex
     * ways, like extending the characters.
     * 
     * @since 4.0.10
     */
    public void setJustify(boolean justify) {
        PangoLayout.setJustify(this, justify);
    }

    /**
     * Gets whether each complete line should be stretched to fill the entire
     * width of the Layout.
     * 
     * @since 4.0.10
     */
    public boolean getJustify() {
        return PangoLayout.getJustify(this);
    }

    /**
     * Sets the <var>alignment</var> for the Layout. This controls how partial
     * lines are positioned within the available horizontal space.
     * 
     * <p>
     * Note that contrary to what is commonly expressed in the user interface
     * of common tools like word processors, justification is not an alignment
     * type. If you wish to have equally wide lines, see
     * {@link #setJustify(boolean) setJustify()}. Alignment remains important
     * as it controls where indentation is relative to and what to do with the
     * last line of each paragraph.
     * 
     * @since 4.0.10
     */
    public void setAlignment(Alignment alignment) {
        PangoLayout.setAlignment(this, alignment);
    }

    /**
     * Get the <var>alignment</var> of the Layout.
     * 
     * @since 4.0.10
     */
    public Alignment getAlignment() {
        return PangoLayout.getAlignment(this);
    }

    /**
     * Sets the width by which to indent the first line of each paragraph. A
     * negative value of indent will produce a hanging indentation. That is,
     * the first line will have the full width, and subsequent lines will be
     * indented by the absolute value of indent.
     * 
     * <p>
     * Note that the indent is relative to the Alignment of the text, if the
     * text is aligned to the right, the indent is computed from there.
     * 
     * @since 4.0.10
     */
    public void setIndent(double indent) {
        PangoLayout.setIndent(this, (int) (indent * Pango.SCALE));
    }

    /**
     * Get the paragraph indent of this Layout. It'll be <code>0</code> unless
     * you called {@link #setIndent(double) setIndent()} to change it.
     * 
     * @since 4.0.10
     */
    public double getIndent() {
        final int units;
        units = PangoLayout.getIndent(this);
        return units / Pango.SCALE;
    }

    /**
     * Get the LayoutLine representing an individual line of text as have been
     * laid out by this Layout. The <code>index</code> number is
     * <code>0</code> origin.
     * 
     * @since 4.0.10
     */
    public LayoutLine getLine(int index) {
        return PangoLayout.getLine(this, index);
    }

    /**
     * Get the number of lines that this Layout has been laid out into. It
     * will always be at least <code>1</code> (and indeed will only be
     * <code>1</code> unless you have called {@link #setWidth(double)
     * setWidth()} and supplied sufficient text that the Layout has wrapped it
     * into a multi-line paragraph).
     * 
     * @since 4.0.10
     */
    public int getLineCount() {
        return PangoLayout.getLineCount(this);
    }

    /**
     * Get the LayoutLine representing an individual line of text as has been
     * laid out by this Layout. The <code>index</code> number ranges from
     * <code>0</code> origin to {@link #getLineCount() getLineCount()}
     * <code> - 1</code>.
     * 
     * <p>
     * This method is optimized for the common case where you are not changing
     * the characteristics of the individual glyphs in the line, and should be
     * used in preference to {@link #getLine(int) getLine()}.
     * 
     * @since 4.0.10
     */
    public LayoutLine getLineReadonly(int index) {
        return PangoLayout.getLineReadonly(this, index);
    }

    /**
     * Get an array of LayoutLines representing the individual lines of text
     * as have been laid out by this Layout.
     * 
     * @since 4.0.10
     */
    public LayoutLine[] getLines() {
        return PangoLayout.getLines(this);
    }

    /**
     * Get an array of LayoutLines representing the individual lines of text
     * as have been laid out by this Layout. This form of
     * <code>getLines()</code> uses faster code paths optimized for the usual
     * case that you are not using Pango to modify the text in the lines, but
     * are instead planning to go directly to rendering them.
     * 
     * @since 4.0.10
     */
    public LayoutLine[] getLinesReadonly() {
        return PangoLayout.getLinesReadonly(this);
    }

    public Rectangle getExtentsInk() {
        final Rectangle result;

        result = new Rectangle();

        PangoLayout.getExtents(this, result, null);

        return result;
    }

    /**
     * Get the Rectangle enclosing the entire Layout as it will be rendered.
     * 
     * @since 4.0.10
     */
    public Rectangle getExtentsLogical() {
        final Rectangle result;

        result = new Rectangle();

        PangoLayout.getExtents(this, null, result);

        return result;
    }

    /**
     * Get the vertical position of the baseline in the first line of this
     * Layout.
     * 
     * <p>
     * If you're laying out lines individually, you almost certainly want to
     * get the extents of each LayoutLine and then use that Rectangle's
     * {@link Rectangle#getAscent() getAscent()} instead.
     * 
     * @since 4.0.10
     */
    public double getBaseline() {
        return PangoLayout.getBaseline(this) / Pango.SCALE;
    }

    /**
     * Return the <b>Pango</b> Context powering this Layout.
     * 
     * <p>
     * Since you probably constructed this Layout with a <i>Cairo</i> Context,
     * you're going to end up with some messy fully qualified names if you
     * need to use this. You might just want to use the type implicitly:
     * 
     * <pre>
     * layout.getContext().setFontOptions(options);
     * </pre>
     * 
     * so that you can keep the rest of the uses of the bare word
     * <code>Context</code> as the Cairo one already imported.
     * 
     * <p>
     * Note that having made a call like the one shown, you need to either
     * call <code>contextChanged()</code> or <code>setText()</code> to cause
     * the Layout to take notice.
     * 
     * @since 4.0.10
     */
    public Context getContext() {
        return PangoLayout.getContext(this);
    }

    /**
     * Sets the sequence of Attributes describing the markup you wish to have
     * in play. This indices of all the Attributes need to have been set after
     * the text in this Layout was established via {@link #setText(String)
     * setText()}.
     * 
     * <p>
     * See {@link AttributeList} for a detailed example of using this method
     * to indicate formatting.
     * 
     * @since 4.0.10
     */
    public void setAttributes(AttributeList list) {
        if (list.isUsed()) {
            throw new IllegalStateException("AttributeList has already been employed");
        }

        /*
         * We now go through the exercise of setting the start and end indexes
         * of the individual Attributes relative to the actual UTF-8 text
         * being rendered. Until this point we kept note of character offsets
         * here on the Java side.
         */

        for (Attribute attr : list.getAttributes()) {
            PangoAttributeOverride.setIndexes(attr, this, attr.getOffset(), attr.getWidth());
            PangoAttrList.insert(list, attr);
        }

        /*
         * Now we are caught up to the state that Pango would expect.
         */

        PangoLayout.setAttributes(this, list);

        list.markUsed();
    }

    /**
     * Get the spacing between lines of a rendered paragraph.
     * 
     * @since 4.0.11
     */
    public double getSpacing() {
        return PangoLayout.getSpacing(this) / Pango.SCALE;
    }

    /**
     * Set the spacing that will occur between lines of a rendered paragraph.
     * 
     * <p>
     * Obviously this will only have any effect if you are rendering complete
     * Layouts as 2D shapes via
     * {@link org.freedesktop.cairo.Context#showLayout(Layout) showLayout()}.
     * If you are working instead with individual LayoutLines then it's up to
     * you how much spacing you pad between lines as you draw them.
     * 
     * <p>
     * The default is <code>0</code>.
     * 
     * @since 4.0.11
     */
    public void setSpacing(double between) {
        PangoLayout.setSpacing(this, (int) (between * Pango.SCALE));
    }

    /**
     * Set the line wrapping mode (if set, then lines turn into paragraphs).
     * The <var>width</var> must be set with {@link #setWidth(double)
     * setWidth()} for this to work.
     * 
     * <p>
     * The default is {@link WrapMode#WORD WORD} so you shouldn't need to call
     * this. In any case, wrapping is turned on by setting a width greater
     * than <code>-1</code>, not by this method.
     * 
     * @since 4.0.11
     */
    public void setWrapMode(WrapMode mode) {
        PangoLayout.setWrap(this, mode);
    }

    /**
     * Indicate that the Layout is not to do paragraph breaks on encountering
     * LINE_SEPARATOR characters.
     * 
     * <p>
     * If this is turned on then when the Layout encounters newlines they will
     * be replaced with an symbol marking the position of the newline. This
     * allows you to create a user interface that edits the newlines
     * explicitly on a single line.
     * 
     * <p>
     * The default is <code>false</code>, obviously.
     * 
     * <p>
     * Note that word wrapping is not affected by this. This is single
     * <i>paragraph</i> mode, not single <i>line</i> mode.
     * 
     * @since 4.0.11
     */
    public void setSingleParagraphMode(boolean setting) {
        PangoLayout.setSingleParagraphMode(this, setting);
    }
}
