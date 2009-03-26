/*
 * Window.java
 *
 * Copyright (c) 2006-2008 Operational Dynamics Consulting Pty Ltd, and Others
 * 
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import org.gnome.gdk.Event;
import org.gnome.gdk.EventConfigure;
import org.gnome.gdk.Gravity;
import org.gnome.gdk.Pixbuf;
import org.gnome.gdk.Screen;
import org.gnome.gdk.WindowState;
import org.gnome.gdk.WindowTypeHint;

/**
 * The top level Widget that contains other Widgets. Typical examples are
 * application windows, dialog boxes, and popup menus. <img src="Window.png"
 * class="snapshot">
 * 
 * <p>
 * It is common to invoke {@link Widget#hide() hide()} on a new top level
 * Window immediately after instantiating it, as in:
 * 
 * <pre>
 * w = new Window();
 * w.hide();
 * ...
 * // construct Widgets to create user interface and add to w 
 * ...
 * w.showAll();
 * </pre>
 * 
 * this prevents the Window from being drawn as a small empty square and then
 * resizing one or more times as its component Widgets are added to create the
 * desired user interface during the initial cycles of the main loop.
 * 
 * <p>
 * <code>hide()</code> is also important when Windows are being closed; see
 * the comments in the description of {@link Window.DeleteEvent
 * Window.DeleteEvent}.
 * 
 * @author Andrew Cowie
 * @author Srichand Pendyala
 * @author Sebastian Mancke
 * @since 4.0.0
 */
public class Window extends Bin
{
    protected Window(long pointer) {
        super(pointer);
        GtkWindowOverride.dropUserRef(this);
    }

    /**
     * Create a new Window.
     */
    public Window() {
        super(GtkWindow.createWindow(WindowType.TOPLEVEL));
        GtkWindowOverride.dropUserRef(this);
    }

    /**
     * Create a new Window of the specified type. In general you don't need to
     * use this; see the comments in {@link org.gnome.gtk.WindowType
     * WindowType}; in particular, {@link org.gnome.gtk.WindowType#POPUP
     * POPUP} is <b>not</b> for dialog windows!
     * 
     * @since 4.0.0
     */
    public Window(WindowType type) {
        super(GtkWindow.createWindow(type));
        GtkWindowOverride.dropUserRef(this);
    }

    /**
     * Sets the title that will be displayed in the Window's title bar.
     * <p>
     * The title of a Window is an important usability factor. It should help
     * the user distinguish this Window from others they may have open - and
     * that gets tough when many, many applications are running. The key is to
     * get the most relevant information is first. Examples of good titles
     * are:
     * <ul>
     * <li><b>Invoice.odt</b>
     * <li><b>Invoice.odt - OpenOffice</b>
     * <li><b>andrew@procyon:~/src</b>
     * <li><b>Audio Configuration</b>
     * </ul>
     * 
     * <p>
     * This is important because the list of Windows titles the user is
     * looking at may have been truncated with the result that you can't tell
     * the difference between different Windows of the same application. For
     * example, these are no good if you can only see the first 20 characters
     * of the title:
     * 
     * <ul>
     * <li><b>OpenOffice 2.0.4 brought to yo</b>u by the letter B! -
     * Invoice.odt
     * <li><b>OpenOffice 2.0.4 brought to yo</b>u by the letter B! -
     * LoveLetter.odt
     * </ul>
     * 
     * <p>
     * Don't forget that Windows also have an icon, and that icon will show in
     * the list too, so you don't even really need the application name -
     * leaving more room for the details that help identify this Window
     * uniquely.
     * 
     * @see <a
     *      href="http://developer.gnome.org/projects/gup/hig/2.0/windows-primary.html#primary-window-titles">GNOME
     *      Human Interface Guidelines</a>
     * @since 4.0.0
     */
    public void setTitle(String title) {
        GtkWindow.setTitle(this, title);
    }

