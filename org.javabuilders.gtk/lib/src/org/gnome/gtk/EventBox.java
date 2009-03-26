/*
 * EventBox.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * Add the ability for a Widget to accept events. A fair number of Widgets do
 * <i>not</i> have the ability to process events coming from the user because,
 * under ordinary circumstances, they don't need it. If you want a Label or
 * Image to take keystrokes or mouse clicks, then you instantiate an EventBox
 * and pack your Widget into it.
 * 
 * <p>
 * Quite a number of Widgets share underlying resources. That's not something
 * you normally need to worry about as a developer, but if you have called
 * {@link Widget#getWindow() getWindow()} and find yourself needing an
 * underlying [<code>org.gnome.gdk</code>] Window specifically for your
 * Widget, then use an EventBox.
 * 
 * <p>
 * EventBoxes can also be used for things like painting a different background
 * colour behind an otherwise transparent Widget.
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public class EventBox extends Bin
{
    protected EventBox(long pointer) {
        super(pointer);
    }

    /**
     * Create a new EventBox. Be sure to call {@link #setAboveChild(boolean)
     * setAboveChild()} and {@link #setVisibleWindow(boolean)
     * setVisibleWindow()} if necessary.
     * 
     * @since 4.0.6
     */
    public EventBox() {
        super(GtkEventBox.createEventBox());
    }

    /**
     * Whether the EventBox should be "above" its child or not. If above, the
     * EventBox will receive <i>all</i> the events targeted at the child. If
     * below, then the events will first go to the child and then to the
     * EventBox. The default is <code>false</code> which is usually what you
     * want (ie, you don't want to mess with existing functionality in a
     * Widget, just add to it).
     * 
     * @since 4.0.6
     */
    public void setAboveChild(boolean setting) {
        GtkEventBox.setAboveChild(this, setting);
    }

    /**
     * Whether the EventBox will have a "visible" child. The default is
     * <code>true</code>; ordinarily you want the child Widget to be displayed
     * normally.
     * 
     * @since 4.0.6
     */
    /*
     * TODO there is a lot more to this than described here. Someone please
     * read the GTK docs, experiment with this, and improve our API
     * documentation accordingly.
     */
    public void setVisibleWindow(boolean setting) {
        GtkEventBox.setVisibleWindow(this, setting);
    }
}
