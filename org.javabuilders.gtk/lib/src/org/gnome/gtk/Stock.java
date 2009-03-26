/*
 * Stock.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2007      Vreixo Formoso
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import java.util.HashMap;

/**
 * Identifiers for the different standard UI elements used to create
 * consistent user interfaces across GNOME applications. Stock items represent
 * commonly-used Menu or Toolbar items such as "Open" and "Quit". A stock item
 * defines properties used when creating new Buttons, MenuItems, etc including
 * the localized user-visible Label, the icon used to identify it the control,
 * and the default keyboard accelerator that triggers it.
 * 
 * <p>
 * GTK comes with a fairly large pre-built set of stock items. You should use
 * them in your programs whenever possible thus ensuring your application will
 * have a look and feel consistent with other GNOME applications. This also
 * aids in discoverability by helping the user identify what a Button does or
 * how to execute a common operation in your application.
 * 
 * <p>
 * Each stock item is identified by a Stock constant that you can pass to
 * constructors of different widgets, such as Buttons. When your application
 * does a common task represented by one of the constants in this class, you
 * should use it to identify that task in your Menus and Buttons.
 * 
 * <p>
 * Please note that this is <i>not</i> a place to get creative. Well known
 * stock icons have a defined behaviour which users can rightfully expect to
 * be consistent. Using these images in non-standard ways will result in a
 * severe usability impact and will cause people to ridicule your program.
 * 
 * <p>
 * <i>In GTK, stock-ids are just plain <code>gchar*</code> strings. We
 * implement these constants here for commodity and type-safety.</i>
 * 
 * <p>
 * <dl>
 * <dt><b>WARNING</b>
 * <dd>TODO This class may have to be refactored when [if] we introduce
 * coverage of StockItem.</dd>
 * </dl>
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.4
 */
/*
 * FIXME by exception, this has been merged with incomplete documentation.
 * It's a huge class, so we'll let people build it up over time. If you know
 * something about the usage of one of these stock items (for example, see
 * Stock.CLOSE), please contribute!
 */
public class Stock
{
    /**
     * The GTK side string constant used to identify this Stock[Item].
     */
    private final String stockId;

    private static final HashMap<String, Stock> knownStocks;

    static {
        knownStocks = new HashMap<String, Stock>(100, 0.999999f);
    }

    /**
     * Construct a new Stock constant from a given string. This is provided so
     * that if we missed an ID that you desperately need, you can subclass and
     * create it. Ideally, though, we'd appreciate it if you'd point out what
     * it is about that stock-id that you needed, and if appropriate submit a
     * patch adding it to this class instead.
     */
    protected Stock(String stockId) {
        this.stockId = stockId;
        knownStocks.put(stockId, this);
    }

    /**
     * Get the stock-id String expected by GTK representing this stock item.
     */
    /*
     * Interesting design question. For the moment, keep this with restricted
     * visibility on the basis of "we don't expose internals!". If we find
     * ourselves with problems out-of-package, then we can reconsider.
     */
    protected String getStockId() {
        return stockId;
    }

    /**
     * Look up a Stock object for the supplied String id as used in the
     * underlying library. Returns <code>null</code> if not found.
     */
    static Stock instanceFor(String stockId) {
        return knownStocks.get(stockId);
    }

    /**
     * Usually used to identify an Action that shows version, copyright, and
     * authorship information about the application.
     */
    public static final Stock ABOUT = new Stock("gtk-about");

    public static final Stock ADD = new Stock("gtk-add");

    public static final Stock APPLY = new Stock("gtk-apply");

    public static final Stock BOLD = new Stock("gtk-bold");

    public static final Stock CANCEL = new Stock("gtk-cancel");

    public static final Stock CDROM = new Stock("gtk-cdrom");

    public static final Stock CLEAR = new Stock("gtk-clear");

