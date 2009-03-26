/*
 * TreeRowReference.java
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

import org.gnome.glib.Boxed;

/**
 * A stable reference to a specific row in a TreeModel. A TreeRowReference
 * listens to all changes made to the model (be they insertions, deletions,
 * sorting being applied, etc) and adjusts itself internally so that the same
 * row is pointed at by the instance regardless.
 * 
 * <p>
 * This class is primarily necessary because a TreeIter instance is no longer
 * usable if the model changes. Neither are TreePaths for that matter; if you
 * change the sorting order then the row pointed at by TreePath "2" will
 * [likely] be different before and after the sort.
 * 
 * <p>
 * Typical usage of this is from a TreeIter as follows:
 * 
 * <pre>
 * TreeModel source;
 * TreePath path;
 * TreeIter row;
 * TreeRowReference ref;
 * ...
 * 
 * path = source.getPath(row);
 * ref = new TreeRowReference(source, path);
 * </pre>
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public final class TreeRowReference extends Boxed
{
    protected TreeRowReference(long pointer) {
        super(pointer);
    }

    /**
     * Construct a new TreeRowReference for the given TreePath into the given
     * Model.
     * 
     * @since 4.0.6
     */
    public TreeRowReference(TreeModel model, TreePath path) {
        super(GtkTreeRowReference.createTreeRowReference(model, path));
    }

    protected void release() {
        GtkTreeRowReference.free(this);
    }

    /**
     * Get a TreePath representing the row that this TreeRowReference is
     * currently pointing at.
     * 
     * @return You'll get <code>null</code> back if the TreeRowReference is no
     *         longer valid, which would happen if the row has been deleted.
     * 
     * @since 4.0.6
     */
    public TreePath getPath() {
        return GtkTreeRowReference.getPath(this);
    }
}
