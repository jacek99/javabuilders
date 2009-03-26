/*
 * FontOptions.java
 *
 * Copyright (c) 2009 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.cairo;

/**
 * FIXME Work in progress
 * 
 * @author Andrew Cowie
 * @since <span style="color: red">Unstable</span>
 */
/*
 * Thanks to Behdad Esfahbod for explaining the need for turning hinting off.
 */
public class FontOptions extends Entity
{
    protected FontOptions(long pointer) {
        super(pointer);
    }

    /**
     * Create a FontOptions instance allowing you to get at various font
     * rendering configuration parameters.
     * 
     * @since 4.0.10
     */
    public FontOptions() {
        super(CairoFontOptions.createFontOptions());
        checkStatus();
    }

    protected final void release() {
        CairoFontOptions.destroy(this);
    }

    protected void checkStatus() {
        checkStatus(CairoFontOptions.status(this));
    }

    /**
     * Whether or not you want hinting. For normal rendering you certainly do
     * (and this is likely the default) but for the rare case where you need
     * multiple versions of the same text to be identical across linear
     * scaling, then you'll need this {@link HintMetrics#OFF OFF}.
     * 
     * @since 4.0.10
     */
    public void setHintMetrics(HintMetrics hinting) {
        CairoFontOptions.setHintMetrics(this, hinting);
        checkStatus();
    }
}
