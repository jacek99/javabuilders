/*
 * TextBuffer.java
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

import java.util.Collection;

import org.gnome.gdk.Pixbuf;
import org.gnome.glib.Object;

import static org.gnome.gtk.TextTagTable.getDefaultTable;

/**
 * A TextBuffer is a powerful mechanism for storing and manipulating text. It
 * exists primarily to be the model that backs the view of one or more
 * paragraphs as presented by the {@link TextView} Widget. Together, these
 * make for a powerful text display and text editing capability, but it does
 * come at the cost of considerable come complexity.
 * 
 * <h2>Usage</h2>
 * 
 * Creating a TextBuffer is straight forward, as is placing text in it:
 * 
 * <pre>
 * TextBuffer buffer;
 * 
 * buffer = new TextBuffer();
 * buffer.setText(&quot;Hello world!&quot;);
 * </pre>
 * 
 * Of course, you rarely just want to write an entire body of text in one go.
 * To insert text in a more piecemeal fashion we use one of the
 * <code>insert()</code> family of methods, but before we can do so, we need
 * to indicate <i>where</i> we want to insert that text. This is the role of
 * {@link TextIter} class.
 * 
 * <pre>
 * TextIter pointer;
 * 
 * pointer = buffer.getIterStart();
 * buffer.insert(pointer, &quot;This is the beginning&quot;);
 * </pre>
 * 
 * Note that TextIters are not persistent; as soon as any change is made to
 * the TextBuffer all TextIters are invalidated and you'll need to acquire new
 * ones. If you need a stable reference to a particular position in the text,
 * create a TextMark with {@link #createMark(TextIter, boolean) createMark()}.
 * 
 * <p>
 * As you might expect, a TextBuffer has a notion of a cursor which indicates
 * the (user's) current insertion point. You can get the TextMark representing
 * the cursor, and can use it to insert text or otherwise manipulate a
 * position by converting it to a valid TextIter:
 * 
 * <pre>
 * TextMark cursor;
 * 
 * cursor = buffer.getInsert();
 * ...
 * pointer = cursor.getIter();
 * buffer.insert(pointer, &quot;This text was inserted at the cursor&quot;);
 * </pre>
 * 
 * The <code>insertAtCursor()</code> method is a shortcut for this.
 * 
 * <p>
 * There are an incredibly wide range of methods available on the TextIter
 * class for manipulating the position into the TextBuffer that it represents
 * and for finding out more information about the nature of the text at that
 * point. TextIter's {@link TextIter#forwardLine() forwardLine()} is one
 * example; you could use it to count the non-empty lines in a TextBuffer:
 * 
 * <pre>
 * int paragraphs;
 * ...
 * 
 * pointer = buffer.getIterStart();
 * paragraphs = 0;
 * 
 * while (pointer.forwardLine()) {
 *     if (pointer.startsWord()) {
 *         paragraphs++;
 *     }
 * }
 * </pre>
 * 
 * Although trivial, this raises a pitfall you need to be aware of: lines of
 * text in a TextBuffer are <i>not</i> the same as wrapped visible lines in a
 * TextView. Lines in a TextBuffer are a sequences of characters separated by
 * newlines (you don't need a <code>'\n'</code> at the end of the TextBuffer;
 * the end is an implicit line termination). When presented in a TextView with
 * word wrapping enabled, however, each of these lines may take up more than
 * one line on the screen. The term paragraph is used there; see
 * {@link TextIter#forwardDisplayLine(TextView) forwardDisplayLine()} to move
 * a TextIter to the next displayed line within a paragraph as shown on screen
 * in a TextView.
 * 
 * <p>
 * Finally, formatting and other properties can be set on ranges of text. See
 * {@link TextTag}. While these are mostly about visual presentation, they are
 * nevertheless set by applying TextTags to text in the TextBuffer via the
 * {@link #applyTag(TextTag, TextIter, TextIter) applyTag()} and
 * {@link #insert(TextIter, String, TextTag) insert()} methods here.
 * 
 * <p>
 * <i>These APIs can be somewhat frustrating to use as the methods you need
 * (relative to the order you need them in) tend to be scattered around
 * between TextView, TextBuffer, and TextIter. It was tempting to try and
 * normalize these to a common interface, but in the end we deferred to being
 * faithful to the placement of methods in the underlying library's classes.
 * We have done our best to cross reference our documentation to help you find
 * the "next" logical element when you need it, but if you find yourself
 * struggling to find something and think this documentation could benefit
 * from another such pointer, please feel free to suggest it.</i>
 * 
 * @author Andrew Cowie
 * @author Stefan Prelle
 * @since 4.0.9
 */
