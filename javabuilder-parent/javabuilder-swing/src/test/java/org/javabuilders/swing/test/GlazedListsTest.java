package org.javabuilders.swing.test;
import static org.junit.Assert.*;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;

import org.javabuilders.swing.samples.resources.Defect;
import org.javabuilders.swing.test.issues.resources.Book;
import org.javabuilders.swing.test.issues.resources.GlazedListPanel;
import org.junit.Before;
import org.junit.Test;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.EventComboBoxModel;
import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventTableModel;

/**
 * GlazedLists integration functionality
 * @author Jacek Furmankiewicz
 */
public class GlazedListsTest {

	private EventList<Defect> defects = new BasicEventList<Defect>();
	
	@Before
	public void before() {
		defects.clear();
	}
	
	@Test
	public void testJListModel() {
		GlazedListPanel panel = new GlazedListPanel();
		
		//should have one 1 item by default
		JList list = panel.getJList();
		EventListModel<String> model = panel.getModel();
		assertNotNull(list);
		assertNotNull(model);
		assertTrue(list.getModel() instanceof EventListModel);
		assertEquals(1,list.getModel().getSize());
		assertEquals("1",list.getModel().getElementAt(0));
		
		//add 1 to the list
		//wait a little...it's asynchronous (could be delayed)
		try {
			Thread.sleep(200);
			panel.getValues().add("2");
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(2,list.getModel().getSize());
		assertEquals("2",list.getModel().getElementAt(1));
		
		//remove second
		try {
			Thread.sleep(200);
			panel.getValues().remove("2");
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(1,list.getModel().getSize());
		assertEquals("1",list.getModel().getElementAt(0));
		
		//remove first one
		try {
			Thread.sleep(200);
			panel.getValues().remove("1");
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(0,list.getModel().getSize());
	}
	
	@Test
	public void testJComboBoxModel() {
		GlazedListPanel panel = new GlazedListPanel();
		
		//should have one 1 item by default
		JComboBox box = panel.getJComboBox();
		EventComboBoxModel<String> model = panel.getComboBoxModel();
		assertNotNull(box);
		assertNotNull(model);
		assertTrue(box.getModel() instanceof EventComboBoxModel);
		assertEquals(1,box.getModel().getSize());
		assertEquals("1",box.getModel().getElementAt(0));
		
		//add 1 to the list
		//wait a little...it's asynchronous (could be delayed)
		try {
			Thread.sleep(200);
			panel.getValues().add("2");
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(2,box.getModel().getSize());
		assertEquals("2",box.getModel().getElementAt(1));
		
		//remove second
		try {
			Thread.sleep(200);
			panel.getValues().remove("2");
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(1,box.getModel().getSize());
		assertEquals("1",box.getModel().getElementAt(0));
		
		//remove first one
		try {
			Thread.sleep(200);
			panel.getValues().remove("1");
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(0,box.getModel().getSize());
	}
	
	@Test
	public void testSimpleJTableModel() {
		GlazedListPanel panel = new GlazedListPanel();
		
		//should have one 1 item by default
		JTable table = panel.getJTable();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		EventTableModel<Book> model = panel.getTableModel();
		assertNotNull(table);
		assertNotNull(model);
		assertTrue(table.getModel() instanceof EventTableModel);
		assertEquals(3,table.getColumnCount());
		assertEquals(1, table.getModel().getRowCount());
		assertEquals("Author", table.getColumnModel().getColumn(0).getHeaderValue());
		assertEquals("Charles Darwin",table.getModel().getValueAt(0, 0));
		assertEquals("Title", table.getColumnModel().getColumn(1).getHeaderValue());
		assertEquals("Origin of Species",table.getModel().getValueAt(0, 1));
		assertEquals("Price", table.getColumnModel().getColumn(2).getHeaderValue());
		assertEquals(9.99,table.getModel().getValueAt(0, 2));
		
		//add 1 to the list
		//wait a little...it's asynchronous (could be delayed)
		Book book = null;
		try {
			Thread.sleep(200);
			book = new Book("Carl Sagan","Cosmos",12.99);
			panel.addBook(book);
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(2,table.getModel().getRowCount());
		assertEquals("Carl Sagan",table.getModel().getValueAt(1,0));
		assertEquals("Cosmos",table.getModel().getValueAt(1,1));
		assertEquals(12.99,table.getModel().getValueAt(1,2));
		
		//remove second
		try {
			Thread.sleep(200);
			panel.removeBook(book);
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(1,table.getModel().getRowCount());
		assertEquals("Charles Darwin",table.getModel().getValueAt(0, 0));
		assertEquals("Origin of Species",table.getModel().getValueAt(0, 1));
		assertEquals(9.99,table.getModel().getValueAt(0, 2));
		
		//remove first one
		try {
			Thread.sleep(200);
			panel.removeBook(0);
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(0,table.getModel().getRowCount());
	}
	
	@Test
	public void testCustomJTableModel() {
		GlazedListPanel panel = new GlazedListPanel();
		
		//should have one 1 item by default
		JTable table = panel.getJTable2();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		EventTableModel<Book> model = panel.getTableModel();
		assertNotNull(table);
		assertNotNull(model);
		assertEquals(2,table.getColumnCount());
		assertTrue(table.getModel() instanceof EventTableModel);
		assertEquals(1, table.getModel().getRowCount());
		assertEquals("Author", table.getColumnModel().getColumn(0).getHeaderValue());
		assertEquals("Charles Darwin",table.getModel().getValueAt(0, 0));
		assertEquals("Book Title", table.getColumnModel().getColumn(1).getHeaderValue());
		assertEquals("Origin of Species",table.getModel().getValueAt(0, 1));
		
		//add 1 to the list
		//wait a little...it's asynchronous (could be delayed)
		Book book = null;
		try {
			Thread.sleep(200);
			book = new Book("Carl Sagan","Cosmos",12.99);
			panel.addBook(book);
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(2,table.getModel().getRowCount());
		assertEquals("Carl Sagan",table.getModel().getValueAt(1,0));
		assertEquals("Cosmos",table.getModel().getValueAt(1,1));
		
		//remove second
		try {
			Thread.sleep(200);
			panel.removeBook(book);
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(1,table.getModel().getRowCount());
		assertEquals("Charles Darwin",table.getModel().getValueAt(0, 0));
		assertEquals("Origin of Species",table.getModel().getValueAt(0, 1));
		
		//remove first one
		try {
			Thread.sleep(200);
			panel.removeBook(0);
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(0,table.getModel().getRowCount());
	}	
	

}
