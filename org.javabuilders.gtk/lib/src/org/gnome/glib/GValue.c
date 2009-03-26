/*
 * GValue.c
 *
 * Copyright (c) 2006-2009 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */

#include <glib.h>
#include <glib-object.h>
#include <pango/pango.h>
#include <jni.h>
#include "org_gnome_glib_GValue.h"
#include "bindings_java.h"

/**
 * Implements
 *   org.gnome.glib.GValue.g_type_name(long value)
 * called from
 *   org.gnome.glib.GValue.typeName(long value)
 * called from
 *   org.gnome.glib.Plumbing.instanceFor(long pointer)
 * and also made available via
 *   org.gnome.glib.GValue.typeName(Value value)
 */
JNIEXPORT jstring JNICALL
Java_org_gnome_glib_GValue_g_1type_1name
(
	JNIEnv *env,
	jclass cls,
	jlong _value
)
{
	GValue* value;
	const gchar* name;

	// translate value
	value = (GValue*) _value;

	// call function & macro
	name = g_type_name(G_VALUE_TYPE(value));
	//g_print("GValue  g_type_name(%ld): %s\n", (long) G_VALUE_TYPE(value), name);

	// return name. Guard against NullPointerException by returning an
	// empty string instead of null
	return (*env)->NewStringUTF(env, (name != NULL ? name : "\0"));
}

/**
 * Implements
 *   org.gnome.glib.GValue.g_value_free(long value)
 * called from
 *   org.gnome.glib.GValue.free(Fundamental reference)
 * called from
 *   org.gnome.glib.Fundamental.release()
 *
 * This is where we free the chunk of memory containing the GValue pointer
 * (that we know we allocated with GSlice).
 */
JNIEXPORT void JNICALL
Java_org_gnome_glib_GValue_g_1value_1free
(
	JNIEnv *env,
	jclass cls,
	jlong _value
)
{
	GValue* value;
		
	value =	(GValue*) _value;
	
	/*
	 * Although this function is mostly about resetting the GValue to
	 * the blank state, it also has the effect of unref()'ing GObjects,
	 * free()'ing NULL terminated strings, etc.
	 */
	g_value_unset(value);

	g_slice_free(GValue, value);
}


