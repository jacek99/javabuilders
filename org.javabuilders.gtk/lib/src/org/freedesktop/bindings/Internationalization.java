/*
 * Internationalization.java
 *
 * Copyright (c) 2008      Vreixo Formoso
 * Copyright (c) 2008-2009 Operational Dynamics Consulting Pty Ltd
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.freedesktop.bindings;

import java.io.File;
import java.text.MessageFormat;

/**
 * Internationalization support for java-gnome. A few definitions:
 * 
 * <p>
 * <dl>
 * <dt>Locale</dt>
 * <dd>The combination of translations into a specific language along with
 * formatting as used in a given country are collectively referred to as a
 * <i>locale</i>.
 * <dt>Internationalization</dt>
 * <dd><i>Internationalization</i> is the process of preparing applications
 * to support multiple languages. You will frequently see this abbreviated as "<code>i18n</code>".</dd>
 * <dt>Localization</dt>
 * <dd>Whereby other people convert your software into their own locale.
 * <i>Localization</i>, as this is process is referred to, involves more than
 * just translating words but also involves reformatting expressions of dates
 * and numbers so they look "correct" in that locale. Frequently abbreviated
 * as "<code>l10n</code>".</dd>
 * </dl>
 * 
 * <p>
 * The objective is that messages be shown to the user in his or her native
 * language, not in English (or whatever language the developer was working
 * in). Note that internationalization does not actually mean that you, as an
 * application developer, need to translate your software to all the locales
 * where it is planned to be used. This is done later, by translators.
 * Applications developers just need to be aware of this, and prepare their
 * software to be localized later. This preparation is what we call
 * internationalization.
 * 
 * <p>
 * First of all, we need to allow for message translation. This simply means
 * not outputting hard coded English Strings directly and instead calling a
 * function that will redirect to the message translation. Similarly, the
 * other aspects of internationalization such as the way dates, numbers,
 * currency, etc are formatted is locale-dependent. The functions in this
 * class will enable you to prepare your application for translation.
 * 
 * <h2>Use translatable Strings instead of hard coded Strings</h2>
 * 
 * <p>
 * Let's take the following Java code as a typical example:
 * 
 * <pre>
 * greeting = new Label(&quot;Good morning&quot;);
 * </pre>
 * 
 * Fortunately, you do not need to know how this message is written in all
 * languages of the world! You let the translators take care of that.
 * 
 * <p>
 * One approach would have been to give your sources to each translation team
 * and have them hunt through the code for each and every String. This would
 * be quite a nightmare, of course, and worse would require the translators to
 * be a programmer on par with yourself. Worst of all, it would require a copy
 * of the application specific to each locale to be distributed, which of
 * course would be ridiculous.
 * 
 * <p>
 * The people doing translations tend <i>not</i> to be hard-core developers
 * but they are making a very valuable contribution to your software by
 * offering to translate it. So we undertake internationalization in a way
 * that enables them to do the localization without needing to be Java
 * programmers.
 * 
 * <p>
 * Instead of the hard coded String shown above, we wrap it in the translation
 * function as follows:
 * 
 * <pre>
 * greeting = new Label(_(&quot;Good morning&quot;));
 * </pre>
 * 
 * The {@link #_(String, Object...) Internationalization._()} function takes
 * care of looking up your String in the translation database and will return
 * the same message localized to the user's native language. Obviously using
 * the static import:
 * 
 * <pre>
 * import static org.freedesktop.bindings.Internationalization._;
 * </pre>
 * 
 * will make things clean and elegant.
 * 
 * <p>
 * In java-gnome, internationalizing your apps is as easy as the code above.
 * You should use the <code>_()</code> function with any message you want to
 * show to the user. Messages intended for developers only (such as debug
 * messages going to the log) do not need to be localized.
 * 
 * <h2>Positional parameters</h2>
 * 
 * <p>
 * Matters become somewhat more complicated when you need to concatenate
 * various parameters into a composite String. Consider the common use of the
 * <code>+</code> operator:
 * 
 * <pre>
 * System.out.println(&quot;The file &quot; + filename + &quot; was modified on &quot; + date);
 * </pre>
 * 
 * This code is not internationalized at all. Not only do we need to allow the
 * message to be translated, but we must also allow for the the date to be
 * formatted appropriately. In java-gnome, you do this as follows:
 * 
 * <pre>
 * System.out.println(_(&quot;The file {0} was modified on {1,date,long}&quot;, filename, date));
 * </pre>
 * 
 * The added complexity comes from the need to cater for the "positional
 * parameters" (here <code>filename</code> and <code>date</code>) which
 * may have a different order when rendered in someone else's language. Again,
 * you don't need to know the specifics for every possible target locale, you
 * just need to supply the information in a form that can be localized.
 * 
 * <p>
 * To format these parameters, you use the {@link MessageFormat} syntax. Put
 * briefly, you will:
 * 
 * <ul>
 * <li>Refer to any parameter with a <code>{n}</code> in your message,
 * where <code>n</code> is the <code>0</code>-based index of the
 * parameter as you submitted to the <code>_()</code> function.</li>
 * <li>If the parameter needs to be formatted (numbers, dates, currency, etc)
 * you pass a format type parameter ("<code>number</code>", "<code>date</code>", "<code>time</code>")
 * and optionally a format style qualifier (in the above example, "<code>long</code>",
 * indicating a longer form). See the MessageFormat documentation for further
 * details.</li>
 * </ul>
 * 
 * <p>
 * Of course, computers don't make magic (still), so you still need a
 * translation process to actually localize your messages. However, with this
 * approach this is done outside the code, in files named "message catalogues"
 * where the translated messages are stored. In fact, the <code>_()</code>
 * function will look up the given message in that catalogues, to show the
 * translated version to the user. As a developer, you don't need to create
 * them, it is the task of translator. However, knowledge of that process is
 * useful, so we outline it below.
 * 
 * <h2>Locales</h2>
 * 
 * Most users are not aware of this, but having selected a "language" when
 * they first logged in they initialize their <code>LANG</code> and
 * <code>LC_MESSAGES</code> environment variables.
 * 
 * <p>
 * Examples of locales include:
 * <dl>
 * <dt><code>en_CA.UTF-8</code>
 * <dd>English as written and used in Canada
 * <dt><code>en_UK.UTF-8</code>
 * <dd>English as written and used in Britain
 * <dt><code>en</code>
 * <dd>English (generic)
 * <dt><code>es_ES.UTF-8</code>
 * <dd>Spanish as written and used in Spain
 * <dt><code>es</code>
 * <dd>Spanish (generic)
 * <dt><code>fr_CA.UTF-8</code>
 * <dd>French as used in Canada
 * <dt><code>fr_FR.UTF-8</code>
 * <dd>French as used in France
 * <dt><code>fr</code>
 * <dd>French (generic)
 * </dl>
 * etc. You'll note the addition of qualifiers like <code>.UTF-8</code>
 * which are how a locale indicates support for specific character sets.
 * 
 * <p>
 * There is also one other locale you will see:
 * <dl>
 * <dt><code>C</code>
 * <dd>the untranslated domain, typically expressed in English.
 * </dl>
 * which is what you write in your programs.
 * 
 * <p>
 * Note that <b>nothing</b> requires you to to use English for the
 * untranslated messages in your source code. English is, however, the
 * <i>lingua franca</i> of our age, and more to the point is the language
 * which most translation teams understand and translate from. If you are
 * doing your own translations, then go right ahead and program in whatever
 * language you want. On the other hand, if you wish to leverage the GNOME
 * Translation Project's expertise, we recommend that your untranslated
 * Strings be in basic English.
 * 
 * <p>
 * Indeed, using uncomplicated English Strings will mean that you will be less
 * likely to "break" Strings (thereby causing the translation teams'
 * localizations to no longer work). Even if you are a native English speaker,
 * we recommend that you localize your own work into (say) <code>en_AU</code>
 * or <code>en_CA</code> as this will cause you to be aware of translation
 * issues - and will minimize String breaks.
 * 
 * <p>
 * As you can imagine, the gettext and glibc libraries fallback in a
 * predictable order. They try to find a translation that is appropriate for
 * your locale, starting with the fully qualified <code>LANG</code> variable
 * and then steadily degrading. For example, if your
 * <code>LANG=fr_CA.UTF-8</code>, you could expect the following sequence
 * of locales to be searched:
 * 
 * <ul>
 * <li>fr_CA.UTF-8
 * <li>fr_CA
 * <li>fr.UTF-8
 * <li>fr
 * <li>C
 * </ul>
 * 
 * <h2>Employing gettext</h2>
 * 
 * <p>
 * java-gnome uses the GNU <a
 * href="http://www.gnu.org/software/gettext/">gettext</a> suite, which is
 * the same translation infrastructure that GNOME and most other Linux
 * applications use. The process used by <code>gettext</code> to generate
 * the message catalogues is as follows:
 * 
 * <p>
 * First of all, the messages used in your code need to be extracted. This is
 * done by the <code>xgettext</code> command. It is able to distinguish
 * between translatable messages and other Strings because the former are
 * marked with the calls to <code>_()</code>. So, the following call:
 * 
 * <pre>
 * $ xgettext -o myapp.pot --omit-header --keyword=_ --keyword=N_ path/to/TYPE.java
 * </pre>
 * 
 * will extract the messages used in the TYPE.java class to a file called
 * <code>myapp.pot</code>. A POT file is a template with the list of
 * translatable messages which translators will use to know the message they
 * must translate. With the command:
 * 
 * <pre>
 * $ msginit -i myapp.pot -o ${LANG}.po
 * </pre>
 * 
 * they can generate a PO file for their particular language which is where
 * the messages will be translated. PO files contain the translated messages.
 * There is one PO file per locale, named with the standard scheme
 * <code>language_COUNTRY</code>. Examples are <code>en_CA.po</code>,
 * <code>es_ES.po</code>, <code>fr.po</code>, <code>pt.po</code>,
 * <code>pt_BR.po</code>.... Typically, the PO files of a given project are
 * stored in a directory named <code>po/</code>.
 * 
 * <p>
 * To be used by gettext, those files need to be "compiled" to a binary form,
 * known as MO files. That is done with the <code>msgfmt</code> command:
 * 
 * <pre>
 * $ msgfmt -o myapp.mo es.po
 * </pre>
 * 
 * MO files are installed together with other application artifacts, usually
 * under
 * <code>/usr/share/locale/${locale}/LC_MESSAGES/${packageName}.mo</code>,
 * where <code>${locale}</code> is the locale and
 * <code>${packageName}</code> is a unique identifier for the program,
 * usually your application name. This name is needed because when installed
 * to the system, MO files are stored under a directory common to all
 * installed apps. For example, localized messages for the <code>pt_BR</code>
 * locale of the <code>myapp</code> application will be packaged as
 * <code>/usr/share/locale/pt_BR/LC_MESSAGES/myapp.mo</code>.
 * 
 * <p>
 * As you can imagine, you will need to tell gettext where those files are
 * physically located. This is done with the
 * {@link #init(String, String) init()} method. This <b>must</b> be called
 * before any usage of java-gnome internationalization infrastructure.
 * 
 * <pre>
 * public void main(String[] args) {
 *     Gtk.init(args);
 *     Internationalization.init(&quot;myapp&quot;, &quot;share/locale/&quot;);
 *     ...
 * }
 * </pre>
 * 
 * <p>
 * In some cases, this might be a problem. If you have messages stored in a
 * static array initializer use the {@link #N_(String) N_()} function to mark
 * these messages, then use <code>_()</code> later on the variable carrying
 * the constant. See <code>N_()</code> for more details.
 * 
 * @author Vreixo Formoso
 * @author Andrew Cowie
 * @since 4.0.7
 */
