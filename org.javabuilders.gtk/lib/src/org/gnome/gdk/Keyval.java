/*
 * Keyval.java
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

import org.freedesktop.bindings.Constant;

/**
 * A key value as returned by a keyboard Event. Handling keystrokes is not as
 * much fun as you might think. There is considerable complexity associated
 * with dealing with language and country specific keyboard variants. Most of
 * this complexity is, however, dealt with for you by the X server as proxied
 * by GDK. This class, in turn, provides constants for the basic keyboard set.
 * Various subclasses give you access to localized keyboards.
 * 
 * <p>
 * It is important to note that lower case and upper case letters are
 * considered different keys even though most keyboards present but a single
 * physical button with an <b><code>A</code></b> showing. Think of that
 * physical key as having both an <b><code>a</code></b> and an <b>
 * <code>A</code></b> printed on it, with the second being the key reached if
 * the <b><code>Shift</code></b> modifier is held down. Have a look at the
 * {@link ModifierType} class for a discussion of how to deal with the
 * modifying keys and for a better understanding of the sequence in which key
 * events occur.
 * 
 * <p>
 * Looking for <b><code>1</code></b> through <b><code>9</code></b>? See
 * {@link #Num0 Num0} to {@link #Num0 Num9}.
 * 
 * <p>
 * The correlation between keys and Unicode characters is a complex one. For
 * basic western ISO Latin letters the keys we press and the characters we get
 * are, to all intents and purposes, one and the same. But there are many
 * thousands of additional characters for which you probably don't have keys
 * for, even if you have (or have mapped something to be) the <b>
 * <code>Compose</code></b> key for taking the composite of several keystrokes
 * to generate special characters. Just remember that Keyvals are <i>key</i>
 * constants, not constants for every <i>unicode</i> character and you'll keep
 * things straight.
 * 
 * <p>
 * <i>GDK deals with keyvals exclusively as <code>int</code>s, and there are
 * over 1700 of them. Most of these "keys" are ridiculous or apply to hardware
 * that has long since ceased to exist. We've deliberately constrained this
 * class to a narrow range; experience in 2.x showed that even mighty Eclipse
 * chokes when asked to complete from a list that big. This leads us to two
 * consequences. 1) Constants will be registered for keyvals encountered that
 * haven't been explicitly created here, so you don't have to worry about some
 * nasty RuntimeException kicking your ass. 2) You can extend this Keyval
 * class as necessary to create your own constants if you need to. See</i>
 * {@link KeypadKeyval} <i>for an example.</i>
 * 
 * @author Andrew Cowie
 * @since 4.0.6
 */
/*
 * WARNING I've done something in this class that ordinarily would be
 * forbidden, and that is to put raw native values in a bindings class. This
 * is, by definition, a bug and a maintenance nightmare waiting to happen.
 * There are so many constants that merely loading this class would result in
 * hundreds (if not thousands) of JNI calls to populate them.
 * 
 * FIXME So change this to a (define-flags) type behaviour if there are any
 * problems. One idea would be to make it lazy loading, somehow, though
 * perhaps the complexity in that case wouldn't be worth it.
 * 
 * This data for this class was extracted by doing regular expressions on
 * gtk+'s gdk/gdkkeysyms.h; see the script in src/util/keyval/ for details.
 */
public class Keyval extends Constant
{
    /**
     * If you know the name of a GDK keyval, you can instantiate a Constant
     * for it with this method.
     */
    protected Keyval(String name) {
        super(keyvalFromName(name), name.intern());
    }

    protected Keyval(int keyval, String nickname) {
        super(keyval, nickname);
    }

    private static int keyvalFromName(String name) {
        final int result;

        result = GdkKeyval.fromName(name);

        if (result == 0xFFFFFF) {
            throw new IllegalArgumentException("Unknown key name");
        }

        return result;
    }