/**
 * Implements
 *   org.gnome.glib.GValue.g_value_new()
 * called from
 *   org.gnome.glib.GValue.createValue()
 * called from
 *   org.gnome.glib.Value.<init>()
 *
 * Allocate a blank GValue, for use in methods which populate a blank GValue
 * in order to return information in an out-parameter-esque fashion.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1new
(
	JNIEnv *env,
	jclass cls
)
{
	GValue* value;
		
	// allocate it and set to zeros, per what g_value_init requires
	value =	g_slice_new0(GValue);

	// return address
	return (jlong) value;
}

/**
 * Implements
 *   org.gnome.glib.GValue.g_value_init(int i)
 * called from
 *   org.gnome.glib.GValue.createValue(int i)
 *
 * Allocate a GValue for a gint32 with GSlice, then initialize it and return
 * the pointer.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1init__I
(
	JNIEnv *env,
	jclass cls,
	jint _i
)
{
	gint32 i;
	GValue* value;

	// translate arg
	i = (gint32) _i;

	// allocate it and set to zeros, per what g_value_init requires
	value =	g_slice_new0(GValue);
	g_value_init(value, G_TYPE_INT);

	// set the value
	g_value_set_int(value, i);

	// return address
	return (jlong) value;
}

/**
 * Implements
 *   org.gnome.glib.GValue.g_value_init(long j)
 * called from
 *   org.gnome.glib.GValue.createValue(long j)
 *
 * Allocate a GValue for a gint64 with GSlice, then initialize it and return
 * the pointer.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1init__J
(
	JNIEnv *env,
	jclass cls,
	jlong _j
)
{
	gint64 j;
	GValue* value;

	// translate arg
	j = (gint64) _j;

	// allocate it and set to zeros, per what g_value_init requires
	value =	g_slice_new0(GValue);
	g_value_init(value, G_TYPE_INT64);

	// set the value
	g_value_set_int64(value, j);

	// return address
	return (jlong) value;
}


/**
 * Implements
 *   org.gnome.glib.GValue.g_value_init(boolean b)
 * called from
 *   org.gnome.glib.GValue.createValue(boolean b)
 *
 * Allocate a GValue for a gboolean with GSlice, then initialize it and return
 * the pointer.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1init__Z
(
	JNIEnv *env,
	jclass cls,
	jboolean _b
)
{
	gboolean b;
	GValue* value;
	
	b = (gboolean) _b;

	// allocate it and set to zeros, per what g_value_init requires
	value =	g_slice_new0(GValue);
	g_value_init(value, G_TYPE_BOOLEAN);

	// set the value
	g_value_set_boolean(value, b);

	// return address
	return (jlong) value;
}

/**
 * Implements
 *   org.gnome.glib.GValue.g_value_init(float f)
 * called from
 *   org.gnome.glib.GValue.createValue(float f)
 *
 * Allocate a GValue for a gfloat with GSlice, then initialize it and return
 * the pointer.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1init__F
(
	JNIEnv *env,
	jclass cls,
	jfloat _f
)
{
	gfloat f;
	GValue* value;
	
	f = (gfloat) _f;
		
	// allocate it and set to zeros, per what g_value_init requires
	value =	g_slice_new0(GValue);
	g_value_init(value, G_TYPE_FLOAT);

	// set the value
	g_value_set_float(value, f);

	// return address
	return (jlong) value;
}

/**
 * Implements
 *   org.gnome.glib.GValue.g_value_init(double d)
 * called from
 *   org.gnome.glib.GValue.createValue(double d)
 *
 * Allocate a GValue for a gdouble with GSlice, then initialize it and return
 * the pointer.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1init__D
(
	JNIEnv *env,
	jclass cls,
	jdouble _d
)
{
	gdouble d;
	GValue* value;
	
	d = (gdouble) _d;

	// allocate it and set to zeros, per what g_value_init requires
	value =	g_slice_new0(GValue);
	g_value_init(value, G_TYPE_DOUBLE);

	// set the value
	g_value_set_double(value, d);

	// return address
	return (jlong) value;
}

/**
 * Implements
 *   org.gnome.glib.GValue.g_value_init(String str)
 * called from
 *   org.gnome.glib.GValue.createValue(String str)
 *
 * Allocate a GValue for a char* with GSlice, then initialize it and return
 * the pointer.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1init__Ljava_lang_String_2
(
	JNIEnv *env,
	jclass cls,
	jstring _str
)
{
	gchar* str;
	GValue* value;

	// translate
	str = (gchar*) (*env)->GetStringUTFChars(env, _str, NULL);
	if (str == NULL) {
		return 0; /* OutOfMemoryError already thrown */
	}

	// allocate and set to zeros, per what g_value_init requires
	value =	g_slice_new0(GValue);
	g_value_init(value, G_TYPE_STRING);

	// set the value
	g_value_set_string(value, str);

	// clean up
	(*env)->ReleaseStringUTFChars(env, _str, str);

	// return address
	return (jlong) value;
}


/**
 * Implements
 *   org.gnome.glib.GValue.g_value_init_object(long obj)
 * called from
 *   org.gnome.glib.GValue.createValue(Object obj)
 *
 * Allocate a GValue for a GObject with GSlice, then initialize it and return
 * the pointer.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1init_1object
(
	JNIEnv *env,
	jclass cls,
	jlong _obj
)
{
	GObject* obj;
	GValue* value;
	
	// translate obj
	obj = (GObject*) _obj;

	// allocate and set to zeros, per what g_value_init requires
	value =	g_slice_new0(GValue);
	g_value_init(value, G_TYPE_OBJECT);

	// set the value
	g_value_set_object(value, obj);

	// clean up obj

	// return address
	return (jlong) value;
}


/**
 * Implements
 *   org.gnome.glib.GValue.g_value_init_font_description(long desc)
 * called from
 *   org.gnome.glib.GValue.createValue(FontDescription obj)
 *
 * Allocate a GValue for a PangoFontDescription with GSlice, then initialize
 * it and return the pointer.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1init_1font_1description
(
    JNIEnv *env,
    jclass cls,
    jlong _desc
)
{
    PangoFontDescription* desc;
    GValue* value;
    
    // translate desc
    desc = (PangoFontDescription*) _desc;
    
    // allocate and set to zeros, per what g_value_init requires
    value = g_slice_new0(GValue);
    g_value_init(value, PANGO_TYPE_FONT_DESCRIPTION);

    // set the value
    g_value_set_boxed(value, desc);

    // clean up desc

    // return address
    return (jlong) value;
}


/**
 * Implements
 *   org.gnome.glib.GValue.g_value_init_pixbuf(long pixbuf)
 * called from
 *   org.gnome.glib.GValue.createValue(Pixbuf obj)
 *
 * Allocate a GValue for a GdkPixbuf with GSlice, then initialize it and return
 * the pointer.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1init_1pixbuf
(
    JNIEnv *env,
    jclass cls,
    jlong _pixbuf
)
{
    GdkPixbuf* pixbuf;
    GValue* value;
    
    // translate obj
    pixbuf = (GdkPixbuf*) _pixbuf;
    
    // allocate and set to zeros, per what g_value_init requires
    value = g_slice_new0(GValue);
    g_value_init(value, GDK_TYPE_PIXBUF);

    // set the value    
    g_value_set_object(value, pixbuf);

    // clean up obj

    // return address
    return (jlong) value;
}


/**
 * Implements
 *   org.gnome.glib.GValue.g_value_init_enum(int num)
 * called from
 *   org.gnome.glib.GValue.createValue(Constant reference)
 *
 * Allocate a GValue for a GObject with GSlice, then initialize it and return
 * the pointer.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1init_1enum
(
	JNIEnv *env,
	jclass cls,
	jstring _name,
	jint _num
)
{
	gchar* name;
	GType type;
	gint num;
	GValue* value;
	
	// translate type;
	name = (gchar*) (*env)->GetStringUTFChars(env, _name, NULL);
	if (name == NULL) {
		return 0; /* OutOfMemoryError already thrown */
	}

	type = g_type_from_name(name);

	(*env)->ReleaseStringUTFChars(env, _name, name);
	
	// translate obj
	num = (gint) _num;
	
	// allocate and set to zeros, per what g_value_init requires
	value =	g_slice_new0(GValue);
	g_value_init(value, type);

	// set the value
	g_value_set_enum(value, num);

	// clean up obj

	// return address
	return (jlong) value;
}

