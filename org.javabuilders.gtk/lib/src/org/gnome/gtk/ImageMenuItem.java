/*
 * ImageMenuItem.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * A MenuItem which displays an icon Image to the left of the Label text. This
 * is used almost exclusively to add the MenuItems with common behaviour from
 * the family of Stock icons, for example "File->Quit" and "Edit->Copy", so
 * that the icons used are consistent with the rest of the GNOME Desktop.
 * Indeed, it is somewhat discouraged to create ImageMenuItems with your own
 * icons as too many icons can distract visually from the fact that the stock
 * ones allow the eye to quickly recognize expected UI elements.
 * 
 * <p>
 * See Action's {@link Action#createMenuItem() createMenuItem()}; you
 * frequently need to do a UI activity from more than one place and Action
 * will (among other things) generate the ImageMenuItem for a given Stock
 * item. See also Button's {@link Button#setImage(Image) setImage()} for a
 * discussion of the relationship between Images and Label text.
 * 
 * @author Andrew Cowie
 * @since 4.0.5
 */
public class ImageMenuItem extends MenuItem
{
    protected ImageMenuItem(long pointer) {
        super(pointer);
    }

    /**
     * Create a MenuItem displaying a stock icon image.
     */
    /*
     * TODO Passing null as the second argument probably isn't quite what we
     * want. You can change the AccelGroup separately, but that's available to
     * support overriding the default accelerators. All in all this will need
     * updating.
     */
    public ImageMenuItem(Stock stock) {
        super(GtkImageMenuItem.createImageMenuItemFromStock(stock.getStockId(), null));
    }

    /**
     * Convenience constructor, allowing you to create a MenuItem displaying a
     * stock icon while simultaneously hooking up the handler which will take
     * its <code>MenuItem.Activate</code> signals.
     */
    public ImageMenuItem(Stock stock, MenuItem.Activate handler) {
        this(stock);
        connect(handler);
    }

    /** @deprecated */
    public ImageMenuItem(Stock stock, MenuItem.ACTIVATE handler) {
        this(stock);
        connect(handler);
    }

    /**
     * Create a MenuItem displaying an image next to text. When you have a
     * custom visual that is appropriate to show beside the label of a given
     * MenuItem, you can use this to construct it.
     * 
     * <p>
     * Don't use an empty string as a label! Users can turn off display of
     * icons in menus, and if they do you'll end up with a MenuItem with
     * "nothing" in it.
     * 
     * <p>
     * There is also a constructor which allows you to connect an
     * <code>MenuItem.Activate</code> handler in-line, see
     * {@link ImageMenuItem#ImageMenuItem(Image, String, org.gnome.gtk.MenuItem.Activate)
     * here}.
     * 
     * @since 4.0.6
     */
    public ImageMenuItem(Image image, String label) {
        super(GtkImageMenuItem.createImageMenuItemWithMnemonic(label));
        GtkImageMenuItem.setImage(this, image);
    }

    /**
     * Create a MenuItem displaying an image next to text, and hook up an
     * <code>MenuItem.Activate</code> handler at the same time.
     * 
     * @since 4.0.6
     */
    public ImageMenuItem(Image image, String label, MenuItem.Activate handler) {
        super(GtkImageMenuItem.createImageMenuItemWithMnemonic(label));
        GtkImageMenuItem.setImage(this, image);
        connect(handler);
    }

    /** @deprecated */
    public ImageMenuItem(Image image, String label, MenuItem.ACTIVATE handler) {
        super(GtkImageMenuItem.createImageMenuItemWithMnemonic(label));
        GtkImageMenuItem.setImage(this, image);
        connect(handler);
    }

    /**
     * Set the Image that will be used as an icon beside the text in the
     * ImageMenuItem.
     * 
     * <p>
     * Be aware that there are system wide settings which allow a user to turn
     * off icons appearing in menus. Since there is always a chance that the
     * "image" Widget being added will be hidden, don't create an
     * ImageMenuItem with an empty label.
     * 
     * @since 4.0.6
     */
    public void setImage(Image image) {
        GtkImageMenuItem.setImage(this, image);
    }
}
