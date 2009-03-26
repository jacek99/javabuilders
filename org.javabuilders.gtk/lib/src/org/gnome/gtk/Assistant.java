/*
 * Assistant.java
 *
 * Copyright (c) 2007-2008 Operational Dynamics Consulting Pty Ltd, and Others
 *
 * The code in this file, and the library it is a part of, are made available
 * to you by the authors under the terms of the "GNU General Public Licence,
 * version 2" plus the "Classpath Exception" (you may link to this code as a
 * library into other programs provided you don't make a derivation of it).
 * See the LICENCE file for the terms governing usage and redistribution.
 */
package org.gnome.gtk;

import org.gnome.gdk.Pixbuf;

/**
 * Guide a user through a multi-step operation one page at a time. You may
 * recognize the name "wizard" or "druid"; GTK calls these Assistants.
 * 
 * <p>
 * An Assistant contains of multiple pages. While each page is just a Widget
 * of your choice, in most cases you will use a layout container as you would
 * in creating a regular top level Window. Each page in the Assistant is
 * marked as a specific type, set with
 * {@link #setPageType(Widget, AssistantPageType) setPageType()}. Be aware
 * that the last page of the Assistant must be of type
 * {@link AssistantPageType#CONFIRM CONFIRM} or
 * {@link AssistantPageType#SUMMARY SUMMARY}.
 * 
 * <p>
 * You can hook to a number of signals which indicate user actions specific to
 * the Assistant:
 * 
 * <ul>
 * <li><code>Assistant.Apply</code> - The user activated the 'Apply' button on
 * the page.</li>
 * <li><code>Assistant.Cancel</code> - The user cancelled the Assistant.</li>
 * <li><code>Assistant.Close</code> - The Assistant closes normally.</li>
 * <li><code>Assistant.Prepare</code> - Another page is going to be displayed</li>
 * </ul>
 * 
 * <p>
 * Listening to the <code>Assistant.Prepare</code> signal allows you to modify
 * the page contents in time, perhaps depending on input made on previous
 * pages.
 * 
 * <p>
 * You create the Assistant with a number of pages with most of them being of
 * type CONTENT. To allow a user to go from one page to the next, the page
 * must be flagged as <var>complete</var>. The 'Next' button will remain
 * disabled until this property is set for that page. The definition of
 * "complete" will necessarily depend on what you are doing on a given page,
 * but typically you hook up to signals emitted from the Widgets inside the
 * page and when contents have changed call
 * {@link #setPageComplete(Widget, boolean) setPageComplete(true)}.
 * 
 * @author Stefan Prelle
 * @author Andrew Cowie
 * @since 4.0.9
 */
public class Assistant extends Window
{
    protected Assistant(long pointer) {
        super(pointer);
    }

    /**
     * Creates a new instance of the Assistant.
     * 
     * @since 4.0.9
     */
    public Assistant() {
        super(GtkAssistant.createAssistant());
    }

    /**
     * Get the page number of the currently displayed page. Page numbers count
     * from <code>0</code>. If the assistant has no pages, <code>-1</code>
     * will be returned.
     * 
     * @since 4.0.9
     */
    public int getCurrentPage() {
        return GtkAssistant.getCurrentPage(this);
    }

    /**
     * Select the page to be displayed to the user.
     * 
     * @since 4.0.9
     */
    public void setCurrentPage(int pageNum) {
        GtkAssistant.setCurrentPage(this, pageNum);
    }

    /**
     * Get the number of pages in the Assistant.
     * 
     * @since 4.0.9
     */
    public int getNumPages() {
        return GtkAssistant.getNPages(this);
    }

    /**
     * Returns the child Widget contained in the given page.
     * 
     * @param pageNum
     *            The index of a page in the assistant. Use <code>-1</code> to
     *            get the last page.
     * @since 4.0.9
     */
    public Widget getPage(int pageNum) {
        final Widget result;

        result = GtkAssistant.getNthPage(this, pageNum);

        if (result == null) {
            throw new IndexOutOfBoundsException("Illegal page number requested");
        }
        return result;
    }

