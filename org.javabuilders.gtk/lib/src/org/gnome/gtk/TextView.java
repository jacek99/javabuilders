/*
 * TextView.java
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

import org.gnome.gdk.Rectangle;
import org.gnome.pango.FontDescription;

/**
 * A multi-line text display Widget. <img class="snapshot" src="TextView.png">
 * 
 * GTK leverages the powerful text rendering capability provided by the Pango
 * library. This is used throughout the toolkit, but nowhere more so that when
 * displaying multiple lines of text in a single Widget. TextView is the view
 * part of GTK's model-view-controller pattern text display Widget, with a
 * {@link TextBuffer} supplying the underlying data model.
 * 
 * <p>
 * TextView can be used for passive display of multiple lines of text by
 * disabling the <var>editable</var> property. Usually, however, a text canvas
 * is used for entering or editing text and the TextView/TextBuffer APIs
 * combine to provide a powerful editing capability.
 * 
 * <h2>Usage</h2>
 * 
 * Having instantiated a TextBuffer to store and programmatically manipulate
 * the text, you create a TextView as follows:
 * 
 * <pre>
 * TextBuffer buffer;
 * TextView view;
 * ScrolledWindow scroll;
 * ...
 * 
 * view = new TextView(buffer);
 * </pre>
 * 
 * Most people want the text to wrap. This is enabled by setting a WrapMode
 * but something must act to restrain the horizontal width of the TextView
 * Widget as it will size-request as much space as would be needed to render a
 * single line. Likewise, wrapping is usually combined with scrolling, and
 * this can be set up in a fairly straight forward fashion:
 * 
 * <pre>
 * view.setWrapMode(WrapMode.WORD);
 * 
 * scroll = new ScrolledWindow();
 * scroll.setPolicy(PolicyType.NEVER, PolicyType.ALWAYS);
 * scroll.add(view);
 * ...
 * 
 * window.setDefaultSize(300, 700);
 * </pre>
 * 
 * then packing the ScrolledWindow into a parent Container hierarchy. In
 * example shown here the call to <code>setDefaultSize()</code> on the
 * toplevel would constrain the overall Window size, forcing a narrow
 * size-allocation on the TextView.
 * 
 * <p>
 * As with TextBuffer, TextIters are the mechanism used to point to locations
 * within the displayed text. There are numerous methods here on TextView
 * which manipulate the displayed view (for example
 * {@link #scrollTo(TextIter) scrollTo()}) many of which take a TextIter as an
 * indicator of position. Don't be confused that the TextIters are somehow
 * different depending on their source; they <i>always</i> refer to a position
 * in a TextBuffer but are often translated to also identify a screen position
 * in the TextView. You will often find yourself getting a TextIter from the
 * TextBuffer (perhaps in response to a <code>TextBuffer.Changed</code> or
 * <code>TextBuffer.InsertText</code> emission) and then switching over to
 * here and calling TextView methods - and then going back to TextBuffer again
 * a moment later.
 * 
 * <h2>Appearance</h2>
 * 
 * <p>
 * {@link TextTag}s are what are use to cause ranges of text within a TextView
 * to appear with various formatting (bold, italics, colour, etc) over and
 * above being displayed as normal text. You apply such tags to the TextBuffer
 * either when <code>insert()</code>ing or with <code>applyTag()</code>. See
 * TextTag for details and examples.
 * 
 * <p>
 * Incidentally, if you need to change the font of the text being rendered in
 * this TextView by default use Widget's {@link #modifyFont(FontDescription)
 * modifyFont()}, for example:
 * 
 * <pre>
 * desc = new FontDescription(&quot;Monospace, 12&quot;);
 * view.modifyFont(desc);
 * </pre>
 * 
 * see FontDescription for all the gory details. As usual, we recommend that
 * you do <i>not</i> do this without good cause, instead leaving the
 * application font to be what the user has selected the system Appearance
 * Preferences font settings dialog provided by GNOME.
 * 
 * <a name="height"></a>
 * <h2>Line height calculations</h2>
 * 
 * <p>
 * Working out the heights for each line of text in the TextView and doing
 * related positioning can, at times, be computationally intensive. So GTK
 * does this in a background idle task.
 * 
 * <p>
 * Ordinarily you don't have to worry about this, but methods like
 * {@link #getLineY(TextIter) getLineY()} will not report correct information
 * until this has happened. If you are doing drawing based on the co-ordinates
 * of a given line in the <code>TEXT</code> Window of the TextView, it is easy
 * to be trapped by this: you hook up to the <code>TextBuffer.Changed</code>
 * thinking that you can use this as an indication of when the TextView has
 * changed, but unfortunately this turns out not to be the case.
 * <code>TextBuffer.Changed</code> is indeed emitted, but the information
 * returned by <code>getLineY()</code> will not have been updated until after
 * the current signal handlers finish and the high-priority idle task can run.
 * 
 * <p>
 * Studying the internal implementation of this logic in GTK, it turns out
 * that the bulk of the work to do validation of the line height calculations
 * and text placement happens in code paths triggered off of
 * <code>Adjustment.ValueChanged</code> (which is emitted, for example, when
 * scrolling occurs). Thus there does seem to be a way to trick the TreeView
 * into getting on with the revalidation early, and that is to emit
 * <code>Adjustment.ValueChanged</code> yourself. So, given the usual
 * TextView/TextBuffer fields and an Adjustment:
 * 
 * <pre>
 * ...
 * final TextView view;
 * final TextBuffer buffer;
 * final ScrolledWindow scroll;
 * final Adjustment vadj;
 * ...
 * </pre>
 * 
 * and assuming <code>view</code> has been packed into <code>scroll</code>,
 * etc, you can get the Adjustment and use <i>it</i> changing as the trigger
 * to do your redrawing logic:
 * 
 * <pre>
 * vadj = scroll.getVAdjustment();
 * vadj.connect(new Adjustment.ValueChanged() {
 *     public void onValueChanged(Adjustment source) {
 *         // now the line heights will be correct
 *         doSomethingWith(view.getLineY());
 *     }
 * });
 * </pre>
 * 
 * That works for when real scrolling happens, but if you need to precipitate
 * matters, use Adjustment's <code>emitValueChanged()</code> to fire the
 * <code>Adjustment.ValueChanged</code> signal:
 * 
 * <pre>
 * buffer.connect(new TextBuffer.Changed() {
 *     public void onChanged(TextBuffer source) {
 *         vadj.emitValueChanged();
 *     }
 * });
 * 
 * window.connect(new Window.ConfigureEvent() {
 *     public boolean onConfigureEvent(Widget source, EventConfigure event) {
 *         vadj.emitValueChanged();
 *         return false;
 *     }
 * });
 * </pre>
 * 
 * and so on.
 * 
 * <p>
 * <i>Obviously "internal to GTK" implies that we are second guessing the
 * implementation details. This workaround is not based on documented public
 * behaviour, and unfortunately is not guaranteed to be stable. So as we say
 * in Open Source, Your Mileage May Vary. Perhaps GTK will improve this aspect
 * of the library in the future.</i>
 * 
 * @author Stefan Prelle
 * @author Andrew Cowie
 * @since 4.0.9
 */
