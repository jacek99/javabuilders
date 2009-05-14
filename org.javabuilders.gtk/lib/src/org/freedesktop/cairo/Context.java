/*
 * Context.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd, and Others
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.cairo;

import org.gnome.gdk.Drawable;
import org.gnome.gdk.Pixbuf;
import org.gnome.pango.Layout;
import org.gnome.pango.LayoutLine;

/**
 * Carry out drawing operations with the Cairo Graphics library. The current
 * Context contains the state of the rendering engine, including the
 * co-ordinates of as yet undrawn elements.
 * 
 * <h2>Constructing</h2>
 * 
 * Graphics will be rendered to the Surface specified when you construct the
 * Context:
 * <ul>
 * <li>If creating an image to be written to a file, start with an
 * {@link ImageSurface}, do your drawing, and then use Surface's writeToPNG()
 * to output your image.
 * <li>If drawing to the screen in a user interface application, construct a
 * Context using the underlying GDK Window in your Widget's
 * {@link org.gnome.gtk.Widget.ExposeEvent Widget.ExposeEvent}, and do your
 * drawing there.
 * </ul>
 * 
 * See the links above for examples of each use case.
 * 
 * <h2>Drawing Operations</h2>
 * 
 * Context has numerous methods allowing you to draw shapes, patterns, and
 * images to the Surface you are drawing on. These operations are all quite
 * low level, but give you very fine grained control over what is drawn and
 * where.
 * 
 * <p>
 * It is somewhat traditional to call your Context <code>cr</code>.
 * 
 * <p>
 * All of the methods on Context take arguments of type <code>double</code> to
 * represent co-ordinates, angles, colours, transparency levels, etc. Colours
 * are represented as values between <code>0.0</code> and <code>0.1</code>,
 * for example:
 * 
 * <pre>
 * cr.setSource(1.0, 0.0, 0.0);
 * </pre>
 * 
 * for solid red. In the case of co-ordinates, you can simply specify the
 * pixel address you wish to move to or draw to: <img src="Context-line.png"
 * class="snapshot">
 * 
 * <pre>
 * cr.moveTo(10, 10);
 * cr.lineTo(90, 50);
 * cr.stroke();
 * </pre>
 * 
 * where stroke draws the current path with the current line thickness.
 * 
 * <p>
 * Various other drawing operations are done by creating a shape and then
 * filling it in: <img src="Context-rectangle.png" class="snapshot">
 * 
 * <pre>
 * cr.rectangle(30, 20, 60, 60);
 * cr.fill();
 * </pre>
 * 
 * and so on.
 * 
 * <p>
 * <i>Obviously this is only the beginning of our documentation for Cairo.</i>
 * 
 * @author Andrew Cowie
 * @author Carl Worth
 * @author Behdad Esfahbod
 * @author Vreixo Formoso
 * @author Zak Fenton
 * @since 4.0.7
 * @see <a href="http://www.cairographics.org/documentation/">Cairo Graphics
 *      documentation</a>
 */
public class Context extends Entity
{
    protected Context(long pointer) {
        super(pointer);
    }

    protected void release() {
        CairoContext.destroy(this);
    }

    /**
     * Construct a new "Cairo Context". You supply the Surface that you are
     * drawing to.
     * 
     * @since 4.0.7
     */
    public Context(Surface target) {
        super(CairoContext.createContext(target));
    }

    /**
     * Check the status of the Cairo Context, and fail with an exception if it
     * is other than SUCCESS. This should be called by each method in our
     * Cairo bindings.
     * 
     * <p>
     * <i>The the fact that errors are not checked for after each operation is
     * a C API convenience only.</i>
     */
    void checkStatus() {
        checkStatus(CairoContext.status(this));
    }

    /**
     * Construct a new "Cairo Context" related to a Drawable. This is the
     * magic glue which allows you to link between GTK's Widgets and Cairo's
     * drawing operations.
     * 
     * <p>
     * You may find yourself needing to get at the Surface that is being drawn
     * on. Use {@link #getTarget() getTarget()}.
     * 
     * <p>
     * <i>Strictly speaking, this method is a part of GDK. We expose it here
     * as we are, from the Java bindings' perspective, constructing a Cairo
     * Context. So a constructor it is.</i>
     * 
     * @since 4.0.7
     */
    /*
     * The function in GdkDrawable is tempting, but since it is not marked as
     * a constructor, it wants to return an object, not a long. It's also in
     * the wrong package. We'll leave that be.
     */
    public Context(Drawable drawable) {
        super(GdkCairoSupport.createContextFromDrawable(drawable));
        checkStatus();
    }

