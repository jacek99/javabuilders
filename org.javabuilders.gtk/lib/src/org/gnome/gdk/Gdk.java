/*
 * Gdk.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

/**
 * The backend to GTK which talks to the actual screen is GDK. There are
 * various classes with the prefix <code>Gdk...</code> which give you access
 * to lower level drawing constructs; these are mapped in java-gnome as the
 * various classes within package <code>org.gnome.gdk</code>.
 * 
 * <p>
 * This particular class, on the other hand, simply contains some of the
 * infrastructure necessary to permit multi-threaded access to GDK, GTK, and
 * other GNOME libraries.
 * 
 * <h2>Thread safety in java-gnome</h2>
 * 
 * <p>
 * None of the other Java graphical toolkits out there let you make GUI calls
 * from threads other than the "main" one; they're all single threaded. Even
 * if all you want to do is a quick worker thread to carry out some input
 * validation in the background after the user presses "OK", you have to jump
 * through horrific contortions to do so safely, resulting in cumbersome,
 * clunky code. By contrast, the java-gnome 4.0 bindings of GTK are
 * <b>transparently thread safe</b>. We integrate properly with the underlying
 * GDK thread lock and as a result you can safely make calls to various GTK
 * methods from worker threads.
 * 
 * <p>
 * <i>Threading and GNOME is tricky. This class mostly exists for the purpose
 * of holding an object to serve as the global threading monitor and so that
 * when the lock appears in thread dumps it has a useful name.</i>
 * 
 * <p>
 * <i>Every call made to the native libraries is protected by entering "the
 * master GDK lock"; those familiar with the C side of things would be used to
 * doing this via <code>gdk_threads_enter()</code> and
 * <code>gdk_threads_leave()</code>. When you initialize your java-gnome
 * program with</i> {@link org.gnome.gtk.Gtk#init(String[]) Gtk.init()}<i>,
 * GDK is carefully configured so that the lock used is actually a Java side
 * monitor and therefore reentrant; nested calls all behave properly.</i>
 * 
 * <p>
 * <i>Note that when in a signal handler callback the GDK lock is already held
 * (you're "in" the main loop when a callback happens), but since it just
 * works transparently you don't need to worry about it. If you do find a need
 * to take the lock into account explicitly in your own code, the object is
 * available here,</i> {@link Gdk#lock Gdk.lock}.
 * 
 * @author Andrew Cowie
 * @since 4.0.3
 */
/*
 * TODO can we just move this into Plumbing somehow?
 */
public class Gdk
{
    /**
     * The global monitor used to regulate access to GTK functions. Any time
     * you call a java-gnome function you will enter the lock on this object.
     * As it happens, when you receive a callback you are "in" the main loop,
     * and thus within the lock held on this object. Because Java
     * <code>synchronized</code> monitors are reentrant you can proceed with
     * calling other java-gnome methods transparently, but keep in mind that
     * you <i>are</i> blocking the main loop during signal callbacks.
     * 
     * <p>
     * Most of the time you simply don't need to know about this; but should
     * you have some reason to take the global GDK lock yourself, you can
     * access it via this field.
     */
    /*
     * If you're not a java-gnome bindings hacker whoreally knows what they're
     * doing, DO NOT TOUCH THIS.
     */
    public static final Lock lock;

    static {
        lock = new Lock();
    }

    /**
     * An inner class for the purely cosmetic purpose of giving an explicit
     * name to the global GDK lock. By using this, we end up having
     * 
     * <pre>
     * &quot;waiting on ..., a Gdk$Lock&quot;
     * </pre>
     * 
     * appear in thread dumps. Otherwise, it could just as well have been
     * java.lang.Object. There is no reason to ever instantiate one of these
     * yourself, so there's no public constructor. In fact, you probably can
     * forget you ever read this.
     * 
     * @author Andrew Cowie
     */
    public static final class Lock
    {
        private Lock() {}
    }

    /*
     * Utility class. No instantiation.
     */
    private Gdk() {}
}