/**
 * Implements
 *   org.gnome.glib.GValue.g_value_get_float(long value)
 * called from
 *   org.gnome.glib.GValue.getFloat(Value value)
 * called from
 *   org.gnome.glib.Object.getPropertyFloat(String name)
 *
 * Extract the gfloat value from a GValue of G_TYPE_FLOAT, returning the
 * primitive.
 */
JNIEXPORT jfloat JNICALL
Java_org_gnome_glib_GValue_g_1value_1get_1float
(
	JNIEnv* env,
	jclass cls,
	jlong _value
)
{
	GValue* value;
	gfloat result;

	// translate value
	value =	(GValue*) _value;
	if (!G_VALUE_HOLDS_FLOAT(value)) {
		bindings_java_throw(env, "You've asked for the float value of a GValue, but it's not a G_TYPE_FLOAT!");
		return 0.0f;
	}

	// call function
	result = g_value_get_float(value);

	// and return
	return (jfloat) result; 
}

/**
 * Implements
 *   org.gnome.glib.GValue.g_value_get_double(long value)
 * called from
 *   org.gnome.glib.GValue.getDouble(Value value)
 * called from
 *   org.gnome.glib.Object.getPropertyDouble(String name)
 *
 * Extract the gdoulbe value from a GValue of G_TYPE_DOUBLE, returning the
 * primitive.
 */
JNIEXPORT jdouble JNICALL
Java_org_gnome_glib_GValue_g_1value_1get_1double
(
	JNIEnv* env,
	jclass cls,
	jlong _value
)
{
	GValue* value;
	gdouble result;

	// translate value
	value =	(GValue*) _value;
	if (!G_VALUE_HOLDS_DOUBLE(value)) {
		bindings_java_throw(env, "You've asked for the double value of a GValue, but it's not a G_TYPE_DOUBLE!");
		return 0.0;
	}

	// call function
	result = g_value_get_double(value);
	
	// and return
	return (jdouble) result; 
}

JNIEXPORT jint JNICALL
Java_org_gnome_glib_GValue_g_1value_1get_1int
(
	JNIEnv* env,
	jclass cls,
	jlong _value
)
{
	GValue* value;
	gint result;

	// translate value
	value =	(GValue*) _value;
	if (!G_VALUE_HOLDS_INT(value)) {
		bindings_java_throw(env, "You've asked for the int value of a GValue, but it's not a G_TYPE_INT!");
		return 0;
	}

	// call function
	result = g_value_get_int(value);
	
	// and return
	return (jint) result;
}

JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1get_1long
(
	JNIEnv* env,
	jclass cls,
	jlong _value
)
{
	GValue* value;
	gint64 result;

	// translate value
	value =	(GValue*) _value;
	if (!G_VALUE_HOLDS_INT64(value)) {
		bindings_java_throw(env, "You've asked for the long value of a GValue, but it's not a G_TYPE_INT64!");
		return 0;
	}

	// call function
	result = g_value_get_int64(value);

	// and return
	return (jlong) result;
}


