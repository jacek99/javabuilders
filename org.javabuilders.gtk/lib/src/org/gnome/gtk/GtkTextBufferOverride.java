/*
 * GtkTextBufferOverride.java
 *
 * Copyright (c) 2009 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import org.gnome.glib.Signal;

/**
 * Hand crafted to allow us to prototype accessing a GtkTextBuffer notify
 * signal.
 * 
 * @author Andrew Cowie
 */
final class GtkTextBufferOverride extends Plumbing
{
    private GtkTextBufferOverride() {}

    interface NotifyCursorPositionSignal extends Signal
    {
        public void onNotifyCursorPosition(TextBuffer source);
    }

    static final void connect(TextBuffer self, GtkTextBufferOverride.NotifyCursorPositionSignal handler,
            boolean after) {
        connectSignal(self, handler, GtkTextBufferOverride.class, "notify::cursor-position", after);
    }

    /*
     * Ignore GParamSpec
     */
    protected static final void receiveNotifyCursorPosition(Signal handler, long source, long pspec) {
        ((GtkTextBufferOverride.NotifyCursorPositionSignal) handler).onNotifyCursorPosition((TextBuffer) objectFor(source));
    }
}
