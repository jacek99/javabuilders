/*
 * AttachOptions.java
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
 * Using AttachOptions you control how a Widget placed in a {@link Table}
 * uses eventually existing additional space, e.g. because another cell in
 * the same row is much wider or another column much higher.
 * <p>
 * It may be necessary to encapsulate your attached Widget in an
 * {@link Alignment} container. This could look like this:
 * </p> 
 * <pre>
 * Table table;
 * ...
 * TextEntry number = new TextEntry();
 * number.setWidthChars(4);
 * Alignment alignNumber = new Alignment(Alignment.LEFT, Alignment.CENTER, 0,0, number);
 * table.attach(alignNumber, 1, 2, 1, 2, AttachOptions.FILL, AttachOptions.SHRINK, 0,0);
 * </pre>
 * 
 * @since 4.0.10
 */
public final class AttachOptions extends Constant
{
    private AttachOptions(int ordinal, String nickname) {
        super(ordinal, nickname);
    }
    
    /**
     * Although there is additional space available, the Widget shall keep
     * its size or even shrink if possible. This usually gets the extra space
     * distributed evenly among the widget, so you have the Widget centered
     * in the extra space.
     * <p>
     * Encapsulating the Widget in an {@link Alignment} container does not
     * help, since the Alignment container only gets the same space granted
     * the shrinked Widget would get.
     * </p>
     */
    public static final AttachOptions SHRINK = new AttachOptions(GtkAttachOptions.SHRINK, "SHRINK");
    
    /**
     * The widget gets all the available space. Unless you put the widget
     * inside an {@link Alignment} container, the Widget is resized to fill
     * the space. If you do use Alignment containers the Widget itself will
     * not grow, but keep its size while the container controls where it 
     * resides.
     */
    public static final AttachOptions FILL = new AttachOptions(GtkAttachOptions.FILL, "FILL");

    /**
     * Like FILL, but also causes the cell to expand and use extra space.
     */
    public static final AttachOptions EXPAND = new AttachOptions(GtkAttachOptions.EXPAND, "EXPAND");

}
