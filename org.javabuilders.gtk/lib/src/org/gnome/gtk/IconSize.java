/*
 * IconSize.java
 *
 * Copyright (c) 2007 Operational Dynamics Consulting Pty Ltd
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
 * Constants referring to the size of an icon. These are typically used when
 * working with Stock icons, for example with the
 * {@link Image#Image(Stock, IconSize) Image} constructor that takes a Stock
 * image identifier and one of these constants to specify the size you want.
 * 
 * <p>
 * We're rather deliberately <i>not</i> indicated the pixel dimensions
 * associated with each IconSize constant, as this is the sort of thing that
 * can evolve over time - and be theme dependant.
 * 
 * <p>
 * In some sense you don't need this; when working with Stock items in
 * conjunction with Buttons or Menus, the images will be arranged to be the
 * appropriate size automatically.
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public final class IconSize extends Constant
{
    private IconSize(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * The standard size used for icons on Buttons. See also
     * {@link Button#setImage(Image)} for setting an image of arbitrary size.
     */
    public static final IconSize BUTTON = new IconSize(GtkIconSize.BUTTON, "BUTTON");

    /**
     * The standard size used for icons appearing on Menus. See
     * {@link ImageMenuItem}.
     */
    public static final IconSize MENU = new IconSize(GtkIconSize.MENU, "MENU");

    /**
     * The size used when Toolbars are being rendered in their smaller
     * incarnation. Some have noted that icons created for this case bear a
     * striking resemblance to the MENU size.
     */
    public static final IconSize SMALL_TOOLBAR = new IconSize(GtkIconSize.SMALL_TOOLBAR, "SMALL_TOOLBAR");

    /**
     * The size used for icons appearing in large (some would call this
     * normal) size Toolbars.
     */
    public static final IconSize LARGE_TOOLBAR = new IconSize(GtkIconSize.LARGE_TOOLBAR, "LARGE_TOOLBAR");

    public static final IconSize DIALOG = new IconSize(GtkIconSize.DIALOG, "DIALOG");

    public static final IconSize DND = new IconSize(GtkIconSize.DND, "DND");

}