    /**
     * Save the current state of this Context so it can be restored later.
     * Calls to <code>save()</code> and <code>restore()</code> can be nested.
     * 
     * <p>
     * This is useful for structured graphics.
     * 
     * @since 4.0.10
     */
    /*
     * TODO which means what?
     */
    public void save() {
        CairoContext.save(this);
        checkStatus();
    }

    /**
     * Restores the Context to the last (nested) saved state.
     * 
     * @since 4.0.10
     */
    public void restore() {
        CairoContext.restore(this);
        checkStatus();
    }

    /**
     * Applies a translation transformation. What this does is move the point
     * of origin so that <code>(0, 0)</code> is now at a new position.
     * 
     * <pre>
     * cr.translate(20,50);
     * cr.moveTo(20,20);    // This is now 40,70
     *  ...
     * </pre>
     * 
     * <p>
     * See {@link Matrix} for the full suite of affine transformations
     * available.
     * 
     * @since 4.0.10
     */
    public void translate(double tx, double ty) {
        CairoContext.translate(this, tx, ty);
    }

    /**
     * Applies a rotate transformation. This rotates the co-ordinates of
     * subsequent drawing operations through a given angle (in radians). The
     * rotation happens around the origin <code>(0, 0)</code>. To rotate
     * around a different point, try the following:
     * 
     * <pre>
     * cr.translate(x, y);
     * cr.rotate(r);
     * cr.translate(-x, -y);
     * </pre>
     * 
     * <p>
     * See {@link Matrix} for the full suite of affine transformations
     * available.
     * 
     * @since 4.0.10
     */
    public void rotate(double r) {
        CairoContext.rotate(this, r);
    }

    /**
     * Set the source pattern within this Context to an opaque colour. The
     * parameters each take the range <code>0.0</code> to <code>1.0</code>.
     * 
     * @since 4.0.10
     */
    public void setSource(double red, double green, double blue) {
        CairoContext.setSourceRgb(this, red, green, blue);
        checkStatus();
    }

    /** @deprecated */
    public void setSourceRGB(double red, double green, double blue) {
        assert false : "use setSource() instead";
        CairoContext.setSourceRgb(this, red, green, blue);
        checkStatus();
    }

    /**
     * Set the source pattern within this Context to a translucent colour. The
     * parameters each take the range <code>0.0</code> to <code>1.0</code>.
     * For the <code>alpha</code> parameter, a value of <code>0.0</code>
     * indicates full transparency, and <code>1.0</code> is full opacity (ie,
     * normal).
     * 
     * @since 4.0.10
     */
    public void setSource(double red, double green, double blue, double alpha) {
        CairoContext.setSourceRgba(this, red, green, blue, alpha);
        checkStatus();
    }

    /** @deprecated */
    public void setSourceRGBA(double red, double green, double blue, double alpha) {
        assert false : "use setSource() instead";
        CairoContext.setSourceRgba(this, red, green, blue, alpha);
        checkStatus();
    }

    /**
     * Add a line from the current location to <code>x</code>,<code>y</code>.
     * After the call the current point will be <code>x</code>,<code>y</code>.
     * 
     * @since 4.0.7
     */
    public void lineTo(double x, double y) {
        CairoContext.lineTo(this, x, y);
        checkStatus();
    }

    /**
     * Set the line width for this Context. This will have effect in next call
     * to {@link #stroke()}. Default value is <code>2.0</code>.
     * 
     * @since 4.0.7
     */
    public void setLineWidth(double width) {
        CairoContext.setLineWidth(this, width);
        checkStatus();
    }

    /**
     * Set the antialiasing mode of the rasterizer used for drawing shapes.
     * This value is a hint, and a particular backend may or may not support a
     * particular value.
     * 
     * @since 4.0.7
     */
    public void setAntialias(Antialias antialias) {
        CairoContext.setAntialias(this, antialias);
        checkStatus();
    }

    /**
     * Move to a new location without drawing, beginning a new sub-path. After
     * the call the current point will be <code>x</code>,<code>y</code>.
     * 
     * @since 4.0.7
     */
    public void moveTo(double x, double y) {
        CairoContext.moveTo(this, x, y);
        checkStatus();
    }

    /**
     * Draw the current path as a line.
     * 
     * @since 4.0.7
     */
    public void stroke() {
        CairoContext.stroke(this);
        checkStatus();
    }

