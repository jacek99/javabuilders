package org.javabuilders.event;

public interface IBindingListenerProvider<B> {

	/**
	 * @param listener Binding listener
	 */
	public void addBindingListener(IBindingListener<B> listener);
	
	/**
	 * @param listener Binding listener
	 */
	public void removeBindingListener(IBindingListener<B> listener);
	
	/**
	 * @return Copied array of the binding listeners
	 */
	public IBindingListener<B>[] getBindingListeners();
}
