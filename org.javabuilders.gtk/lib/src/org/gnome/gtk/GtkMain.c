/*
 * GtkMain.c
 *
 * Copyright (c) 2006-2009 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */

#include <jni.h>
#include <glib.h>
#include <gdk/gdk.h>
#include <gtk/gtk.h>

#include "bindings_java.h"
#include "org_gnome_gtk_GtkMain.h"

/*
 * Implements
 *   org.gnome.gtk.Gtk.gtk_init(Object lock, String[] args)
 * called from
 *   org.gnome.gtk.Gtk.init(String[] args)
 *
 * FIXME we still have to handle returning the trimmed args array.
 */ 
JNIEXPORT void JNICALL
Java_org_gnome_gtk_GtkMain_gtk_1init
(
	JNIEnv *env,
	jclass cls,
	jobject _lock,
	jobjectArray _args
)
{
	int argc;
	char** argv;
	gint i;
	jstring _arg;
	gchar* arg;

	bindings_java_logging_init();

	bindings_java_threads_init(env, _lock);
	
	g_thread_init(NULL);
	gdk_threads_init();

	g_set_prgname("java");

	// convert args
	if (_args == NULL) {
		argc = 0;
	} else {
		argc = (*env)->GetArrayLength(env, _args);
	}
	argv = (char**) g_newa(char**, argc+1);

	for (i = 0; i < argc; i++) {
		_arg = (jstring) (*env)->GetObjectArrayElement(env, _args, i);
		arg = (gchar*)(*env)->GetStringUTFChars(env, _arg, NULL);
		argv[i+1] = arg;
	}

	/*
	 * In C, the first element in the argv is the program name from the
	 * command line. Java skips this, so we need to re-introduce a dummy 
	 * value here. This is also why it was [i+1] above.
	 */
	argv[0] = "";
	argc++;

	// call function
	gtk_init(&argc, &argv);
 
	/*
	 * Work around for what may be bug #85715. It appears that the root
	 * window is not given an initial Ref by GDK; if you call Window's
	 * getScreen() the resultant org.gnome.gdk.Screen's ToogleRef is the
	 * only ref count, and when we go through a garbage collection cycle
	 * the ref count drops to zero, resulting in an attempt to "destroy
	 * the root window" which, needless to say, GDK objects to somewhat
	 * vehemently.
	 */
	g_object_ref(gdk_screen_get_default());
}


/*
 * Implements
 *   org.gnome.gtk.Gtk.gtk_main()
 * called from
 *   org.gnome.gtk.Gtk.main()
 * 
 * Atypically we do the necessary operations to take and release the GDK lock
 * here on the JNI side; everywhere else in the library we use a Java side
 * synchronized block. This works around a strange behaviour in Eclipse and
 * hopefully results in a better debugging experience.
 *
 * Note that the main loop implicitly uses the gdk_threads_enter/leave()
 * mechanism while spinning. This means that although the Gdk$Lock monitor is
 * held upon making this call (which blocks), the lock is released briefly
 * each time the main loop iterates.
 */
JNIEXPORT void JNICALL
Java_org_gnome_gtk_GtkMain_gtk_1main
(
	JNIEnv *env,
	jclass cls
)
{
	// call function
	gdk_threads_enter();
	gtk_main();
	gdk_threads_leave();
}

/*
 * Implements
 *   org.gnome.gtk.Gtk.gtk_main_quit()
 * called from
 *   org.gnome.gtk.Gtk.mainQuit()
 */
JNIEXPORT void JNICALL
Java_org_gnome_gtk_GtkMain_gtk_1main_1quit
(
	JNIEnv *env,
	jclass cls
)
{
	// call function
	gtk_main_quit();
}


/*
 * Implements
 *   org.gnome.gtk.Gtk.gtk_events_pending()
 * called from
 *   org.gnome.gtk.Gtk.eventsPending()
 */
JNIEXPORT jboolean JNICALL
Java_org_gnome_gtk_GtkMain_gtk_1events_1pending
(
	JNIEnv *env,
	jclass cls
)
{
	gboolean result;
	
	// call function
	result = gtk_events_pending();
	
	// return result
	return (jboolean) result;	
}


/*
 * Implements
 *   org.gnome.gtk.Gtk.gtk_main_iteration_do()
 * called from
 *   org.gnome.gtk.Gtk.mainIterationDo()
 */
JNIEXPORT jboolean JNICALL
Java_org_gnome_gtk_GtkMain_gtk_1main_1iteration_1do
(
	JNIEnv *env,
	jclass cls,
	jboolean _blocking
)
{
	gboolean blocking;
	gboolean result;
	
	// translate blocking
	blocking = (gboolean) _blocking;
	
	// call function
	result = gtk_main_iteration_do(blocking);
	
	// clean up blocking
	
	// return result
	return (jboolean) result;	
}