    /**
     * Confines subsequent drawing operations to the inside area of the
     * current path.
     * 
     * @since 4.0.10
     */
    public void clip() {
        CairoContext.clip(this);
        checkStatus();
    }

    /**
     * Confines subsequent drawing operations to the inside area of the
     * current path, leaving the path intact for subsequent reuse.
     * 
     * @since 4.0.10
     */
    public void clipPreserve() {
        CairoContext.clipPreserve(this);
        checkStatus();
    }

    /**
     * Draw the current path as a line, preserving the path such that it can
     * be used used again. If you have drawn a shape and want to
     * <code>fill()</code> it, you are better off calling
     * {@link #fillPreserve() fillPreserve()} and, changing source and then
     * calling {@link #stroke() stroke()}; otherwise your fill will blot out
     * the inside of your stroke.
     * 
     * @since 4.0.10
     */
    public void strokePreserve() {
        CairoContext.strokePreserve(this);
        checkStatus();
    }

    /**
     * Get the current source Pattern for this Context.
     * 
     * @since 4.0.7
     */
    public Pattern getSource() {
        final Pattern result;

        result = CairoContext.getSource(this);
        checkStatus();

        return result;
    }

    /**
     * Get the Surface that this Context is drawing on.
     * 
     * <p>
     * <i>Yes, this method has a stupid name. It really should be
     * <code>getSurface()</code>. So many people have a hard time finding the
     * generic method that allows you to get to the Surface that they're
     * considering renaming this to <code>cairo_get_surface</code> in Cairo
     * itself, but until they do, we'll stick with the algorithmic mapping of
     * <code>cairo_get_target</code>.</i>
     * 
     * @since 4.0.7
     */
    public Surface getTarget() {
        final Surface result;

        result = CairoContext.getTarget(this);
        checkStatus();

        return result;
    }

    /**
     * Set the operation that will govern forthcoming compositing actions.
     * 
     * <p>
     * One particularly useful sequence is clearing the Surface to all
     * transparent pixels:
     * 
     * <pre>
     * cr.setOperator(Operator.CLEAR);
     * cr.paint();
     * </pre>
     * 
     * @since 4.0.7
     */
    public void setOperator(Operator op) {
        CairoContext.setOperator(this, op);
        checkStatus();
    }

    /**
     * Paint the current source everywhere within the current clip region.
     * 
     * @since 4.0.7
     */
    public void paint() {
        CairoContext.paint(this);
        checkStatus();
    }

    /**
     * Create a Pattern from a Surface, and then use it in this Context. This
     * is a convenience method.
     * 
     * <p>
     * <code>x</code>,<code>y</code> define where, in user-space coordinates,
     * that the Pattern should appear.
     * 
     * <p>
     * You can get the Pattern that was created internally by calling this
     * with {@link #getSource() getSource()} and manipulate it further if you
     * need to change the defaults.
     * 
     * @since 4.0.10
     */
    public void setSource(Surface surface, double x, double y) {
        CairoContext.setSourceSurface(this, surface, x, y);
        checkStatus();
    }

    /** @deprecated */
    public void setSourceSurface(Surface surface, double x, double y) {
        assert false : "use setSource() instead";
        CairoContext.setSourceSurface(this, surface, x, y);
        checkStatus();
    }

    /**
     * Draw a (closed) rectangular sub-path. The rectangle will be at
     * <code>x</code>,<code>y</code> in user-space coordinates of the given
     * <code>width</code> and <code>height</code>.
     * 
     * @since 4.0.7
     */
    public void rectangle(double x, double y, double width, double height) {
        CairoContext.rectangle(this, x, y, width, height);
        checkStatus();
    }

