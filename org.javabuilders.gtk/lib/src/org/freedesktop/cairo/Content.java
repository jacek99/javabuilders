/*
 * Content.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.cairo;

import org.freedesktop.bindings.Flag;

/**
 * Constants to specify the colour space that will apply when creating a new
 * Surface based on an existing one. Used when you call Surface's
 * {@link Surface#createSimilar(Content, int, int) createSimilar()}.
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
public class Content extends Flag
{
    private Content(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Surface will hold colour content only.
     */
    public static final Content COLOR = new Content(CairoContent.COLOR, "COLOR");

    /**
     * Surface will hold alpha content only.
     */
    public static final Content ALPHA = new Content(CairoContent.ALPHA, "ALPHA");

    /**
     * Surface will hold full data, colour and alpha both.
     */
    public static final Content COLOR_ALPHA = new Content(CairoContent.COLOR_ALPHA, "COLOR_ALPHA");

}
