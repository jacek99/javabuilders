package org.javabuilders.vaadin.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ResourceBundle;

import org.javabuilders.BuildResult;
import org.javabuilders.util.BuilderUtils;
import org.javabuilders.vaadin.VaadinJB;
import org.junit.Test;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;


/**
 * Unit test for simple App.
 */
public class VaadinCoreTest 
{

	@Test
	public void buildVaadinWindow1() throws IOException {
		
		ResourceBundle bundle = ResourceBundle.getBundle("VaadinTestResources");
		
		String yaml = BuilderUtils.getYamlContent(VaadinJB.getConfig(), VaadinCoreTest.class,"VaadinWindow1.yml");
		BuildResult r = VaadinJB.build(this, yaml, bundle);
		
		Window w = (Window) r.get("mainWindow");
		assertNotNull(w);
		
		CustomComponent cc = (CustomComponent) r.get("mainComponent");
		assertNotNull(cc);

		Panel pnl = (Panel)r.get("mainPanel");
		assertNotNull(pnl);
		assertEquals(cc,pnl.getParent());
		//assertEquals(w,cc.getParent());
		
		Button btn = (Button) r.get("btnTest");
		assertNotNull(btn);
		assertEquals(bundle.getString("btnTest.caption"),btn.getCaption());
		assertEquals(bundle.getString("btnTest.desc"),btn.getDescription());
		assertEquals(bundle.getString("btnTest.required"),btn.getRequiredError());
		assertEquals(cc, btn.getParent());
		
		CheckBox cbx = (CheckBox)r.get("cbxTest");
		assertNotNull(cbx);
		assertEquals(cc,cbx.getParent());
		
	}
	
}