    /**
     * Adds a circular arc of the given radius to the current path. The arc is
     * centered at <code>xc,yc</code>, begins at <code>angle1</code> and
     * proceeds in the direction of increasing angles to end at
     * <code>angle2</code>. If <code>angle2</code> is less than
     * <code>angle1</code> it will be progressively increased by
     * <code>2&pi;</code> until it is greater than <code>angle1</code>.
     * 
     * <p>
     * If there is a current point, an initial line segment will be added to
     * the path to connect the current point to the beginning of the arc.
     * 
     * <p>
     * Angles are measured in radians. An angle of <code>0.0</code> is in the
     * direction of the positive <i>x</i> axis. An angle of
     * <code>&pi;/2</code> radians (90&deg;) is in the direction of the
     * positive <i>y</i> axis. Angles increase in the direction from the
     * positive <i>x</i> axis toward the positive <i>y</i> axis, increasing in
     * a clockwise direction. <img class="snapshot" src="Context-arc.png">
     * 
     * <p>
     * The 60&deg; arc shown is from angle <code>0</code> through
     * <code>+&pi;/3</code> radians, and was achieved with the following call:
     * 
     * <pre>
     * cr.arc(50.0, 50.0, 30.0, 0.0, Math.PI / 3.0);
     * </pre>
     * 
     * The illustration has its axis centred at position <code>50</code>,
     * <code>50</code>. The key point to note is that positive <i>y</i> is
     * towards the <b>bottom</b>, and that increasing angles as drawn by this
     * function go <b>clockwise</b> which is backwards from the Cartesian or
     * Polar co-ordinates you're probably used to using in mathematics.
     * 
     * <p>
     * See {@link #arcNegative(double, double, double, double, double)
     * arcNegative()} to draw arcs that go in the other direction.
     * 
     * @since 4.0.7
     */
    public void arc(double xc, double yc, double radius, double angle1, double angle2) {
        CairoContext.arc(this, xc, yc, radius, angle1, angle2);
        checkStatus();
    }

    /**
     * Adds a circular arc of the given radius to the current path. The arc is
     * centered at <code>xc</code>,<code>yc</code>, will begin at
     * <code>angle1</code> and proceeds in the direction of decreasing angles
     * to end at <code>angle2</code>. If <code>angle2</code> is greater than
     * <code>angle1</code> it will be progressively decreased by
     * <code>2&pi;</code> until it is less than <code>angle1</code>. <img
     * class="snapshot" src="Context-arcNegative.png">
     * 
     * <p>
     * This 135&deg; arc shown in the illustration goes from <code>0</code> to
     * <code>-3/4&pi</code> radians:
     * 
     * <pre>
     * cr.arcNegative(50.0, 50.0, 30.0, 0.0, -Math.PI * 3.0 / 4.0);
     * </pre>
     * 
     * note that in this example the second angle is negative; if
     * <code>3/4&pi</code> had been specified the arc would have continued to
     * a point 45&deg; below the <code>-</code><i>x</i> axis.
     * 
     * <p>
     * See {@link #arc(double, double, double, double, double) arc()} for
     * drawing arcs in the positive, clockwise direction.
     * 
     * @since 4.0.7
     */
    public void arcNegative(double xc, double yc, double radius, double angle1, double angle2) {
        CairoContext.arcNegative(this, xc, yc, radius, angle1, angle2);
        checkStatus();
    }

    /**
     * Fill the current path, implicitly closing sub-paths first. The drawing
     * will be done according to the current FillRule. The path will be
     * cleared after calling <code>fill()</code>; if you want to keep it use
     * {@link #fillPreserve() fillPreserve()} instead.
     * 
     * @since 4.0.7
     */
    public void fill() {
        CairoContext.fill(this);
        checkStatus();
    }

    /**
     * Fill the current path, preserving the path such that it can be used
     * used again. This is useful if you have drawn a shape and want to
     * {@link #stroke() stroke()} it with a different colour as an outline.
     * 
     * @since 4.0.7
     */
    public void fillPreserve() {
        CairoContext.fillPreserve(this);
        checkStatus();
    }

    /**
     * Draw a paragraph of text. The top-left corner of the Layout's rendered
     * extents will be drawn at the current Context point.
     * 
     * <p>
     * The text to draw and its format is specified in a Pango {@link Layout},
     * previously constructed with this Context.
     * 
     * @since 4.0.10
     */
    public void showLayout(Layout layout) {
        CairoContext.showLayout(this, layout);
        checkStatus();
    }

    /**
     * Draw a single line of text as extracted from a Layout.
     * 
     * <p>
     * Unlike the <code>showLayout()</code> taking a full Layout, this method
     * draws the base line of the extent (its Rectangle's <code>x</code>,
     * <code>y</code> origin) at the current Context point. See LayoutLine's
     * {@link LayoutLine#getExtentsLogical() getExtentsLogical()} method for
     * details.
     * 
     * @since 4.0.10
     */
    public void showLayout(LayoutLine line) {
        CairoContext.showLayoutLine(this, line);
        checkStatus();
    }

    /*
     * This really doesn't belong here, but we don't have anywhere else for it
     * right now. showLayout() above is lovely, and this is entirely parallel
     * and complementary to it. So it'll do for now.
     */
    public void updateLayout(Layout layout) {
        CairoContext.updateLayout(this, layout);
        checkStatus();
    }

