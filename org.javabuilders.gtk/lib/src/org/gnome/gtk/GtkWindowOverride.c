/*
 * GtkWindow.c
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */

/*
 * We run into problems with the default GtkWindow memory management.
 * The default behaviour for 'delete-event' is to propegate to the dispose
 * code path, which among other things, releases all its child GtkWidgets.
 * The GtkWindow itself is not released, however, because we could still hold
 * a valid ToggleRef to it if we still have a reference on the Java side to
 * the Java side Proxy; show()ing the Window again is possible, but if you do
 * its contents will be gone.
 * 
 * To work around this we use a bit of GTK hackery. First we block
 * 'delete-event' from propegating into dispose, and instead just hide() the
 * window. Then we take advantage of there is an internal property
 * called GtkWindow->has_user_ref_count. By overriding it to 0 we can tell
 * GTK that we are managing the Ref to the GtkWindow representing it being on
 * screen, and we can safely drop GTK's Ref to the GtkWindow. Finally, in
 * operation, we restore this Ref count on windows that are show()n and
 * re-remove it on hide(). Thus a hidden GtkWindow that is also not strongly
 * referenced from any Java code will be eligigble for cleanup. 
 * 
 * Thanks to Owen Taylor for having researched these scenarios and in
 * particular for having verified that the 'hide' signal is indeed emitted by
 * gtk_widget_dispose(). The original suggestion to leverage the 'show' and
 * 'hide' signals was from Vreixo Formoso. 
 */

#include <jni.h>
#include <gtk/gtk.h>
#include "bindings_java.h"
#include "org_gnome_gtk_GtkWindowOverride.h"

/*
 * When we hit a 'delete-event' signal, hide the window and block further
 * signal emission, preventing the native dispose hierarchy from running. It
 * is critical that this be connected to run "after", otherwise standard GTK
 * expected behaviours will be subverted.
 */
/*
 * Signature the prototype of a (*delete-event) callback, meeting the
 * requirements of the third parameter to g_signal_connect_after() for signal
 * 'delete-event'.
 */
static gboolean
window_delete_handler
(
	GtkWidget* object,
	gpointer user_data
)
{
	if (DEBUG_MEMORY_MANAGEMENT) {
		g_printerr("mem: delete caught for\t\t%s\n", bindings_java_memory_pointerToString(object));
	}
	gtk_widget_hide(object);
	
	return TRUE;
}

/*
 * Signature the prototype of a (*GSourceFunc) callback, meeting the
 * requirements of the third argument to gdk_threads_add_timeout_full()
 */
static gboolean
window_hide_deref
(
	gpointer data
)
{
	GtkWidget* window;
	
	window = (GtkWidget*) data;
	
	g_object_unref(window);
	return FALSE;
}


/*
 * On hide, drop a Ref count. This replaces the built in behaviour of GTK but
 * is safe becuase we have told GtkWindow that we are holding the user Ref count
 * to the object.
 */
/*
 * Signature the prototype of a (*hide) callback, meeting the requirements of
 * the third parameter to g_signal_connect() for signal 'hide'.
 */
static void
window_hide_handler
(
	GtkWidget* widget,
	gpointer user_data
)
{
	if (DEBUG_MEMORY_MANAGEMENT) {
		g_printerr("mem: hide caught for\t\t%s\n", bindings_java_memory_pointerToString(widget));
	}
	gdk_threads_add_timeout_full(G_PRIORITY_LOW, 100, window_hide_deref, widget, NULL);
}


/*
 * On show, restore a Ref count.
 */
/*
 * Signature the prototype of a (*show) callback, meeting the requirements
 * of the third parameter to g_signal_connect() for signal 'show'.
 */
static void
window_show_handler
(
	GtkWidget* widget,
	gpointer user_data
)
{
	if (DEBUG_MEMORY_MANAGEMENT) {
		g_printerr("mem: show caught for\t\t%s\n", bindings_java_memory_pointerToString(widget));
	}
	g_object_ref(widget);
}


/**
 * Implements
 *   org.gnome.gtk.GtkWindowOverride.gtk_window_drop_user_ref(long self)
 * called from
 *   org.gnome.gtk.GtkWindowOverride.dropUserRef(Window self)
 * called from all
 *   org.gnome.gtk.Window.<init>()
 * constructors after instantiation in order to change memory management
 * behaviour by changing has_user_ref_count field and dropping a Ref count.  
 */
JNIEXPORT void JNICALL
Java_org_gnome_gtk_GtkWindowOverride_gtk_1window_1drop_1user_1ref
(
	JNIEnv* env,
	jclass cls,
	jlong _self
)
{
	GtkWindow* self;

	// convert parameter self
	self = (GtkWindow*) _self;

	/*
	 * Hook up our custom overrides to unref on 'hide' and ref on 'show',
	 * and to block 'delete-event' from propegating and to instead hide() 
	 * only. 
	 */

	g_signal_connect_after(self, "delete-event", G_CALLBACK(window_delete_handler), NULL); 
	g_signal_connect(self, "hide", G_CALLBACK(window_hide_handler), NULL);
	g_signal_connect(self, "show", G_CALLBACK(window_show_handler), NULL);

	/*
	 * Now. force the behaviour change on GtkWindow. We reconfigure its
	 * memory management mode, and then drop the reference to self that
	 * GTK places in GtkWindows.
	 */

	self->has_user_ref_count = FALSE;
	g_object_unref(self);

	// cleanup parameter self
}
