/*
 * Plumbing.java
 *
 * Copyright (c) 2006-2008 Operational Dynamics Consulting Pty Ltd, and Others
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.glib;

import java.io.InputStream;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Properties;

import org.freedesktop.bindings.Constant;
import org.freedesktop.bindings.Proxy;
import org.gnome.gdk.Gdk;

/**
 * Translation layer class which adds the ability to connect signals to
 * GObjects. See {@link org.freedesktop.bindings.Plumbing Plumbing} for
 * utility methods which work with Proxy and Constant. <b>No one using
 * developing applications which happen to use bindings based on this package
 * should ever need to see, or subclass, this.</b>
 * 
 * @author Andrew Cowie
 * @since 4.0.0
 */
public abstract class Plumbing extends org.freedesktop.bindings.Plumbing
{
    protected static final java.lang.Object lock;

    protected Plumbing() {}

    private static final IdentityHashMap<String, String> typeMapping;

    private static final String TYPE_MAPPING = "typeMapping.properties";

    static {
        final InputStream is;
        final Properties p;

        Glib.checkInitialized();

        lock = Gdk.lock;

        typeMapping = new IdentityHashMap<String, String>(470);

        p = new Properties();

        try {
            is = loader.getResourceAsStream(TYPE_MAPPING);
            if (is == null) {
                throw new NullPointerException("InputStream for " + TYPE_MAPPING + " is null");
            }
            p.load(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\nUnable to load GType to Java class mapping file " + TYPE_MAPPING);

            System.exit(2);
        }

        for (Iterator<java.lang.Object> iter = p.keySet().iterator(); iter.hasNext();) {
            String gType = (String) iter.next();
            String javaClass = (String) p.get(gType);
            registerType(gType, javaClass);
        }
    }

    /**
     * Register a GType name as corresponding to the given Proxy subclass.
     * This form, where a class name is specified by a string, is used by the
     * generated translation layer classes to get around the case where a hand
     * written wrapper class presenting a public API has not been (or will not
     * be) written.
     */
    protected static void registerType(String nativeName, String javaClassName) {

        assert ((nativeName != null) && (!nativeName.equals(""))) : "GType name being registered cannot be null or empty";
        assert (javaClassName != null) : "Java class name being registered cannot be null";

        typeMapping.put(nativeName.intern(), javaClassName.intern());
    }

    /**
     * Register a GType name as corresponding to the given Proxy subclass. Use
     * this form when you're hand writing a translation layer class - after
     * all, you should also have implemented the public class that goes along
     * with it!
     */
    protected static void registerType(String nativeName, Class<?> javaClass) {
        assert (javaClass != null) : "Java class being registered cannot be null";
        registerType(nativeName, javaClass.getName());
    }

    /**
     * Retrieve an array of appropriate Java Object for the given array of
     * pointers.
     */
    protected static Object[] objectArrayFor(long[] pointers, Object[] objects) {
        if (pointers == null) {
            return null;
        }

        for (int i = 0; i < pointers.length; ++i) {
            objects[i] = objectFor(pointers[i]);
        }
        return objects;
    }

    /**
     * Retrieve an array of appropriate Java Boxeds for the given array of
     * pointers.
     */
    protected static Boxed[] boxedArrayFor(final Class<?> type, final long[] pointers, Boxed[] boxeds) {
        if (pointers == null) {
            return null;
        }

        for (int i = 0; i < pointers.length; ++i) {
            boxeds[i] = boxedFor(type, pointers[i]);
        }
        return boxeds;
    }

    /**
     * Retrieve an appropriate Java Boxed for this pointer.
     * 
     * @throw ClassCastException if the GType pointed by given pointer is not
     *        a GBoxed.
     */
    protected static Boxed boxedFor(Class<?> type, final long pointer) {
        Boxed proxy;

        if (pointer == 0L) {
            return null;
        }

        proxy = (Boxed) createPointer(type, pointer);
        return proxy;
    }

    /**
     * Retrieve the Proxies corresponding to several pointers to GObject, and
     * fill an Object array with them.
     * 
     * @see #objectFor(long)
     */
    protected static void fillObjectArray(Object[] objects, long[] pointers) {
        if (pointers == null) {
            return;
        }
        for (int i = 0; i < pointers.length; ++i) {
            objects[i] = objectFor(pointers[i]);
        }
    }

    /**
     * Retrieve the Proxies corresponding to several pointers to GBoxed, and
     * fill an Boxed array with them.
     * 
     * @see #boxedFor(Class, long)
     */
    protected static void fillBoxedArray(Class<?> type, Boxed[] boxeds, long[] pointers) {
        if (pointers == null) {
            return;
        }
        for (int i = 0; i < pointers.length; ++i) {
            boxeds[i] = boxedFor(type, pointers[i]);
        }
    }

    /**
     * Retrieve an appropriate Java Proxy for a pointer to a GObject. This
     * will return the Proxy instance already created using a real constructor
     * if one was created Java side; where no Proxy exists it looks up the
     * underlying <code>GType</code> and does its best to create it.
     * 
     * @return null if the <code>pointer</code> argument is <code>0x0</code>,
     *         <i>which can happen after looking up a valid GValue which turns
     *         out to contains a <code>NULL</code> pointer value for a GObject
     *         it is carrying.</i>
     * @throw UnsupportedOperationException if no Java class has been
     *        registered for this <code>GType</code>.
     * 
     * @see org.freedesktop.bindings.Plumbing#instanceFor(long)
     */
    protected static Object objectFor(long pointer) {
        Proxy proxy;

        if (pointer == 0L) {
            return null;
        }

        proxy = instanceFor(pointer);

        if (proxy != null) {
            /*
             * A Proxy exists for this. Great! Simply return it.
             */
            return (Object) proxy;
        } else {
            /*
             * Oh. A proxy doesn't exist (yet). Okay. Lookup the GType name,
             * and see if we've got it registered. If we do, then we can go
             * ahead and try to instantiate a Proxy. If not, blow an
             * exception.
             */
            final String name;
            final Class<?> type;

            /*
             * We intern the returned GType name string to reduce memory
             * pressure and to permit lookup by identity.
             */

            name = GObject.typeName(pointer);

            if (name.equals("")) {
                throw new IllegalStateException("\n" + "GType name lookup failed");
            }

            /*
             * Now we handle the expected case of being able to get the
             * instance for the Proxy subclass.
             */

            type = lookupType(name);

            proxy = (Proxy) createPointer(type, pointer);
            return (Object) proxy;
        }
    }

    /**
     * Retrieve a Pointer object corresponding to a <code>GValue</code>. This
     * should only be needed by the property getter functions.
     * 
     * <p>
     * <i><code>GValues</code> are special... and a pain in the ass.
     * Superficially the structure seems like <code>GObject</code> and
     * friends, starting with a <code>GType</code>, but this is misleading
     * because <code>GObject</code> has a <b>pointer</b> to a
     * <code>GTypeInstance</code> which contains the <code>GType</code> first.
     * This means that we cannot use the same code path to figure out what the
     * type name of a pointer is that we do in objectFor() above.</i>
     */
    protected static Value valueFor(long pointer) {
        Value obj;

        if (pointer == 0L) {
            return null;
        }

        obj = new Value(pointer, true);
        return obj;
    }

    /**
     * Get the Class object that this supplied name maps to.
     */
    protected final static Class<?> lookupType(String name) {
        final String java;

        java = typeMapping.get(name);

        if (java == null) {
            /*
             * No class indicates that we don't have any information about
             * this native type and how to map it to Java. So,
             */
            throw new FatalError("\n" + "No mapping for " + name + " (yet!)");
        }
        try {
            return Class.forName(java, true, loader);
        } catch (ClassNotFoundException cnfe) {
            throw new FatalError("\n" + "Mapping exists, but class not found! " + cnfe.getMessage());
        }
    }

    /**
     * Connect a signal handler to a GObject.
     * 
     * @param instance
     *            the Object Proxy you want to connect the signal to
     * @param handler
     *            the object implementing the Signal subinterface we defined
     *            in one of our public classes.
     * @param name
     *            the name of the signal being connected. <i>By design, the
     *            literal name of the interface being connected is the name of
     *            the signal in capitals by our naming convention.
     *            Nevertheless, you have to explicitly name the signal in
     *            because self-delegation means we never quite know which is
     *            being hooked up. Because all the calls to connectSignal()
     *            will be in generated code, the signal name is sourced from
     *            .defs data and should always be correct!</i>
     * @param after
     *            Whether this handler should be registered before or after
     *            the default handler. The default is to connect before, so
     *            <code>false</code>. Any time you use this, you <b>must</b>
     *            name the public method <code>connectAfter()</code>.
     */
    /*
     * In this case we need to go back to GObject in order to be able to make
     * the native call to our g_signal_connect() wrapper.
     */
    protected static final void connectSignal(Object instance, Signal handler, Class<?> receiver,
            String name, boolean after) {
        GObject.g_signal_connect(pointerOf(instance), handler, receiver, name, after);
        instance.addHandler(handler);
    }

    /*
     * FIXME this is a pathetic hack, only here to permit compilation to
     * succeed while we work out a better means of handling the cross-package
     * visibility issue. Entities are the various Cairo types.
     */
    protected static Proxy entityFor(Class<?> type, long pointer) {
        throw new UnsupportedOperationException(
                "Unfortunately, we haven't figured out how to call the method in Cairo's Plumbing yet");
    }

    /**
     * This (quite inefficiently) goes the reverse direction as compared to
     * lookupType() [those based on g_type_name()], from a known class name to
     * a GType name.
     */
    /*
     * At time of writing, this isonly being used to create GValues containing
     * enums for use in GObject property setting. For whatever reason GValue
     * requires something more derived than G_TYPE_ENUM.
     * 
     * FUTURE If this becomes a hotspot at all, replace with a Map going the
     * reverse direction as typeMapping does presenty.
     */
    protected static String typeOf(Class<? extends Constant> cls) {
        final String name;
        final Collection<String> values;

        name = cls.getName();

        values = typeMapping.values();

        if (!values.contains(name)) {
            throw new IllegalArgumentException("Class " + name + " not in typeMapping");
        }

        for (String gType : typeMapping.keySet()) {
            if (typeMapping.get(gType) == name) {
                return gType;
            }
        }

        throw new IllegalStateException("Reverse type lookup failed");
    }
}