    /**
     * Close the current Window or the current document. While meaning of this
     * does vary a bit between applications depending on their purpose, the
     * one thing close does <b>not</b> mean is to <i>quit</i> the application
     * (unless the program's behaviour is to terminate when the last document
     * is closed - that's ok). For outright exiting a program, see
     * {@link #QUIT QUIT}.
     * 
     * <p>
     * <img src="file:///usr/share/icons/gnome/22x22/actions/gtk-close.png">
     */
    /*
     * FIXME Ok, that IMG is pretty crazy to have there, but why not? Of
     * course, we need to have a canonical path that would actually work for
     * everyone, and that's a tricky proposition [while last I checked most
     * the distros left this sort of thing alone. Probably can't trust Debian,
     * though]. A better idea would be to source an image from somewhere in
     * http://library.gnome.org/devel/...
     */
    public static final Stock CLOSE = new Stock("gtk-close");

    public static final Stock COLOR_PICKER = new Stock("gtk-color-picker");

    public static final Stock CONVERT = new Stock("gtk-convert");

    public static final Stock CONNECT = new Stock("gtk-connect");

    public static final Stock COPY = new Stock("gtk-copy");

    public static final Stock CUT = new Stock("gtk-cut");

    public static final Stock DELETE = new Stock("gtk-delete");

    public static final Stock DIALOG_AUTHENTICATION = new Stock("gtk-dialog-authentication");

    public static final Stock DIALOG_ERROR = new Stock("gtk-dialog-error");

    public static final Stock DIALOG_INFO = new Stock("gtk-dialog-info");

    public static final Stock DIALOG_QUESTION = new Stock("gtk-dialog-question");

    public static final Stock DIALOG_WARNING = new Stock("gtk-dialog-warning");

    public static final Stock DIRECTORY = new Stock("gtk-directory");

    public static final Stock DISCONNECT = new Stock("gtk-disconnect");

    public static final Stock DND = new Stock("gtk-dnd");

    public static final Stock DND_MULTIPLE = new Stock("gtk-dnd-multiple");

    public static final Stock EDIT = new Stock("gtk-edit");

    public static final Stock EXECUTE = new Stock("gtk-execute");

    public static final Stock FILE = new Stock("gtk-file");

    public static final Stock FIND = new Stock("gtk-find");

    public static final Stock FIND_AND_REPLACE = new Stock("gtk-find-and-replace");

    public static final Stock FLOPPY = new Stock("gtk-floppy");

    public static final Stock FULLSCREEN = new Stock("gtk-fullscreen");

    public static final Stock GOTO_BOTTOM = new Stock("gtk-goto-bottom");

    public static final Stock GOTO_FIRST = new Stock("gtk-goto-first");

    public static final Stock GOTO_LAST = new Stock("gtk-goto-last");

    public static final Stock GOTO_TOP = new Stock("gtk-goto-top");

    public static final Stock GO_BACK = new Stock("gtk-go-back");

    public static final Stock GO_DOWN = new Stock("gtk-go-down");

    public static final Stock GO_FORWARD = new Stock("gtk-go-forward");

    public static final Stock GO_UP = new Stock("gtk-go-up");

    public static final Stock HARDDISK = new Stock("gtk-harddisk");

    public static final Stock HELP = new Stock("gtk-help");

    public static final Stock HOME = new Stock("gtk-home");

    public static final Stock INDENT = new Stock("gtk-indent");

    public static final Stock INDEX = new Stock("gtk-index");

    public static final Stock INFO = new Stock("gtk-info");

    public static final Stock ITALIC = new Stock("gtk-italic");

    public static final Stock JUMP_TO = new Stock("gtk-jump-to");

    public static final Stock JUSTIFY_CENTER = new Stock("gtk-justify-center");

    public static final Stock JUSTIFY_FILL = new Stock("gtk-justify-fill");

    public static final Stock JUSTIFY_LEFT = new Stock("gtk-justify-left");

    public static final Stock JUSTIFY_RIGHT = new Stock("gtk-justify-right");

    public static final Stock LEAVE_FULLSCREEN = new Stock("gtk-leave-fullscreen");

    public static final Stock MEDIA_FORWARD = new Stock("gtk-media-forward");

