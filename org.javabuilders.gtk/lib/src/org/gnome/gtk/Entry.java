/*
 * Entry.java
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
 * A data entry field allowing the user to input a single line of text.
 * 
 * @author Sebastian Mancke
 * @author Andrew Cowie
 * @since 4.0.3
 */
public class Entry extends Widget implements Editable, CellEditable
{
    protected Entry(long pointer) {
        super(pointer);
    }

    /**
     * Construct a new Entry
     * 
     * @since 4.0.3
     */
    public Entry() {
        this(GtkEntry.createEntry());
    }

    /**
     * Construct a new Entry, initialized with the specified text
     * 
     * @since 4.0.3
     */
    public Entry(String text) {
        this(GtkEntry.createEntry());
        setText(text);
    }

    /**
     * Replace the current contents of the Entry with the supplied text.
     * 
     * @since 4.0.3
     */
    public void setText(String text) {
        GtkEntry.setText(this, text);
    }

    /**
     * Get the text currently showing in the Entry. This is typically the most
     * significant method as it is the one you use to get the result of the
     * user's activity upon receiving a {@link Entry.Activate} signal.
     * 
     * @since 4.0.3
     */
    public String getText() {
        return GtkEntry.getText(this);
    }

    /**
     * Specify the maximum number of characters the user is allowed to enter.
     * Note that if the current text in the Entry is longer than the specified
     * length, the contents will be truncated!
     * 
     * @param max
     *            A value of <code>0</code> indicates no maximum length.
     * @since 4.0.3
     */
    public void setMaxLength(int max) {
        GtkEntry.setMaxLength(this, max);
    }

    /**
     * Returns the current maximum width, in characters, the text in the Entry
     * is allowed to be.
     * 
     * @since 4.0.3
     */
    public int getMaxLength() {
        return GtkEntry.getMaxLength(this);
    }

    /**
     * Set whether the text in the entry is visible or obscured. This is
     * typically used for password fields.
     * 
     * When set to be not visible, characters entered are shown with a
     * <code>*</code> instead. This default can be changed with
     * {@link #setInvisibleChar(char) setInvisibleChar()}.
     * 
     * @param visible
     *            true for showing, false for hiding
     * @since 4.0.3
     */
    public void setVisibleChars(boolean visible) {
        GtkEntry.setVisibility(this, visible);
    }

    /**
     * Returns the state of whether text in the Entry are visible or hidden by
     * an obscuring character.
     * 
     * @return <code>true</code> if characters entered are visible,
     *         <code>false</code> if obscured.
     * @since 4.0.3
     */
    public boolean isVisibleChars() {
        return GtkEntry.getVisibility(this);
    }

    /**
     * Change the character used to obscure text when
     * {@link #setVisibleChars(boolean) setVisibleChars()} is false.
     * 
     * @param replacement
     *            The new character to be used to obscure text. A value of
     *            <code>0</code> will cause no feedback to displayed at all
     *            when the user is typing in the Entry.
     */
    public void setInvisibleChar(char replacement) {
        GtkEntry.setInvisibleChar(this, replacement);
    }

    /**
     * Set whether the text in the Entry can be change by the user.
     * 
     * @since 4.0.3
     */
    public void setEditable(boolean editable) {
        GtkEditable.setEditable(this, editable);
    }

    /**
     * The <code>Entry.Activate</code> signal occurs when the user presses <b>
     * <code>Enter</code></b> or <b><code>Return</code></b> in an Entry.
     * 
     * <p>
     * Note that the other significant signal on an Entry is
     * <code>Editable.Changed</code>, inherited from Editable. There is a
     * {@link Entry#connect(org.gnome.gtk.Editable.Changed) connect()} method.
     * 
     * @since 4.0.3
     */
    public interface Activate extends GtkEntry.ActivateSignal
    {
        public void onActivate(Entry source);
    }

    /**
     * Connects an <code>Entry.Activate</code> handler to the Widget.
     * 
     * @since 4.0.3
     */
    public void connect(Entry.Activate handler) {
        GtkEntry.connect(this, handler, false);
    }

    /** @deprecated */
    public interface ACTIVATE extends GtkEntry.ActivateSignal
    {
    }

    /** @deprecated */
    public void connect(ACTIVATE handler) {
        assert false : "use Entry.Activate instead";
        GtkEntry.connect(this, handler, false);
    }

