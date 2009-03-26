/*
 * Clipboard.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import org.gnome.gdk.EventOwnerChange;
import org.gnome.gdk.Pixbuf;
import org.gnome.glib.Object;

/**
 * A mechanism allowing a user to copy data between programs.
 * 
 * <p>
 * Clipboards were originally for sharing data between different processes,
 * but of course conventional practise these days is that cut, copy and paste
 * are used quite ubiquitously both as a way to copy things from one program
 * to another, as well as simply moving data around within an application.
 * 
 * <p>
 * You can copy text to the main system Clipboard quite simply, as follows:
 * 
 * <pre>
 * clipboard = Clipboard.getDefault();
 * clipboard.setText(&quot;Hello there&quot;);
 * </pre>
 * 
 * @author Serkan Kaba
 * @author Andrew Cowie
 * @since 4.0.10
 * @see <a
 *      href="http://www.freedesktop.org/wiki/Specifications/clipboards-spec">The
 *      gory historical details that lead to the FreeDesktop clipboard
 *      specification</a>
 */
/*
 * TODO The underlying GtkClipboard is wildly complex and very powerful,
 * allowing for asynchronous data transfer. If anything, it seems a bit
 * overboard, and perhaps a simpler synchronous implementation would be handy.
 * Regardless, someone will have to manually set up signals to proxy for these
 * callbacks as at time of writing we don't have a [generated] way to
 * automatically represent function pointers. See the native code underneath
 * TreeModelFilter's setVisibleCallback() for an example.
 */
public class Clipboard extends Object
{
    protected Clipboard(long pointer) {
        super(pointer);
    }

    /**
     * Get the default Clipboard.
     * 
     * <p>
     * <i>There are actually quite a number of different clipboards in the X
     * server! Normal people are used to there only being one, and current
     * conventions are that the</i> <code>CLIPBOARD</code> <i>clipboard is the
     * one that cut, copy, and paste operations go to. That is the clipboard
     * returned here.</i>
     * 
     * @since 4.0.10
     */
    /*
     * FUTURE, so, obviously, if someone desperately needs access to PRIMARY,
     * they can expose it as getPrimary().
     */
    public static Clipboard getDefault() {
        return GtkClipboardOverride.get();
    }

    /**
     * Copy the given text to this Clipboard.
     * 
     * @since 4.0.10
     */
    public void setText(String text) {
        GtkClipboard.setText(this, text, -1);
    }

    /**
     * Copy the given image to this Clipboard.
     * 
     * @since 4.0.10
     */
    public void setImage(Pixbuf pixbuf) {
        GtkClipboard.setImage(this, pixbuf);
    }

    /**
     * This signal is emitted any time the owner of the Clipboard [contents]
     * changes.
     * 
     * <p>
     * Somewhat surprisingly, you will get a hit on this every time you call
     * Clipboard's {@link Clipboard#setText(String) setText()}. This is
     * because calling that method passes a copy of the String to the X server
     * which takes ownership of the data for passage to other applications.
     * 
     * <p>
     * <i>That's a behaviour we might like to fix.</i>
     * 
     * @author Andrew Cowie
     * @since 4.0.10
     */
    /*
     * Strincyl
     */
    public interface OwnerChange extends GtkClipboard.OwnerChangeSignal
    {
        public void onOwnerChange(Clipboard source, EventOwnerChange event);
    }

    /**
     * Hook up a <code>Clipboard.OwnerChange</code> handler.
     * 
     * @since 4.0.10
     */
    public void connect(Clipboard.OwnerChange handler) {
        GtkClipboard.connect(this, handler, false);
    }

    /**
     * Get a textual representation of whatever is in this Clipboard. Some
     * applications use the system clipboard for internal transfer of complex
     * state, so if you've gone and copied some complex vector graphic using
     * an application like Inkscape, you may find that the String you get back
     * from calling this method is a little obscure.
     * 
     * <p>
     * This call blocks until the text is available, though the main loop may
     * iterate while it is doing so.
     * 
     * <p>
     * <i>The underlying implementation is actually asynchronous; a request
     * for the data has to be made through the X server to whatever
     * application actually placed the data in the clipboard.</i>
     * 
     * @since 4.0.10
     */
    /*
     * Signature change to complement setText(). If we end up exposing the
     * full family of waitForWhatever() functions then this can probably be
     * kept as a convenience method, but even then they should perhaps be
     * getWhatever().
     */
    public String getText() {
        return GtkClipboard.waitForText(this);
    }
}
