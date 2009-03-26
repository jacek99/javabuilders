/*
 * StatusIcon.java
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

import org.gnome.gdk.Pixbuf;

/**
 * An icon that is displayed in the notification area.
 * 
 * <p>
 * A StatusIcon is an element that can be added to a notification area. It can
 * be used to display information about user events that may occur (events in
 * the sense of "something interesting", not as in GTK event signal). Examples
 * of user events may be a new email notification, or an incoming instant
 * message, or a completed file transfer. StatusIcons can be used to display
 * information of an activity that is occurring in the background, such as a
 * print job or a file copy activity. A third way of using the StatusIcon
 * capability is to monitor an existing resource; two existing examples are
 * laptop battery charge state from <code>gnome-power-manager</code> and the
 * state of a wireless network connection from NetworkManager's
 * <code>nm-applet</code> program.
 * 
 * <p>
 * Using a StatusIcon in the notification area is generally less annoying than
 * popping up a {@link org.gnome.gtk.Dialog}, but they are also less likely to
 * catch user attention. Thus a StatusIcon can be used to inform the user of
 * an important event, but not necessarily an urgent event.
 * 
 * <p>
 * A StatusIcon can also have an associated tooltip, not to be confused with
 * {@link org.gnome.gtk.Tooltip}. This tooltip appears in a balloon at the
 * StatusIcon itself and can be used to convey additional information.
 * 
 * <p>
 * <b>Do not use as a substitute for writing an applet!</b>. StatusIcons are
 * frequently abused and/or over used. Only add one to your application if it
 * conveys critical status information to the user. If it has a more
 * utilitarian and full-time purpose, it should really be in an applet. In
 * fact, the power and network examples above are both borderline. The battery
 * state in particular should probably be an applet and may well move back to
 * being one; the major argument to make it an applet is to give the user
 * control over where the battery status is positioned on the panel; the
 * counter argument is that in the new power management program, the battery
 * charge state display is transitory and (by default) not shown when not
 * charging. As you can see, it's a complex issue.
 * 
 * <p>
 * It's essential to keep in mind that the Notification Area is itself a panel
 * applet; if one isn't running the image displayed by the StatusIcon may not
 * visible to the user. This is another reason to consider writing an
 * applet... or just go all the way and write a proper daemon program. See the
 * GNOME Human Interface Guidelines for more detailed information and
 * policies.
 * 
 * <p>
 * <i>As it happens, StatusIcon does not derive from Widget for historical
 * reasons related to the inability of some other platforms to adhere to the
 * FreeDesktop standards properly.</i>
 * 
 * 
 * @author Nat Pryce
 * @author Srichand Pendyala
 * @author Andrew Cowie
 * @since 4.0.4
 */
public class StatusIcon extends org.gnome.glib.Object
{
    protected StatusIcon(long pointer) {
        super(pointer);
    }

    /**
     * Create an empty StatusIcon.
     */
    public StatusIcon() {
        super(GtkStatusIcon.createStatusIcon());
    }

    /**
     * Create a StatusIcon that shows the specified Pixbuf.
     */
    public StatusIcon(Pixbuf pixbuf) {
        super(GtkStatusIcon.createStatusIconFromPixbuf(pixbuf));
    }

    /**
     * Create a StatusIcon that displays an image file.
     * 
     * @param filename
     *            The path of an image file.
     */
    /*
     * TODO Both createStatusIconFromFile() and createStatusIconFromIconName()
     * take a String as a parameter, so we can't expose both in a constructor.
     * What can be a better option?
     */
    public StatusIcon(String filename) {
        super(GtkStatusIcon.createStatusIconFromFile(filename));
    }

    /**
     * Create a StatusIcon that shows as its image a given stock item.
     */
    public StatusIcon(Stock stock) {
        super(GtkStatusIcon.createStatusIconFromStock(stock.getStockId()));
    }

    /**
     * Make this StatusIcon display the supplied Pixbuf.
     */
    public void setFromPixbuf(Pixbuf pixbuf) {
        GtkStatusIcon.setFromPixbuf(this, pixbuf);
    }

