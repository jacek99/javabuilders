/*
 * GtkTreeModelOverride.c
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
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
#include "org_gnome_gtk_GtkTreeModelOverride.h"

/**
 * Called from
 *   org.gnome.gtk.GtkTreeModeOverride.gtk_list_store_new(String[])
 * called from
 *   org.gnome.gtk.GtkTreeModeOverride.createListStore(Class[])
 * called from
 *   org.gnome.gtk.ListStore.<init>(???)
 *
 * You should already have established Java side that ther array is bigger
 * that 0 elements before calling this.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_gtk_GtkTreeModelOverride_gtk_1list_1store_1new
(
	JNIEnv* env,
	jclass cls,
	jobjectArray _columns
)
{
	GtkListStore* result;
	gint num_columns;
	GType* columns; // GType[]
	gint i;
	jstring _name;
	const gchar* name;
	
	num_columns = (gint) (*env)->GetArrayLength(env, _columns);
	columns = g_newa(GType, num_columns);
		
	for (i = 0; i < num_columns; i++) {
		_name = (jstring) (*env)->GetObjectArrayElement(env, _columns, i);

		name = (const gchar*) (*env)->GetStringUTFChars(env, _name, NULL);
		if (name == NULL) {
			return 0L; // OutOfMemory already thrown
		}

		columns[i] = bindings_java_type_lookup(name);
		
		if (columns[i] == G_TYPE_INVALID) {
			bindings_java_throw(env, "Don't know how to map %s into a GType", name);
			return 0L;
		}

		(*env)->ReleaseStringUTFChars(env, _name, name);
		(*env)->DeleteLocalRef(env, _name);
	}

	// call constructor
	result = gtk_list_store_newv(num_columns, columns);

	// clean up of columns is automatic

	// and finally
	return (jlong) result;
}

/**
 * Called from
 *   org.gnome.gtk.GtkTreeModeOverride.gtk_tree_store_new(String[])
 * called from
 *   org.gnome.gtk.GtkTreeModeOverride.createTreeStore(Class[])
 * called from
 *   org.gnome.gtk.TreeStore.<init>(???)
 *
 * You should already have established Java side that ther array is bigger
 * that 0 elements before calling this.
 */
JNIEXPORT jlong JNICALL
Java_org_gnome_gtk_GtkTreeModelOverride_gtk_1tree_1store_1new
(
    JNIEnv* env,
    jclass cls,
    jobjectArray _columns
)
{
    GtkTreeStore* result;
    gint num_columns;
    GType* columns; // GType[]
    gint i;
    jstring _name;
    const gchar* name;
    
    num_columns = (gint) (*env)->GetArrayLength(env, _columns);
    columns = g_newa(GType, num_columns);
        
    for (i = 0; i < num_columns; i++) {
        _name = (jstring) (*env)->GetObjectArrayElement(env, _columns, i);

        name = (const gchar*) (*env)->GetStringUTFChars(env, _name, NULL);
        if (name == NULL) {
            return 0L; // OutOfMemory already thrown
        }

        columns[i] = bindings_java_type_lookup(name);
        
        if (columns[i] == G_TYPE_INVALID) {
            bindings_java_throw(env, "Don't know how to map %s into a GType", name);
            return 0L;
        }

        (*env)->ReleaseStringUTFChars(env, _name, name);
        (*env)->DeleteLocalRef(env, _name);
    }

    // call constructor
    result = gtk_tree_store_newv(num_columns, columns);

    // clean up of columns is automatic

    // and finally
    return (jlong) result;
}

/*
 * This could _potentially_ be replaced if we were to expose Value binding of
 * GBoxed types on the Java side.
 */
JNIEXPORT void JNICALL
Java_org_gnome_gtk_GtkTreeModelOverride_gtk_1list_1store_1set_1reference
(
	JNIEnv *env,
	jclass cls,
	jlong _self,
	jlong _row,
	jint _column,
	jobject _reference
)
{
	GtkListStore* self;
	GtkTreeIter* row;
	gint column;
	GValue value = { 0, };
	gpointer reference;

	// convert parameter self
	self = (GtkListStore*) _self;

	// convert parameter iter
	row = (GtkTreeIter*) _row;

	// convert parameter column
	column = (gint) _column;

	// convert parameter reference
	g_value_init(&value, BINDINGS_JAVA_TYPE_REFERENCE);
	reference = (gpointer) _reference;

	g_value_set_boxed(&value, reference);

	// call function
	gtk_list_store_set_value(self, row, column, &value);

	// clean up
	g_value_unset(&value);
}

/*
 * This could _potentially_ be replaced if we were to expose Value binding of
 * GBoxed types on the Java side.
 */
JNIEXPORT void JNICALL
Java_org_gnome_gtk_GtkTreeModelOverride_gtk_1tree_1store_1set_1reference
(
    JNIEnv *env,
    jclass cls,
    jlong _self,
    jlong _row,
    jint _column,
    jobject _reference
)
{
    GtkTreeStore* self;
    GtkTreeIter* row;
    gint column;
    GValue value = { 0, };
    gpointer reference;

    // convert parameter self
    self = (GtkTreeStore*) _self;

    // convert parameter iter
    row = (GtkTreeIter*) _row;

    // convert parameter column
    column = (gint) _column;

    // convert parameter reference
    g_value_init(&value, BINDINGS_JAVA_TYPE_REFERENCE);
    reference = (gpointer) _reference;

    g_value_set_boxed(&value, reference);

    // call function
    gtk_tree_store_set_value(self, row, column, &value);

    // clean up
    g_value_unset(&value);
}
	
JNIEXPORT jobject JNICALL
Java_org_gnome_gtk_GtkTreeModelOverride_gtk_1tree_1model_1get_1reference
(
	JNIEnv *env,
	jclass cls,
	jlong _self,
	jlong _row,
	jint _column
)
{
	GtkTreeModel* self;
	GtkTreeIter* row;
	gint column;
	GValue value = { 0, };
	gpointer result;
	jobject safe;

	// convert parameter self
	self = (GtkTreeModel*) GTK_TREE_MODEL((GtkListStore*) _self);

	// convert parameter iter
	row = (GtkTreeIter*) _row;

	// convert parameter column
	column = (gint) _column;

	/*
	 * Somewhat strangely, you don't call g_value_init() because the
	 * gtk_tree_model_get_value() call does it for you.
	 */

	// call function
	gtk_tree_model_get_value(self, row, column, &value);
	result = g_value_get_boxed(&value);

	// clean up value
	/*
	 * This is insanely nasty. When g_value_unset() is called, the result
	 * jobject reference gets smashed. This appears to be the result of
	 * DeleteGlobalRef() being called in bindings_java_reference_free().
	 * While dropping the reference is, of course, the whole point, the
	 * result is that the value of result is no longer usable. jobjects
	 * really _do_ behave like pointers.
	 * 
	 * The solution is to create a new local reference right here before
	 * calling g_value_unset() method; this jobject will remain safe for
	 * the duration of this native method and can be returned to Java. 
	 */
	safe = (*env)->NewLocalRef(env, (jobject) result);
	g_value_unset(&value);

	// return reference
	return safe;
}
