package org.audiolord;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Display;
import org.javabuilders.swt.SwtJavaBuilder;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class AudioLord {

	public static void main(String[] args) {
		
		final Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				
				try {
					SwtJavaBuilder.getConfig().addResourceBundle("org/audiolord/AudioLord");
					
					ClassPathResource res = new ClassPathResource("audiolord-beans.xml");
					XmlBeanFactory factory = new XmlBeanFactory(res);

					AudioLordWindow w = (AudioLordWindow) factory.getBean("audioLordWindow");
					w.setBlockOnOpen(true);
					w.open();
					Display.getCurrent().dispose();
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
		
		
	}
	
}
