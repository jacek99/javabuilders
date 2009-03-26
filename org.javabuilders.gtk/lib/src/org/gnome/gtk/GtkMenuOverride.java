/*
 * GtkMenuOverride.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * Expose a custom workaround for the craziness of the real gtk_menu_popup()'s
 * required arguments.
 * 
 * @author Andrew Cowie
 */
final class GtkMenuOverride extends Plumbing
{
    private GtkMenuOverride() {}

    static final void popup(Menu self) {
        synchronized (lock) {
            gtk_menu_popup(pointerOf(self));
        }
    }

    private static native final void gtk_menu_popup(long self);

    /**
     * Call gtk_menu_popup(), but hardwired to use
     * gtk_status_icon_position_menu() as the (*GtkMenuPositionFunc).
     */
    static final void popupStatusIcon(Menu self, StatusIcon status) {
        synchronized (lock) {
            gtk_menu_popup_status_icon(pointerOf(self), pointerOf(status));
        }
    }

    private static native final void gtk_menu_popup_status_icon(long self, long status);
}
