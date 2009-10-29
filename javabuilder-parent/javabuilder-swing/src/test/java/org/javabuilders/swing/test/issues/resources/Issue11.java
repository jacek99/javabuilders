package org.javabuilders.swing.test.issues.resources;

import java.awt.PopupMenu;

import javax.swing.JPopupMenu;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingBuilder;

public class Issue11 {

	private BuildResult result = SwingBuilder.build(this);
	private PopupMenu popup;
    private JPopupMenu jpopup;
    
    public Issue11() {
        
    } 
	
	public PopupMenu getPopup() {
		return popup;
	}

	public JPopupMenu getJpopup() {
		return jpopup;
	}



}