public class TextView extends Container
{
    protected TextView(long pointer) {
        super(pointer);
    }

    /**
     * Create an empty TextView without (yet) having an associated TextBuffer.
     * Use {@link #setBuffer(TextBuffer) setBuffer()} to indicate later which
     * TextBuffer to use.
     * 
     * @since 4.0.9
     */
    public TextView() {
        super(GtkTextView.createTextView());
    }

    /**
     * Create a TextView and display the contents of the TextBuffer.
     * 
     * @since 4.0.9
     */
    public TextView(TextBuffer buffer) {
        super(GtkTextView.createTextViewWithBuffer(buffer));
    }

    /**
     * Get the TextBuffer currently underlying this TextView.
     * 
     * @since 4.0.9
     */
    public TextBuffer getBuffer() {
        return GtkTextView.getBuffer(this);
    }

    /**
     * Set or replace the TextBuffer that is currently being displayed by this
     * TextView.
     * 
     * @since 4.0.9
     */
    public void setBuffer(TextBuffer buffer) {
        GtkTextView.setBuffer(this, buffer);
    }

    /**
     * Set the line wrapping for the view.
     * 
     * @since 4.0.9
     */
    public void setWrapMode(WrapMode mode) {
        GtkTextView.setWrapMode(this, mode);
    }

