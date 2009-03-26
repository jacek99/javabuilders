/*
 * SeparatorToolItem.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * A separator between groups of related ToolItems in a Toolbar.
 * 
 * <p>
 * A SeparatorToolItem usually appears as a vertical line, and is used to
 * create a visual distinction between logically related items in a Toolbar.
 * 
 * <p>
 * Somewhat unusually, a SeparatorToolItem can be used to force align other
 * ToolItems at the right of the Toolbar by adding one with
 * {@link #setDraw(boolean) setDraw(false)} and {@link #setExpand(boolean)
 * setExpand(true)} between the two "sides".
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.4
 */
public class SeparatorToolItem extends ToolItem
{
    protected SeparatorToolItem(long pointer) {
        super(pointer);
    }

    /**
     * Creates a new SeparatorToolItem.
     */
    public SeparatorToolItem() {
        super(GtkSeparatorToolItem.createSeparatorToolItem());
    }

    /**
     * Set whether the separator will display a vertical line.
     * SeparatorToolItems have the curious property that you can tell them not
     * to present themselves in an obtrusive manner while still carrying out
     * their spacing function.
     */
    public void setDraw(boolean draw) {
        GtkSeparatorToolItem.setDraw(this, draw);
    }

    /**
     * Is the separator being displayed as a vertical line, or is it just
     * blank?
     */
    public boolean getDraw() {
        return GtkSeparatorToolItem.getDraw(this);
    }
}
