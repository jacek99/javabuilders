/*
 * GBoxed.c
 *
 * Copyright (c) 2006 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
 
#include <glib.h>
#include <glib-object.h>
#include <jni.h>
#include "org_gnome_glib_GBoxed.h"

/*
 * Implements
 *   org.gnome.glib.GBoxed.g_boxed_free(long boxed)
 * called from
 *   org.gnome.glib.GBoxed.free(Boxed reference)
 * called from
 *   org.gnome.glib.Boxed.release()
 *
 * This is where we free a GBoxed if we're the owner of it.
 */
// nothing here
