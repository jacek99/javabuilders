/*
 * GdkRectangleOverride.c
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
#include <gdk/gdk.h>
#include "bindings_java.h"
#include "org_gnome_gdk_GdkRectangleOverride.h"

/*
 * Allocator and release function for GdkRectangle structs. Most of the time
 * we are just allocating a blank, so 0 are passed knowing full well the
 * struct will be populated by the next function call. We've saved ourself
 * some trouble by having one function for both that case and the case where
 * the developer needs to create one themselves. 
 */

JNIEXPORT jlong JNICALL
Java_org_gnome_gdk_GdkRectangleOverride_gdk_1rectangle_1new
(
	JNIEnv* env,
	jclass cls,
	jint _x,
	jint _y,
	jint _width,
	jint _height
)
{
	gint x;
	gint y;
	gint width;
	gint height;
	GdkRectangle* result;
	
	// convert parameters

	x = (gint) _x;
	y = (gint) _y;
	width = (gint) _width;
	height = (gint) _height;
	
	/*
	 * This is a dynamic allocation.
	 */	
	result = g_slice_new0(GdkRectangle);

	result->x = x;
	result->y = y;
	result->width = width;
	result->height = height;
		
	// cleanup parameter self

	// and finally
	return (jlong) result;
}

JNIEXPORT void JNICALL
Java_org_gnome_gdk_GdkRectangleOverride_gdk_1rectangle_1free
(
	JNIEnv* env,
	jclass cls,
	jlong _self
)
{
	GdkRectangle* self;
	
	// convert paramter self
	self = (GdkRectangle*) _self;
	
	// call function
	g_slice_free(GdkRectangle, self);
	
	// cleanup parameter self
}