JNIEXPORT jboolean JNICALL
Java_org_gnome_glib_GValue_g_1value_1get_1boolean
(
	JNIEnv* env,
	jclass cls,
	jlong _value
)
{
	GValue* value;
	gboolean result;

	// translate value
	value =	(GValue*) _value;
	if (!G_VALUE_HOLDS_BOOLEAN(value)) {
		bindings_java_throw(env, "You've asked for the boolean value of a GValue, but it's not a G_TYPE_BOOLEAN!");
		return 0;
	}

	// call function
	result = g_value_get_boolean(value);

	// and return
	return (jboolean) result; 
}

/**
 * Implements
 *   org.gnome.glib.GValue.g_value_get_string(long value)
 * called from
 *   org.gnome.glib.GValue.getString(Value value)
 * called from
 *   org.gnome.glib.Object.getPropertyString(String name)
 *
 * Extract the string value from a GValue of G_TYPE_STRING, returning the
 * primitive (well, String) to be used as such.
 */
JNIEXPORT jstring JNICALL
Java_org_gnome_glib_GValue_g_1value_1get_1string
(
	JNIEnv* env,
	jclass cls,
	jlong _value
)
{
	GValue* value;
	const gchar* str; 

	// translate value
	value =	(GValue*) _value;
	if (!G_VALUE_HOLDS_STRING(value)) {
		bindings_java_throw(env, "You've asked for the string value of a GValue, but it's not a G_TYPE_STRING!");
		return NULL;
	}

	// call function
	str = g_value_get_string(value);

	// and return	
	return (*env)->NewStringUTF(env, str);
}

/**
 * Implements
 *   org.gnome.glib.GValue.g_value_get_enum(long value)
 * called from
 *   org.gnome.glib.GValue.getEnum(Value value)
 * called from
 *   org.gnome.glib.Object.getPropertyEnum(String name)
 *
 * Extract the ordinal of an enum stored in a GValue of type G_TYPE_ENUM.
 */
JNIEXPORT jint JNICALL
Java_org_gnome_glib_GValue_g_1value_1get_1enum
(
	JNIEnv* env,
	jclass cls,
	jlong _value
)
{
	GValue* value;
	gint num; 

	// translate value
	value =	(GValue*) _value;
	if (!G_VALUE_HOLDS_ENUM(value)) {
		bindings_java_throw(env, "You've asked for the ordinal value of a GValue, but it's not a G_TYPE_ENUM!");
		return 0;
	}

	// call function
	num = g_value_get_enum(value);

	// and return	
	return (jint) num;
}

/**
 * Implements
 *   org.gnome.glib.GValue.g_value_get_flags(long value)
 * called from
 *   org.gnome.glib.GValue.getFlags(Value value)
 * called from
 *   org.gnome.glib.Object.getPropertyFlags(String name)
 *
 * Extract the ordinal of an flag stored in a GValue of type G_TYPE_FLAGS.
 */
JNIEXPORT jint JNICALL
Java_org_gnome_glib_GValue_g_1value_1get_1flags
(
	JNIEnv* env,
	jclass cls,
	jlong _value
)
{
	GValue* value;
	guint num; 

	// translate value
	value =	(GValue*) _value;
	if (!G_VALUE_HOLDS_FLAGS(value)) {
		bindings_java_throw(env, "You've asked for the flags ordinal value of a GValue, but it's not a G_TYPE_FLAGS!");
		return 0;
	}

	// call function
	num = g_value_get_flags(value); 

	// and return	
	return (jint) num;
}

JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1get_1object
(
	JNIEnv* env,
	jclass cls,
	jlong _value
)
{
	GValue* value;
	GObject* object; 

	// translate value
	value =	(GValue*) _value;
	if (!G_VALUE_HOLDS_OBJECT(value)) {
		bindings_java_throw(env, "You've asked for the GObject within a GValue, but it's not a G_TYPE_OBJECT!");
		return 0L;
	}

	// call function
	object = g_value_get_object(value); 

	// and return	
	return (jlong) object;
}

JNIEXPORT jlong JNICALL
Java_org_gnome_glib_GValue_g_1value_1get_1pixbuf
(
    JNIEnv* env,
    jclass cls,
    jlong _value
)
{
    GValue* value;
    GdkPixbuf* pixbuf; 

    // translate value
    value = (GValue*) _value;
    if (G_VALUE_TYPE(value) != GDK_TYPE_PIXBUF) {
        bindings_java_throw(env, "You've asked for the GdkPixbuf within a GValue, but it's not a GDK_TYPE_PIXBUF!");
        return 0L;
    }

    // call function
    pixbuf = g_value_get_object(value); 

    // and return   
    return (jlong) pixbuf;
}
