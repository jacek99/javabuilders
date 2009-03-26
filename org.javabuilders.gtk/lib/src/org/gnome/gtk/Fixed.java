/*
 * Fixed.java
 *
 * Copyright (c) 2006 Srichand Pendyala
 * Copyright (c) 2006 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * Fixed is a Container Widget that allows you to position other widgets on it
 * at fixed coordinates. These could include Buttons and Labels as well as
 * more complex composite widgets like Boxes.
 * 
 * <p>
 * You should not use Fixed for any non-trivial purpose. While you might think
 * at first that it would simplify your application, the reality is that Fixed
 * is almost always the wrong Container choice. Because GTK is the widget
 * toolkit used to power the user interfaces of GNOME applications, the
 * appearance of Widgets is very dynamic. Different window managers and theme
 * engines can radically change the size and appearance of Widgets to suit
 * varying accessibility and usability requirements; different fonts available
 * to your users will cause Labels to be sized differently than you'd expect,
 * and of course translation completely throws predictability right out the
 * window. Since the Fixed Container does <i>not</i> adapt to the size
 * requests of the Widgets with in it, the frequent result is truncated text,
 * overlapping Widgets, and other visual bugs.
 * 
 * <p>
 * The Fixed widget can't be properly mirrored in right-to-left languages such
 * as Hebrew and Arabic. A Fixed widget with a right-to-left font will render
 * your application unusable.
 * 
 * <p>
 * Adding or removing any GUI elements from this Fixed requires you to
 * reposition all the other Widgets within it. As you can imagine, this will
 * end up a long-term maintenance headache for your application.
 * 
 * <p>
 * If any of those are a concern for your application, then you should be
 * using a different Container, either combinations of {@link VBox VBox} and
 * {@link HBox HBox}, or perhaps {@link Table Table}. You have been warned!
 * 
 * @see Layout
 * @see DrawingArea
 * 
 * @author Srichand Pendyala
 * @author Andrew Cowie
 * @since 4.0.1
 */
public class Fixed extends Container
{
    protected Fixed(long pointer) {
        super(pointer);
    }

    /**
     * Create a new Fixed Widget.
     * 
     * @since 4.0.1
     */
    public Fixed() {
        super(GtkFixed.createFixed());
    }

    /**
     * Place a Widget into the Fixed Container at a specified location.
     * 
     * <p>
     * It is up to you to ensure that the placing of the new Widget will not
     * overlap any existing Widgets. If this is starting to be a burden, it's
     * a good sign you're using the wrong Container!
     * 
     * <p>
     * The x and y co-ordinates are measured in pixels from the top left
     * corner of the Fixed.
     * 
     * @param widget
     *            the Widget to be placed in the Fixed.
     * @param x
     *            horizontal position for the Widget being added
     * @param y
     *            vertical position for the Widget being added
     * @since 4.0.1
     */
    public void put(Widget widget, int x, int y) {
        GtkFixed.put(this, widget, x, y);
    }

    /**
     * Move a Widget that has already been added to this Fixed to a new
     * location.
     * 
     * <p>
     * Calling <code>move()</code> will cause GTK to inherently redraw the
     * entire Fixed surface. If you have many Widgets in a Fixed, this can
     * lead to flickering. Consider using {@link Layout} or
     * {@link DrawingArea} instead.
     * 
     * <p>
     * The x and y co-ordinates are measured in pixels from the top left
     * corner of the Fixed.
     * 
     * @param widget
     *            the Widget that will be moved.
     * @param x
     *            the horizontal position to move <code>widget</code> to.
     * @param y
     *            the vertical position to move <code>widget</code> to.
     * @since 4.0.1
     */
    public void move(Widget widget, int x, int y) {
        GtkFixed.move(this, widget, x, y);
    }
}
