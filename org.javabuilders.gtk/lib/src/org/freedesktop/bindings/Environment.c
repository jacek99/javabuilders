/*
 * Environment.c
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
#include <glib.h>
#include <stdlib.h>
#include <errno.h>
#include "bindings_java.h"
#include "org_freedesktop_bindings_Environment.h"

/*
 * Implements
 *   org.freedesktop.bindings.Environment.getenv(String variableName)
 */
JNIEXPORT jobject JNICALL
Java_org_freedesktop_bindings_Environment_getenv
(
	JNIEnv *env,
	jclass cls,
	jstring _name
)
{
	gchar* name;
	gchar* result;

	// convert parameter name
	name = (gchar*) (*env)->GetStringUTFChars(env, _name, NULL);
	if (name == NULL) {
		return NULL; /* OutOfMemoryError already thrown */
	}

	// call function
	result = (gchar*) getenv(name); 

	// clean up name
	(*env)->ReleaseStringUTFChars(env, _name, name);

	// and return	
	return (*env)->NewStringUTF(env, result);
}


/*
 * Implements
 *   org.freedesktop.bindings.Environment.setenv(String variableName, String value)
 */
JNIEXPORT void  JNICALL
Java_org_freedesktop_bindings_Environment_setenv
(
	JNIEnv *env,
	jclass cls,
	jstring _name,
	jstring _value
)
{
	gchar* name;
	gchar* value;

	// convert parameter name
	name = (gchar*) (*env)->GetStringUTFChars(env, _name, NULL);
	if (name == NULL) {
		return; /* OutOfMemoryError already thrown */
	}

	// convert parameter value
	value = (gchar*) (*env)->GetStringUTFChars(env, _value, NULL);
	if (value == NULL) {
		return; /* OutOfMemoryError already thrown */
	}

	// call function
	if (setenv(name, value, 1) == -1) {
		bindings_java_throw(env, "\nsetenv() failed: Insufficient space in environment");
	}
	
	// clean up name
	(*env)->ReleaseStringUTFChars(env, _name, name);

	// clean up name
	(*env)->ReleaseStringUTFChars(env, _value, value);
}


/*
 * Implements
 *   org.freedesktop.bindings.Environment.unsetenv(String variableName)
 */
JNIEXPORT void  JNICALL
Java_org_freedesktop_bindings_Environment_unsetenv
(
	JNIEnv *env,
	jclass cls,
	jstring _name
)
{
	gchar* name;

	// convert parameter name
	name = (gchar*) (*env)->GetStringUTFChars(env, _name, NULL);
	if (name == NULL) {
		return; /* OutOfMemoryError already thrown */
	}

	// call function
	if (unsetenv(name) == -1) {
		bindings_java_throw(env, "\nunsetenv() failed: %s", g_strerror(errno));
	}

	// clean up name
	(*env)->ReleaseStringUTFChars(env, _name, name);
}




/*
 * Will implement
 *   org.freedesktop.bindings.Environment.getWidth()
 */
JNIEXPORT jint JNICALL
Java_org_freedesktop_bindings_Environment_getWidth
(
	JNIEnv *env,
	jclass cls
)
{
	// TODO how can we do this?
	return (jint) 0;
}
