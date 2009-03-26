/*
 * QuestionMessageDialog.java
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

/**
 * A Dialog properly preconfigured to ask a yes or no question of the user.
 * 
 * <p>
 * This is a modal MessageDialog of type {@link MessageType#QUESTION QUESTION}
 * with an "Yes" and a "No" Button.
 * 
 * <p>
 * You would typically use this in conjunction with <code>run()</code> as
 * follows:
 * 
 * <pre>
 * final Window main;
 * final Dialog question;
 * final ResponseType response;
 * 
 * main = new Window();
 * ...
 * question = new QuestionMessageDialog(main, &quot;File exists!&quot;, &quot;Do you really want to overwrite it?&quot;);
 * response = question.run();
 * 
 * question.hide();
 * if (response == ResponseType.YES) {
 *     // save file
 * } else {
 *     // cancel and return to application
 * }
 * </pre>
 * 
 * which will result in something like:
 * 
 * <p>
 * <img src="QuestionMessageDialog.png">
 * 
 * <p>
 * (depending on your theme, icon set, window manager preferences, of course).
 * Notable here are the "question mark" icon and that the "No" Button has
 * focus by default.
 * 
 * @author Andrew Cowie
 * @author Vreixo Formoso
 */
public class QuestionMessageDialog extends MessageDialog
{
    public QuestionMessageDialog(Window parent, String primary, String secondary) {
        super(parent, true, MessageType.QUESTION, ButtonsType.YES_NO, primary);
        if (secondary != null) {
            this.setSecondaryText(secondary);
        }
    }
}
