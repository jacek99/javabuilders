/*
 * FatalError.java
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.glib;

/**
 * Misuse of the underlying library. This is thrown as a result of a
 * programmer carrying out an operation which the native library considers
 * illegal. In an ideal world we would inhibit this before we even get to the
 * native side, but in this case we didn't, and their defence mechanisms have
 * caught the problem.
 * 
 * <p>
 * By definition, a <code>CRITICAL</code> has to be fatal; the application is
 * known to be in an undefined state after one has been emitted. While some
 * programs allow the user to carry on in blissful ignorance, these warnings
 * indicate a programmer doing something wrong, and that needs fixing.
 * 
 * <p>
 * The message has, therefore, been thrown as a Java Error. This gets you a
 * stack trace at the place where the problem occurred, and that's how we
 * identify problems in the Java world.
 * 
 * <p>
 * <i><b>This is not the wrapper around <code>GError</code>!</b></i>
 * 
 * <p>
 * <i>This class is our way of exposing fatal error conditions in a
 * Java-appropriate fashion. <code>GError</code>, on the other hand, is GLib's
 * mechanism for returning conditions that the developer can ask the user for
 * a decision about. Incidentally, we do not expose those directly in the
 * java-gnome public API; where they occur we propagate an appropriate Java
 * checked exception instead. See {@link GlibException}</i>.
 * 
 * @author Andrew Cowie
 * @since 4.0.7
 */
/*
 * It was very tempting to have this class extend CoderMalfunctionError. What
 * a great name. "Problem exists between Keyboard and Chair". I love it! :)
 */
public class FatalError extends org.freedesktop.bindings.FatalError
{
    private static final long serialVersionUID = 1;

    protected FatalError() {
        super();
    }

    protected FatalError(String msg) {
        super(msg);
    }
}
