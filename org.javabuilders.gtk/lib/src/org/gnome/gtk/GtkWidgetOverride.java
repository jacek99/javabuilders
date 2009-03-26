/*
 * GtkWidgetOverride.java
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
 * Hand crafted to get at the fields of Widget.
 * 
 * @author Andrew Cowie
 */
final class GtkWidgetOverride extends Plumbing
{
    private GtkWidgetOverride() {}

    static final org.gnome.gdk.Window getWindow(Widget self) {
        long result;

        synchronized (lock) {
            result = gtk_widget_get_window(pointerOf(self));

            return (org.gnome.gdk.Window) objectFor(result);
        }
    }

    private static native final long gtk_widget_get_window(long self);

    /**
     * Allocation is yet another accessible GObject field. We really need to
     * deal with this into the code generator.
     */
    static final Allocation getAllocation(Widget self) {
        long result;

        synchronized (lock) {
            result = gtk_widget_get_allocation(pointerOf(self));

            return (Allocation) boxedFor(Allocation.class, result);
        }
    }

    private static native final long gtk_widget_get_allocation(long self);

    static final Requisition getRequisition(Widget self) {
        long result;

        synchronized (lock) {
            result = gtk_widget_get_requisition(pointerOf(self));

            return (Requisition) boxedFor(Requisition.class, result);
        }
    }

    private static native final long gtk_widget_get_requisition(long self);

    /**
     * Set the GdkWindow underlying this Widget to receive visibility events!
     * As a necessary convenience, the Widget will be realized first if
     * necessary.
     */
    static final void setEventsVisibility(Widget self) {
        synchronized (lock) {
            gtk_widget_set_events_visibility(pointerOf(self));
        }
    }

    private static native final void gtk_widget_set_events_visibility(long self);
}
