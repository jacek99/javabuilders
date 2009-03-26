/*
 * GtkClipboardOverride.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * Hand crafted to get around the fact that we don't otherwise need to model
 * GdkAtom, which is what identifies the clipboard being used because we only
 * care about the CLIPBOARD clipboard.
 */
final class GtkClipboardOverride extends Plumbing
{
    static final Clipboard get() {
        long result;

        synchronized (lock) {
            result = gtk_clipboard_get();

            return (Clipboard) objectFor(result);
        }
    }

    private static native final long gtk_clipboard_get();
}
