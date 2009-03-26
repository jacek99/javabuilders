/*
 * GObject.c
 *
 * Copyright (c) 2006-2007 Operational Dynamics Consulting Pty Ltd and Others
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */

/*
 * The code for signal marshaling was completely rewritten by Andrew Cowie
 * during the 4.0 re-engineering effort. Working with JNI and with GLib is
 * very idiomatic, however, and the sequence of calls employed here was drawn
 * directly from the example set by the java-gnome 2.x project in their
 * glib-java/src/jni/org_gnu_glib_GObject.c. We are certainly indebted to the
 * previous hackers for having worked out these techniques, an echo of which
 * appears here in our implementation of g_signal_connect()
 */

#include <glib.h>
#include <glib-object.h>
#include <jni.h>
#include "bindings_java.h"
#include "org_gnome_glib_GObject.h"


/**
 * Implements
 *   org.gnome.glib.GObject.g_type_name(long object)
 * called from
 *   org.gnome.glib.Plumbing.instanceForObject(long pointer)
 * also made available via
 *   org.gnome.glib.GObject.typeName(Object object);
 */
JNIEXPORT jstring JNICALL
Java_org_gnome_glib_GObject_g_1type_1name
(
	JNIEnv *env,
	jclass cls,
	jlong _object
)
{
	GObject* object;
	const gchar* name;

	// translate value
	object = (GObject*) _object;
	
	// call function & macro	
	name = g_type_name(G_TYPE_FROM_INSTANCE(object));
	//g_print("GObject (%ld): %s\n", (long) G_OBJECT_TYPE(object), name);
	
	// return name. Guard against NullPointerException by returning an
	// empty string instead of null
	return (*env)->NewStringUTF(env, (name != NULL ? name : "\0"));
}


/**
 * Implements
 *   org.gnome.glib.GObject.g_object_set_property(long instance, String name, long value)
 * called from
 *   org.gnome.glib.GObject.setProperty(Object instance, String name, Value value)
 */
JNIEXPORT void JNICALL
Java_org_gnome_glib_GObject_g_1object_1set_1property
(
	JNIEnv *env,
	jclass cls,
	jlong _instance,
	jstring _name,
	jlong _value
)
{
	GObject* instance;
	gchar* name;
	GValue* value;
	
	// translate instance
	instance = (GObject*) _instance;
	
	// translate name
	name = (gchar*) (*env)->GetStringUTFChars(env, _name, NULL);
	if (name == NULL) {
		return; /* OutOfMemoryError already thrown */
	}
	
	// translate value
	value = (GValue*) _value;
	
	// call	method
	g_object_set_property(instance, name, value);

	// clean up name
	(*env)->ReleaseStringUTFChars(env, _name, name);
}

/**
 * Implements
 *   org.gnome.glib.GObject.g_object_get_property(long instance, String name)
 * called from
 *   org.gnome.glib.GObject.getProperty(Object instance, String name)
 * 
 * The idea of using g_object_class_find_property() to get at an appropriate
 * GType for the empty GValue we need to pass as an out parameter to
 * g_object_get_property() is borrowed from java-gnome 2.x's implementation.  
 */
JNIEXPORT jlong JNICALL Java_org_gnome_glib_GObject_g_1object_1get_1property
(
	JNIEnv* env,
	jclass cls,
	jlong _instance,
	jstring _name
)
{
	GObject* instance;
	gchar* name;
	GValue* value;
	GParamSpec* spec;
	
	// translate instance
	instance = (GObject*) _instance;
	
	// translate name
	name = (gchar*) (*env)->GetStringUTFChars(env, _name, NULL);
	if (name == NULL) {
		return 0L; /* OutOfMemoryError already thrown */
	}

	// initialize value
	spec = g_object_class_find_property(G_OBJECT_GET_CLASS(instance), name);
	if (spec == NULL) {
		bindings_java_throw(env, "GParamSpec for %s was NULL", name);
		return 0L;
	}

	value =	g_slice_new0(GValue);
	g_value_init(value, spec->value_type);

	// call	method
	g_object_get_property(instance, name, value);

	// clean up name
	(*env)->ReleaseStringUTFChars(env, _name, name);
	
	/*
	 * we do not need to clean up value; it will eventually be underneath a
	 * Fundamental extends Value extends Proxy which will ultimately call
	 * g_value_free() when it is ready to be disposed.
	 */
	 	
	return (jlong) value;
}


