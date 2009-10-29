package org.javabuilders.swing.test.issues.resources;

/**
 * Book POJO
 */
public class Book {
	
	private String author;
	private String title;
	private double price = 0f;

	public Book() {
		super();
	}
	
	public Book(String author, String title) {
		this(author,title,0f);
	}

	public Book(String author, String title, double price) {
		super();
		this.author = author;
		this.title = title;
		this.price = price;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", author,title);
	}


	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
}