/*
 * Judging by the GTK documentation, almost everything in this class works
 * through the default signal handlers. Thus if we expose any signals we must
 * a) make sure that the documentation of the corresponding methods here
 * mentions that behaviour could change, b) warn people in the signal's
 * documentation that they could massively screw things up, and c) write test
 * coverage to guard against this sort of thing. Based on all that, I'm not
 * going to be in a rush to see any of those internal signals exposed in our
 * public API.
 */
public class TextBuffer extends Object
{
    /**
     * This constant is used to identify cells in the TextBuffer that are not
     * actually characters - in other words, Widgets and Pixbufs. The
     * placeholder is Unicode value <code>0xFFFC</code>, the "Object
     * Replacement Character" from the Special block. You might see it as
     * &#65532; in other contexts.
     * 
     * @since 4.0.9
     */
    public static final char OBJECT_REPLACEMENT_CHARACTER = 0xFFFC;

    private final boolean usingDefaultTable;

    protected TextBuffer(long pointer) {
        super(pointer);
        usingDefaultTable = false;
    }

    /**
     * Create a new TextBuffer.
     * 
     * <p>
     * This will use the default built-in TextTagTable (shared by all
     * TextBuffers constructed with this call) which in turn will be populated
     * with tags created using the no-arg {@link TextTag#TextTag() TextTag}
     * constructor. This is a convenience; if you need to segregate TextTags
     * used by different TextBuffers then just use the other TextBuffer
     * constructor.
     * 
     * @since 4.0.9
     */
    public TextBuffer() {
        super(GtkTextBuffer.createTextBuffer(getDefaultTable()));
        usingDefaultTable = true;
    }

    /**
     * Create a new TextBuffer. Uses (or reuses) the supplied TextTagTable;
     * you can add more TextTags to it later.
     * 
     * @since 4.0.9
     */
    public TextBuffer(TextTagTable tags) {
        super(GtkTextBuffer.createTextBuffer(tags));
        usingDefaultTable = false;
    }

    /**
     * Replace the entire current contents of the TextBuffer.
     * 
     * @since 4.0.9
     */
    public void setText(String text) {
        GtkTextBuffer.setText(this, text, -1);
    }

    /**
     * Returns the text in the range <code>start</code> .. <code>end</code>.
     * Excludes undisplayed text (text marked with tags that set the
     * <var>invisibility</var> attribute) if <code>includeHidden</code> is
     * <code>false</code>. Does not include characters representing embedded
     * images, so indexes into the returned string do not correspond to
     * indexes into the TextBuffer.
     * 
     * @since 4.0.9
     */
    public String getText(TextIter start, TextIter end, boolean includeHidden) {
        return GtkTextBuffer.getText(this, start, end, includeHidden);
    }

    /**
     * Returns the text in the TextBuffer in its entirety. This is merely a
     * convenience function that calls
     * {@link #getText(TextIter, TextIter, boolean)} from buffer start to end
     * with <code>includeHidden</code> being <code>true</code>.
     * 
     * @since 4.0.9
     */
    public String getText() {
        return getText(getIterStart(), getIterEnd(), true);
    }

    /**
     * Marks the content as changed. This is primarily used to <i>unset</i>
     * this property, making it <code>false</code>. See {@link #getModified()
     * getModified()} for details.
     * 
     * @since 4.0.9
     */
    public void setModified(boolean modified) {
        GtkTextBuffer.setModified(this, modified);
    }

