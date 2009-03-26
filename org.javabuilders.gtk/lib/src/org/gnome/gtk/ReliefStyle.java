/*
 * ReliefStyle.java
 *
 * Copyright (c) 2006 Operational Dynamics Consulting Pty Ltd
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
 * The relief to be drawn around a Button. Ordinarily when you think of a
 * Button you think of something that can obviously be pressed, and the relief
 * that GTK theme engines draw around Buttons emphasizes this accordingly. You
 * can, however, change this behaviour with this class.
 * 
 * @author Andrew Cowie
 * @since 4.0.1
 */
public final class ReliefStyle extends Constant
{
    private ReliefStyle(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Draw normal relief around the Button. In other words, it looks like
     * something you can press. This is the default.
     */
    public static final ReliefStyle NORMAL = new ReliefStyle(GtkReliefStyle.NORMAL, "NORMAL");

    /**
     * Only draw relief around the Button "half" the time.
     */
    /*
     * TODO this means what, exactly? To be honest, I thought that
     * GTK_RELIEF_HALF would exhibit the behaviour described for NONE below.
     */
    public static final ReliefStyle HALF = new ReliefStyle(GtkReliefStyle.HALF, "HALF");

    /**
     * Draw no relief around the Button at all. This is actually a misnomer:
     * no relief is drawn except when the mouse hovers over it, at which point
     * it suddenly decorates up like the Button it really is. This is terrific
     * when you do not want a Button to attract attention, but want to give a
     * hint that it actually <i>is</i> a Button when the user's mouse hovers
     * over it and when the Button is activated.
     */
    public static final ReliefStyle NONE = new ReliefStyle(GtkReliefStyle.NONE, "NONE");
}
