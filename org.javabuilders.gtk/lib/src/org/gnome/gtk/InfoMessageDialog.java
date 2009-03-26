/*
 * InfoMessageDialog.java
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
 * A Dialog preconfigured to present an information message to the user.
 * 
 * <p>
 * This is a modal MessageDialog of type {@link MessageType#INFO INFO} with an
 * "Ok" Button.
 * 
 * <p>
 * You <code>show()</code>ing this Dialog will prevent interaction with the
 * rest of the application; calling <code>run()</code> will of course block
 * until the user clicks the "Ok" Button or closes the Dialog.
 * 
 * <p>
 * You can use this as follows:
 * 
 * <pre>
 * final Dialog d;
 * 
 * d = new InfoMessageDialog(w, &quot;You should feel privileged&quot;, &quot;He hardly lands for anyone.&quot;);
 * d.run();
 * d.hide();
 * </pre>
 * 
 * which will result in:
 * 
 * <p>
 * <img src="InfoMessageDialog.png">
 * 
 * @author Andrew Cowie
 * @author Vreixo Formoso
 */
public class InfoMessageDialog extends MessageDialog
{
    public InfoMessageDialog(Window parent, String primary, String secondary) {
        super(parent, true, MessageType.INFO, ButtonsType.OK, primary);
        if (secondary != null) {
            this.setSecondaryText(secondary);
        }
    }
}
