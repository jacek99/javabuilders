package org.javabuilders.swing.test;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import javax.swing.JButton;

import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.annotations.DoInBackground;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.event.BackgroundEventListener;
import org.javabuilders.event.IBackgroundCallback;
import org.javabuilders.event.IBackgroundProcessingHandler;
import org.javabuilders.swing.handler.event.CommonActionListenerHandler;
import org.javabuilders.swing.handler.property.AbstractButtonActionCommandHandler;
import org.javabuilders.swing.handler.property.AbstractButtonTextHandler;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the background event listener logic
 */
public class BackgroundEventListenerTest {

	private boolean globalStarted = false, globalStopped = false, localStarted = false, localStopped = false, 
		handlerCalled = false, methodCalled = false;
	
	@Before
	public void setup() {
		globalStarted = false;
		globalStopped = false;
		localStarted = false;
		localStopped = false;
		handlerCalled = false;
		methodCalled = false;
	}
	
	@Test
	public void testCustomListeners() {
		TestBuilderConfig config = new TestBuilderConfig(JButton.class);
		
		config.forType(JButton.class)
			.propertyHandler(AbstractButtonActionCommandHandler.getInstance(),CommonActionListenerHandler.getInstance(),
				new AbstractButtonTextHandler());
		
		config.setBackgroundProcessingHandler(new IBackgroundProcessingHandler() {
			@Override
			public void doInBackground(BuildResult result, Object target, Method method, BackgroundEvent event,
					IBackgroundCallback callbackWhenFinished) throws Exception {
				method.invoke(BackgroundEventListenerTest.this, event);
				handlerCalled = true;
			}
		});
		
		config.addBackgroundEventListener(new BackgroundEventListener() {
			@Override
			public void backgroundTaskStarted(BuildResult r, BackgroundEvent evt) {
				globalStarted = true;
			}
			
			@Override
			public void backgroundTaskEnded(BuildResult r, BackgroundEvent evt) {
				globalStopped = true;
			}
		});
		
		
		String yaml =  "JButton(name=button,onAction=backgroundTask)"; 
		
		BuildResult r = Builder.buildFromString(config, this, yaml);
		r.addBackgroundEventListener(new BackgroundEventListener() {
			@Override
			public void backgroundTaskStarted(BuildResult r, BackgroundEvent evt) {
				localStarted = true;
			}
			
			@Override
			public void backgroundTaskEnded(BuildResult r, BackgroundEvent evt) {
				localStopped = true;
			}
		});
		
		JButton button = (JButton) r.get("button");
		assertNotNull(button);
		
		//invoke action and assert that all the listeners have fired
		button.doClick();
		
		assertTrue("Global start not fired", globalStarted);
		assertTrue("Global stop not fired", globalStopped);
		assertTrue("Local start not fired", localStarted);
		assertTrue("Local stop not fired", localStopped);
		assertTrue("Background handler not called", handlerCalled);
		assertTrue("Background method not called", methodCalled);
	}
	
	
	@SuppressWarnings("unused")
	@DoInBackground(blocking=false)
	private void backgroundTask(BackgroundEvent evt) {
		methodCalled = true;
	}
}