    /**
     * Get the line wrapping for the view.
     * 
     * @since 4.0.9
     */
    public WrapMode getWrapMode() {
        return GtkTextView.getWrapMode(this);
    }

    /**
     * Set whether the normal state of this TextView is to allow editing or
     * not. The default for the <var>editability</var> property is
     * <code>true</code>.
     * 
     * <p>
     * Regardless of the default setting here, you can override this for
     * specific regions of text with by applying TextTags with
     * {@link TextTag#setEditable(boolean) setEditable()} set.
     * 
     * @since 4.0.9
     */
    public void setEditable(boolean editable) {
        GtkTextView.setEditable(this, editable);
    }

    /**
     * Get whether the default editability of the TextView. Tags in the buffer
     * may override this setting for some ranges of text.
     * 
     * @since 4.0.9
     */
    public boolean getEditable() {
        return GtkTextView.getEditable(this);
    }

    /**
     * Allows you to activate or deactivate the visible cursor. Usually used
     * to hide the cursor when displaying text that is non-editable. The
     * default is <code>true</code>, indicating the cursor will be shown.
     * 
     * @since 4.0.9
     */
    public void setCursorVisible(boolean visible) {
        GtkTextView.setCursorVisible(this, visible);
    }

    /**
     * Returns whether the cursor is currently visible or not.
     * 
     * @since 4.0.9
     */
    public boolean getCursorVisible() {
        return GtkTextView.getCursorVisible(this);
    }

    /**
     * Load a Widget into the TextView at the given position.
     * 
     * <p>
     * A very impressive feature of TextViews is that you can embed Widgets
     * into them! You supply the <code>position</code> where you want the
     * Widget to be anchored, and the Widget will appear in the display amidst
     * the rest of your text. Don't forget to <code>show()</code> the Widget
     * you're adding or it won't appear.
     * 
     * <p>
     * There is an {@link TextBuffer#insert(TextIter, Widget, TextView)
     * insert()} method available on TextBuffer which wraps this; you may find
     * it more convenient.
     * 
     * <p>
     * <i>The underlying library is somewhat convoluted about this due to the
     * fact that more than one TextView can be displaying a given TextBuffer,
     * but a Widget can only appear in one parent Container. GTK uses an
     * intermediate called TextChildAnchor to bridge between TextView and
     * TextBuffer; we take care of handling that for you.</i>
     * 
     * @since 4.0.9
     */
    public void add(Widget child, TextIter position) {
        final TextBuffer buffer;
        final TextChildAnchor anchor;

        /*
         * TextChildAnchors are just an intermediate to bridge between
         * TextView and TextBuffer. There doesn't seem to be a reason to
         * expose them when we can just join them here. So, first create an
         * anchor:
         */

        buffer = getBuffer();
        anchor = buffer.createChildAnchor(position);

        /*
         * Now use the anchor to add the Widget:
         */

        GtkTextView.addChildAtAnchor(this, child, anchor);
    }

    /**
     * Set the size (width for LEFT and RIGHT and height for TOP and BOTTOM)
     * of the specified side panels. You only need this if using the optional
     * side panels, and obscure, advanced, and not wholly functional feature;
     * see {@link TextWindowType}. If you're reading this and wanting to set
     * the padding around the TextView, you probably want
     * {@link #setBorderWidth(int) setBorderWidth()}, a method inherited from
     * Container.
     * 
     * @since 4.0.9
     */
    public void setBorderWindowSize(TextWindowType which, int size) {
        GtkTextView.setBorderWindowSize(this, which, size);
    }

