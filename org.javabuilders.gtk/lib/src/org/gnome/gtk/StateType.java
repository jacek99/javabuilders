/*
 * StateType.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import org.freedesktop.bindings.Constant;

/**
 * The current state of a Widget with respect to drawing and theming. Within
 * GTK this is used for sub-elements making up a Widget, and different
 * elements may be in different states. In practise, you only use this for
 * rare occasions when you need to override the defaults for example
 * background colour of a Widget. As this will conflict with the Style
 * settings of the users theme and end up creating inconsistencies in visual
 * appearance on the Desktop, methods using StateType should only be used with
 * deliberate care.
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
public final class StateType extends Constant
{
    private StateType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * The ordinary state of a Widget
     */
    public static final StateType NORMAL = new StateType(GtkStateType.NORMAL, "NORMAL");

    /**
     * A Widget that is currently active. The definition of this will vary
     * from Widget to Widget, but a Button, for example, is active while it is
     * depressed.
     */
    public static final StateType ACTIVE = new StateType(GtkStateType.ACTIVE, "ACTIVE");

    /**
     * The mouse pointer is currently hovering over the Widget, and the Widget
     * will be responding to mouse clicks.
     */
    public static final StateType PRELIGHT = new StateType(GtkStateType.PRELIGHT, "PRELIGHT");

    /**
     * The element is selected. The canonical example is a row in a TreeView
     * which has been selected; most themes show this by doing a form of
     * reverse video, swapping foreground and background colours, etc.
     */
    public static final StateType SELECTED = new StateType(GtkStateType.SELECTED, "SELECTED");

    /**
     * The Widget is not responding to events. See
     * {@link Window#setSensitive(boolean) setSensitive()} on Window for more
     * information about this state.
     */
    public static final StateType INSENSITIVE = new StateType(GtkStateType.INSENSITIVE, "INSENSITIVE");
}
