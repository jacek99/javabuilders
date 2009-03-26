/*
 * MovementStep.java
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

/*
 * FIXME this is a placeholder stub for what will become the public API for
 * this type. Replace this comment with appropriate javadoc including author
 * and since tags. Note that the class may need to be made abstract, implement
 * interfaces, or even have its parent changed. No API stability guarantees
 * are made about this class until it has been reviewed by a hacker and this
 * comment has been replaced.
 */
/**
 * Constants describing the nature of a motion in <code>MoveCursor</code>
 * signal handlers found in {@link TextView}, {@link TreeView}, {@link Entry},
 * etc.
 * 
 * @author Andrew Cowie
 */
/*
 * FIXME Make constants public as necessary. What is the difference between
 * these? Especially, what are "logical" and "virtual" positions and why would
 * you care?
 */
public final class MovementStep extends Constant
{
    private MovementStep(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    static final MovementStep LOGICAL_POSITIONS = new MovementStep(GtkMovementStep.LOGICAL_POSITIONS,
            "LOGICAL_POSITIONS");

    static final MovementStep VISUAL_POSITIONS = new MovementStep(GtkMovementStep.VISUAL_POSITIONS,
            "VISUAL_POSITIONS");

    static final MovementStep WORDS = new MovementStep(GtkMovementStep.WORDS, "WORDS");

    static final MovementStep DISPLAY_LINES = new MovementStep(GtkMovementStep.DISPLAY_LINES,
            "DISPLAY_LINES");

    static final MovementStep DISPLAY_LINE_ENDS = new MovementStep(GtkMovementStep.DISPLAY_LINE_ENDS,
            "DISPLAY_LINE_ENDS");

    static final MovementStep PARAGRAPHS = new MovementStep(GtkMovementStep.PARAGRAPHS, "PARAGRAPHS");

    static final MovementStep PARAGRAPH_ENDS = new MovementStep(GtkMovementStep.PARAGRAPH_ENDS,
            "PARAGRAPH_ENDS");

    static final MovementStep PAGES = new MovementStep(GtkMovementStep.PAGES, "PAGES");
}