    /**
     * Find out if the TextBuffer's content has changed. This is actually
     * defined as "has the TextBuffer changed since the last call to
     * {@link #setModified(boolean) setModified(false)}"; operations which
     * change the TextBuffer will toggle this property, and you can of course
     * manually set it with {@link #setModified(boolean) setModified(true)}.
     * 
     * <p>
     * This can be used to record whether a file being edited has been
     * modified and is not yet saved to disk (although most applications would
     * probably track that state more robustly, this can at least feed
     * information into that process).
     * 
     * @since 4.0.9
     */
    public boolean getModified() {
        return GtkTextBuffer.getModified(this);
    }

    /**
     * Returns a pointer to the beginning of the TextBuffer.
     * 
     * @since 4.0.9
     */
    public TextIter getIterStart() {
        final TextIter iter;

        iter = new TextIter(this);

        GtkTextBuffer.getStartIter(this, iter);

        return iter;
    }

    /**
     * Returns a pointer to the end of the TextBuffer.
     * 
     * @since 4.0.9
     */
    /*
     * The naming of this method is inverted so as to correspond with the
     * naming pattern established in TreeModel.
     */
    public TextIter getIterEnd() {
        final TextIter iter;

        iter = new TextIter(this);

        GtkTextBuffer.getEndIter(this, iter);

        return iter;
    }

    /**
     * Create a new TextMark at the position of the supplied TextIter.
     * 
     * <p>
     * The <code>gravity</code> parameter is interesting. It specifies whether
     * you want the TextMark to have left-gravity. If {@link TextMark#LEFT
     * LEFT} (<code>true</code>), then the TextMark will remain pointing to
     * the same location if text is inserted at this point. If
     * {@link TextMark#RIGHT RIGHT} (<code>false</code>), then as text is
     * inserted at this point the TextMark will move to the right. The
     * standard behaviour of the blinking cursor we all stare at all day long
     * following us as we type is an example of right-gravity.
     */
    public TextMark createMark(TextIter where, boolean gravity) {
        return GtkTextBuffer.createMark(this, null, where, gravity);
    }

    void deleteMark(TextMark mark) {
        GtkTextBuffer.deleteMark(this, mark);
    }

    /**
     * Insert a text at a given position. All {@link TextIter} behind the
     * position move accordingly, while marks keep their position.
     * 
     * @since 4.0.9
     */
    public void insert(TextIter position, String text) {
        GtkTextBuffer.insert(this, position, text, -1);
    }

    /**
     * Insert text as for {@link #insert(TextIter, String) insert()} but
     * simultaneously apply the formatting described by <code>tag</code>. You
     * can specify <code>null</code> TextTag if you actually want to skip
     * applying formatting, but in that case you'd probably rather just use
     * {@link #insert(TextIter, String) insert()}.
     * 
     * @since 4.0.9
     */
    public void insert(TextIter position, String text, TextTag tag) {
        checkTag(tag);
        GtkTextBuffer.insertWithTags(this, position, text, -1, tag);
    }

    /**
     * Insert text at the given position, applying all of the tags specified.
     * 
     * @since 4.0.10
     */
    /*
     * This essentially duplicates the logic in GTK's
     * gtk_text_buffer_insert_with_tags().
     */
    public void insert(TextIter position, String text, TextTag[] tags) {
        TextIter start;
        int original;

        original = position.getOffset();

        GtkTextBuffer.insert(this, position, text, -1);

        if (tags == null) {
            return;
        }

        start = position.copy();
        start.setOffset(original);

        for (TextTag tag : tags) {
            if (tag == null) {
                continue;
            }
            checkTag(tag);
            GtkTextBuffer.applyTag(this, tag, start, position);
        }
    }

    /**
     * Insert text at the given position, applying all of the tags specified.
     * 
     * <p>
     * <i> Having an overload taking a generic is somewhat unusual in
     * java-gnome, but people maintain a (fairly rapidly changing) List or Set
     * with the TextTags they are currently inserting so frequently that we
     * wanted to support this use case efficiently.</i>
     * 
     * @since 4.0.10
     */
    /*
     * Not to mention that Collection to array conversion is one of the uglier
     * idioms in Java. Yes, this is a copy of the code in other insert()
     * method above; we want to save gratuitous unnecessary array creation.
     */
    public void insert(TextIter position, String text, Collection<TextTag> tags) {
        final TextIter start;
        final int original;

        original = position.getOffset();

        GtkTextBuffer.insert(this, position, text, -1);

        if (tags == null) {
            return;
        }

        start = position.copy();
        start.setOffset(original);

        for (TextTag tag : tags) {
            if (tag == null) {
                continue;
            }
            checkTag(tag);
            GtkTextBuffer.applyTag(this, tag, start, position);
        }
    }