    public void setPosition(int position) {
        if (position < -1) {
            throw new IllegalArgumentException(
                    "Position must be -1 to indicate you want it after the last character.");
        }
        GtkEditable.setPosition(this, position);
    }

    /**
     * Request that the width of this Entry be wide enough for a given number
     * of characters.
     * 
     * <p>
     * As with all font related operations, there are a number of competing
     * approximations involved. In particular, this method operates by
     * influencing the size <i>requested</i> by this Widget; the box packing
     * model will still have the final say in the size-allocation phase.
     * 
     * <p>
     * See also Label's {@link Label#setWidthChars(int) setWidthChars()}; the
     * challenges and constraints involved are similar.
     * 
     * @param width
     *            A setting of <code>-1</code> will return the Entry to normal
     *            sizing behaviour.
     * @since 4.0.6
     */
    public void setWidthChars(int width) {
        GtkEntry.setWidthChars(this, width);
    }

    /**
     * The signal emitted when the text in the Entry has changed.
     * 
     * @author Andrew Cowie
     * @since 4.0.8
     */
    /*
     * This signal is inherited from Editable which is implemented by Entry,
     * but some IDEs did not show Entry.Changed it as an option beside
     * Editable.Activate when doing code completion. We have therefore exposed
     * it (again) here to force the issue.
     */
    public interface Changed extends Editable.Changed
    {
    }

    /**
     * Connect a <code>Editable.Changed</code> handler. Note that you can say:
     * 
     * <pre>
     * e.connect(new Entry.Changed() {
     *     public void onChanged(Editable source) {
     *         doStuff();
     *     }
     * });
     * </pre>
     * 
     * as the Editable.Changed interface is [re]exposed here.
     * 
     * @since 4.0.6
     */
    public void connect(Editable.Changed handler) {
        GtkEditable.connect(this, handler, false);
    }

    /** @deprecated */
    public void connect(CHANGED handler) {
        assert false : "use Editable.Changed instead";
        GtkEditable.connect(this, handler, false);
    }

    public void selectRegion(int start, int end) {
        GtkEditable.selectRegion(this, start, end);
    }

    /**
     * Set the alignment of the the text being displayed in the Entry.
     * 
     * @param xalign
     *            A value from <code>0.0f</code> for fully left-aligned
     *            through <code>1.0f</code> for fully right-aligned. You can
     *            use the constants {@link Alignment#LEFT LEFT},
     *            {@link Alignment#CENTER CENTER} and {@link Alignment#RIGHT
     *            RIGHT} in Alignment for convenience if you like. No, this
     *            has nothing to do with politics.
     * @since 4.0.6
     */
    /*
     * Supposedly this reverses for RTL text layout. It'd be nice of someone
     * could test that and document it to that effect if true.
     */
    public void setAlignment(float xalign) {
        if ((xalign < 0.0) || (xalign > 1.0)) {
            throw new IllegalArgumentException("xalign must be between 0.0 and 1.0");
        }
        GtkEntry.setAlignment(this, xalign);
    }

    /**
     * Set whether the Entry has a bevelled frame around it or not. The
     * default (as you will be well accustomed to seeing) is <code>true</code>
     * .
     * 
     * <p>
     * As this decoration is a strong visual cue for users to realize that
     * they are able to enter text into a given control, your are discouraged
     * from turning it off unless you really need to minutely control
     * placement; in that case, see also
     * {@link #setInnerBorder(int, int, int, int) setInnerBorder()}.
     * 
     * @since 4.0.8
     */
    public void setHasFrame(boolean setting) {
        GtkEntry.setHasFrame(this, setting);
    }

    /**
     * Set the padding between the text entry control itself, and the
     * surrounding decoration. This overrides the <var>inner-border</var>
     * style property set by the theme.
     * 
     * <p>
     * Ordinarily you shouldn't need this; in general you should leave things
     * alone so that Entries appear uniform across the user's desktop. This is
     * provided for the rare cases that minute positioning control is required
     * (think of what goes on when you edit text in a CellRendererText) it is
     * possible to do so.
     * 
     * @since 4.0.8
     */
    public void setInnerBorder(int left, int right, int top, int bottom) {
        GtkEntryOverride.setInnerBorder(this, left, right, top, bottom);
    }
}
