/*
 * TextIter.java
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

import org.gnome.gdk.Pixbuf;
import org.gnome.glib.Boxed;

/**
 * A TextIter defines a position inside a {@link TextBuffer} and allows you to
 * manipulate it.
 * 
 * <p>
 * TextIters are just used for immediate operations, and are invalidated any
 * time when the TextBuffer's contents change. If you need to hold a reference
 * to a specific position, convert this TextIter into a TextMark with
 * TextBuffer's {@link TextBuffer#createMark(TextIter, boolean) createMark()}.
 * 
 * <h2>Navigation</h2>
 * 
 * <p>
 * Quite a number of methods on TextIter allow you to move the location of the
 * pointer in the TextBuffer, including {@link #forwardLine() forwardLine()},
 * {@link #backwardChars(int) backwardChars()}, and many others. These are
 * both imperative commands and a means of querying. The TreeIter will attempt
 * to move as requested, while the boolean return value of the call will tell
 * you <i>whether</i> the TreeIter changed location. This can be used to
 * ascertain your position in a logical sense or to limit loops. Remeber,
 * however, that (unless otherwise marked) any method which changes the
 * TextBuffer invalidates the TextIters pointing into it.
 * 
 * @author Stefan Prelle
 * @author Andrew Cowie
 * @since 4.0.9
 */
public final class TextIter extends Boxed
{
    private TextBuffer buffer;

    protected TextIter(long pointer) {
        super(pointer);
    }

    /**
     * Allocate a blank TextIter structure. This is done by declaring one
     * locally, copying it, and returning the pointer to the copy.
     * 
     * <p>
     * <b>For use by bindings hackers only!</b>
     */
    TextIter(TextBuffer buffer) {
        super(GtkTextIterOverride.createTextIter());

        this.buffer = buffer;
    }

    void setBuffer(TextBuffer buffer) {
        this.buffer = buffer;
    }

    TextBuffer getBuffer() {
        if (buffer == null) {
            throw new IllegalStateException(
                    "\n"
                            + "Sorry, this TextIter doesn't have its internal reference to its parent TextBuffer set");
        }
        return buffer;
    }

    protected void release() {
        GtkTextIter.free(this);
    }

    /**
     * Creates an independent copy of the iterator.
     * 
     * <p>
     * <i>This is basically the same as a clone() on normal Java object would
     * do, but in this case is passed on to the native GTK layer.</i>
     * 
     * @since 4.0.9
     */
    public TextIter copy() {
        return GtkTextIter.copy(this);
    }

    /**
     * Returns the number of characters into the TextBuffer that this TextIter
     * is pointing. Offset counts are <code>0</code> origin.
     * 
     * 
     * <p>
     * If you have a given offset, you can use the TextBuffer
     * {@link TextBuffer#getIter(int) getIter()} method taking an
     * <code>int</code> to convert it into an TreeIter. If you want to move
     * this TreeIter to a given offset, then call {@link #setOffset(int)
     * setOffset()}.
     * 
     * @return A character offset from the start of the TextBuffer.
     * @since 4.0.9
     */
    public int getOffset() {
        return GtkTextIter.getOffset(this);
    }

    /**
     * Change this TextIter so that it points to the given offset into the
     * underlying TextBuffer.
     * 
     * @since 4.0.9
     */
    public void setOffset(int offset) {
        GtkTextIter.setOffset(this, offset);
    }

    /**
     * Get the line number of the TextBuffer that this TextIter points at.
     * Lines in a TextBuffer are numbered from <code>0</code>.
     * 
     * <p>
     * Be aware that this is the count of <i>unwrapped</i> lines in the
     * TextBuffer, where lines are split by newline characters. Most of the
     * time TextViews are set to wrap lines and thus a single very long line
     * will be presented visually as a paragraph (there is no definition of
     * paragraph in TextBuffer terms). If you're wondering about where a
     * position (the cursor, say) is down into a TextView widget, then see
     * {@link #startsDisplayLine(TextView) startsDisplayLine()} and other
     * related "<code>display</code>" methods.
     * 
     * @since 4.0.9
     */
    public int getLine() {
        return GtkTextIter.getLine(this);
    }