    /**
     * Place a child Widget into one of the optional side panels around a
     * TextView. This is an advanced feature; see {@link TextWindowType} for a
     * full discussion.
     * 
     * <p>
     * The Widget <code>child</code> will be placed at the coordinates
     * <code>x</code>,<code>y</code> in the [org.gnome.gdk] Window specified
     * by which. You can get that Window by calling TextView's variant of
     * {@link #getWindow(TextWindowType) getWindow()}.
     * 
     * <p>
     * This cannot be used unless <code>which</code> has been initialized to
     * have a non-zero size with
     * {@link #setBorderWindowSize(TextWindowType, int) setBorderWindowSize()}.
     * 
     * <p>
     * <b>WARNING</b><br>
     * <i style="color:red">This feature seems somewhat poorly implemented in
     * the underlying library. While we have fully exposed it, testing showed
     * it to be rather difficult to use reliably. Sorry we can't do better for
     * you.</i>
     * 
     * @since 4.0.9
     */
    public void add(Widget child, TextWindowType which, int x, int y) {
        final int width;

        width = GtkTextView.getBorderWindowSize(this, which);

        if (width < 1) {
            throw new IllegalStateException("Optional border panels must have non-zero size.");
        }

        GtkTextView.addChildInWindow(this, child, which, x, y);
    }

    /**
     * @deprecated Despite TextView inherheriting from Container, add(Widget)
     *             doesn't work. Use TextView's add(Widget,TextIter) instead.
     */
    /*
     * This is marked deprecated just to keep it out of the API documentation.
     */
    public void add(Widget child) {
        throw new UnsupportedOperationException("Use add(Widget,TextIter) instead");
    }

    /**
     * Change the co-ordinates of a child Widget in one of the optional side
     * panels. <code>x</code>,<code>y</code> are specified in <i>window
     * co-ordinates</i>.
     * 
     * @since 4.0.9
     */
    public void moveChild(Widget child, int x, int y) {
        GtkTextView.moveChild(this, child, x, y);
    }

    /**
     * Convert <code>X</code> from <var>buffer co-ordinates</var> to
     * <var>window co-ordinates</var>. See
     * {@link #convertBufferToWindowCoordsY(TextWindowType, int)
     * convertBufferToWindowCoordsY()} for a detailed discussion.
     * 
     * @since 4.0.9
     */
    /*
     * In this method and the corresponding Y co-ordinate version we just pass
     * 0 for the unused axis because we're ignoring the result for that axis
     * anyway. This seems sane based on inspection of the code paths in GTK's
     * gtk/gtktextview.c
     */
    public int convertBufferToWindowCoordsX(TextWindowType which, int X) {
        int[] x;

        x = new int[1];

        GtkTextView.bufferToWindowCoords(this, which, X, 0, x, null);

        return x[0];
    }

    /**
     * The canvas that is used to present the text in a TextView has an origin
     * at <code>0</code>,<code>0</code> that is at the top left corner. and
     * extends for as many pixels as would be necessary to present the entire
     * TextBuffer if it were shown on an arbitrarily large screen without
     * scrolling.
     * 
     * <p>
     * In most cases, the text shown will require an area larger than the
     * viewport provided by the primary area of the TextView. Even without
     * scrollbars (which can be added by putting the TextView into a
     * ScrolledWindow), the viewport showing the text will slide when the
     * cursor is moved down from the start position and into the body of text.
     * Thus you can be at a position in <var>buffer co-ordinates</var> that is
     * far "greater" than the size of the [org.gnome.gdk] Window that displays
     * it.
     * 
     * <p>
     * Numerous methods, notably {@link #getLineY(TextIter) getLineY()},
     * return a value in <var>buffer co-ordinates</var>. If you need to
     * determine what position this represents on screen, you need to convert
     * to <var>window co-ordinates</var> which are relative to the top left
     * corner of the [org.gnome.gdk] Window being used to present the text on
     * screen. This method will carry out that conversion for the vertical
     * axis. See {@link #convertBufferToWindowCoordsX(TextWindowType, int)
     * convertBufferToWindowCoordsX()} for the corresponding horizontal
     * conversion.
     * 
     * @since 4.0.9
     */
    public int convertBufferToWindowCoordsY(TextWindowType which, int Y) {
        int[] y;

        y = new int[1];

        GtkTextView.bufferToWindowCoords(this, which, 0, Y, null, y);

        return y[0];
    }

