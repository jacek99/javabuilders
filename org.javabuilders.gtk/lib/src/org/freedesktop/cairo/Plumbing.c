/*
 * Plumbing.c
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd and Others
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */

#include <jni.h>
#include <cairo.h>
#include "bindings_java.h"
#include "org_freedesktop_cairo_Plumbing.h"

/*
 * Implements
 *   org.freedesktop.cairo.Plumbing.createPattern(long pointer)
 */
JNIEXPORT jobject JNICALL
Java_org_freedesktop_cairo_Plumbing_createPattern
(
	JNIEnv* env,
	jclass cls,
	jlong _pointer
)
{
	cairo_pattern_t* pattern;
	jclass found;
	static jclass SolidPattern = NULL;
	static jclass SurfacePattern = NULL;	
	static jclass LinearPattern = NULL;	
	static jclass RadialPattern = NULL;	
	jclass type;
	jmethodID constructor;
	jobject proxy;
	
	// convert pointer
	pattern = (cairo_pattern_t*) _pointer;
	
	switch (cairo_pattern_get_type(pattern)) {
	case CAIRO_PATTERN_TYPE_SOLID:
		if (SolidPattern == NULL) {
			found = (*env)->FindClass(env, "org/freedesktop/cairo/SolidPattern");
			SolidPattern = (*env)->NewGlobalRef(env, found);
		}
		type = SolidPattern;
		break;

	case CAIRO_PATTERN_TYPE_SURFACE:
		if (SurfacePattern == NULL) {
			found = (*env)->FindClass(env, "org/freedesktop/cairo/SurfacePattern");
			SurfacePattern = (*env)->NewGlobalRef(env, found);
		}
		type = SurfacePattern;
		break;

	case CAIRO_PATTERN_TYPE_LINEAR:
		if (LinearPattern == NULL) {
			found = (*env)->FindClass(env, "org/freedesktop/cairo/LinearPattern");
			LinearPattern = (*env)->NewGlobalRef(env, found);
		}
		type = LinearPattern;
		break;

	case CAIRO_PATTERN_TYPE_RADIAL:		
		if (RadialPattern == NULL) {
			found = (*env)->FindClass(env, "org/freedesktop/cairo/RadialPattern");
			RadialPattern = (*env)->NewGlobalRef(env, found);
		}
		type = RadialPattern;
		break;

	default:
		g_critical("Unimplemented pattern type");
		return NULL;
	}
	if (type == NULL) {
		bindings_java_throw(env, "FindClass() failed");
		return NULL;
	}

	constructor = (*env)->GetMethodID(env, type, "<init>", "(J)V");
	if (constructor == NULL) {
		g_error("Constructor methodID not found");
		return NULL;
	}
	
	proxy = (*env)->NewObject(env, type, constructor, _pointer);
	return proxy;
}


/*
 * Implements
 *   org.freedesktop.cairo.Plumbing.createSurface(long pointer)
 */
JNIEXPORT jobject JNICALL
Java_org_freedesktop_cairo_Plumbing_createSurface
(
	JNIEnv* env,
	jclass cls,
	jlong _pointer
)
{
	cairo_surface_t* surface;
	jclass found;
	static jclass ImageSurface = NULL;
	static jclass XlibSurface = NULL;
	static jclass PdfSurface = NULL;
	static jclass SvgSurface = NULL;
	static jclass UnknownSurface = NULL;
	jclass type;
	jmethodID constructor;
	jobject proxy;

	// convert pointer
	surface = (cairo_surface_t*) _pointer;

	switch (cairo_surface_get_type(surface)) {
	case CAIRO_SURFACE_TYPE_IMAGE:
		if (ImageSurface == NULL) {
			found = (*env)->FindClass(env, "org/freedesktop/cairo/ImageSurface");
			ImageSurface = (*env)->NewGlobalRef(env, found);
		}
		type = ImageSurface;
		break;
		
	case CAIRO_SURFACE_TYPE_XLIB:
		if (XlibSurface == NULL) {
			found = (*env)->FindClass(env, "org/freedesktop/cairo/XlibSurface");
			XlibSurface = (*env)->NewGlobalRef(env, found);
		}
		type = XlibSurface;
		break;

        case CAIRO_SURFACE_TYPE_PDF:
		if (PdfSurface == NULL) {
			found = (*env)->FindClass(env, "org/freedesktop/cairo/PdfSurface");
			PdfSurface = (*env)->NewGlobalRef(env, found);
		}
		type = PdfSurface;
		break;

        case CAIRO_SURFACE_TYPE_SVG:
		if (SvgSurface == NULL) {
			found = (*env)->FindClass(env, "org/freedesktop/cairo/SvgSurface");
			SvgSurface = (*env)->NewGlobalRef(env, found);
		}
		type = SvgSurface;
		break;
 
	default:
		/*
		 * This is an unusual scenario. Normally in java-gnome if we
		 * don't know the type that's a fatal error (and on purpose; if
		 * we haven't got a concrete Proxy subclass for someone, that's
		 * it). In Cairo, however, there are a number of cases where
		 * internal types are exposed (notably MetaSurface, created if
		 * you call createSimilar() on a vector backend) for which
		 * there is no publicly available identification. So
		 * UnknownSurface it is. This, however, obscures the real error
		 * condition of needing to add a block to this switch statement
		 * for a newly covered type.
		 */
		if (UnknownSurface == NULL) {
			found = (*env)->FindClass(env, "org/freedesktop/cairo/UnknownSurface");
			UnknownSurface = (*env)->NewGlobalRef(env, found);
		}
		type = UnknownSurface;
	}
	if (type == NULL) {
		bindings_java_throw(env, "FindClass() failed");
		return NULL;
	}

	constructor = (*env)->GetMethodID(env, type, "<init>", "(J)V");
	if (constructor == NULL) {
		bindings_java_throw(env, "Constructor methodID not found");
		return NULL;
	}

	proxy = (*env)->NewObject(env, type, constructor, _pointer);
	return proxy;
}
