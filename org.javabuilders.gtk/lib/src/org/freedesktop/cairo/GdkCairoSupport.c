/*
 * GdkCairoSupport.c
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
#include <cairo.h>
#include "bindings_java.h"
#include "org_freedesktop_cairo_GdkCairoSupport.h"

/**
 * This accesses gdk_cairo_create(), a utility function in GDK allowing you to
 * get the Cairo cairo_t for a given GdkDrawable. We have exposed this in our
 * bindings as a constructor to Context.
 */
JNIEXPORT jlong JNICALL
Java_org_freedesktop_cairo_GdkCairoSupport_gdk_1cairo_1create
(
	JNIEnv* env,
	jclass cls,
	jlong _drawable
)
{
	GdkDrawable* drawable;
	cairo_t* result;

	// convert drawable
	drawable = (GdkDrawable*) _drawable;

	// call function
	result = gdk_cairo_create(drawable);

	// cleanup parameter drawable

	// and finally
	return (jlong) result;
}


JNIEXPORT void JNICALL
Java_org_freedesktop_cairo_GdkCairoSupport_gdk_1cairo_1set_1source_1pixbuf
(
        JNIEnv* env,
        jclass cls,
        jlong _context,
        jlong _pixbuf,
        jdouble _x,
        jdouble _y
)
{
        cairo_t* context;
        GdkPixbuf* pixbuf;
        double x;
        double y;
        
        // convert context
        context = (cairo_t*) _context;
        
        // convert pixbuf
        pixbuf = (GdkPixbuf*) _pixbuf;

        // convert x
        x = (double) _x;
        
        // convert x
        y = (double) _y;
        
        // call function
        gdk_cairo_set_source_pixbuf(context, pixbuf, x, y);

        // cleanup parameter context

        // cleanup parameter pixbuf

        // cleanup parameter x

        // cleanup parameter y
}
