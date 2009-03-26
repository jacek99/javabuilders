package test;

import org.gnome.gdk.Event;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;
import org.javabuilders.gtk.GtkJavaBuilder;

public class GtkSamplesWindow extends Window {
	
	public GtkSamplesWindow() {
		GtkJavaBuilder.build(this);
	}
	
    public static void main(String[] args) {
        
        /*
         * Initialize GTK. You MUST call this to load the library before
         * trying to use any other elements provided by java-gnome.
         */

        Gtk.init(args);
        GtkSamplesWindow window = new GtkSamplesWindow();
        
        window.connect(new Window.DeleteEvent() {
			public boolean onDeleteEvent(Widget source, Event event) {
                Gtk.mainQuit();
                return false;
			}
        });
        window.showAll();
        Gtk.main();

    }
}
