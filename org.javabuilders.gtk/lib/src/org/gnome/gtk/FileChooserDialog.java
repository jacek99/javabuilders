/*
 * FileChooserDialog.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import java.net.URI;

/**
 * A Dialog suitable for operations that need to select a file, such as "File
 * -> Open" or "File -> Save" commands.
 * 
 * <p>
 * A FileChooserDialog is just a Dialog with a FileChooserWidget plus
 * appropriate Buttons that corresponding to the specified
 * {@link FileChooserAction FileChooserAction}. Otherwise, all the methods
 * provided by the FileChooser interface are available which gives you the
 * necessary power to manipulate the selection received from the Dialog.
 * 
 * <p>
 * Using a FileChooserDialog to open a file could go like this:
 * 
 * <pre>
 * FileChooserDialog dialog;
 * ResponseType response;
 * 
 * // instantiate
 * dialog = new FileChooserDialog(&quot;Open File&quot;, window, FileChooserAction.OPEN);
 * 
 * // run the Dialog
 * response = dialog.run();
 * dialog.hide();
 * 
 * // deal with the result
 * if (response == ResponseType.OK) {
 *     open(dialog.getFilename());
 * }
 * </pre>
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.5
 */
public class FileChooserDialog extends Dialog implements FileChooser
{
    protected FileChooserDialog(long pointer) {
        super(pointer);
    }

    /**
     * Create a new FileChooserDialog.
     * 
     * <p>
     * Buttons appropriate to each of the different FileChooserActions have
     * been preconfigured in the <var>action area</var> of the Dialog. In all
     * cases, the executive to go ahead with the action will be the return of
     * ResponseType <code>OK</code>.
     * 
     * @param title
     *            the text to use in the title bar of the Dialog Window as
     *            drawn by the window manager, or <code>null</code> if you
     *            want a blank title.
     * @param parent
     *            the transient parent of the Dialog. While <code>null</code>
     *            is allowed, things are designed to work properly on the
     *            assumption that a parent is specified so it is recommended
     *            that you do so.
     * @param action
     *            which style of FileChooser you want.
     */
    public FileChooserDialog(String title, Window parent, FileChooserAction action) {
        super(GtkFileChooserDialog.createFileChooserDialog(title, parent, action, null));

        this.addButton(Stock.CANCEL, ResponseType.CANCEL);
        if (action == FileChooserAction.SAVE) {
            this.addButton(Stock.SAVE, ResponseType.OK);
        } else if (action == FileChooserAction.OPEN) {
            this.addButton(Stock.OPEN, ResponseType.OK);
        } else if (action == FileChooserAction.SELECT_FOLDER) {
            this.addButton(Stock.OK, ResponseType.OK);
        } else if (action == FileChooserAction.CREATE_FOLDER) {
            this.addButton(Stock.NEW, ResponseType.OK);
        }
    }

    public String getCurrentFolder() {
        return GtkFileChooser.getCurrentFolder(this);
    }

    public boolean setCurrentFolder(String directory) {
        return GtkFileChooser.setCurrentFolder(this, directory);
    }

    public String getFilename() {
        return GtkFileChooser.getFilename(this);
    }

    public void setAction(FileChooserAction action) {
        GtkFileChooser.setAction(this, action);
    }

    public FileChooserAction getAction() {
        return GtkFileChooser.getAction(this);
    }

    public URI getURI() {
        String uri = GtkFileChooser.getUri(this);
        if (uri != null) {
            return URI.create(uri);
        } else {
            return null;
        }
    }

    public boolean setFilename(String filename) {
        if (filename.charAt(0) == '/') {
            return GtkFileChooser.setFilename(this, filename);
        } else {
            throw new IllegalArgumentException("The filename argument must be an absolute path");
        }
    }
}
