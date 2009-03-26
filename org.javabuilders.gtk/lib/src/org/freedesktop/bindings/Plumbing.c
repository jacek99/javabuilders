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
#include "bindings_java.h"
#include "org_freedesktop_bindings_Plumbing.h"

/*
 * Implements
 *   org.freedesktop.bindings.Plumbing.createPointer(Class type, long pointer)
 */
JNIEXPORT jobject JNICALL
Java_org_freedesktop_bindings_Plumbing_createPointer
(
	JNIEnv *env,
	jclass cls,
	jclass type,
	jlong pointer
)
{
	jmethodID constructor;
	jobject proxy;
	
	constructor = (*env)->GetMethodID(env, type, "<init>", "(J)V");
	if (constructor == NULL) {
		g_critical("Constructor methodID not found");
		return NULL;
	}
	
	proxy = (*env)->NewObject(env, type, constructor, pointer);
	return proxy;
}

/*
 * Implements
 *   org.freedesktop.bindings.Plumbing.createFlag(Class type, int ordinal, String nickname)
 */
JNIEXPORT jobject JNICALL
Java_org_freedesktop_bindings_Plumbing_createFlag
(
	JNIEnv *env,
	jclass cls,
	jclass type,
	jint ordinal, 
	jstring nickname
)
{
	jmethodID constructor;
	jobject flag;
	
	constructor = (*env)->GetMethodID(env, type, "<init>", "(ILjava/lang/String;)V");
	if (constructor == NULL) {
		g_critical("Constructor methodID not found");
		return NULL;
	}
	
	flag = (*env)->NewObject(env, type, constructor, ordinal, nickname);
	return flag;
}


/*
 * Implements
 *   org.freedesktop.bindings.Plumbing.toHexString(long pointer)
 */
JNIEXPORT jstring JNICALL
Java_org_freedesktop_bindings_Plumbing_toHexString
(
	JNIEnv *env,
	jclass cls,
	jlong _pointer
)
{
	const gchar* result;
	
	result = bindings_java_memory_pointerToString((gpointer) _pointer);
	
	return (*env)->NewStringUTF(env, result);
}
