package org.javabuilders.swing.test;
import static org.junit.Assert.*;

import javax.swing.JList;

import org.javabuilders.swing.test.issues.resources.GlazedListJListPanel;
import org.junit.Test;

import ca.odell.glazedlists.swing.EventListModel;

/**
 * GlazedLists integration functionality
 * @author Jacek Furmankiewicz
 */
public class GlazedListsTests {
	
	@Test
	public void testJListModel() {
		GlazedListJListPanel panel = new GlazedListJListPanel();
		
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
			panel.getValues().remove("2");
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(1,list.getModel().getSize());
		assertEquals("1",list.getModel().getElementAt(0));
		
		//remove first one
		try {
			panel.getValues().remove("1");
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		assertEquals(0,list.getModel().getSize());
	}
	

}
