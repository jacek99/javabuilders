/*
 * DialogFlags.java
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
 * Flags used to control the Dialog creation.
 * 
 * @author Vreixo Formoso
 * @since 4.0.5
 */
/*
 * Only MODAL flag is really useful, we musn't expose DESTROY_WITH_PARENT and
 * NO_SEPARATOR seems also not very useful. So, I plan to not expose this
 * class, and instead of that use a boolean for modal property.
 */
final class DialogFlags extends Flag
{
    private DialogFlags(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    static final DialogFlags NONE = new DialogFlags(0, "NONE");

    static final DialogFlags MODAL = new DialogFlags(GtkDialogFlags.MODAL, "MODAL");
}
