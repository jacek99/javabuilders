/*
 * ModifierType.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gdk;

import org.freedesktop.bindings.Flag;

/**
 * Constants representing what modifier keys are being held down on a
 * keystroke, if any. You get an object containing flags that are set via the
 * {@link EventKey#getState() getState()} method on the EventKey you receive
 * when hooking up a <code>Widget.KeyPressEvent</code> or
 * <code>Widget.KeyReleaseEvent</code>.
 * 
 * <p>
 * Try running this fragment if you're confused about the relationship between
 * Keyvals, ModifierTypes, and the keyboard events:
 * 
 * <pre>
 * foo.connect(new Widget.KeyPressEvent() {
 *     public boolean onKeyPressEvent(Widget source, EventKey event) {
 *         final Keyval key;
 *         final ModifierType mod;
 * 
 *         key = event.getKeyval();
 *         mod = event.getState();
 * 
 *         System.out.print(&quot;Pressed: &quot; + key.toString() + &quot;, &quot;);
 *         System.out.print(&quot;Modifier: &quot; + mod.toString() + &quot; &quot;);
 * 
 *         if (mod == ModifierType.SHIFT_MASK) {
 *             System.out.print(&quot;That's Shifty!&quot;);
 *         }
 *         if (mod.contains(ModifierType.ALT_MASK)) {
 *             System.out.print(&quot;Hooray for Alt!&quot;);
 *         }
 * 
 *         System.out.println();
 *         return false;
 *     }
 * });
 * </pre>
 * 
 * For each of the following keystrokes, you'll get a sequence of output
 * something like the following:
 * 
 * <dl>
 * <dt><b><code>A</code></b>
 * <dd>
 * 
 * <pre>
 * Pressed: Keyval.a, Modifier: ModifierType.UNSET
 * </pre>
 * 
 * <dt><b><code>Shift+A</code></b>
 * <dd>
 * 
 * <pre>
 * Pressed: Keyval.ShiftRight, Modifier: ModifierType.UNSET 
 * Pressed: Keyval.A, Modifier: ModifierType.SHIFT_MASK That's Shifty!
 * </pre>
 * 
 * <dt><b><code>Ctrl+A</code></b>
 * <dd>
 * 
 * <pre>
 * Pressed: Keyval.ControlRight, Modifier: ModifierType.UNSET 
 * Pressed: Keyval.a, Modifier: ModifierType.CONTROL_MASK
 * </pre>
 * 
 * <dt><b><code>Ctrl+Shift+A</code></b>
 * <dd>
 * 
 * <pre>
 * Pressed: Keyval.ControlRight, Modifier: ModifierType.UNSET  
 * Pressed: Keyval.ShiftRight, Modifier: ModifierType.CONTROL_MASK 
 * Pressed: Keyval.A, Modifier: ModifierType.SHIFT_MASK|CONTROL_MASK
 * </pre>
 * 
 * <dt><b><code>Alt+A</code></b>
 * <dd>
 * 
 * <pre>
 * Pressed: Keyval.AltRight, Modifier: ModifierType.UNSET 
 * Pressed: Keyval.a, Modifier: ModifierType.ALT_MASK Hooray for Alt!
 * </pre>
 * 
 * <dt><b><code>Ctrl+Alt+A</code></b>
 * <dd>
 * 
 * <pre>
 * Pressed: Keyval.ControlLeft, Modifier: ModifierType.UNSET 
 * Pressed: Keyval.AltLeft, Modifier: ModifierType.CONTROL_MASK 
 * Pressed: Keyval.a, Modifier: ModifierType.CONTROL_MASK|ALT_MASK Hooray for Alt!
 * </pre>
 * 
 * </dl>
 * 
 * <p>
 * The sequence of keystrokes for the modifying keys will depend on the order
 * the user strikes them, but you won't get them showing up as ModifierType
 * constants until a "normal" key is hit. Incidentally, this is where the
 * usefulness of Keyval's {@link Keyval#toUnicode() toUnicode()} come in: you
 * can filter key events until you get one with a non-<code>0</code>
 * translation.
 * 
 * <p>
 * <i>As with Keyval there are many other modifier constants that we haven't
 * bothered to expose. If you need one, feel free to subclass this and add it.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
public final class ModifierType extends Flag
{
    protected ModifierType(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * No modifiers were pressed.
     * 
     * @since 4.0.6
     */
    /*
     * For some reason this isn't explicitly specified in GDK. Well, it's just
     * 0 so you don't need much of a constant for that.
     */
    public static final ModifierType NONE = new ModifierType(0, "NONE");

    /**
     * The <b><code>Shift</code></b> key modifier.
     * 
     * @since 4.0.6
     */
    public static final ModifierType SHIFT_MASK = new ModifierType(GdkModifierType.SHIFT_MASK,
            "SHIFT_MASK");

    /**
     * The <b><code>Control</code></b> key modifier.
     * 
     * @since 4.0.6
     */
    public static final ModifierType CONTROL_MASK = new ModifierType(GdkModifierType.CONTROL_MASK,
            "CONTROL_MASK");

    /**
     * The <b><code>Alt</code></b> key modifier.
     * 
     * <p>
     * <i>The legacy code in the X server as wrapped by GDK has this as
     * <code>MOD1_MASK</code>; you should probably be aware that it is
     * possible that</i> <b><code>Alt</code></b> <i>could be mapped to a
     * different modifier, There are, however, a number of hard coded
     * references tying Mod1 to the Alt key in various places, notably
     * gnome-control-center's <code>gnome-keybinding-properties</code>, so
     * calling this <code>ALT_MASK</code> seems safe enough.</i>
     * 
     * @since 4.0.6
     */
    public static final ModifierType ALT_MASK = new ModifierType(GdkModifierType.MOD1_MASK, "ALT_MASK");

    public static final ModifierType SUPER_MASK = new ModifierType(GdkModifierType.SUPER_MASK,
            "SUPER_MASK");

    public static final ModifierType HYPER_MASK = new ModifierType(GdkModifierType.HYPER_MASK,
            "HYPER_MASK");

    /**
     * The <b><code>Window</code></b> modifier key. You will probably also get
     * <code>SUPER_MASK</code> with this one.
     * 
     * <p>
     * <i>Unless your user has changed things of their X server is doing
     * something weird, it is likely that <code>MOD4_MASK</code> is mapped to
     * the "key with the Microsoft Windows logo" that is present on modern
     * PC104 keyboards. Damn monopolists. Anyway, that's what people call it,
     * so that's that's what we've named our constant.</i>
     * 
     * @since 4.0.6
     */
    public static final ModifierType WINDOW_MASK = new ModifierType(GdkModifierType.MOD4_MASK,
            "WINDOW_MASK");
}
