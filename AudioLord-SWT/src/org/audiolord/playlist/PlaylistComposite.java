package org.audiolord.playlist;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.javabuilders.swt.SwtJavaBuilder;

public class PlaylistComposite extends Composite {

	@SuppressWarnings("unused")
	private LibraryComposite library;
	@SuppressWarnings("unused")
	private FilesComposite files;
	@SuppressWarnings("unused")
	private DeviceComposite devices;
	
	/**
	 * @param parent
	 */
	public PlaylistComposite(Composite parent) {
		super(parent,SWT.NONE);
		SwtJavaBuilder.build(this);
	}
}
