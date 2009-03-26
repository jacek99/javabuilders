/*
 * GtkEntryOverride.c
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
#include "org_gnome_gtk_GtkEntryOverride.h"


JNIEXPORT void JNICALL
Java_org_gnome_gtk_GtkEntryOverride_gtk_1entry_1set_1inner_1border
(
	JNIEnv* env,
	jclass cls,
	jlong _self,
	jint _left,
	jint _right,
	jint _top,
	jint _bottom
)
{
	GtkEntry* self;
	GtkBorder border = { 0, };

	// convert parameter self
	self = (GtkEntry*) _self;

	// convert parameters
	border.left = (gint) _left;
	border.right = (gint) _right;
	border.top = (gint) _top;
	border.bottom = (gint) _bottom;
	
	// call function
	gtk_entry_set_inner_border(self, &border);

	// cleanup parameter self

	// cleanup parameters
}

