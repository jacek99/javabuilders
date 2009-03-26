/*
 * CairoMatrixOverride.c
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
#include "org_freedesktop_cairo_CairoMatrixOverride.h"

/*
 * Allocates a CairoMatrix by calling the boxed's _copy() function on a local
 * struct variable. See GtkTreeIterOverride.c for discussion and possible
 * remedies.
 */
JNIEXPORT jlong JNICALL
Java_org_freedesktop_cairo_CairoMatrixOverride_cairo_1matrix_1init_1identity
(
	JNIEnv* env,
	jclass cls
)
{
	cairo_matrix_t* result;
	
	// allocate a matrix. No need to 0 it
	result = g_slice_new(cairo_matrix_t);

	// because we call an initializing function
	cairo_matrix_init_identity(result);

	// and finally
	return (jlong) result;
}

JNIEXPORT void JNICALL
Java_org_freedesktop_cairo_CairoMatrixOverride_cairo_1matrix_1free
(
	JNIEnv* env,
	jclass cls,
	jlong _self
)
{
	cairo_matrix_t* self;

	// convert self
	self = (cairo_matrix_t*) _self;
	
	// call function
	g_slice_free(cairo_matrix_t, self);
}
