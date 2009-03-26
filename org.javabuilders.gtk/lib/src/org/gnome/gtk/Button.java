/*
 * Button.java
 *
 * Copyright (c) 2006-2008 Operational Dynamics Consulting Pty Ltd, and Others
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * A Widget that emits a signal when clicked on. Button can hold any just
 * about any other Widget as its child. The most commonly used child is a
 * Label, and there are convenience methods to help you just create a button
 * with the given text automatically, notably the
 * {@link Button#Button(String) Button(String)} constructor. <img
 * src="Button.png" class="snapshot">
 * 
 * <p>
 * Since Button is a Bin it strictly only has one child. Internally, however,
 * it may have both an icon image and some text (which is the look commonly
 * seen in the action buttons in Dialog boxes). You can add such an image to a
 * Button by calling {@link #setImage(Image) setImage()}; this works alongside
 * and with {@link #setLabel(String) setLabel()}. The machinery within Button
 * will manage creating the necessary internal structure (HBoxes, Alignments,
 * etc).
 * 
 * @author Andrew Cowie
 * @author Vreixo Formoso
 * @author Mario Torre
 * @since 4.0.0
 */
public class Button extends Bin
{
    protected Button(long pointer) {
        super(pointer);
    }

    /**
     * Create an "empty" button to use as a Container. You'll need to
     * {@link org.gnome.gtk.Container#add(Widget) add()} the Widget which will
     * be the Button's child.
     * 
     * <p>
     * For most uses {@link #setImage(Image) setImage()} and
     * {@link #setLabel(String) setLabel()} will more than take care of
     * things; they can be used together.
     * 
     * @since 4.0.0
     */
    public Button() {
        super(GtkButton.createButton());
    }

    /**
     * Create a button with a Label as its child. Simply specify the text you
     * want for the Label and a Button will be created accordingly. This is
     * quite a common case - in fact, we're generally more used to thinking of
     * Buttons as being Labels that you can press than as arbitrary Widget
     * Containers.
     * 
     * <p>
     * Note that you <i>can</i> use {@link #setImage(Image) setImage()} on a
     * Button created this way.
     * 
     * @param text
     *            the text you wish on the Label that will be created in the
     *            Button.
     * @since 4.0.0
     */
    public Button(String text) {
        super(GtkButton.createButtonWithMnemonic(text));
    }

    /**
     * Create a new Button with a Label and Image from a StockItem. By using a
     * system StockItem, the newly created Button with use the same Label and
     * Image as other GNOME applications. To ensure consistent look-n-feel
     * between applications, it is highly recommend that you use provided
     * StockItems whenever possible.
     * 
     * @param stock
     *            The stock item that will determine the text and icon of the
     *            Button.
     * @since 4.0.4
     */
    public Button(Stock stock) {
        super(GtkButton.createButtonFromStock(stock.getStockId()));
    }

    /**
     * Set the text showing in the Button.
     * 
     * <p>
     * If you created an empty Button without a Label using {@link #Button()
     * Button()}, this will create a Label nested in an Alignment for you.
     * That <i>won't</i> work if you create an empty Button then put a custom
     * Widget in place with {@link Container#add(Widget) add()} instead of
     * employing this method and/or {@link #setImage(Image) setImage()}).
     * 
     * @since 4.0.0
     */
    public void setLabel(String text) {
        GtkButton.setLabel(this, text);
    }

    /**
     * Get the text showing on the Button.
     * 
     * @return the text of the Label, or <code>null</code> if the no-arg
     *         constructor was used and you've just got an arbitrary
     *         Widget-containing-Button, not the more usual Button-with-Label.
     * @since 4.0.0
     */
    public String getLabel() {
        return GtkButton.getLabel(this);
    }

    /**
     * Paint an arbitrary Image over this Button. If this is used on an empty
     * Button then the Button will be the size of the Image and will what is
     * activatable. On the other hand, you <i>can</i> use this in conjunction
     * with {@link #setLabel(String) setLabel()} in which case you will get an
     * icon on the left and the label text on the right.
     * 
     * @since 4.0.5
     */
    public void setImage(Image image) {
        GtkButton.setImage(this, image);
    }

    /**
     * Get the Image associated with this Button.
     * 
     * @return the Widget associated with this Button using the
     *         {@link #setImage(Image) setImage()} method, or
     *         <code>null</code> if the Button doesn't have one set.
     * 
     * @since 4.0.5
     */
    public Image getImage() {
        return (Image) GtkButton.getImage(this);
    }

    /**
     * Get the horizontal alignment of the child Widget within this Button.
     * The return will range from 0.0 (full left) to 1.0 (full right).
     */
    public float getAlignmentX() {
        float[] x = new float[1];
        float[] y = new float[1];

        GtkButton.getAlignment(this, x, y);

        return x[0];
    }

    /**
     * Get the vertical alignment of the child Widget within this Button. The
     * return will range from 0.0 (top) to 1.0 (bottom).
     */
    public float getAlignmentY() {
        float[] x = new float[1];
        float[] y = new float[1];

        GtkButton.getAlignment(this, x, y);

        return y[0];
    }

    /**
     * Set the alignment of the child Widget within the Button. This has no
     * impact unless the child of the Button is a Misc (which of course the
     * default child, a Label, is).
     * 
     * @param xalign
     *            from 0.0f representing fully left-aligned through 1.0f
     *            representing fully right-aligned.
     * @param yalign
     *            from 0.0f for fully top-aligned through 1.0f for fully
     *            bottom-aligned
     */
    public void setAlignment(float xalign, float yalign) {
        GtkButton.setAlignment(this, xalign, yalign);
    }

