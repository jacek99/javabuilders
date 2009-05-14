package org.audiolord;

import org.audiolord.playlist.PlaylistComposite;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.javabuilders.swt.SwtJavaBuilder;

/**
 * Main application window
 * 
 * @author Jacek Furmankiewicz
 */

public class AudioLordWindow extends ApplicationWindow {

	/**
	 * @param parentShell
	 */
	public AudioLordWindow() {
		super(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets
	 * .Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite c = new AudioLordComposite(parent);
		getShell().setMaximized(true);
		return c;
	}

	public static void main(String[] args) {
		
		SwtJavaBuilder.getConfig().addResourceBundle("org/audiolord/AudioLord");
		
		AudioLordWindow w = new AudioLordWindow();
		w.setBlockOnOpen(true);
		w.open();
		Display.getCurrent().dispose();
	}

}
