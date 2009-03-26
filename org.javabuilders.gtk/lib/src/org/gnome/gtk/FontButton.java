/*
 * FontButton.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/*
 * FIXME this is a placeholder stub for what will become the public API for
 * this type. Replace this comment with appropriate javadoc including author
 * and since tags. Note that the class may need to be made abstract, implement
 * interfaces, or even have its parent changed. No API stability guarantees
 * are made about this class until it has been reviewed by a hacker and this
 * comment has been replaced.
 */
public class FontButton extends Button
{
    protected FontButton(long pointer) {
        super(pointer);
    }

    /**
     * Construct a FontButton.
     * 
     * @since 4.0.10
     */
    public FontButton() {
        super(GtkFontButton.createFontButton());
    }

    /**
     * The signal emitted when a font is selected.
     * 
     * @author Andrew Cowie
     * @since 4.0.10
     */
    public interface FontSet extends GtkFontButton.FontSetSignal
    {
        void onFontSet(FontButton source);
    }

    /**
     * Hook up a <code>FontButton.FontSet</code> handler.
     * 
     * @since 4.0.10
     */
    public void connect(FontButton.FontSet handler) {
        GtkFontButton.connect(this, handler, false);
    }

    /**
     * Get the name of the font currently selected by this FontButton.
     * 
     * @since 4.0.10
     */
    public String getFontName() {
        return GtkFontButton.getFontName(this);
    }
}