/**
 * Implements
 *   org.gnome.glib.GObject.g_signal_connect(long instance, Object handler, String name, boolean after)
 * called from
 *   org.gnome.glib.Plumbing.connectSignal(Object instance, Signal handler, String name, boolean after)
 * called from
 *   <generated package scope classes>.connect(Object instance, Signal handler, boolean after)
 *
 * This is where the magic to create a GClosure and hook it up to the GSignal
 * handling mechanisms is taken care of. A reference is created to the passed
 * Java object which is used as the callback when the signal is fired.
 */
JNIEXPORT void JNICALL
Java_org_gnome_glib_GObject_g_1signal_1connect
(
	JNIEnv *env,
	jclass cls,
	jlong _instance,
	jobject _handler,
	jobject _receiver,
	jstring _name,
	jboolean _after
)
{
	GObject* instance;
  	gchar* name;
  	gboolean after;

  	guint id;
  	GQuark detail = 0;
  	GClosure* closure;
  	gboolean ok;

	// translate instance  	
  	instance = (GObject*) _instance;

	// translate the signal name
	name = (gchar*) (*env)->GetStringUTFChars(env, _name, NULL);	

	// translate after  	
  	after = (gboolean) _after;

	/*
	 * Lookup the signal information. We use this rather than g_signal_lookup() because
	 * it allows us to sidestep the issue of detailed signal names.
	 */

	ok = g_signal_parse_name(name, G_OBJECT_TYPE(instance), &id, &detail, TRUE);
	
	if (!ok) {
		bindings_java_throw(env, "Unknown signal name %s for object %s", name, G_OBJECT_TYPE_NAME(instance));
    		return;
  	}
  	
  	closure = bindings_java_closure_new(env, _handler, (jclass) _receiver, name, id);
  	if (closure == NULL) {
  		// and an exception has already been thrown
	    	return;
  	}

	// returns the handler id, but we don't need it.
	g_signal_connect_closure_by_id(instance, id, detail, closure, after);
	
	// cleanup. Not really necessary as will happen automatically in a moment.
	(*env)->ReleaseStringUTFChars(env, _name, name);
}

/**
 * Implements
 *   org.gnome.glib.GObject.g_object_add_toggle_ref(long reference, Object target)
 * called from
 *   org.gnome.glib.GObject.addToggleRef(Object reference)
 * called from
 *   org.gnome.glib.Object.<init>(long pointer)
 * 
 * When we make a Proxy to a GObject, we need to tell the
 * reference system about it.
 */
 
JNIEXPORT void JNICALL 
Java_org_gnome_glib_GObject_g_1object_1add_1toggle_1ref
(
	JNIEnv* env,
	jclass cls,
	jlong _reference,
	jobject _target
)
{
	GObject* reference;
	
	// translate reference
	reference = (GObject*) _reference;
	
	// call function
	bindings_java_memory_ref(env, reference, _target);
}


/**
 * Implements
 *   org.gnome.glib.GObject.g_object_remove_toggle_ref(long reference)
 * called from
 *   org.gnome.glib.GObject.removeToggleRef(Object reference)
 * called when
 *   org.gnome.glib.Object.release()
 * is invoked by a finalizer.
 */
JNIEXPORT void JNICALL 
Java_org_gnome_glib_GObject_g_1object_1remove_1toggle_1ref
(
	JNIEnv* env,
	jclass cls,
	jlong _reference
)
{
	GObject* reference;
	
	// translate reference
	reference = (GObject*) _reference;
	
	// call function
	bindings_java_memory_unref(reference);
	
	/*
	 * Which should, incidentally, dispose of this GObject if we're the
	 * only one still holding a reference count to it. See discussion at
	 * the JavaDoc for org.gnome.glib.Object's release() method. 
	 */
}
