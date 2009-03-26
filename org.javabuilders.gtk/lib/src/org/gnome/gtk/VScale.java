/*
 * VScale.java
 *
 * Copyright (c) 2007-2009 Operational Dynamics Consulting Pty Ltd
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

/**
 * A sliding Widget allowing you to represent data vertically and offer the
 * user the ability to manipulate it. <img src="VScale.png" class="snapshot"
 * style="padding-right: 30px;">
 * 
 * <p>
 * Other than orientation, there's nothing different about VScale, so see
 * {@link HScale} for full discussion of the details of the constructors.
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 * 
 */
public class VScale extends Scale
{
    protected VScale(long pointer) {
        super(pointer);
    }

    public VScale(int min, int max, int step) {
        super(GtkVScale.createVScaleWithRange(min, max, step));
    }
}
