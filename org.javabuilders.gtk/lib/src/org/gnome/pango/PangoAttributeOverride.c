/*
 * PangoAttributeOverride.c
 *
 * Copyright (c) 2009 Operational Dynamics Consulting Pty Ltd
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
#include "org_gnome_pango_PangoAttributeOverride.h"

JNIEXPORT void JNICALL
Java_org_gnome_pango_PangoAttributeOverride_pango_1attribute_1set_1indexes
(
	JNIEnv* env,
	jclass cls,
	jlong _self,
	jlong _layout,
	jint _offset,
	jint _width
)
{
	PangoAttribute* self;
	PangoLayout* layout;
	gint offset;
	gint width;
	const char* text;
	char* alpha;
	char* omega;
	guint start;
	guint end;
	
	// convert paramter self
	self = (PangoAttribute*) _self;

	// convert paramter layout
	layout = (PangoLayout*) _layout;

	// convert parameter offset
	offset = (gint) _offset;

	// convert parameter width
	width = (gint) _width;

	// convert to bounds
	/*
	 * Get the text out of the layout, and then work out what
	 * the offset and offset+width work out to in byte terms.
	 */

	text = pango_layout_get_text(layout);

	alpha = g_utf8_offset_to_pointer(text, offset);
	omega = g_utf8_offset_to_pointer(text, offset + width);

	start = alpha - text;
	end = omega - text;

	// set fields
	self->start_index = start;
	self->end_index = end;
	
	// cleanup parameter self

	// cleanup parameter layout

	// cleanup parameter offset

	// cleanup parameter width

	// local text should not be modified or freed
}


/*
 * Express shortcut to determine the width of this String in bytes.
 */
JNIEXPORT int JNICALL
Java_org_gnome_pango_PangoAttributeOverride_strlen
(
	JNIEnv* env,
	jclass cls,
	jstring _str
)
{
	return (*env)->GetStringUTFLength(env, _str);
}
