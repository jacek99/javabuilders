/*
 * Matrix.java
 *
 * Copyright (c) 2008-2009 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.cairo;

/**
 * A matrix describing an affine transformation.
 * 
 * <p>
 * You can apply this transformation to a Cairo Context with its
 * {@link Context#transform(Matrix) transform()} method as follows:
 * 
 * <pre>
 * matrix = new Matrix();
 * matrix.rotate(...);    // and/or
 * matrix.scale(...);     // and/or
 * matrix.translate(...); 
 * 
 * cr.transform(matrix);
 * </pre>
 * 
 * Calls to the <code>rotate()</code>, <code>scale()</code>, and
 * <code>translate</code> methods are cumulative on a given Matrix.
 * 
 * <p>
 * Applying a transformation is what take you from <var>device
 * co-ordinates</var> to <var>user-space co-ordinates</var>; you are in fact
 * always working in the later but we tend not to focus on this as the default
 * is naturally no transformation.
 * 
 * <h2>Examples</h2>
 * 
 * <p>
 * In each of the illustrations below, we draw a box as follows:
 * 
 * <pre>
 * cr.setSource(0.7, 0.8, 0.8);
 * cr.rectangle(5, 5, 25, 15);
 * cr.stroke();
 * </pre>
 * 
 * We then apply the transform shown, change colour to blue, and then draw the
 * exact same rectangle again:
 * 
 * <pre>
 * cr.setSource(0.0, 0.0, 1.0);
 * cr.rectangle(5, 5, 25, 15);
 * cr.stroke();
 * </pre>
 * 
 * Thus both the original rectangle and the result of the matrix operation are
 * shown.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
public class Matrix extends Entity
{
    protected Matrix(long pointer) {
        super(pointer);
    }

    protected void release() {
        CairoMatrixOverride.free(this);
    }

    /**
     * Construct a new transformation matrix with the identity (no-op)
     * transform.
     * 
     * @since 4.0.10
     */
    public Matrix() {
        super(CairoMatrixOverride.createMatrixIdentity());
    }

    /**
     * Rotate the transformation by the given angle. As with elsewhere in
     * Cairo, <code>angle</code> is in radians. <img src="Matrix-rotate.png"
     * class="snapshot">
     * 
     * <pre>
     * matrix.rotate(-Math.PI / 4.0);
     * cr.transform(matrix);
     * </pre>
     * 
     * <p>
     * A negative angle is used in this example for the same reason as
     * discussed in
     * {@link Context#arc(double, double, double, double, double) arc()}.
     * 
     * @since 4.0.10
     */
    public void rotate(double angle) {
        CairoMatrix.rotate(this, angle);
    }

    /**
     * Translate by <code>tx</code> horizontally and <code>ty</code>
     * vertically. <img src="Matrix-translate.png" class="snapshot">
     * 
     * <pre>
     * matrix.translate(5, 10);
     * cr.transform(matrix);
     * </pre>
     * 
     * @since 4.0.10
     */
    public void translate(double tx, double ty) {
        CairoMatrix.translate(this, tx, ty);
    }

    /**
     * Scale by <code>sx</code> horizontally and <code>sy</code> vertically.
     * <img src="Matrix-scale.png" class="snapshot">
     * 
     * <pre>
     * matrix.scale(-0.8, 1.6);
     * cr.transform(matrix);
     * </pre>
     * 
     * Note that in this example the <code>sx</code> argument being negative
     * results in a reflection through the <i>y</i> axis. Note also that the
     * line widths are <b>not</b> the same as the original image's were; only
     * scaling by <code>1.0</code> would have left the widths alone.
     * 
     * <p>
     * Don't scale by <code>0</code>.
     * 
     * @since 4.0.10
     */
    public void scale(double sx, double sy) {
        CairoMatrix.scale(this, sx, sy);
    }
}
