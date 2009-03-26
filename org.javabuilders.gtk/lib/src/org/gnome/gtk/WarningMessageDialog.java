/*
 * WarningMessageDialog.java
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
 * A Dialog preconfigured to present a cautionary message to the user and to
 * request confirmation of an action.
 * 
 * <p>
 * Be careful not to overdo it - a Dialog popping up to confirm every last
 * action is a) really annoying and worse b) will desensitize the user to the
 * message - they will just start ignoring the Dialog and thus probably miss
 * the chance to avoid something <i>really</i> important.
 * 
 * <p>
 * This is a modal MessageDialog of type {@link MessageType#WARNING WARNING}
 * with an "Ok" Button for the user to confirm they want to proceed, and a
 * "Cancel" Button for them to abort the operation.
 * 
 * <p>
 * 
 * <pre>
 * final Window w;
 * final Dialog d;
 * final ResponseType result;
 * 
 * d = new WarningMessageDialog(w, &quot;You are about set off the fire alarm&quot;,
 *         &quot;Is that what you really want to do?&quot;);
 * result = d.run();
 * 
 * d.hide();
 * if (result == ResponseType.OK) {
 *     // do dangerous action
 * }
 * </pre>
 * 
 * @author Andrew Cowie
 * @author Vreixo Formoso
 */
public class WarningMessageDialog extends MessageDialog
{
    public WarningMessageDialog(Window parent, String primary, String secondary) {
        super(parent, true, MessageType.WARNING, ButtonsType.OK_CANCEL, primary);
        if (secondary != null) {
            this.setSecondaryText(secondary);
        }
    }
}
