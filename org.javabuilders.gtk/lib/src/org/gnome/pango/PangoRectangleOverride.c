/*
 * PangoRectangleOverride.c
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
#include <pango/pango.h>
#include "bindings_java.h"
#include "org_gnome_pango_PangoRectangleOverride.h"

/*
 * Allocator and release function for PangoRectangle structs.
 */

JNIEXPORT jlong JNICALL
Java_org_gnome_pango_PangoRectangleOverride_pango_1rectangle_1new
(
	JNIEnv* env,
	jclass cls
)
{
	PangoRectangle* result;
	
	/*
	 * This is a dynamic allocation.
	 */	
	result = g_slice_new0(PangoRectangle);

	// and finally
	return (jlong) result;
}

JNIEXPORT void JNICALL
Java_org_gnome_pango_PangoRectangleOverride_pango_1rectangle_1free
(
	JNIEnv* env,
	jclass cls,
	jlong _self
)
{
	PangoRectangle* self;
	
	// convert paramter self
	self = (PangoRectangle*) _self;
	
	// call function
	g_slice_free(PangoRectangle, self);
	
	// cleanup parameter self
}