    public static final Stock MEDIA_NEXT = new Stock("gtk-media-next");

    public static final Stock MEDIA_PAUSE = new Stock("gtk-media-pause");

    public static final Stock MEDIA_PLAY = new Stock("gtk-media-play");

    public static final Stock MEDIA_PREVIOUS = new Stock("gtk-media-previous");

    public static final Stock MEDIA_RECORD = new Stock("gtk-media-record");

    public static final Stock MEDIA_REWIND = new Stock("gtk-media-rewind");

    public static final Stock MEDIA_STOP = new Stock("gtk-media-stop");

    public static final Stock MISSING_IMAGE = new Stock("gtk-missing-image");

    public static final Stock NETWORK = new Stock("gtk-network");

    public static final Stock NEW = new Stock("gtk-new");

    public static final Stock NO = new Stock("gtk-no");

    public static final Stock OK = new Stock("gtk-ok");

    public static final Stock OPEN = new Stock("gtk-open");

    public static final Stock ORIENTATION_LANDSCAPE = new Stock("gtk-orientation-landscape");

    public static final Stock ORIENTATION_PORTRAIT = new Stock("gtk-orientation-portrait");

    public static final Stock ORIENTATION_REVERSE_LANDSCAPE = new Stock(
            "gtk-orientation-reverse-landscape");

    public static final Stock ORIENTATION_REVERSE_PORTRAIT = new Stock(
            "gtk-orientation-reverse-portrait");

    public static final Stock PASTE = new Stock("gtk-paste");

    public static final Stock PREFERENCES = new Stock("gtk-preferences");

    public static final Stock PRINT = new Stock("gtk-print");

    public static final Stock PRINT_PREVIEW = new Stock("gtk-print-preview");

    public static final Stock PROPERTIES = new Stock("gtk-properties");

    /**
     * Terminate the application. If you just want to close a single document
     * Window, then use {@link #CLOSE CLOSE}.
     * 
     * <p>
     * <img src="file:///usr/share/icons/gnome/22x22/actions/gtk-quit.png">
     * 
     * <p>
     * <i>There's no</i> <code>EXIT</code><i>, in case you were wondering.
     * </i>
     */
    public static final Stock QUIT = new Stock("gtk-quit");

    public static final Stock REDO = new Stock("gtk-redo");

    public static final Stock REFRESH = new Stock("gtk-refresh");

    public static final Stock REMOVE = new Stock("gtk-remove");

    public static final Stock REVERT_TO_SAVED = new Stock("gtk-revert-to-saved");

    public static final Stock SAVE = new Stock("gtk-save");

    public static final Stock SAVE_AS = new Stock("gtk-save-as");

    public static final Stock SELECT_ALL = new Stock("gtk-select-all");

    public static final Stock SELECT_COLOR = new Stock("gtk-select-color");

    public static final Stock SELECT_FONT = new Stock("gtk-select-font");

    public static final Stock SORT_ASCENDING = new Stock("gtk-sort-ascending");

    public static final Stock SORT_DESCENDING = new Stock("gtk-sort-descending");

    public static final Stock SPELL_CHECK = new Stock("gtk-spell-check");

    public static final Stock STOP = new Stock("gtk-stop");

    public static final Stock STRIKETHROUGH = new Stock("gtk-strikethrough");

    public static final Stock UNDELETE = new Stock("gtk-undelete");

    public static final Stock UNDERLINE = new Stock("gtk-underline");

    public static final Stock UNDO = new Stock("gtk-undo");

    public static final Stock UNINDENT = new Stock("gtk-unindent");

    public static final Stock YES = new Stock("gtk-yes");

    public static final Stock ZOOM_100 = new Stock("gtk-zoom-100");

    public static final Stock ZOOM_FIT = new Stock("gtk-zoom-fit");

    public static final Stock ZOOM_IN = new Stock("gtk-zoom-in");

    public static final Stock ZOOM_OUT = new Stock("gtk-zoom-out");
}
