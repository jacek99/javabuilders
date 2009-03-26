/*
 * GtkTextIterOverride.c
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd, and Others
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
#include "org_gnome_gtk_GtkTextIterOverride.h"

/*
 * Allocates a GtkTextIter by calling the boxed's _copy() function on a local
 * struct variable. See GtkTreeIterOverride.c for discussion and possible
 * remedies.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_gtk_GtkTextIterOverride_gtk_1text_1iter_1new
(
	JNIEnv* env,
	jclass cls
)
{
	GtkTextIter blank = { 0, };
	GtkTextIter* result;
	
	// blank is allocated locally on the stack

	// copy blank
	result = gtk_text_iter_copy(&blank);

	// and finally
	return (jlong) result;
}
