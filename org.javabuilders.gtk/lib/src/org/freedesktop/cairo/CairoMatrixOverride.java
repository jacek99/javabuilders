/*
 * CairoMatrixOverride.java
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

/**
 * Expose an allocator for cairo_matrix_t.
 * 
 * @author Andrew Cowie
 */
final class CairoMatrixOverride extends Plumbing
{
    static final long createMatrixIdentity() {
        synchronized (lock) {
            return cairo_matrix_init_identity();
        }
    }

    private static native final long cairo_matrix_init_identity();

    static final void free(Matrix self) {
        synchronized (lock) {
            cairo_matrix_free(pointerOf(self));
        }
    }

    private static native final void cairo_matrix_free(long self);
}
