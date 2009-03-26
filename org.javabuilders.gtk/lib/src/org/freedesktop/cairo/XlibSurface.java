/*
 * XlibSurface.java
 *
 * Copyright (c) 2008-2009 Operational Dynamics Consulting Pty Ltd, and Others
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.cairo;

/**
 * A Window as rendered by the X Server. Used internally when drawing to an
 * application window.
 * 
 * <p>
 * Ordinarily you don't think about creating an XlibSurface; one is implicitly
 * created for you when you create a Cairo context in order to draw in an
 * Widget.ExposeEvent handler:
 * 
 * <pre>
 * gizmo.connect(new Widget.ExposeEvent() {
 *     public boolean onExposeEvent(Widget source, EventExpose event) {
 *         Context cr;
 *         Surface surface;
 *         
 *         cr = new Context(source.getWindow());
 *         
 *         // start drawing
 *         
 *         // for interest's sake
 *         surface = cr.getTarget();
 *         assert (surface instanceof XlibSurface);
 *     }
 * }
 * </pre>
 * 
 * <h2>Efficient caching of image data</h2>
 * 
 * <p>
 * There is a fair bit of work involved in translating from an image in bitmap
 * form in application memory to the representation that will be used by the X
 * server in video memory. Thus if you are drawing frequently with the same
 * Pixbuf, you may find you are dramatically better off caching the image in
 * the X server.
 * 
 * <p>
 * The best way to do this is to create an XlibSurface with this image in it.
 * You set that image to be the source pattern with a call to
 * <code>setSource()</code> and then paint that onto the Context where you are
 * drawing. The key method involved is <code>createSimilar()</code>, which
 * allows you to create an X resource as a cache:
 * 
 * <pre>
 * gizmo.connect(new Widget.ExposeEvent() {
 *     private Surface cache;
 * 
 *     public boolean onExposeEvent(Widget source, EventExpose event) {
 *         final Context cr, cr2;
 *         final Surface target;
 *         final Pixbuf pixbuf;
 *         final double x, y;
 *         
 *         cr = new Context(source.getWindow());
 *         
 *         // start drawing
 *         ...
 *         
 *         // cache image
 *         if (cache == null) {
 *             pixbuf = new Pixbuf(filename);
 *             
 *             target = cr.getTarget();
 *             cache = target.createSimilar(Content.COLOR_ALPHA, pixbuf.getWidth(), pixbuf.getHeight());
 *             cr2 = new Context(cache);
 *             cr2.setSource(pixbuf, 0.0, 0.0);
 *             cr2.paint();
 *         }
 *         
 *         // now we can draw the image
 *         cr.setSource(cache, x, y);
 *         cr.paint();
 *     }
 * }
 * </pre>
 * 
 * note that this is <i>not</i> an ImageSurface; we've deliberately created
 * another XlibSurface which is the proxy around the X resource which the X
 * server can decide how best to blit with efficiently.
 * 
 * <p>
 * <i>Obviously you are consuming X server memory doing this, and it can be
 * overdone. You'll have to look at your application's performance to decide
 * whether this is necessary and if so, how much caching to do. Nevertheless,
 * modern graphics cards are very good at blitting images together so if you
 * are doing anything image intensive you are best to let the X server do the
 * work. Cairo is designed with this in mind.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
/*
 * Thanks to Carl Worth, Davyd Madeley, and Mathias Hasselman for having
 * explained the code paths involved and the implications of efficiently using
 * X server memory and it in turn using video memory.
 */
public class XlibSurface extends Surface
{
    protected XlibSurface(long pointer) {
        super(pointer);
    }
}
