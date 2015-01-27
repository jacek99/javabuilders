package org.javabuilders.event;

import java.util.EventListener;

import org.javabuilders.BuildResult;

/**
 * A listener for binding events
 * @author jacekf
 *
 * @param <B> Domain-specific binding
 */
public interface IBindingListener<B> extends EventListener {

	/**
	 * Fired right after binding was created, but before it was activated
	 * @param binding
	 */
	public void bindingCreated(BuildResult result, B binding);
	
}
