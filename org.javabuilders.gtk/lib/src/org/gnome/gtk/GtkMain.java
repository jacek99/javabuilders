/*
 * GtkMain.java
 *
 * Copyright (c) 2006-2009 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 * 
 * This code originally lived in Gtk.java
 */
package org.gnome.gtk;

/**
 * Crafted to avail ourselves of a dependency on Plumbing, whose ultimate
 * class initializer loads our shared library, which of course we need to have
 * completed before any of the native methods here will be available.
 * 
 * @author Andrew Cowie
 * @since 4.0.9
 */
final class GtkMain extends Plumbing
{
    private GtkMain() {}

    static final void init(String[] args) {
        gtk_init(lock, args);
    }

    /*
     * This is one of the rarer cases where the arguments we pass to the JNI
     * side have little relation to the signature of the actual target
     * function. In this case, the first argument is a reference to the GDK
     * lock used to permit multithreaded access to the GTK library.
     */
    private static native final void gtk_init(java.lang.Object lock, String[] args);

    /*
     * Note that although this code is marked as being within our Gdk$Lock,
     * there is, in effect, a wait() within this call: as the GTK main loop
     * cycles it releases the lock. The effect is that the monitor on Gdk.lock
     * is frequently relinquished, which is the behaviour that is expected if
     * a piece of Java code object executes wait() within a monitor block.
     * Which is exactly what we need!
     * 
     * We'd rather have a synchronized (lock) {...} block here like we do for
     * every other translation layer method in java-gnome; ensuring the main
     * loop is within the GDK lock is critical to our successful thread safety
     * story. For obscure reasons relating to optimizing Eclipse's debugging
     * behaviour, for this one method ONLY we enter the monitor on the JNI
     * side.
     */
    static final void main() {
        // enter synchronized block
        gtk_main();
        // leave synchronized block
    }

    private static native final void gtk_main();

    static final void mainQuit() {
        synchronized (lock) {
            gtk_main_quit();
        }
    }

    private static native final void gtk_main_quit();

    /**
     * Are there any events pending for the main loop to process?
     * 
     * <p>
     * <b>This is not for general use! Do not expose this and do not encourage
     * anyone to use this to hack into the main loop.</b>
     * 
     * <p>
     * In a test case, this could be used as follows; see
     * <code>TestCaseGtk.cycleMainLoop()</code> in the <code>tests/</code>
     * tree for details:
     * 
     * <pre>
     * while (Gtk.eventsPending()) {
     *     Gtk.mainIterationDo(false);
     * }
     * </pre>
     */
    static final boolean eventsPending() {
        synchronized (lock) {
            return gtk_events_pending();
        }
    }

    private static native final boolean gtk_events_pending();

    /**
     * Run a single iteration of the main loop.
     * 
     * <p>
     * Not public! This is for internal use only, notably by test cases.
     * 
     * @param block
     *            Whether to block or not. If <code>true</code>, this method
     *            will block until an event is processed.
     * @return Will result in <code>true</code> if <code>Gtk.mainQuit()</code>
     *         (aka <code>gtk_main_quit()</code>) has been called on the
     *         innermost active main loop. <code>true</code> will also be
     *         returned if there <i>is</i> no main loop running.
     */
    static final boolean mainIterationDo(boolean block) {
        synchronized (lock) {
            return gtk_main_iteration_do(block);
        }
    }

    private static native final boolean gtk_main_iteration_do(boolean blocking);
}
