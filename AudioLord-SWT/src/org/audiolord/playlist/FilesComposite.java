/**
 * 
 */
package org.audiolord.playlist;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.javabuilders.swt.SwtJavaBuilder;

/**
 * Library playlist composite
 * @author Jacek Furmankiewicz
 *
 */
public class FilesComposite extends Composite {

	public Combo folder;
	private String currentFolder;
	
	/**
	 * @param parent
	 */
	public FilesComposite(Composite parent) {
		super(parent, SWT.NONE);
		SwtJavaBuilder.build(this);
	}
	
	//opens the browse for folder dialog
	@SuppressWarnings("unused")
	private void browse() {
		DirectoryDialog dialog = new DirectoryDialog(getShell());
		String dir = dialog.open();
		if (dir != null) {
			folder.setText(dir);
			IObservableValue test;
		}
		
	}

	/**
	 * @return the current folder
	 */
	public String getCurrentFolder() {
		return currentFolder;
	}

	/**
	 * @param currentFolder the current folder 
	 */
	public void setCurrentFolder(String currentFolder) {
		this.currentFolder = currentFolder;
	}
	
}