    /**
     * Set a Pattern to be the source of this Context.
     * 
     * @since 4.0.7
     */
    public void setSource(Pattern pattern) {
        CairoContext.setSource(this, pattern);
        checkStatus();
    }

    /**
     * Paint the current source using the alpha channel of
     * <code>pattern</code> as a mask. This means "opaque areas of the mask
     * will be painted with the source, whereas transparent areas will not be
     * painted"
     * 
     * @since 4.0.7
     */
    public void mask(Pattern pattern) {
        CairoContext.mask(this, pattern);
        checkStatus();
    }

    /**
     * Paint the current source using the alpha channel of the given
     * <code>surface</code> as its mask. The Surface will be offset by
     * <code>x</code> and <code>y</code> before drawing.
     * 
     * @since 4.0.10
     */
    public void mask(Surface surface, double x, double y) {
        CairoContext.maskSurface(this, surface, x, y);
    }

    /**
     * Given an image already loaded in a Pixbuf, set the current Source to be
     * that image. For example, to put the image at the bottom right of your
     * drawing area, you might do something like:
     * 
     * <pre>
     * pixbuf = new Pixbuf(filename);
     * cr.setSource(pixbuf, pageWidth - pixbuf.getWidth(), pageHeight - pixbuf.getHeight());
     * cr.paint();
     * </pre>
     * 
     * as <code>paint()</code> paints the current source "everywhere", and so
     * down goes your image.
     * 
     * If you are drawing the same image data to screen frequently, consider
     * caching the image in video memory. See {@link XlibSurface}.
     * 
     * @since 4.0.10
     */
    public void setSource(Pixbuf pixbuf, double x, double y) {
        GdkCairoSupport.setSourcePixbuf(this, pixbuf, x, y);
    }

    /**
     * Move to a location relative to the current point.
     * 
     * <p>
     * If the point is at <code>x</code>,<code>y</code> then this will move
     * the point to <code>x+dx</code>,<code>y+dy</code>.
     * 
     * <p>
     * <i>In the underlying native library this is</i>
     * <code>cairo_rel_move_to()</code>. <i>We have adjusted the name slightly
     * to provide for better discoverability in the completion space.</i>
     * 
     * @since 4.0.10
     */
    public void moveRelative(double dx, double dy) {
        CairoContext.relMoveTo(this, dx, dy);
    }

    /**
     * Move to a location relative to the current point.
     * 
     * <p>
     * If the point is at <code>x</code>,<code>y</code> then this will draw a
     * line from <code>x</code>,<code>y</code> to <code>x+dx</code>,
     * <code>y+dy</code>, leaving the point at the latter location.
     * 
     * <p>
     * <i>In the underlying native library this is</i>
     * <code>cairo_rel_line_to()</code>. <i>We have adjusted the name slightly
     * to provide for better discoverability in the completion space.</i>
     * 
     * @since 4.0.10
     */
    public void lineRelative(double dx, double dy) {
        CairoContext.relLineTo(this, dx, dy);
    }

    /**
     * Get the x co-ordinate of the current point.
     * 
     * <p>
     * You'll need to call this if you want to resume drawing at that point
     * after calling <code>stroke()</code> or <code>fill()</code>, as after
     * doing their work they clear the current path; the current point goes
     * along with it.
     * 
     * <pre>
     * // do some drawing
     * 
     * x = cr.getCurrentPointX();
     * y = cr.getCurrentPointY();
     * 
     * cr.stroke();
     * 
     * cr.moveTo(x, y);
     * 
     * // carry on drawing
     * </pre>
     * 
     * @since 4.0.10
     */
    public double getCurrentPointX() {
        double[] x;
        double[] y;

        x = new double[1];
        y = new double[1];

        CairoContext.getCurrentPoint(this, x, y);

        return x[0];
    }

    /**
     * Get the y co-ordinate of the current point. See
     * {@link #getCurrentPointX() getCurrentPointX()} for discussion of when
     * you'd need this.
     * 
     * @since 4.0.10
     */
    public double getCurrentPointY() {
        double[] x;
        double[] y;

        x = new double[1];
        y = new double[1];

        CairoContext.getCurrentPoint(this, x, y);

        return y[0];
    }

    /**
     * Apply the given Matrix to affine transform this Context. See
     * {@link Matrix} for examples.
     * 
     * <p>
     * Beware that if there is a scaling component, line widths resulting from
     * <code>stroke()</code> calls will scale too!
     * 
     * @since 4.0.10
     */
    public void transform(Matrix matrix) {
        CairoContext.transform(this, matrix);
    }
}
