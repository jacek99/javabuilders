package org.javabuilders.swing.test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;

import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.test.issues.resources.Book;
import org.javabuilders.swing.test.resources.BooksPanel;
import org.javabuilders.swing.util.SwingYamlBuilder;
import org.javabuilders.util.YamlBuilder;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.junit.After;
import org.junit.Before;
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
	
	@Test
	public void testJTableBeansBinding() {
		BooksPanel panel = new BooksPanel();
		panel.setBooks(books);
		
		BuildResult r = new SwingYamlBuilder("JPanel:") {{
			___("- JTable(name=list)");
			bind();
			___("- list.model: this.books(author,title,price)");
		}}.build(panel);
		
	}
	
	
	
	
}
