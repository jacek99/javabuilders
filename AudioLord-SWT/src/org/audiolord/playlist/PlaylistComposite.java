package org.audiolord.playlist;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.javabuilders.swt.SwtJavaBuilder;

public class PlaylistComposite extends Composite {

	/**
	 * @param parent
	 */
	public PlaylistComposite(Composite parent) {
		super(parent,SWT.NONE);
		SwtJavaBuilder.build(this);
	}

}
