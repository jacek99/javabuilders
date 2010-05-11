package org.javabuilders.swing.test.resources;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.javabuilders.swing.test.issues.resources.Book;
import org.jdesktop.observablecollections.ObservableCollections;

/**
 * Author POJO
 * @author 
 *
 */
public class Author {
	private String firstName;
	private String lastName;
	private List<Book> books = ObservableCollections.observableList(new LinkedList<Book>());

	
	public Author(String firstName, String lastName, String...bookTitles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		for(String title : bookTitles) {
			Book book = new Book(firstName + " " + lastName, title);
			books.add(book);
		}
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}
}
