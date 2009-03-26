/*
 * GtkTreeIterOverride.c
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
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
#include "org_gnome_gtk_GtkTreeIterOverride.h"

/*
 * Allocates a GtkTreeIter by calling the boxed's _copy() function on a local
 * struct variable. By definition, a "boxed" is a struct. Local variables, of
 * course, all have local storage on the stack. So with the & operater you
 * can pass them to a function that takes a pointer so it can modify them; in
 * our usage we need one in pointer form so use gtk_tree_iter_copy() to get
 * us one in a manner that will be safely released by a later call to
 * gtk_tree_iter_free().
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_gtk_GtkTreeIterOverride_gtk_1tree_1iter_1new
(
	JNIEnv* env,
	jclass cls
)
{
	GtkTreeIter blank = { 0, };
	GtkTreeIter* result;
	
	// blank is allocated locally on the stack

	// copy blank
	result = gtk_tree_iter_copy(&blank);

	// and finally
	return (jlong) result;
}
