/*
 * Screen.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

import org.gnome.glib.Object;

/**
 * Representation of a physical monitor screen. You can get the Screen object
 * for one of your application's Windows by calling Window's
 * {@link org.gnome.gtk.Window#getScreen() getScreen()} method; it you want
 * the width and height of the screen your Window is on, you're in the right
 * place.
 * 
 * <p>
 * A Screen is typically one monitor, but could actually be more; it depends
 * on how your X server is configured. A Display, in turn, is made up of one
 * or more Screens; again it depends.
 * 
 * <p>
 * <i>With the advent of the</i> <code>XINERAMA</code> <i>extension in the</i>
 * XFree <i>and later</i> X.org <i>X Windows servers, you tend to find that
 * what would have been multiple Screens have been (transparently) merged and
 * stretched to run over an entire multi-headed Display. This works out better
 * (single mouse and keyboard works over the entire desktop, as does cut and
 * paste, dragging, etc) and since the window manager is aware of the
 * situation, it can maximize Windows properly to be only on one physical
 * screen as you'd expect and desire.</i>
 * 
 * <p>
 * <i>As a result, the distinction between Screen and Display is nowadays
 * somewhat blurred. In practise you can treat them synonymously especially
 * since their methods don't overlap. Frankly, this is all another classic
 * case of "don't second guess the window manager"; just let it do it's job
 * and leave the Window placement alone.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.4
 * @see Display
 * @see <span>The <code>X</code>(7) man page on your system</span>
 */
public class Screen extends Object
{
    protected Screen(long pointer) {
        super(pointer);
    }

    /**
     * Get the horizontal width of this Screen, in pixels.
     * 
     * @since 4.0.4
     */
    public int getWidth() {
        return GdkScreen.getWidth(this);
    }

    /**
     * Get the vertical height of this Screen, in pixels.
     * 
     * @since 4.0.4
     */
    public int getHeight() {
        return GdkScreen.getHeight(this);
    }

    /*
     * Functions being used in debugging Pixmaps. Not clear if they need to be
     * public; doesn't seem so.
     */

    /**
     * Get the default Screen on the default Display.
     */
    static Screen getDefault() {
        return GdkScreen.getDefault();
    }

    /**
     * Get the default Colormap associated with this Screen.
     * 
     * @since 4.0.10
     */
    /*
     * Method signature adjusted to avoid collision with getDefault() and to
     * provide completion space for both getColormap...() methods.
     */
    public Colormap getColormapDefault() {
        return GdkScreen.getDefaultColormap(this);
    }

    /**
     * Get the RGBA Colormap associated with this Screen. This is necessary
     * for per-pixel translucency in top level Windows.
     * 
     * @since 4.0.10
     */
    public Colormap getColormapRGBA() {
        return GdkScreen.getRgbaColormap(this);
    }
}
