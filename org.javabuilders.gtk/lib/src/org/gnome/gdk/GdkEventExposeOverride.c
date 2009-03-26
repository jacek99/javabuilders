/*
 * GdkEventExposeOverride.c
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
#include "org_gnome_gdk_GdkEventExposeOverride.h"


/**
 * Access GdkEventExpose's area field, a GdkRectanle struct. Note that we
 * return a pointer to the live struct, not a copy. See GtkWidgetOverride.c
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_gdk_GdkEventExposeOverride_gdk_1event_1expose_1get_1area
(
	JNIEnv* env,
	jclass cls,
	jlong _self
)
{
	GdkRectangle* result;
	GdkEventExpose* self;

	// convert parameter self
	self = (GdkEventExpose*) _self;
	
	/*
	 * This is a dynamic allocation. This must match the release function
	 * implemented in GdkRectangleOverride.free().
	 */	
	result = g_slice_new0(GdkRectangle);
		
	result->x = self->area.x;
	result->y = self->area.y;
	result->width = self->area.width;
	result->height = self->area.height;

	// cleanup parameter self

	// and finally
	return (jlong) result;
}
