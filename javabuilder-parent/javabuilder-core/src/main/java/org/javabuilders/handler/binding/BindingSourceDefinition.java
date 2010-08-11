package org.javabuilders.handler.binding;

import org.javabuilders.BuildException;

/**
 * Defines the source expression or property to be used in data binding
 * @author Jacek Furmankiewicz
 */
public class BindingSourceDefinition {

	public Object source;
	public String bindingExpression;
	public String updateStrategy;
	
	/**
	 * @param source Source object
	 * @param bindingExpression Binding expression
	 */
	public BindingSourceDefinition(Object source, String bindingExpression, String updateStrategy) {
		super();
		this.bindingExpression = bindingExpression;
		this.source = source;
		this.updateStrategy = updateStrategy;
	}

	/**
	 * @return Source
	 */
	public Object getSource() {
		return source;
	}
	/**
	 * @param source Source
	 */
	public void setSource(Object source) {
		this.source = source;
	}
	/**
	 * @return Binding expression
	 */
	public String getBindingExpression() {
		return bindingExpression;
	}
	/**
	 * @param bindingExpression Binding expression
	 */
	public void setBindingExpression(String bindingExpression) {
		this.bindingExpression = bindingExpression;
	}
	
	/**
	 * @return Update strategy
	 */
	public String getUpdateStrategy() {
		return updateStrategy;
	}

	/**
	 * @param updateStrategy Update strategy
	 */
	public void setUpdateStrategy(String updateStrategy) {
		this.updateStrategy = updateStrategy;
	}

	
	/**
	 * @return If binding expression refers to a single property or an expression with multiple ones
	 */
	public boolean isSingleProperty() {
		if (getBindingExpression() != null) {
			return !getBindingExpression().matches(".*\\$\\{.+}.*");
		 } else {
			 throw new BuildException("No Binding Expression defined yet: " + getBindingExpression());
		 }
		
	}
	
}
