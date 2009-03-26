/**
 * 
 */
package org.audiolord;

import java.sql.SQLException;

import org.audiolord.database.Database;
import org.gnome.gdk.Event;
import org.gnome.gtk.ButtonsType;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.MessageDialog;
import org.gnome.gtk.MessageType;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;
import org.javabuilders.gtk.GtkJavaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application class
 * @author Jacek Furmankiewicz
 */
public class AudioLord extends Window {

	private static final Logger LOGGER = LoggerFactory.getLogger(AudioLord.class);
	
	public AudioLord() {
		GtkJavaBuilder.build(this);
		setTitle("AudioLord");
	}
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			
	        
	        /*
	         * Initialize GTK. You MUST call this to load the library before
	         * trying to use any other elements provided by java-gnome.
	         */

	        Gtk.init(args);
	        final AudioLord window = new AudioLord();
	        
	        
	        window.connect(new Window.DeleteEvent() {
				public boolean onDeleteEvent(Widget source, Event event) {
	                
	                try {
						Database.close();
					} catch (SQLException e) {
						LOGGER.error("AudioLord Database Close Error",e);
						MessageDialog dialog = new MessageDialog(window,true,MessageType.ERROR,ButtonsType.OK,e.getMessage());
						dialog.run();
						dialog.hide();
					}
					
					Gtk.mainQuit();
					
	                return false;
				}
	        });
	        
	        Database.init();
	        window.showAll();
	        Gtk.main();


		
		} catch (Exception e) {
			LOGGER.error("AudioLord Error",e);
			MessageDialog dialog = new MessageDialog(null,true,MessageType.ERROR,ButtonsType.OK,e.getMessage());
			dialog.showAll();
		}

	}

}
