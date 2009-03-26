/*
 * FileChooserButton.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd and Others
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
 * Displays a filename and a Button which, if pressed, opens a
 * FileChooserDialog allowing the user to select the file.
 * 
 * <p>
 * This Widget implements the FileChooser interface, which has most of the
 * methods necessary to manipulate the selection in the Widget.
 * 
 * <p>
 * Note that FileChooserButton only supports selecting files (mode
 * {@link FileChooserAction#OPEN OPEN}) or directories (mode
 * {@link FileChooserAction#SELECT_FOLDER SELECT_FOLDER}). If you need
 * something more complicated, then you'll need to use wrap a
 * FileChooserWidget in a custom Widget or launch a FileChooserDialog.
 * 
 * @author Andrew Cowie
 * @since 4.0.2
 * @see FileChooserWidget
 * @see FileChooserDialog
 */
public class FileChooserButton extends HBox implements FileChooser
{
    protected FileChooserButton(long pointer) {
        super(pointer);
    }

    /**
     * Creates a new FileChooserButton. The selected file is unset, and will
     * appear as "(none)" in the display.
     * 
     * @param title
     *            a title for the FileChooserDialog when it is popped.
     * @param action
     *            which style of FileChooser you want. Only
     *            {@link FileChooserAction#OPEN OPEN} (selecting a single
     *            file), and {@link FileChooserAction#SELECT_FOLDER
     *            SELECT_FOLDER}, (selecting a single directory) are enabled
     *            for FileChooserButton.
     */
    public FileChooserButton(String title, FileChooserAction action) {
        super(GtkFileChooserButton.createFileChooserButton(title, action));
    }

    public String getFilename() {
        return GtkFileChooser.getFilename(this);
    }

    public String getCurrentFolder() {
        return GtkFileChooser.getCurrentFolder(this);
    }

    public boolean setCurrentFolder(String directory) {
        return GtkFileChooser.setCurrentFolder(this, directory);
    }

    public void setAction(FileChooserAction action) {
        GtkFileChooser.setAction(this, action);
    }

    public FileChooserAction getAction() {
        return GtkFileChooser.getAction(this);
    }

    /*
     * Changes to this implementation need to be dittoed in FileChooserDialog
     * and FileChooserWidget!
     */
    public URI getURI() {
        String uri = GtkFileChooser.getUri(this);
        if (uri != null) {
            return URI.create(uri);
        } else {
            return null;
        }
    }

    /**
     * Signal emitted when the file indicated by this FileChooserButton has
     * been set by the user.
     * 
     * @author Andrew Cowie
     * @since 4.0.5
     */
    public interface FileSet extends GtkFileChooserButton.FileSetSignal
    {
        public void onFileSet(FileChooserButton source);
    }

    /**
     * Hook up a callback to handle the <code>FILE_SET</code> signal generated
     * when the file or directory has been selected by the user using this
     * FileChooserButton.
     * 
     * @since 4.0.5
     */
    public void connect(FileSet handler) {
        GtkFileChooserButton.connect(this, handler, false);
    }

    public boolean setFilename(String filename) {
        if (filename.charAt(0) == '/') {
            return GtkFileChooser.setFilename(this, filename);
        } else {
            throw new IllegalArgumentException("The filename argument must be an absolute path");
        }
    }
}
