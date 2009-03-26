/*
 * ErrorMessageDialog.java
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
 * A Dialog preconfigured to present an urgent error message to the user.
 * 
 * <p>
 * This is a modal MessageDialog of type {@link MessageType#ERROR ERROR} with
 * an "Ok" Button.
 * 
 * <p>
 * See {@link InfoMessageDialog InfoMessageDialog} for an example of how to
 * use these convenience MessageDialog subclasses.
 * 
 * @author Andrew Cowie
 * @author Vreixo Formoso
 */
public class ErrorMessageDialog extends MessageDialog
{
    public ErrorMessageDialog(Window parent, String primary, String secondary) {
        super(parent, true, MessageType.ERROR, ButtonsType.OK, primary);
        if (secondary != null) {
            this.setSecondaryText(secondary);
        }
    }
}
