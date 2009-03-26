/*
 * Glib.java
 *
 * Copyright (c) 2006-2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.glib;

/**
 * Static methods to initialize the Java bindings around GLib
 * 
 * @author Andrew Cowie
 * @since 4.0.0
 */
public class Glib
{
    /**
     * A guard against someone calling init() twice
     */
    private static boolean initialized = false;

    static {
        // FIXME: call g_type_init()
    }

    /**
     * No instantiation. Static methods only!
     */
    protected Glib() {}

    /**
     * Initialize GLib's internal subsystems. To simplify things, this is
     * called automatically by
     * {@link org.gnome.gtk.Gtk#init(java.lang.String[]) Gtk.init()}, so the
     * occasions to call this directly should be pretty rare.
     * 
     * @throws IllegalStateException
     *             if GLib has already been initialized, ie you either called
     *             this twice by accident, or you already initialized GLib by
     *             calling Gtk.init() or Program.init().
     * @since 4.0.0
     */
    protected static void init(String[] args) {
        if (initialized) {
            throw new IllegalStateException("Glib already initialized");
        }

        // TODO: other initializations?

        /*
         * Prevent subsequent manual initialization.
         */
        initialized = true;
    }

    /**
     * Notify org.gnome.glib.Glib that it will be initialized care of a sub
     * library's initialization. <i>This is so Gtk or Gnome can just carry on
     * calling gtk_init() or gnome_program_init(), both of which initialize
     * <code>GLib</code>, <code>GType</code>, etc</i>
     * 
     * @since 4.0.1
     */
    protected static void skipInit() {
        /*
         * Prevent subsequent manual initialization.
         */
        initialized = true;
    }

    /**
     * Check if GLib and GTK have been initialized; abort if not.
     */
    /*
     * TODO make it possible for non GTK libraries to initialize java-gnome.
     * This will involve moving the System.loadLibrary() call here, and more
     * importantly adding a JNI call here to do the GThreads and related
     * setup. For now, keeping the requirement as Gtk.init() [and hence the
     * JNI code in src/bindings/org/gnome/gtk/Gtk.c] is fine; running it
     * doesn't hurt very much.
     */
    static void checkInitialized() {
        if (!initialized) {
            throw new FatalError(
                    "\n\nYou *must* call Gtk.init() before using anything else in java-gnome!\n");
        }
    }

    /**
     * Change the internal program name used by GLib and GTK for internal
     * error messages. Occasionally (especially as we develop new
     * functionality) you or we will do something wrong, and GLib will
     * complain loudly about it to the console, for example:
     * 
     * <pre>
     * (gnome-panel:5581): Gtk-WARNING **: gtk_widget_size_allocate(): attempt to...
     * </pre>
     * 
     * where "<code>gnome-panel</code>" was the name set by that program with
     * this method call, and <code>5581</code> was the process id originating
     * the message. As you can see, the whole thing is pretty ugly (not to
     * mention having no context), which is why one of the design goals of
     * java-gnome is to fully proxy the entire underlying library and have
     * none of the internals from GLib or GTK be exposed to the Java
     * developer. If we do our job right, your users should never see a
     * message like that; at <i>worst</i> it would be reported as a Java stack
     * trace.
     * 
     * <p>
     * You don't really need to call this, but it's here if you want to make
     * it clearer in the <code>.xsession-errors</code> log what the culprit
     * application is. The default name is "java", which is fine until you
     * deploy for production use.
     * 
     * @since 4.0.6
     */
    /*
     * Another one to potentially move to a GtkApplication class.
     */
    public static void setProgramName(String name) {
        GlibMisc.setPrgname(name);
    }
}
