/*
 * FileChooserAction.java
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

import org.freedesktop.bindings.Constant;

/**
 * Set which kind of FileChooser Dialog will appear.
 * 
 * @author Andrew Cowie
 * @since 4.0.2
 */
public final class FileChooserAction extends Constant
{
    private FileChooserAction(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * Open mode: the FileChooser will only let the user pick an existing
     * file.
     */
    public static final FileChooserAction OPEN = new FileChooserAction(GtkFileChooserAction.OPEN, "OPEN");

    /**
     * Save mode: the FileChooser will come up in a form suited to saving
     * files. It will let the user pick an existing file [TODO: which will
     * lead to an overwrite yes/no question, right?], or type in a new
     * filename.
     */
    public static final FileChooserAction SAVE = new FileChooserAction(GtkFileChooserAction.SAVE, "SAVE");

    /**
     * Open folder mode: set the FileChooser to enable the user pick a
     * specific directory.
     */

    public static final FileChooserAction SELECT_FOLDER = new FileChooserAction(
            GtkFileChooserAction.SELECT_FOLDER, "SELECT_FOLDER");

    /**
     * Create folder mode: put the FileChooser into a mode whereby it is
     * creating a new directory. It will, however, let the user name an
     * existing directory, which might be thought of as a bit odd, but it's a
     * usability feature making the interface consistent across GNOME.
     */
    public static final FileChooserAction CREATE_FOLDER = new FileChooserAction(
            GtkFileChooserAction.CREATE_FOLDER, "CREATE_FOLDER");
}