    /**
     * Convert a horizontal position from <var>window co-ordinates</var> (the
     * on screen position) to <var>buffer co-ordinates</var> (the pixel
     * distance into the canvas used to describe the entire text being
     * displayed). See
     * {@link #convertBufferToWindowCoordsY(TextWindowType, int)
     * convertBufferToWindowCoordsY()} for a detailed discussion.
     * 
     * @since 4.0.9
     */
    public int convertWindowToBufferCoordsX(TextWindowType which, int x) {
        int[] X;

        X = new int[1];

        GtkTextView.windowToBufferCoords(this, which, 0, x, null, X);

        return X[0];
    }

    /**
     * Convert a vertical position from <var>window co-ordinates</var> to
     * <var>buffer co-ordinates</var>. See
     * {@link #convertBufferToWindowCoordsY(TextWindowType, int)
     * convertBufferToWindowCoordsY()} for a detailed discussion.
     * 
     * @since 4.0.9
     */
    public int convertWindowToBufferCoordsY(TextWindowType which, int y) {
        int[] Y;

        Y = new int[1];

        GtkTextView.windowToBufferCoords(this, which, 0, y, null, Y);

        return Y[0];
    }

    /**
     * Get the Rectangle describing what portion of the text canvas the
     * viewport is currently showing. This is (only) relevant when scrollbars
     * are employed.
     * 
     * <p>
     * If you consider the text being displayed as a canvas of a fixed size,
     * but have turned on scrolling and only have a limited portion of that
     * canvas displayed due to the Widget being sized smaller than that
     * canvas, then the <code>x</code>,<code>y</code> co-ordinates returned in
     * the Rectangle represent the current <i>offset</i> into that canvas that
     * the viewport is showing.
     * 
     * <p>
     * If, for example, you only have vertical scrolling enabled,
     * 
     * <pre>
     * view.setWrapMode(WORD);
     * scroll = new ScrolledWindow();
     * scroll.setPolicy(NEVER, ALWAYS);
     * scroll.add(view);
     * </pre>
     * 
     * then you can expect <code>getVisibleRectangle()</code> to always return
     * Rectangles with an {@link Rectangle#getX() x} offset value of
     * <code>0</code> - the viewport is never scrolled horizontally into the
     * text canvas.
     * 
     * <p>
     * The <code>width</code> and <code>height</code> will, more or less,
     * correspond to the size of the area of text actually being displayed in
     * the TextView.
     * 
     * <p>
     * See {@link #getLocation(TextIter) getLocation()} if you need a
     * Rectangle enclosing a given TextIter.
     * 
     * @since 4.0.9
     */
    public Rectangle getVisibleRectangle() {
        final Rectangle visible;

        visible = new Rectangle(0, 0, 0, 0);

        GtkTextView.getVisibleRect(this, visible);

        return visible;
    }

    /**
     * Get a Rectangle enclosing the screen position of the given TreeIter.
     * This will be in <var>buffer co-ordinates</var>.
     * 
     * <p>
     * This is very useful in a <code>TextBuffer.NotifyCursorPosition</code>
     * if you need to figure out <i>where</i> the cursor is so as to handle
     * presentation of some external control accordingly.
     * 
     * @since 4.0.10
     */
    /*
     * We will not name this getIterLocation() because all the other methods
     * in the getIter... completion space are methods that return a TreeIter
     * based on some argument.
     */
    public Rectangle getLocation(TextIter pointer) {
        final Rectangle location;

        location = new Rectangle(0, 0, 0, 0);

        GtkTextView.getIterLocation(this, pointer, location);

        return location;
    }

    /**
     * Get the underlying resource corresponding with one of the sub elements
     * of this TextView. See {@link TextWindowType} for a detailed discussion.
     * 
     * @since 4.0.9
     */
    public org.gnome.gdk.Window getWindow(TextWindowType which) {
        return GtkTextView.getWindow(this, which);
    }

    /**
     * Get a TextIter corresponding to a given location in the canvas that is
     * displayed by the TextView. <code>X</code>,<code>Y</code> are in
     * <var>buffer co-ordinates</var>; if you have a position into the
     * [org.gnome.gdk] Window then use
     * {@link #convertWindowToBufferCoordsY(TextWindowType, int)
     * convertWindowToBufferCoordsY()} to convert.
     * 
     * @since 4.0.9
     */
    public TextIter getIterAtLocation(int X, int Y) {
        final TextIter result;

        result = new TextIter(getBuffer());

        GtkTextView.getIterAtLocation(this, result, X, Y);

        return result;
    }

