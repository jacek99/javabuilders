/*
 * MessageType.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import org.freedesktop.bindings.Constant;

/**
 * The type of the message shown in a {@link MessageDialog MessageDialog}.
 * Mostly this is about determining the type of icon that will be shown.
 * 
 * @author Vreixo Formoso
 * @since 4.0.5
 */
public final class MessageType extends Constant
{
    private MessageType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * The Dialog shows an informational message.
     * 
     * <p>
     * Only use this kind of messages for important information the user must
     * know before continuing to use the application, or for information the
     * user has requested. For less important information you shouldn't use a
     * MessageDialog, but other informational Widgets such as a
     * {@link Statusbar Statusbar}.
     * 
     * <p>
     * Consider using a single {@link ButtonsType#OK OK} Button to let the
     * user close the Dialog.
     */
    public static final MessageType INFO = new MessageType(GtkMessageType.INFO, "INFO");

    /**
     * The Dialog present a choice to the user.
     * 
     * <p>
     * Use this type to present Dialogs that ask users to take a non serious
     * decision. Usually you will provide {@link ButtonsType#YES_NO YES_NO}
     * Buttons to let the user express his choice.
     * 
     * <p>
     * If the choice can have a serious impact in the application, such as the
     * lost of data, a {@link #WARNING WARNING} message is a better
     * alternative.
     */
    public static final MessageType QUESTION = new MessageType(GtkMessageType.QUESTION, "QUESTION");

    /**
     * The Dialog shows a warning message.
     * 
     * <p>
     * A typical usage of these messages is to request user confirmation to a
     * potentially dangerous action. You should present such messages when the
     * the task that is going to be executed may destroy user data, create a
     * security risk, or take more than 30 seconds of user effort to recover
     * from if it was selected in error.
     * 
     * <p>
     * While simple warning messages can be correctly handled by
     * {@link ButtonsType#OK_CANCEL OK_CANCEL} Buttons, in many cases you may
     * want to provide a Help Button to give the user more information about
     * the risk, or to replace the OK Button with a message that clearly show
     * what the Button does. For example, in a "Close without saving?" alert,
     * the OK text could be "Discard changes". A third Button can also be
     * useful (for example, a "Save changes" Button in the previous" case. In
     * all cases a CANCEL Button must be present to let the user cancel the
     * operation.
     */
    public static final MessageType WARNING = new MessageType(GtkMessageType.WARNING, "WARNING");

    /**
     * The Dialog shows an error message.
     * 
     * <p>
     * You should use this kind of MessageDialog when an operation requested
     * by the user cannot be successfully completed. However, if the operation
     * that has caused the error is a background or periodic operation, you
     * may want to consider displaying the error by other means, such as a
     * {@link Statusbar Statusbar} message, unless the error could result in
     * data loss or other serious problems.
     * 
     * <p>
     * You should present an {@link ButtonsType#OK OK} Button to let user
     * close the Dialog. Optionally, if the error can be immediately fixed,
     * you may add a Button to launch the repair operation (a "Format..."
     * Button in a "This disk is not formatted" alert, for example).
     */
    public static final MessageType ERROR = new MessageType(GtkMessageType.ERROR, "ERROR");

    /**
     * Use this when the predefined MessageTypes are not suitable for your
     * MessageDialog. In that cases you may want to add a custom icon to the
     * Dialog.
     */
    public static final MessageType OTHER = new MessageType(GtkMessageType.OTHER, "OTHER");

}
