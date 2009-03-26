/*
 * GtkClipboardOverride.c
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
#include "org_gnome_gtk_GtkClipboardOverride.h"

JNIEXPORT jlong JNICALL
Java_org_gnome_gtk_GtkClipboardOverride_gtk_1clipboard_1get
(
	JNIEnv* env,
	jclass cls
)
{
	GtkClipboard* result;
	
	result = gtk_clipboard_get(GDK_SELECTION_CLIPBOARD);
	
	return (jlong) result;
}