    /**
     * Insert the text at the current cursor position.
     * 
     * @since 4.0.9
     */
    public void insertAtCursor(String text) {
        GtkTextBuffer.insertAtCursor(this, text, -1);
    }

    /**
     * Like {@link #insert(TextIter, String) insert()}, but the insertion will
     * not occur if <code>pos</code> points to a non-editable location in the
     * buffer - meaning that it is enclosed in TextTags that mark the area
     * non-editable.<br/>
     * 
     * @param pos
     *            Position to insert at.
     * @param text
     *            Text to insert.
     * @param defaultEditability
     *            How shall the area be handled, if there are no tags
     *            affecting the <var>editable</var> property at the given
     *            location. You probably want to use <code>true</code> unless
     *            you used TextView's {@link TextView#setEditable(boolean)
     *            setEditable()} to change the default setting in the display
     *            Widget you're using.
     * @since 4.0.9
     */
    public void insertInteractive(TextIter pos, String text, boolean defaultEditability) {
        GtkTextBuffer.insertInteractive(this, pos, text, -1, defaultEditability);
    }

    /**
     * Inserts an image at the cursor position.
     * 
     * @since 4.0.9
     */
    public void insert(TextIter position, Pixbuf image) {
        GtkTextBuffer.insertPixbuf(this, position, image);
    }

    /**
     * Insert a Widget into the TextBuffer.
     * 
     * <p>
     * Since multiple TextViews can present information from a given
     * TextBuffer, you need to specify <code>which</code> TextView you want
     * the image to appear in.
     * 
     * <p>
     * Don't forget to <code>show()</code> the Widget.
     * 
     * <p>
     * <i>Widgets, of course, are neither text nor image data; they need to
     * appear in a Container hierarchy. Thus adding a Widget is actually a
     * function of TextView. All the other methods that conceptually insert
     * things into a TextBuffer are here, however, so we include this as a
     * convenience. This is just a wrapper around TextView's</i>
     * {@link TextView#add(Widget, TextIter) add()}.
     * 
     * @since 4.0.9
     */
    /*
     * FUTURE Can anyone think of a way to get rid of the need to specify the
     * TextView?
     */
    public void insert(TextIter position, Widget child, TextView which) {
        which.add(child, position);
    }

    /**
     * Returns the current cursor position. Is also used at the start position
     * of a selected text.
     * 
     * <p>
     * You can call {@link #getIter(TextMark) getIter()} to convert the
     * <var>insert</var> TextMark to an TextIter.
     * 
     * @since 4.0.9
     */
    public TextMark getInsert() {
        return GtkTextBuffer.getInsert(this);
    }

    /**
     * Returns the end of the current selection. If you need the beginning of
     * the selection use {@link #getInsert() getInsert()}.
     * 
     * <p>
     * Under ordinary circumstances you could think the
     * <var>selection-bound</var> TextMark as being the beginning of a
     * selection, and the <var>insert</var> mark as the end, but if the user
     * (or you, programmatically) has selected "backwards" then this TextMark
     * will be further ahead in the TextBuffer than the insertion one.
     * 
     * <p>
     * You can call {@link #getIter(TextMark) getIter()} to convert the
     * TextMark to an TextIter.
     * 
     * @since 4.0.9
     */
    public TextMark getSelectionBound() {
        return GtkTextBuffer.getSelectionBound(this);
    }

    /**
     * Returns whether or not the TextBuffer has a selection
     * 
     * @since 4.0.9
     */
    public boolean getHasSelection() {
        return GtkTextBuffer.getHasSelection(this);
    }