public final class Internationalization
{
    private Internationalization() {}

    /**
     * Translate and format a given message according to user locale.
     * 
     * <p>
     * This attempts to translate a text string into the user's native
     * language. You just need to call it with the message in <code>C</code>,
     * as follows:
     * 
     * <pre>
     * String translated = _(&quot;Hello&quot;);
     * </pre>
     * 
     * <p>
     * The java-gnome implementation of <code>_()</code> also supports message
     * formatting and concatenation in a language-neutral way. For example,
     * let's suppose we want to print the following message: "The file
     * 'data.log' has been modified at March 21, 2008 at 5:27:22 PM". This
     * message actually has two parameters, the filename, and the date of
     * modification. This data is locale-dependent, as the dates are
     * represented differently depending on language and country. We could get
     * the internationalized message with:
     * 
     * <pre>
     * String filename;
     * Date date;
     * _(&quot;The file '{0}' has been modified on {1,date,long} at {1,time}&quot;, filename, date);
     * </pre>
     * 
     * As you can see, it is easy to construct a given message from several
     * parameters, even when some parameters need locale-dependent formatting.
     * 
     * <p>
     * The actual formatting is done by {@link MessageFormat}, so take a look
     * at its documentation for all available format options. Translation is
     * done handled by gettext before the message is passed to MessageFormat
     * for further handling of the positional parameters.
     * 
     * @param msg
     *            The message to print. This is the untranslated message,
     *            usually in English.
     * @param parameters
     *            Parameters of the message
     * @return The message translated and properly formatted. If no
     *         translation is found, the original English is returned.
     * @since 4.0.7
     */
    public static final String _(String msg, java.lang.Object... parameters) {
        if (msg == null) {
            return null;
        }
        /*
         * gettext()'s behaviour is undefined if given an empty String
         */
        if (msg.equals("")) {
            return msg;
        }

        if (parameters.length == 0) {
            return gettext(msg);
        } else {
            return MessageFormat.format(gettext(msg), parameters);
        }
    }

