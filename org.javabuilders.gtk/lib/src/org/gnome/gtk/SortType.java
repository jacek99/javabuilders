/*
 * SortType.java
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

import org.freedesktop.bindings.Constant;

/**
 * Constants describing what sort ordering you want to be in effect. This is
 * used by TreeSortables such as {@link TreeModelSort}.
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
public final class SortType extends Constant
{
    private SortType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Sort in ascending order. This means:
     * <ul>
     * <li><code>0</code>, <code>1</code>, <code>2</code>, ..., <i>n</i> and</li>
     * <li><code>A</code>, <code>B</code>, <code>C</code>, ..., <code>X</code>, <code>Y</code>, <code>Z</code>
     * </ul>
     * where <code>0</code> or <code>A</code> or whatever will be at the top
     * of your list.
     */
    public static SortType ASCENDING = new SortType(GtkSortType.ASCENDING, "ASCENDING");

    /**
     * Sort in descending order. This means:
     * <ul>
     * <li><i>n</i>, <i>n</i><code>-1</code>, <i>n</i><code>-2</code>, ...,
     * <code>2</code>, <code>1</code>, <code>0</code> and</li>
     * <li><code>Z</code>, <code>Y</code>, <code>X</code>, ... <code>C</code>,
     * <code>B</code>, <code>A</code></li>
     * </ul>
     * where <i>n</i> or <code>Z</code> or whatever will be at the top of your
     * list.
     */
    public static SortType DESCENDING = new SortType(GtkSortType.DESCENDING, "DESCENDING");
}
