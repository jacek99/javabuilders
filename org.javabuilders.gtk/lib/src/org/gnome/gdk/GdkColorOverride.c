/*
 * GdkColorOverride.c
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
#include <gdk/gdk.h>
#include "bindings_java.h"
#include "org_gnome_gdk_GdkColorOverride.h"

/*
 * TODO This should really be replaced with generated code once the codegen
 * takes care of allocating Boxeds, although having the setters direct in the
 * constructor is handy.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_gdk_GdkColorOverride_gdk_1color_1new
(
	JNIEnv* env,
	jclass cls,
	jint _red,
	jint _green,
	jint _blue
)
{
	GdkColor blank = { 0, };
	GdkColor* result;
	
	// blank is allocated locally on the stack
	
	blank.red = (gint) _red;
	blank.green = (gint) _green;
	blank.blue = (gint) _blue;

	// copy blank
	result = gdk_color_copy(&blank);

	// and finally
	return (jlong) result;
}
