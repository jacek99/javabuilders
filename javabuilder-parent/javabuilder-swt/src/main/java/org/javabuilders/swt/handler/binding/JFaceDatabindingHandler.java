/**
 * 
 */
package org.javabuilders.swt.handler.binding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.*;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.Node;
import org.javabuilders.handler.binding.AbstractBuilderBindingsHandler;
import org.javabuilders.handler.binding.BindingSourceDefinition;
import org.javabuilders.swt.SwtBuilderUtils;
import org.javabuilders.util.PropertyUtils;

/**
 * Implements data binding for the SWT domain using JFace Databinding
 * @author Jacek Furmankiewicz
 *
 */
public class JFaceDatabindingHandler extends AbstractBuilderBindingsHandler {

	public static final String BACKGROUND = "background";
	//public static final String DELAYED_VALUE = "delayedValue";
	public static final String EDITABLE = "editable";
	public static final String ENABLED = "enabled";
	public static final String FONT = "font";
	public static final String FOREGROUND = "foreground";
	public static final String ITEMS = "items";
	public static final String MAX = "max";
	public static final String MIN = "min";
	public static final String SELECTION = "selection";
	public static final String SINGLE_SELECTION_INDEX = "singleSelectionIndex";
	public static final String TEXT = "text";
	public static final String TOOLTIP_TEXT = "tooltipText";
	public static final String VISIBLE = "visible";
	
	private static final String NESTED_MATCHER = ".+\\..+";
	
	@SuppressWarnings("unused")
	private static final int defaultDelayedValueDelay = 50;
	
	private static final JFaceDatabindingHandler singleton = new JFaceDatabindingHandler();
	/**
	 * @return Singleton
	 */
	public static JFaceDatabindingHandler getInstance() { return singleton;}

	/**
	 * @param expression Binding expression to evaluate
	 * @return Evaluates if its a nested property expression
	 */
	public static boolean isNestedProperty(String expression) {
		return expression.matches(NESTED_MATCHER);
	}
	
	private Map<String,Integer> updateStrategies = new HashMap<String, Integer>();
	
	private JFaceDatabindingHandler() {
		updateStrategies.put(READ, SWT.READ_ONLY);
		updateStrategies.put(READ_ONCE, SWT.READ_ONLY);
		updateStrategies.put(READ_WRITE, SWT.Modify);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition,
			Object instance) throws BuildException {
		
		Node node = new Node(parent, key, typeDefinition);
		node.setMainObject(instance);
		DataBindingContext dbc = new DataBindingContext();
		
		process.getBuildResult().setBindingContext(dbc);
		
		Map<NamedObjectProperty,BindingSourceDefinition> defs = getBindingDefinitions(node, process);
		for(NamedObjectProperty targetProperty : defs.keySet()) {
			BindingSourceDefinition sourceDef = defs.get(targetProperty);
		
			int strategy = updateStrategies.get(sourceDef.getUpdateStrategy());
			
			Object source = sourceDef.getSource();
			String sourceExpression = sourceDef.getBindingExpression();
			Object target = process.getByName(targetProperty.getName());
			String targetExpression = targetProperty.getPropertyExpression();
			
			Object sourceObservable = getObservableValue(source, sourceExpression, strategy);
			Object targetObservable = getObservableValue(target, targetExpression, strategy);
			
			if (sourceObservable instanceof IObservableValue && targetObservable instanceof IObservableValue) {
			
				dbc.bindValue((IObservableValue)sourceObservable, (IObservableValue)targetObservable, null, null);
				
			} else if (sourceObservable instanceof IObservableList && targetObservable instanceof IObservableList) {
				
				dbc.bindList((IObservableList)sourceObservable, (IObservableList)targetObservable, null, null);
				
			} else {
				
				throw new BuildException("Unable to bind, incompatible observable types: " + sourceObservable.getClass() 
						+ " / " + targetObservable.getClass());
				
			}
			
		}
		
		return node;
	}
	
	//gets the observable value
	private Object getObservableValue(Object root, String sourceExpression, int strategy)  {
		IObservableValue value = null;
		IObservableList list = null;
		
		Object source = root;
		if (isNestedProperty(sourceExpression)) {
			try {
				String[] parts = SwtBuilderUtils.getNestedBindingExpressionParts(sourceExpression);
				
				source = PropertyUtils.getNestedProperty(root, parts[0]);
				sourceExpression = parts[1];
				
			} catch (Exception e) {
				throw new BuildException("Unable to get value of nested property for databinding: {0}. {1}",
						sourceExpression, e.getMessage());
			} 
		}
		
		try {
		
			if (source instanceof Control) {
				//SWT control
				
				Control control = (Control) source;
				if (BACKGROUND.equals(sourceExpression)) {
					value = SWTObservables.observeBackground(control);
				//} else if (DELAYED_VALUE.equals(sourceExpression)) {
				//	value = SWTObservables.observeDelayedValue(defaultDelayedValueDelay, control);
				} else if (EDITABLE.equals(sourceExpression)) {
					value = SWTObservables.observeEditable(control);
				} else if (ENABLED.equals(sourceExpression)) {
					value = SWTObservables.observeEnabled(control);
				} else if (FONT.equals(sourceExpression)) {
					value = SWTObservables.observeFont(control);
				} else if (FOREGROUND.equals(sourceExpression)) {
					value = SWTObservables.observeForeground(control);
				} else if (ITEMS.equals(sourceExpression)) {
					list = SWTObservables.observeItems(control);
				} else if (MAX.equals(sourceExpression)) {
					value = SWTObservables.observeMax(control);
				} else if (MIN.equals(sourceExpression)) {
					value = SWTObservables.observeMin(control);
				} else if (SELECTION.equals(sourceExpression)) {
					value = SWTObservables.observeSelection(control);
				} else if (SINGLE_SELECTION_INDEX.equals(sourceExpression)) {
					value = SWTObservables.observeSingleSelectionIndex(control);
				} else if (TEXT.equals(sourceExpression)) {
					value = SWTObservables.observeText(control, strategy);
				} else if (TOOLTIP_TEXT.equals(sourceExpression)) {
					value = SWTObservables.observeTooltipText(control);
				} else if (VISIBLE.equals(sourceExpression)) {
					value = SWTObservables.observeVisible(control);
				} else {
					//fallback on JavaBean observable
					value = BeansObservables.observeValue(source, sourceExpression);
				}
				
			} else {
				if (List.class.isAssignableFrom(PropertyUtils.getPropertyType(source, sourceExpression))) {
					list = BeansObservables.observeList(Realm.getDefault(), source, sourceExpression);
				} else {
					//JavaBean observable
					value = BeansObservables.observeValue(source, sourceExpression);
				}
			}
		
			if (value != null) {
				return value;
			} else {
				return list;
			}
			
		} catch (Exception ex) {
			throw new BuildException("Unable to obtain a JFace Databinding observable for {0}:{1} : {2}",
					source.getClass().getName(), sourceExpression, ex.getMessage());
		}
	}
	

}
 