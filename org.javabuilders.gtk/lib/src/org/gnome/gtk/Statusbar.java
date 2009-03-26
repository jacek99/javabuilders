/*
 * Statusbar.java
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
 * The space at the bottom of an application Window where status messages are
 * displayed. <img src="Statusbar.png" class="snapshot">
 * 
 * <p>
 * A Statusbar should generally be present at the bottom of a GNOME
 * application's main Window by being the first Widget to be packed with
 * respect to the end of the VBox used to vertically layout such a Window:
 * 
 * <pre>
 * status = new Statusbar();
 * vbox.packEnd(status, false, false, 0);
 * window.add(vbox);
 * </pre>
 * 
 * in fact, this is so much a convention that a Statusbar should be present
 * regardless of whether or not you plan to have messages to display. Make
 * sure you pack it into the top level VBox with <var>expand</var> and
 * <var>fill</var> set to <code>false</code> as shown. Statusbars somewhat by
 * definition should stay narrow; they shouldn't grow thicker if the user
 * resizes vertically.
 * 
 * <p>
 * The text in the Statusbar is set with {@link #setMessage(String)
 * setMessage()}. Most applications leave the Statusbar empty as a default
 * state, but if you wish to inform the user of things being in a normal
 * state, you can certainly do so:
 * 
 * <pre>
 * status.setMessage(&quot;Ready&quot;);
 * </pre>
 * 
 * or similar words appropriate to your program.
 * 
 * <p>
 * Statusbars are excellent for providing hints to the user about what the
 * user can do next (see <b><code>Inkscape</code></b> as a terrific example),
 * or to update the user with what the application is up to when processing.
 * Keep in mind, however, that this is considered only an assistance; people
 * don't necessarily look to the Statusbar when wondering what is going on
 * (and further, many applications allow the user to turn the Statusbar off
 * entirely). If you need to provide urgent information to the user then use a
 * Dialog.
 * 
 * <p>
 * As a Box subclass, you can pack other Widgets into the Statusbar. This is a
 * great place for a ProgressBar if you have a long running worker thread that
 * needs to report its percentage completion. Widgets so added will be packed
 * after the Label that is (obviously) internally present to display the
 * status messages. If it is a small Window you may want to constrain the size
 * of the ProgressBar lest it blot out the message:
 * 
 * <pre>
 * status = new Statusbar();
 * bar = new ProgressBar();
 * bar.setSizeRequest(30, -1);
 * status.packEnd(bar, false, false, 0);
 * </pre>
 * 
 * then later:
 * 
 * <pre>
 * status.setMessage(&quot;Sending all your files to the CIA...&quot;);
 * bar.setFraction(0.35);
 * </pre>
 * 
 * <p>
 * A visible Statusbar usually drawn with a grip in the bottom right corner
 * which most users will recognized as a stylized visual indication that the
 * Window can be resized. If necessary, it can be suppressed by calling
 * {@link #setHasResizeGrip(boolean) setHasResizeGrip(false)}.
 * 
 * <p>
 * <i>The underlying API in <code>GtkStatusbar</code> is ridiculously
 * complicated for absolutely no good reason. We have therefore compressed its
 * hideous stack-based mechanism into the simple single-message interface
 * presented here.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
/*
 * Not only is the whole context thing excessive, it turns out it is
 * unnecessary. Per gtkstatusbar.h, there is a default context, id 0, which
 * can be used as a global default.
 */
public class Statusbar extends HBox
{
    protected Statusbar(long pointer) {
        super(pointer);
        GtkStatusbar.push(this, 0, "");
    }

    /**
     * Construct a new Statusbar. The initial message is empty (you don't need
     * to explicitly set it to be so).
     * 
     * @since 4.0.6
     */
    /*
     * Strictly speaking, adding an empty status message isn't necessary, but
     * it makes the logic for our setMessage() method a bit sounder.
     */
    public Statusbar() {
        super(GtkStatusbar.createStatusbar());
        GtkStatusbar.push(this, 0, "");
    }

    /**
     * Set whether or not the Statusbar has a visual marking at its right hand
     * side indicating whether or not it can be resized and acting as a
     * convenient target to click on.
     * 
     * <p>
     * As the default for this is <code>true</code>, you only need to call
     * this if you want to suppress the resize handle from being drawn. Note
     * that this is just a visual cue; the actual resize behaviour is governed
     * by the window manager in concert with the Window's <var>resizable</var>
     * property. See {@link Window#setResizable(boolean) setResizable()}.
     * 
     * <p>
     * As a very rough guide, main application Windows really ought to have
     * the resize grip; fancy custom popups filling some transient purpose
     * that just happen to have a Statusbar can do without them.
     * 
     * @since 4.0.6
     */
    public void setHasResizeGrip(boolean setting) {
        GtkStatusbar.setHasResizeGrip(this, setting);
    }

    /**
     * Set the message showing in the Statusbar. You can call this frequently
     * with whatever indication you wish to display to the user; the last
     * message will be discarded.
     * 
     * <p>
     * If you want to clear the message, simply pass the empty string,
     * <code>""</code>.
     * 
     * @since 4.0.6
     */
    public void setMessage(String text) {
        GtkStatusbar.pop(this, 0);
        GtkStatusbar.push(this, 0, text);
    }
}