    /**
     * Prepend <code>page</code> to the existing pages in the Assistant.
     * 
     * @return the index (starting at 0) of the inserted page.
     * @since 4.0.9
     */
    public int prependPage(Widget page) {
        return GtkAssistant.prependPage(this, page);
    }

    /**
     * Appends a page to the pages in the Assistant.
     * 
     * @param page
     *            Page to add.
     * @return the index (starting at 0) of the inserted page
     * @since 4.0.9
     */
    public int appendPage(Widget page) {
        return GtkAssistant.appendPage(this, page);
    }

    /**
     * Places a page at a specific position between the pages already existing
     * in the Assistant.
     * 
     * @param page
     *            Page to add.
     * @param position
     *            The index (starting at <code>0</code>) at which to insert
     *            the page, or <code>-1</code> to append the page to the
     *            Assistant.
     * @return The index (starting at <code>0</code>) of the inserted page.
     * @since 4.0.9
     */
    public int insertPage(Widget page, int position) {
        return GtkAssistant.insertPage(this, page, position);
    }

    /**
     * Sets the <var>page-type</var> for the given page. This determines the
     * page's behaviour in the Assistant; see {@link AssistantPageType}.
     * 
     * @since 4.0.9
     */
    public void setPageType(Widget page, AssistantPageType type) {
        GtkAssistant.setPageType(this, page, type);
    }

    /**
     * Get the type of the given Assistant page.
     * 
     * @since 4.0.9
     */
    public AssistantPageType getPageType(Widget page) {
        return GtkAssistant.getPageType(this, page);
    }

    /**
     * Sets the title for a given page. The title is displayed in the header
     * area of the Assistant when page is the current page.
     * 
     * @since 4.0.9
     */
    public void setPageTitle(Widget page, String title) {
        GtkAssistant.setPageTitle(this, page, title);
    }

    /**
     * Obtains the title that has been set for a specific page.
     * 
     * @since 4.0.9
     */
    public String getPageTitle(Widget page) {
        return GtkAssistant.getPageTitle(this, page);
    }

    /**
     * Sets a header image for page. This image is displayed in the header
     * area of the Assistant when <code>page</code> is the current page.
     * 
     * @param page
     *            A page of assistant
     * @param image
     *            the new header image
     * @since 4.0.9
     */
    public void setPageHeaderImage(Widget page, Pixbuf image) {
        GtkAssistant.setPageHeaderImage(this, page, image);
    }

    /**
     * Gets the header image for page.
     * 
     * @since 4.0.9
     */
    public Pixbuf getPageHeaderImage(Widget page) {
        return GtkAssistant.getPageHeaderImage(this, page);
    }

    /**
     * Sets a side image for page. This image is displayed in the side area of
     * the assistant when <code>page</code> is the current page.
     * 
     * @param page
     *            A page of assistant
     * @param image
     *            the new side image
     * @since 4.0.9
     */
    public void setPageSideImage(Widget page, Pixbuf image) {
        GtkAssistant.setPageSideImage(this, page, image);
    }

    /**
     * Gets the side image for page.
     * 
     * @since 4.0.9
     */
    public Pixbuf getPageSideImage(Widget page) {
        return GtkAssistant.getPageSideImage(this, page);
    }

    /**
     * Sets whether page contents are complete. This will make assistant
     * update the buttons state to be able to continue the task.
     * 
     * @since 4.0.9
     */
    public void setPageComplete(Widget page, boolean complete) {
        GtkAssistant.setPageComplete(this, page, complete);
    }

    /**
     * Gets whether page is complete.
     * 
     * @since 4.0.9
     */
    public boolean getPageComplete(Widget page) {
        return GtkAssistant.getPageComplete(this, page);
    }

