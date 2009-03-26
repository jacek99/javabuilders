/*
 * GtkMenuOverride.c
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
#include "org_gnome_gtk_GtkMenuOverride.h"

/**
 * This is hand written to deal with the fact that in conventional use all
 * the arguments to gtk_menu_popup() are completely useless.
 */
JNIEXPORT void JNICALL
Java_org_gnome_gtk_GtkMenuOverride_gtk_1menu_1popup
(
	JNIEnv* env,
	jclass cls,
	jlong _self
)
{
	GtkMenu* self;

	// convert parameter self
	self = (GtkMenu*) _self;

	// call function
	gtk_menu_popup(self, NULL, NULL, NULL, NULL, 0, gtk_get_current_event_time());
	
	// cleanup parameter self
}

/*
 * Call gtk_menu_popup(), but hardwired to use 
 * gtk_status_icon_position_menu() as the (*GtkMenuPositionFunc).
 */
JNIEXPORT void JNICALL
Java_org_gnome_gtk_GtkMenuOverride_gtk_1menu_1popup_1status_1icon
(
	JNIEnv* env,
	jclass cls,
	jlong _self,
	jlong _status
)
{
	GtkMenu* self;
	GtkStatusIcon* status;

	// convert parameter self
	self = (GtkMenu*) _self;

	// convert parameter status
	status = (GtkStatusIcon*) _status;

	// call function
	gtk_menu_popup(self, NULL, NULL, gtk_status_icon_position_menu, status, 0, gtk_get_current_event_time());
	
	// cleanup parameter self
}
