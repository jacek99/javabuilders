/*
 * SvgSurface.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2008      Vreixo Formoso
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.cairo;

import java.io.IOException;

/**
 * A Surface that you can use to write to a <abbr title="Scalable Vector
 * Graphics">SVG</abbr> file. You should use it as in the following example:
 * 
 * <pre>
 * surface = new SvgSurface(filename, 100, 100);
 * cr = new Context(surface);
 * 
 * // do drawing 
 * 
 * // and write to the file
 * surface.finish();
 * </pre>
 * 
 * It is important to call {@link Surface#finish() finish()} at the end, to
 * ensure contents are actually written to the file.
 * 
 * @author Vreixo Formoso
 * @since 4.0.10
 */
public class SvgSurface extends Surface
{
    protected SvgSurface(long pointer) {
        super(pointer);
    }

    /**
     * Create a new SvgSurface.
     * 
     * @param filename
     *            The file to write to.
     * @param width
     *            width of the surface, in points (1 point == 1/72.0 inch)
     * @param height
     *            height of the surface, in points (1 point == 1/72.0 inch)
     * @throws IOException
     *             If you do not have write permissions on the given file.
     */
    public SvgSurface(String filename, double width, double height) throws IOException {
        super(CairoSurface.createSurfaceSvg(filename, width, height));
        Status status = CairoSurface.status(this);
        if (status == Status.WRITE_ERROR) {
            throw new IOException("You cannot write to file " + filename);
        }
        checkStatus(status);
    }
}