    /**
     * Translate this Keyval to a unicode representation. This is useful when
     * you've intercepted a keystroke and, having determined it is a "normal"
     * character and not something more unusual, need to append it to a
     * String.
     * 
     * <p>
     * One way this might be used is:
     * 
     * <pre>
     * final Pattern regexAtoZ = Pattern.compile(&quot;[a-z]&quot;);
     * final Keyval key;
     * final String str, result;
     * 
     * str = &quot;&quot; + key.toUnicode();
     * 
     * if (key == Keyval.Return) {
     *     // execute
     * } else if (key == Keyval.Escape) {
     *     // abort
     * } else if (regexAtoZ.matcher(str).matches()) {
     *     entry.setText(str);
     * } else {
     *     ...
     * }
     * </pre>
     * 
     * which is a bit cumbersome, but illustrates one way of identifying
     * "normal" letters being typed.
     * 
     * <p>
     * Not all keys have translations; in such cases <code>0</code> will be
     * returned. Testing for this can sometimes be an easier approach to
     * figuring out if a "normal" key was hit, but your mileage will vary
     * depending on the user's actual configuration. Note that that's
     * <code>(char) 0</code>, not <code>'0'</code>!
     * 
     * <p>
     * <b>Be wary of appending this return value to a String!</b><br>
     * <i>Although Java won't lose the character or subsequent ones,</i>
     * <code>\0</code> <i>is the "<code>NULL</code> terminator" for
     * <code>char*</code> strings in C and will prematurely terminate the
     * printed output if you try to print the String (as, after all, in the
     * final analysis the Java VM is nothing more than a C program and
     * eventually a C library routine will be called to output the text)!</i>
     * 
     * @since 4.0.6
     */
    /*
     * FUTURE This is important, but I have a nagging concern it is expensive.
     * If so, what is a better way to determine a "normal" key event?
     */
    public char toUnicode() {
        return (char) GdkKeyval.toUnicode(GdkKeyvalOverride.numOf(this));
    }

    public static final Keyval BackSpace = new Keyval(0xff08, "BackSpace");

    public static final Keyval Tab = new Keyval(0xff09, "Tab");

    public static final Keyval Return = new Keyval(0xff0d, "Return");

    public static final Keyval Pause = new Keyval(0xff13, "Pause");

    public static final Keyval ScrollLock = new Keyval(0xff14, "ScrollLock");

    public static final Keyval SysReq = new Keyval(0xff15, "SysReq");

    public static final Keyval Escape = new Keyval(0xff1b, "Escape");

    public static final Keyval Delete = new Keyval(0xffff, "Delete");

    public static final Keyval Home = new Keyval(0xff50, "Home");

    public static final Keyval Left = new Keyval(0xff51, "Left");

    public static final Keyval Up = new Keyval(0xff52, "Up");

    public static final Keyval Right = new Keyval(0xff53, "Right");

    public static final Keyval Down = new Keyval(0xff54, "Down");

    public static final Keyval PageUp = new Keyval(0xff55, "PageUp");

    public static final Keyval PageDown = new Keyval(0xff56, "PageDown");

    public static final Keyval End = new Keyval(0xff57, "End");

    public static final Keyval Print = new Keyval(0xff61, "Print");

    public static final Keyval Insert = new Keyval(0xff63, "Insert");

    public static final Keyval Menu = new Keyval(0xff67, "Menu");

    public static final Keyval Break = new Keyval(0xff6b, "Break");

    public static final Keyval NumLock = new Keyval(0xff7f, "NumLock");

    public static final Keyval F1 = new Keyval(0xffbe, "F1");

    public static final Keyval F2 = new Keyval(0xffbf, "F2");

    public static final Keyval F3 = new Keyval(0xffc0, "F3");

    public static final Keyval F4 = new Keyval(0xffc1, "F4");

    public static final Keyval F5 = new Keyval(0xffc2, "F5");

    public static final Keyval F6 = new Keyval(0xffc3, "F6");

    public static final Keyval F7 = new Keyval(0xffc4, "F7");

