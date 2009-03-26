/*
 * Frame.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * A decorative border frame with an optional label. This is typically used to
 * surround a group of widgets with a a visual hint grouping them together.
 * 
 * <p>
 * These are frequently over-used and so using Frames is actually highly
 * discouraged: in general Widgets already have sufficient decoration and
 * adding extra horizontal and vertical lines distracts the user and actually
 * makes it <b>harder</b> to distinguish the Widgets from one another. If you
 * need to group widgets, do so with white space or use a technique like
 * applying {@link SizeGroup}s to give a set of Widgets a consistent
 * appearance.
 * 
 * <p>
 * Note that the "label" can be a full Widget in its own right; if you use the
 * methods which take a text string they will transparently create a Label for
 * you containing that text and using it on the edge of the Frame.
 * 
 * @author Sebastian Mancke
 * @author Andrew Cowie
 * @author Vreixo Formoso
 * @since 4.0.3
 */
public class Frame extends Bin
{
    protected Frame(long pointer) {
        super(pointer);
    }

    /**
     * Construct a new Frame with a simple text label.
     * 
     * @param label
     *            The desired label, or <code>null</code> if you don't want to
     *            use any label.
     */
    public Frame(String label) {
        super(GtkFrame.createFrame(label));
    }

    /**
     * Set the text label for the Frame (assuming you created it with a Label
     * in the first place).
     */
    public void setLabel(String label) {
        GtkFrame.setLabel(this, label);
    }

    /**
     * Returns the text from the frame's edge (assuming it's a Label Widget as
     * is usual practise).
     */
    public String getLabel() {
        return GtkFrame.getLabel(this);
    }

    /**
     * Set a Widget to be the "label" for the Frame.
     */
    public void setLabelWidget(Widget label) {
        GtkFrame.setLabelWidget(this, label);
    }

    /**
     * Returns the Widget being used as the "label" of the Frame.
     */
    public Widget getLabelWidget() {
        return GtkFrame.getLabelWidget(this);
    }

    /**
     * Set the ShadowType of the Frame, that will determine the appearance of
     * the outline. The default is {@link ShadowType#ETCHED_IN ETCHED_IN}.
     * 
     * @since 4.0.7
     */
    public void setShadowType(ShadowType type) {
        GtkFrame.setShadowType(this, type);
    }

    /**
     * Sets the alignment of the Frame's label.
     * 
     * @param xalign
     *            The position of the label along the top edge of the widget.
     *            A value of <code>0.0f</code> represents left alignment;
     *            <code>1.0f</code> represents right alignment. The default
     *            value is <code>0.0f</code>
     * @param yalign
     *            The vertical alignment of the label. A value of
     *            <code>0.0f</code> aligns under the frame; <code>1.0f</code>
     *            aligns above the frame. The default value is
     *            <code>0.5f</code>.
     * @since 4.0.7
     */
    public void setLabelAlign(float xalign, float yalign) {
        if ((xalign < 0.0) || (xalign > 1.0)) {
            throw new IllegalArgumentException("xalign must be between 0.0 and 1.0, inclusive.");
        }
        if ((yalign < 0.0) || (yalign > 1.0)) {
            throw new IllegalArgumentException("yalign must be between 0.0 and 1.0, inclusive.");
        }
        GtkFrame.setLabelAlign(this, xalign, yalign);
    }
}
