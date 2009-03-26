/*
 * Viewport.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * An adapter that allows a large Widget to only have a limited view be
 * presented. Specifically, Viewports are for scrolling. Generally you don't
 * need to create one of these yourself; you can add your Widget to a
 * ScrolledWindow in one step with
 * {@link ScrolledWindow#addWithViewport(Widget) addWithViewport()}.
 * 
 * @author Andrew Cowie
 * @since 4.0.8
 */
public class Viewport extends Bin
{
    protected Viewport(long pointer) {
        super(pointer);
    }

    /**
     * Construct a Viewport, specifying the Adjustment objects used to control
     * the panning. If you're adding your child Widget to a ScrolledWindow,
     * use its {@link ScrolledWindow#addWithViewport(Widget)
     * addWithViewport()} instead of constructing a Viewport manually.
     * 
     * @since 4.0.8
     */
    public Viewport(Adjustment hadjustment, Adjustment vadjustment) {
        super(GtkViewport.createViewport(hadjustment, vadjustment));
    }

    /**
     * Get the Adjustment that is being used to drive the horizontal extent of
     * the region of the child Widget being shown.
     * 
     * @since 4.0.8
     */
    public Adjustment getHAdjustment() {
        return GtkViewport.getHadjustment(this);
    }

    /**
     * Get the Adjustment that is being used to drive the vertical extent of
     * the region of the child Widget being shown.
     * 
     * @since 4.0.8
     */
    public Adjustment getVAdjustment() {
        return GtkViewport.getVadjustment(this);
    }

    /**
     * Set the type of decoration you want around the Viewport. If you're
     * using a ScrolledWindow, you can get at this via its
     * {@link ScrolledWindow#setShadowType(ShadowType) setShadowType()}; it
     * does the same thing.
     * 
     * @since 4.0.8
     */
    public void setShadowType(ShadowType type) {
        GtkViewport.setShadowType(this, type);
    }
}