    /**
     * Move this TextIter to the start of the given line.
     * 
     * <p>
     * If <code>num</code> is negative or larger than the number of lines in
     * the TextBuffer, it moves the iterator to the start of the last line.
     * Line numbers are <code>0</code> origin.
     * 
     * @since 4.0.9
     */
    public void setLine(int row) {
        GtkTextIter.setLine(this, row);
    }

    /**
     * Get the character offset of this TextIter, counting from the start of
     * the line. The first character on the line has line offset
     * <code>0</code>.
     * 
     * <p>
     * <i> This is, in essence, a <code>getColumn()</code> method, but beware
     * that the number of characters you are into a given line in a TextBuffer
     * will only correspond to the column position on screen if the presenting
     * TextView is not wrapping lines.</i>
     * 
     * @since 4.0.9
     */
    public int getLineOffset() {
        return GtkTextIter.getLineOffset(this);
    }

    /**
     * Move the iterator within the current TextBuffer line to the given
     * character offset.
     * 
     * @since 4.0.9
     */
    public void setLineOffset(int column) {
        GtkTextIter.setLineOffset(this, column);
    }

    /**
     * Like {@link #getLineOffset()} but not counting those characters that
     * are tagged "invisible".
     * 
     * @return Offset from start of line
     * @since 4.0.9
     */
    public int getVisibleLineOffset() {
        return GtkTextIter.getVisibleLineOffset(this);
    }

    /**
     * Move this TextIter into the current line at the given offset, but
     * skipping characters that have been marked
     * {@link TextTag#setInvisible(boolean) invisible}.
     * 
     * <p>
     * See {@link #setLineOffset(int) setLineOffset()} for the method that
     * treats all characters normally.
     * 
     * @since 4.0.9
     */
    public void setVisibleLineOffset(int column) {
        GtkTextIter.setVisibleLineOffset(this, column);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer("TextIter ");
        buf.append(getOffset());
        buf.append(" (");
        buf.append(getLine());
        buf.append(",");
        buf.append(getLineOffset());
        buf.append(")");
        return buf.toString();
    }

    /**
     * Get the character immediately following the position this TextIter is
     * pointing at.
     * 
     * @return A <code>char</code> value of
     *         {@link TextBuffer#OBJECT_REPLACEMENT_CHARACTER
     *         OBJECT_REPLACEMENT_CHARACTER} indicates a non-character element
     *         (an embedded Pixbuf or Widget). You'll get <code>0</code> (ie
     *         <code>'\0'</code>, not <code>'0'</code>) if this TextIter is
     *         already at the TextBuffer's end.
     * @since 4.0.9
     */
    public char getChar() {
        return GtkTextIter.getChar(this);
    }

    /**
     * Bump this TextIter forward to the start of the next line. As a special
     * case, if you're already pointing at the last line of the TextBuffer,
     * then you will be moved to the end of the line.
     * 
     * <p>
     * Keep in mind that a line in the TextBuffer may well be wrapped when
     * displayed onscreen in a TextView as the several lines comprising a
     * paragraph; to move forward such a displayed line (ie <i>within</i> a
     * paragraph) use {@link #forwardDisplayLine(TextView)
     * forwardDisplayLine()}.
     * 
     * @since 4.0.9
     */
    public boolean forwardLine() {
        return GtkTextIter.forwardLine(this);
    }

    /**
     * Bump this TextIter backward to the start of the next line. As a special
     * case, if you're already pointing at the first line of the TextBuffer,
     * then you will be moved to the start of the line.
     * 
     * @since 4.0.9
     */
    public boolean backwardLine() {
        return GtkTextIter.backwardLine(this);
    }

