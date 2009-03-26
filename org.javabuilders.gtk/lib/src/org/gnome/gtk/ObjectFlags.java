/*
 * ObjectFlags.java
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

import org.freedesktop.bindings.Flag;

/**
 * Flags in {@link Object}s.
 * 
 * <p>
 * <i>At time of writing, this contained only internals to Gtk, none of which
 * need exposure.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.3
 * @see WidgetFlags
 */
/*
 * FIXME this is a placeholder stub for what will become the public API for
 * this type. Replace this comment with appropriate javadoc including author
 * and since tags. Note that the class may need to be made abstract, implement
 * interfaces, or even have its parent changed. No API stability guarantees
 * are made about this class until it has been reviewed by a hacker and this
 * comment has been replaced.
 */
/*
 * See the comment in WidgetFlags at the class declaration for further
 * details. This class is not really be necessary, but seems right for
 * consistency.
 */
abstract class ObjectFlags extends Flag
{
    protected ObjectFlags(int ordinal, String nickname) {
        super(ordinal, nickname);
    }
}
