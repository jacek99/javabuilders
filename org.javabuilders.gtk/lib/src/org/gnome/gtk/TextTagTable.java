/*
 * TextTagTable.java
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

import org.gnome.glib.Object;

/**
 * A set of TextTags that can be used by one or more TextBuffers.
 * 
 * <p>
 * Creating a TextTag automatically adds it to the TextTagTable passed when
 * the TextTag is constructed.
 * 
 * <p>
 * You certainly only need one of these per TextBuffer, and as most people
 * want to reuse the same set of TextTags across all the TextBuffers in a
 * given program, they typically end up only having one TextTagTable across
 * the entire application.
 * 
 * @author Andrew Cowie
 * @since 4.0.9
 */
public class TextTagTable extends Object
{
    private static TextTagTable defaultTable;

    protected TextTagTable(long pointer) {
        super(pointer);
    }

    /**
     * Instantiate a new table for collecting TextTags.
     * 
     * @since 4.0.9
     */
    public TextTagTable() {
        super(GtkTextTagTable.createTextTagTable());
    }

    /**
     * We maintain a single default TextTagTable to facilitate no-arg
     * convenience constructors for TextTag and TextBuffer. Get (and
     * initialize if necessary) this table.
     */
    /*
     * synchronized?
     */
    static TextTagTable getDefaultTable() {
        if (defaultTable == null) {
            defaultTable = new TextTagTable();
        }
        return defaultTable;
    }
}