    /**
     * Adds a widget to the action area of a Assistant.
     * 
     * @since 4.0.9
     */
    public void addActionWidget(Widget child) {
        GtkAssistant.addActionWidget(this, child);
    }

    /**
     * Removes a Widget that has been added with
     * {@link #addActionWidget(Widget)}.
     * 
     * @since 4.0.9
     */
    public void removeActionWidget(Widget child) {
        GtkAssistant.removeActionWidget(this, child);
    }

    /**
     * Force the ... FIXME
     * 
     * @since 4.0.9
     */
    public void updateButtonsState() {
        GtkAssistant.updateButtonsState(this);
    }

    /**
     * The signal emitted every time a page inside the assistant is displayed.
     * This includes the first page as well as every page when flipping
     * forward and backward through the Assistant's pages.
     * 
     * @since 4.0.9
     */
    public interface Prepare extends GtkAssistant.PrepareSignal
    {
        public void onPrepare(Assistant source, Widget page);
    }

    /**
     * Hook up an <code>Assistant.Prepare</code> handler that is called when a
     * new page is displayed.
     * 
     * @since 4.0.9
     */
    public void connect(Assistant.Prepare handler) {
        GtkAssistant.connect(this, handler, false);
    }

    /**
     * The signal emitted when the user is on a <code>CONFIRM</code> page and
     * confirms the input they have given.
     * 
     * @since 4.0.9
     */
    public interface Apply extends GtkAssistant.ApplySignal
    {
        public void onApply(Assistant source);
    }

    /**
     * Hook up an <code>Assistant.Apply</code> handler that will be called
     * when the user chooses to confirm their selections.
     * 
     * @since 4.0.9
     */
    public void connect(Assistant.Apply handler) {
        GtkAssistant.connect(this, handler, false);
    }

    /**
     * This signal emitted at the end of the Assistant's life cycle. This is
     * raised <i>after</i> the <code>Assistant.Apply</code> signal when the
     * handler ends normally.
     * 
     * @since 4.0.9
     */
    public interface Close extends GtkAssistant.CloseSignal
    {
        public void onClose(Assistant source);
    }

    /**
     * Attach a <code>Assistant.Close</code> handler that will be called when
     * the Assistant is closed normally at a <code>CONFIRM</code> or
     * <code>SUMMARY</code> page.
     * 
     * @since 4.0.9
     */
    public void connect(Assistant.Close handler) {
        GtkAssistant.connect(this, handler, false);
    }

    /**
     * The signal emitted if the user cancels the Assistant. This signal will
     * be raised regardless of which page the user happens to be on when they
     * cancel.
     * 
     * @since 4.0.9
     */
    public interface Cancel extends GtkAssistant.CancelSignal
    {
        public void onCancel(Assistant source);
    }

    /**
     * Hook up an <code>Assistant.Cancel</code> handler will be called when
     * the Assistant is cancelled.
     * 
     * @since 4.0.9
     */
    public void connect(Assistant.Cancel handler) {
        GtkAssistant.connect(this, handler, false);
    }

    /*
     * Performs a sanity check whether you thought about having a CONFIRM or
     * SUMMARY page at your last page in the assistant.
     */
    void checkReadyForDisplay() {
        final Widget lastPage;
        final AssistantPageType type;

        lastPage = getPage(getNumPages() - 1);
        type = getPageType(lastPage);

        if ((type != AssistantPageType.CONFIRM) && (type != AssistantPageType.SUMMARY)) {
            throw new IllegalArgumentException("Last page must be of type CONFIRM or SUMMARY");
        }
    }

    /*
     * Override Widget to performs additional sanity checks.
     */
    public void show() {
        checkReadyForDisplay();
        super.show();
    }

    /*
     * Override Widget to performs additional sanity checks.
     */
    public void showAll() {
        checkReadyForDisplay();
        super.showAll();
    }
}