    /**
     * Converts a {@link TextMark TextMark} into a valid {@link TextIter
     * TextIter} that you can use to point into the TextBuffer in its current
     * state.
     * 
     * @since 4.0.9
     */
    /*
     * This method is more compactly named so as to correspond with the naming
     * pattern already established in TreeModel.
     */
    public TextIter getIter(TextMark mark) {
        final TextIter iter;

        iter = new TextIter(this);

        GtkTextBuffer.getIterAtMark(this, iter, mark);

        return iter;
    }

    /**
     * Get a TextIter pointing at the position <code>offset</code> characters
     * into the TextBuffer.
     * 
     * @since 4.0.9
     */
    public TextIter getIter(int offset) {
        final TextIter iter;

        iter = new TextIter(this);

        GtkTextBuffer.getIterAtOffset(this, iter, offset);

        return iter;
    }

    /*
     * Validate that the TextTag being submitted for application is legal for
     * use in this TextBuffer. FUTURE we could cache the table in the
     * constructors if this becomes a hot spot.
     */
    private void checkTag(TextTag tag) {
        if (tag == null) {
            return;
        }
        if (usingDefaultTable) {
            if (tag.table != getDefaultTable()) {
                throw new IllegalArgumentException("\n"
                        + "You cannot apply a TextTag created with the no-arg TextTag() constructor\n"
                        + "to a TextBuffer not likewise constructed with the no-arg TextBuffer()\n"
                        + "constructor.");
            }
        } else {
            if (tag.table != GtkTextBuffer.getTagTable(this)) {
                throw new IllegalArgumentException("\n"
                        + "You can only apply a TextTag to a TextBuffer created with the same\n"
                        + "TextTagTable.");
            }
        }
    }

    /**
     * Apply the selected tag on the area in the TextBuffer between the start
     * and end positions.
     * 
     * @since 4.0.9
     */
    public void applyTag(TextTag tag, TextIter start, TextIter end) {
        checkTag(tag);
        GtkTextBuffer.applyTag(this, tag, start, end);
    }

    /**
     * Apply an array of TextTags to the given range.
     * 
     * @since 4.0.10
     */
    /*
     * Convenience method. This doesn't need to be here, but it lends a
     * certain elegence when used alongside the insert() overload
     */
    public void applyTag(TextTag[] tags, TextIter start, TextIter end) {
        if (tags == null) {
            return;
        }
        for (TextTag tag : tags) {
            if (tag == null) {
                continue;
            }
            checkTag(tag);
            GtkTextBuffer.applyTag(this, tag, start, end);
        }
    }

    /**
     * Select a range of text. The <var>selection-bound</var> mark will be
     * placed at <code>start</code>, and the <var>insert</var> mark will be
     * placed at <code>end</code>.
     * 
     * <p>
     * Note that this should be used in preference to manually manipulating
     * the two marks with {@link #moveMark() moveMark()} calls; doing it that
     * way results in transient selections appearing which are different than
     * what you wish to see.
     * 
     * <p>
     * See {@link #placeCursor(TextIter) placeCursor()} if you just want to
     * move the two marks to the same location; and, assuming the TextBuffer
     * is showing in a TextView, this will move the cursor.
     * 
     * <p>
     * <i>The native GTK function has these arguments reversed but start and
     * end make more sense in consecutive order.</i>
     * 
     * @since 4.0.9
     */
    public void selectRange(TextIter start, TextIter end) {
        GtkTextBuffer.selectRange(this, end, start);
    }

    /**
     * Remove the effect of the supplied <code>tag</code> from across the
     * range between <code>start</code> and <code>end</code>. The order of the
     * two TextIters doesn't actually matter; they are just bounds.
     * 
     * @since 4.0.9
     */
    public void removeTag(TextTag tag, TextIter start, TextIter end) {
        GtkTextBuffer.removeTag(this, tag, start, end);
    }

    /**
     * Remove all TextTags that may be present in a range.
     * 
     * <p>
     * Beware that <b>all</b> tags between <code>start</code> and
     * <code>end</code> will be nuked, not just ones set by the piece of code
     * you happen to be working in. That should be obvious, but apparently
     * people often make mistakes with this.
     * 
     * @since 4.0.10
     */
    public void removeAllTags(TextIter start, TextIter end) {
        GtkTextBuffer.removeAllTags(this, start, end);
    }