    /**
     * By default, Windows are decorated with a title bar,
     * minimize/maximize/close buttons, a border, resize handles, etc. This
     * isn't done by your program, though - it's automatically by the window
     * manager which is a part of your desktop. Some window managers allow GTK
     * to disable these decorations, creating a borderless window. If you set
     * the decorated property to <code>false</code> using this method, GTK
     * will do its best to convince the window manager not to decorate the
     * Window.
     * 
     * <p>
     * <ul>
     * <li>You will have no problem creating undecorated Windows on a GNOME
     * desktop.
     * <li>Apparently, turning off decorations will not work if the Window is
     * already visible on some systems. So if you're going to use
     * <code>setDecorated(false)</code>, call it before invoking
     * {@link Widget#show() show()} on the Window.
     * </ul>
     * 
     * @since 4.0.0
     */
    public void setDecorated(boolean setting) {
        GtkWindow.setDecorated(this, setting);
    }

    /**
     * Sets the default size of a Window. If the Window's "natural" size (the
     * size request resulting from the aggregate requests of all the Widgets
     * contained in this Window) is larger than the default, the default will
     * be ignored. The default size of a Window only affects the first time a
     * Window is shown; if a Window is hidden and re-shown, it will remember
     * the size it had prior to hiding, rather than using the default size.
     * 
     * <p>
     * Depending on your needs, {@link #resize(int, int) resize()} could be
     * more appropriate, especially if the Window is already realized.
     * <code>resize()</code> changes the current size of the Window, rather
     * than the size to be used on initial display which is what this method
     * is for.
     * 
     * <p>
     * You probably want to call this after you've done the bulk of your
     * packing.
     * 
     * <p>
     * Incidentally, Windows can't be 0x0; the minimum size is 1x1.
     * 
     * @param width
     *            The default minimum width you'd like to set. A value of 0
     *            will be silently bumped to 1. A value of -1 will unset any
     *            previous default width setting.
     * @param height
     *            Same.
     * @since 4.0.1
     */
    public void setDefaultSize(int width, int height) {
        GtkWindow.setDefaultSize(this, width, height);
    }

    /**
     * Set a new constraint for the position that the Window will be rendered
     * on the screen. Note that this is not always honoured by window
     * managers, but it's a good start.
     * 
     * <p>
     * Somewhat unusually, if the new value for <code>position</code> is
     * {@link WindowPosition#CENTER_ALWAYS CENTER_ALWAYS}, then this call will
     * also result in the Window being moved to the new centered position.
     * 
     * @since 4.0.3
     * @see WindowPosition
     */
    public void setPosition(WindowPosition position) {
        GtkWindow.setPosition(this, position);
    }

    /**
     * Request that the window manager to place the Window in the fullscreen
     * state or return it to normal state.
     * 
     * <p>
     * Note that you shouldn't assume the Window is definitely fullscreen (or
     * restored) afterwards because other entities (e.g. the user or window
     * manager itself) could toggle it again. Further, not all window managers
     * honour requests to fullscreen windows. Be prepared for these
     * eventualities.
     * 
     * @param setting
     *            <code>true</code> to request fullscreen mode,
     *            <code>false</code> to request that the window be returned to
     *            normal management.
     * @since 4.0.4
     */
    public void setFullscreen(boolean setting) {
        if (setting) {
            GtkWindow.fullscreen(this);
        } else {
            GtkWindow.unfullscreen(this);
        }
    }

    /**
     * This signal arises when a user tries to close a top level window. As
     * you would expect, the default handler for this signal destroys the
     * Window.
     * 
     * <p>
     * If you want to prevent a Window from being closed, connect this signal,
     * and return <code>true</code>. Often the reason to do this is to pop up
     * a notification Dialog, for example asking you if you want to save an
     * unsaved document. Another technique is reusing a Window: rather than
     * going to all the trouble to create this Window again, you can just
     * temporarily hide it by calling Widget's {@link Widget#hide() hide()}.
     * 
     * <p>
     * Likewise, if you are going to take a long time to tear down the
     * resources used by the Window or application as a whole (often the case
     * when the user clicks on the <code>[X]</code> button in the window
     * decorations), it is a good idea to call <code>hide()</code>
     * immediately. Many window managers will popup a warning dialog asking
     * you if you want to force-terminate the application if a window is
     * perceived to be "unresponsive" to a close request. You can avoid this
     * by simply hiding the Window while you clean up.
     * 
     * <p>
     * <i>This signal is actually "delete-event" which lives on GtkWidget.
     * That, however, is for implementation reasons in GTK because all the
     * GdkEvents go to GtkWidget even though this particular signal only has
     * to do with Windows. So, we expose it here.</i>
     * 
     * @author Andrew Cowie
     * @author Devdas Bhagat
     * @since 4.0.0
     */
    public interface DeleteEvent extends GtkWidget.DeleteEventSignal
    {
        public boolean onDeleteEvent(Widget source, Event event);
    }