    /**
     * Make this StatusIcon display the image in file <code>filename</code>.
     * 
     * @param filename
     *            the name of an icon file.
     */
    public void setFromFile(String filename) {
        GtkStatusIcon.setFromFile(this, filename);
    }

    /**
     * Makes this StatusIcon display a stock icon as its image.
     */
    public void setFromStock(Stock stock) {
        GtkStatusIcon.setFromStock(this, stock.getStockId());
    }

    /**
     * Makes this StatusIcon display the named icon from the current icon
     * theme.
     */
    public void setFromIconName(String name) {
        GtkStatusIcon.setFromIconName(this, name);
    }

    /**
     * Gets the type of representation being used by the StatusIcon to store
     * image data. If the StatusIcon has no image data, the return value will
     * be {@link ImageType#EMPTY EMPTY}.
     */
    public ImageType getStorageType() {
        return GtkStatusIcon.getStorageType(this);
    }

    /**
     * Returns the {@link Pixbuf} being displayed by the gtk.StatusIcon. The
     * storage type of the StatusIcon must be {@link ImageType#EMPTY EMPTY} or
     * {@link ImageType#PIXBUF PIXBUF}.
     * 
     * @return the {@link Pixbuf} being displayed by the StatusIcon or
     *         <code>null</code> if the StatusIcon is empty.
     * @see #getStorageType()
     */
    public Pixbuf getPixbuf() {
        return GtkStatusIcon.getPixbuf(this);
    }

    /**
     * Returns the id of the stock icon being displayed by the StatusIcon. The
     * StorageType of the StatusIcon must be {@link ImageType#EMPTY EMPTY} or
     * {@link ImageType#STOCK STOCK}; use {@link #getStorageType()
     * getStorageType()} to find out which it is.
     * 
     * @return the Stock representing of the stock icon being displayed by the
     *         StatusIcon or <code>null</code> if the StatusIcon is empty.
     */
    public Stock getStock() {
        String stockId = GtkStatusIcon.getStock(this);
        return Stock.instanceFor(stockId);
    }

    /**
     * Set the text of the Tooltip for the StatusIcon.
     * 
     * @param text
     *            a value of <code>null</code> will remove the tooltip if
     *            there is one presently set.
     */
    public void setTooltip(String text) {
        GtkStatusIcon.setTooltip(this, text);
    }

    /**
     * Reports if the StatusIcon is visible. Note that being visible does not
     * guarantee that the user can actually see the Status, there must also be
     * a Notification Area applet running to display it; see
     * {@link #isEmbedded() isEmbedded()}
     * 
     * @return <code>true</code> if the StatusIcon is visible,
     *         <code>false</code> otherwise.
     */
    public boolean getVisible() {
        return GtkStatusIcon.getVisible(this);
    }

    /**
     * Shows or hides the StatusIcon. This is equivalent to the more familiar
     * {@link Widget#hide() hide()} functionality you'll be used to with
     * Widgets.
     * 
     * @param visible
     *            <code>true</code> to show the StatusIcon, <code>false</code>
     *            to hide it.
     */
    /*
     * FIXME is that second sentence true?
     */
    public void setVisible(boolean visible) {
        GtkStatusIcon.setVisible(this, visible);
    }

    /**
     * Reports if the StatusIcon is blinking.
     */
    public boolean getBlinking() {
        return GtkStatusIcon.getBlinking(this);
    }

    /**
     * Make the StatusIcon start [or stop] blinking.
     * 
     * <p>
     * Note that blinking user interface elements may be problematic for some
     * users and may be turned off on a desktop wide level, in which case this
     * setting would have no effect. Even ignoring the accessibility question,
     * many people find blinking StatusIcons annoying in general. Use
     * sparingly, if at all.
     * 
     * @param setting
     *            <code>true</code> to start the StatusIcon blinking,
     *            <code>false</code> to stop it blinking.
     */
    public void setBlinking(boolean setting) {
        GtkStatusIcon.setBlinking(this, setting);
    }

