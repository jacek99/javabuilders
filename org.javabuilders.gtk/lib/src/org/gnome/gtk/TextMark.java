/*
 * TextMark.java
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

import org.gnome.glib.Object;

/**
 * A stable reference to a specific position within a TextBuffer.
 * 
 * <p>
 * TextIters are transient pointers to positions within TextBuffers which are
 * invalidated when their TextBuffer is modified, so you can't "save" them. If
 * you need to preserve a position for later reuse, then get a TextMark by
 * calling TextBuffer's {@link TextBuffer#createMark(TextIter, boolean)
 * createMark()} on it.
 * 
 * <p>
 * There are two "standard" TextMarks available in all TextBuffers, relating
 * to the cursor and the current selection. See {@link TextBuffer#getInsert()
 * getInsert()} and {@link TextBuffer#getSelectionBound() getSelectionBound()}.
 * 
 * <p>
 * Instances of this class fill the same role for TextBuffers that
 * {@link TreeRowReference} does in providing stability for rows pointed to
 * transiently by TreeIters that come from TreeModels.
 * 
 * @author Andrew Cowie
 * @since 4.0.9
 */
public class TextMark extends Object
{
    protected TextMark(long pointer) {
        super(pointer);
    }

    /**
     * Left-gravity. This is a convenience constant that can be used when
     * creating a TextMark with
     * {@link TextBuffer#createMark(TextIter, boolean) createMark()}. The
     * TextMark will stay fixed to the left if text is added there.
     */
    public static final boolean LEFT = true;

    /**
     * Right-gravity. Used when creating a TextMark via
     * {@link TextBuffer#createMark(TextIter, boolean) createMark()}. The
     * TextMark will move to the right if text is added there. The
     * <var>insert</var> TextMark (your cursor) has right-gravity.
     */
    public static final boolean RIGHT = false;

    /**
     * Get the TextBuffer that this TextMark points into.
     * 
     * @since 4.0.9
     */
    public TextBuffer getBuffer() {
        return GtkTextMark.getBuffer(this);
    }

    /**
     * Get a currently valid TextIter indicating where in the TextBuffer this
     * TextMark is pointing. This is a convenience method for the common case
     * where you have a series of TextMarks but need to call various
     * TextBuffer insertion or markup related methods, all of which take
     * TextIter arguments.
     * 
     * <p>
     * The usual caveats about changes invalidating iterators apply, but if
     * you use these inline you will have no problems. For example, given
     * TextMarks <code>start</code>, <code>end</code> and a TextTag of some
     * sort, you could easily do:
     * 
     * <pre>
     * buffer.applyTag(bold, start.getIter(), end.getIter());
     * </pre>
     * 
     * which is very straight forward. If you are using the same position
     * repeatedly, however, and not changing the TextBuffer, then it is far
     * more efficient to convert the TextMark into a TextIter once and use
     * that directly.
     * 
     * @since 4.0.9
     */
    public TextIter getIter() {
        final TextIter pointer;
        final TextBuffer buf;

        buf = GtkTextMark.getBuffer(this);
        pointer = buf.getIter(this);

        return pointer;
    }
}