    public void connect(DeleteEvent handler) {
        GtkWidget.connect(this, handler, false);
    }

    /**
     * @deprecated
     */
    public interface DELETE_EVENT extends GtkWidget.DeleteEventSignal
    {
    }

    /**
     * @deprecated
     */
    public void connect(DELETE_EVENT handler) {
        assert false : "use Window.DeleteEvent instead";
        GtkWidget.connect(this, handler, false);
    }

    /**
     * Request that the Window be moved to the specified co-ordinates. As with
     * other Window operations, the window manager running on the display may
     * or may not service the request; in particular you sometimes find that
     * initial placement is overridden by the window manager.
     * 
     * <p>
     * <code>x</code> and <code>y</code> are in pixels.
     * 
     * <p>
     * Chances are
     * {@link org.gnome.gtk.Window#setPosition(org.gnome.gtk.WindowPosition)
     * setPosition()} will do what you want more easily than manually moving
     * the Window.
     * 
     * <p>
     * <i>The co-ordinates <code>x</code>, <code>y</code> are with respect to
     * the reference point specified by the "gravity" in effect for this
     * Window; since the default is</i> {@link Gravity#NORTH_WEST NORTH_WEST}
     * <i>, x and y mean what you want them to: horizontal and vertical
     * distance of the top-left corner of the Window from the top-left corner,
     * respectively.</i>
     * 
     * @since 4.0.4
     */
    public void move(int x, int y) {
        GtkWindow.move(this, x, y);
    }

    /**
     * Set the interpretation of co-ordinates passed to
     * {@link #move(int, int)} and returned by {@link #getPositionX()
     * getPosition()}.
     * 
     * <p>
     * <i>The window manager specification has long been notorious for not
     * actually being a spec; it's more a collection of guesses, assumptions,
     * and outright lies. Unsurprisingly, then, even a window manager that
     * wants to do the right thing can't get it right because there isn't
     * actually a correct answer. Gravity is a case in point, apparently. You
     * might as well consider that things are broken and stick with the
     * default, NORTH_WEST. If you insist on using this anyway, keep in mind
     * that users may experience widely varying results.</i>
     * 
     * @since 4.0.4
     */
    public void setGravity(Gravity gravity) {
        GtkWindow.setGravity(this, gravity);
    }

    /**
     * Get the position of the Window frame (x co-ordinate). This is relative
     * to the Window's gravity setting; since the default is
     * {@link Gravity#NORTH_WEST} this usually means horizontal distance from
     * the top-left corner, which is the normal usage on X displays.
     * 
     * <p>
     * <i>Apparently this is not entirely reliable; X itself does not provide
     * an authoritative means to determine the dimensions of any decorations
     * the window manager might have applied around a Window, and so GTK does
     * its best to guess the necessary adjustments that "should work with sane
     * window managers". We leave it as an exercise to the reader to define
     * sanity.</i>
     * 
     * @since 4.0.4
     */
    public int getPositionX() {
        int[] x = new int[1];
        int[] y = new int[1];

        GtkWindow.getPosition(this, x, y);

        return x[0];
    }

    /**
     * Get the position of the Window frame (y co-ordinate). See
     * {@link #getPositionX()} for details.
     * 
     * @since 4.0.4
     */
    public int getPositionY() {
        int[] x = new int[1];
        int[] y = new int[1];

        GtkWindow.getPosition(this, x, y);

        return y[0];
    }

    /**
     * Get the Screen that this Window is on.
     */
    public Screen getScreen() {
        return GtkWindow.getScreen(this);
    }

