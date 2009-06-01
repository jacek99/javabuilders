/**
 * 
 */
package org.audiolord.playlist;

import org.audiolord.database.Database;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.javabuilders.swt.SwtJavaBuilder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Library playlist composite
 * @author Jacek Furmankiewicz
 *
 */
public class LibraryComposite extends Composite {

	@Autowired(required=true)
	private Database database;
	
	/**
	 * @param parent
	 */
	public LibraryComposite(Composite parent) {
		super(parent, SWT.NONE);
		SwtJavaBuilder.build(this);
	}

}
