package org.javabuilders.swing.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.javabuilders.BuildResult;
import org.javabuilders.event.IBindingListener;
import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.test.issues.resources.BindingPanel;
import org.javabuilders.swing.test.issues.resources.Book;
import org.javabuilders.swing.test.resources.Author;
import org.javabuilders.swing.test.resources.AuthorsPanel;
import org.javabuilders.swing.test.resources.BooksPanel;
import org.javabuilders.swing.util.SwingYamlBuilder;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
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
public class DataBindingTest {

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
	
	@Test
	public void testJComboBoxBeansBinding() {
		JComboBox box = new JComboBox();
	
		@SuppressWarnings("rawtypes")
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
		
		JListBinding<Book, ?, ?> binding = SwingBindings.createJListBinding(UpdateStrategy.READ_WRITE, books, list);
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
		
		@SuppressWarnings("rawtypes")
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
		
		@SuppressWarnings("unused")
		BuildResult r = new SwingYamlBuilder("JPanel:") {{
			___("- JTable(name=list)");
			bind();
			___("- list.model: this.books(author,title,price)");
		}}.build(panel);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testPOJOBinding() throws InterruptedException {
		
		@SuppressWarnings("rawtypes")
		IBindingListener listener = null;
		
		final AtomicInteger counter = new AtomicInteger(0);
		
		try {
		
			listener = new IBindingListener<Binding<? extends Object,? extends Object,? extends Object,? extends Object>>() {
				@Override
				public void bindingCreated(BuildResult result,
						Binding<? extends Object, ? extends Object, ? extends Object, ? extends Object> binding) {
					counter.incrementAndGet();
				}
			};
			
			SwingJavaBuilder.getConfig().addBindingListener(
					new IBindingListener<Binding<? extends Object,? extends Object,? extends Object,? extends Object>>() {
				@Override
				public void bindingCreated(BuildResult result,
						Binding<? extends Object, ? extends Object, ? extends Object, ? extends Object> binding) {
					counter.incrementAndGet();
				}
			});
			
			BindingPanel p = new BindingPanel();
			
			//test from UI to POJO
			p.author.setText("Charles Darwin");
			assertEquals("Charles Darwin", p.getBook().getAuthor());
			p.title.setText("The Origin of Species");
			assertEquals("The Origin of Species", p.getBook().getTitle());
			p.price.setText("12.99");
			assertEquals(12.99, p.getBook().getPrice(),0);
			
			//test from POJO to UI - need to sleep after each setter to give events time to fire
			//asynchronously
			p.getBook().setAuthor("Carl Sagan");
			Thread.sleep(100);
			assertEquals("Carl Sagan", p.author.getText());
			p.getBook().setTitle("Cosmos");
			Thread.sleep(100);
			assertEquals("Cosmos", p.title.getText());
			p.getBook().setPrice(5.99);
			Thread.sleep(100);
			assertEquals("5.99", p.price.getText());
			
			//change whole book in one shot
			Book book = new Book("Stanislaw Lem","Przygody pilota Pirxa",9.99);
			p.setBook(book);
			Thread.sleep(100);
			assertEquals("Stanislaw Lem", p.author.getText());
			assertEquals("Przygody pilota Pirxa", p.title.getText());
			assertEquals("9.99", p.price.getText());

			//test from UI to POJO with synthetic BBB properties (issue 139)
			//since ON_FOCUS_LOST was not triggered, value should not have been updated
			p.author139.setText("Charles Darwin");
			assertEquals("Stanislaw Lem", p.getBook().getAuthor());
			
			//assert binding listeners were fired
			assertEquals("Binding listeners were not fired",4,counter.get());
			
		} finally {
			SwingJavaBuilder.getConfig().removeBindingListener(listener);
		}
		
	}
	
	
}


