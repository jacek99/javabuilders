/*
 * ToolItem.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2007 Vreixo Formoso
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * ToolItems are the items that can be added to a {@link Toolbar}.
 * 
 * <p>
 * Usually you will prefer to use a subtype of this class, such as
 * {@link ToolButton}, in your Toolbars. However, if you want to add another
 * kind of Widget, you have to create a new ToolItem and {@link #add(Widget)
 * add()} the desired Widget to it.
 * 
 * @see Toolbar
 * 
 * @author Vreixo Formoso
 * @since 4.0.4
 */
public class ToolItem extends Bin
{
    protected ToolItem(long pointer) {
        super(pointer);
    }

    /**
     * Create a new ToolItem.
     */
    public ToolItem() {
        super(GtkToolItem.createToolItem());
    }

    /**
     * Set whether this ToolItem will be expanded when there is available
     * space on the Toolbar.
     */
    public void setExpand(boolean expand) {
        GtkToolItem.setExpand(this, expand);
    }

    /**
     * Get if this ToolItem will be [is] expanded in the presence of extra
     * available space on the Toolbar.
     */
    public boolean getExpand() {
        return GtkToolItem.getExpand(this);
    }
}
