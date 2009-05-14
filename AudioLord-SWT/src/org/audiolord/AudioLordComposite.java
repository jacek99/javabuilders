package org.audiolord;

import org.audiolord.playlist.PlaylistComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.javabuilders.swt.SwtJavaBuilder;

/**
 * Main app composite
 * @author Jacek Furmankiewicz
 *
 */
public class AudioLordComposite extends Composite {

	@SuppressWarnings("unused")
	private PlaylistComposite playlists;
	
	/**
	 * @param Parent
	 */
	public AudioLordComposite(Composite parent) {
		super(parent,SWT.NONE);
		SwtJavaBuilder.build(this);
	}

	
	
}
