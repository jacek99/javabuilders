/*
 * ActionGroup.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2007 Vreixo Formoso
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import org.gnome.glib.Object;

/**
 * A group of Actions.
 * 
 * <p>
 * Actions are generally organized into groups. All Actions that would make
 * sense to use in a particular context should be in a single group. Multiple
 * ActionGroups may be used for a particular user interface. In fact, it is
 * expected that most nontrivial applications will make use of multiple
 * ActionGroups. For example, in an application that can edit multiple
 * documents, you would likely have one ActionGroup holding global Actions
 * (e.g. quit, about, new), and one ActionGroup per document holding Actions
 * that act on that document (eg. save, cut/copy/paste, etc). Each Window's
 * Menus would be constructed from a combination of two ActionGroups.
 * 
 * @author Vreixo Formoso
 * @since 4.0.4
 */
public class ActionGroup extends Object
{
    protected ActionGroup(long pointer) {
        super(pointer);
    }

    /**
     * Create a new ActionGroup.
     * 
     * @param name
     *            TODO what the name is for? it seems only useful for
     *            accelerator questions that we will expose in another way!
     */
    public ActionGroup(String name) {
        super(GtkActionGroup.createActionGroup(name));
    }

    /**
     * Sets whether Actions in this ActionGroup can respond to user events.
     * 
     * <p>
     * Notice that both the Action itself and the ActionGroup need to be
     * sensitive to actually allow the user to activate the Action. See
     * {@link Action#setSensitive(boolean) Action.setSensitive()} for the
     * other half of this equation.
     */
    public void setSensitive(boolean sensitive) {
        GtkActionGroup.setSensitive(this, sensitive);
    }

    /**
     * Whether this ActionGroup is sensitive, i.e., if its Actions can be
     * activated by users. Take care that even if the ActionGroup is
     * sensitive, any or all of its Actions may not be.
     */
    public boolean getSensitive() {
        return GtkActionGroup.getSensitive(this);
    }

    /**
     * Sets whether Actions in this ActionGroup are visible to the user.
     * 
     * <p>
     * Notice that both the Action itself and the ActionGroup need to be
     * visible in order the users can see them displayed on screen. See
     * {@link Action#setVisible(boolean) Action.setVisible()} for the
     * corresponding per-Action method.
     */
    public void setVisible(boolean visible) {
        GtkActionGroup.setVisible(this, visible);
    }

    /**
     * Get if this ActionGroup is visible for the user. Take care that even if
     * the ActionGroup is visible, its Actions might not be.
     */
    public boolean getVisible() {
        return GtkActionGroup.getVisible(this);
    }

    /**
     * Adds an Action to this ActionGroup.
     */
    /*
     * FIXME Gtk+ docs claim that this function can lead to problems if the
     * user tries to modify the accelerator of a MenuItem associated with the
     * Action. Thus, I'm tempted to map it to
     * GtkActionGroup.addActionWithAccel, with a "" accelerator. Anyway, given
     * that accelerators are not exposed yet, I will delay this discussion a
     * bit.
     */
    public void addAction(Action action) {
        GtkActionGroup.addAction(this, action);
    }
}
