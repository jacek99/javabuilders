/*
 * RadioButton.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd
 * Copyright (c) 2008      Vreixo Formoso
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * A special kind of CheckButton used to select from a mutually exclusive set
 * of options. <img src="RadioButton.png" class="snapshot">
 * 
 * <p>
 * A RadioButton is somewhat similar to a CheckButton, but it is shown as an
 * empty circle (rather than an empty square) and the selected Button in the
 * group us shown with a dot inside (rather than a check mark).
 * 
 * <p>
 * However, while a CheckButton can be used alone to choose between two
 * states, a RadioButton is used together in a group of other (related)
 * RadioButtons to offer a choice of one of those Buttons. Only a single
 * Button in a group can the active at any one time.
 * 
 * <p>
 * To create a group of RadioButtons, you first create a
 * {@link RadioButtonGroup} and then construct the RadioButtons passing them
 * that group object.
 * 
 * <pre>
 * RadioButtonGroup group;
 * RadioButton opt1, opt2, opt3;
 * 
 * group = new RadioButtonGroup();
 * 
 * opt1 = new RadioButton(group, &quot;Option _1&quot;);
 * opt2 = new RadioButton(group, &quot;Option _2&quot;);
 * opt3 = new RadioButton(group, &quot;Option _3&quot;);
 * </pre>
 * 
 * You can get the active option at any time with RadioButtonGroup's
 * {@link RadioButtonGroup#getActive() getActive()} method. And while you can
 * still connect a handler to the ToggleButton's
 * {@link org.gnome.gtk.ToggleButton.TOGGLED TOGGLED} signal, the
 * {@link org.gnome.gtk.RadioButtonGroup.GroupToggled GROUP_TOGGLED} signal is
 * provided as a convenience.
 * 
 * <p>
 * You should generally place related RadioButtons together, better if
 * disposed vertically, as this makes them easier to scan visually - in other
 * words, pack them into a {@link VBox}. It is also frequently a good idea to
 * place a descriptive Label at the top of the Container holding the
 * RadioButtons. A {@link Frame} is a possible way to fit both requirements,
 * as you can use it to place the RadioButtons altogether with a Label at the
 * top:
 * 
 * <pre>
 * Frame option;
 * VBox buttons;
 * 
 * // Create a Frame with a descriptive Label and without outline...
 * option = new Frame(&quot;Option:&quot;);
 * option.setBorderWidth(3);
 * option.setShadowType(ShadowType.NONE);
 * 
 * // ... and with a VBox to layout the RadioButtons
 * buttons = new VBox(false, 2);
 * option.add(buttons);
 * 
 * buttons.add(opt1);
 * buttons.add(opt2);
 * buttons.add(opt3);
 * </pre>
 * 
 * <p>
 * In your applications you will usually use RadioButtons you have two or more
 * mutually exclusive values for an option. Note that if such values are
 * equivalent to the concept of enable/disable a given option,
 * {@link CheckButton} or {@link ToggleButton} are probably a better
 * alternative. In the same way, if you have too many possible values, you
 * should consider using a {@link ComboBox} instead, or even think about the
 * possibility of simplifying your user interface.
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.7
 */
public class RadioButton extends CheckButton
{
    /*
     * Reference keeps our group mechanism in scope, and powers getGroup()
     */
    private RadioButtonGroup enclosingGroup;

    protected RadioButton(long pointer) {
        super(pointer);
    }

    /**
     * Create a new RadioButton with the given label. It will be placed in its
     * own group, you submit it later to the constructors of other Buttons
     * that should be in the same group.
     * 
     * @param label
     *            The label that will be placed near the RadioButton. If the
     *            text contains an underscore (<code>_<code>) it will be taken 
     *            to be the mnemonic for the Widget.
     * @since 4.0.7
     */
    public RadioButton(RadioButtonGroup group, String label) {
        super(GtkRadioButton.createRadioButtonWithLabelFromWidget(group.getMember(), label));
        group.setMember(this);
        enclosingGroup = group;
    }

    /**
     * Get the RadioButtonGroup that encloses this RadioButton and the others
     * that belonging to the same mutual exclusion group.
     * 
     * @since 4.0.7
     */
    public RadioButtonGroup getGroup() {
        return enclosingGroup;
    }
}