    /**
     * Get the y co-ordinate of the line holding the supplied position. The
     * value is in <var>buffer co-ordinates</var>, and refers to the top of
     * the line. If you need to know how high the line is, call
     * {@link #getLineRange(TextIter) getLineRange()}.
     * 
     * <p>
     * <b>WARNING</b><br>
     * The co-ordinates of the start of each line height are cached are not
     * immediately updated when the underlying TextBuffer changes; see the
     * comment titled "<a href="#height">Line Height Calculations</a>" in the
     * documentation for this class for discussion of when you can safely use
     * this method.
     * 
     * @since 4.0.9
     */
    public int getLineY(TextIter position) {
        int[] y;

        y = new int[1];

        GtkTextView.getLineYrange(this, position, y, null);

        return y[0];
    }

    /**
     * This is the compliment of {@link #getLineY(TextIter) getLineY()},
     * giving you the corresponding line height that drops from the top
     * specified by that method.
     * 
     * <p>
     * <b>WARNING</b><br>
     * Line height values are cached by are not immediately refreshed when the
     * underlying TextBuffer changes; see the comment titled "<a
     * href="#height">Line Height Calculations</a>" in the documentation for
     * this class for discussion of when you can safely use this method.
     * 
     * @since 4.0.9
     */
    public int getLineRange(TextIter position) {
        int[] range;

        range = new int[1];

        GtkTextView.getLineYrange(this, position, null, range);

        return range[0];
    }

    /**
     * Move the cursor (ie, the <var>insert</var> TextMark in the current
     * source TextBuffer) so that is is showing somewhere in the section of
     * text currently displayed in the viewport.
     * 
     * @since 4.0.9
     */
    public void placeCursorOnscreen() {
        GtkTextView.placeCursorOnscreen(this);
    }

    /**
     * Scroll the viewport so that <code>pointer</code> is visible. This will
     * get the location specified onto the screen with as little scroll
     * movement as possible. If you need finer grained control, use one of the
     * other <code>scrollTo()</code> variants. variant.
     * 
     * @since 4.0.9
     */
    public void scrollTo(TextIter pointer) {
        scrollTo(pointer, 0.0, 0.0, 0.0);
    }

    /**
     * Scroll the viewport so that <code>pointer</code> is visible, attempting
     * to fine tune the result of the scrolling. See the
     * {@link #scrollTo(TextMark, double, double, double) scrollTo()} method
     * taking a TextMark and the same parameters for a detailed discussion of
     * their use.
     * 
     * @since 4.0.9
     */
    /*
     * WARNING! The real gtk_tree_view_scroll_to_iter() function is known to
     * be broken. See GTK bugs #102862, #311728 and perhaps many others. This
     * implementation therefore skips calling that function and follows the
     * suggestions of the bugs above. Obviously when GTK is fixed this
     * behaviour should be reverted to calling the real function.
     */
    public void scrollTo(TextIter pointer, double withinMargin, double xalign, double yalign) {
        TextBuffer buffer;
        TextMark mark;

        buffer = getBuffer();
        mark = buffer.createMark(pointer, true);

        scrollTo(mark, withinMargin, xalign, yalign);

        buffer.deleteMark(mark);
    }

    /**
     * Scroll the viewport so that <code>mark</code> is visible. This will
     * have the effect of doing the minimum necessary scrolling to get the
     * location specified by the TextMark onto the screen.
     * 
     * <p>
     * See also the full {@link #scrollTo(TextMark, double, double, double)
     * scrollTo()} which takes additional parameters which may allow you to
     * fine tune the result of the scrolling.
     * 
     * @since 4.0.9
     */
    public void scrollTo(TextMark mark) {
        GtkTextView.scrollToMark(this, mark, 0.0, false, 0.0, 0.0);
    }

