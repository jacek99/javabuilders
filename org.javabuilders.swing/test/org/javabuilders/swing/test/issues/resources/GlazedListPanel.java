package org.javabuilders.swing.test.issues.resources;

import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.EventComboBoxModel;
import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventTableModel;

//tests GlazedLists models 
public class GlazedListPanel extends JPanel {

	private EventList<String> values = new BasicEventList<String>();
	private EventList<Book> books = new BasicEventList<Book>();
	
	private BuildResult result;
	
	public GlazedListPanel() {
		values.add("1");
		result = SwingJavaBuilder.build(this,ResourceBundle.getBundle("TestResources"));
		
		Book book = new Book("Charles Darwin","Origin of Species",9.99);
		addBook(book);
	}
	
	public void addBook(Book book) {
		books.getReadWriteLock().readLock().lock();
		try {
			books.add(book);
		} finally {
			books.getReadWriteLock().readLock().unlock();
		}
	}
	
	public void removeBook(Book book) {
		books.getReadWriteLock().readLock().lock();
		try {
			books.remove(book);
		} finally {
			books.getReadWriteLock().readLock().unlock();
		}
	}
	
	public void removeBook(int index) {
		removeBook(books.get(index));
	}
	
	public List<String> getValues() {
		return values;
	}
	
	private List<Book> getBooks() {
		return books;
	}
	
	public JList getJList() {
		return (JList) result.get("list");
	}
	
	public EventListModel<String> getModel() {
		return (EventListModel<String>) result.get("model");
	}
	
	public JComboBox getJComboBox() {
		return (JComboBox) result.get("box");
	}
	
	public EventComboBoxModel<String> getComboBoxModel() {
		return (EventComboBoxModel<String>) result.get("boxmodel");
	}
	
	public JTable getJTable() {
		return (JTable) result.get("table");
	}
	
	public EventTableModel<Book> getTableModel() {
		return (EventTableModel<Book>) result.get("tablemodel");
	}
	
	public JTable getJTable2() {
		return (JTable) result.get("table2");
	}
	
	public EventTableModel<Book> getTableModel2() {
		return (EventTableModel<Book>) result.get("tablemodel2");
	}
	
}