    /**
     * Request that this Window be kept on top of all other windows.
     * 
     * <p>
     * Note that the request to apply the "keep above" state may be overridden
     * or ignored by the window manager. Likewise, the user may toggle this
     * state between the program requesting it and the program subsequently
     * proceeding on the expectation that it is set. As a result you should
     * not write code that assumes this request has been successful.
     * 
     * <p>
     * The window manager specifications are fairly explicit that these
     * settings are a user preference. In particular, "keep above" should not
     * be used as a gimmick to attempt to draw attention to a Window.
     * 
     * <p>
     * Usability note: while it always seems like such a good idea to put your
     * favourite window on top of everything else, in practise this can pale.
     * You will find that your current "favourite" changes frequently, and not
     * being able to rely on the normal window management behaviour to bring
     * whatever you are <i>now</i> working on over top of the Window you have
     * kept above will quickly result in you being annoyed that you can't get
     * rid if it. All the usual arguments against modal windows also apply.
     * 
     * <p>
     * Since a proper window manager like <code>Metacity</code> gives you
     * quick and immediate access to the "keep on top" mode via a right click
     * on the Window's title bar decoration, you really only should need this
     * on the rare occasions when you have turned off decorations. So yes,
     * there are are legitimate uses for this, but they are few and far
     * between.
     * 
     * @param setting
     *            <code>true</code> to request keep above be on,
     *            <code>false</code> to request normal behaviour.
     * @since 4.0.4
     */
    public void setKeepAbove(boolean setting) {
        GtkWindow.setKeepAbove(this, setting);
    }

    /**
     * Request that this Window be behind all other windows showing on the
     * desktop.
     * 
     * <p>
     * You can call this before <code>show()</code>ing a Window, in which case
     * the initial presentation will be behind other windows.
     * 
     * <p>
     * The caveats and notes discussed in {@link #setKeepAbove(boolean)
     * setKeepAbove()} apply here. Once again, while there are legitimate uses
     * for this method, please think about the impact on user's overall
     * desktop experience before employing it.
     * 
     * 
     * @param setting
     *            <code>true</code> to request this Window be kept behind all
     *            other windows on the desktop, <code>false</code> for normal
     *            behaviour.
     * @since 4.0.4
     */
    public void setKeepBelow(boolean setting) {
        GtkWindow.setKeepBelow(this, setting);
    }

    /**
     * Request that the Window be visible on all user workspaces.
     * 
     * <p>
     * Many window managers provide the concept of "workspaces" or "virtual
     * desktops" whereby the user can switch from one to another and use this
     * as a means of organizing their work. Ordinarily, an application's
     * Windows will only show in the workspace they appeared in (or to which
     * they were moved by the user). By calling <code>setStick(true)</code>,
     * the Window will always be visible, regardless of which workspace is
     * switched to. While not all window managers have this capability, in
     * general this will work.
     * 
     * <p>
     * Note that the request to stick may not succeed, or may subsequently be
     * reversed by the user.
     * 
     * <p>
     * <i>Some desktops show a thumbnail of each workspace and when a window
     * is stuck, it will "appear" in each workspace thumbnail. This does not
     * mean there are suddenly four copies of your application running or
     * anything silly like that.</i>
     * 
     * @param setting
     *            <code>true</code> to request the Window be stuck,
     *            <code>false</code> to request a return to the normal default
     *            state.
     * @since 4.0.4
     */
    public void setStick(boolean setting) {
        if (setting) {
            GtkWindow.stick(this);
        } else {
            GtkWindow.unstick(this);
        }
    }

