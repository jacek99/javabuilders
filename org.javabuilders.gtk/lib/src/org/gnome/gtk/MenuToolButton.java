/*
 * MenuToolButton.java
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
 * A MenuToolButton is an special kind of ToolButton that has an additional
 * drop-down Menu.
 * 
 * <p>
 * Next to the ToolButton itself, a MenuToolButton shows another little Button
 * with an arrow. When the user clicks this additional Button, a drop-down
 * Menu pops up.
 * 
 * <p>
 * The main usage of a MenuToolButton is to provide access to several related
 * actions in a Toolbar without wasting too much screen space. For example,
 * your application can have a MenuToolButton for the "New Document" action,
 * using the attached Menu to let users choose what kind of new document they
 * want to create.
 * 
 * <p>
 * A MenuToolButton has a default action, to be executed when user clicks the
 * main Button itself and not the the arrow Button. You can capture that
 * default event with the {@link ToolButton.Clicked} signal. User Menu
 * selections are captured with the usual {@link MenuItem.Activate} signal of
 * each <code>MenuItem</code>.
 * 
 * @see Toolbar
 * @see Menu
 * 
 * @author Vreixo Formoso
 * @since 4.0.4
 */
public class MenuToolButton extends ToolButton
{
    protected MenuToolButton(long pointer) {
        super(pointer);
    }

    /**
     * Creates a new MenuToolButton using the given icon and Label.
     * 
     * @param iconWidget
     *            The Widget to be used as the icon for the MenuToolButton.
     *            Usually you will want to use a Widget display an image, such
     *            as {@link Image}.
     * @param label
     *            The Label for the MenuToolButton.
     */
    public MenuToolButton(Widget iconWidget, String label) {
        super(GtkMenuToolButton.createMenuToolButton(iconWidget, label));
    }

    /**
     * Creates a new MenuToolButton from the specific stock item. Both the
     * Label and icon will be set properly from the stock item. By using a
     * system stock item, the newly created MenuToolButton with use the same
     * Label and Image as other GNOME applications. To ensure consistent look
     * and feel between applications, it is highly recommended that you use
     * provided stock items whenever possible.
     * 
     * @param stock
     *            The StockId that will determine the Label and icon of the
     *            MenuToolButton.
     */
    public MenuToolButton(Stock stock) {
        super(GtkMenuToolButton.createMenuToolButtonFromStock(stock.getStockId()));
    }

    /**
     * Sets the Menu to be popped up when the user clicks the arrow Button.
     */
    /*
     * FIXME: you can pass null to make arrow insensitive, but this is not
     * supported until we complete the 'null-ok' branch.
     */
    public void setMenu(Menu menu) {
        GtkMenuToolButton.setMenu(this, menu);
    }

    /**
     * Get the Menu associated with the MenuToolButton.
     * 
     * @return The associated Menu or <code>null</code> if no Menu has been
     *         set.
     */
    public Menu getMenu() {
        return (Menu) GtkMenuToolButton.getMenu(this);
    }
}
