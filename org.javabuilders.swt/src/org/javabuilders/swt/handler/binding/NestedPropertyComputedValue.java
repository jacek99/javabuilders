package org.javabuilders.swt.handler.binding;

import org.apache.commons.beanutils.PropertyUtils;
import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.javabuilders.BuildException;

/**
 * Adds nested property support to JFace Databinding (to match Beans Binding functionality from Swing)
 * @author Jacek Furmankiewicz
 */
public class NestedPropertyComputedValue extends ComputedValue {

	private static final String NESTED_MATCHER = ".+\\..+";
	
	/**
	 * @param expression Binding expression to evaluate
	 * @return Evaluates if its a nested property expression
	 */
	public static boolean isNestedProperty(String expression) {
		return expression.matches(NESTED_MATCHER);
	}
	
	private Object source;
	private String expression;
	
	/**
	 * Constructor
	 * @param source Source/root object
	 * @param result Current build result
	 */
	public NestedPropertyComputedValue(Object source, String expression) {
		this.source=source;
		this.expression = expression;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.value.ComputedValue#calculate()
	 */
	@Override
	protected Object calculate() {
		try {
			return PropertyUtils.getNestedProperty(source, expression);
		} catch (Exception e) {
			throw new BuildException(e, "Unable to get a value for expression {0}", expression);
		} 
	}

}