    /**
     * Gets the size available for the image, in pixels. Stock icons adapt
     * their size automatically if the size of the notification area changes.
     * For other storage types, the {@link StatusIcon.SizeChanged} signal can
     * be used to react to size changes.
     */
    public int getSize() {
        return GtkStatusIcon.getSize(this);
    }

    /**
     * Reports if the StatusIcon is embedded in a notification area. This
     * being <code>true</code> implies that the Notification Area applet is
     * running and added to one of the user's panels on the desktop.
     * 
     * <p>
     * <i>Sometimes users forget to do that, or remove their Notification Area
     * applet by accident and don't know how to get it back. On the panel,
     * just</i> <b>Right Click &gt; Add to Panel...</b><i> and select</i>
     * <b>Notification Area</b>.
     */
    public boolean isEmbedded() {
        return GtkStatusIcon.isEmbedded(this);
    }

    /**
     * The signal emitted when the user activates the StatusIcon. In general
     * this happens when the user left-clicks on this StatusIcon's image in
     * the notification area.
     */
    /*
     * FIXME any other ways?
     */
    public interface Activate extends GtkStatusIcon.ActivateSignal
    {
        /**
         * The signal emitted when the user activates the StatusIcon.
         * 
         * @param source
         *            the StatusIcon that was activated.
         */
        void onActivate(StatusIcon source);
    }

    /**
     * Hook up a <code>StatusIcon.Activate</code> handler
     * 
     * @since 4.0.4
     */
    public void connect(StatusIcon.Activate handler) {
        GtkStatusIcon.connect(this, handler, false);
    }

    /** @deprecated */
    public interface ACTIVATE extends GtkStatusIcon.ActivateSignal
    {
    }

    /** @deprecated */
    public void connect(ACTIVATE handler) {
        assert false : "use StatusIcon.Activate instead";
        GtkStatusIcon.connect(this, handler, false);
    }

    /**
     * The signal emitted when the user brings up the context menu of the
     * StatusIcon.
     */
    public interface PopupMenu extends GtkStatusIcon.PopupMenuSignal
    {
        /**
         * The signal emitted when the user right-clicks on the StatusIcon.
         * You're almost certain to want to be bringing up a context menu in
         * that case, so see Menu's {@link Menu#popup(StatusIcon) popup()} for
         * details of how to do it.
         * 
         * <p>
         * The two additional parameters in the handler prototype are
         * described below, but we disregard them and they may be removed in a
         * future version of java-gnome.
         * 
         * @param button
         *            the button that was pressed, or 0 if the signal is not
         *            emitted in response to a button press event.
         * @param activateTime
         *            the timestamp of the event that triggered the signal
         *            emission.
         */
        /*
         * FIXME activateTime is an int?!? If it's not long seconds since
         * epoch, what does it mean?
         */
        void onPopupMenu(StatusIcon source, int button, int activateTime);
    }

    public void connect(StatusIcon.PopupMenu handler) {
        GtkStatusIcon.connect(this, handler, false);
    }

    /** @deprecated */
    public interface POPUP_MENU extends GtkStatusIcon.PopupMenuSignal
    {
    }

    /** @deprecated */
    public void connect(POPUP_MENU handler) {
        assert false : "use StatusIcon.PopupMenu instead";
        GtkStatusIcon.connect(this, handler, false);
    }

    /**
     * Signal emitted when the size available for the StatusIcon's image
     * changes. This happens if the panel the Notification Area applet is
     * running in gets resized.
     */
    public interface SizeChanged extends GtkStatusIcon.SizeChangedSignal
    {
        /**
         * Signal emitted when the size available for the image changes.
         * 
         * @param source
         *            the object which received the signal.
         * @param size
         *            the new size, in pixels.
         */
        boolean onSizeChanged(StatusIcon source, int size);
    }

    public void connect(StatusIcon.SizeChanged handler) {
        GtkStatusIcon.connect(this, handler, false);
    }

    /** @deprecated */
    public interface SIZE_CHANGED extends GtkStatusIcon.SizeChangedSignal
    {
    }

    /** @deprecated */
    public void connect(SIZE_CHANGED handler) {
        assert false : "use StatusIcon.SizeChanged instead";
        GtkStatusIcon.connect(this, handler, false);
    }
}
