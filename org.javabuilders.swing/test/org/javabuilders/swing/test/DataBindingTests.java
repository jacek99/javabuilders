package org.javabuilders.swing.test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.test.issues.resources.Book;
import org.javabuilders.swing.test.resources.Author;
import org.javabuilders.swing.test.resources.AuthorsPanel;
import org.javabuilders.swing.test.resources.BooksPanel;
import org.javabuilders.swing.util.SwingYamlBuilder;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Data binding tests
 */
public class DataBindingTests {

	private List<Book> books = new LinkedList<Book>();
	
	@Before
	public void setup() {
		Book book1 = new Book("Charles Darwin","The Origin of Species",14.99);
		books.add(book1);
		Book book2 = new Book("Carl Sagan","Cosmos",8.99);
		books.add(book2);	
	}
	
	@After
	public void teardown() {
		books.clear();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testJComboBoxBeansBinding() {
		JComboBox box = new JComboBox();
	
		JComboBoxBinding binding = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE,
				books, box);
		binding.bind();		
		
		assertEquals(books.size(),box.getItemCount());
		assertEquals("Charles Darwin: The Origin of Species", box.getItemAt(0).toString());
		assertEquals("Carl Sagan: Cosmos", box.getItemAt(1).toString());
	}
	
	
	@Test
	public void testJComboBoxModel() {

		BooksPanel panel = new BooksPanel();
		panel.setBooks(books);
		
		BuildResult r = new SwingYamlBuilder("JPanel:") {{
			___("- JComboBox(name=box)");
			bind();
			___("- box.model: this.books");
		}}.build(panel);
		
		JComboBox box = (JComboBox) r.get("box");
		assertNotNull(box);

		assertEquals(books.size(),panel.getBooks().size());
		assertEquals(books.size(),box.getItemCount());
		assertEquals("Charles Darwin: The Origin of Species", box.getItemAt(0).toString());
		assertEquals("Carl Sagan: Cosmos", box.getItemAt(1).toString());
	}
	
	@Test
	public void testJListBeansBinding() {
		JList list = new JList();
		
		JListBinding binding = SwingBindings.createJListBinding(UpdateStrategy.READ_WRITE, books, list);
		binding.bind();
		
		assertEquals(books.size(),list.getModel().getSize());
		assertEquals("Charles Darwin: The Origin of Species", list.getModel().getElementAt(0).toString());
		assertEquals("Carl Sagan: Cosmos", list.getModel().getElementAt(1).toString());

	}
	
	@Test
	public void testJListModel() {

		BooksPanel panel = new BooksPanel();
		panel.setBooks(books);
		
		BuildResult r = new SwingYamlBuilder("JPanel:") {{
			___("- JList(name=list)");
			bind();
			___("- list.model: this.books");
		}}.build(panel);
		
		JList list = (JList) r.get("list");
		assertNotNull(list);

		assertEquals(books.size(),panel.getBooks().size());
		assertEquals(books.size(),list.getModel().getSize());
		assertEquals("Charles Darwin: The Origin of Species", list.getModel().getElementAt(0).toString());
		assertEquals("Carl Sagan: Cosmos", list.getModel().getElementAt(1).toString());
	}
	
	@Test @Ignore
	public void testJListNestedModel() {
		AuthorsPanel panel = new AuthorsPanel();
		
		JList authors = (JList) panel.result.get("authors");
		assertNotNull(authors);
		JList books = (JList) panel.result.get("books");
		assertNotNull(books);
		JTextField firstName = (JTextField) panel.result.get("firstName");
		assertNotNull(firstName);
		JTextField lastName = (JTextField) panel.result.get("lastName");
		assertNotNull(lastName);
		JTextField bookTitle = (JTextField) panel.result.get("bookTitle");
		assertNotNull(bookTitle);
		
		List<Author> list = panel.getAuthors();
		assertEquals(list.size(), authors.getModel().getSize());
		
		for(Author author : list) {

			authors.setSelectedValue(author, true);
			
			assertEquals(author, authors.getSelectedValue());
			
			assertEquals(author.getFirstName(),firstName.getText());
			assertEquals(author.getLastName(),lastName.getText());
			
			List<Book> authorBooks = author.getBooks();
			assertEquals(authorBooks.size(), books.getModel().getSize());
			
			for(Book book : authorBooks) {
				books.setSelectedValue(book,true);
				assertEquals(book.getTitle(), bookTitle.getText());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testJTableBeansBinding() {
		BooksPanel panel = new BooksPanel();
		panel.setBooks(books);
		
		BuildResult r = new SwingYamlBuilder("JPanel:") {{
			___("- JTable(name=table)");
		}}.build(panel);

		JTable table = (JTable) r.get("table");
		assertNotNull(table);
		TableModel model = table.getModel();
		
		//create columns
		TableColumn col = new TableColumn(0);
		col.setHeaderValue("Author Column");
		table.getColumnModel().addColumn(col);
		
		JTableBinding bind = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE, books, table);
		bind.addColumnBinding(0, BeanProperty.create("author")).setColumnClass(String.class).setEditable(true);
		bind.addColumnBinding(1, BeanProperty.create("title")).setColumnClass(String.class).setEditable(true);
		bind.addColumnBinding(2, BeanProperty.create("price")).setColumnClass(double.class).setEditable(true);
		bind.bind();
		
		assertEquals(books.size(), table.getRowCount());
		assertEquals(3, table.getColumnCount());
		
		//check values
		for(int i = 0; i < table.getRowCount();i++) {
			Book book = books.get(i);
			assertEquals(book.getAuthor(), table.getValueAt(i, 0));
			assertEquals(book.getTitle(), table.getValueAt(i, 1));
			assertEquals(book.getPrice(), table.getValueAt(i, 2));
		}
		
		//BB overrides the default model
		assertFalse(model.equals(table.getModel()));
		
		//BB overrides the table column
		assertFalse("Author Column".equals(table.getColumnModel().getColumn(0).getHeaderValue()));
		
	}
	
	@Test @Ignore
	public void testJTableModel() {
		BooksPanel panel = new BooksPanel();
		panel.setBooks(books);
		
		BuildResult r = new SwingYamlBuilder("JPanel:") {{
			___("- JTable(name=list)");
			bind();
			___("- list.model: this.books(author,title,price)");
		}}.build(panel);
		
	}
	
	
}


