/*
 * Action.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2007      Vreixo Formoso
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
 * Actions represent an operation that the user can perform from one of
 * several GUI places.
 * 
 * <p>
 * Usually, an application provides several ways to let users execute an
 * operation, for example the "Open File..." shows up in Menus, Toolbars, and
 * elsewhere. If an operation can be executed from several GUI places, it
 * seems logical that such different places appear similar to the user.
 * Namely, they should have the same textual label, the same icon, and
 * certainly the same accelerator key. This way, if the user is accustomed to
 * executing an operation from the Toolbar and then sees the equivalent
 * operation in as a MenuItem with the same icon and label, she will know that
 * both will do the same thing. It thus seems reasonable that both MenuItem
 * and ToolButton share some Object where the common information is stored,
 * and this is indeed the role of the Action class.
 * 
 * <p>
 * An Action holds information about an operation that could be executed from
 * different places. This information includes the label that is shown to the
 * user, the icon, and the tooltip (popup help message). It also stores state
 * information, such as whether the action is "visible" or "sensitive". When
 * this information changes, all the Action <i>proxies</i> (i.e. the Widgets
 * such as MenuItems or ToolButtons related to it) are also all changed
 * properly.
 * 
 * <p>
 * Each Action has an {@link Action.Activate} signal emitted when any of its
 * proxies are triggered. Thus you can just connect one signal to start the
 * needed operation, instead of having to connect to the
 * <code>Action.Activate</code> signal of each of various proxies.
 * 
 * <p>
 * Once you have created an Action, you can get proxies for it with the
 * {@link #createMenuItem() createMenuItem()} and {@link #createToolItem()
 * createToolItem()} methods.
 * 
 * <p>
 * Incidentally, you can still use Actions even if you only plan to let the
 * user execute operations from a single place in your application; they're a
 * useful technique to concentrate the code handling of user activity.
 * 
 * @see ActionGroup
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.4
 */
public class Action extends Object
{
    protected Action(long pointer) {
        super(pointer);
    }

    /**
     * Create a new Action, and connect a handler to its
     * <code>Action.Activate</code> signal.
     * 
     * @param name
     *            A unique name for the Action.
     * @param label
     *            The text that will be displayed in the proxy Widgets. You
     *            usually will want to localize it to the user language.
     * @param tooltip
     *            A Tooltip or little help message for the Action. Also
     *            localized.
     * @param stock
     *            The stock icon to display in proxy Widgets.
     * @param handler
     *            A handler to connect to the <code>Action.Activate</code>
     *            signal. Typically this will be used to actually start the
     *            operation related to this Action.
     * @since 4.0.4
     */
    /*
     * FIXME describe the implications of different choices for name.
     */
    public Action(String name, String label, String tooltip, Stock stock, Action.Activate handler) {
        super(GtkAction.createAction(name, label, tooltip, stock.getStockId()));
        connect(handler);
    }

    /** @deprecated */
    public Action(String name, String label, String tooltip, Stock stock, ACTIVATE handler) {
        super(GtkAction.createAction(name, label, tooltip, stock.getStockId()));
        connect(handler);
    }

    /**
     * Create a new Action.
     * 
     * @param name
     *            A unique name for the Action.
     * @param label
     *            The text that will be displayed in the proxy Widgets. You
     *            usually will want to localize it to the user language.
     * @param tooltip
     *            A Tooltip or little help message for the Action. Also
     *            localized.
     * @param stock
     *            The Stock icon to display in proxy Widgets.
     * @since 4.0.4
     */
    public Action(String name, String label, String tooltip, Stock stock) {
        super(GtkAction.createAction(name, label, tooltip, stock.getStockId()));
    }

    /**
     * Create a new Action from a Stock item. The message and tooltip will be
     * supplied by GTK automatically.
     * 
     * @since 4.0.7
     */
    public Action(String name, Stock stock) {
        super(GtkAction.createAction(name, null, null, stock.getStockId()));
    }

    /**
     * Create a new Action based on a Stock item, and connect a handler to its
     * <code>Action.Activate</code> signal. Complements the
     * {@link #Action(String, Stock) &lt;init&gt;(String, Stock)} constructor.
     * 
     * @since 4.0.9
     */
    public Action(String name, Stock stock, Action.Activate handler) {
        super(GtkAction.createAction(name, null, null, stock.getStockId()));
        connect(handler);
    }

