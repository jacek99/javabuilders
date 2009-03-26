/*
 * AssistantPageType.java
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

import org.freedesktop.bindings.Constant;

/**
 * Used for determining the page role inside the {@link Assistant}. It's used
 * to handle buttons sensitivity and visibility.
 * 
 * <p>
 * Note that an assistant needs to end its page flow with a page of type
 * {@link #CONFIRM CONFIRM} or {@link #SUMMARY SUMMARY} to be correct.
 * 
 * @author Stefan Prelle
 * @since 4.0.8
 */
public final class AssistantPageType extends Constant
{
    private AssistantPageType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * The page has regular contents. It provides a 'Cancel','Back'
     */
    public static final AssistantPageType CONTENT = new AssistantPageType(GtkAssistantPageType.CONTENT,
            "CONTENT");

    /**
     * The page contains an introduction to the assistant task.
     */
    public static final AssistantPageType INTRO = new AssistantPageType(GtkAssistantPageType.INTRO,
            "INTRO");

    /**
     * The page lets the user confirm or deny the changes. It provides a
     * 'Cancel', 'Back' and 'Apply' button.
     */
    public static final AssistantPageType CONFIRM = new AssistantPageType(GtkAssistantPageType.CONFIRM,
            "CONFIRM");

    /**
     * The page informs the user of the changes done. It provides a 'Close'
     * button.
     */
    public static final AssistantPageType SUMMARY = new AssistantPageType(GtkAssistantPageType.SUMMARY,
            "SUMMARY");

    /**
     * Used for tasks that take a long time to complete, blocks the assistant 
     * until the page is marked as complete with 
     * {@link Assistant#setPageComplete(Widget, boolean) setPageComplete()}
     */
    public static final AssistantPageType PROGRESS = new AssistantPageType(
            GtkAssistantPageType.PROGRESS, "PROGRESS");

}