    /**
     * Request the window manager maximize (grow to cover the whole screen) or
     * restore (return to normal) this Window.
     * 
     * <p>
     * The difference between this and {@link #setFullscreen(boolean)
     * setFullscreen(true)} is that a maximized Window still has the title at
     * the top of the screen (along with other window decorations, depending
     * on the theme), and the panel(s) are still visible; a fullscreen window
     * is over top of <i>everything</i>. While there are legitimate uses for
     * both, maximizing is somewhat preferable since it does not obscure the
     * panels.
     * 
     * <p>
     * The default for new Windows in ordinary circumstances is that they are
     * not maximized.
     * 
     * <p>
     * Note that maximizing is generally done by the <i>user</i> via the
     * global <code>Alt+F10</code> accelerator or the <code>[&#9633;]</code>
     * button in the window title bar. The only reason to call this
     * programmatically is if restoring the application to the maximized state
     * in order to maintaining the user's preference from a previous run of
     * the program. You can accordingly call this method before a Window is
     * mapped to indicate to the Window manager that you want it to be
     * maximized when shown.
     * 
     * <p>
     * As with all the other window manager operations, this is a request and
     * may or may not be honoured by the window manager depending on what
     * constraints it is operating under.
     * 
     * @since 4.0.4
     */
    public void setMaximize(boolean setting) {
        if (setting) {
            GtkWindow.maximize(this);
        } else {
            GtkWindow.unmaximize(this);
        }
    }