    /**
     * Create a new TextChildAnchor at <code>location</code>. Once you have an
     * anchor for where you want the Widget, you use TextView's
     * {@link TextView#add(Widget, TextChildAnchor) add()} to load a Widget
     * into it.
     * 
     * @since 4.0.9
     */
    /*
     * This is already a convenience method according to the GTK docs. Uh,
     * "good". Thanks. Means we don't need another constructor in
     * TextChildAnchor.
     */
    TextChildAnchor createChildAnchor(TextIter location) {
        return GtkTextBuffer.createChildAnchor(this, location);
    }

    /**
     * Move the cursor (ie, the <var>selection-bound</var> and
     * <var>insert</var> marks) to the given location.
     * 
     * <p>
     * This is more than just a convenience function; like
     * {@link #selectRange(TextIter, TextIter) selectRange()} it carries out
     * the move without intermediate state of part of the text being selected
     * while the individual TextMarks are being moved.
     * 
     * <p>
     * See also TextView's {@link TextView#placeCursorOnscreen()
     * placeCursorOnscreen()} if you just want to force the cursor into the
     * currently showing section of the text.
     * 
     * @since 4.0.9
     */
    public void placeCursor(TextIter location) {
        GtkTextBuffer.placeCursor(this, location);
    }

    /**
     * Returns the number of characters in this buffer.
     * 
     * @since 4.0.9
     */
    public int getCharCount() {
        return GtkTextBuffer.getCharCount(this);
    }

    /**
     * Returns the number of text lines in this buffer.
     * 
     * @since 4.0.9
     */
    public int getLineCount() {
        return GtkTextBuffer.getLineCount(this);
    }

    /**
     * The signal emitted when the contents of the TextBuffer have changed.
     * 
     * @author Andrew Cowie
     * @since 4.0.9
     */
    public interface Changed extends GtkTextBuffer.ChangedSignal
    {
        public void onChanged(TextBuffer source);
    }

    /**
     * Hook up a handler for <code>TextBuffer.Changed</code> signals.
     * 
     * @since 4.0.9
     */
    public void connect(TextBuffer.Changed handler) {
        GtkTextBuffer.connect(this, handler, false);
    }

    /**
     * Signal emitted when text is inserted into the TextBuffer.
     * 
     * <p>
     * You must leave the TextIter <code>pointer</code> in a valid state; that
     * is, if you do something in your signal handler that changes the
     * TextBuffer, you must revalidate <code>pos</code> before returning.
     * 
     * <p>
     * <i>The default handler for this signal is where the mechanism to
     * actually insert text into the TextBuffer lives.</i>
     * 
     * @author Andrew Cowie
     * @since 4.0.9
     */
    /*
     * FIXME How do you do that?
     */
    public interface InsertText
    {
        public void onInsertText(TextBuffer source, TextIter pointer, String text);
    }

    /**
     * Hook up a handler for <code>TextBuffer.InsertText</code> signals. This
     * will be invoked before the default handler is run, that is, before the
     * new text is actually inserted into the TextBuffer.
     * 
     * @since 4.0.9
     */
    public void connect(TextBuffer.InsertText handler) {
        GtkTextBuffer.connect(this, new InsertTextHandler(handler), false);
    }

    /**
     * Hook up a handler for <code>TextBuffer.InsertText</code> signals that
     * will be called <i>after</i> the default handler.
     * 
     * @since 4.0.9
     */
    public void connectAfter(TextBuffer.InsertText handler) {
        GtkTextBuffer.connect(this, new InsertTextHandler(handler), true);
    }

    /*
     * Trim off the length parameter which is unnecessary in Java.
     */
    private static class InsertTextHandler implements GtkTextBuffer.InsertTextSignal
    {
        private final InsertText handler;

        private InsertTextHandler(InsertText handler) {
            this.handler = handler;
        }

        public void onInsertText(TextBuffer source, TextIter pos, String text, int length) {
            handler.onInsertText(source, pos, text);
        }
    }

