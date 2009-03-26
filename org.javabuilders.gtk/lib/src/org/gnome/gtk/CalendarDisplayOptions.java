/*
 * CalendarDisplayOptions.java
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

import org.freedesktop.bindings.Flag;

/**
 * These options are used to modify the display and behaviour of a
 * {@link Calendar} Widget.
 * 
 * <p>
 * You can specify one or several of these using the method
 * {@link Calendar#setDisplayOptions(CalendarDisplayOptions)
 * setDisplayOptions()} in Calendar.
 * 
 * @author Vreixo Formoso
 * @since 4.0.3
 */
public final class CalendarDisplayOptions extends Flag
{
    private CalendarDisplayOptions(int ordinal, String nickname) {
        super(ordinal, nickname);
    }

    /**
     * When set the Calendar will display a heading showing with the current
     * year and month.
     * 
     * <p>
     * So long as {@link #NO_MONTH_CHANGE} is not specified, the user can
     * change both the month and the year shown in the Calendar using little
     * arrows that will appear in the header.
     */
    public final static CalendarDisplayOptions SHOW_HEADING = new CalendarDisplayOptions(
            GtkCalendarDisplayOptions.SHOW_HEADING, "SHOW_HEADING");

    /**
     * Whether to show the names of the days in the Calendar. The default is
     * for them not to appear.
     */
    public final static CalendarDisplayOptions SHOW_DAY_NAMES = new CalendarDisplayOptions(
            GtkCalendarDisplayOptions.SHOW_DAY_NAMES, "SHOW_DAY_NAMES");

    /**
     * Prevents the user from switching months (and years) with the Calendar
     * widget, forcing it to display only whichever month has been set by the
     * programmer.
     */
    public final static CalendarDisplayOptions NO_MONTH_CHANGE = new CalendarDisplayOptions(
            GtkCalendarDisplayOptions.NO_MONTH_CHANGE, "NO_MONTH_CHANGE");

    /**
     * Displays the week number (relative to the start of the current year)
     * down the left side of the Calendar.
     */
    public final static CalendarDisplayOptions SHOW_WEEK_NUMBERS = new CalendarDisplayOptions(
            GtkCalendarDisplayOptions.SHOW_WEEK_NUMBERS, "SHOW_WEEK_NUMBERS");

    /**
     * Returns a CalendarDisplayOptions object that contains all options
     * specified by the two given arguments. It can be thought as the logical
     * OR between the two Flags.
     */
    public static CalendarDisplayOptions or(CalendarDisplayOptions one, CalendarDisplayOptions two) {
        return (CalendarDisplayOptions) Flag.orTwoFlags(one, two);
    }
}
