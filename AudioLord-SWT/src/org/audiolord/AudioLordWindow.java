package org.audiolord;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.springframework.stereotype.Component;

/**
 * Main application window
 * 
 * @author Jacek Furmankiewicz
 */
@Component("audioLordWindow")
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
		return c;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.ApplicationWindow#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setMaximized(true);
		shell.setSize(1024,768);
	}

}
