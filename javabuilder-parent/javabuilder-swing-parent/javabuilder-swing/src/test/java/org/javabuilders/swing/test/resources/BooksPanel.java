package org.javabuilders.swing.test.resources;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import org.javabuilders.swing.test.issues.resources.Book;

public class BooksPanel extends JPanel {

	private List<Book> books = new LinkedList<Book>();

	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * @param books the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