    /**
     * Signal emitted when one or more characters are deleted.
     * 
     * <p>
     * You can get a String representing the the removed characters by
     * calling:
     * 
     * <pre>
     * deleted = buffer.getText(start, end, false);
     * </pre>
     * 
     * <p>
     * Note that in the case of a user action which attempts to delete text in
     * a TextView that is not <var>editable</var> or within a range of
     * characters affected by TextTags with the <var>editable</var> property
     * set to <code>false</code> then the action will be inhibited and this
     * signal will not be raised.
     * 
     * @author Andrew Cowie
     * @since 4.0.9
     */
    /*
     * TODO Can anyone explain how to stop a deletion from occurring in
     * response to the value of the text between start and end? The default
     * handler will nuke the text.
     */
    public interface DeleteRange extends GtkTextBuffer.DeleteRangeSignal
    {
        public void onDeleteRange(TextBuffer source, TextIter start, TextIter end);
    }

    /**
     * Hook up a handler for <code>TextBuffer.DeleteRange</code> signals on
     * this TextBuffer.
     * 
     * @since 4.0.9
     */
    public void connect(TextBuffer.DeleteRange handler) {
        GtkTextBuffer.connect(this, handler, false);
    }

    /**
     * Delete text between <code>start</code> and <code>end</code>. (The order
     * of the two TextIters doesn't matter; this method will delete between
     * the two regardless).
     * 
     * <p>
     * The two TextIters passed to <code>delete()</code> will be reset so as
     * to point to the location where the text was removed. This can be a
     * useful feature; since it mutates the TextBuffer, calling this method
     * will invalidate all other TextIters.
     * 
     * @since 4.0.9
     */
    public void delete(TextIter start, TextIter end) {
        GtkTextBuffer.delete(this, start, end);
    }

    /**
     * Manually move a TextMark to a new location.
     * 
     * <p>
     * Note that if you're trying to move the "cursor", then you are much
     * better off calling {@link #placeCursor(TextIter) placeCursor()} as that
     * simultaneously moves both the <var>selection-bound</var> TextMark as
     * well as the <var>insert</var> one.
     * 
     * @since 4.0.10
     */
    public void moveMark(TextMark mark, TextIter where) {
        GtkTextBuffer.moveMark(this, mark, where);
    }

    /**
     * Signal emitted when a TextMark is set (or moved) in this TextBuffer.
     * 
     * <p>
     * This can be used as a way to react to the cursor moving. The cursor is,
     * of course, represented by the <var>insert</var> TextMark, and so,
     * doing:
     * 
     * <pre>
     * insert = buffer.getInsert();
     * 
     * buffer.connect(new TextBuffer.MarkSet() {
     *     public void onMarkSet(TextBuffer source, org.gnome.gtk.TextIter location, TextMark mark) {
     *         if (mark == insert) {
     *             // react!
     *         }
     *     }
     * });
     * </pre>
     * 
     * will allow you to react to the cursor moving.
     * 
     * <p>
     * Somewhat counter-intuitively, however, inserting text does <i>not</i>
     * "move" a TextMark; the <var>insert</var> TextMark will flow right
     * according to its gravity as text is added. Using the arrow keys or
     * mouse to move the cursor will, on the other hand, result in this signal
     * being emitted.
     * 
     * <p>
     * While an interesting example, doing this to track the cursor changing
     * isn't actually a good idea. You get a <code>TextBuffer.MarkSet</code>
     * callback for <i>every</i> TextMark being set (including ones used
     * internally by GTK!) which means lots of activity that has nothing to do
     * with you. Use {@link TextBuffer.NotifyCursorPosition} instead.
     * 
     * <p>
     * <i>Using signal is very inefficient; why this is a signal on TextBuffer
     * and not a signal coming (only) from individual TextMarks is
     * mystery.</i>
     * 
     * @author Andrew Cowie
     * @since 4.0.10
     */
    public interface MarkSet extends GtkTextBuffer.MarkSetSignal
    {
        public void onMarkSet(TextBuffer source, TextIter location, TextMark mark);
    }

