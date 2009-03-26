/*
 * GtkTreeModelFilterOverride.c
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */

#include <jni.h>
#include <gtk/gtk.h>
#include "bindings_java.h"
#include "org_gnome_gtk_GtkTreeModelFilterOverride.h"
#include <gtk/gtkmarshal.h>

static guint signalID = 0;

/**
 * Find out whether a given row is to be visible by emitting our custom
 * "visible" signal, to which the we have connected a Java side signal handler 
 * returning boolean.
 * 
 * Note that the reference to self is prepended by the g_signal_emit() code
 * automatically. You need it in the callback signature, but not in the .defs
 * or in the parameters here.
 */
/*
 * Meets the signature requirement of (*GtkTreeModelFilterVisibleFunc) in
 * order to be the second parameter to the call to 
 * gtk_tree_model_filter_set_visible_func() below.
 */
static gboolean
emit_visible
(
	GtkTreeModel *child,
	GtkTreeIter *iter,
	gpointer instance
)
{
	gboolean result;
	
	g_signal_emit_by_name(GTK_TREE_MODEL_FILTER(instance), "visible", child, iter, &result);
	
	return result;
}

/**
 * called from
 *   org.gnome.gtk.GtkTreeModelFilterOverride.setVisibleFunc()
 * called from
 *   org.gnome.gtk.TreeModelFilter.setVisibleCallback()
 *
 */
JNIEXPORT void JNICALL
Java_org_gnome_gtk_GtkTreeModelFilterOverride_gtk_1tree_1model_1filter_1set_1visible_1func
(
	JNIEnv* env,
	jclass cls,
	jlong _self
)
{
	GtkTreeModelFilter* self;

	// convert parameter self
	self = (GtkTreeModelFilter*) _self;

	if (signalID == 0) {
		signalID = g_signal_new("visible",
					GTK_TYPE_TREE_MODEL_FILTER,
					G_SIGNAL_ACTION,
					0,
					NULL,
					NULL, 
					NULL, // note 1
					G_TYPE_BOOLEAN,
					2,    // note 2
					GTK_TYPE_TREE_MODEL,
					GTK_TYPE_TREE_ITER);
		/*
		 * Notes:
		 *
		 * 1. Don't need to register a marshall function; the
		 * subsequent g_signal_connect() as invoked by our
		 * TreeModelFilter's setVisibleHandler() will register a 
		 * dynamic bindings_java_marshaller().
		 *
		 * 2. As ever, there is an implicit reference to self
		 * automatically added as the first parameter for the signal.
		 * Good of them, but confusing, because in this case, the 
		 * model parameter is the _child_, not the reference to self.
		 */ 
	}

	// call function
	gtk_tree_model_filter_set_visible_func(self, emit_visible, self, NULL);

	// clean up
}
