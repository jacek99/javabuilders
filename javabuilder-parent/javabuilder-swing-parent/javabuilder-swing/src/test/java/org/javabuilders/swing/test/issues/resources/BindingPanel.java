package org.javabuilders.swing.test.issues.resources;

import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.SwingPropertyChangeSupport;

import org.javabuilders.swing.SwingJavaBuilder;

public class BindingPanel extends JPanel {

	private Book book = new Book();
	private SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this,true);
	public JTextField author;
	public JTextField title;
	public JTextField price;
	public JTextField author139; //issue 139
	
	public BindingPanel() {
		SwingJavaBuilder.build(this);
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		Book old = this.book;
		this.book = book;
		pcs.firePropertyChange("book", old, book);
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