    public static final Keyval F8 = new Keyval(0xffc5, "F8");

    public static final Keyval F9 = new Keyval(0xffc6, "F9");

    public static final Keyval F10 = new Keyval(0xffc7, "F10");

    public static final Keyval F11 = new Keyval(0xffc8, "F11");

    public static final Keyval F12 = new Keyval(0xffc9, "F12");

    public static final Keyval ShiftLeft = new Keyval(0xffe1, "ShiftLeft");

    public static final Keyval ShiftRight = new Keyval(0xffe2, "ShiftRight");

    public static final Keyval ControlLeft = new Keyval(0xffe3, "ControlLeft");

    public static final Keyval ControlRight = new Keyval(0xffe4, "ControlRight");

    public static final Keyval SuperLeft = new Keyval(0xffeb, "SuperLeft");

    public static final Keyval SuperRight = new Keyval(0xffec, "SuperRight");

    public static final Keyval CapsLock = new Keyval(0xffe5, "CapsLock");

    public static final Keyval AltLeft = new Keyval(0xffe9, "AltLeft");

    public static final Keyval AltRight = new Keyval(0xffea, "AltRight");

    public static final Keyval Space = new Keyval(0x020, "Space");

    public static final Keyval Exclaim = new Keyval(0x021, "Exclaim");

    public static final Keyval QuoteDouble = new Keyval(0x022, "QuoteDouble");

    public static final Keyval NumberSign = new Keyval(0x023, "NumberSign");

    public static final Keyval Dollar = new Keyval(0x024, "Dollar");

    public static final Keyval Percent = new Keyval(0x025, "Percent");

    /**
     * Also known as Carrot or Hat.
     */
    public static final Keyval Circumflex = new Keyval(0x05e, "Circumflex");

    public static final Keyval Ampersand = new Keyval(0x026, "Ampersand");

    public static final Keyval Apostrophe = new Keyval(0x027, "Apostrophe");

    public static final Keyval QuoteSingle = new Keyval(0x027, "QuoteSingle");

    public static final Keyval ParenLeft = new Keyval(0x028, "ParenLeft");

    public static final Keyval ParenRight = new Keyval(0x029, "ParenRight");

    public static final Keyval Asterisk = new Keyval(0x02a, "Asterisk");

    public static final Keyval Plus = new Keyval(0x02b, "Plus");

    public static final Keyval Comma = new Keyval(0x02c, "Comma");

    public static final Keyval Minus = new Keyval(0x02d, "Minus");

    public static final Keyval Period = new Keyval(0x02e, "Period");

    public static final Keyval Slash = new Keyval(0x02f, "Slash");

    public static final Keyval Num0 = new Keyval(0x030, "0");

    public static final Keyval Num1 = new Keyval(0x031, "1");

    public static final Keyval Num2 = new Keyval(0x032, "2");

    public static final Keyval Num3 = new Keyval(0x033, "3");

    public static final Keyval Num4 = new Keyval(0x034, "4");

    public static final Keyval Num5 = new Keyval(0x035, "5");

    public static final Keyval Num6 = new Keyval(0x036, "6");

    public static final Keyval Num7 = new Keyval(0x037, "7");

    public static final Keyval Num8 = new Keyval(0x038, "8");

    public static final Keyval Num9 = new Keyval(0x039, "9");

    public static final Keyval Colon = new Keyval(0x03a, "Colon");

    public static final Keyval Semicolon = new Keyval(0x03b, "Semicolon");

    public static final Keyval Less = new Keyval(0x03c, "Less");

    public static final Keyval Equal = new Keyval(0x03d, "Equal");

    public static final Keyval Greater = new Keyval(0x03e, "Greater");

    public static final Keyval Question = new Keyval(0x03f, "Question");

    public static final Keyval AtSign = new Keyval(0x040, "AtSign");

    public static final Keyval A = new Keyval(0x041, "A");

    public static final Keyval B = new Keyval(0x042, "B");

