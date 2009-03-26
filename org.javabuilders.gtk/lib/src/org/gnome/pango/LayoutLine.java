/*
 * LayoutLine.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
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
 * A line within a Layout. Once a Layout has been given text to lay out,
 * individual lines within it can be accessed via the methods on this class.
 * 
 * <pre>
 * lines = layout.getLinesReadonly();
 * 
 * x = leftMargin;
 * y = topMargin + rect.getAscent();
 * 
 * for (i = 0; i &lt; lines.length; i++) {
 *     rect = lines[i].getExtentsLogical();
 *     y += rect.getHeight();
 * 
 *     cr.moveTo(x, y);
 *     cr.showLayout(lines[i]);
 * }
 * </pre>
 * 
 * which of course is the hard way of doing:
 * 
 * <pre>
 * cr.showLayout(layout);
 * </pre>
 * 
 * but gives you control over the individual lines of the paragraph which is
 * necessary when doing paginated layout and worrying about available vertical
 * space.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
public final class LayoutLine extends Boxed
{
    protected LayoutLine(long pointer) {
        super(pointer);
        /*
         * FYI, we changed our .defs data to have the generated code wrapping
         * pango_layout_line_ref() to return void rather than returning the
         * PangoLayoutLine pointer. Otherwise we get an endless loop of
         * init(ling) -> ref() -> boxedFor() -> createPointer() -> init(long)
         * -> ref() which blows out the stack. The point is to increase the
         * ref count safely; we don't need the return value.
         */
        PangoLayoutLine.ref(this);
    }

    protected void release() {
        PangoLayoutLine.unref(this);
    }

    public Rectangle getExtentsInk() {
        final Rectangle result;

        result = new Rectangle();

        PangoLayoutLine.getExtents(this, result, null);

        return result;
    }

    /**
     * Get a Rectangle describing the logical extents calculated for this line
     * of rendered text.
     * 
     * <p>
     * The <code>x</code>, <code>y</code>, and <code>height</code> parameters
     * of these Rectangles are the same for each LayoutLine in a given Layout;
     * the <var>width</var> will be variable (unless you have evenly justified
     * the text, and even then last line of each paragraph will be less than
     * the <var>width</var> of the others).
     * 
     * <p>
     * <b>Note</b><br>
     * It is a common mistake to assume that the origin of the Pango
     * co-ordinate space is the top-right corner of an extents Rectangle. This
     * is not the case; the vertical origin is at the base line.
     * 
     * <p>
     * The {@link org.freedesktop.cairo.Context#showLayout(LayoutLine)
     * showLayout()} method that takes a LayoutLine starts its drawing by
     * placing its Rectangle's origin at the current Context point. So to
     * render consistently spaced lines of text you will need to move the
     * Context point <b>down</b> by the value of the ascent (which is the
     * negative of the <code>y</code> co-ordinate describing top of the
     * rectangle from the origin):
     * 
     * <pre>
     * rect = line.getExtentsLogical();
     * 
     * h = leftMargin;
     * v = topMargin + rect.getAscent() + lineNumber * rect.getHeight();
     * 
     * cr.moveTo(h, v);
     * cr.showLayout(line);
     * </pre>
     * 
     * @since 4.0.10
     */
    /*
     * TODO are x, y, height actually going to be the same for all LayoutLines
     * in a given Layout? That's seems to make sense, and is indeed what
     * testing observed. Could be wrong, of course.
     */
    public Rectangle getExtentsLogical() {
        final Rectangle result;

        result = new Rectangle();

        PangoLayoutLine.getExtents(this, null, result);

        return result;
    }
}
