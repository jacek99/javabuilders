/*
 * Editable.java
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
 * Methods common to Widgets which allow the line of text they display to be
 * edited.
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public interface Editable
{
    /**
     * Set the position of the cursor in this Editable. The cursor will be put
     * before the character at the position indicated.
     * 
     * @param position
     *            The value given must be less than or equal to the number of
     *            characters currently in the Editable field. Supplying a
     *            value of <code>-1</code> will cause the cursor to move to a
     *            position after the last character in the text.
     * 
     * @since 4.0.6
     */
    public void setPosition(int position);

    /**
     * The signal emitted when the text in the Editable has changed.
     * 
     * @author Andrew Cowie
     * @since 4.0.6
     */
    public interface Changed extends GtkEditable.ChangedSignal
    {
        void onChanged(Editable source);
    }

    /** @deprecated */
    public interface CHANGED extends GtkEditable.ChangedSignal
    {
    }

    /**
     * Hook up a handler for <code>Editable.Changed</code> signals.
     * 
     * @since 4.0.6
     */
    public void connect(Editable.Changed handler);

    /**
     * Select a region of the text in this Editable. The characters between
     * <code>start</code> up to <i>but not including</i> <code>end</code> will
     * be selected.
     * 
     * <p>
     * Calling <code>selectRegion(0, 0)</code> will remove the selection
     * (although that will only happen if some other Widget has the focus; in
     * Windows where there is only one control the user can manipulate an
     * Entry will end up selected no matter what).
     * 
     * @param end
     *            If negative, then the selection will be from
     *            <code>start</code> to the end of the text in the Editable.
     * @since 4.0.6
     */
    public void selectRegion(int start, int end);
}