    /**
     * Scroll the viewport so that <code>mark</code> is visible.
     * 
     * <p>
     * The GTK documentation states that the <i>the effective screen will be
     * reduced by</i> <code>withinMargin</code>. The acceptable range is
     * <code>0.0</code> to <code>0.5</code>. TODO It would be cool if someone
     * could figure out what that actually means; the allowed range is clearly
     * not a multiplier, so what is it?
     * 
     * <p>
     * The alignment parameters have the same meaning as elsewhere in GTK:
     * <code>0.0</code> for top|right, <code>1.0</code> for bottom|left.
     * 
     * <p>
     * If you don't need to mess with margins or alignment, then just use the
     * single arg {@link #scrollTo(TextMark) scrollTo()} method.
     * 
     * <p>
     * <b>WARNING</b><br>
     * <i>It turns out that much of TextView's processing is done in idle
     * callbacks. In particular, this method only works correctly if the
     * heights of each line have been computed and cached. Since doing so can
     * be computationally expensive, it happens some time after text is
     * actually inserted and thus may not be available yet. In theory the
     * scrolling will be queued up, but you may notice odd effects.</i>
     * 
     * <p>
     * This problem can crop up if you have newly populated a large amount of
     * text into a TextView and want to force the viewport and cursor to be at
     * the end of the text. One possible workaround: after doing the large
     * <code>insertAtCursor()</code>, you might try the following:
     * 
     * <pre>
     * start = buffer.getIterStart();
     * buffer.placeCursor(start);
     * </pre>
     * 
     * before calling
     * 
     * <pre>
     * end = buffer.getIterEnd();
     * view.scrollTo(end);
     * </pre>
     * 
     * this may have the effect of causing the heights to be calculated.
     * 
     * @since 4.0.9
     */
    /*
     * The oddities and "workaround" suggested are based on the currently weak
     * scrolling behaviour in GtkTextView. If and when that gets cleaned up,
     * then remove this nonsense from the documentation here.
     */
    public void scrollTo(TextMark mark, double withinMargin, double xalign, double yalign) {
        if ((withinMargin < 0) || (withinMargin > 0.5)) {
            throw new IllegalArgumentException("withinMargin must be between 0.0 and 0.5");
        }
        if ((xalign < 0.0) || (xalign > 1.0)) {
            throw new IllegalArgumentException("xalign must be between 0.0 and 1.0");
        }
        if ((yalign < 0.0) || (yalign > 1.0)) {
            throw new IllegalArgumentException("yalign must be between 0.0 and 1.0");
        }

        GtkTextView.scrollToMark(this, mark, withinMargin, true, xalign, yalign);
    }

    /**
     * Signal emitted by GTK allowing you to populate MenuItems into the popup
     * context menu displayed by a TextView (typically in response to the user
     * right-clicking).
     * 
     * <p>
     * The signal has a parameter of type Menu and populating the popup menu
     * is done by adding items to it with Menu's <code>append()</code>, etc.
     * After constructing your menu one <i>must</i> call
     * <code>showAll()</code> on the Menu or your newly added MenuItems will
     * <i>not</i> appear in the popup menu.
     * 
     * <p>
     * An example:
     * 
     * <pre>
     * TextView t;
     * 
     * t.connect(new TextView.PopulatePopup() {
     *     public void onPopulatePopup(TextView source, Menu menu) {
     *         menu.append(new ImageMenuItem(Stock.SAVE, new MenuItem.Activate() {
     *             public void onActivate(MenuItem source) {
     *                 doStuff();
     *             }
     *         }));
     *         menu.showAll();
     *     }
     * });
     * </pre>
     * 
     * @author Kenneth Prugh
     * @since 4.0.9
     */
    public interface PopulatePopup extends GtkTextView.PopulatePopupSignal
    {
        /**
         * Add MenuItems you wish to see in the TreeView's context menu to
         * <code>menu</code>.
         */
        public void onPopulatePopup(TextView source, Menu menu);
    }

    /**
     * Hook up a handler to receive <code>TextView.PopulatePopup</code>
     * signals on this TextView. This will be emitted each time the user
     * right-clicks or presses the <b><code>Menu</code></b> key, and allows
     * you to populate the popup menu according to the current circumstances -
     * in other words, making it a context menu.
     * 
     * @since 4.0.9
     */
    public void connect(TextView.PopulatePopup handler) {
        GtkTextView.connect(this, handler, false);
    }

