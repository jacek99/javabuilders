/*
 * Dialog.java
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
 * A Dialog is a special Window which is used to display information for the
 * user or to get response from her. While a normal Window usually is used to
 * represent the whole application and to offer sometimes many interactions
 * for the user, a Dialog should only be opened if a special situation has
 * occurred.
 * 
 * <p>
 * A Dialog is a composite Window which contains a {@link VBox}. This box is
 * split by an HSeparator into two areas:
 * 
 * <ul>
 * <li>
 * <p>
 * The <var>main area</var> consists of the VBox itself above the separator.
 * It is used to pack Widgets such as Labels or Icons which will display the
 * descriptive part of your Dialog. The easiest way to add Widget(s) to the
 * top area is to simply call the {@link Dialog#add(Widget) add()} method. It
 * is a good technique to first add your own Container such as an {@link HBox}
 * to the top area and pack all other Widgets to it if you need more refined
 * layout control, although this is not compulsory.
 * 
 * <li>
 * <p>
 * The bottom <var>action area</var> (actually an HButtonBox) is used to pack
 * buttons to the Dialog which may perform actions such as "Ok" or "Cancel".
 * You should only add Buttons to the <var>action area</var> using one of the
 * {@link Dialog#addButton(String, ResponseType) addButton()} methods allowing
 * you to specify a ResponseType constant to be emitted if the Button is
 * clicked.
 * </ul>
 * 
 * <p>
 * To open the Dialog as a normal non-blocking Window you use the
 * {@link Widget#show() show()} method after you have packed the various child
 * elements into it. On the other hand, for occasions where you are using a
 * Dialog to get information required for the further progress of the main
 * application, the {@link Dialog#run() run()} method can be used to open the
 * Dialog. This method blocks the application and waits for response from the
 * user.
 * 
 * <p>
 * GTK comes with a number of standard Dialogs which can be used for typical
 * situations such as {@link MessageDialog MessageDialog} to present urgent
 * information to the user, and {@link FileChooserDialog FileChooserDialog}
 * which provides the familiar behaviour of popping up a Dialog to select a
 * file.
 * 
 * @author Thomas Schmitz
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.5
 */
public class Dialog extends Window
{
    protected Dialog(long pointer) {
        super(pointer);
    }

    /**
     * Creates a new Dialog box with both <var>main area</var> and an empty
     * <var>action area</var>, separated by an HSeparator. You'll need to do
     * the rest of the Dialog setup manually yourself, including calling
     * {@link Window#setTransientFor(Window) setTransientFor()} and
     * {@link Window#setTitle(String) setTitle()}.
     */
    public Dialog() {
        super(GtkDialog.createDialog());
    }

    /**
     * Create a new Dialog. This is a convenience constructor with the same
     * effect as:
     * 
     * <pre>
     * d = new Dialog();
     * d.setTitle(title);
     * d.setModal(modal);
     * d.setTransientFor(parent);
     * </pre>
     * 
     * @param title
     *            Title of the new Dialog.
     * @param parent
     *            Transient parent of the dialog, or <code>null</code>. If a
     *            parent is provided, the window manager will keep the Dialog
     *            on top of it. If the Dialog is modal, it is highly
     *            recommended that you provide a parent Window, otherwise the
     *            user could be trying to interact with the main Window while
     *            the Dialog is blocking it, but hidden under other Window. In
     *            that case, the user experience is really bad, as it is not
     *            easy to figure out the real case of the blocking.
     * @param modal
     *            Whether the dialog is to be modal or not. A modal Dialog
     *            blocks interaction with the other parts of the application.
     *            Note that you can also get a similar behaviour using the
     *            {@link #run() run()} method.
     */
    public Dialog(String title, Window parent, boolean modal) {
        super(GtkDialog.createDialogWithButtons(title, parent, modal ? DialogFlags.MODAL
                : DialogFlags.NONE, null));
    }

    /**
     * Add a Widget to the <var>main area</var> of the Dialog.
     */
    public void add(Widget widget) {
        final VBox box;

        box = (VBox) this.getChild();
        box.add(widget);
    }

