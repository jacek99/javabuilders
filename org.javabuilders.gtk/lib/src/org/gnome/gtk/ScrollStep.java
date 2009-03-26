/*
 * ScrollStep.java
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
 * Constants used by the {@link TextView.MoveViewport}, which doesn't even
 * seem to get hit. Do we even need these?
 * 
 * @author Andrew Cowie
 */
/*
 * FIXME to make public, figure out when necessary and document to that
 * effect. What is the difference between these?
 */
final class ScrollStep extends Constant
{
    private ScrollStep(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    static final ScrollStep STEPS = new ScrollStep(GtkScrollStep.STEPS, "STEPS");

    static final ScrollStep PAGES = new ScrollStep(GtkScrollStep.PAGES, "PAGES");

    static final ScrollStep ENDS = new ScrollStep(GtkScrollStep.ENDS, "ENDS");

    static final ScrollStep HORIZONTAL_STEPS = new ScrollStep(GtkScrollStep.HORIZONTAL_STEPS,
            "HORIZONTAL_STEPS");

    static final ScrollStep HORIZONTAL_PAGES = new ScrollStep(GtkScrollStep.HORIZONTAL_PAGES,
            "HORIZONTAL_PAGES");

    static final ScrollStep HORIZONTAL_ENDS = new ScrollStep(GtkScrollStep.HORIZONTAL_ENDS,
            "HORIZONTAL_ENDS");
}