    /**
     * Returns the text from the position defined by this TextIter to the
     * position referenced by a given TextIter. Non-printable elements like
     * Widgets and Pixbufs are not included in the returned string. If you
     * need them use {@link #getSlice(TextIter)}.
     * 
     * @since 4.0.9
     */
    public String getText(TextIter end) {
        return GtkTextIter.getText(this, end);
    }

    /**
     * Returns the text from the position defined by this TextIter to the
     * position referenced by a given TextIter, including non character
     * elements like widgets or pixbufs.
     * 
     * @since 4.0.9
     */
    public String getSlice(TextIter end) {
        return GtkTextIter.getSlice(this, end);
    }

    /**
     * Like {@link #getSlice(TextIter)} but returns only those parts that are
     * not flagged invisible.
     * 
     * @since 4.0.9
     */
    public String getVisibleSlice(TextIter end) {
        return GtkTextIter.getVisibleSlice(this, end);
    }

    /**
     * Like {@link #getText(TextIter)} but returns only those parts that are
     * not flagged invisible.
     * 
     * @since 4.0.9
     */
    public String getVisibleText(TextIter end) {
        return GtkTextIter.getVisibleText(this, end);
    }

    /**
     * If the element at this position is an image
     * 
     * @return The {@link Pixbuf} object or <code>null</code> if there is no
     *         image at the current position of this TextIter.
     * 
     * @since 4.0.9
     */
    public Pixbuf getPixbuf() {
        return GtkTextIter.getPixbuf(this);
    }

    /**
     * Return the {@link TextMark}s that exists at the location the TextIter
     * points to. If there are none present here then a zero length array will
     * be returned.
     * 
     * <p>
     * <i>GTK maintains a number of TextMarks internally in each TextBuffer
     * for its own purposes (even over and above <var>selection-bound</var>
     * and <var>insert</var>), proxies of which will get returned along with
     * any TextMarks you've put there. So this method isn't quite as useful as
     * one might like it to be.</i>
     * 
     * @since 4.0.9
     */
    /*
     * TODO wouldn't it be cool if we could filter the internal ones out and
     * only return user created TextMarks?
     */
    public TextMark[] getMarks() {
        return GtkTextIter.getMarks(this);
    }

    /**
     * Return all the TextTags that are either turned on or off at this
     * position.
     * 
     * @since 4.0.9
     */
    public TextTag[] getToggledTags(boolean state) {
        return GtkTextIter.getToggledTags(this, state);
    }

    /**
     * If there is a {@link TextChildAnchor} at the current position, return
     * it. Otherwise return <code>null</code>.
     * 
     * @since 4.0.9
     */
    public TextChildAnchor getChildAnchor() {
        return GtkTextIter.getChildAnchor(this);
    }

    /**
     * Check whether a specific {@link TextTag} is started at exactly this
     * point identified by the TextIter.
     * 
     * @since 4.0.9
     */
    public boolean beginsTag(TextTag tag) {
        return GtkTextIter.beginsTag(this, tag);
    }

    /**
     * Check whether a specific {@link TextTag} is ended at exactly this point
     * identified by the TextIter.
     * 
     * @since 4.0.9
     */
    public boolean endsTag(TextTag tag) {
        return GtkTextIter.endsTag(this, tag);
    }

    /**
     * Check whether a specific {@link TextTag} is either started or ended at
     * exactly this point identified by the TextIter.
     * 
     * @since 4.0.9
     */
    public boolean togglesTag(TextTag tag) {
        return GtkTextIter.togglesTag(this, tag);
    }

    /**
     * Checks whether a specific {@link TextTag} is applied to the text at the
     * current position. Or in other words if the current position is between
     * start and end of the {@link TextTag}.
     * 
     * @since 4.0.9
     */
    public boolean hasTag(TextTag tag) {
        return GtkTextIter.hasTag(this, tag);
    }