    public static final Keyval C = new Keyval(0x043, "C");

    public static final Keyval D = new Keyval(0x044, "D");

    public static final Keyval E = new Keyval(0x045, "E");

    public static final Keyval F = new Keyval(0x046, "F");

    public static final Keyval G = new Keyval(0x047, "G");

    public static final Keyval H = new Keyval(0x048, "H");

    public static final Keyval I = new Keyval(0x049, "I");

    public static final Keyval J = new Keyval(0x04a, "J");

    public static final Keyval K = new Keyval(0x04b, "K");

    public static final Keyval L = new Keyval(0x04c, "L");

    public static final Keyval M = new Keyval(0x04d, "M");

    public static final Keyval N = new Keyval(0x04e, "N");

    public static final Keyval O = new Keyval(0x04f, "O");

    public static final Keyval P = new Keyval(0x050, "P");

    public static final Keyval Q = new Keyval(0x051, "Q");

    public static final Keyval R = new Keyval(0x052, "R");

    public static final Keyval S = new Keyval(0x053, "S");

    public static final Keyval T = new Keyval(0x054, "T");

    public static final Keyval U = new Keyval(0x055, "U");

    public static final Keyval V = new Keyval(0x056, "V");

    public static final Keyval W = new Keyval(0x057, "W");

    public static final Keyval X = new Keyval(0x058, "X");

    public static final Keyval Y = new Keyval(0x059, "Y");

    public static final Keyval Z = new Keyval(0x05a, "Z");

    public static final Keyval BracketLeft = new Keyval(0x05b, "BracketLeft");

    public static final Keyval Backslash = new Keyval(0x05c, "Backslash");

    public static final Keyval BracketRight = new Keyval(0x05d, "BracketRight");

    public static final Keyval Underscore = new Keyval(0x05f, "Underscore");

    public static final Keyval Grave = new Keyval(0x060, "Grave");

    public static final Keyval a = new Keyval(0x061, "a");

    public static final Keyval b = new Keyval(0x062, "b");

    public static final Keyval c = new Keyval(0x063, "c");

    public static final Keyval d = new Keyval(0x064, "d");

    public static final Keyval e = new Keyval(0x065, "e");

    public static final Keyval f = new Keyval(0x066, "f");

    public static final Keyval g = new Keyval(0x067, "g");

    public static final Keyval h = new Keyval(0x068, "h");

    public static final Keyval i = new Keyval(0x069, "i");

    public static final Keyval j = new Keyval(0x06a, "j");

    public static final Keyval k = new Keyval(0x06b, "k");

    public static final Keyval l = new Keyval(0x06c, "l");

    public static final Keyval m = new Keyval(0x06d, "m");

    public static final Keyval n = new Keyval(0x06e, "n");

    public static final Keyval o = new Keyval(0x06f, "o");

    public static final Keyval p = new Keyval(0x070, "p");

    public static final Keyval q = new Keyval(0x071, "q");

    public static final Keyval r = new Keyval(0x072, "r");

    public static final Keyval s = new Keyval(0x073, "s");

    public static final Keyval t = new Keyval(0x074, "t");

    public static final Keyval u = new Keyval(0x075, "u");

    public static final Keyval v = new Keyval(0x076, "v");

    public static final Keyval w = new Keyval(0x077, "w");

    public static final Keyval x = new Keyval(0x078, "x");

    public static final Keyval y = new Keyval(0x079, "y");

    public static final Keyval z = new Keyval(0x07a, "z");

    public static final Keyval BraceLeft = new Keyval(0x07b, "BraceLeft");

    /**
     * Also known as Pipe (from the fact that this is used in a Unix shell to
     * "pipe" output from one command to another).
     */
    public static final Keyval Bar = new Keyval(0x07c, "Bar");

    public static final Keyval BraceRight = new Keyval(0x07d, "BraceRight");

    public static final Keyval Tilde = new Keyval(0x07e, "Tilde");
}
