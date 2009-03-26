/*
 * Attribute.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd
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
 * A (single) specific formatting or font to be applied to a range of Text.
 * 
 * <p>
 * Examples of setting up Attributes include:
 * 
 * <pre>
 * attr = new StyleAttribute(Style.ITALIC);
 * </pre>
 * 
 * although many settings can be compunded by setting a FontDescription:
 * 
 * <pre>
 * desc = new FontDescription(&quot;DejaVu Serif, 9pt&quot;);
 * attr = new FontDescriptionAttribute(desc);
 * </pre>
 * 
 * in general it's one Attribute per characteristic. You then aggregate these
 * together in an AttributeList and then apply them to a Layout. See
 * {@link AttributeList} for an example of using these on discrete parts of
 * text.
 * 
 * <p>
 * <b>WARNING</b>:<br>
 * Once you've assigned an Attribute to a specific range of text in a given
 * Layout, do not attempt to reuse it.
 * 
 * <p>
 * <i> The different text attribute manipulations you can do are analogous to
 * those found on FontDescription and TextTag. Indeed, Pango's Attributes are
 * is the underlying mechanism powering TextView and Label's rendering of rich
 * markup.</i>
 * 
 * 
 * <p>
 * <i>Pango Attributes have an internal ugliness which is that each one needs
 * to be told the offsets of text it applies to. The problem is that these are
 * in terms of UTF-8 bytes, which not something we have access to from Java
 * (nor would we want to expose such in our public API). We take care of
 * setting the offsets properly when you call</i>
 * {@link #setIndices(Layout, int, int) setIndices()}<i>, but you have to have
 * already set the text into the Layout for us to be able to do so.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
/*
 * Apparently there is a facility for developers to extend Attributes, but we
 * haven't needed to expose that as yet. If someone needs this, then perhaps
 * the constructor here will need to change back to protected.
 */
public abstract class Attribute extends Boxed
{
    protected Attribute(long pointer) {
        super(pointer);
    }

    protected void release() {
        PangoAttribute.destroy(this);
    }

    /**
     * Given the text already in a Pango Layout, a starting position, and a
     * width, set the indexes of this Attribute accordingly.
     * <code>offset</code> and <code>width</code> are in terms of Java
     * characters. The result of this call is that the <var>start_index</var>
     * and <var>end_index</var> properties of the specified Attribute will be
     * set.
     * 
     * <p>
     * By default an Attribute added to an AttributeList covers all the text
     * in whatever that AttributeList is applied to. If that's not what you
     * want, you need to tell each Attribute what range it covers using this
     * method.
     * 
     * @since 4.0.10
     */
    public void setIndices(Layout layout, int offset, int width) {
        PangoAttributeOverride.setIndexes(this, layout, offset, width);
    }
}
