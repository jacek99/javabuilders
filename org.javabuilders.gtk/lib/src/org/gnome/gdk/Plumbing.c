/*
 * Plumbing.c
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd and Others
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
#include "org_gnome_gdk_Plumbing.h"

/*
 * Implements
 *   org.gnome.gdk.Plumbing.getEventTypeOrdinal(long pointer)
 * 
 * Assuming that the supplied pointer is a GdkEvent, then regardless of which
 * element of the union the actual structure is, the first field is always of
 * GdkEventType.
 * 
 * See http://developer.gnome.org/doc/API/2.0/gdk/gdk-Event-Structures.html#GdkEvent 
 */
JNIEXPORT jint JNICALL
Java_org_gnome_gdk_Plumbing_getEventTypeOrdinal
(
	JNIEnv *env,
	jclass cls,
	jlong _pointer
)
{
	GdkEvent* event;
	GdkEventType type;
	
	event = (GdkEvent*) _pointer;
	
	type = event->type;
	
	return (jint) type;
}