    /**
     * Set the padding that will be put below each paragraph of text in the
     * TextView. The default is <code>0</code>, ie for it just to continue
     * with normal line spacing as specified by the current font metrics.
     * 
     * <p>
     * See also {@link #setPaddingAboveParagraph(int)
     * setPaddingAboveParagraph()} and {@link #setPaddingInsideParagraph(int)
     * setPaddingInsideParagraph()}.
     * 
     * <p>
     * <i>This sets the <var>pixels-below-lines</var> property in GTK.</i>
     * 
     * @since 4.0.9
     */
    public void setPaddingBelowParagraph(int pixels) {
        GtkTextView.setPixelsBelowLines(this, pixels);
    }

    /**
     * Set the padding that will be put above each paragraph of text in the
     * TextView. The default is <code>0</code>; if you change this then the
     * first line will be offset from the top edge of the TextView.
     * 
     * <p>
     * See also {@link #setPaddingBelowParagraph(int)
     * setPaddingBelowParagraph()} and {@link #setPaddingInsideParagraph(int)
     * setPaddingInsideParagraph()}.
     * 
     * <p>
     * <i>This sets the <var>pixels-above-lines</var> property in GTK.</i>
     * 
     * @since 4.0.9
     */
    public void setPaddingAboveParagraph(int pixels) {
        GtkTextView.setPixelsAboveLines(this, pixels);
    }

    /**
     * Set the padding that will be put between each line in a paragraph if
     * wrapping is turned on. Wrapping happens as a result of enabling
     * {@link #setWrapMode(WrapMode) setWrapMode()} along with something
     * acting to restrict the width allocated to the TextView (for example, by
     * placing it in a ScrolledWindow). When wrapping occurs, then a single
     * line of text in a TextBuffer will become a paragraph of multiple lines
     * in the TextView displaying it.
     * 
     * <p>
     * The default is <code>0</code>, ie to leave the line spacing alone. If
     * nothing is causing lines to wrap then this setting will have no effect.
     * 
     * <p>
     * See also {@link #setPaddingAboveParagraph(int)
     * setPaddingAboveParagraph()} and {@link #setPaddingBelowParagraph(int)
     * setPaddingBelowParagraph()} for the spacing before and after each
     * paragraph (wrapped or not).
     * 
     * <p>
     * <i>This sets the <var>pixels-inside-wrap</var> property in GTK.</i>
     * 
     * @since 4.0.9
     */
    public void setPaddingInsideParagraph(int pixels) {
        GtkTextView.setPixelsInsideWrap(this, pixels);
    }

    /**
     * Set the behaviour when the <b><code>Tab</code></b> key is pressed. The
     * default is <code>true</code>, that a <code>'\t'</code> character will
     * be inserted into the underlying TextBuffer. If you would rather that
     * <b><code>Tab</code></b> causes the focus to change to the next Widget
     * rather than inserting a tab, then set this to <code>false</code>.
     * 
     * @since 4.0.9
     */
    public void setAcceptsTab(boolean setting) {
        GtkTextView.setAcceptsTab(this, setting);
    }

    /**
     * Set the padding to appear on the left side of the text. The default is
     * <code>0</code>.
     * 
     * <p>
     * <i>This sets the <var>left-margin</var> property in GTK.</i>
     * 
     * @since 4.0.9
     */
    public void setMarginLeft(int pixels) {
        if (pixels < 0) {
            throw new IllegalArgumentException("Margin must be >= 0 pixels");
        }
        GtkTextView.setLeftMargin(this, pixels);
    }

    /**
     * Set the padding to appear on the right side of the text. The default is
     * <code>0</code>.
     * 
     * <p>
     * <i>This sets the <var>right-margin</var> property in GTK.</i>
     * 
     * @since 4.0.9
     */
    public void setMarginRight(int pixels) {
        if (pixels < 0) {
            throw new IllegalArgumentException("Margin must be >= 0 pixels");
        }
        GtkTextView.setRightMargin(this, pixels);
    }

    /**
     * Set the number of pixels that will be between the left hand edge of the
     * TextView and the left hand edge of the paragraphs of text.
     * 
     * @since 4.0.10
     */
    public void setLeftMargin(int pixels) {
        GtkTextView.setLeftMargin(this, pixels);
    }
}
