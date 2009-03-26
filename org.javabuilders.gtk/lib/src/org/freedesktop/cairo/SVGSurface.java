/*
 * SVGSurface.java
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
 * @deprecated This class was renamed to match type naming conventions in use
 *             elsewhere. See {@link SvgSurface} which replaces it.
 */
public class SVGSurface extends Surface
{
    /**
     * @deprecated
     */
    protected SVGSurface(long pointer) {
        super(pointer);
    }

    /**
     * @deprecated
     */
    public SVGSurface(String filename, double width, double height) throws IOException {
        super(CairoSurface.createSurfaceSvg(filename, width, height));

        assert false : "Class renamed. Use SvgSurface instead";

        Status status = CairoSurface.status(this);
        if (status == Status.WRITE_ERROR) {
            throw new IOException("You cannot write to file " + filename);
        }
        checkStatus(status);
    }
}