    /**
     * Set the "relief" style used to determine how the edges of this Button
     * will be decorated. The default is {@link ReliefStyle#NORMAL NORMAL}
     * which results in a Button just as you would expect, screaming out to be
     * pressed! There are two other variations, see {@link ReliefStyle} for
     * details.
     * 
     * @since 4.0.1
     */
    public void setRelief(ReliefStyle style) {
        GtkButton.setRelief(this, style);
    }

    /**
     * Get the relief style in use around this Button.
     * 
     * @since 4.0.1
     */
    public ReliefStyle getRelief() {
        return GtkButton.getRelief(this);
    }

    /**
     * Event generated when a user presses and releases a button, causing it
     * to activate.
     * 
     * <p>
     * <i>When the mouse is used to click on a Button this signal will be
     * emitted, but only if the cursor is still in the Button when the mouse
     * button is released. You're probably used to this behaviour without
     * realizing it.</i>
     * 
     * <h2>All about signal handling</h2>
     * 
     * <h3>Use an anonymous inner class</h3>
     * 
     * <p>
     * In general, the way that java-gnome is intended to be used is for you
     * to create an anonymous inner class right where you call the
     * <code>connect()</code> method to hook up the signal. A typical example
     * is as follows:
     * 
     * <pre>
     * final Button b;
     * 
     * b.connect(new Button.Clicked() {
     *     public void onClicked(Button source) {
     *         doSomething();
     *     }
     * }
     * </pre>
     * 
     * This is the form we recommend; it has the advantage that your handler
     * code is close to the code that declares and configures the Widget.
     * Also, if variables are declared <code>final</code> then you can access
     * them from within the nested anonymous class, and that makes things easy
     * indeed.
     * 
     * <h3>Using a concrete instance</h3>
     * 
     * <p>
     * If you have more complex code, perhaps that you need to reuse across
     * several Widgets, then you can of course create a concrete class that
     * implements Button.Clicked and then use instances of it if you have
     * highly complicated algorithms to implement:
     * 
     * <pre>
     * class ComplexHandler implements Button.Clicked
     * {
     *     private int field;
     * 
     *     ComplexHandler(int param) {
     *         this.field = param;
     *     }
     * 
     *     public void onClicked(Button source) {
     *         doSomethingVeryComplicatedWith(field);
     *     }
     * }
     * </pre>
     * 
     * and then when you construct the Button:
     * 
     * <pre>
     * b = new Button();
     * b.connect(new ComplexHandler(42));
     * </pre>
     * 
     * You can just as easily declare one and reuse it if you want to apply
     * the same logic to a series of Button; this is where the
     * <code>source</code> parameter that every signal callback signature
     * includes.
     * 
     * <pre>
     * final ComplexHandler handler;
     * 
     * handler = new ComplexHandler(42);
     * ...
     * b1.connect(handler);
     * b2.connect(handler);
     * b3.connect(handler);
     * ...
     * </pre>
     * 
     * <h3>Self-delegation</h3>
     * 
     * <p>
     * If you implement Button.Clicked in the class you're currently working
     * on, then you use a technique called "self-delegation" which can be
     * useful in certain circumstances:
     * 
     * <pre>
     * b.connect(this);
     * </pre>
     * 
     * This has disadvantage that you can only have one
     * <code>onClicked()</code> method in your class, so if you have lots of
     * Buttons then you lose the benefit of being able to connect different
     * handlers to different Buttons.
     * 
     * <p>
     * We recommend you use anonymous inner classes in-place whenever
     * possible. In our experience, having the handler logic next to the code
     * that declares an instance dramatically improves readability.
     * 
     * @author Andrew Cowie
     * @since 4.0.0
     */
    public interface Clicked extends GtkButton.ClickedSignal
    {
        public void onClicked(Button source);
    }

    /**
     * Hook up a handler to receive <code>Button.Clicked</code> events on this
     * Button. See {@link Button.Clicked} for a detailed discussion of how to
     * connect signals.
     * 
     * @since 4.0.0
     */
    public void connect(Clicked handler) {
        GtkButton.connect(this, handler, false);
    }

    /**
     * @deprecated
     */
    public interface CLICKED extends GtkButton.ClickedSignal
    {
    }

    /**
     * @deprecated
     */
    public void connect(CLICKED handler) {
        assert false : "use Button.Clicked instead";
        GtkButton.connect(this, handler, false);
    }

    /*
     * Button.Activate: "Applications should never connect to this signal, but
     * use the 'clicked' signal."
     * 
     * Button.Entered, Button.Pressed, etc: deprecated.
     */

    /**
     * Cause a <code>Button.Clicked</code> signal to be emitted.
     * 
     * @since 4.0.6
     */
    public void emitClicked() {
        GtkButton.clicked(this);
    }

    /**
     * Set whether clicking the Button will cause the Button to grab the
     * focus. The default is <code>true</code>, the behaviour you're
     * accustomed to with regular Buttons. Setting this to <code>false</code>
     * is only used in unusual situations like toolboxes where you don't want
     * to steal focus away from where the main action is taking place.
     * 
     * @since 4.0.8
     */
    public void setFocusOnClick(boolean setting) {
        GtkButton.setFocusOnClick(this, setting);
    }
}