    private static native final String gettext(String msg);

    /**
     * Mark the given message as translatable, without actually translating
     * it. This is used for static fields that are initialized before
     * Internationalization is initialized:
     * 
     * <pre>
     * private static final String BUTTON_MESSAGE = N_(&quot;Press me!&quot;);
     * </pre>
     * 
     * You still need to call <code>_()</code> later, to actually translate
     * the message.
     * 
     * <pre>
     * button.setLabel(_(BUTTON_MESSAGE));
     * </pre>
     * 
     * Obviously the problem now is to come up with constant names that are
     * unobtrusive. There are various different naming schemes that can be
     * employed; all are somewhat ugly. In general this leads to people not
     * using Strings in static initializers as much as they might have been
     * used to. Indeed, the whole point of abstracting out such Strings (so
     * that they are in one place at the "top" of the file) is less relevant
     * given that the gettext tools will be extracting all your messages
     * anyway.
     * 
     * @param msg
     *            The message to mark as translatable
     * @return The <code>msg</code> argument, <i>not</i> translated. Remember,
     *         <code>N_()</code> is only used to mark a String as translatable
     *         so that <code>xgettext</code> can extract it.
     * @since 4.0.7
     */
    public static final String N_(String msg) {
        return msg;
    }

    /**
     * Initialize internationalization support. You <b>must</b> call this
     * function before any other usage of the methods on the
     * Internationalization class, ie:
     * 
     * <pre>
     * public void main(String[] args) {
     *     Gtk.init(args);
     *     Initialization.init(&quot;myapp&quot;, &quot;/usr/share/locale/&quot;);
     *     ...
     * }
     * </pre>
     * 
     * 
     * @param packageName
     *            Application name
     * @param localeDir
     *            Directory where to find the message catalogues (usually
     *            <code>/usr/share/locale</code>) The actually message
     *            catalogue is found at
     *            <code>${localeDir}/${locale}/LC_MESSAGES/${packageName}.mo</code>
     *            For example:
     *            <code>/usr/share/locale/pt_BR/LC_MESSAGES/myapp.mo</code>.
     *            It is not compulsory to use an absolute path for the
     *            <code>localeDir</code> parameter.
     * @since 4.0.7
     */
    public static final void init(String packageName, String localeDir) {
        final File locale;

        if (((packageName == null) || packageName.equals(""))) {
            throw new IllegalArgumentException("packageName cannot be null or empty");
        }

        if (localeDir == null) {
            throw new IllegalArgumentException("localeDir cannot be null");
        }
        locale = new File(localeDir);
        if (!locale.isDirectory()) {
            throw new FatalError("\nThe supplied locale base dir \"" + localeDir + "\" is not found");
        }

        bindtextdomain(packageName, localeDir);
    }

    private static native final void bindtextdomain(String packageName, String localeDir);
}