    /**
     * Adds an action {@link Button} with the given text as its label to the
     * end of the Dialog's <var>action area</var>. The given ResponseType will
     * be returned back in the Dialog's {@link Dialog.Response} signal when
     * the Button added as a result of this call is clicked.
     * 
     * @return the added Button. This is a convenience allowing you to hook up
     *         further handlers to the Button if necessary.
     */
    public Button addButton(String text, ResponseType response) {
        return (Button) GtkDialog.addButton(this, text, response.getResponseId());
    }

    /**
     * Add a Button whose icon and label are taken from a given Stock. It is,
     * as ever, recommended to use a Stock Button for common actions. See
     * {@link #addButton(String, ResponseType) addButton()}.
     */
    public Button addButton(Stock stock, ResponseType response) {
        return (Button) GtkDialog.addButton(this, stock.getStockId(), response.getResponseId());
    }

    /**
     * Block the rest of the application by running in a recursive main loop
     * until a {@link Dialog.Response} signal arises on the Dialog as a result
     * of the user activating one of the <var>action area</var> Buttons. This
     * is known as a 'modal' Dialog. While this loop is running the user is
     * prevented from interacting with the rest of the application.
     * 
     * <p>
     * While there are legitimate uses of modal Dialogs, it is a feature that
     * tends to be badly abused. Therefore, before you call this method,
     * please consider carefully if it is wise to prevent the rest of the user
     * interface from being used.
     * 
     * <p>
     * A common bug is for people to neglect to {@link Widget#hide() hide()}
     * the Dialog after this method returns. If you don't, then the Dialog
     * will remain on screen despite repeated clicking of (for example) the
     * "Close" Button. [The Window is not hidden automatically because of
     * cases like "Apply" in preferences Dialogs and "Open" in FileChoosers
     * when a folder is selected and activation will change directory rather
     * than finishing the Dialog]
     * 
     * <p>
     * While <code>run()</code> can be very useful in callbacks when popping
     * up a quick question, you may find hooking up to the
     * {@link Dialog.Response} signal more flexible.
     * 
     * @return the emitted response constant. If asking a question, you should
     *         check this against the various constants in the ResponseType
     *         class. Don't forget to <code>hide()</code> afterwards.
     */
    public ResponseType run() {
        final int value;
        final ResponseType response;

        value = GtkDialog.run(this);
        response = ResponseType.constantFor(value);

        return response;
    }

    /**
     * This signal arises when a user activates one of the Widgets laid out in
     * the <var>action area</var> of the Dialog.
     * 
     * @author Thomas Schmitz
     * @author Vreixo Formoso
     * @since 4.0.5
     */
    public interface Response
    {
        void onResponse(Dialog source, ResponseType response);
    }

    /**
     * Hook up a <code>Dialog.Response</code> handler.
     */
    public void connect(Dialog.Response handler) {
        GtkDialog.connect(this, new ResponseHandler(handler), false);
    }

    /*
     * Needed for emit the Dialog.Response signal with a type-safe
     * ResponseType instead of the underlying int ordinal.
     */
    private static class ResponseHandler implements GtkDialog.ResponseSignal
    {
        private Dialog.Response handler;

        private ResponseHandler(Dialog.Response handler) {
            this.handler = handler;
        }

        public void onResponse(Dialog source, int responseId) {
            final ResponseType response;

            response = ResponseType.constantFor(responseId);

            handler.onResponse(source, response);
        }
    }

    /** @deprecated */
    public interface RESPONSE
    {
        void onResponse(Dialog source, ResponseType response);
    }

    /** @deprecated */
    public void connect(RESPONSE handler) {
        assert false : "use Dialog.Response instead";
        GtkDialog.connect(this, new ResponseHandler0(handler), false);
    }

    /** @deprecated */
    private static class ResponseHandler0 implements GtkDialog.ResponseSignal
    {
        private RESPONSE handler;

        /** @deprecated */
        private ResponseHandler0(RESPONSE handler) {
            this.handler = handler;
        }

        public void onResponse(Dialog source, int responseId) {
            final ResponseType response;

            response = ResponseType.constantFor(responseId);

            handler.onResponse(source, response);
        }
    }

    /**
     * Cause a <code>Dialog.Response</code> signal with the specified
     * ResponseType to be emitted by this Dialog.
     * 
     * @since 4.0.9
     */
    public void emitResponse(ResponseType response) {
        GtkDialog.response(this, response.getResponseId());
    }
}
