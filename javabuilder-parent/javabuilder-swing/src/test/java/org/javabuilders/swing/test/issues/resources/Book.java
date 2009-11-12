package org.javabuilders.swing.test.issues.resources;

import java.beans.PropertyChangeListener;

import javax.swing.event.SwingPropertyChangeSupport;

/**
 * Book POJO
 */
public class Book {
	
	private String author;
	private String title;
	private Double price = 0d;

	private SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this, true);
	
	public Book() {
		super();
	}
	
	public Book(String author, String title) {
		this(author,title,0d);
	}

	public Book(String author, String title, Double price) {
		super();
		this.setAuthor(author);
		this.setTitle(title);
		this.setPrice(price);
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
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}
	
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		String old = this.author;
		this.author = author;
		pcs.firePropertyChange("author",old,author);
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		String old = this.title;
		this.title = title;
		pcs.firePropertyChange("title", old,title);
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		Double old = this.price;
		this.price = price;
		pcs.firePropertyChange("price", old, price);
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", author,title);
	}
	
    public void addPropertyChangeListener( PropertyChangeListener listener )
    {
        this.pcs.addPropertyChangeListener( listener );
    }

    public void removePropertyChangeListener( PropertyChangeListener listener )
    {
        this.pcs.removePropertyChangeListener( listener );
    }

}
