package org.javabuilders.swing.test;

import static org.junit.Assert.*;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.test.issues.resources.CustomGenericPanel;
import org.javabuilders.swing.util.SwingYamlBuilder;
import org.junit.Test;

public class TypeHandlersTest {

	@Test
	public void frameSizeTest() {
		JFrame f=  (JFrame) new SwingYamlBuilder("JFrame(name=frame,size=200x400):") {{
		}}.build(this).get("frame");
		
		assertEquals(200d,f.getSize().getWidth(),0);
		assertEquals(400d,f.getSize().getHeight(),0);
	}
	
	@Test
	public void framePackedSizeTest() {
		//should accept packed
		JFrame f=  (JFrame) new SwingYamlBuilder("JFrame(name=frame,size=packed):") {{
		}}.build(this).get("frame");
	}
	
	@Test
	public void windowSizeTest() {
		JDialog f=  (JDialog) new SwingYamlBuilder("JDialog(name=dialog,size=200x400):") {{
		}}.build(this).get("dialog");
		
		assertEquals(200d,f.getSize().getWidth(),0);
		assertEquals(400d,f.getSize().getHeight(),0);
	}
	
	@Test
	public void windowPackedSizeTest() {
		//should accept packed
		JDialog f=  (JDialog) new SwingYamlBuilder("JDialog(name=dialog,size=packed):") {{
		}}.build(this).get("dialog");
	}
	
	@Test
	public void dimensionTest() {
		CustomGenericPanel p = new CustomGenericPanel();
		assertEquals(100,p.getDimension().getWidth(),0);
		assertEquals(200,p.getDimension().getHeight(),0);
	}
	
}
