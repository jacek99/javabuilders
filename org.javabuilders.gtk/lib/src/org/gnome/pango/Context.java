/*
 * Context.java
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

import org.freedesktop.cairo.FontOptions;
import org.gnome.glib.Object;

/**
 * The internal state used by Pango when rendering.
 * 
 * <p>
 * This is one of those cases where we are unfortunate that the class name
 * collides with the same name from another package and you could find
 * yourself using both in the same file. If you're working from a Layout and
 * need to use this, and given theses:
 * 
 * <pre>
 * import org.freedesktop.cairo.Context;
 * import org.freedesktop.cairo.FontOptions;
 * import org.gnome.pango.Layout;
 * import org.gnome.gtk.Widget;
 * </pre>
 * 
 * etc, you might be well off to do something like this in a
 * <code>Widget.ExposeEvent</code>:
 * 
 * <pre>
 * final Context cr;
 * final Layout layout;
 * final FontOptions config;
 * 
 * layout = new Layout(cr);
 * config = new FontOptions();
 * ...
 * 
 * layout.getContext().setFontOptions(config);
 * </pre>
 * 
 * which avoids you having to use the fully qualified
 * <code>org.gnome.pango.Context</code> name when you call Layout's
 * <code>getContext()</code>.
 * 
 * <p>
 * <i>You ordinarily do not need to use this, although you might want to know
 * that every time a Pango Layout is created a fresh Pango Context is done
 * up.</i>
 * 
 * @since 4.0.10
 */
public class Context extends Object
{
    protected Context(long pointer) {
        super(pointer);
    }

    /**
     * Set the configuration options that will be used by Cairo when doing the
     * actual rendering of font glyphs. This is how you control things like
     * which hinting methods being used.
     * 
     * @since 4.0.10
     */
    public void setFontOptions(FontOptions options) {
        PangoContext.setFontOptions(this, options);
    }
}
