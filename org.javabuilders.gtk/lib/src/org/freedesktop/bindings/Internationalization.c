/*
 * Internationalization.c
 *
 * Copyright (c) 2008 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2008 Vreixo Formoso
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */

#include <libintl.h>
#include <locale.h>
#include <jni.h>
#include "bindings_java.h"
#include "org_freedesktop_bindings_Internationalization.h"

/**
 * Implements
 *   org.freedesktop.bindings.Internationalization.gettext(String msg)
 * called from
 *   org.freedesktop.bindings.Internationalization._(String msg, java.lang.Object ...parameters)
 */
JNIEXPORT jstring JNICALL
Java_org_freedesktop_bindings_Internationalization_gettext
(
	JNIEnv *env,
	jclass cls,
	jstring _msg
)
{	
	const char* msg;
	char* result;

	// convert parameter msg
	msg = (const char*) (*env)->GetStringUTFChars(env, _msg, NULL);
	if (msg == NULL) {
		return NULL; // expeption already thrown
	}

	// call function
	result = gettext(msg);
        
	/*
	 * If there was no translation, so just return the input String, This
	 * avoids corrupting the statically allocated char* returned by
	 * gettext().
	 * 
	 * If there is a translation, allocate a new String for it, and
	 * return it.
	 */

	// convert and return
	if (result == msg) {
		(*env)->ReleaseStringUTFChars(env, _msg, msg);		
		return _msg;
	} else {
		(*env)->ReleaseStringUTFChars(env, _msg, msg);
		return (*env)->NewStringUTF(env, result);
	}
}

JNIEXPORT void JNICALL 
Java_org_freedesktop_bindings_Internationalization_bindtextdomain
(
	JNIEnv *env,
	jclass cls,
	jstring _packageName,
	jstring _localeDir
)
{
	const char* packageName;
	const char* localeDir;

	// convert parameter packageName
	packageName = (const char*) (*env)->GetStringUTFChars(env, _packageName, NULL);
	if (packageName == NULL) {
		return; // expeption already throw
	}

	// convert parameter localeDir
	localeDir = (const char*) (*env)->GetStringUTFChars(env, _localeDir, NULL);
	if (localeDir == NULL) {
		return; // expeption already throw
	}

	/*
	 * Initialize internationalization and localization libraries. The
	 * second argument to setlocale() being "" means to pull settings
	 * from the environment. 
	 */

	if (setlocale(LC_ALL, "") == NULL) {
		bindings_java_throw(env, "\nCall to setlocale() to initialize the program's locale failed");
		return;
	}
	if (bindtextdomain(packageName, localeDir) == NULL) {
		bindings_java_throw(env, "\nCall to bindtextdomain() to set the locale base dir failed");
		return;
	}
	if (bind_textdomain_codeset(packageName, "UTF-8") == NULL) {
		bindings_java_throw(env, "\nCall to bind_textdomain_codeset() to set UTF-8 failed");
		return;
	}
	if (textdomain(packageName) == NULL) {
		bindings_java_throw(env, "\nCall to textdomain() to set message source failed");
		return;
	}

	// cleanup parameter packageName
	(*env)->ReleaseStringUTFChars(env, _packageName, packageName);

	// cleanup parameter localeDir
	(*env)->ReleaseStringUTFChars(env, _localeDir, localeDir);
}