    /**
     * Create a new Action, and connect a handler to its
     * <code>Action.Activate</code> signal.
     * 
     * @param name
     *            A unique name for the Action.
     * @param label
     *            The label that will be displayed in the proxy Widgets. You
     *            usually will want to localize it to the user language.
     * @param handler
     *            A handler to connect to the <code>Action.Activate</code>
     *            signal. Usually will will start from here the operation
     *            related to the Action.
     * @since 4.0.4
     */
    public Action(String name, String label, Action.Activate handler) {
        super(GtkAction.createAction(name, label, null, null));
        connect(handler);
    }

    /** @deprecated */
    public Action(String name, String label, ACTIVATE handler) {
        super(GtkAction.createAction(name, label, null, null));
        connect(handler);
    }

    /**
     * Create a new Action.
     * 
     * @param name
     *            A unique name for the Action.
     * @param label
     *            The text that will be displayed in the proxy Widgets. You
     *            usually will want to localize it to the user language.
     * @since 4.0.4
     */
    public Action(String name, String label) {
        super(GtkAction.createAction(name, label, null, null));
    }

    /**
     * Create a MenuItem from this Action.
     * 
     * <p>
     * You can add the returned MenuItem to a {@link Menu}. The MenuItem will
     * have the same label, icon or accelerator of this Action, and when the
     * user activates the MenuItem, this Action will
     * <code>Action.Activate</code> too.
     * 
     * @since 4.0.4
     */
    public MenuItem createMenuItem() {
        return (MenuItem) GtkAction.createMenuItem(this);
    }

    /**
     * Creates a ToolItem from this Action.
     * 
     * <p>
     * You can add the returned ToolItem to a {@link Toolbar}. The ToolItem
     * will have the same Label, icon, tooltips or accelerator of this Action,
     * and when the user clicks it, this Action will be
     * <code>Action.Activate</code>d.
     * 
     * @since 4.0.4
     */
    public ToolItem createToolItem() {
        return (ToolItem) GtkAction.createToolItem(this);
    }

    /**
     * Set if the Action is available to be activated by the user. When an
     * Action is not sensitive, all its ToolItem or MenuItem proxies are
     * likewise disabled, meaning the user can't activate any of them.
     * 
     * <p>
     * You usually will want to deactivate an Action when its operation makes
     * no sense in current application status. For example, in a text editor
     * you should probably disable the "Save" Action when there is no document
     * is open or when current document hasn't been changed.
     * 
     * <p>
     * When not sensitive, Action proxies are displayed differently than
     * enabled proxies (usually in grey color). That way, user knows the
     * Action has no application in the current program state, and she needs
     * to do something before the disabled Action becomes applicable.
     * 
     * <p>
     * However, when a full Menu on a MenuBar needs to be disabled, generally
     * a better idea is to {@link #setVisible(boolean) setVisible(false)} it.
     * 
     * <p>
     * Finally, note that setting an Action sensitive doesn't always mean it
     * will be actually sensitive, as you also need to make sensitive the
     * ActionGroup the Action belongs to (see {@link #isSensitive()}).
     * 
     * <p>
     * An Action starts life sensitive (ie, <code>true</code>).
     * 
     * @since 4.0.4
     */
    public void setSensitive(boolean sensitive) {
        GtkAction.setSensitive(this, sensitive);
    }

    /**
     * Return whether this Action itself is enabled to be activated by the
     * user. Note that this doesn't necessarily mean effective sensitivity due
     * to the effect of ActionGroups; see {@link #isSensitive() isSensitive()}
     * for that.
     * 
     * @since 4.0.4
     */
    public boolean getSensitive() {
        return GtkAction.getSensitive(this);
    }

    /**
     * Returns whether the action is effectively sensitive, i.e., if both the
     * Action and the ActionGroup it belongs to are enabled.
     * 
     * @since 4.0.4
     */
    public boolean isSensitive() {
        return GtkAction.isSensitive(this);
    }

    /**
     * Set whether the Action is visible to users.
     * 
     * <p>
     * When an Action is not visible, any associated proxies, such as
     * MenuItems or ToolItems are hidden to the used.
     * 
     * <p>
     * In most cases, it's a better idea to {@link #setSensitive(boolean)
     * setSensitive(false)} an Action instead of make it not visible. That
     * way, users can see that such operation exists in the application, but
     * they need to do some operations before it becomes available.
     * 
     * <p>
     * However, when a full Menu is disabled, this could be a good option. For
     * example, on a text edit application, you could hide the "Edit" menu
     * when there's no opened document.
     * 
     * <p>
     * Finally, note that setting an Action visible doesn't always mean it
     * will be actually displayed, as you also need to make visible the
     * ActionGroup the Action belongs to (see {@link #isVisible() isVisible()}
     * for details).
     * 
     * @since 4.0.4
     */
    public void setVisible(boolean visible) {
        GtkAction.setVisible(this, visible);
    }

