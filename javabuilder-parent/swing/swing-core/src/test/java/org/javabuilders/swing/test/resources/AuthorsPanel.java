package org.javabuilders.swing.test.resources;

import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.SwingPropertyChangeSupport;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.test.issues.resources.Book;
import org.jdesktop.observablecollections.ObservableCollections;

public class AuthorsPanel extends JPanel {
	
	private List<Author> authorsList  = ObservableCollections.observableList(new LinkedList<Author>());
	private Author author;
	private Book book;
	private SwingPropertyChangeSupport support = new SwingPropertyChangeSupport(this);
	
	public BuildResult result = null;

	public AuthorsPanel() {
		authorsList.add(new Author("Charles", "Darwin", "The evolution of species"));
		authorsList.add(new Author("Carl", "Sagan", "Cosmos","Contact"));
		authorsList.add(new Author("Bertrand", "Russell", "The Analysis of Mind","Mysticism and Logic and Other Essays","Proposed Roads To Freedom"));
		authorsList.add(new Author("Isaac","Asimov", "Foundation","Foundation and Empire"));
		
		result = SwingJavaBuilder.build(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	
	/**
	 * @return the authors List
	 */
	public List<Author> getAuthors() {
		return authorsList;
	}


	/**
	 * @return the author
	 */
	public Author getAuthor() {
		return author;
	}


	/**
	 * @param author the author to set
	 */
	public void setAuthor(Author author) {
		Author old = this.author;
		this.author = author;
		support.firePropertyChange("author", old, author);
	}

	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		Book oldValue = this.book;
		this.book = book;
		support.firePropertyChange("books", oldValue, book);
	}


}