    /**
     * Returns all TextTags that apply to the current position. If there are
     * not any present here then a zero length array will be returned.
     * 
     * @since 4.0.9
     */
    public TextTag[] getTags() {
        return GtkTextIter.getTags(this);
    }

    /**
     * Check if text at the current position is editable. This is the case if
     * the default editability is <code>true</code> or the TextIter is within
     * or at the start of an editable block. If the TextIter is at the end of
     * an editable block (which is also the star of a non-editable area), this
     * method returns <code>false</code>.
     * 
     * @since 4.0.9
     */
    public boolean isEditable(boolean defaultEditability) {
        return GtkTextIter.editable(this, defaultEditability);
    }

    /**
     * Check if text inserted at the current position would be editable. This
     * is the case if the TextIter is within the range of an editable block
     * (including the start AND end position) or the default editability is
     * <code>true</code>.
     * 
     * @since 4.0.9
     */
    public boolean canInsert(boolean defaultEditability) {
        return GtkTextIter.canInsert(this, defaultEditability);
    }

    /**
     * Returns if a word is beginning at the current position, which is the
     * case when the TextIter points to the first letter of the word.
     * 
     * @since 4.0.9
     */
    public boolean startsWord() {
        return GtkTextIter.startsWord(this);
    }

    /**
     * Returns if the current position marks the end of a word. Usually this
     * means that you are at the first whitespace or sentence ending sign
     * behind a word.
     * 
     * @since 4.0.9
     */
    public boolean endsWord() {
        return GtkTextIter.endsWord(this);
    }

    /**
     * Check if the current position is inside a word, including the first or
     * last letter.
     * 
     * @since 4.0.9
     */
    public boolean insideWord() {
        return GtkTextIter.insideWord(this);
    }

    /**
     * Check if the current position is at the start of a line.
     * 
     * @since 4.0.9
     */
    public boolean startsLine() {
        return GtkTextIter.startsLine(this);
    }

    /**
     * Check if the current position marks the end of a line.
     * 
     * @since 4.0.9
     */
    public boolean endsLine() {
        return GtkTextIter.endsLine(this);
    }

    /**
     * Returns <code>true</code> if the TextIter points to the first character
     * of a sentence.
     * 
     * @since 4.0.9
     */
    public boolean startsSentence() {
        return GtkTextIter.startsSentence(this);
    }

    /**
     * Returns <code>true</code> if the TextIter points to the last sign that
     * does not belong to the sentence - usually the first whitespace, since
     * e.g. dots belong to a sentence.
     * 
     * @since 4.0.9
     */
    public boolean endsSentence() {
        return GtkTextIter.endsSentence(this);
    }

    /**
     * Checks whether or not the current position is inside a sentence.
     * 
     * @since 4.0.9
     */
    public boolean insideSentence() {
        return GtkTextIter.insideSentence(this);
    }

    /**
     * Returns if the TextIter is identical with the GUIs cursor position
     * inside the TextView.
     * 
     * @since 4.0.9
     */
    public boolean isCursorPosition() {
        return GtkTextIter.isCursorPosition(this);
    }

    /**
     * Counts the characters in the current text line, including the newline
     * if present. Non-printable objects like images count as one character.
     * 
     * @since 4.0.9
     */
    public int getCharsInLine() {
        return GtkTextIter.getCharsInLine(this);
    }

    /**
     * Checks if the TextIter points to the end of the buffer. This is the
     * case if this TextIter has the same offset as the TextIter returned by
     * {@link TextBuffer#getIterEnd()}
     * 
     * @since 4.0.9
     */
    public boolean isEnd() {
        return GtkTextIter.isEnd(this);
    }

    /**
     * Checks if the TextIter points to the start of the buffer. This is the
     * case if this TextIter has the offset 0.
     * 
     * @since 4.0.9
     */
    public boolean isStart() {
        return GtkTextIter.isStart(this);
    }