    /**
     * Return whether the Action itself is visible. Note that this doesn't
     * necessarily mean effective visibility.
     * 
     * @see #isVisible()
     * @see #setVisible(boolean)
     * @since 4.0.4
     */
    public boolean getVisible() {
        return GtkAction.getVisible(this);
    }

    /**
     * Is the Action visible on the screen?
     * 
     * @return <code>true</code> when both the Action and the ActionGroup it
     *         belongs to are visible, <code>false</code> otherwise.
     * @see #setVisible(boolean)
     * @since 4.0.4
     */
    public boolean isVisible() {
        return GtkAction.isVisible(this);
    }

    /**
     * Activates the Action.
     * 
     * <p>
     * Programmatically cause this Action to fire its
     * <code>Action.Activate</code> signal. Note that this has no effect if
     * the Action is not currently sensitive.
     * 
     * <p>
     * Since the Action is automatically activated when user activates one of
     * its proxies (selecting the specific MenuItem or clicking the ToolButton
     * that goes with this Action), normally <b>you don't need this</b>.
     * However, in some cases you want to activate the Action in your
     * application code. Use this there.
     * 
     * @since 4.0.8
     */
    public void emitActivate() {
        GtkAction.activate(this);
    }

    /** @deprecated */
    public void activate() {
        assert false : "use emitActivate() instead";
        GtkAction.activate(this);
    }

    /**
     * Set a tooltip (little help message appearing in a hover) for the
     * Action.
     * 
     * @since 4.0.4
     */
    public void setTooltip(String tooltip) {
        setPropertyString("tooltip", tooltip);
    }

    /**
     * Get the tooltip for the Action.
     * 
     * @since 4.0.4
     */
    public String getTooltip() {
        return getPropertyString("tooltip");
    }

    /**
     * Signal emitted when the Action is activated.
     * 
     * <p>
     * An Action is activated when the user clicks a ToolButton proxy, when
     * (s)he activates an associated MenuItem or when
     * {@link Action#activate() activate()} is called.
     * 
     * @since 4.0.4
     */
    public interface Activate extends GtkAction.ActivateSignal
    {
        public void onActivate(Action source);
    }

    /**
     * Connect a handler to the <code>Action.Activate</code> signal.
     * 
     * @since 4.0.4
     */
    public void connect(Action.Activate handler) {
        GtkAction.connect(this, handler, false);
    }

    /** @deprecated */
    public interface ACTIVATE extends GtkAction.ActivateSignal
    {
    }

    /** @deprecated */
    public void connect(ACTIVATE handler) {
        assert false : "use Action.Activate instead";
        GtkAction.connect(this, handler, false);
    }

    /**
     * Specify a Widget that will be (another) actor hooked up to this Action.
     * When the proxy Widget is activated (ie if a Button, when it is pressed
     * or clicked , etc) then this Action will be activated and its
     * <code>Action.Activate</code> signal will be fired.
     * 
     * <p>
     * You use this when you want to use an Action to centralize activity
     * being launched by various different UI controls, but for which the
     * existing <code>create*</code> proxies are not sufficient. So you create
     * your Widget separately, then tie it to this Action with this method.
     * 
     * <p>
     * GTK will attempt to "synchronize" the tooltips, labels, and icons in
     * use between the Action and the proxy. Thus you can do:
     * 
     * <pre>
     * Action nifty;
     * ImageMenuItem item;
     * 
     * nifty = new Action(&quot;nifty&quot;, &quot;Do nifty things!&quot;);
     * nifty.setTooltip(&quot;This will result in amazingly nifty things happening&quot;);
     * nifty.connect(new Action.Activate() {
     *     public void onActivate(Action source) {
     *     // do something cool
     *     }
     * });
     * 
     * item = new ImageMenuItem(picture, &quot;&quot;);
     * menu.append(item);
     * </pre>
     * 
     * Under ordinary circumstances the text of the MenuItem would be blank
     * (bad), but doing:
     * 
     * <pre>
     * nifty.connectProxy(item);
     * </pre>
     * 
     * will cause the MenuItem's text label to become
     * <code>&quot;Do nifty things!&quot;</code>, and for selecting that
     * MenuItem from the menu to result in the handler you hooked up to
     * <code>nifty</code>'s <code>Action.Activate</code> signal being called.
     * 
     * @since 4.0.6
     */
    public void connectProxy(Widget proxy) {
        GtkAction.connectProxy(this, proxy);
    }
}
