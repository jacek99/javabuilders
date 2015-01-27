package org.javabuilders.swing.test.issues.resources;

import javax.swing.JTextField;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

public class CustomGenericPanel extends AbstractGenericPanel {
	private JTextField customTextField;
    private BuildResult result = SwingJavaBuilder.build(this);

    public CustomGenericPanel()
    {
        // customTextField is not null 
        // but genericTextField is null
    }
    
    public JTextField getCustomTextField() {
    	return customTextField;
    }

}