    /**
     * Reduce the character offset by 1. If already at the first character,
     * remain there.
     * 
     * @return <code>true</code>, if the offset was decreased or
     *         <code>false</code> if the TextIter already pointed to the start
     *         of the TextBuffer.
     * 
     * @since 4.0.9
     */
    public boolean backwardChar() {
        return GtkTextIter.backwardChar(this);
    }

    /**
     * Increase the character offset by 1. If already at the last character,
     * remain there.
     * 
     * <p>
     * The method returns <code>true</code>, if the offset was increased or
     * <code>false</code> if the TextIter already pointed to the end of the
     * TextBuffer <i>or one character before the end</i>.
     * 
     * <p>
     * If iterating character by character over a TextBuffer, you probably
     * want to use {@link #isEnd() isEnd()} and if not at the end call this
     * (and ignore its return value).
     * 
     * @since 4.0.9
     */
    public boolean forwardChar() {
        return GtkTextIter.forwardChar(this);
    }

    /**
     * Reduce the character offset by <code>count</code>. If this would point
     * to a negative offset, point to the start of the TextBuffer.
     * 
     * @return <code>true</code> if the position changed because of this call.
     * 
     * @since 4.0.9
     */
    public boolean backwardChars(int count) {
        return GtkTextIter.backwardChars(this, count);
    }

    /**
     * Increase the character offset by <code>count</code>. If this would
     * point to a position behind the buffers end, point to the end of the
     * TextBuffer.
     * 
     * @return <code>true</code> if the position changed because of this call.
     * 
     * @since 4.0.9
     */
    public boolean forwardChars(int count) {
        return GtkTextIter.forwardChars(this, count);
    }

    /**
     * Bump this TextIter backward multiple lines and to the start of the next
     * line. As a special case, if you're already pointing at the first line
     * of the TextBuffer, then you will be moved to the start of the line.
     * 
     * @since 4.0.9
     */
    public boolean backwardLines(int count) {
        return GtkTextIter.backwardLines(this, count);
    }

    /**
     * Bump this TextIter forward multiple lines and to the start of the next
     * line. As a special case, if you're already pointing at the last line of
     * the TextBuffer, then you will be moved to the end of the line.
     * 
     * @since 4.0.9
     */
    public boolean forwardLines(int count) {
        return GtkTextIter.forwardLines(this, count);
    }

    /**
     * Movie forward one <i>display</i> line within a paragraph as displayed
     * in a TextView. A single line in a TextBuffer will be displayed as
     * multiple lines (known to mere mortals as a "paragraph") in a TextView
     * if word wrapping is enabled and something is acting to constrain the
     * width of the TextView to less than the width of the lines.
     * 
     * <p>
     * A given TextBuffer can be displayed by more than one TextView, so where
     * the wrapping occurs is dependent on the width of that Widget, which is
     * why you need to pass the view you are working in to this method.
     * 
     * <p>
     * <i>In the native library this is a method on TextView; however, we have
     * exposed it here so that it is in the same completion space as all the
     * other</i> <code>forward...()</code> <i>methods available on
     * TextIter.</i>
     * 
     * @return <code>true</code> if the location the TextIter points at was
     *         changed; that is, if <code>pointer</code> is not yet at the end
     *         of the underlying TextBuffer.
     * @since 4.0.9
     */
    public boolean forwardDisplayLine(TextView view) {
        return GtkTextView.forwardDisplayLine(view, this);
    }

    /**
     * Move backward one <i>display</i> line within a paragraph as displayed
     * in this TextView. This is the complement of
     * {@link #forwardDisplayLine(TextView) forwardDisplayLine()}; see there
     * for details.
     * 
     * @since 4.0.9
     */
    public boolean backwardDisplayLine(TextView view) {
        return GtkTextView.backwardDisplayLine(view, this);
    }

    /**
     * Does the position represented by this TextIter start a display line
     * (within a paragraph) in the given TextView?
     * 
     * @since 4.0.9
     */
    public boolean startsDisplayLine(TextView view) {
        return GtkTextView.startsDisplayLine(view, this);
    }

}