    /**
     * Enquire whether or not this Window is maximized.
     * 
     * <p>
     * While this is in the form of a getter, <var>maximize</var> is not a
     * property, as such. This method only works (ie, reports
     * <code>true</code>) if the Window is actually on the screen (ie has been
     * mapped via a <code>show()</code> call), and really <b>is</b> currently
     * maximized.
     * 
     * <p>
     * In other words, this won't do you any good when initializing your
     * program. It doesn't refer to a future state that will be realized in
     * due course - which means, incidentally that it won't tell you if you
     * already called {@link #setMaximize(boolean) setMaximize(true)} to
     * register your intent to have the window be maximized on presentation.
     * 
     * @since 4.0.4
     */
    public boolean getMaximized() {
        final org.gnome.gdk.Window underlying;
        final WindowState state;

        underlying = super.getWindow();
        if (underlying == null) {
            throw new IllegalStateException("The underlying GdkWindow is null.");
        }
        state = underlying.getState();

        if (state.contains(WindowState.MAXIMIZED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set the icon image to be used for this Window. The icon will appear in
     * the window switcher (what appears when you press <code>Alt+Tab</code>),
     * window list applet and, in most themes, as an identifying image in the
     * top left corner of the window title bar.
     * 
     * <p>
     * Most often you will simply create an image with
     * {@link Pixbuf#Pixbuf(String) Pixbuf(filename)} and pass it in.
     * 
     * <p>
     * A 48x48 PNG image is generally the optimal size to work with; you
     * rarely need icons larger but a lower resolution image will be forced to
     * scale up with the usual poor quality result.
     * 
     * <p>
     * <i>You should specify the image in its natural form, whatever that is,
     * as GTK itself will scale the image depending on the various sizes it is
     * called upon to provide by the theme engine and the window manager.</i>
     * 
     * @since 4.0.5
     */
    public void setIcon(Pixbuf icon) {
        GtkWindow.setIcon(this, icon);
    }

    /**
     * Present the Window to the user. This will raise the window to the top
     * of the stack, deiconify it, and even bring it to the current virtual
     * workspace (all depending, as ever, on how co-operative your window
     * manager is). This is also invokes the equivalent of {@link #show()
     * show()} to [re]map the Window.
     * 
     * <p>
     * This method is ideal for cases where a Window is already showing
     * somewhere and you need to [re]present it to the user. It's also what
     * you should use if you have already called <code>show()</code> to force
     * the Window to map but then immediately called <code>hide()</code> while
     * you finished building the Window.
     * 
     * @since 4.0.5
     */
    public void present() {
        GtkWindow.present(this);
    }

    /**
     * Get the width of the Window.
     * 
     * <p>
     * There are some problems with using this:
     * 
     * <ul>
     * <li>If the Window hasn't been mapped to the screen yet then you will
     * get GTK's current estimate of what it expects to the geometry of the
     * Window might be. This is still highly dependent on what the window
     * manager and the X server actually end up agreeing to allocate.
     * <li>People frequently call this because they want to manually position
     * the Window. In GNOME we discourage doing this. It is the window
     * manager's job to position things, and this reflects among other things
     * accessibility and user preference. Also, GTK is unable to take into
     * account the size of any window decorations that may be present. Use
     * {@link #setPosition(WindowPosition) setPosition()}!
     * <li>If you need to take a dynamic size dependent action you should hook
     * up to the {@link Window.ConfigureEvent} signal which has more accurate
     * information and which will allow you to react appropriately. If you
     * instead use this you will be subject to a race condition as the size of
     * the Window may change between you calling this method and taking action
     * based on the returned value.
     * </ul>
     * 
     * In other words, although this method can be useful for debugging, it's
     * mostly here to tell you what to use instead.
     * 
     * @since 4.0.5
     */
    public int getWidth() {
        int[] width = new int[1];
        int[] height = new int[1];

        GtkWindow.getSize(this, width, height);

        return width[0];
    }

    /**
     * Get the height of the Window.
     * 
     * <p>
     * See {@link #getWidth() getWidth()} for a discussion of the problems
     * that are inherent in using this method.
     * 
     * @since 4.0.5
     */
    public int getHeight() {
        int[] width = new int[1];
        int[] height = new int[1];

        GtkWindow.getSize(this, width, height);

        return height[0];
    }

    /**
     * Set whether the Window is to be modal or not. Modal Windows prevent
     * interaction with other application Windows.
     * 
     * <p>
     * Non-modal Windows give user more flexibility to perform other tasks
     * within your application in any order she or he wants. Leaving Windows
     * non-modal is thus preferred. Making a Window modal should only be done
     * when the application <i>really</i> requires user to not interact with
     * other Windows. Its main usage is in critical Dialogs where an urgent
     * response from the user is required.
     * 
     * <p>
     * If you make a Window modal, you should provide a clear way to close the
     * Window, such as "Close" or "Cancel" Button.
     * 
     * <p>
     * Finally, it is convenient to make modal Windows to be transient for the
     * parent, by calling {@link #setTransientFor(Window) setTransientFor()}.
     * That way, the modal Window will be always on top, even if the user
     * tries to interact with the parent, which is a good way to indicate to
     * them that they must close the modal Window before going on. Usage of
     * modal Windows without marking them transient leads to a severe
     * usability problem: if the user tries to interact with the parent Window
     * when the modal Window is hidden below that parent, the situation will
     * appear as if the application is frozen.
     * 
     * @param modal
     *            <code>true</code> to make the Window modal. The default is
     *            <code>false</code>.
     * @since 4.0.5
     */
    public void setModal(boolean modal) {
        GtkWindow.setModal(this, modal);
    }

    /**
     * Make this Window as transient to another. When marked this way, the
     * window manager will keep the Window above the parent, even if the user
     * tries to interact with the parent.
     * 
     * <p>
     * This is specially useful with modal Windows; see
     * {@link #setModal(boolean) setModal()}.
     * 
     * @param parent
     *            The Window this will be transient for, or <code>null</code>
     *            to prevent this Window for being treated as transient
     *            (that's the default, so you only need to call this when
     *            explicitly setting a Window as transient).
     * @since 4.0.5
     */
    public void setTransientFor(Window parent) {
        GtkWindow.setTransientFor(this, parent);
    }

    /**
     * Set whether the user can resize this Window. Windows are realizable by
     * default.
     * 
     * <p>
     * You'd probably best have a very good reason for suppressing this. GTK's
     * box packing model and size request/size allocation cycle will draw a
     * Window at the optimum size necessary for all the Widgets packed into it
     * to fit and so that all text labels draw properly, etc. That said,
     * sometimes a user wants to see more of a certain column or to expand the
     * area given to the Window so that some dynamic element within (perhaps
     * an image) will scale larger. It is very frustrating for a user to find
     * that the developer has prohibited them from doing such things.
     * 
     * @since 4.0.6
     */
    public void setResizable(boolean setting) {
        GtkWindow.setResizable(this, setting);
    }

    /**
     * Resize the Window to the given <code>width</code> and
     * <code>height</code>.
     * 
     * <p>
     * The size specified here needs to meet or exceed the current aggregate
     * size request for this Window as determined by the children packed into
     * it. If that's a problem, then influence the size request with
     * {@link #setSizeRequest(int, int) setSizeRequest()}.
     * 
     * <p>
     * If you call this before the Window is realized, then the settings
     * provided here will override those specified with
     * {@link #setDefaultSize(int, int) setDefaultSize()}.
     * 
     * @since 4.0.8
     */
    public void resize(int width, int height) {
        if ((width < 1) || (height < 1)) {
            throw new IllegalArgumentException("absolute minimum size is 1x1");
        }
        GtkWindow.resize(this, width, height);
    }

    /**
     * Request that GNOME not include this Window in lists of open windows.
     * These are notably the "Window List" and "Window Selector" applets
     * included with <code>gnome-panel</code> and the on-screen-display popup
     * presented by the window manager. This is useful when creating special
     * purpose auxiliary windows that are not the main program.
     * 
     * <p>
     * Like other "hint" setting methods, this is only a request to the
     * external environment and could potentially be ignored.
     * 
     * <p>
     * You may also need {@link #setSkipPagerHint(boolean) setSkipPagerHint()}.
     * 
     * <p>
     * Note that if the WindowTypeHint of a Window has been set appropriately,
     * you will not need to call this. Therefore use
     * {@link #setTypeHint(WindowTypeHint) setTypeHint()} instead.
     * 
     * @since 4.0.8
     */
    public void setSkipTaskbarHint(boolean setting) {
        GtkWindow.setSkipTaskbarHint(this, setting);
    }

    /**
     * Request that GNOME not include this Window in pagers. Pagers are
     * applets and other utilities which show thumbnails of windows as an aide
     * to navigation and switching. The "Workspace Switcher" applet included
     * with <code>gnome-panel</code> is a pager.
     * 
     * <p>
     * If you're trying to keep this Window off of the list of windows shown
     * as buttons in a panel, then you're probably looking for
     * {@link #setSkipTaskbarHint(boolean) setSkipTaskbarHint()} instead,
     * although this can be a nice touch too.
     * 
     * <p>
     * Note that if the WindowTypeHint of a Window has been set appropriately,
     * you will not need to call this. Therefore use
     * {@link #setTypeHint(WindowTypeHint) setTypeHint()} instead.
     * 
     * @since 4.0.8
     */
    public void setSkipPagerHint(boolean setting) {
        GtkWindow.setSkipPagerHint(this, setting);
    }

    /**
     * Indicate to the window manager what type of use this Window will be put
     * to. While the default is {@link WindowTypeHint#NORMAL NORMAL}, you may
     * find the greatest utility from calling this with the
     * {@link WindowTypeHint#UTILITY UTILITY} hint.
     * 
     * @since 4.0.8
     */
    /*
     * Yes, that's a bad pun. I dare you to do better!
     */
    public void setTypeHint(WindowTypeHint hint) {
        GtkWindow.setTypeHint(this, hint);
    }

    /**
     * Event emitted when the Window's size or position changes. The
     * {@link EventConfigure EventConfigure} object has the position and size
     * information.
     * 
     * <p>
     * This event will also be emitted when the Window is first mapped and
     * when it reappears on the screen having been obscured, so don't count on
     * the values received being different from a previous iteration.
     * 
     * <p>
     * <i>Note that this event signal plays a fairly crucial role in GTK
     * internally; it is used by numerous subsystems (notably the size-request
     * / size-allocation mechanism) to propagate that a Window had a new
     * configuration. Do not attempt to block this signal.</i>
     * 
     * @author Andrew Cowie
     * @since 4.0.8
     */
    /*
     * This is here for the same reason that DeleteEvent is.
     */
    public interface ConfigureEvent extends GtkWidget.ConfigureEventSignal
    {
        /**
         * Return <code>false</code>! Although this is an event signal with a
         * boolean return, there is no point in attempting to block further
         * propagation.
         */
        public boolean onConfigureEvent(Widget source, EventConfigure event);
    }

    /**
     * Hook up a <code>Window.ConfigureEvent</code> handler.
     * 
     * @since 4.0.8
     */
    public void connect(ConfigureEvent handler) {
        GtkWidget.connect(this, handler, false);
    }
}
