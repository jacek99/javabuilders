/*
 * DrawingArea.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * A simple blank canvas Widget that you can do arbitrary drawing on.
 * 
 * <p>
 * DrawingArea takes care of handling a number of implementation details
 * associated with Widget implementations, including basic handling of the
 * size-request / size-allocation cycle and realizing the necessary underlying
 * resouces. The end result is blank [<code>org.gnome.gdk</code>] Window that
 * you can draw on with Cairo in a <code>Widget.ExposeEvent</code>.
 * 
 * @author Andrew Cowie
 * @since 4.0.10
 */
/*
 * FIXME Explain what assumptions DrawingArea makes, and what requirements it
 * places on you if you use it, especially as regards event masks.
 */
public class DrawingArea extends Widget
{
    protected DrawingArea(long pointer) {
        super(pointer);
    }

    /**
     * Create a blank DrawingArea.
     * 
     * @since 4.0.10
     */
    public DrawingArea() {
        super(GtkDrawingArea.createDrawingArea());
    }
}