    /**
     * Hook up a handler for <code>TextBuffer.MarkSet</code> signals on this
     * TextBuffer.
     * 
     * @since 4.0.10
     */
    public void connect(TextBuffer.MarkSet handler) {
        GtkTextBuffer.connect(this, handler, false);
    }

    /**
     * The signal emitted when a TextTag is added to a range of text.
     * 
     * <p>
     * If you're using this then you'll probably also need the complement of
     * this signal, which is <code>TextBuffer.RemoveTag</code>.
     * 
     * <p>
     * <i>The code to actually carry out the application of the tag is in the
     * default handler, which will run after you return from yours.</i>
     * 
     * @author Andrew Cowie
     * @since 4.0.10
     */
    /*
     * TODO Which raises some questions: do we need to expose this as a
     * connectAfter()? Also, if a tag is already present does this signal get
     * raised anyway? Finally, do we need emitApplyTag()?
     */
    public interface ApplyTag extends GtkTextBuffer.ApplyTagSignal
    {
        void onApplyTag(TextBuffer source, TextTag tag, TextIter start, TextIter end);
    }

    /**
     * Hook up a handler for <code>TextBuffer.ApplyTag</code> signals on this
     * TextBuffer.
     * 
     * @since 4.0.10
     */
    public void connect(TextBuffer.ApplyTag handler) {
        GtkTextBuffer.connect(this, handler, false);
    }

    /**
     * The signal emitted when a TextTag is removed from a range of text.
     * 
     * @author Andrew Cowie
     * @since 4.0.10
     */
    public interface RemoveTag extends GtkTextBuffer.RemoveTagSignal
    {
        void onRemoveTag(TextBuffer source, TextTag tag, TextIter start, TextIter end);
    }

    /**
     * Hook up a handler for <code>TextBuffer.RemoveTag</code> signals on this
     * TextBuffer.
     * 
     * @since 4.0.10
     */
    public void connect(TextBuffer.RemoveTag handler) {
        GtkTextBuffer.connect(this, handler, false);
    }

    /**
     * Signal emitted when the <var>cursor-position</var> property changes.
     * This is a good way to find out that the cursor has moved.
     * 
     * 
     * <pre>
     * buffer.connect(new TextBuffer.NotifyCursorPosition()) {
     *     public void onNotifyCursorPosition(TextBuffer source) {
     *          final int offset;
     *          
     *          offset = buffer.getCursorPosition();
     *     }
     * });
     * </pre>
     * 
     * If you've already got the <var>insert</var> TextMark, then you could
     * instead do:
     * 
     * <pre>
     * buffer.connect(new TextBuffer.NotifyCursorPosition()) {
     *     public void onNotifyCursorPosition(TextBuffer source) {
     *          final TextIter pointer;
     *          final int offset;
     *          
     *          pointer = buffer.getIter(insertBound);
     *          offset = buffer.getOffset();
     *     }
     * });
     * </pre>
     * 
     * which amounts to the same thing.
     * 
     * <p>
     * Using the <code>TextBuffer.NotifyCursorPosition</code> signal is much
     * more efficient than hooking up to <code>TextBuffer.MarkSet</code>
     * signals.
     * 
     * @author Andrew Cowie
     * @since 4.0.10
     */
    public interface NotifyCursorPosition extends GtkTextBufferOverride.NotifyCursorPositionSignal
    {
        public void onNotifyCursorPosition(TextBuffer source);
    }

    /**
     * Hook up a handler to receive
     * <code>TextBuffer.NotifyCursorPosition</code> signals emitted when the
     * <var>cursor-position</var> of this TextBuffer changes.
     * 
     * @since 4.0.10
     */
    public void connect(TextBuffer.NotifyCursorPosition handler) {
        GtkTextBufferOverride.connect(this, handler, false);
    }

    /**
     * Get the current value of the <var>cursor-position</var> property.
     * 
     * <p>
     * This corresponds to the offset of the <var>insert</var> TextMark; if
     * you already have a TextIter for that you call it's
     * {@link TextIter#getOffset() getOffset()} instead.
     * 
     * @since 4.0.10
     */
    public int getCursorPosition() {
        return getPropertyInteger("cursor-position");
    }
}
